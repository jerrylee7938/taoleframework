/**
 * Project:taole-heaframework
 * File:CloneTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

/**
 * @author Rory
 * @date Mar 28, 2012
 * @version $Id: CloneTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class CloneTest {
	
	@Test
	public void testClone() throws Exception {
		A a = new A();
		a.setCreateDate(new Date());
		a.setAge(20);
		a.setName("Tester");
		a.addAttribute("foo", "bar");
		a.getAlist().add("hello");
		
		A b = a.clone();
		assertEquals(20, b.getAge());
		assertEquals("Tester", b.getName());
		b.setName("a Tester");
		b.setAge(25);
		b.addAttribute("foo", "another bar");
		b.getCreateDate().setTime(b.getCreateDate().getTime() + 1000 * 60);
		b.getAlist().clear();
		b.getAlist().add("world");
		
		assertEquals("Tester", a.getName());
		assertEquals(20, a.getAge());
		assertEquals("bar", a.getAttributes().get("foo"));
		assertEquals(1000 * 60, b.getCreateDate().getTime() - a.getCreateDate().getTime());
		assertEquals("hello", a.getAlist().get(0));
		
		A aa = new A();
		
		aa.clone();
		
	}

	private static class A implements Cloneable {
		private Date createDate;
		
		private String name;
		
		private int age;
		
		private Map<String, String> attributes = new HashMap<String, String>();
		
		private List<String> alist = new ArrayList<String>();

		public List<String> getAlist() {
			return alist;
		}

		public void setAlist(List<String> alist) {
			this.alist = alist;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Map<String, String> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}
		
		public void addAttribute(String key, String value) {
			getAttributes().put(key, value);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected A clone() throws CloneNotSupportedException {
			A cloneA = (A)super.clone();
			if (cloneA.getCreateDate() != null) {
				cloneA.setCreateDate((Date)cloneA.getCreateDate().clone());
			}
			cloneA.setAttributes(cloneAttributes());
			List<String> cloneList = new ArrayList<String>();
			cloneList.addAll(cloneA.getAlist());
			cloneA.setAlist(cloneList);
			return cloneA;
		}
		
		public Map<String, String> cloneAttributes() {
			Map<String, String> cloneAttributes = Maps.newHashMap();
			cloneAttributes.putAll(getAttributes());
			return cloneAttributes;
		}
	}
}
