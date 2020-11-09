package com.taole.toolkit.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CmisStringUtil {
	public static String stringsToString(String[] strs) {
		String newStr = null;
		if (strs != null) {
			for (String str : strs) {
				if (newStr == null) {
					newStr = str;
				} else {
					newStr = newStr + "," + str;
				}
			}
		}
		return newStr;
	}

	/**
	 * 过滤字符串数组中的空字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] filterStrsForBlank(String[] strs) {
		List<String> strList = new ArrayList<String>();
		for (String str : strs) {
			if (StringUtils.isNotBlank(str)) {
				strList.add(str);
			}
		}

		if (strList.size() > 0) {
			String[] newStrs = new String[strList.size()];
			for (int i = 0; i < strList.size(); i++) {
				newStrs[i] = strList.get(i);
			}
			return newStrs;
		}
		return null;
	}

	public static String doubleToString(Double num) {
		return String.valueOf(num);
	}
}
