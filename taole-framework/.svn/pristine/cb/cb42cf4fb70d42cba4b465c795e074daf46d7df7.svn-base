package com.taole.framework.rest.processor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractResponseProcessor implements ResponseProcessor {
	
	@Autowired
	private ResponseProcessorRegistry processorRegistry;
	
	@PostConstruct
	public void init() {
		processorRegistry.register(this);
	}

}
