/**
 * Project:taole-toolkit
 * File:CategoryCondition.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.dict.condition;

import org.apache.commons.lang3.StringUtils;

import com.taole.framework.dao.util.PredicateCondition;
import com.taole.toolkit.dict.domain.Dictionary;

/**
 * @author Rory
 * @date May 31, 2012
 * @version $Id: DictionaryCondition.java 6761 2015-09-07 07:12:01Z tonytang $
 */
public class DictionaryCondition extends PredicateCondition<Dictionary> {

	private String name;
	private String value;
	private String type;
	// private String father;
	private String description;// 主要用于计量单位英文单位的查询

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(Dictionary entity) {
		if (StringUtils.isNotBlank(name) && !name.equals(entity.getName())) {
			return false;
		}

		if (StringUtils.isNotBlank(value) && !value.equals(entity.getValue())) {
			return false;
		}

		if (StringUtils.isNotBlank(type) && !type.equals(entity.getType())) {
			return false;
		}

		if (StringUtils.isNotBlank(description) && !description.equals(entity.getDescription())) {
			return false;
		}
		return true;
	}

}
