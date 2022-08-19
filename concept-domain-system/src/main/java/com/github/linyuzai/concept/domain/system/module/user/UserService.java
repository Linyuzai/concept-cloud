package com.github.linyuzai.concept.domain.system.module.user;

import com.github.linyuzai.concept.domain.basic.AbstractDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractDomainService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserFacadeAdapter<?> userFacadeAdapter;

    public Collection<User> create(List<UserCreateCommand> commands) {
        List<User> users = commands.stream()
                .map(userFacadeAdapter::create)
                .collect(Collectors.toList());
        userRepository.add(users);
        publish(new UserCreatedEvent(users));
        return users;
    }

    public void update(List<UserUpdateCommand> commands) {

    }
}
