package com.github.linyuzai.concept.domain.basic;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("all")
public interface DomainRepository<T extends DomainObject> {

    default void add(T... objects) {
        add(Arrays.asList(objects));
    }

    void add(Collection<? extends T> objects);

    default void update(T... objects) {
        update(Arrays.asList(objects));
    }

    void update(Collection<? extends T> objects);

    default void delete(String... uniques) {
        delete(Arrays.asList(uniques));
    }

    void delete(Collection<? extends String> uniques);

    T get(String unique);

    default Collection<? extends T> select(String... uniques) {
        return select(Arrays.asList(uniques));
    }

    Collection<? extends T> select(Collection<? extends String> uniques);

    Collection<? extends T> all();
}
