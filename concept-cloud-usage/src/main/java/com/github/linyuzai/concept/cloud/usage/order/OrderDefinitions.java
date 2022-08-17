package com.github.linyuzai.concept.cloud.usage.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDefinitions {

    private static final Map<Class<?>, Integer> orderDefinitionMap = new ConcurrentHashMap<>();

    public static void define(Class<?> c, int order) {
        orderDefinitionMap.put(c, order);
    }

    public static int get(Class<?> c, int defaultOrder) {
        return orderDefinitionMap.getOrDefault(c, defaultOrder);
    }

    public static Map<Class<?>, Integer> get() {
        return orderDefinitionMap;
    }
}
