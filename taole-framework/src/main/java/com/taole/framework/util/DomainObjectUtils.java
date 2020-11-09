/*
 * @(#)DomainObjectUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.annotation.DomainEntity;
import com.taole.framework.annotation.NamePosition;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.opensymphony.util.BeanUtils;

public abstract class DomainObjectUtils {

	/*
	 * @param obj
	 * 
	 * @param map
	 * 
	 * @param updateProps
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateDomainObjectByMap(DomainObject obj, Map map) {
		BeanUpdater.updateObject(obj, map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateDomainObjectProperty(Object obj, String propName, Object newVal) {
		Object oldVal = BeanUtils.getValue(obj, propName);
		if (oldVal != null && newVal != null && oldVal instanceof Map<?, ?> && newVal instanceof Map<?, ?>) {
			Map map0 = (Map) oldVal;
			Map map1 = (Map) newVal;
			map0.putAll(map1);
		} else {
			BeanUpdater.updateObject(obj, propName, newVal);
		}
	}

	static Set<String> systemPropNames = new HashSet<String>(Arrays.asList("sysId", "sysFlag", "sysDomain", "sysOwner", "sysCreateDate", "sysUpdateDate", "sysVersion"));

	/**
	 * 从两个对象中复制相同的属性，containsSystemProperties 表示是否包含系统字段。
	 * 
	 * @param obj
	 * @param other
	 * @param containsSystemProperties
	 */
	public static void updateDomainObjectByAnother(DomainObject obj, DomainObject other, boolean containsSystemProperties) {
		String[] propNames = com.taole.framework.util.BeanUtils.getPropertyNames(com.taole.framework.util.BeanUtils.getClass(obj));
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			if (!containsSystemProperties && systemPropNames.contains(propName)) {
				continue;
			}
			Object value = BeanUtils.getValue(other, propName);
			if (value != null) {
				BeanUpdater.updateObject(obj, propName, value);
			}
		}
	}

	public static void updateDomainObjectByAnother(Object obj, Object other) {
		String[] propNames = com.taole.framework.util.BeanUtils.getPropertyNames(com.taole.framework.util.BeanUtils.getClass(obj));
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			Object value = BeanUtils.getValue(other, propName);
			if (value != null) {
				BeanUpdater.updateObject(obj, propName, value);
			}
		}
	}

	public static void updateDomainObjectBasePropertiesByAnother(Object obj, Object other) {
		String[] propNames = com.taole.framework.util.BeanUtils.getPropertyNames(com.taole.framework.util.BeanUtils.getClass(obj));
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			Object value = BeanUtils.getValue(other, propName);
			if (value != null && com.taole.framework.util.BeanUtils.isBasicInstance(value)) {
				BeanUpdater.updateObject(obj, propName, value);
			}
		}
	}

	/**
	 * @param type
	 * @param propName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getGetMethod(Class type, String propName) {
		String methodName = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
		try {
			return type.getMethod(methodName, new Class[0]);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param type
	 * @param propName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getSetMethod(Class type, String propName) {
		String methodName = "set" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
		try {
			return type.getMethod(methodName, new Class[0]);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param list
	 * @param propName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getPropertyList(List list, String propName) {
		Iterator iterator = list.iterator();
		List propValList = new ArrayList();
		while (iterator.hasNext()) {
			propValList.add(BeanUtils.getValue(iterator.next(), propName));
		}
		return propValList;
	}

	public static <T> Set<T> getPropertySet(List<?> list, String propName, Class<T> t) {
		Iterator<?> iterator = list.iterator();
		Set<T> set = new HashSet<T>();
		while (iterator.hasNext()) {
			set.add(t.cast(BeanUtils.getValue(iterator.next(), propName)));
		}
		return set;
	}

	static Map<Class<?>, String> entityNames = new HashMap<Class<?>, String>();
	static Map<String, Class<?>> entityTypes = new HashMap<String, Class<?>>();

	public static String getEntityName(Class<?> type) {
		String name = entityNames.get(type);
		if (name != null) {
			return name;
		}
		DomainEntity de = type.getAnnotation(DomainEntity.class);
		if (de != null) {
			name = de.name();
		} else {
			name = ProjectBeanNameGenerator.getClassProjectName(type.getName());
			if (name == null) {
				name = type.getName();
			} else {
				name += "." + type.getSimpleName();
			}
		}
		entityNames.put(type, name);
		entityTypes.put(name, type);
		return name;
	}

	public static Class<?> getEntityType(String entityName) {
		return entityTypes.get(entityName);
	}

	public static Object getPrimaryKeyValue(Object bean) {
		String entityPK = AnnotationUtils.getPrimaryKeyName(bean.getClass());
		return BeanUtils.getValue(bean, entityPK);
	}

	static Map<Class<?>, Object> namePositions = new HashMap<Class<?>, Object>();

	public static String getObjectName(Object bean) {
		if (bean == null) {
			return null;
		}
		Object position = namePositions.get(bean.getClass());
		if (position == null) {
			Field[] fields = AnnotationUtils.getAnnotationDeclaredFieldsWithSupers(bean.getClass(), NamePosition.class);
			if (fields.length > 0) {
				position = fields[0];
			} else {
				Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(bean.getClass(), NamePosition.class);
				if (methods.length > 0) {
					position = methods[0];
				}
			}
			if (position != null) {
				namePositions.put(bean.getClass(), position);
			}
		}
		if (position == null) {
			return null;
		}
		if (position instanceof Field) {
			return String.valueOf(BeanUtils.getValue(bean, ((Field) position).getName()));
		} else if (position instanceof Method) {
			try {
				return String.valueOf(((Method) position).invoke(bean));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
