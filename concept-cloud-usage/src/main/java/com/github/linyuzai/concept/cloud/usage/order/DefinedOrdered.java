package com.github.linyuzai.concept.cloud.usage.order;

import org.springframework.core.Ordered;

public interface DefinedOrdered extends Ordered {

    @Override
    default int getOrder() {
        return OrderDefinitions.get(getClass(), getDefaultOrder());
    }

    default int getDefaultOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
