/*
 * @(#)ClassFactoryBean.java 0.1 2010-4-6
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.bean;

import org.springframework.beans.factory.FactoryBean;

public class ClassFactoryBean implements FactoryBean<Class<?>> {

	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getObject() throws Exception {
		return Class.forName(className);
	}

	public Class<?> getObjectType() {
		return Class.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
