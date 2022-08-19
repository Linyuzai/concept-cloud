package com.github.linyuzai.concept.domain.system.module.authority;

import com.github.linyuzai.concept.domain.basic.AbstractDomainBuilder;
import com.github.linyuzai.concept.domain.basic.DomainValue;
import lombok.Getter;

@Getter
public class Authority implements DomainValue {

    protected String key;

    protected String name;

    protected String pattern;

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
    }
}
