/**
 * Copyright 2004-2009  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 身份证工具类
 * 
 * @author rory
 * @since Dec 8, 2009 1:45:01 PM
 * @version $Id: IDNumberUtil.java 5 2014-01-15 13:55:28Z yedf $ 
 */
public class IDNumberUtil {

	/**
	 * 新身份证是18位的
	 */
	public static final int NEW_IDNUMBER_LENGTH = 18;
	
	/**
	 * 老身份证是15位的
	 */
	public static final int OLD_IDNUMBER_LENGTH = 15;
	
	private IDNumberUtil(){}
	
	/**
	 * 将15位的身份证转成18位的
	 * @return
	 */
	public static String convertToNewIdNumber(String oldNumber) {
		if (oldNumber == null) {
			return oldNumber;
		}
		oldNumber = oldNumber.trim();
		if (oldNumber.length() == 15) {
			int[] w = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		    char[] A = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		    String ID17 = oldNumber.substring(0, 6) + "19" + oldNumber.substring(6, 15);
		    String newID17 = "";
		    int[] ID17Array;
		    ID17Array = new int[17];
		    for (int i = 0; i < 17; i++) {
			ID17Array[i] = Integer.parseInt(ID17.substring(i, i + 1));
			newID17 += String.valueOf(ID17Array[i]);
		    }
		    int s = 0;
		    for (int i = 0; i < 17; i++) {
			s = s + ID17Array[i] * w[i];
		    }
		    s = s % 11;
		    return newID17 + A[s];
		}
		return oldNumber;
	}
	
	/**
	 * 根据18位身份证得到出生日期
	 * @param idNumber
	 * @return
	 */
	public static Date parseBirthday(String idNumber) {
		if (idNumber.length() == OLD_IDNUMBER_LENGTH) {
			idNumber = convertToNewIdNumber(idNumber);
		}
		if (idNumber.length() == 18) {
			String birthdayStr = idNumber.substring(6, 14);
			try {
				return DateUtils.parseDate(birthdayStr, new String[]{"yyyyMMdd"});
			} catch (ParseException e) {
				//do nothing
			}
		}
		return null;
	}
}
