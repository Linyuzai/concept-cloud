package com.github.linyuzai.concept.cloud.web.context;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GlobalWebContext implements WebContext {

    //TODO Use InheritableThreadLocal or TransmittableThreadLocal if necessity
    private static final ThreadLocal<Map<Object, Object>> CONTEXT = ThreadLocal.withInitial(LinkedHashMap::new);

    @Override
    public WebContext put(Object key, Object value) {
        CONTEXT.get().put(key, value);
        return this;
    }

    @Override
    public <V> V get(Object key) {
        return (V) CONTEXT.get().get(key);
    }

    @Override
    public <V> V get(Object key, V defaultValue) {
        return (V) CONTEXT.get().getOrDefault(key, defaultValue);
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
