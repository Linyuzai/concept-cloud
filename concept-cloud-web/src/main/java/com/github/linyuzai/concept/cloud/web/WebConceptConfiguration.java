package com.github.linyuzai.concept.cloud.web;

import com.github.linyuzai.concept.cloud.web.context.GlobalWebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactory;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactoryImpl;
import com.github.linyuzai.concept.cloud.web.error.ExceptionResultErrorInterceptor;
import com.github.linyuzai.concept.cloud.web.error.LoggerErrorInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainFactory;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptorChainFactoryImpl;
import com.github.linyuzai.concept.cloud.web.request.UriSkipRequestInterceptor;
import com.github.linyuzai.concept.cloud.web.response.*;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactory;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactoryAdapter;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactoryAdapterImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(WebConceptProperties.class)
public class WebConceptConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebContext globalWebContext() {
        return new GlobalWebContext();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebContextFactory webContextFactory(WebContext global) {
        WebContext.setGlobal(global);
        return new WebContextFactoryImpl();
    }

    @Configuration
    @ConditionalOnProperty(name = "concept.cloud.web.intercept.enabled", havingValue = "true", matchIfMissing = true)
    public static class InterceptConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public WebInterceptorChainFactory webInterceptorChainFactory() {
            return new WebInterceptorChainFactoryImpl();
        }

        @Configuration
        @ConditionalOnProperty(name = "concept.cloud.web.intercept.request.enabled", havingValue = "true", matchIfMissing = true)
        public static class RequestConfiguration {

            @Bean
            @ConditionalOnProperty(name = "concept.cloud.web.intercept.request.skip.urls")
            public UriSkipRequestInterceptor uriSkipRequestInterceptor(WebConceptProperties properties) {
                return new UriSkipRequestInterceptor(properties.getIntercept().getRequest().getSkip().getUrls());
            }
        }

        @Configuration
        @ConditionalOnProperty(name = "concept.cloud.web.intercept.response.enabled", havingValue = "true", matchIfMissing = true)
        public static class ResponseConfiguration {

            @Bean
            @ConditionalOnProperty(name = "concept.cloud.web.intercept.response.skip.urls")
            public UriSkipResponseInterceptor uriSkipResponseInterceptor(WebConceptProperties properties) {
                return new UriSkipResponseInterceptor(properties.getIntercept().getResponse().getSkip().getUrls());
            }

            @Bean
            public ResultMessageResponseInterceptor resultMessageResponseInterceptor() {
                return new ResultMessageResponseInterceptor();
            }

            @Bean
            public ResponseWebResultFactory responseWebResultFactory() {
                return new ResponseWebResultFactory();
            }

            @Bean
            @ConditionalOnMissingBean
            public WebResultFactoryAdapter webResultFactoryAdapter(List<WebResultFactory> webResultFactories) {
                return new WebResultFactoryAdapterImpl(webResultFactories);
            }

            /**
             * 加载响应WrapResult拦截器
             */
            @Bean
            public WrapResultResponseInterceptor wrapResultResponseInterceptor(WebResultFactoryAdapter webResultFactoryAdapter) {
                return new WrapResultResponseInterceptor(webResultFactoryAdapter);
            }

            /**
             * 加载响应String拦截器
             */
            @Bean
            public StringTypeResponseInterceptor stringTypeResponseInterceptor() {
                return new StringTypeResponseInterceptor();
            }
        }

        @Configuration
        @ConditionalOnProperty(name = "concept.cloud.web.intercept.error.enabled", havingValue = "true", matchIfMissing = true)
        public static class ErrorConfiguration {

            @Bean
            public LoggerErrorInterceptor loggerErrorInterceptor() {
                return new LoggerErrorInterceptor();
            }

            @Bean
            public ExceptionResultErrorInterceptor exceptionResultErrorInterceptor() {
                return new ExceptionResultErrorInterceptor();
            }
        }

        @Bean
        public RestControllerAndResponseBodyAdvice restControllerAndResponseBodyAdvice(WebConceptProperties properties,
                                                                                       WebContextFactory webContextFactory,
                                                                                       WebInterceptorChainFactory webInterceptorChainFactory,
                                                                                       List<WebInterceptor> interceptors) {
            return new RestControllerAndResponseBodyAdvice(properties, webContextFactory, webInterceptorChainFactory, interceptors);
        }
    }
}
