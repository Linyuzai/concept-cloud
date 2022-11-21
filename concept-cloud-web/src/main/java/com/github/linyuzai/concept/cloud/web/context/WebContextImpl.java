package com.github.linyuzai.concept.cloud.web.context;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class WebContextImpl implements WebContext {

    private final Map<Object, Object> context = new LinkedHashMap<>();

    public WebContextImpl() {
        WebContext c = global().get(WebContext.class);
        if (c != null) {
            context.putAll(c.all());
        }
        global().put(WebContext.class, this);
    }

    @Override
    public WebContext put(Object key, Object value) {
        context.put(key, value);
        return this;
    }

    @Override
    public <V> V get(Object key) {
        return (V) context.get(key);
    }

    @Override
    public <V> V get(Object key, V defaultValue) {
        return (V) context.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<Object, Object> all() {
        return context;
    }

    @Override
    public void reset() {
        context.clear();
    }
}
