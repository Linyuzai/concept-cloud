package com.github.linyuzai.concept.domain.system.module.authority.file;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

@Data
public class AuthorityPO {

    protected String key;

    protected String name;

    protected String pattern;

    protected Collection<AuthorityPO> children;

    public AuthorityPO search(String name) {
        return search(this, authority -> authority.getName().contains(name));
    }

    public AuthorityPO search(Predicate<AuthorityPO> predicate) {
        return search(this, predicate);
    }

    protected AuthorityPO search(AuthorityPO authority, Predicate<AuthorityPO> predicate) {
        AuthorityPO po = new AuthorityPO();
        po.setKey(authority.getKey());
        po.setName(authority.getName());
        po.setPattern(authority.getPattern());
        po.setChildren(new ArrayList<>());
        for (AuthorityPO child : authority.getChildren()) {
            AuthorityPO searched = search(child, predicate);
            if (searched != null) {
                po.getChildren().add(searched);
            }
        }
        if (!po.getChildren().isEmpty() || predicate.test(po)) {
            return po;
        } else {
            return null;
        }
    }
}
