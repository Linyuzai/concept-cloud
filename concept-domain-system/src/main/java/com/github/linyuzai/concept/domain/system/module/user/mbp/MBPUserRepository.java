package com.github.linyuzai.concept.domain.system.module.user.mbp;

import com.github.linyuzai.concept.domain.basic.mbp.MBPDomainRepository;
import com.github.linyuzai.concept.domain.system.module.user.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class MBPUserRepository extends MBPDomainRepository<User, UserPO> implements UserRepository, UserSearcher {

    @Autowired
    protected UserMapper baseMapper;

    @Autowired
    protected UserFacadeAdapter<UserPO> facadeAdapter;
}
