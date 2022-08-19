package com.github.linyuzai.concept.domain.system.module.authority;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthorityVO {

    protected String key;

    protected String name;

    protected String pattern;

    protected Collection<AuthorityVO> children;
}
