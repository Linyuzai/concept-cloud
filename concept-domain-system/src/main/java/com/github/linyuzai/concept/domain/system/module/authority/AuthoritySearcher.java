package com.github.linyuzai.concept.domain.system.module.authority;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface AuthoritySearcher {

    Collection<? extends AuthorityVO> tree(String name);
}
