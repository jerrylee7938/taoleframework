package com.taole.framework.rest;

import org.apache.commons.lang3.StringUtils;

import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.util.ExtjsDataConvertor;
import com.taole.framework.util.JSONUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: RestUtils.java 7597 2015-12-25 01:43:49Z tonytang $
 */
public final class RestUtils {

	public static int DEFAULT_PAGE_SIZE = 100;

	public static Sorter getSorter(RestContext context, String defaultSortField) {
		return getSorter((RequestParameters) context.getParameterObject(), defaultSortField);
	}

	public static Sorter getSorter(RequestParameters reqParams) {
		return getSorter(reqParams, null);
	}

	public static Sorter getSorter(RequestParameters reqParams, String defaultSortField) {
		return getSorter(reqParams, defaultSortField, true);
	}

	public static Sorter getSorter(RestContext context, String defaultSortField, boolean desc) {
		return getSorter((RequestParameters) context.getParameterObject(), defaultSortField, desc);
	}

	public static Sorter getSorter(RequestParameters reqParams, String defaultSortField, boolean desc) {
		Object[] sorts = reqParams.getParameterValues("sort");
		Object[] dirs = reqParams.getParameterValues("dir");
		Sorter sorter = null;
		if (sorts != null && sorts.length > 0) {
			for (int i = 0; i < sorts.length; i++) {
				String sort = (String) sorts[i];
				// 如果是 JSON 格式
				if (JSONUtils.isJSON(sort)) {
					return ExtjsDataConvertor.convertSorter(sort);
				}
				if (StringUtils.isBlank(sort)) {
					continue;
				}
				String dir = null;
				if (dirs != null && dirs.length > i) {
					dir = (String) dirs[i];
				}
				if (StringUtils.equalsIgnoreCase("desc", dir)) {
					(sorter = getOrCreateSorter(sorter)).desc(sort);
				} else {
					(sorter = getOrCreateSorter(sorter)).asc(sort);
				}
			}
		} else {
			if (StringUtils.isNotBlank(defaultSortField)) {
				if (desc) {
					(sorter = getOrCreateSorter(sorter)).desc(defaultSortField);
				} else {
					(sorter = getOrCreateSorter(sorter)).asc(defaultSortField);
				}
			} else {
				return null;
			}
		}
		return sorter;
	}

	private static Sorter getOrCreateSorter(Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter();
		}
		return sorter;
	}

	public static Range getRange(RequestParameters reqParams) {
		return getRange(reqParams, DEFAULT_PAGE_SIZE);
	}

	public static Range getRange(RequestParameters reqParams, int defaultPageSize) {
		Integer start = reqParams.getParameter("start", Integer.class);
		Integer page = reqParams.getParameter("page", Integer.class);
		Integer limit = reqParams.getParameter("limit", Integer.class);
		if (limit == null) {
			limit = reqParams.getParameter("pageSize", Integer.class);
		}
		if (limit == null) {
			limit = defaultPageSize;
		}
		if (page == null || page < 1) {
			page = 1;
		}
		if (start == null) {
			start = (page - 1) * limit;
		}
		return new Range(start, limit);
	}
}
