package com.github.linyuzai.concept.cloud.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

/**
 * 字符串响应拦截器
 * 若响应体为字符串则设置设置响应实体内容，且为string类型
 */
@Getter
@Setter
@AllArgsConstructor
public class StringTypeResponseInterceptor implements WebResponseInterceptor {

    public static final int ORDER_STRING_TYPE = 2000;

    private ObjectMapper objectMapper;

    public StringTypeResponseInterceptor() {
        this(new ObjectMapper());
    }

    @SneakyThrows
    @Override
    public void intercept(WebContext context) {
        Method method = context.get(Method.class);
        if (method.getReturnType() == String.class) {
            Object body = context.get(WebContext.Key.RESPONSE_BODY);
            context.put(WebContext.Key.RESPONSE_BODY, objectMapper.writeValueAsString(body));
        }
    }

    @Override
    public int getDefaultOrder() {
        return ORDER_STRING_TYPE;
    }
}
