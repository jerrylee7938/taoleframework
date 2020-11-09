/*
 * @(#)BaseTypeConvertor.java 0.1 Jul 6, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.Date;

public abstract class BaseTypeConvertor {
	
	public static String convert2String (Object value) {
    	if (value == null) {
    		return null;
    	} else if (value instanceof String) {
    		return "string:" + value;
    	} else if (value instanceof Integer) {
    		return "int:" + String.valueOf(value);
    	} else if (value instanceof Long) {
    		return "long:" + String.valueOf(value);
    	} else if (value instanceof Boolean) {
    		return "boolean:" + String.valueOf(value);
    	} else if (value instanceof Date) {
    		return "date:" + ((Date) value).getTime();
    	} else if (value instanceof Float) {
    		return "float:" + String.valueOf(value);
    	} else if (value instanceof Double) {
    		return "double:" + String.valueOf(value);
    	} else {
    		return value.toString();
    	}
	}

	public static Object convert2Object (String value) {
		if (value == null) {
			return null;
		}
		int offset = value.indexOf(':');
		if (offset == -1) {
			return value;
		}
		String type = value.substring(0, offset);
		if ("string".equalsIgnoreCase(type)) {
			return value.substring(offset + 1);
		} else if ("int".equalsIgnoreCase(type)) {
			return Integer.valueOf(value.substring(offset + 1));
		} else if ("long".equalsIgnoreCase(type)) {
			return Long.valueOf(value.substring(offset + 1));
		} else if ("boolean".equalsIgnoreCase(type)) {
			return Boolean.valueOf(value.substring(offset + 1));
		} else if ("date".equalsIgnoreCase(type)) {
			return DateUtils.parseDate(value.substring(offset + 1));
		} else if ("float".equalsIgnoreCase(type)) {
			return Float.valueOf(value.substring(offset + 1));
		} else if ("dobule".equalsIgnoreCase(type)) {
			return Double.valueOf(value.substring(offset + 1));
		} else {
			return value;
		}
	}
	
}
