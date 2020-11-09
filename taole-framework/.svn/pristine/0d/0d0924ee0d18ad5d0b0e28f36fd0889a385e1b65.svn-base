/**
 * Project:taole-heaframework
 * File:ArrayListTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * @author Rory
 * @date Aug 23, 2012
 * @version $Id: ArrayListTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ArrayListTest {

	static List<Pen> pens;
	
	static {
		pens = new ArrayList<Pen>();
		pens.add(new Pen("pen001"));
		pens.add(new Pen("pen002"));
		pens.add(new Pen("pen003", Pen.Type.Big));
	}
	
	@Test
	public void testDuplicated() throws Exception {
		List<Pen> all = new ArrayList<Pen>();
		assertEquals(0, all.size());
		all.addAll(pens);
		assertEquals(3, all.size());
		all.addAll(pens);
		assertEquals(6, all.size());
		all.addAll(pens);
		assertEquals(9, all.size());
		
		Set<Pen> sets = new HashSet<Pen>();
		sets.addAll(pens);
		assertEquals(3, sets.size());
		sets.addAll(pens);
		assertEquals(3, sets.size());
		sets.addAll(pens);
		assertEquals(3, sets.size());
	}
	
	public static class Pen {
		
		private String name;
		
		private Type type;
		
		public Pen(String name) {
			this.name = name;
		}
		
		public Pen(String name, Type type) {
			this.name = name;
			this.type = type;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public static enum Type {
			Big
		}
	}
}
