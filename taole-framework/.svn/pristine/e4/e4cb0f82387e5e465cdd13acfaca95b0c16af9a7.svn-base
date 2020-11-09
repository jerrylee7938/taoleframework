package com.taole.framework.rest.parser;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractReqestParser implements ParameterParser {
	
	@Autowired
	private ParameterParserRegistry parameterParserRegistry;
	
	@PostConstruct
	public void init() {
		parameterParserRegistry.register(this);
	}

}
