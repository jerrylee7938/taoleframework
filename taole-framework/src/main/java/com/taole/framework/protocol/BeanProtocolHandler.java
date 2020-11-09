/*
 * @(#)ProtocalHandler.java 0.1 May 20, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.protocol;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.taole.framework.bean.DomainObject;

public class BeanProtocolHandler extends AbstractProtocolHandler<Object> implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	Log logger = LogFactory.getLog(getClass());

	public static final String PROTOCOL = "bean";

	static final String URI_PREFIX = PROTOCOL + ":";

	public Object getObject(String uri) {
		String id = uri.substring(URI_PREFIX.length());
		return applicationContext.getBean(id);
	}

	public String getURI(Object entity) {
		Object bean = applicationContext.getBean(entity.getClass());
		if (bean == null) {
			return null;
		}
		Map<String, ?> map = applicationContext.getBeansOfType(entity.getClass());
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			if (entry.getValue() == entity) {
				return URI_PREFIX + entry.getKey();
			}
		}
		return null;
	}

	public boolean supports(Class<?> type) {
		try {
			if (DomainObject.class.isAssignableFrom(type)) {
				return false;
			}
			return applicationContext.getBean(type) != null;
		} catch (BeansException e) {
			return false;
		}
	}

	public String getProtocol() {
		return PROTOCOL;
	}

}
