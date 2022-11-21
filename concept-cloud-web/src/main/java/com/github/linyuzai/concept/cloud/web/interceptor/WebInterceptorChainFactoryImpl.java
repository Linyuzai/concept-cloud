package com.github.linyuzai.concept.cloud.web.interceptor;

import java.util.List;

public class WebInterceptorChainFactoryImpl implements WebInterceptorChainFactory {

    @Override
    public WebInterceptorChain create(List<WebInterceptor> interceptors) {
        return new WebInterceptorChainImpl(0, interceptors);
    }
}
