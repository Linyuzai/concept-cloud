package com.github.linyuzai.concept.cloud.web.error;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebErrorInterceptor;

public class ExceptionResultErrorInterceptor implements WebErrorInterceptor {

    @Override
    public void intercept(WebContext context) {
        Throwable e = context.get(Throwable.class);
        context.put(WebContext.Key.RESULT_SUCCESS, false);
        context.put(WebContext.Key.RESULT_MESSAGE, e.getMessage());
    }

    @Override
    public int getOrder() {
        return Order.EXCEPTION_RESULT;
    }
}
