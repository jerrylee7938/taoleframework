/**
 * Project:taole-heaframework
 * File:JSONObjectRequestParametersTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONObject;
import org.junit.Test;

import com.taole.framework.rest.parser.JSONObjectRequestParameters;

/**
 * @author Rory
 * @date Mar 9, 2012
 * @version $Id: JSONObjectRequestParametersTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class JSONObjectRequestParametersTest {

	
	@Test
	public void testParseArrayParameter() throws Exception {
		String json = "{\"a\":\"xx\", \"array\":[]}";
		JSONObjectRequestParameters params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		String[] array = params.getParameter("array", String[].class);
		assertEquals(0, array.length);
		
		json = "{\"a\":\"xx\", \"array\":[\"sss\"]}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		array = params.getParameter("array", String[].class);
		assertEquals(1, array.length);
		assertEquals("sss", array[0]);
	}
	
	@Test
	public void testParseIntArrayParameter() throws Exception {
		String json = "{\"a\":\"xx\", \"array\":[]}";
		JSONObjectRequestParameters params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		String[] array = params.getParameter("array", String[].class);
		assertEquals(0, array.length);
		
		json = "{\"a\":\"xx\", \"array\":[1,2]}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		Integer[] array2 = params.getParameter("array", Integer[].class);
		assertEquals(2, array2.length);
		assertTrue(1 == array2[0]);
		assertTrue(2 == array2[1]);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testParseListParameter() throws Exception {
		String json = "{\"a\":\"xx\", \"array\":[]}";
		JSONObjectRequestParameters params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		List<String> list = params.getParameter("array", List.class);
		assertEquals(0, list.size());
		
		json = "{\"a\":\"xx\", \"array\":[\"sss\"]}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		list = params.getParameter("array", List.class);
		assertEquals(1, list.size());
		assertEquals("sss", list.get(0));
	}
	
	@Test
	public void testParseDateParameter() throws Exception {
		String json = "{\"a\":\"xx\", \"date\":\"2012-03-12T09:31:55\"}";
		JSONObjectRequestParameters params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		Date date = params.getParameter("date", Date.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		assertEquals(55, calendar.get(Calendar.SECOND));
		
		
		json = "{\"a\":\"xx\", \"date\":\"2012-03-12T09:31\"}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		
		json = "{\"a\":\"xx\", \"date\":\"2012-03-12 09:31\"}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		
		json = "{\"a\":\"xx\", \"date\":\"2012-03-12 09:31:55\"}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		assertEquals(55, calendar.get(Calendar.SECOND));
		
		json = "{\"a\":\"xx\", \"date\":\"2012-03-12 09:31\"}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		
		json = "{\"a\":\"xx\", \"date\":\"2012-03-12\"}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		
		json = "{\"a\":\"xx\", \"date\":1331515915000}";
		params = new JSONObjectRequestParameters(new JSONObject(json));
		assertEquals("xx", params.getParameter("a", String.class));
		date = null;
		date = params.getParameter("date", Date.class);
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.setTime(date);
		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(2, calendar.get(Calendar.MONTH));
		assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(31, calendar.get(Calendar.MINUTE));
		assertEquals(55, calendar.get(Calendar.SECOND));
	}
}
