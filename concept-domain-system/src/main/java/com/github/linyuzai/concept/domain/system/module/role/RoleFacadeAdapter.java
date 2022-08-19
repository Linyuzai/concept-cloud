package com.github.linyuzai.concept.domain.system.module.role;

import com.github.linyuzai.concept.domain.basic.DomainFacadeAdapter;

public interface RoleFacadeAdapter<P> extends DomainFacadeAdapter<Role, P> {

    String generateId();

    Role create(RoleCreateCommand command);

    Role update(RoleUpdateCommand command);

    RoleVO do2vo(Role role);

    RoleVO po2vo(P po);
}
