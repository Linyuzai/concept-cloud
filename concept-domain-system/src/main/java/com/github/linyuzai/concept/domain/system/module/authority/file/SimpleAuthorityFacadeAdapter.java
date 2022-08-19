package com.github.linyuzai.concept.domain.system.module.authority.file;

import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import com.github.linyuzai.concept.domain.system.module.authority.AuthorityFacadeAdapter;
import com.github.linyuzai.concept.domain.system.module.authority.AuthorityVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SimpleAuthorityFacadeAdapter implements AuthorityFacadeAdapter<AuthorityPO> {

    @Override
    public AuthorityPO do2po(Authority authority) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Authority po2do(AuthorityPO po) {
        return new Authority.Builder()
                .key(po.getKey())
                .name(po.getName())
                .pattern(po.getPattern())
                .build();
    }

    @Override
    public AuthorityVO po2vo(AuthorityPO po) {
        AuthorityVO vo = new AuthorityVO();
        vo.setKey(po.getKey());
        vo.setName(po.getName());
        vo.setPattern(po.getPattern());
        vo.setChildren(new ArrayList<>());
        for (AuthorityPO child : po.getChildren()) {
            vo.getChildren().add(po2vo(child));
        }
        return vo;
    }
}
