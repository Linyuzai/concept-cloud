package com.github.linyuzai.concept.cloud.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebChainInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * 字符串响应拦截器
 * 若响应体为字符串则设置设置响应实体内容，且为string类型
 */
@Getter
@Setter
@AllArgsConstructor
public class StringTypeResponseInterceptor implements WebResponseInterceptor, WebChainInterceptor {

    private ObjectMapper objectMapper;

    public StringTypeResponseInterceptor() {
        this(new ObjectMapper());
    }

    @SneakyThrows
    @Override
    public void intercept(WebContext context) {
        MethodParameter parameter = context.get(MethodParameter.class);
        Method method = parameter.getMethod();
        if (method == null) {
            return;
        }
        if (method.getReturnType() == String.class) {
            Object body = context.get(WebContext.Key.RESPONSE_BODY);
            if (body == null || body instanceof String) {
                return;
            }
            context.put(WebContext.Key.RESPONSE_BODY, objectMapper.writeValueAsString(body));
        }
    }

    @Override
    public int getOrder() {
        return Order.STRING_TYPE;
    }
}
