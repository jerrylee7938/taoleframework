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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

/**
 * 用户自定义类型
 */
public class StringListType implements UserType {

	private static final String SPLITTER = "$$$$$";

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
		return List.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
	 *      java.lang.Object)
	 */
    @SuppressWarnings({ "rawtypes" })
	public boolean equals(Object one, Object another) throws HibernateException {
		if (one == another)
			return true;
		if (one != null && another != null) {
			List a = (List) one;
			List b = (List) another;
			if (a.size() != b.size())
				return false;
			for (int i = 0; i < a.size(); i++) {
				String str1 = (String) a.get(i);
				String str2 = (String) b.get(i);
				if (!str1.equals(str2))
					return false;
			}
			return true;
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
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
	 *      java.lang.String[], java.lang.Object)
	 */
	@SuppressWarnings("deprecation")
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
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
			String stringValue = null;
			if (value instanceof List) {
				stringValue = disassemble((List<?>) value);
			} else {
				stringValue = (String) value;
			}
			StringType.INSTANCE.nullSafeSet(pstmt, stringValue, index);
		} else {
			StringType.INSTANCE.nullSafeSet(pstmt, null, index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		List sourceList = (List) value;
		List targetList = new ArrayList();
		targetList.addAll(sourceList);
		return targetList;
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
	public String disassemble(List values) throws HibernateException {
		if (values.isEmpty())
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.size() - 1; i++) {
			sb.append(values.get(i)).append(SPLITTER);
		}
		sb.append(values.get(values.size() - 1));
		return sb.toString();
	}

	private Object parse(String value) {
		String[] strs = StringUtils.split(value, SPLITTER);
		List<String> quotes = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			quotes.add(strs[i]);
		}
		return quotes;
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
