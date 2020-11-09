/*
 * @(#)WebDataContext.java 0.1 Apr 13, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taole.framework.rest.processor.ResponseProcessor;

/**
 * Class <code>WebDataContext</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Apr 13, 2008
 */
public interface RestContext {

    /**
     * 获得数据句柄名称
     * @return
     */
    public String getResource();
    
    public String getAction();
    
    public String getModule();
    
    public Object getParameterObject() ;

	public void setParameterObject(Object obj);
    
	public void setResponseProcessor(ResponseProcessor processor);

	public ResponseProcessor getResponseProcessor();
	
    /**
     * 获得 Servlet Request
     * @return
     */
    public HttpServletRequest getHttpServletRequest();

    /**
     * 获得 Servlet Response
     * @return
     */
    public HttpServletResponse getHttpServletResponse();
    
    /**
     * @return
     */
	public Map<String, Object> getAttributes();
	
	public Object getAttribute(String name);
	
	public <T> T getAttribute(String name, Class<T> clazz);

	public void setAttribute(String name, Object value);
	
	public void removeAttribute(String name);
    
}
