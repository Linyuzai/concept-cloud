package com.github.linyuzai.concept.cloud.web.result;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import org.springframework.core.Ordered;

public interface WebResultFactory extends Ordered {

    boolean support(WebContext context);

    Object create(WebContext context);

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
