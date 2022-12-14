package com.github.linyuzai.concept.cloud.web.interceptor;

/**
 * 拦截请求的接口，该接口继承Ordered接口，提供拦截顺序
 */
public interface WebRequestInterceptor extends WebInterceptor {

    @Override
    default Type getType() {
        return Type.REQUEST;
    }
}
