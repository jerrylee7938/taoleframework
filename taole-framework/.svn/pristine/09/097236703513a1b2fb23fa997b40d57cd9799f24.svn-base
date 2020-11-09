/*
 * @(#)HibernateConfigurationHelper.java 0.1 Jan 31, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.mapping.PersistentClass;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * Class <code>HibernateConfigurationHelper</code> is ...
 * 
 * @author Changhua Wan
 * @version 0.1, Jan 31, 2008
 */
public class HibernateConfigurationHelper {

	private Map<String, PersistentClass> classNameMap = null;
	private Map<String, PersistentClass> entityNameMap = null;
	private LocalSessionFactoryBean localSessionFactoryBean;

	public void setLocalSessionFactoryBean(LocalSessionFactoryBean localSessionFactoryBean) {
		this.localSessionFactoryBean = localSessionFactoryBean;
	}

	@SuppressWarnings("rawtypes")
	public String getPkColumnName(Class clazz) {
		return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();
	}

	public String getPkColumnName(String entityName) {
		return getPersistentClass(entityName).getTable().getPrimaryKey().getColumn(0).getName();
	}

	@SuppressWarnings("rawtypes")
	public String getTableName(Class clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	public String getTableName(String entityName) {
		return getPersistentClass(entityName).getTable().getName();
	}

	@SuppressWarnings("rawtypes")
	public String getEntityName(Class clazz) {
		return getPersistentClass(clazz).getEntityName();
	}

	@SuppressWarnings("rawtypes")
	private synchronized PersistentClass getPersistentClass(Class clazz) {

		if (classNameMap == null) {
			classNameMap = new HashMap<String, PersistentClass>();
			Iterator iterator = localSessionFactoryBean.getConfiguration().getClassMappings();
			while (iterator.hasNext()) {
				PersistentClass pc = (PersistentClass) iterator.next();
				classNameMap.put(pc.getClassName(), pc);
			}
		}
		return classNameMap.get(clazz.getName());
	}

	@SuppressWarnings("rawtypes")
	private synchronized PersistentClass getPersistentClass(String entityName) {

		if (entityNameMap == null) {
			entityNameMap = new HashMap<String, PersistentClass>();
			Iterator iterator = localSessionFactoryBean.getConfiguration().getClassMappings();
			while (iterator.hasNext()) {
				PersistentClass pc = (PersistentClass) iterator.next();
				entityNameMap.put(pc.getEntityName(), pc);
			}
		}
		return entityNameMap.get(entityName);
	}

}
