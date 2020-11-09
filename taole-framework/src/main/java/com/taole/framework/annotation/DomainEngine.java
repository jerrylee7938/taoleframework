package com.taole.framework.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

import com.taole.framework.bean.DomainObject;

@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface DomainEngine {

	Class<? extends DomainObject>[] types();

	@Target({ METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface C {
		Class<? extends DomainObject> type() default DomainObject.class;
	}

	@Target({ METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface R {
		Class<? extends DomainObject> type() default DomainObject.class;
	}

	@Target({ METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface U {
		Class<? extends DomainObject> type() default DomainObject.class;
	}

	@Target({ METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface D {
		Class<? extends DomainObject> type() default DomainObject.class;
	}

}
