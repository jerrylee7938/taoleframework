package com.taole.framework.util;

import java.io.UnsupportedEncodingException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @author wanchangqin 2007-4-29
 * @version $Id: PinyinUtils.java 5 2014-01-15 13:55:28Z yedf $
 */
public final class PinyinUtils {

	private PinyinUtils() {
	}

	/**
	 * 判断是否是中文字符
	 * 
	 * @param cn
	 * @return
	 */
	public static boolean isChineseChar(char c) {
		try {
			return ((String.valueOf(c)).getBytes("gb18030").length == 2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 返回对应字符的汉语拼音字符
	 * 
	 * @param str
	 * @return
	 */
	public static char toHanyuPinyinChar(char c) {
		String[] pys = PinyinHelper.toHanyuPinyinStringArray(c);
		if (pys == null || pys.length == 0) {
			return 0;
		} else {
			return pys[0].charAt(0);
		}
	}

	/**
	 * 返回对应的汉语拼音字符串，多音字返回第一个
	 * 
	 * @param c
	 * @return
	 */
	public static String toHanyuPinyinString(char c) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		try {
			String[] pys = PinyinHelper.toHanyuPinyinStringArray(c, format);
			if (pys != null && pys.length >0) {
				return pys[0];
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 返回对应的汉语拼音字符串,首写字母大写，多音字返回第一个
	 * 
	 * @param c
	 * @return
	 */
	public static String toHanyuPinyinCapitalString(char c) {
		String py = toHanyuPinyinString(c);
		if (py.length() > 0) {
			return py.substring(0, 1).toUpperCase() + py.substring(1);
		} else {
			return py;
		}
	}

	/**
	 * 返回对应的汉语拼音字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String toHanyuPinyinString(String str) {
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, l = str.length(); i < l; i++) {
				char c = str.charAt(i);
				if (!isChineseChar(c)) {
					sb.append(c);
				} else {
					sb.append(toHanyuPinyinString(c));
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 返回对应的汉语拼音字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String toHanyuPinyinCapitalString(String str) {
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, l = str.length(); i < l; i++) {
				char c = str.charAt(i);
				if (!isChineseChar(c)) {
					sb.append(c);
				} else {
					sb.append(toHanyuPinyinCapitalString(c));
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * 返回对应的汉语拼音字符串,忽略非中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static String toHanyuPinyinCapitalStringIgnoreNonChinese(String str) {
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, l = str.length(); i < l; i++) {
				char c = str.charAt(i);
				if (isChineseChar(c)) {
					sb.append(toHanyuPinyinCapitalString(c));
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 返回 {@code word} 中所有汉字的首字母
	 * @param word
	 * @return
	 */
	public static String getHanyuPinyinAcronym(String word) {
		if (StringUtils.isEmpty(word)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, l = word.length(); i < l; i++) {
			char c = word.charAt(i);
			if (!isChineseChar(c)) {
				sb.append(c);
			} else {
				String s = toHanyuPinyinString(c);
				if (s.length() > 0) {
					sb.append(s.charAt(0));
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 返回 {@code word} 中所有汉字的首字母, 忽略所有非中文字符
	 * @param word
	 * @return
	 */
	public static String getHanyuPinyinAcronymIgnoreNonChinese(String word) {
		if (StringUtils.isEmpty(word)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, l = word.length(); i < l; i++) {
			char c = word.charAt(i);
			if (isChineseChar(c)) {
				String s = toHanyuPinyinString(c);
				if (s.length() > 0) {
					sb.append(s.charAt(0));
				}
			}
		}
		return sb.toString();
	}

}
