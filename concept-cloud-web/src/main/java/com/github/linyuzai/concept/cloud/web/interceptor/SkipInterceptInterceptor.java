package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;

public abstract class SkipInterceptInterceptor implements WebInterceptor {

    @Override
    public void intercept(WebContext context, WebInterceptorChain chain) {
        if (shouldSkip(context)) {
            return;
        }
        chain.next(context);
    }

    @Override
    public int getOrder() {
        return Order.SKIP;
    }

    public abstract boolean shouldSkip(WebContext context);
}
