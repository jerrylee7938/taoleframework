/**
 * Project:taole-heaframework
 * File:PinyinUtilsTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.util.PinyinUtils;

/**
 * @author Rory
 * @date Mar 20, 2012
 * @version $Id: PinyinUtilsTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class PinyinUtilsTest {

	@Test
	public void testParseWithSign() throws Exception {
		String text = "北京同一源（上海）律师事务所";
		assertEquals("bjtyyshlssws", PinyinUtils.getHanyuPinyinAcronym(text));
		
		text = "北京同一源(上海)律师事务所";
		assertEquals("bjtyy(sh)lssws", PinyinUtils.getHanyuPinyinAcronym(text));
		
		text = "北京同一源xx(上海)律师yy事务所";
		assertEquals("bjtyyxx(sh)lsyysws", PinyinUtils.getHanyuPinyinAcronym(text));
	}
	
	@Test
	public void testParseIgnoreChinese() throws Exception {
		String text = "北京同一源(上海)律师事务所";
		assertEquals("bjtyyshlssws", PinyinUtils.getHanyuPinyinAcronymIgnoreNonChinese(text));
		
		text = "北京同一源xx(上海)yy律师事务所";
		assertEquals("bjtyyshlssws", PinyinUtils.getHanyuPinyinAcronymIgnoreNonChinese(text));
	}
}
