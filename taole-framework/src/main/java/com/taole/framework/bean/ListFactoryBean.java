/*
 * @(#)ListFactoryBean.java 0.1 2010-4-7
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ListFactoryBean extends AbstractFactoryBean {

	private List<List> sourceLists;

	/**
	 * Set the source List, typically populated via XML "list" elements.
	 */
	public void setSourceLists(List<List> sourceLists) {
		this.sourceLists = sourceLists;
	}

	public Class getObjectType() {
		return List.class;
	}

	protected Object createInstance() {
		if (this.sourceLists == null) {
			throw new IllegalArgumentException("'sourceLists' is required");
		}
		List result = new ArrayList();
		for (Iterator<List> it = this.sourceLists.iterator(); it.hasNext();) {
			result.addAll(it.next());
		}
		return result;
	}

}
