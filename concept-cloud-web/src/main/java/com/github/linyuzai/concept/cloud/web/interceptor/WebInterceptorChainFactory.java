package com.github.linyuzai.concept.cloud.web.interceptor;

import java.util.List;

public interface WebInterceptorChainFactory {

    WebInterceptorChain create(List<WebInterceptor> interceptors);
}
