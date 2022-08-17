package com.github.linyuzai.concept.cloud.web.response;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;
import com.github.linyuzai.concept.cloud.web.result.WebResult;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactory;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactoryAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求响应拦截器
 * 将响应体包装为WebResult格式
 */
@Getter
@Setter
@AllArgsConstructor
public class WrapResultResponseInterceptor implements WebResponseInterceptor {

    public static final int ORDER_WRAP_RESULT = 1000;

    private WebResultFactoryAdapter webResultFactoryAdapter;

    @Override
    public void intercept(WebContext context) {
        Object body = context.get(WebContext.Key.RESPONSE_BODY);
        if (body instanceof WebResult) {
            return;
        }
        WebResultFactory factory = webResultFactoryAdapter.getWebResultFactory(context);
        WebResult webResult = factory.create(context);
        context.put(WebContext.Key.RESPONSE_BODY, webResult);
    }

    @Override
    public int getDefaultOrder() {
        return ORDER_WRAP_RESULT;
    }
}
