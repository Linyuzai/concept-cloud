package com.github.linyuzai.concept.domain.system.module.authority.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.linyuzai.concept.domain.system.module.authority.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Repository
public class ClassPathAuthorityRepository implements AuthorityRepository, AuthoritySearcher {

    @Autowired
    protected AuthorityFacadeAdapter<AuthorityPO> facadeAdapter;

    protected final String path;

    protected ObjectMapper objectMapper;

    protected AuthorityPO tree;

    protected Map<String, Authority> authorities;

    @SneakyThrows
    @PostConstruct
    public void init() {
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStream is = resource.getInputStream()) {
            tree = objectMapper.readValue(is, AuthorityPO.class);
            authorities = new LinkedHashMap<>();
            convert(tree, authorities);
        }
    }

    protected void convert(AuthorityPO po, Map<String, Authority> map) {
        if (map.containsKey(po.getKey())) {
            throw new IllegalArgumentException("Key duplicated");
        }
        Authority authority = facadeAdapter.po2do(po);
        map.put(authority.getKey(), authority);
        for (AuthorityPO child : po.getChildren()) {
            convert(child, map);
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
    public Authority get(String key) {
        return authorities.get(key);
    }

    @Override
    public Collection<? extends Authority> select(Collection<? extends String> keys) {
        Set<String> keySet = new HashSet<>(keys);
        return Collections.unmodifiableCollection(authorities.values()
                .stream()
                .filter(it -> keySet.contains(it.getKey()))
                .collect(Collectors.toSet()));
    }

    @Override
    public Collection<? extends Authority> all() {
        return Collections.unmodifiableCollection(authorities.values());
    }

    @Override
    public Collection<? extends AuthorityVO> tree(String name) {
        return facadeAdapter.po2vo(tree.search(name)).getChildren();
    }
}
