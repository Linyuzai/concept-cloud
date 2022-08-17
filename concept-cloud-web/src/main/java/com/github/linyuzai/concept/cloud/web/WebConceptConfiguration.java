package com.github.linyuzai.concept.cloud.web;

import com.github.linyuzai.concept.cloud.web.context.GlobalWebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactory;
import com.github.linyuzai.concept.cloud.web.context.WebContextFactoryImpl;
import com.github.linyuzai.concept.cloud.web.error.ExceptionResultErrorInterceptor;
import com.github.linyuzai.concept.cloud.web.error.LoggerErrorInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebInterceptor;
import com.github.linyuzai.concept.cloud.web.response.ResponseWebResultFactory;
import com.github.linyuzai.concept.cloud.web.response.StringTypeResponseInterceptor;
import com.github.linyuzai.concept.cloud.web.response.WrapResultResponseInterceptor;
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
    //@ConditionalOnProperty
    public WrapResultResponseInterceptor wrapResultResponseInterceptor(WebResultFactoryAdapter webResultFactoryAdapter) {
        return new WrapResultResponseInterceptor(webResultFactoryAdapter);
    }

    /**
     * 加载响应String拦截器
     */
    @Bean
    //@ConditionalOnProperty
    public StringTypeResponseInterceptor stringTypeResponseInterceptor() {
        return new StringTypeResponseInterceptor();
    }

    @Bean
    //@ConditionalOnProperty
    public LoggerErrorInterceptor loggerErrorInterceptor() {
        return new LoggerErrorInterceptor();
    }

    @Bean
    //@ConditionalOnProperty
    public ExceptionResultErrorInterceptor exceptionResultErrorInterceptor() {
        return new ExceptionResultErrorInterceptor();
    }

    @Bean
    public RestControllerAndResponseBodyAdvice restControllerAndResponseBodyAdvice(WebContextFactory webContextFactory,
                                                                                   List<WebInterceptor> interceptors) {
        return new RestControllerAndResponseBodyAdvice(webContextFactory, interceptors);
    }
}
