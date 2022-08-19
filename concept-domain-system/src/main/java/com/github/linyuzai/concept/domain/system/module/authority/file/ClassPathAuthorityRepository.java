package com.github.linyuzai.concept.domain.system.module.authority.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.linyuzai.concept.domain.system.module.authority.Authority;
import com.github.linyuzai.concept.domain.system.module.authority.AuthorityRepository;
import com.github.linyuzai.concept.domain.system.module.authority.AuthoritySearcher;
import com.github.linyuzai.concept.domain.system.module.authority.AuthorityVO;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ClassPathAuthorityRepository implements AuthorityRepository, AuthoritySearcher {

    private final Authority authority;

    public ClassPathAuthorityRepository(String path) {
        this(path, new ObjectMapper());
    }

    @SneakyThrows
    public ClassPathAuthorityRepository(String path, ObjectMapper objectMapper) {
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStream is = resource.getInputStream()) {
            AuthorityPO read = objectMapper.readValue(is, AuthorityPO.class);
            authority = read.toAuthority();
        }
    }

    @Override
    public void add(Collection<? extends Authority> objects) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Collection<? extends Authority> objects) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Collection<? extends String> uniques) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Authority get(String unique) {
        return authority.get(unique);
    }

    @Override
    public Collection<? extends Authority> select(Collection<? extends String> uniques) {
        List<Authority> authorities = new ArrayList<>();
        for (String unique : uniques) {
            Authority get = authority.get(unique);
            if (get == null) {
                continue;
            }
            authorities.add(get);
        }
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public Collection<? extends Authority> all() {
        return Collections.unmodifiableCollection(authority.getChildren());
    }

    @Override
    public Collection<? extends AuthorityVO> tree(String name) {
        return AuthorityVO.from(authority.search(name)).getChildren();
    }
}
