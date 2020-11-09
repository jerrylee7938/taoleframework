/**
 * Project:taole-heaframework
 * File:RestClientException.java
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.client;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Rory
 * @date Dec 13, 2011
 * @version $Id: RestClientException.java 5 2014-01-15 13:55:28Z yedf $
 */
public class RestClientException extends NestableException {

	private static final long serialVersionUID = 4720147866562045048L;
	
	private int code;
	
	private String description;
	
	private String response; //错误时对应服务器的返回内容

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RestClientException() {
		super();
	}

	public RestClientException(int code, String msg, Throwable cause) {
		super(code + ":" + msg, cause);
	}

	public RestClientException(int code, String msg) {
		super(code + ":" + msg);
	}
	
	public RestClientException(int code, String msg, String response) {
		super(code + ":" + msg);
		this.response = response;
	}

	public RestClientException(Throwable cause) {
		super(cause);
	}

}
