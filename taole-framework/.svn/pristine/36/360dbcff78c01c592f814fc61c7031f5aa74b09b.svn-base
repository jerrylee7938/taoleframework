/*
 * @(#)Setup.java 0.1 2009-7-27
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.setup;

import org.springframework.context.SmartLifecycle;

public abstract class Initializer implements SmartLifecycle {

	public abstract void initialize();

	// 越小越在前面执行
	public static final int DEFAULT_PHASE = 10000;

	boolean running = false;

	@Override
	public void start() {
		running = true;
		initialize();
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public int getPhase() {
		return DEFAULT_PHASE;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		this.stop();
		callback.run();
	}

}
