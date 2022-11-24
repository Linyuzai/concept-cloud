package com.github.linyuzai.concept.cloud.web;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactory;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChain;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainFactory;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAndResponseBodyAdvice implements ResponseBodyAdvice<Object>, WebMvcConfigurer {

    private final WebConceptProperties properties;

    private final WebContextFactory contextFactory;

    private final WebInterceptorChainFactory chainFactory;

    private final List<WebInterceptor> requestInterceptors;

    private final List<WebInterceptor> responseInterceptors;

    private final List<WebInterceptor> errorInterceptors;

    public RestControllerAndResponseBodyAdvice(WebConceptProperties properties,
                                               WebContextFactory contextFactory,
                                               WebInterceptorChainFactory chainFactory,
                                               List<WebInterceptor> interceptors) {
        Map<WebInterceptor.Type, List<WebInterceptor>> interceptorsMap = interceptors.stream()
                .collect(Collectors.groupingBy(WebInterceptor::getType, Collectors.toList()));
        this.requestInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.REQUEST, new ArrayList<>());
        this.responseInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.RESPONSE, new ArrayList<>());
        this.errorInterceptors = interceptorsMap.getOrDefault(WebInterceptor.Type.ERROR, new ArrayList<>());
        this.properties = properties;
        this.contextFactory = contextFactory;
        this.chainFactory = chainFactory;
    }

    //@InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @ModelAttribute
    public void modelAttribute(HttpServletRequest request, HttpServletResponse response) {
        if (properties.getIntercept().getRequest().isEnabled()) {
            WebContext context = getOrCreateContext()
                    .put(HttpServletRequest.class, request)
                    .put(HttpServletResponse.class, response);
            WebInterceptorChain chain = chainFactory.create(requestInterceptors);
            chain.next(context);
        }
    }

    @ExceptionHandler({Throwable.class})
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        if (properties.getIntercept().getError().isEnabled()) {
            WebContext context = getOrCreateContext()
                    .put(HttpServletRequest.class, request)
                    .put(HttpServletResponse.class, response)
                    .put(Throwable.class, e);
            WebInterceptorChain chain = chainFactory.create(errorInterceptors);
            chain.next(context);
            return context.get(WebContext.Key.RESPONSE_BODY, e);
        } else {
            return e;
        }
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
        Object result;
        if (properties.getIntercept().getResponse().isEnabled()) {
            WebContext context = getOrCreateContext()
                    .put(ServerHttpRequest.class, request)
                    .put(ServerHttpResponse.class, response)
                    .put(MethodParameter.class, returnType)
                    .put(MediaType.class, selectedContentType)
                    .put(HttpMessageConverter.class, selectedConverterType)
                    .put(WebContext.Key.RESPONSE_BODY, body);
            WebInterceptorChain chain = chainFactory.create(responseInterceptors);
            chain.next(context);
            result = context.get(WebContext.Key.RESPONSE_BODY);
        } else {
            result = body;
        }
        WebContext.getGlobal().reset();
        return result;
    }

    protected WebContext getOrCreateContext() {
        WebContext context = WebContext.getGlobal().get(WebContext.class);
        if (context == null) {
            WebContext newContext = contextFactory.create();
            WebContext.getGlobal().put(WebContext.class, newContext);
            return newContext;
        } else {
            return context;
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerMethodHandlerInterceptor());
    }

    public class HandlerMethodHandlerInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull Object handler) throws Exception {
            if (handler instanceof HandlerMethod && (
                    properties.getIntercept().getRequest().isEnabled() ||
                            properties.getIntercept().getResponse().isEnabled() ||
                            properties.getIntercept().getError().isEnabled())) {
                getOrCreateContext().put(HandlerMethod.class, handler);
            }
            return true;
        }
    }
}
