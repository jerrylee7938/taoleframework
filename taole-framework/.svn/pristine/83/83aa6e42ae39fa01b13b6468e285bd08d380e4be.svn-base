/*
 * @(#)JSONUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONUtils {

	/**
	 * 转换String为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(String arg0) {
		if (arg0 == null) {
			return "null";
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append('"');
			int l = arg0.length();
			for (int i = 0; i < l; i++) {
				char c = arg0.charAt(i);
				if (c == '\\') {
					sb.append("\\\\");
				} else if (c == '"') {
					sb.append("\\\"");
				} else if (c == '\b') {
					sb.append("\\b");
				} else if (c == '\f') {
					sb.append("\\f");
				} else if (c == '\t') {
					sb.append("\\t");
				} else if (c == '\n') {
					sb.append("\\n");
				} else if (c == '\r') {
					sb.append("\\r");
				} else {
					sb.append(c);
				}
			}
			sb.append('"');
			return sb.toString();
		}
	}

	/**
	 * 转换String[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(String[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换Object为JSON
	 * 
	 * @param arg0
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	public static String convertJava2JSON(Object arg0) {
		if (arg0 == null) {
			return "null";
		} else if (arg0 instanceof String) {
			return convertJava2JSON((String) arg0);
		} else if (arg0 instanceof Boolean) {
			return convertJava2JSON(((Boolean) arg0).booleanValue());
		} else if (arg0 instanceof Integer) {
			return convertJava2JSON(((Integer) arg0).intValue());
		} else if (arg0 instanceof Long) {
			return convertJava2JSON(((Long) arg0).longValue());
		} else if (arg0 instanceof Double) {
			return convertJava2JSON(((Double) arg0).doubleValue());
		} else if (arg0 instanceof Float) {
			return convertJava2JSON(((Float) arg0).floatValue());
		} else if (arg0 instanceof Map) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			Iterator itor = ((Map) arg0).entrySet().iterator();
			if (itor.hasNext()) {
				Entry entry = (Entry) itor.next();
				sb.append(convertJava2JSON(entry.getKey()));
				sb.append(": ");
				sb.append(convertJava2JSON(entry.getValue()));
			}
			while (itor.hasNext()) {
				sb.append(",");
				Entry entry = (Entry) itor.next();
				String propName = (String) entry.getKey();
				sb.append(convertJava2JSON(propName));
				sb.append(": ");
				sb.append(convertJava2JSON(entry.getValue()));
			}
			sb.append("}");
			return sb.toString();
		}  else if (arg0 instanceof JSONObject) {
			return ((JSONObject) arg0).toString();
		} else if (arg0 instanceof JSONArray) {
			return ((JSONArray) arg0).toString();
		}
		throw new NotImplementedException(
				"convertJava2JSON#JSONUtils has not implemented!");
	}

	/**
	 * 转换Object[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(Object[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换int为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(int arg0) {
		return String.valueOf(arg0);
	}

	/**
	 * 转换int[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(int[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换float为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(float arg0) {
		return String.valueOf(arg0);
	}

	/**
	 * 转换float[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(float[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换long为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(long arg0) {
		return String.valueOf(arg0);
	}

	/**
	 * 转换long[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(long[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换double为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(double arg0) {
		return String.valueOf(arg0);
	}

	/**
	 * 转换double[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(double[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 转换boolean为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(boolean arg0) {
		return arg0 ? "true" : "false";
	}

	/**
	 * 转换boolean[]为JSON
	 * 
	 * @param arg0
	 * @return
	 */
	public static String convertJava2JSON(boolean[] arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int l = arg0.length;
		if (l > 0) {
			sb.append(convertJava2JSON(arg0[0]));
		}
		for (int i = 1; i < l; i++) {
			sb.append(",");
			sb.append(convertJava2JSON(arg0[i]));
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static boolean isJSON(String s) {
		if (s == null) {
			return false;
		}
		s = s.trim();
		if (s.startsWith("{") && s.endsWith("}")) {
			return true;
		}
		if (s.startsWith("[") && s.endsWith("]")) {
			return true;
		}
		if (s.startsWith("\"") && s.endsWith("\"")) {
			return true;
		}
		if (s.startsWith("'") && s.endsWith("'")) {
			return true;
		}
		if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false") || s.equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertJSON2Map(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> itor = json.keys();
		while (itor.hasNext()) {
			String key = itor.next();
			try {
				map.put(key, json.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * for unit test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out
				.println(convertJava2JSON(new String[] { new String("abc") }));
	}

}
