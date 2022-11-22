package com.github.linyuzai.concept.cloud.web.interceptor;

/**
 * 请求响应拦截器
 */
public interface WebResponseInterceptor extends WebInterceptor {

    @Override
    default Type getType() {
        return Type.RESPONSE;
    }
}
