package com.github.linyuzai.concept.cloud.web.interceptor.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnProperty(name = "concept.cloud.web.intercept.enabled", havingValue = "true", matchIfMissing = true)
public @interface ConditionalOnWebInterceptEnabled {

}
