/*
 * @(#)ApplicationEventListener.java 0.1 2009-10-10
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.events;

import java.util.List;

import javax.annotation.PostConstruct;

public abstract class EventListener {
	
	protected List<EventTarget> eventTargets;
	protected boolean autoListen = true;

	public void setEventTargets(List<EventTarget> eventTargets) {
		this.eventTargets = eventTargets;
	}

	public void setAutoListen(boolean autoListen) {
		this.autoListen = autoListen;
	}
	
	public void startListen () {
		if (eventTargets != null) {
			for (EventTarget target: eventTargets) {
				target.addEventListener(this);
			}
		}
	}
	
	public void stopListen () {
		if (eventTargets != null) {
			for (EventTarget target: eventTargets) {
				target.removeEventListener(this);
			}
		}
	}

	@PostConstruct
	public void init() throws Exception {
		if (autoListen) {
			startListen();
		}
	}

	/**
	 * @param event
	 */
	public abstract void handleEvent(EventObject event);

}
