package com.github.linyuzai.concept.cloud.web.context;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebContextFactoryImpl implements WebContextFactory {

    @Override
    public WebContext create() {
        WebContext context = WebContext.getGlobal().get(WebContext.class);
        if (context == null) {
            WebContext newContext = new WebContextImpl();
            WebContext.getGlobal().put(WebContext.class, newContext);
            return newContext;
        } else {
            return context;
        }
    }
}
