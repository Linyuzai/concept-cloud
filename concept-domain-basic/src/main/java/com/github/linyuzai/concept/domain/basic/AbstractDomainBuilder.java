package com.github.linyuzai.concept.domain.basic;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractDomainBuilder<T extends DomainObject> implements DomainBuilder<T> {

    public T build() {
        valid();
        return create();
    }

    public void valid() {

    }

    @SneakyThrows
    public T create() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) superclass).getActualTypeArguments();
            if (arguments.length == 1) {
                Type type = arguments[0];
                if (type instanceof Class) {
                    @SuppressWarnings("unchecked")
                    Constructor<T> constructor = ((Class<T>) type).getConstructor();
                    if (!constructor.isAccessible()) {
                        constructor.setAccessible(true);
                    }
                    return constructor.newInstance();
                }
            }
        }
        throw new ReflectiveOperationException("Generic type get error: " + superclass);
    }
}
