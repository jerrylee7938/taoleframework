/**
 * Project:taole-toolkit
 * File:FileCondition.java 
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.filesystem.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

/**
 * @author rory
 * @date Nov 1, 2011
 * @version $Id: FileCondition.java 16 2014-01-17 17:58:24Z yedf $
 */
public class FileCondition implements HibernateCondition {

	private String owner;

	private String path;

	private String name;

	private String type;

	private List<String> ids;

	public List<String> getIds() {
		if (ids == null) {
			ids = new ArrayList<String>();
		}
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public FileCondition addIds(String... ids) {
		if (ids != null) {
			getIds().addAll(Arrays.asList(ids));
		}
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(path)) {
			criteria.add(Restrictions.ilike("path", path, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(owner)) {
			criteria.add(Restrictions.eq("owner", owner));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (!getIds().isEmpty()) {
			criteria.add(Restrictions.in("id", ids));
		}
	}

}
