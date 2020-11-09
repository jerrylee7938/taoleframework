/*
 * @(#)HtmlDomParser.java 0.1 2010-6-30
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.IOException;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public abstract class HtmlDomParser {

	/**
	 * @param html
	 *            HTML 内容
	 * @param xpath
	 * @return
	 * @throws IOException
	 * @throws XPatherException
	 */
	public static String parseStringByXPath(String html, String xpath) {
		try {
			HtmlCleaner cleaner = new HtmlCleaner();
			TagNode rootNode = cleaner.clean(html);
			return parseStringByXPath(rootNode, xpath);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseStringByXPath(TagNode rootNode, String xpath) {
		try {
			HtmlCleaner cleaner = new HtmlCleaner();
			Object[] nodes = rootNode.evaluateXPath(xpath);
			if (nodes != null && nodes.length > 0) {
				Object obj = nodes[0];
				if (obj instanceof String) {
					return (String) obj;
				} else if (obj instanceof TagNode) {
					return cleaner.getInnerHtml((TagNode)obj);
				} else {
					return null;
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
