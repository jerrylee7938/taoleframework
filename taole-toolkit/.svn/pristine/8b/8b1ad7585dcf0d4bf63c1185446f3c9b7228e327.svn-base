package com.taole.toolkit.util;

import org.jsoup.helper.StringUtil;

import com.taole.framework.dao.util.Range;

public class PageUtil {
	/**
	 * 根据请求上来的页码和每页记录数，规范化Range对象
	 * 
	 * @return
	 */
	public static Range getRangeAByReq(String page, String length) {
		if (StringUtil.isBlank(page)) {
			page = "1";
		}

		if (StringUtil.isBlank(length)) {
			length = "10";
		}
		int start = (Integer.parseInt(page) - 1) * Integer.parseInt(length);
		Range range = new Range(start, Integer.parseInt(length));
		return range;
	}
}
