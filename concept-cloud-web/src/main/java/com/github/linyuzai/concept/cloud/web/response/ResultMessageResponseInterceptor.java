package com.github.linyuzai.concept.cloud.web.response;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;
import com.github.linyuzai.concept.cloud.web.result.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

@Getter
@Setter
@AllArgsConstructor
public class ResultMessageResponseInterceptor implements WebResponseInterceptor {

    @SneakyThrows
    @Override
    public void intercept(WebContext context) {
        MethodParameter parameter = context.get(MethodParameter.class);
        Method method = parameter.getMethod();
        if (method == null) {
            return;
        }
        ResultMessage annotation = method.getAnnotation(ResultMessage.class);
        if (annotation == null) {
            return;
        }
        boolean success = context.get(WebContext.Key.RESULT_SUCCESS, true);
        String message = success ? annotation.success() : annotation.failure();
        if (message.isEmpty()) {
            return;
        }
        context.put(WebContext.Key.RESULT_MESSAGE, success);
    }

    @Override
    public int getOrder() {
        return Order.RESULT_MESSAGE;
    }
}
