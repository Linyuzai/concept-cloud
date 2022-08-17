package com.github.linyuzai.concept.cloud.web;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactory;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainImpl;
import com.github.linyuzai.concept.cloud.web.result.WebResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class RestControllerAndResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final WebContextFactory webContextFactory;

    private final List<WebInterceptor> requestInterceptors;

    private final List<WebInterceptor> responseInterceptors;

    private final List<WebInterceptor> errorInterceptors;

    public RestControllerAndResponseBodyAdvice(WebContextFactory webContextFactory,
                                               List<WebInterceptor> interceptors) {
        this.webContextFactory = webContextFactory;
        this.requestInterceptors = interceptors.stream()
                .filter(it -> it.getType() == WebInterceptor.Type.REQUEST)
                .collect(Collectors.toList());
        this.responseInterceptors = interceptors.stream()
                .filter(it -> it.getType() == WebInterceptor.Type.RESPONSE)
                .collect(Collectors.toList());
        this.errorInterceptors = interceptors.stream()
                .filter(it -> it.getType() == WebInterceptor.Type.ERROR)
                .collect(Collectors.toList());
    }

    //@InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @ModelAttribute
    public void modelAttribute(HttpServletRequest request, HttpServletResponse response) {
        WebContext context = webContextFactory.create()
                .put(HttpServletRequest.class, request)
                .put(HttpServletResponse.class, response);
        new WebInterceptorChainImpl(0, requestInterceptors).next(context);
    }

    @ExceptionHandler({Throwable.class})
    public Object handleException(HttpServletRequest request, ServerHttpResponse response, Throwable e) {
        WebContext context = webContextFactory.create()
                .put(HttpServletRequest.class, request)
                .put(HttpServletResponse.class, response)
                .put(Throwable.class, e);
        new WebInterceptorChainImpl(0, errorInterceptors).next(context);
        return context.get(WebResult.class);
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
                .put(Method.class, returnType.getMethod())
                .put(WebContext.Key.RESPONSE_BODY, body);
        new WebInterceptorChainImpl(0, responseInterceptors).next(context);
        context.global().reset();
        return context.get(WebContext.Key.RESPONSE_BODY);
    }
}
