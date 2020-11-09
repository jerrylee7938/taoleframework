/*
 * @(#)ServletUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;

/**
 * Class: ServletUtils Remark: TODO
 * 
 * @author: 
 */
public final class ServletUtils {

	private static final Integer DEFAULT_PAGE_SIZE = 20;

	private ServletUtils() {
	}

	/**
	 * Gets parameter from request
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultVal
	 * @return
	 */
	public static int getIntParameter(ServletRequest request, String paramName, int defaultVal) {
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}

	/**
	 * Gets parameter from request
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultVal
	 * @return
	 */
	public static String getStringParameter(ServletRequest request, String paramName, String defaultVal) {

		String val = request.getParameter(paramName);
		return (val == null) ? defaultVal : val;
	}

	/**
	 * 获得客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String addr = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(addr) || "unknown".equalsIgnoreCase(addr)) {
			addr = request.getHeader("proxy-client-ip");
		}
		if (StringUtils.isEmpty(addr) || "unknown".equalsIgnoreCase(addr)) {
			addr = request.getHeader("wl-proxy-client-ip");
		}
		if (!StringUtils.isEmpty(addr)) {
			addr = addr.replaceAll("unknown,?", "").trim().replaceAll(",.*", "");
		} else {
			addr = request.getRemoteAddr();
		}
		return addr;
	}

	public static String getFullContextPath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		if (request.getServerPort() != 80) {
			sb.append(":");
			sb.append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}
	
	
	public static Sorter getSorter(HttpServletRequest request) {
		return getSorter(request, null, true);
	}
	
	public static Sorter getSorter(HttpServletRequest request, String defaultSortField) {
		return getSorter(request, defaultSortField, true);
	}
	
	
	public static Sorter getSorter(HttpServletRequest request, String defaultSortField, boolean desc) {
		String[] sorts = ServletRequestUtils.getStringParameters(request, "sort");
		String[] orders = ServletRequestUtils.getStringParameters(request, "order");
		Sorter sorter = new Sorter();
		if (sorts != null && sorts.length > 0) {
			for (int i = 0; i < sorts.length; i++) {
				String sort = sorts[i];
				String order = null;
				if (orders != null && orders.length >= i) {
					order = orders[i];
				}
				if (StringUtils.equalsIgnoreCase("desc", order)) {
					sorter.desc(sort);
				} else {
					sorter.asc(sort);
				}
			}
		} else if(StringUtils.isNotBlank(defaultSortField)) {
			if (desc) {
				sorter.desc(defaultSortField);
			} else {
				sorter.asc(defaultSortField);
			}
		}
		return sorter;
	}
	
	public static Range getRange(HttpServletRequest request, int defaultPageSize) {
		try {
			Integer page = ServletRequestUtils.getIntParameter(request, "page");
			Integer pageSize = ServletRequestUtils.getIntParameter(request, "pageSize");
			if (page == null || page < 1) {
				page = 1;
			}
			if (pageSize == null) {
				pageSize = defaultPageSize;
			}
			int start = (page - 1) * pageSize;
			return new Range(start, pageSize);
		} catch (ServletRequestBindingException e) {
			return new Range(0, defaultPageSize);
		}
	}
	
	public static Range getRange(HttpServletRequest request) {
		return getRange(request, DEFAULT_PAGE_SIZE);
	}
	
	
	/**
	 * 解析查询参数
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> parseQueryStringParams(String queryString) {
		if (queryString == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> params = new HashMap<String, String>();
		if (queryString.indexOf("?") != -1) {
			queryString = StringUtils.substringAfterLast(queryString, "?");
		}
		queryString = StringUtils.replace(queryString, "&amp;", "&");
		String[] keyValues = StringUtils.split(queryString, "&");
		if (keyValues != null && keyValues.length > 0) {
			for (String keyValue : keyValues) {
				if (keyValue.indexOf("=") == -1) {
					continue;
				}
				String key = StringUtils.substringBefore(keyValue, "=");
				String value = StringUtils.substringAfter(keyValue, "=");
				params.put(key, value);
			}
		}
		return params;
	}

}
