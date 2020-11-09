package com.taole.framework.dao.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import com.taole.framework.util.BeanUtils;


public class PropertyCondition extends PredicateCondition<Object> {
	
	private Class<?> type;

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	private Map<String, Object> properties = new HashMap<String, Object>();

	/**
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public PropertyCondition(Class<?> type) {
		this.type = type;
	}
	
	public PropertyCondition(Class<?> type, Map<String, Object> properties) {
		this.type = type;
		this.properties.clear();
		this.properties.putAll(properties);
	}

	public boolean accept(Object object) {
		if (type.isInstance(object)) {
			BeanMap beanMap = BeanMap.create(object);
			for (String key: properties.keySet()) {
				if (!beanMap.containsKey(key)) {
					return false;
				}
				if (!BeanUtils.equalsIgnoreType(properties.get(key), beanMap.get(key))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
