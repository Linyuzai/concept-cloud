package com.github.linyuzai.concept.domain.system.module.account;

import com.github.linyuzai.concept.domain.basic.DomainEntity;
import com.github.linyuzai.concept.domain.system.module.role.Role;
import lombok.Getter;

import java.util.Collection;

@Getter
public class Account implements DomainEntity {

    protected String id;

    protected String username;

    protected String password;

    protected String nickname;

    protected String avatar;

    protected Status status;

    protected Collection<Role> roles;

    public Collection<? extends Role> getRoles() {
        return roles;
    }

    public boolean hasAuthority(String key) {
        for (Role role : roles) {
            if (role.hasAuthority(key)) {
                return true;
            }
        }
        return false;
    }

    public enum Status {

    }
}
