/*
 * @(#)Base64Util.java 0.1 Apr 11, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public abstract class Base64Util {

	public static String encodeString(String s) {
		if (s != null) {
			try {
				return new String(Base64.encodeBase64(s.getBytes("utf-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static String decodeString(String s) {
		if (s != null) {
			try {
				return new String(Base64.decodeBase64(s.getBytes()), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
