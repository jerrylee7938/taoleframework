package com.taole.framework.rest.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可绑定post/get过来的参数
 * 
 * @author Rory
 * @date Feb 29, 2012
 * @version $Id: ParameterVariable.java 5 2014-01-15 13:55:28Z yedf $
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterVariable {

	/** The URI template variable to bind to. */
	String value() default "";

}
