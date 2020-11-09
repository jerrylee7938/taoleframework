/*
 * @(#)SmartAppSetup.java 0.1 2009-9-22
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taole.framework.setup.Initializer;
import com.taole.toolkit.config.manager.ConfigManager;

public class SmartAppSetup {
	
	public static final String APP_VERSION = "SYSTEM/Version";
	
	protected Log logger = LogFactory.getLog(this.getClass());

	private String version;
	
	public void setVersion(String version) {
		this.version = version;
	}

	List<Initializer> initers;

	public void setIniters(List<Initializer> initers) {
		this.initers = initers;
	}
	
	private ConfigManager configManager;
	
	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}

	public void setup () {
		logger.info("Check Current Version: " + version + " ...");
		String currVersion = configManager.getConfig(APP_VERSION, String.class);
		if (StringUtils.equals(currVersion, version)) {
			return;
		}
		logger.info("Start setup ...");
		if (initers != null) {
			for (int i = 0; i < initers.size(); i++) {
				initers.get(i).initialize();
			}
		}
		configManager.setConfig(APP_VERSION, version);
	}
}
