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

import com.taole.framework.cache.MapCacheFactoryImpl;
import com.taole.framework.cache.ThreadCacheFactory;
import com.taole.framework.util.ThreadLocalFactory;

/**
 * Class <code>HttpHostServerNameFilter</code> is ...
 * 
 * @author 
 * @version 0.1, Feb 27, 2009
 */
public class ThreadInitializerFilter extends OncePerRequestFilter {

	private String[] shouldNotFilterUrls = null;

	protected void initFilterBean() throws ServletException {
		String paramShouldNotFilterUrls = this.getFilterConfig().getInitParameter("shouldNotFilterUrls");
		if (paramShouldNotFilterUrls == null) {
			shouldNotFilterUrls = new String[] {};
		} else {
			shouldNotFilterUrls = paramShouldNotFilterUrls.split(",");
		}
	}
	

	/**
	 * Can be overridden in subclasses for custom filtering control, returning <code>true</code> to avoid filtering of the given request.
	 * <p>
	 * The default implementation always returns <code>false</code>.
	 * 
	 * @param request
	 *            current HTTP request
	 * @return whether the given request should <i>not</i> be filtered
	 * @throws ServletException
	 *             in case of errors
	 */
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String requestUri = request.getRequestURI();
		for (String shouldNotFilterUrl : shouldNotFilterUrls) {
			if (requestUri.startsWith(shouldNotFilterUrl)) {
				return true;
			}
		}
		return false;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			logger.debug(" -------------- init thread ---------  for: " + request.getRequestURI());
			ThreadLocalFactory.cleanAllThreadLocals();
			ThreadCacheFactory.initCacheFactory(new MapCacheFactoryImpl());
			filterChain.doFilter(request, response);
		} finally {
			ThreadCacheFactory.destroyCacheFactory();
		}
	}

}
