/*
 * @(#)ClassFactoryBean.java 0.1 2010-4-6
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.bean;

import org.springframework.beans.factory.FactoryBean;

public class StringFactoryBean implements FactoryBean<String> {

	private String template;
	private String var;;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getObject() throws Exception {
		return template.replaceAll("\\%\\{VAR\\}", var);
	}

	public Class<String> getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
