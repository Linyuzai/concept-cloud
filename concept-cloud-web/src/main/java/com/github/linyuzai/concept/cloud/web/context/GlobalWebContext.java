package com.github.linyuzai.concept.cloud.web.context;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalWebContext implements WebContext {

    //TODO TransmittableThreadLocal
    private static final ThreadLocal<Map<Object, Object>> CONTEXT = new InheritableThreadLocal<Map<Object, Object>>() {

        @Override
        protected Map<Object, Object> initialValue() {
            return new LinkedHashMap<>();
        }
    };

    @Override
    public WebContext put(Object key, Object value) {
        CONTEXT.get().put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(Object key) {
        return (V) CONTEXT.get().get(key);
    }

    @Override
    public Map<Object, Object> all() {
        return CONTEXT.get();
    }

    @Override
    public void reset() {
        CONTEXT.remove();
    }
}
