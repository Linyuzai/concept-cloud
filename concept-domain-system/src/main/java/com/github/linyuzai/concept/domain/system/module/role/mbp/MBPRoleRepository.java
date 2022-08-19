package com.github.linyuzai.concept.domain.system.module.role.mbp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.linyuzai.concept.domain.basic.mbp.MBPDomainRepository;
import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import com.github.linyuzai.concept.domain.system.module.role.Role;
import com.github.linyuzai.concept.domain.system.module.role.RoleFacadeAdapter;
import com.github.linyuzai.concept.domain.system.module.role.RoleRepository;
import com.github.linyuzai.concept.domain.system.module.role.RoleSearcher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Repository
public class MBPRoleRepository extends MBPDomainRepository<Role, RolePO> implements RoleRepository, RoleSearcher {

    @Autowired
    protected RoleMapper baseMapper;

    @Autowired
    protected RoleFacadeAdapter<RolePO> facadeAdapter;

    @Autowired
    protected RoleAuthorityMapper roleAuthorityMapper;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void add(Collection<? extends Role> roles) {
        super.add(roles);
        deleteRoleAuthority(roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet()));
        addRoleAuthority(roles);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(Collection<? extends Role> roles) {
        super.update(roles);
        deleteRoleAuthority(roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet()));
        addRoleAuthority(roles);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Collection<? extends String> roleIds) {
        super.delete(roleIds);
        deleteRoleAuthority(roleIds);
    }

    protected void deleteRoleAuthority(Collection<? extends String> roleIds) {
        roleAuthorityMapper.delete(Wrappers.<RoleAuthorityPO>lambdaQuery()
                .in(RoleAuthorityPO::getRoleId, roleIds));
    }

    protected void addRoleAuthority(Collection<? extends Role> roles) {
        List<RoleAuthorityPO> roleAuthorities = new ArrayList<>();
        for (Role role : roles) {
            Collection<? extends Authority> authorities = role.getAuthorities();
            for (Authority authority : authorities) {
                roleAuthorities.add(new RoleAuthorityPO(role.getId(), authority.getKey()));
            }
        }
        roleAuthorities.forEach(roleAuthorityMapper::insert);
    }
}
