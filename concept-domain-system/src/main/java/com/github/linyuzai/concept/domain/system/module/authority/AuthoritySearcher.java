package com.github.linyuzai.concept.domain.system.module.authority;

import java.util.Collection;

public interface AuthoritySearcher {

    Collection<? extends AuthorityVO> tree(String name);
}
