/*
 * @(#)DomainObject.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.bean;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.google.common.collect.Maps;

/**
 * Class: DomainObject Remark: 可扩展的对象
 * 
 * @author: 
 */

@MappedSuperclass
public abstract class ExtendableObject {

	// ~ Instance fields ====================================================================================

	private Map<String, Object> attributes; // 扩展属性

	// ~ Methods ============================================================================================

	// ~ Getters and Setters

	@Type(type = "com.taole.framework.dao.util.MapType")
	@Column(name = "VC2_ATTRIBUTES", length = 4000)
	public Map<String, Object> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public ExtendableObject setAttribute(String name, Object value) {
		this.getAttributes().put(name, value);
		return this;
	}

	public ExtendableObject addAttribute(String name, Object value) {
		this.getAttributes().put(name, value);
		return this;
	}

	public ExtendableObject removeAttribute(String name) {
		getAttributes().remove(name);
		return this;
	}

	@Transient
	public Object getAttribute(String name) {
		return this.getAttributes().get(name);
	}

	@Transient
	public <T> T getAttribute(String name, Class<T> clz) {
		return clz.cast(getAttributes().get(name));
	}

	public Map<String, Object> cloneAttributes() {
		Map<String, Object> cloneAttributes = Maps.newHashMap();
		cloneAttributes.putAll(getAttributes());
		return cloneAttributes;
	}

}
