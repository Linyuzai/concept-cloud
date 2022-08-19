package com.github.linyuzai.concept.domain.system.module.role.mbp;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.linyuzai.concept.domain.system.module.authority.AuthorityRepository;
import com.github.linyuzai.concept.domain.system.module.role.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MBPRoleFacadeAdapter<P> implements RoleFacadeAdapter<RolePO> {

    @Autowired
    protected RoleAuthorityMapper roleAuthorityMapper;

    @Autowired
    protected AuthorityRepository authorityRepository;

    @Override
    public RolePO do2po(Role object) {
        return null;
    }

    @Override
    public Role po2do(RolePO po) {
        Set<String> authorityKeys = roleAuthorityMapper.selectList(Wrappers.<RoleAuthorityPO>lambdaQuery()
                        .eq(RoleAuthorityPO::getRoleId, po.getId()))
                .stream()
                .map(RoleAuthorityPO::getAuthorityKey)
                .collect(Collectors.toSet());
        return new Role.Builder()
                .id(po.getId())
                .name(po.getName())
                .authorities(authorityRepository.select(authorityKeys))
                .build();
    }

    @Override
    public String generateId() {
        return IdWorker.getIdStr();
    }

    @Override
    public Role create(RoleCreateCommand command) {
        return null;
    }

    @Override
    public Role update(RoleUpdateCommand command) {
        return null;
    }

    @Override
    public RoleVO do2vo(Role role) {
        return null;
    }

    @Override
    public RoleVO po2vo(RolePO po) {
        return null;
    }
}
