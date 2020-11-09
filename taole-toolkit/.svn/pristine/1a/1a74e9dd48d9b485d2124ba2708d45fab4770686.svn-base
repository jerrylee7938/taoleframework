package com.taole.toolkit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

	/**
	 * 手机号有效性判断
	 * 
	 * @param mobNum
	 * @return
	 */
	public static boolean isValidMobNum(String mobNum) {
		// 非空判断
		if (mobNum == null || mobNum.equals("")) {
			return false;
		}

		// 合法性判断
		if (mobNum != null && !mobNum.equals("")) {
			Pattern p = Pattern.compile("^1[\\d]{10}$"); // 验证手机号
			Matcher m = p.matcher(mobNum);
			if (m.matches()) {
				return true;
			}
		}
		return false;
	}
}
