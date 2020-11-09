/*
 * @(#)ServiceSessionFactory.java 0.1 Apr 22, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.rest;

/**
 * Class <code>ServiceSessionFactory</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Apr 22, 2008
 */
public class RestSessionFactory {

	// ~ Static fields ======================================================================================

	public static final ThreadLocal<RestContext> session = new ThreadLocal<RestContext>();

	// ~ Methods ============================================================================================

	/**
	 * 获得当前ServiceContext
	 * 
	 * @return
	 */
	public static RestContext getCurrentContext() {
		return (RestContext) session.get();
	}

	/**
	 * 设置当前ServiceContext
	 * 
	 * @param context
	 */
	public static void initCurrentContext(RestContext context) {
		session.set(context);
	}

	public static void destroyCurrentContext() {
		session.remove();
	}

}
