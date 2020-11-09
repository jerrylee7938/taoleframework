/*
 * @(#)ApplicationSetup.java 0.1 2009-7-28
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taole.framework.setup.Initializer;

public class AppSetup {
	
	protected Log logger = LogFactory.getLog(this.getClass());

	List<Initializer> initers;

	public void setIniters(List<Initializer> initers) {
		this.initers = initers;
	}
	
	public void setup () {
		logger.info("Start setup ...");
		if (initers != null) {
			for (int i = 0; i < initers.size(); i++) {
				initers.get(i).initialize();
			}
		}
	}
}
