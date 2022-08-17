package com.github.linyuzai.concept.cloud.web.result;

import com.github.linyuzai.concept.cloud.usage.order.DefinedOrdered;
import com.github.linyuzai.concept.cloud.web.context.WebContext;

public interface WebResultFactory extends DefinedOrdered {

    boolean support(WebContext context);

    WebResult create(WebContext context);
}
