package com.github.linyuzai.concept.domain.system.module.authority;

import com.github.linyuzai.concept.domain.basic.AbstractDomainBuilder;
import com.github.linyuzai.concept.domain.basic.DomainValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class Authority implements DomainValue {

    protected String key;

    protected String name;

    protected String pattern;

    protected Collection<Authority> children;

    protected Map<String, Authority> childMap;

    public boolean has(String key) {
        return get(key) != null;
    }

    public Authority get(String key) {
        return get(key, this);
    }

    protected Authority get(String key, Authority authority) {
        if (authority.getKey().equals(key)) {
            return authority;
        }
        for (Authority value : authority.getChildMap().values()) {
            Authority get = get(key, value);
            if (get == null) {
                continue;
            }
            return get;
        }
        return null;
    }

    public void addChild(Authority authority) {
        if (childMap.containsKey(authority.getKey())) {
            throw new IllegalArgumentException("Key duplicated");
        }
        children.add(authority);
        childMap.put(authority.getKey(), authority);
    }

    public void removeChild(Authority authority) {
        removeChild(authority.getKey());
    }

    public void removeChild(String authorityKey) {
        children.removeIf(authority -> authority.getKey().equals(authorityKey));
        childMap.remove(authorityKey);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public Authority search(String name) {
        return search(this, authority -> authority.getName().contains(name));
    }

    public Authority search(Predicate<Authority> predicate) {
        return search(this, predicate);
    }

    protected Authority search(Authority authority, Predicate<Authority> predicate) {
        Authority build = new Authority.Builder()
                .key(authority.getKey())
                .name(authority.getName())
                .pattern(authority.getPattern())
                .children(new ArrayList<>())
                .build();
        for (Authority child : authority.getChildren()) {
            Authority searched = search(child, predicate);
            if (searched != null) {
                build.addChild(searched);
            }
        }
        if (build.hasChildren() || predicate.test(build)) {
            return build;
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Authority) {
            return key.equals(((Authority) obj).getKey());
        } else {
            return false;
        }
    }

    public static class Builder extends AbstractDomainBuilder<Authority> {

        protected String key;

        protected String name;

        protected String pattern;

        protected Collection<? extends Authority> children;

        protected Map<String, ? extends Authority> childMap;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder children(Collection<? extends Authority> children) {
            this.children = children;
            return this;
        }

        @Override
        public void valid() {
            if (this.children == null) {
                this.children = Collections.emptyList();
            }
            childMap = children.stream()
                    .collect(Collectors.toMap(Authority::getKey, Function.identity()));
            super.valid();
        }
    }
}
