package com.taole.framework.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {

	String name() default "";

	Field[] fields() default {};

	boolean unique() default false;

	public @interface Field {

		String key() default "";

		boolean asc() default true;

	}

}
