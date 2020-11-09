/*
 * @(#)Translator.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.Hashtable;
import java.util.Map;

public abstract class CacheSupportTranslator implements Translator<String, String> {

	private Map<String, String> map = new Hashtable<String, String>();

	/**
	 * @param word
	 * @return
	 */
	public abstract String translateWithoutCache(String word);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taole.framework.util.Translator#translate(java.lang.String)
	 */
	public String translate(String word) {
		if (word == null) {
			return null;
		}
		if (map.containsKey(word)) {
			return map.get(word);
		}
		String word1 = translateWithoutCache(word);
		if (word1 != null) {
			map.put(word, word1);
		}
		return word1;
	}

}
