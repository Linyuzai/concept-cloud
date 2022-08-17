package com.github.linyuzai.concept.cloud.web.context;

import java.util.Map;

public interface WebContext {

    static void setGlobal(WebContext context) {
        Global.set(context);
    }

    static WebContext getGlobal() {
        return Global.get();
    }

    default WebContext global() {
        return WebContext.getGlobal();
    }

    WebContext put(Object key, Object value);

    <V> V get(Object key);

    Map<Object, Object> all();

    void reset();

    class Global {

        private static WebContext global;

        public static WebContext get() {
            return global;
        }

        public static void set(WebContext context) {
            global = context;
        }
    }

    enum Key {

        RESPONSE_BODY,

        RESULT_SUCCESS,

        RESULT_CODE,

        RESULT_MESSAGE
    }
}
