package com.github.linyuzai.concept.domain.basic;

public interface DomainEventPublisher {

    void publish(Object event);
}
