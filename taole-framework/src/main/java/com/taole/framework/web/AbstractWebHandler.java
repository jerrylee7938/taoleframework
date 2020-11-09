/**
 * Project:HEAFramework 3.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since May 20, 2011 6:50:53 PM
 * @version $Id: AbstractWebHandler.java 5 2014-01-15 13:55:28Z yedf $
 */
public abstract class AbstractWebHandler {
	
	private final Log log = LogFactory.getLog(AbstractWebHandler.class);
	
	/**
	 * 
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	public ModelAndView create(String id, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		if ("GET".equals(request.getMethod())) {
			return createForm();
		}
		return doCreate();
	}

	public ModelAndView update(String id, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		if ("GET".equals(request.getMethod())) {
			return updateForm(id);
		}
		return doUpdate(id);
	}

	public ModelAndView delete(String id, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return doDelete(id);
	}
	
	public ModelAndView search(String id, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return doSearch();
	}
	
	public ModelAndView view(String id, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return doView(id);
	}

	public abstract ModelAndView doCreate();
	
	public abstract ModelAndView createForm();

	public abstract ModelAndView doView(String id);
	
	public abstract ModelAndView updateForm(String id);
	
	public abstract ModelAndView doUpdate(String id);

	public abstract ModelAndView doDelete(String id);

	public abstract ModelAndView doSearch();
	
	protected Sorter getSorter(HttpServletRequest request) {
		return getSorter(request, null, true);
	}
	
	protected Sorter getSorter(HttpServletRequest request, String defaultSortField) {
		return getSorter(request, defaultSortField, true);
	}
	
	protected Sorter getSorter(HttpServletRequest request, String defaultSortField, boolean desc) {
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
		} else if (defaultSortField != null) {
			if (desc) {
				sorter.desc(defaultSortField);
			} else {
				sorter.asc(defaultSortField);
			}
		}
		return sorter;
	}
	
	protected Range getRange(HttpServletRequest request) {
		try {
			Integer page = ServletRequestUtils.getIntParameter(request, "page");
			Integer pageSize = ServletRequestUtils.getIntParameter(request, "pageSize");
			if (page == null || page < 1) {
				page = 1;
			}
			if (pageSize == null) {
				pageSize = DEFAULT_PAGE_SIZE;
			}
			int start = (page - 1) * pageSize;
			return new Range(start, pageSize);
		} catch (ServletRequestBindingException e) {
			log.error("get range parameter error:", e);
		}
		return new Range(0, DEFAULT_PAGE_SIZE);
	}
	

}
