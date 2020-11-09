package com.taole.cache.redis;

import com.taole.cache.ExcuteType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaoleCache {
    ExcuteType tp() default ExcuteType.COLD;
}
