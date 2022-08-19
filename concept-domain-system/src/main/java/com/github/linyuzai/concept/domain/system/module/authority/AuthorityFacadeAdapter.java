package com.github.linyuzai.concept.domain.system.module.authority;

import com.github.linyuzai.concept.domain.basic.DomainFacadeAdapter;

public interface AuthorityFacadeAdapter<P> extends DomainFacadeAdapter<Authority, P> {

    AuthorityVO po2vo(P po);
}
