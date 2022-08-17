package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.usage.order.DefinedOrdered;
import com.github.linyuzai.concept.cloud.web.context.WebContext;

/**
 * 拦截请求的接口，该接口继承Ordered接口，提供拦截顺序
 */
public interface WebInterceptor extends DefinedOrdered {

    /**
     * 拦截请求并做处理，根据判断是否进入下一个拦截器
     *
     * @param context 当前请求
     * @param chain   下一个请求拦截器
     */
    void intercept(WebContext context, WebInterceptorChain chain);

    Type getType();

    enum Type {

        REQUEST, RESPONSE, ERROR
    }
}
