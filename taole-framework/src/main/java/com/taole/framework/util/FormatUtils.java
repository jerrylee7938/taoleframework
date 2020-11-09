/*
 * @(#)FormatUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

public abstract class FormatUtils {

	// 定义日期format对象
	private static final DateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 格式化日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return (date == null) ? "" : format0.format(date);
	}

	public static String formatDate(Date date, String format) {
		return (date == null) ? "" : new SimpleDateFormat(format).format(date);
	}

	/**
	 * 得到一个日期对象根据指定的日期串
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate(String str) {
		try {
			return DateUtils.parseDate(str, new String[] { "yyyy-MM-dd", "yyyy-m-d", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm", "MM-dd", "HH:mm", "yyyy-MM-dd'T'HH:mm:ss" });
		} catch (ParseException e) {
			// e.printStackTrace();
			return null;
		}
	}

	// 定义带时间的format对象
	private static final DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式化日期时间对象
	 * 
	 * @param date
	 * @return
	 */
	public final static String formatDateTime(Date date) {
		return (date == null) ? "" : format1.format(date);
	}

	/**
	 * 得到一个日期对象根据指定的日期时间串
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateTime(String str) {
		try {
			return StringUtils.isEmpty(str) ? null : format1.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// static final String[] WEEK_NAMES = { "Sun", "Mon", "Tue", "Wed", "Thu",
	// "Fri", "Sat" };
	//
	// static final String[] MONTH_NAMES = { "Jan", "Feb", "Mar", "Apr", "May",
	// "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	// 定义GMT的format对象
	private static final DateFormat format2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

	static {
		format2.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public static String formatDateToGMTWithWeek(Date date) {
		return format2.format(date);
	}

	public static Date parseDateToGMTWithWeek(String source) {
		try {
			return format2.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 智能转换日期
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date smartParseDate(String source) {

		try {
			return new Date(source);
		} catch (java.lang.IllegalArgumentException e) {
		}

		try {
			return format2.parse(source);
		} catch (ParseException e) {
		}

		try {
			return format1.parse(source);
		} catch (ParseException e) {
		}
		
		try {
			return format6.parse(source);
		} catch (ParseException e) {
		}

		try {
			return format0.parse(source);
		} catch (ParseException e) {
		}

		return null;
	}

	private static final DateFormat format3 = new SimpleDateFormat("MM-dd");

	public final static String formatShortDate(Date date) {
		return (date == null) ? "" : format3.format(date);
	}

	// 定义带时间的format对象
	private static final DateFormat format4 = new SimpleDateFormat("MM-dd HH:mm");

	/**
	 * 格式化日期时间对象
	 * 
	 * @param date
	 * @return
	 */
	public final static String formatShortDateTime(Date date) {
		return (date == null) ? "" : format4.format(date);
	}

	// 定义日期format对象
	private static final DateFormat format5 = new SimpleDateFormat("yyyy年MM月dd日");

	public final static String formatCNDate(Date date) {
		return (date == null) ? "" : format5.format(date);
	}

	@SuppressWarnings("deprecation")
	public final static String formatCNWeekDay(Date date) {
		int day = date.getDay();
		switch (day) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			return "error";
		}
	}

	private static final DateFormat format6 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public final static String formatJSONDate(Date date) {
		return (date == null) ? "" : format6.format(date);
	}

	public final static Date parseJSONDate(String str) {
		try {
			return StringUtils.isEmpty(str) ? null : format6.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}
}
