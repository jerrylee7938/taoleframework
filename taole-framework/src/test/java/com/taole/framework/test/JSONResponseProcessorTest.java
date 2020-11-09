/**
 * Project:taole-heaframework
 * File:JSONResponseProcessorTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Maps;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.rest.ResultSet.Fetcher;
import com.taole.framework.rest.ResultSet.Row;
import com.taole.framework.rest.processor.JSONResponseProcessor;
import com.taole.framework.test.domain.Student;

/**
 * @author Rory
 * @date May 10, 2012
 * @version $Id: JSONResponseProcessorTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class JSONResponseProcessorTest extends AbstractRestTest {

	@Test
	public void testProcess() throws Exception {
		JSONResponseProcessor jp = new JSONResponseProcessor();
		Map<String, ResultSet> map = Maps.newHashMap();
		List<Student> students = new ArrayList<Student>();
		Student student = new Student();
		student.setName("李四");
		students.add(student);
		ResultSet rs = new ResultSet(students);
		rs.addField("father", new Fetcher<String>() {
			@Override
			public String fetch(Row row, Object propName) {
				String name = (String) row.getValue("name");
				return name + "'s father";
			}
		});
		map.put("Test", rs);
		Object result = jp.process(map);
		@SuppressWarnings("unchecked")
		ResponseEntity<byte[]> entity = (ResponseEntity<byte[]>) result;
		JSONObject json = new JSONObject(new String(entity.getBody(), Charset.forName("UTF-8")));
		assertTrue(json.has("Test"));
		JSONArray array = json.getJSONArray("Test");
		JSONObject item =  (JSONObject) array.get(0);
		assertEquals("李四", item.get("name"));
		assertEquals("李四's father", item.get("father"));
		assertNotNull(result);
	}
}
