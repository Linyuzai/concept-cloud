package com.github.linyuzai.concept.cloud.web.context;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebContextFactoryImpl implements WebContextFactory {

    @Override
    public WebContext create() {
        return new WebContextImpl();
    }
}
