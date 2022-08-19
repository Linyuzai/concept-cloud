package com.github.linyuzai.concept.domain.system.module.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    protected AuthorityService authorityService;

    @Autowired
    protected AuthoritySearcher authoritySearcher;

    @GetMapping("/tree")
    public Collection<? extends AuthorityVO> tree(String name) {
        return authoritySearcher.tree(name);
    }
}
