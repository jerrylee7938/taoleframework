/*
 * @(#)NotSupportedException.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.exception;

/**
 * Class: NotSupportedException Remark: TODO
 * 
 * @author: 
 */
public class NotSupportedException extends SystemException {

	private static final long serialVersionUID = -1670030191206583109L;

	public NotSupportedException(String msg) {
		super(msg);
	}

	public NotSupportedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
