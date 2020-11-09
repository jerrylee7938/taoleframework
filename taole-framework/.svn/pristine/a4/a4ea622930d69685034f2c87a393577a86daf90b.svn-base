/**
 * Project:taole-heaframework
 * File:ClassUtilsTest.java 
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.taole.framework.util.ClassUtils;

/**
 * @author rory
 * @date Sep 8, 2011
 * @version $Id: ClassUtilsTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ClassUtilsTest {

	@Test
	public void testGetClassNames() throws Exception {
		List<String> classNames = ClassUtils.getClassNamesFromPackage("com.taole.framework.test");
		assertTrue(classNames.contains("com.taole.framework.test.ClassUtilsTest"));
		
		classNames = ClassUtils.getClassNamesFromPackage("org.springframework.util");
		assertTrue(classNames.contains("org.springframework.util.ResourceUtils"));
		
		classNames = ClassUtils.getClassNamesFromPackage("com.taole");
		assertEquals(0, classNames.size());
		
		classNames = ClassUtils.getClassNamesFromPackage("org.springframework");
		assertEquals(0, classNames.size());
		
	}
}
