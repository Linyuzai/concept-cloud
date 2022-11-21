package com.github.linyuzai.concept.cloud.web;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactory;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChain;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainFactory;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainImpl;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class RestControllerAndResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final WebContextFactory webContextFactory;

    private final WebInterceptorChainFactory webInterceptorChainFactory;

    private final List<WebInterceptor> requestInterceptors;

    private final List<WebInterceptor> responseInterceptors;

    private final List<WebInterceptor> errorInterceptors;

    public RestControllerAndResponseBodyAdvice(WebContextFactory webContextFactory,
                                               WebInterceptorChainFactory webInterceptorChainFactory,
                                               List<WebInterceptor> interceptors) {
        Map<WebInterceptor.Type, List<WebInterceptor>> interceptorsMap = interceptors.stream()
                .collect(Collectors.groupingBy(WebInterceptor::getType, Collectors.toList()));
        this.requestInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.REQUEST, new ArrayList<>());
        this.responseInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.RESPONSE, new ArrayList<>());
        this.errorInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.ERROR, new ArrayList<>());
        this.webContextFactory = webContextFactory;
        this.webInterceptorChainFactory = webInterceptorChainFactory;
    }

    //@InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @ModelAttribute
    public void modelAttribute(HttpServletRequest request, HttpServletResponse response) {
        WebContext context = webContextFactory.create()
                .put(HttpServletRequest.class, request)
                .put(HttpServletResponse.class, response);
        WebInterceptorChain chain = webInterceptorChainFactory.create(requestInterceptors);
        chain.next(context);
    }

    @ExceptionHandler({Throwable.class})
    public Object handleException(HttpServletRequest request, ServerHttpResponse response, Throwable e) {
        WebContext context = webContextFactory.create()
                .put(HttpServletRequest.class, request)
                .put(HttpServletResponse.class, response)
                .put(Throwable.class, e);
        WebInterceptorChain chain = webInterceptorChainFactory.create(errorInterceptors);
        chain.next(context);
        return context.get(Throwable.class);
    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        WebContext context = webContextFactory.create()
                .put(ServerHttpRequest.class, request)
                .put(ServerHttpResponse.class, response)
                .put(MethodParameter.class, returnType)
                .put(WebContext.Key.RESPONSE_BODY, body);
        WebInterceptorChain chain = webInterceptorChainFactory.create(responseInterceptors);
        chain.next(context);
        Object result = context.get(WebContext.Key.RESPONSE_BODY);
        context.global().reset();
        return result;
    }
}
