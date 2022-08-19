package com.github.linyuzai.concept.domain.system.module.authority;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class AuthorityVO {

    protected String key;

    protected String name;

    protected String pattern;

    protected Collection<AuthorityVO> children;

    public static AuthorityVO from(Authority authority) {
        AuthorityVO vo = new AuthorityVO();
        vo.setKey(authority.getKey());
        vo.setName(authority.getName());
        vo.setPattern(authority.getPattern());
        vo.setChildren(new ArrayList<>());
        for (Authority child : authority.getChildren()) {
            vo.getChildren().add(from(child));
        }
        return vo;
    }
}
