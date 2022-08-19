package com.github.linyuzai.concept.domain.system.module.authority.file;

import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import lombok.Getter;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class AuthorityPO {

    protected String key;

    protected String name;

    protected String pattern;

    protected Collection<AuthorityPO> children;

    public Authority toAuthority() {
        return new Authority.Builder()
                .key(key)
                .name(name)
                .pattern(pattern)
                .children(children.stream()
                        .map(AuthorityPO::toAuthority)
                        .collect(Collectors.toList()))
                .build();
    }
}
