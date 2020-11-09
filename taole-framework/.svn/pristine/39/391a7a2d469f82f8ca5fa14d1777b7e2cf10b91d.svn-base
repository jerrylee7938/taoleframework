/*
 * @(#)JspHelper.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;

/**
 * Class: JspHelper Remark: TODO
 * 
 * @author: 
 */
public class ServletRequestParameterHelper {

	ServletRequest request;

	public ServletRequestParameterHelper(ServletRequest request) {
		this.request = request;
	}

	/**
	 * 获得请求参数
	 * 
	 * @param paraName
	 * @return
	 */
	public Collection<String> getRequestParameterNames() {
		Collection<String> list = new ArrayList<String>();
		Enumeration<?> enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			list.add((String) enums.nextElement());
		}
		return list;
	}
	
	/**
	 * 获得请求参数
	 * 
	 * @param paraName
	 * @return
	 */
	public String getRequestParameter(String paraName) {
		return request.getParameter(paraName);
	}

	/**
	 * 获得请求参数,如没有发现该参数则返回缺省值
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 */
	public String getRequestParameter(String paraName, String defVal) {
		String val = request.getParameter(paraName);
		return (val != null) ? val : defVal;
	}

	/**
	 * 返回字符串参数
	 * 
	 * @see #getRequestParameter(String)
	 */
	public String getRequestStringParameter(String paraName) {
		return getRequestParameter(paraName);
	}
	
	public String[] getRequestStringParameters(String paraName) {
		return request.getParameterValues(paraName);
	}

	public List<String> getRequestStringParameterList(String paraName) {
		String[] values = getRequestStringParameters(paraName);
		if (values != null) {
			return new ArrayList<String>(Arrays.asList(values));
		} else {
			return new ArrayList<String>();
		}
	}
	
	public List<String> getRequestStringParameterListIgnoreBlank(String paraName) {
		String[] values = getRequestStringParameters(paraName);
		List<String> list = new ArrayList<String>();
		if (values != null) {
			for (String value: values) {
				if (value != null && !value.isEmpty()) {
					list.add(value);
				}
			}
		}
		return list;
	}

	/**
	 * 返回字符串类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public String getRequestStringParameter(String paraName, String defVal) {
		return getRequestParameter(paraName, defVal);
	}

	/**
	 * 返回int类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public int getRequestIntParameter(String paraName, int defVal) {
		try {
			return Integer.parseInt(getRequestParameter(paraName));
		} catch (Exception e) {
			return defVal;
		}
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public boolean getRequestBooleanParameter(String paraName, boolean defVal) {
		String val = getRequestParameter(paraName);
		if (val == null) {
			return defVal;
		} else if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("1") || val.equalsIgnoreCase("on")) {
			return true;
		} else if (val.equalsIgnoreCase("false") || val.equalsIgnoreCase("0")) {
			return false;
		} else {
			return defVal;
		}
	}
	
	
	public <T extends Enum<T>> T getRequestEnumParameter(String paraName, Class<T> enumType) {
		String val = getRequestParameter(paraName);
		try {
			return Enum.valueOf(enumType, val);
		} catch(Exception e) {
			return null	;
		}
	}
	
	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public boolean getRequestBooleanParameter(String paraName, String[] trueVals) {
		String val = getRequestParameter(paraName);
		if (val == null) {
			return false;
		}
		if (trueVals == null) {
			trueVals = new String[]{"true", "1"};
		}
		for (int i = 0; i < trueVals.length; i++) {
			if (trueVals[i].equalsIgnoreCase(val)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName) {
		try {
			String val = getRequestParameter(paraName);
			try {
				long times = Long.parseLong(val);
				return new Date(times);
			} catch (NumberFormatException e) {
				return DateFormat.getInstance().parse(
						getRequestParameter(paraName));
			}
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 返回boolean类型的参数
	 * @param paraName
	 *            参数名
	 * @param format
	 *            日期格式
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, String format) {
		try {
			return new SimpleDateFormat(format)
					.parse(getRequestParameter(paraName));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, Date defVal) {
		try {
			Date d = getRequestDateParameter(paraName);
			if (d == null) {
				return defVal;
			} else {
				return d;
			}
		} catch (Exception e) {
			return defVal;
		}
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param format
	 *            日期格式
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, String format,
			Date defVal) {
		try {
			String val = getRequestParameter(paraName);
			return (val == null) ? defVal : new SimpleDateFormat(format)
					.parse(val);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 返回请求中是否包含某个参数
	 * 
	 * @param paraName
	 * @return
	 */
	public boolean containsRequestParameter(String paraName) {
		return (getRequestParameter(paraName) == null);
	}

	public PageRequest getPageRequest(String pageNoParamName, int pageSize) {
		int pageNo = getRequestIntParameter(pageNoParamName, 1);
		return new PageRequest(pageNo, pageSize);
	}

	public PageRequest getPageRequest(int pageSize) {
		return getPageRequest("page", pageSize);
	}

	public PageRequest getPageRequestObject() {
		return getPageRequest(PageRequest.DEFAULT_PAGE_SIZE);
	}

}
