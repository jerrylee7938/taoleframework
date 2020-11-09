/*
 * @(#)HttpHostServerNameFilter.java 0.1 Feb 27, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class <code>HttpHostServerNameFilter</code> is ...
 * 
 * @author 
 * @version 0.1, Feb 27, 2009
 */
public class RefererDependentFilter extends OncePerRequestFilter {

	private static Map<String, Long> lastModifedMap = new HashMap<String, Long>();
	private static Map<String, Set<String>> refererMap = new HashMap<String, Set<String>>();
	
	public static Set<String> getURLsByReferer(String referer) {
		return refererMap.get(referer);
	}
	
	public static Long getLastModifed(String referer) {
		return lastModifedMap.get(referer);
	}
	
	public static void addURL2Referer(String referer, String url) {
		Set<String> list = refererMap.get(referer);
		if (list == null) {
			list = new LinkedHashSet<String>();
			refererMap.put(referer, list);
		}
		if (!list.contains(url)) {
			list.add(url);
			lastModifedMap.put(referer, System.currentTimeMillis());
		}
	}

	/** {@inheritDoc}
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			String referer = (String) request.getHeader("referer");
			if (referer != null) {
				referer = referer.split("#")[0];
				String url = request.getRequestURI();
				String query = request.getQueryString();
				if (!StringUtils.isEmpty(query)) {
					url = url + "?" + query;
				}
				addURL2Referer(referer, url);
			}
		}
		filterChain.doFilter(request, response);
	}

}
