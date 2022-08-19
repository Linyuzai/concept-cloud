package com.github.linyuzai.concept.domain.basic;

public interface DomainFacadeAdapter<T extends DomainObject, P> {

    P do2po(T object);

    T po2do(P po);
}
