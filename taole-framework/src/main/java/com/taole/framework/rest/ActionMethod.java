package com.taole.framework.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.taole.framework.rest.parser.AutoParameterParser;
import com.taole.framework.rest.parser.FormParameterParser;
import com.taole.framework.rest.parser.JSONParameterParser;
import com.taole.framework.rest.parser.OriginalParamterParser;
import com.taole.framework.rest.parser.PojoParameterParser;
import com.taole.framework.rest.processor.CustomResponseProcessor;
import com.taole.framework.rest.processor.ExtGridResponseProcessor;
import com.taole.framework.rest.processor.ExtTreeGridResponseProcessor;
import com.taole.framework.rest.processor.ExtTreeResponseProcessor;
import com.taole.framework.rest.processor.HtmlResponseProcessor;
import com.taole.framework.rest.processor.JSONResponseProcessor;
import com.taole.framework.rest.processor.ViewResponseProcessor;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMethod {

	String action() default "";

	/**
	 * Supports: ["form", "json", "xml", "auto", "pojo", "original"]
	 * 
	 * @see FormParameterParser
	 * @see JSONParameterParser
	 * @see AutoParameterParser
	 * @see PojoParameterParser
	 * @see OriginalParamterParser
	 * 
	 */
	String request() default "auto";

	/**
	 * Supports: ["view", "json", "html", "xml", "stream", "custom", "ext-grid", "ext-treegrid", "ext-tree"]
	 * 
	 * @see ViewResponseProcessor
	 * @see JSONResponseProcessor
	 * @see HtmlResponseProcessor
	 * @see ExtGridResponseProcessor
	 * @see CustomResponseProcessor
	 * @see ExtTreeGridResponseProcessor
	 * @see ExtTreeResponseProcessor
	 */
	String response() default "custom";

	/**
	 * Supports: ["browser", "pad", "mobile", "auto"]
	 */
	String[] agents() default { "browser" };

}
