/**
 * Project:HEAFramework 2.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since May 17, 2010 10:06:18 AM
 * @version $Id: EmailValidator.java 5 2014-01-15 13:55:28Z yedf $
 */
public class EmailValidator {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);


	/**
	 * Validate email with regular expression
	 * 
	 * @param email
	 *            email for validation
	 * @return true valid email, false invalid email
	 */
	public static boolean validate(final String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

}
