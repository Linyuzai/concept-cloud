package com.github.linyuzai.concept.domain.system.module.information;

import com.github.linyuzai.concept.domain.basic.DomainEntity;
import lombok.Getter;

@Getter
public class Information implements DomainEntity {

    protected String id;

    protected String name;

    protected String gender;

    protected String phone;

    protected String email;

    protected String identityCard;

    protected String photo;
}
