package com.github.linyuzai.concept.domain.basic;

public interface DomainBuilder<T extends DomainObject> {

    T build();
}
