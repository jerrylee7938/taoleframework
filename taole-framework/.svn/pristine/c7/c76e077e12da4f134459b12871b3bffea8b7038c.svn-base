/*
 * @(#)XMLUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

/**
 * Class: XMLUtils Remark: TODO
 * 
 * @author: 
 */
public abstract class XMLUtils {

    /**
     * 对Attribute内容进行编码
     * 
     * @param s
     * @return
     */
    public static String encodeAttribute(String s) {
	if (s == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer();
	int l = s.length();
	for (int i = 0; i < l; i++) {
	    char c = s.charAt(i);
	    if (c == '&') {
		sb.append("&amp;");
	    } else if (c == '"') {
		sb.append("&quot;");
	    } else {
		sb.append(c);
	    }
	}
	return sb.toString();
    }

    /**
     * 对内容进行html编码
     * 
     * @param s
     * @return
     */
    public static String encodeNodeValue(String s) {
	if (s == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer();
	int l = s.length();
	for (int i = 0; i < l; i++) {
	    char c = s.charAt(i);
	    switch (c) {
	    case '&':
		sb.append("&amp;");
		break;
	    case '"':
		sb.append("&quot;");
		break;
	    case '>':
		sb.append("&gt;");
		break;
	    case '<':
		sb.append("&lt;");
		break;
	    default:
		sb.append(c);
		break;
	    }
	}
	return sb.toString();
    }

    public static String createElementString(String nodeName, String nodeValue) {
	StringBuilder sb = new StringBuilder();
	if (nodeValue == null) {
	    sb.append("<");
	    sb.append(nodeName);
	    sb.append("/>");
	} else {
	    sb.append("<");
	    sb.append(nodeName);
	    sb.append(">");
	    sb.append(XMLUtils.encodeNodeValue(nodeValue));
	    sb.append("</");
	    sb.append(nodeName);
	    sb.append(">");
	}
	return sb.toString();
    }

    public static String createElementString(String nodeName, int nodeValue) {
	return createElementString(nodeName, String.valueOf(nodeValue));
    }

}
