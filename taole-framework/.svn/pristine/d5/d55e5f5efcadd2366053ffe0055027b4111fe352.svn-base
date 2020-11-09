/**
 * Project:taole-heaframework
 * File:AbstractRestTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.processor.ResponseProcessor;

/**
 * 这里是给需要Rest测试环境的类提供的基类。
 * @author Rory
 * @date May 10, 2012
 * @version $Id: AbstractRestTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public abstract class AbstractRestTest {

	@Before
	public void init() {
		RestSessionFactory.initCurrentContext(new RestContext() {
			@Override
			public void setParameterObject(Object obj) {
			}

			@Override
			public void setAttribute(String name, Object value) {
			}

			@Override
			public void removeAttribute(String name) {
			}

			@Override
			public String getResource() {
				return null;
			}

			@Override
			public Object getParameterObject() {
				return null;
			}

			@Override
			public String getModule() {
				return null;
			}

			@Override
			public HttpServletResponse getHttpServletResponse() {
				return new MockHttpServletResponse();
			}

			@Override
			public HttpServletRequest getHttpServletRequest() {
				return new MockHttpServletRequest();
			}

			@Override
			public Map<String, Object> getAttributes() {
				return null;
			}

			@Override
			public <T> T getAttribute(String name, Class<T> clazz) {
				return null;
			}

			@Override
			public Object getAttribute(String name) {
				return null;
			}

			@Override
			public String getAction() {
				return null;
			}

			@Override
			public void setResponseProcessor(ResponseProcessor processor) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public ResponseProcessor getResponseProcessor() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
