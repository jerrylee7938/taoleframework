/*
 * @(#)ServiceResult.java 0.1 Dec 10, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.DomainObjectUtils;

/**
 * Class <code>ServiceResult</code> is ...
 * 
 * @author 
 * @version 0.1, Dec 10, 2007
 */
public class ServiceResult {
	
	// ~ Instance fields
	// ====================================================================================

	private int code; // 返回代码

	private String description; // 描述

	private Object result; // 结果

	private boolean structSupport = true;

	// ~ Constructors
	// =======================================================================================

	public ServiceResult() {
	}
	
	public ServiceResult(int code) {
		this(code, null);
	}

	public ServiceResult(int code, String description) {
		this(code, description, null);
	}

    public ServiceResult(int code, String description, Object result) {
		this(code, description, result, true);
	}
    
    public ServiceResult(int code, Object result) {
    	this(code, null, result, true);
    }

	public ServiceResult(int code, String description, Object result, boolean structSupport) {
		this.code = code;
		this.description = description;
		this.structSupport = structSupport;
		this.result = cloneResultObject(result, structSupport);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object cloneResultObject(Object result, boolean structSupport) {
		if (BeanUtils.isBasicInstance(result)) {
			return result;
		} else if (result instanceof Throwable) {
			Throwable t = (Throwable) result;
			return t.getClass().getName() + ":" + t.getMessage();
		} else {
			if (result instanceof Set) {
				Set originalSet = (Set) result;
				Set clonedSet = new HashSet();
				for (Object object : originalSet) {
					clonedSet.add(cloneResultObject(object, structSupport));
				}
				return clonedSet;
			} else if (result instanceof List) {
				List list0 = (List) result;
				List list1 = new ArrayList();
				for (int i = 0; i < list0.size(); i++) {
					list1.add(cloneResultObject(list0.get(i), structSupport));
				}
				return list1;
			} else if (result.getClass().isArray()) {
				List list = new ArrayList();
				for (int i = 0, l = Array.getLength(result); i < l; i++) {
					list.add(cloneResultObject(Array.get(result, i), structSupport));
				}
				return list;
			} else if (result instanceof Map) {
				Map map0 = (Map) result;
				Map map1 = new HashMap();
				Iterator iterator = map0.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					map1.put(entry.getKey(), cloneResultObject(entry.getValue(), structSupport));
				}
				return map1;
			} else if (result instanceof Enum || result instanceof ResultSet) {
				return result;
			} else if (result instanceof JSONObject || result instanceof JSONArray) {
				return result;
			} else {
				try {
					Object obj = BeanUtils.getClass(result).newInstance();
					if (!structSupport) {
						DomainObjectUtils.updateDomainObjectBasePropertiesByAnother(obj, result);
					} else {
						String[] fields = AnnotationUtils.getFullInstanceDeclaredEntityFieldNames(obj.getClass());
						for (String field: fields) {
							Object val = BeanUtils.getValue(result, field);
							val = cloneResultObject(val, structSupport);
							try {
								BeanUtils.setValue(obj, field, val);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					return obj;
				} catch (Exception e) {
					LoggerFactory.getLogger(ServiceResult.class).error("clone object error.", e);
					return null;
				}
			}
		}
	}


	// ~ Methods
	// ============================================================================================

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isStructSupport() {
		return structSupport;
	}

	public void setStructSupport(boolean structSupport) {
		this.structSupport = structSupport;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
