/*
 * @(#)SetupInitializer.java 0.1 2009-12-16
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.system.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.setup.Initializer;
import com.taole.toolkit.system.SystemInfoManager;

public class SystemInitializer extends Initializer {

	@Autowired
	private SystemInfoManager systemInfoManager;

	public void initialize() {
		systemInfoManager.getCurrentSystemInfo();
		systemInfoManager.updateCurrentSystemInfo();
	}


}
