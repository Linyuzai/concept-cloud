package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import org.springframework.core.Ordered;

/**
 * 拦截请求的接口，该接口继承Ordered接口，提供拦截顺序
 */
public interface WebInterceptor extends Ordered {

    /**
     * 拦截请求并做处理，根据判断是否进入下一个拦截器
     *
     * @param context 当前请求
     * @param chain   下一个请求拦截器
     */
    void intercept(WebContext context, WebInterceptorChain chain);

    Type getType();

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    enum Type {

        REQUEST, RESPONSE, ERROR
    }

    class Order {

        //Response
        public static final int RESULT_MESSAGE = 0;

        public static final int WRAP_RESULT = 1000;

        public static final int STRING_TYPE = 2000;

        //Error
        public static final int LOGGER_ERROR = Ordered.HIGHEST_PRECEDENCE + 1000;

        public static final int EXCEPTION_RESULT = Ordered.LOWEST_PRECEDENCE - 1000;

    }
}
