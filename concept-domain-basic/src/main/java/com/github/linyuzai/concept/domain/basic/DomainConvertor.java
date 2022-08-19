package com.github.linyuzai.concept.domain.basic;

public interface DomainConvertor<T extends DomainObject, V> {

    T to(V v);

    V from(T t);
}
