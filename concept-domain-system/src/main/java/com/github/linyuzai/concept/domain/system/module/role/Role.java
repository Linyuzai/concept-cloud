package com.github.linyuzai.concept.domain.system.module.role;

import com.github.linyuzai.concept.domain.basic.AbstractDomainBuilder;
import com.github.linyuzai.concept.domain.basic.DomainEntity;
import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role implements DomainEntity {

    protected String id;

    protected String name;

    protected Collection<Authority> authorities;

    public Collection<? extends Authority> getAuthorities() {
        return authorities;
    }

    public boolean hasAuthority(String key) {
        Map<String, Authority> authorityMap = authorities.stream()
                .collect(Collectors.toMap(Authority::getKey, Function.identity()));
        return authorityMap.containsKey(key);
    }

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
