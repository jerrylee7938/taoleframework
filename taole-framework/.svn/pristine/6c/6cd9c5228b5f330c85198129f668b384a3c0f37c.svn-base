/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.util.EmailValidator;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 27, 2011 1:38:37 PM
 * @version $Id: EmailValidatorTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class EmailValidatorTest {

	@Test
	public void testValidate() throws Exception {
		assertFalse(EmailValidator.validate("tester@11.22"));
		assertTrue(EmailValidator.validate("tester@51.net"));
		assertTrue(EmailValidator.validate("mr.tester@gmail.com"));
	}
}
