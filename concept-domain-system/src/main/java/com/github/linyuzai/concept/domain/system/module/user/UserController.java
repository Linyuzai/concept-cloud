package com.github.linyuzai.concept.domain.system.module.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserSearcher userSearcher;

    @Autowired
    protected UserFacadeAdapter<?> userFacadeAdapter;

    @PostMapping
    public Collection<UserVO> create(@RequestBody List<UserCreateCommand> commands) {
        Collection<User> users = userService.create(commands);
        return users.stream()
                .map(userFacadeAdapter::do2vo)
                .collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody List<UserUpdateCommand> commands) {
        userService.update(commands);
    }

    @DeleteMapping
    public void delete() {

    }


}
