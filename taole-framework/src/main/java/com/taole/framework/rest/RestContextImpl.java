/*
 * @(#)WebDataContextImpl.java 0.1 Apr 13, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taole.framework.rest.processor.ResponseProcessor;

/**
 * Class <code>WebDataContextImpl</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Apr 13, 2008
 */
public class RestContextImpl implements RestContext {

	private Object paramObject;
	private ResponseProcessor processor;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String module;
	private String resource;
	private String action;

	public RestContextImpl(String module, String resource, String action, HttpServletRequest request, HttpServletResponse response) {
		this.module = module;
		this.resource = resource;
		this.action = action;
		this.request = request;
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * @see com.taole.framework.service.webdata.WebDataContext#getHandler()
	 */
	public String getModule() {
		return module;
	}

	public String getResource() {
		return resource;
	}

	public String getAction() {
		return action;
	}

	public Object getParameterObject() {
		return paramObject;
	}

	public void setParameterObject(Object obj) {
		paramObject = obj;
	}

	public Map<String, Object> getAttributes() {
		return new HashMap<String, Object>(attributes);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}
	
	@Override
	public <T> T getAttribute(String name, Class<T> clazz) {
		return clazz.cast(attributes.get(name));
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public HttpServletRequest getHttpServletRequest() {
		return request;
	}

	public HttpServletResponse getHttpServletResponse() {
		return response;
	}


	public void setResponseProcessor(ResponseProcessor processor) {
		this.processor = processor;
	}

	public ResponseProcessor getResponseProcessor() {
		return processor;
	}

}
