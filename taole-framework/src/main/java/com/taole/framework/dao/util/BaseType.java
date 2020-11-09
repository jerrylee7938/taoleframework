/*
 * @(#)StringListType.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import com.taole.framework.util.DateUtils;

/**
 * 用户自定义类型
 */
public class BaseType implements UserType {

	private static final int[] TYPES = new int[] { Types.VARCHAR };

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return TYPES;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class<?> returnedClass() {
		return Object.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
	 *      java.lang.Object)
	 */
	public boolean equals(Object one, Object another) throws HibernateException {
		if (one == another) {
			return true;
		} else if (one != null && another != null) {
			one.equals(another);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		@SuppressWarnings("deprecation")
		String value = (String) StringType.INSTANCE.nullSafeGet(rs, names[0]);
		if (value != null) {
			return parse(value);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement,
	 *      java.lang.Object, int)
	 */
    @SuppressWarnings("deprecation")
	public void nullSafeSet(PreparedStatement pstmt, Object value, int index)
			throws HibernateException, SQLException {
		if (value != null) {
			StringType.INSTANCE.nullSafeSet(pstmt, format(value), index);
		} else {
			StringType.INSTANCE.nullSafeSet(pstmt, null, index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
    	if (value == null) {
    		return null;
    	} else if (value instanceof String) {
    		return String.valueOf(value);
    	} else if (value instanceof Integer) {
    		return Integer.valueOf(value.toString());
    	} else if (value instanceof Long) {
    		return Long.valueOf(value.toString());
    	} else if (value instanceof Boolean) {
    		return Boolean.valueOf(value.toString());
    	} else if (value instanceof Date) {
    		return ((Date)value).clone();
    	} else if (value instanceof Float) {
    		return Float.valueOf(value.toString());
    	} else if (value instanceof Double) {
    		return Double.valueOf(value.toString());
    	} else {
    		return value;
    	}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public String format(Object value) {
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

	private Object parse(String value) {
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

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}
}
