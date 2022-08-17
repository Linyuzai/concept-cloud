package com.github.linyuzai.concept.cloud.web.result;

import com.github.linyuzai.concept.cloud.web.context.WebContext;

public interface WebResultFactoryAdapter {

    WebResultFactory getWebResultFactory(WebContext context);
}
