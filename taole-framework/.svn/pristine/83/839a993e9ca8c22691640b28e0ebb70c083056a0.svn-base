/*
 * @(#)DateUtils.java 0.1 Aug 15, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class <code>DateUtils</code> is ...
 * 
 * @author
 * @version 0.1, Aug 15, 2008
 */
public abstract class DateUtils {

	public static final int FIELD_YEAR = 1;

	public static boolean compare(Date arg0, Date arg1) {
		if (arg0 == null && arg1 == null) {
			return true;
		} else if (arg0 != null && arg1 != null) {
			return arg0.getTime() == arg1.getTime();
		} else {
			return false;
		}
	}

	public static boolean isToday(Date date) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(date, new Date());
	}

	public static boolean afterNow(Date date) {
		return date.getTime() > System.currentTimeMillis();
	}

	public static boolean beforeNow(Date date) {
		return date.getTime() < System.currentTimeMillis();
	}

	public static final long ONEDAYTIMES = 1000 * 60 * 60 * 24;

	public static void cutTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	public static Date cutTime(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cutTime(cal);
		return cal.getTime();
	}

	public static void fillTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
	}

	public static Date fillTime(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		fillTime(cal);
		return cal.getTime();
	}

	public static Date getWeekBeginDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		cutTime(cal);
		return cal.getTime();
	}

	public static Date getWeekEndDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 7);
		fillTime(cal);
		return cal.getTime();
	}

	public static Date getMonthBeginDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cutTime(cal);
		return cal.getTime();
	}

	public static Date getMonthEndDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		fillTime(cal);
		return cal.getTime();
	}

	public static Date getYearBeginDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		cutTime(cal);
		return cal.getTime();
	}

	public static Date getYearEndDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 12);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		fillTime(cal);
		return cal.getTime();
	}

	public static Date yearStart(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date yearEnd(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date monthStart(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date monthEnd(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date dayStart(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date dayEnd(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date parseDate(String s) {
		return new Date(Long.valueOf(s));
	}

	public static int getBetweenDays(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if (c1.after(c2)) {
			c1.setTime(d2);
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			betweenDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
		}
		return betweenDays;
	}

	public static String dateToStr(Date d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	public static Date strToDate(String str, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	public static Date dateFormat(Date d, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(sdf.format(d));
	}

	public static Date getSpecialDate(Date d, int day) throws ParseException {
		String str = dateToStr(d, "yyyy-MM") + "-" + Integer.toString(day);
		return strToDate(str, "yyyy-MM-dd");
	}

	/**
	 * 日期做减法
	 * 
	 * @param date
	 *            参考日期
	 * @param calendarSort
	 *            数字类型，如Calendar.DATE
	 * @param num
	 *            要减掉的日期量
	 * @return
	 * @throws Exception
	 */
	public static Date subDate(Date date, int calendarSort, Integer num) throws Exception {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendarSort, calendar.get(calendarSort) - num);
		return dft.parse(dft.format(calendar.getTime()));
	}

	public static Date addDate(Date date, int calendarSort, Integer num) throws Exception {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendarSort, calendar.get(calendarSort) + num);
		return dft.parse(dft.format(calendar.getTime()));
	}
	
	/**
	 * 获取日期对应的星期数
	 * @param date
	 * @return
	 */
	public static int getWeekNum(Date date){
		Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if (w < 0)  
            w = 0;
        
        return w;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(getBetweenDays(FormatUtils.parseDate("2011-12-31"),
		// FormatUtils.parseDate("2012-03-01")));
		System.out.println(getSpecialDate(new Date(), 13));
	}

}
