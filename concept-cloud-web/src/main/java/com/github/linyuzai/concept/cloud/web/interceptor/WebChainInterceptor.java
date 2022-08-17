package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;

public interface WebChainInterceptor extends WebInterceptor {

    @Override
    default void intercept(WebContext context, WebInterceptorChain chain) {
        intercept(context);
        chain.next(context);
    }

    void intercept(WebContext context);
}
