package com.github.linyuzai.concept.cloud.web.response;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;
import com.github.linyuzai.concept.cloud.web.result.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

@Getter
@Setter
@AllArgsConstructor
public class ResultMessageResponseInterceptor implements WebResponseInterceptor {

    public static final int ORDER_RESULT_MESSAGE = 0;

    @SneakyThrows
    @Override
    public void intercept(WebContext context) {
        Method method = context.get(Method.class);
        ResultMessage annotation = method.getAnnotation(ResultMessage.class);
        if (annotation == null) {
            return;
        }
        String success = annotation.success();
        context.put(WebContext.Key.RESULT_MESSAGE, success);
    }

    @Override
    public int getDefaultOrder() {
        return ORDER_RESULT_MESSAGE;
    }
}
