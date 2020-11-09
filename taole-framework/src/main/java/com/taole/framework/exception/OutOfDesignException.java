/*
 * @(#)OutOfDesignException.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.exception;

/**
 * Class: OutOfDesignException 超出设计范围
 * 
 * @author: 
 */
public class OutOfDesignException extends SystemException {

	private static final long serialVersionUID = -1353227672342510024L;

	public OutOfDesignException(String message) {
		super(message);
	}

	public OutOfDesignException(String message, Throwable cause) {
		super(message, cause);
	}
}
