package com.taole.framework.rest.processor;

public interface ResponseProcessor {

	String getName();

	Object process(Object input);

}
