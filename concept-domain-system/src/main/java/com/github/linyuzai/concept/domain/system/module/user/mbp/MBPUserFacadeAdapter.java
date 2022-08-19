package com.github.linyuzai.concept.domain.system.module.user.mbp;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.linyuzai.concept.domain.system.module.user.*;
import org.springframework.stereotype.Component;

@Component
public class MBPUserFacadeAdapter implements UserFacadeAdapter<UserPO> {

    @Override
    public UserPO do2po(User user) {
        return new UserPO(user);
    }

    @Override
    public User po2do(UserPO po) {
        if (po == null) {
            return null;
        }
        return new User.Builder()
                //.account()
                //.information()
                .build();
    }

    @Override
    public String generateId() {
        return IdWorker.getIdStr();
    }

    @Override
    public User create(UserCreateCommand command) {
        return null;
    }

    @Override
    public User update(UserUpdateCommand command) {
        return null;
    }

    @Override
    public UserVO do2vo(User user) {
        return new UserVO(user);
    }

    @Override
    public UserVO po2vo(UserPO po) {
        return null;
    }
}
