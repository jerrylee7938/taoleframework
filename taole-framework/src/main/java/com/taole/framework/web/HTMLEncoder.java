/*
 * @(#)HTMLEncoder.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web;

import com.taole.framework.util.StringUtils;
import com.taole.framework.util.XMLUtils;

/**
 * Class: HTMLEncoder Remark: TODO
 * 
 * @author: 
 */
public class HTMLEncoder {

	/**
	 * 对Attribute内容进行编码
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeAttribute(String s) {
		return XMLUtils.encodeAttribute(s);
	}

	/**
	 * 对内容进行html编码
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeNodeValue(String s) {
		return XMLUtils.encodeNodeValue(s);
	}

	
	public static String encodeText2HTML(String s) {
		if (StringUtils.isEmpty(s)) {
		    return "";
		}
		StringBuffer sb = new StringBuffer();
		int l = s.length();
		for (int i = 0; i < l; i++) {
		    char c = s.charAt(i);
		    switch (c) {
		    case ' ':
			sb.append("&nbsp;");
			break;
		    case '\r':
			sb.append("");
			break;
		    case '\n':
			sb.append("<br/>");
			break;
		    default:
			sb.append(c);
			break;
		    }
		}
		return sb.toString();
	}

}
