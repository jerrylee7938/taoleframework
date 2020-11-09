/**
 * Project:taole-heaframework
 * File:PatternTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Rory
 * @date Jun 14, 2012
 * @version $Id: PatternTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class PatternTest {

	@Test
	public void testQuote() throws Exception {
		String keyword = "\\有(在.)[和]+国^这?轻要人{以经要}";
		String quotedString = Pattern.quote(keyword);
		assertNotNull(quotedString);
		assertFalse(Pattern.matches(quotedString, "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + quotedString + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote(".") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("+") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("(") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote(")") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("?") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("{") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("}") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
		assertTrue(Pattern.matches("^.*" + Pattern.quote("\\") + ".*$", "xx\\有(在.)[和]+国^这?轻要人{以经要}dd"));
	}
}
