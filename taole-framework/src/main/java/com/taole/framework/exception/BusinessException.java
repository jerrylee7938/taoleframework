/*
 * @(#)BusinessException.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.exception;

/**
 * Class: BusinessException Remark: TODO
 * 
 * @author: 
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -8466437605864686664L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
