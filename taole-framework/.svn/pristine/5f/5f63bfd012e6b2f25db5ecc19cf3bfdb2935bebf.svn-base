/*
 * @(#)ProtocalHandler.java 0.1 May 20, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.protocol;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProtocolHandler<T> implements ProtocolHandler<T> {

	Log logger = LogFactory.getLog(getClass());

	@Autowired(required = true)
	private ProtocolRegistry protocolRegistry;

	@PostConstruct
	public void init() {
		protocolRegistry.register(this);
		logger.debug("Protocol hanlder for '" + this.getProtocol() + "' registered!");
	}

}
