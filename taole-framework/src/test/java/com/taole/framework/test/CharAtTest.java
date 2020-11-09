/**
 * Project:taole-heaframework
 * File:CharAtTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @author Rory
 * @date Jul 11, 2012
 * @version $Id: CharAtTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class CharAtTest {

	public static final String PROTOCOL = "classpath:";

	@Test
	public void testCharAt() throws Exception {
		String name = "classpath:/com/taole/cms/a.ftl";
		if (name.charAt(PROTOCOL.length()) == '/') {
			name = StringUtils.replaceOnce(name, "/", "");
		}
		assertEquals("classpath:com/taole/cms/a.ftl", name);
	}
}
