/**
 * Project:lenchy-loms
 * File:Context.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.install;

import java.io.Writer;

import org.springframework.context.ApplicationContext;

/**
 * @author wch
 * @date 2013-10-21
 * @version $Id: Context.java 16 2014-01-17 17:58:24Z yedf $
 */
public class Context {

	ApplicationContext applicationContext;
	Writer out;

	public Context(ApplicationContext applicationContext, Writer out) {
		this.applicationContext = applicationContext;
		this.out = out;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Writer getWriter() {
		return out;
	}
}
