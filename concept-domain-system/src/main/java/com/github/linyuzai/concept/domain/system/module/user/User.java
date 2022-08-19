package com.github.linyuzai.concept.domain.system.module.user;

import com.github.linyuzai.concept.domain.basic.AbstractDomainBuilder;
import com.github.linyuzai.concept.domain.basic.DomainEntity;
import com.github.linyuzai.concept.domain.system.module.account.Account;
import com.github.linyuzai.concept.domain.system.module.information.Information;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements DomainEntity {

    protected String id;

    protected Account account;

    protected Information information;

    public boolean hasAuthority(String key) {
        return account.hasAuthority(key);
    }

    public static class Builder extends AbstractDomainBuilder<User> {

        protected String id;

        protected Account account;

        protected Information information;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder information(Information information) {
            this.information = information;
            return this;
        }
    }
}
