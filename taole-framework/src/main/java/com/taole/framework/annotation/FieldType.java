package com.taole.framework.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldType {

	Class<?> type() default Class.class;
	boolean parent() default false;
	String schema() default "";
	String format() default "";

}