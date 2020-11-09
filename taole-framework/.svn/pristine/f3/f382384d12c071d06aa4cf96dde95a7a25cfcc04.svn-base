package com.taole.framework.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
public @interface ModuleConfig {

	String name();

	String basePackage() default "";

	String[] domainPackages() default {};

	String version() default "1.0";
}
