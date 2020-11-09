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
import java.util.Date;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

/**
 * 用户自定义类型
 */
public class PropertiesType implements UserType {

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
		return Properties.class;
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
	@SuppressWarnings("deprecation")
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String value = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
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
    	} else {
    		return ((Properties)value).clone();
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
    	}
    	Properties props = (Properties) value;
    	StringWriter writer = new StringWriter();
    	try {
			props.store(writer, "stored by " + new Date().toString());
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object parse(String value) {
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
		return props;
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
