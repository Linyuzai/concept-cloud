package com.github.linyuzai.concept.cloud.web.response;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import com.github.linyuzai.concept.cloud.web.result.WebResult;
import com.github.linyuzai.concept.cloud.web.result.WebResultFactory;

public class ResponseWebResultFactory implements WebResultFactory {

    @Override
    public boolean support(WebContext context) {
        return true;
    }

    @Override
    public Object create(WebContext context) {
        Object body = context.get(WebContext.Key.RESPONSE_BODY);
        boolean success = context.get(WebContext.Key.RESULT_SUCCESS, true);
        String code = context.get(WebContext.Key.RESULT_CODE);
        String message = context.get(WebContext.Key.RESULT_MESSAGE);
        return WebResult.builder()
                .success(success)
                .code(code)
                .message(message)
                .object(body)
                .build();
    }
}
