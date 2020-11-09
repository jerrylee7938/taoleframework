/*
 * @(#)HttpHostServerNameFilter.java 0.1 Feb 27, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class <code>HttpHostServerNameFilter</code> is ...
 * 
 * @author 
 * @version 0.1, Feb 27, 2009
 */
public class HttpHostServerNameFilter extends OncePerRequestFilter {

	private static ThreadLocal<String> hostMap = new ThreadLocal<String>();

	public static String getCurrentHost() {
		return hostMap.get();
	}

	private static void setCurrentHost(String host) {
		hostMap.set(host);
	}
	
	private static void removeCurrentHost() {
		hostMap.remove();
	}

	/** {@inheritDoc}
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String host = request.getServerName();
		String uri = request.getRequestURI();
		int offset = uri.indexOf("/!");
		if (offset != -1) {
			host = uri.substring(offset + 1);
			offset = host.indexOf("/");
			if (offset != -1) {
				host = host.substring(1, offset);
			}
		}
		setCurrentHost(host);
		filterChain.doFilter(request, response);
		removeCurrentHost();
	}

}
