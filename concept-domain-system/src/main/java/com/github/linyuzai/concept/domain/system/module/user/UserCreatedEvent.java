package com.github.linyuzai.concept.domain.system.module.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class UserCreatedEvent {

    private Collection<User> users;
}
