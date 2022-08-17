package com.github.linyuzai.concept.cloud.web.error;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebErrorInterceptor;
import org.springframework.core.Ordered;

public class ExceptionResultErrorInterceptor implements WebErrorInterceptor {

    public static final int ORDER_EXCEPTION_RESULT = Ordered.LOWEST_PRECEDENCE - 1000;

    @Override
    public void intercept(WebContext context) {
        Throwable e = context.get(Throwable.class);
        context.put(WebContext.Key.RESPONSE_BODY, e);
        context.put(WebContext.Key.RESULT_SUCCESS, false);
        context.put(WebContext.Key.RESULT_MESSAGE, e.getMessage());
    }

    @Override
    public int getDefaultOrder() {
        return ORDER_EXCEPTION_RESULT;
    }
}
