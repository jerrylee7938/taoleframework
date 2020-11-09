/**
 * Project:taole-heaframework
 * File:CollectionUtilsTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

/**
 * @author Rory
 * @date Aug 29, 2012
 * @version $Id: CollectionUtilsTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class CollectionUtilsTest {

	@Test
	public void testUnion() throws Exception {
		List<String> aList = Arrays.asList("1111", "2222", "3333");
		List<String> bList = Arrays.asList("4444", "2222", "5555");
		@SuppressWarnings("unchecked")
		Collection<String> collection  = CollectionUtils.union(aList, bList);
		assertEquals(5, collection.size());
		assertTrue(collection.contains("1111"));
		assertTrue(collection.contains("2222"));
		assertTrue(collection.contains("3333"));
		assertTrue(collection.contains("4444"));
		assertTrue(collection.contains("5555"));
	}
	
}
