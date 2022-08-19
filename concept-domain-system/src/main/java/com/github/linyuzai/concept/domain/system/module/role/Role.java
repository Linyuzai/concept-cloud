package com.github.linyuzai.concept.domain.system.module.role;

import com.github.linyuzai.concept.domain.basic.AbstractDomainBuilder;
import com.github.linyuzai.concept.domain.basic.DomainEntity;
import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role implements DomainEntity {

    protected String id;

    protected String name;

    protected Collection<? extends Authority> authorities;

    public static class Builder extends AbstractDomainBuilder<Role> {

        protected String id;

        protected String name;

        protected Collection<? extends Authority> authorities;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder authorities(Collection<? extends Authority> authorities) {
            this.authorities = authorities;
            return this;
        }
    }
}
