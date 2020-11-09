/*
 * @(#)XmlRpcBeanUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.exception.SystemException;
import com.taole.framework.service.ServiceResult;

public class XmlRpcBeanUtils {

    /**
     * 将java对象类型转化为xmlrpc数据类型
     * 
     * @param object
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object convertJava2XmlRpc(Object object)
	    throws IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException {

	if (object == null) {
	    return null;
	} else if (BeanUtils.isBasicInstance(object)) {
	    if (object.getClass() == boolean.class) {
		return Boolean.valueOf(object.toString());
	    } else if (object.getClass() == int.class) {
		return Integer.valueOf(object.toString());
	    } else if (object.getClass() == long.class) {
		return Integer.valueOf(object.toString());
	    } else if (object.getClass() == float.class) {
		return Double.valueOf(object.toString());
	    } else if (object.getClass() == double.class) {
		return Double.valueOf(object.toString());
	    } else {
		return object;
	    }
	} else if (object instanceof Date) {
	    return object;
	} else if (object.getClass().isArray()) {
	    // Vector<Object> vector = new Vector<Object>();
	    List<Object> list = new ArrayList<Object>();
	    Object[] objs = (Object[]) object;
	    for (int i = 0; i < objs.length; i++) {
		list.add(convertJava2XmlRpc(objs[i]));
	    }
	    return list;
	} else if (object instanceof List) {
	    // Vector<Object> vector = new Vector<Object>();
	    List<Object> list = new ArrayList<Object>();
	    List olist = (List) object;
	    for (int i = 0; i < olist.size(); i++) {
		list.add(convertJava2XmlRpc(olist.get(i)));
	    }
	    return list;
	} else if (object instanceof DomainObject) {
	    // Map map = new HashMap();
	    // map.put("__class__", object.getClass().getName());
	    Map beanMap = new HashMap();
	    // map.put("__vos__", beanMap);
	    Class type = object.getClass();
	    String[] props = BeanUtils.getPropertyNames(type);
	    for (int i = 0; i < props.length; i++) {
		String prop = props[i];
		if (prop.startsWith("CGLIB") || prop.startsWith("hibernate")) {
		    continue;
		}
		if (prop.equals("callback") || prop.equals("callbacks")) {
		    continue;
		}
		Object value = BeanUtils.getValue(object, prop);
		beanMap.put(prop, convertJava2XmlRpc(value));
	    }
	    // return map;
	    return beanMap;
	} else if (object instanceof ServiceResult) {
	    ServiceResult r = (ServiceResult) object;
	    Map map = new HashMap();
	    map.put("code", r.getCode());
	    map.put("description", r.getDescription());
	    map.put("result", convertJava2XmlRpc(r.getResult()));
	    return map;
	} else if (object instanceof Throwable) {
	    Throwable t = (Throwable) object;
	    Map map = new HashMap();
	    map.put("cause", convertJava2XmlRpc(t.getCause()));
	    map.put("message", t.getMessage());
	    return map;
	} else {
	    throw new SystemException("unkown object type Exception Type "
		    + object.getClass().getName());
	}

    }

    /**
     * 将 xmlrpc 对象类型转化为java对象类型 .
     * 
     * @param clazz
     * @param beanMap
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object convertXmlRpc2Java(Object srcObj, Class clazz)
	    throws SystemException {

	if (BeanUtils.isBasicType(clazz)) {
	    return BeanUtils.convertType(srcObj, clazz);
	} else if (clazz.isInstance(srcObj)){
		return clazz.cast(srcObj);
	} else if (srcObj instanceof Map) {
	    Map beanMap = (Map) srcObj;
	    String className = (String) ((Map) srcObj).get("__class__");
	    if (className != null) {
		try {
		    clazz = Class.forName(className);
		    beanMap = (Map) beanMap.get("__vos__");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	    Object bean = null;
	    try {
		if (clazz.isArray()) {
		    clazz = clazz.getComponentType();
		}
		bean = createInstance(clazz);
	    } catch (InstantiationException e) {
		throw new SystemException(" this class '" + clazz.getName()
			+ "' is not to be instance Exception!", e);
	    } catch (IllegalAccessException e) {
		throw new SystemException(
			" the currently executing method does not hava access to the definition of the specified class!",
			e);
	    }

	    for (Iterator iterator = beanMap.entrySet().iterator(); iterator
		    .hasNext();) {
		Map.Entry entry = (Map.Entry) iterator.next();
		String name = entry.getKey().toString();
		Object value = entry.getValue();
		// check getter method
		Exception ex = null;
		try {
		    String getMethodName = "get"
			    + name.substring(0, 1).toUpperCase()
			    + name.substring(1);
		    Method getter = clazz.getMethod(getMethodName,
			    new Class[] {});
		    if (getter != null) {
			Class pType = getter.getReturnType();
			setPropertyValue(bean, name, pType, convertXmlRpc2Java(
				value, pType));
			continue;
		    }
		} catch (Exception e) {
		    ex = e;
		}

		// check iser method
		try {
		    String isMethodName = "is"
			    + name.substring(0, 1).toUpperCase()
			    + name.substring(1);
		    Method iser = clazz.getMethod(isMethodName, new Class[] {});
		    if (iser != null) {
			Class pType = iser.getReturnType();
			setPropertyValue(bean, name, pType, convertXmlRpc2Java(
				value, pType));
			continue;
		    }
		} catch (Exception e) {
		    ex = e;
		}
		
		if (ex != null) {
		   // ex.printStackTrace();
		}
		

	    }
	    return bean;
	} else if (srcObj.getClass().isArray()) {

	    int len = Array.getLength(srcObj);
	    Object[] vals = new Object[len];
	    for (int i = 0; i < len; i++) {
		vals[i] = convertXmlRpc2Java(Array.get(srcObj, i), clazz
			.getComponentType());
	    }
	    if (clazz == List.class) {
		List list = new ArrayList();
		for (int i = 0; i < len; i++) {
		    list.add(vals[i]);
		}
		return list;
	    } else if (clazz == Vector.class) {
		Vector vector = new Vector();
		for (int i = 0; i < len; i++) {
		    vector.add(vals[i]);
		}
		return vector;
	    } else {
		return vals;
	    }
	} else if (srcObj instanceof Vector) {
	    Vector beanVer = (Vector) srcObj;
	    Object[] vals = new Object[beanVer.size()];
	    int len = beanVer.size();
	    for (int i = 0; i < len; i++) {
		vals[i] = convertXmlRpc2Java(beanVer.get(i), clazz
			.getComponentType());
	    }
	    return vals;
	} else if (srcObj instanceof List) {
	    List beanVer = (List) srcObj;
	    Object[] vals = new Object[beanVer.size()];
	    int len = beanVer.size();
	    for (int i = 0; i < len; i++) {
		vals[i] = convertXmlRpc2Java(beanVer.get(i), clazz
			.getComponentType());
	    }
	    return vals;
	} else {
	    throw new SystemException("unkown object type: '"
		    + srcObj.getClass().getName() + "' Exception!");
	}
    }

    /**
     * @param obj
     * @param property
     * @param type
     * @param value
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    static boolean setPropertyValue(Object obj, String property, Class type,
	    Object value) {
	if (type.isArray()) {
	    Class comType = type.getComponentType();
	    Object[] srcValue = (Object[]) value;
	    if (comType == String.class) {
		int len = srcValue.length;
		String[] tarValue = new String[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (String) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Integer.class) {
		int len = srcValue.length;
		Integer[] tarValue = new Integer[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Integer) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Date.class) {
		int len = srcValue.length;
		Date[] tarValue = new Date[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Date) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Boolean.class) {
		int len = srcValue.length;
		Boolean[] tarValue = new Boolean[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Boolean) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Long.class) {
		int len = srcValue.length;
		Long[] tarValue = new Long[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Long) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Double.class) {
		int len = srcValue.length;
		Double[] tarValue = new Double[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Double) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    } else if (comType == Float.class) {
		int len = srcValue.length;
		Float[] tarValue = new Float[len];
		for (int i = 0; i < len; i++) {
		    tarValue[i] = (Float) srcValue[i];
		}
		return BeanUtils.setValue(obj, property, tarValue);
	    }
	}
	return BeanUtils.setValue(obj, property, BeanUtils.convertType(value,
		type));
    }


    @SuppressWarnings({ "rawtypes" })
	private static Object createInstance(Class type) throws InstantiationException, IllegalAccessException {
    	if (type == Map.class) {
    		return new HashMap();
    	} else 	if (type == List.class) {
    		return new ArrayList();
    	} else {
    		return type.newInstance();
    	}
    }
}
