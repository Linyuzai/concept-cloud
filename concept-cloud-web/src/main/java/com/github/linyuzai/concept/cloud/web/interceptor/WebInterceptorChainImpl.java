package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 拦截器链
 */
@Getter
@AllArgsConstructor
public class WebInterceptorChainImpl implements WebInterceptorChain {

    /**
     * 当前拦截器的拦截顺序
     */
    private int index;

    /**
     * 容器中的拦截器列表
     */
    private final List<WebInterceptor> interceptors;

    /**
     * 调用下个顺序的拦截器
     */
    @Override
    public void next(WebContext context) {
        if (index < interceptors.size()) {
            WebInterceptor interceptor = interceptors.get(index++);
            interceptor.intercept(context, this);
        }
    }
}
