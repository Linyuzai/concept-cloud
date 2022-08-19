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

    protected Collection<? extends Role> roles;

    public enum Status {

    }
}
