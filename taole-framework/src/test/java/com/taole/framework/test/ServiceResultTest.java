/**
 * Project:taole-heaframework
 * File:ServiceResultTest.java
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.service.ServiceResult;
import com.taole.framework.test.domain.Student;

/**
 * @author Rory
 * @date Nov 23, 2011
 * @version $Id: ServiceResultTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ServiceResultTest {

	@Test
	public void testClone() throws Exception {
		Student student = new Student();
		student.setGender(Student.Gender.Female);
		ServiceResult result = new ServiceResult(1, student);
		Object obj = null;
		try {
			obj = result.cloneResultObject(result, true);
		} catch (Exception e) {
			fail();
		}
		System.out.println(obj);
	}
}
