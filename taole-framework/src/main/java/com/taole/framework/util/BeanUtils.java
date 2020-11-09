/*
 * @(#)BeanUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Factory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.aop.framework.Advised;
import org.springframework.util.ClassUtils;

import com.taole.framework.annotation.NonEntityField;
import com.opensymphony.provider.BeanProvider;

/**
 * Class: BeanUtils
 * 
 * @author: 
 */

public abstract class BeanUtils {

	static BeanProvider beanProvider = new FastBeanProvider();

	/**
	 * Get list of property names of class.
	 * 
	 * @param type
	 *            Class to query for property names.
	 * @return Array of property names, or null if an error occurred.
	 */
	@SuppressWarnings("rawtypes")
	public final static String[] getPropertyNames(Class type) {

		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			List<String> propList = new ArrayList<String>();
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].getPropertyType() != Class.class) {
					propList.add(properties[i].getName());
				}
			}
			return (String[]) propList.toArray(new String[0]);
		} catch (IntrospectionException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public final static String[] getDeclaredFieldNames(Class type) {
		List<String> list = new ArrayList<String>();
		for (Field field : type.getDeclaredFields()) {
			list.add(field.getName());
		}
		return list.toArray(new String[0]);
	}

	@SuppressWarnings("rawtypes")
	public final static String[] getFullDeclaredFieldNames(Class type) {
		Set<String> set = new LinkedHashSet<String>();
		Class superClass = type.getSuperclass();
		if (superClass != null) {
			for (String name : getFullDeclaredFieldNames(superClass)) {
				set.add(name);
			}
		}
		for (Field field : type.getDeclaredFields()) {
			set.add(field.getName());
		}
		return set.toArray(new String[0]);
	}

	public final static Field getClassField(Class<?> type, String fieldName) {
		try {
			return type.getDeclaredField(fieldName);
		} catch (Exception e) {
			if (type.getSuperclass() != null && type.getSuperclass() != Object.class) {
				return getClassField(type.getSuperclass(), fieldName);
			} else {
				return null;
			}
		}
	}

	public final static Method getClassMethod(Class<?> type, String methodName) {
		try {
			return type.getDeclaredMethod(methodName);
		} catch (Exception e) {
			if (type.getSuperclass() != null && type.getSuperclass() != Object.class) {
				return getClassMethod(type.getSuperclass(), methodName);
			} else {
				return null;
			}
		}
	}

	public final static Object invokeMethod(Object obj, String methodName) {
		Method method = getClassMethod(obj.getClass(), methodName);
		if (method != null) {
			try {
				return method.invoke(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public final static String[] getInstanceDeclaredFieldNames(Class type) {
		List<String> list = new ArrayList<String>();
		for (Field field : type.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				list.add(field.getName());
			}
		}
		return list.toArray(new String[0]);
	}

	@SuppressWarnings("rawtypes")
	public final static String[] getFullInstanceDeclaredFieldNames(Class type) {
		Set<String> set = new LinkedHashSet<String>();
		Class superClass = type.getSuperclass();
		if (superClass != null) {
			for (String name : getFullInstanceDeclaredFieldNames(superClass)) {
				set.add(name);
			}
		}
		for (Field field : type.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				set.add(field.getName());
			}
		}
		return set.toArray(new String[0]);
	}

	public final static String[] getBasicTypePropertyNames(Class<?> type) {

		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			List<String> propList = new ArrayList<String>();
			for (int i = 0; i < properties.length; i++) {
				if (isBasicType(properties[i].getPropertyType())) {
					propList.add(properties[i].getName());
				}
			}
			return (String[]) propList.toArray(new String[0]);
		} catch (IntrospectionException e) {
			return null;
		}
	}

	/**
	 * Get list of property names of class.
	 * 
	 * @param type
	 *            Class to query for property names.
	 * @param pattern
	 *            pattern to query for property name .
	 * @return Array of property names, or null if an error occurred.
	 */
	public final static String[] getPropertyNames(Class<?> type, String pattern) {

		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			List<String> propList = new ArrayList<String>();
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].getPropertyType() == Class.class) {
					continue;
				}
				String propName = properties[i].getName();
				if (StringUtils.matches(pattern, propName)) {
					propList.add(propName);
				}
			}
			return (String[]) propList.toArray((new String[0]));
		} catch (IntrospectionException e) {
			return null;
		}
	}

	/**
	 * Get list of property names of class.
	 * 
	 * @param type
	 *            Class to query for property names.
	 * @param patterns
	 *            patterns to query for property name .
	 * @return Array of property names, or null if an error occurred.
	 */
	public final static String[] getPropertyNames(Class<?> type, String[] patterns) {

		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			List<String> propList = new ArrayList<String>();
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].getPropertyType() == Class.class) {
					continue;
				}
				String propName = properties[i].getName();
				for (int j = 0; j < patterns.length; j++) {
					if (StringUtils.matches(patterns[j], propName)) {
						propList.add(propName);
					}
				}
			}
			return (String[]) propList.toArray(new String[0]);
		} catch (Exception e0) {
			e0.printStackTrace();
			return null;
		} /*
		 * catch (IntrospectionException e) { e.printStackTrace(); return null; }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.util.BeanUtils#setValue(Object obj, String property, Object value)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static boolean setValue(Object obj, String property, Object value) {
		Class propType = getPropertyType(obj.getClass(), property);
		Object newVal = null;
		if (value != null) {
			if (propType != null && List.class.isAssignableFrom(propType) && value != null && value.getClass().isArray()) {
				newVal = Arrays.asList((Object[]) value);
			} else if (propType != null && propType.isArray() && value instanceof List) {
				newVal = ((List) value).toArray((Object[]) Array.newInstance(propType.getComponentType(), 0));
			} else {
				try {
					newVal = convertType(value, propType);
				} catch (Exception ex) {
					throw new IllegalArgumentException("illegal value:" + value + "for property:" + property, ex);
				}
			}
		}
		return com.opensymphony.util.BeanUtils.setValue(obj, property, newVal);
	}

	@SuppressWarnings("rawtypes")
	public final static Class getPropertyType(Class type, String propName) {
		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				PropertyDescriptor property = properties[i];
				if (property.getName().equals(propName)) {
					return property.getPropertyType();
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public final static boolean hasProperty(Class type, String propName) {
		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				PropertyDescriptor property = properties[i];
				if (property.getName().equals(propName)) {
					return true;
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public final static boolean hasProperty(Object obj, String propName) {
		return hasProperty(obj.getClass(), propName);
	}

	/*
	 * @see com.opensymphony.util.BeanUtils#getValue(Object obj, String property)
	 */
	public final static Object getValue(Object obj, String property) {
		return beanProvider.getProperty(obj, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.util.BeanUtils#setValue(Object obj, String property, Object value)
	 */
	public final static boolean setNullProperty(Object obj, String property) {
		return com.opensymphony.util.BeanUtils.setValue(obj, property, null);
	}

	/**
	 * 转换基本数据类型: Integer, Long, Float, Double, Boolean
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static Object convertType(Object obj, Class type) {
		if (type == null) {
			return obj;
		}
		String objStr = null;
		if (obj == null) {
			return null;
		} else if (obj.getClass() == type || type.isAssignableFrom(obj.getClass())) {
			return obj;
		} else if (obj instanceof String) {
			objStr = (String) obj;
		} else if (obj instanceof Date) {
			objStr = String.valueOf(((Date) obj).getTime());
		} else {
			objStr = obj.toString();
		}
		if (type == String.class) {
			return objStr;
		} else if (type == Boolean.class) {
			return Boolean.valueOf(objStr);
		} else if (type == boolean.class) {
			return Boolean.parseBoolean(objStr);
		} else if (type == Integer.class) {
			return Integer.valueOf(truncateDecimalPart(objStr));
		} else if (type == int.class) {
			return Integer.parseInt(truncateDecimalPart(objStr));
		} else if (type == Long.class) {
			return Long.valueOf(truncateDecimalPart(objStr));
		} else if (type == long.class) {
			return Long.parseLong(truncateDecimalPart(objStr));
		} else if (type == Float.class) {
			return Float.valueOf(objStr);
		} else if (type == float.class) {
			return Float.parseFloat(objStr);
		} else if (type == Double.class) {
			return Double.valueOf(objStr);
		} else if (type == double.class) {
			return Double.parseDouble(objStr);
		} else if (type == BigDecimal.class) {
			return new BigDecimal(objStr);
		} else if (type == Date.class) {
			try {
				return new Date(Long.parseLong(objStr));
			} catch (NumberFormatException e) {
				return FormatUtils.smartParseDate(objStr);
			}
		} else if (((Class<Enum>) type).isEnum()) {
			return Enum.valueOf((Class) type, objStr);
		} else {
			return obj;
		}
	}

	/**
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBasicType(Class clazz) {
		return clazz == String.class || clazz == Date.class || clazz == java.sql.Date.class || clazz == java.sql.Timestamp.class || clazz == Integer.class || clazz == int.class || clazz == Long.class || clazz == long.class || clazz == Float.class
				|| clazz == float.class || clazz == Double.class || clazz == double.class || clazz == Boolean.class || clazz == boolean.class || clazz == BigDecimal.class || clazz == BigInteger.class || clazz == Byte.class || clazz == byte.class;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean isBasicInstance(Object obj) {
		if (obj == null) {
			return true;
		}
		return isBasicType(obj.getClass());
	}

	/**
	 * 截断小数部分
	 * 
	 * @param str
	 * @return
	 */
	private static String truncateDecimalPart(String str) {
		int pos = str.indexOf('.');
		if (pos != -1) {
			return str.substring(0, pos);
		} else {
			return str;
		}
	}

	@SuppressWarnings("rawtypes")
	private static Map<Object, Class> interfaceMap = new Hashtable<Object, Class>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class getAssignableClass(Object object, Class base) {
		if (interfaceMap.containsKey(object)) {
			return interfaceMap.get(object);
		}
		if (object instanceof Advised) {
			Advised advised = (Advised) object;
			try {
				Object target = advised.getTargetSource().getTarget();
				return getAssignableClass(target, base);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Class[] interfaces = object.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			Class clz = interfaces[i];
			while (clz != null) {
				if (base.isAssignableFrom(clz)) {
					Class iClz = interfaces[i];
					interfaceMap.put(object, iClz);
					return iClz;
				} else {
					clz = clz.getSuperclass();
				}
			}
		}
		// TODO throws exception.
		return null;
	}

	public static Class<?> getClass(Object object) {
		if (object instanceof Factory) {
			return object.getClass().getSuperclass();
		}
		if (ClassUtils.isCglibProxy(object)) {
			return object.getClass().getSuperclass();
		}
		return object.getClass();
	}

	public static boolean equalsIgnoreType(Object obj0, Object obj1) {
		if (obj0 == null && obj1 == null) {
			return true;
		}
		if (obj0 == null && obj1 != null || obj0 != null && obj1 == null) {
			return false;
		}
		if (isBasicInstance(obj0) && isBasicInstance(obj1)) {
			return obj0.hashCode() == obj1.hashCode() || String.valueOf(obj0).equals(String.valueOf(obj1));
		} else {
			return obj0.equals(obj1);
		}
	}

	public static void updateInstanceDeclaredFieldByMap(Object bean, Map<String, Object> updateProps) {
		String[] fields = getFullInstanceDeclaredFieldNames(bean.getClass());
		for (String field : fields) {
			if (updateProps.containsKey(field)) {
				Object val = updateProps.get(field);
				try {
					BeanUtils.setValue(bean, field, val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void updateInstanceDeclaredFieldByAnother(Object bean, Object another) {
		String[] fields = getFullInstanceDeclaredFieldNames(bean.getClass());
		for (String field : fields) {
			Object val = BeanUtils.getValue(another, field);
			try {
				BeanUtils.setValue(bean, field, val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static Map<String, List<String>> fullIDEntityFieldNamesMap = new Hashtable<String, List<String>>();

	public final static List<String> getFullInstanceDeclaredEntityFieldNames(Class<?> type) {
		String cacheKey = type.getName();
		List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
		if (names != null) {
			return new ArrayList<String>(names);
		}
		names = new ArrayList<String>();
		Class<?> superClass = type.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			for (String name : getFullInstanceDeclaredEntityFieldNames(superClass)) {
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		for (Field field : type.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NonEntityField.class)) {
				String name = field.getName();
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		fullIDEntityFieldNamesMap.put(cacheKey, names);
		return names;
	}

	public final static List<String> getFullInstanceDeclaredEntityFieldNames(Class<?> classType, Class<?> fieldType) {
		String cacheKey = classType.getName() + "#" + fieldType.getName();
		List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
		if (names != null) {
			return new ArrayList<String>(names);
		}
		names = new ArrayList<String>();
		Class<?> superClass = classType.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			for (String name : getFullInstanceDeclaredEntityFieldNames(superClass, fieldType)) {
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		for (Field field : classType.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NonEntityField.class) && fieldType.isAssignableFrom(field.getType())) {
				String name = field.getName();
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		fullIDEntityFieldNamesMap.put(cacheKey, names);
		return names;
	}

	public final static List<String> getFullInstanceDeclaredEntityFieldNamesWithout(Class<?> classType, Class<?> fieldType) {
		String cacheKey = classType.getName() + "#!" + fieldType.getName();
		List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
		if (names != null) {
			return new ArrayList<String>(names);
		}
		names = new ArrayList<String>();
		Class<?> superClass = classType.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			for (String name : getFullInstanceDeclaredEntityFieldNamesWithout(superClass, fieldType)) {
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		for (Field field : classType.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NonEntityField.class) && !fieldType.isAssignableFrom(field.getType())) {
				String name = field.getName();
				if (!names.contains(name)) {
					names.add(name);
				}
			}
		}
		fullIDEntityFieldNamesMap.put(cacheKey, names);
		return names;
	}

	static Map<String, List<Field>> fullIDEntityFieldsMap = new Hashtable<String, List<Field>>();

	public final static List<Field> getFullInstanceDeclaredEntityFields(Class<?> clz, Class<?> fieldType) {
		String cacheKey = clz.getName() + "#" + (fieldType == null ? null : fieldType.getName());
		List<Field> fields = fullIDEntityFieldsMap.get(cacheKey);
		if (fields != null) {
			return new ArrayList<Field>(fields);
		}
		fields = new ArrayList<Field>();
		Class<?> superClass = clz.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			for (Field field : getFullInstanceDeclaredEntityFields(superClass, fieldType)) {
				if (!fields.contains(field)) {
					fields.add(field);
				}
			}
		}
		for (Field field : clz.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NonEntityField.class) && (fieldType == null || fieldType.isAssignableFrom(field.getType()))) {
				if (!fields.contains(field)) {
					fields.add(field);
				}
			}
		}
		fullIDEntityFieldsMap.put(cacheKey, fields);
		return fields;
	}

	public static <T extends Enum<T>> T ordinalOf(Class<T> enumType, int ordinal) {
		for (T en : enumType.getEnumConstants()) {
			if (en.ordinal() == ordinal) {
				return en;
			}
		}
		throw new IllegalArgumentException("No enum const " + ordinal);
	}

	private static String getTypeName(Class<?> type) {
		if (type == java.lang.String.class) {
			return "string";
		} else if (type == Integer.class || type == int.class) {
			return "int";
		} else if (type == Long.class || type == long.class) {
			return "long";
		} else if (type == Float.class || type == float.class) {
			return "float";
		} else if (type == Double.class || type == double.class) {
			return "double";
		} else if (type.isAssignableFrom(Number.class)) {
			return "number";
		} else if (type.isAssignableFrom(java.util.Date.class)) {
			return "date";
		} else if (type == Boolean.class || type == boolean.class) {
			return "boolean";
		} else if (type.isAssignableFrom(Enum.class)) {
			return "string";
		} else {
			return "object";
		}
	}

	public static JSONObject getMeta(Class<?> type) {
		JSONObject json = new JSONObject();
		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			json.put("javaClass", type.getName());
			json.put("entityName", DomainObjectUtils.getEntityName(type));
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			List<String> props = Arrays.asList(BeanUtils.getFullInstanceDeclaredFieldNames(type));
			JSONArray fields = new JSONArray();
			for (int i = 0; i < properties.length; i++) {
				try {
					JSONObject field = new JSONObject();
					String fieldName = properties[i].getName();
					if (!props.contains(fieldName)) {
						// continue;
					}
					String fieldType = getTypeName(properties[i].getPropertyType());
					field.put("name", fieldName);
					field.put("type", fieldType);
					// if ("date".equals(fieldType)) {
					// field.put("dateFormat",
					// JSONTransformer.JSON_DATE_FORMAT);
					// }
					fields.put(field);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			json.put("fields", fields);
		} catch (Exception e) {
			return null;
		}
		return json;
	}

}
