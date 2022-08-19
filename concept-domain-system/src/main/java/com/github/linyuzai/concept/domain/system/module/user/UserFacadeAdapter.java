package com.github.linyuzai.concept.domain.system.module.user;

import com.github.linyuzai.concept.domain.basic.DomainFacadeAdapter;

public interface UserFacadeAdapter<P> extends DomainFacadeAdapter<User, P> {

    String generateId();

    User create(UserCreateCommand command);

    User update(UserUpdateCommand command);

    UserVO do2vo(User user);

    UserVO po2vo(P po);
}
