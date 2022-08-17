package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;

/**
 * 请求拦截链接口
 */
public interface WebInterceptorChain {

    /**
     * 获取下一个拦截器并调用
     */
    void next(WebContext context);
}
