/*
 * @(#)MapUtils.java 0.1 2009-9-18
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class MapUtils {
	
	public static void reserveKeys(Map<String, Object> map, String[] keys) {
		Map<String, Object> newMap = copyMap(map, keys);
		map.clear();
		map.putAll(newMap);
	}
	
	public static void removeKeys(Map<String, Object> map, String[] keys) {
		for (int i = 0; i < keys.length; i++) {
			map.remove(keys[i]);
		}
	}
	
	public static Map<String, Object> copyMap(Map<String, Object> map, String[] keys) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			newMap.put(keys[i], map.get(keys[i]));
		}
		return newMap;
	}
	
	/**
	 * 去掉map中的空 key-value
	 * @param map
	 * @return
	 */
	public static Map<String, Object> removeEmpty(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() == null || org.apache.commons.lang.StringUtils.isBlank(String.valueOf(entry.getValue()))) {
				continue;
			}
			newMap.put(entry.getKey(), entry.getValue());
		}
		return newMap;
	}
	
	public static String toPropertiesStringWithoutComment(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Properties props = new Properties();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = BaseTypeConvertor.convert2String(entry.getValue());
			if (value != null) {
				props.put(key, value);
			}
		}
		StringWriter writer = new StringWriter();
		try {
			props.store(writer, null);
			String s = writer.toString();
			String[] strArr = StringUtils.split(s, '\n');
			List<String> strList = new ArrayList<String>();
			for (String string : strArr) {
				if (!string.startsWith("#")) {
					strList.add(string);
				}
			}
			return StringUtils.join(strList, '\n');
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
