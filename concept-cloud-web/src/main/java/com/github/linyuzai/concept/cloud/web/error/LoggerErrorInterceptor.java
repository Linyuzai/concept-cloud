package com.github.linyuzai.concept.cloud.web.error;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebChainInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebErrorInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

@CommonsLog
@AllArgsConstructor
public class LoggerErrorInterceptor implements WebErrorInterceptor, WebChainInterceptor {

    private Function<WebContext, String> messageProvider;

    public LoggerErrorInterceptor() {
        this(context -> {
            HttpServletRequest request = context.get(HttpServletRequest.class);
            return request.getRequestURI();
        });
    }

    @Override
    public void intercept(WebContext context) {
        Throwable e = context.get(Throwable.class);
        log.error(messageProvider.apply(context), e);
    }

    @Override
    public int getOrder() {
        return Order.LOGGER_ERROR;
    }
}
