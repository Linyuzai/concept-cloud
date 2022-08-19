package com.github.linyuzai.concept.domain.system.module.information;

import com.github.linyuzai.concept.domain.basic.DomainEntity;
import com.github.linyuzai.concept.domain.basic.DomainValue;
import lombok.Getter;

@Getter
public class Information implements DomainValue {

    protected String key;

    protected String name;

    protected String phone;

    protected String email;

    protected String identityCard;

    protected String photo;
}
