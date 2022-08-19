package com.github.linyuzai.concept.domain.basic;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDomainService implements DomainService {

    @Autowired
    protected DomainEventPublisher domainEventPublisher;

    public void publish(Object event) {
        domainEventPublisher.publish(event);
    }
}
