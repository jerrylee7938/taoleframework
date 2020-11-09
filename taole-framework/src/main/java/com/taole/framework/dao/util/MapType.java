/*
 * @(#)StringListType.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import com.taole.framework.util.BaseTypeConvertor;

/**
 * 用户自定义类型,用于保存{@code map}类型，保存在一个字段中,用varchar保存。
 */
public class MapType implements UserType {

	private static final int[] TYPES = new int[] { Types.VARCHAR };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return TYPES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class<?> returnedClass() {
		return Map.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	public boolean equals(Object one, Object another) throws HibernateException {
		if (one == another) {
			return true;
		} else if (one != null && another != null) {
			return one.equals(another);
		} else if (one == null && another == null) {
			return true;
		} else if (one == null) {
			Map map = (Map) another;
			if (map.isEmpty()) {
				return true;
			}
		} else if (another == null) {
			Map map = (Map) one;
			if (map.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	@SuppressWarnings("deprecation")
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
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
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	@SuppressWarnings("deprecation")
	public void nullSafeSet(PreparedStatement pstmt, Object value, int index) throws HibernateException, SQLException {
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
	@SuppressWarnings("rawtypes")
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		} else {
			return ((HashMap) value).clone();
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
	 * 
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	public String format(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		}
		Properties props = convert2Properties((Map) value);
		StringWriter writer = new StringWriter();
		try {
			props.store(writer, null);
			String s = writer.toString();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private Map parse(String value) {
		if (value == null) {
			return null;
		}
		StringReader reader = new StringReader(value);
		Properties props = new Properties();
		try {
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convert2Map(props);
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@SuppressWarnings("rawtypes")
	private Map convert2Map(Properties props) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator iterator = props.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			map.put((String) entry.getKey(), BaseTypeConvertor.convert2Object((String) entry.getValue()));
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	private Properties convert2Properties(Map map) {
		Properties props = new Properties();
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Object value = BaseTypeConvertor.convert2String(entry.getValue());
			if (value != null) {
				props.put(key, value);
			}
		}
		return props;
	}
}
