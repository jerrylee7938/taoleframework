/*
 * @(#)ApplicationEventListener.java 0.1 2009-10-10
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.events;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;

import com.taole.framework.annotation.AnnotationUtils;

public abstract class AutoDispatchEventListener extends EventListener implements SmartLifecycle {

	@Autowired
	ApplicationContext applicationContext;
	Map<String, Set<Method>> handlers = new HashMap<String, Set<Method>>();
	boolean running = false;

	public void handleEvent(EventObject event) {
		String key = event.getTarget() + "#" + event.getType().toLowerCase();
		Set<Method> list = handlers.get(key);
		if (list == null) {
			list = new LinkedHashSet<Method>();
		}
		Set<Method> list0 = handlers.get(event.getTarget() + "#*");
		if (list0 != null) {
			for (Method method : list0) {
				if (!list.contains(method)) {
					list.add(method);
				}
			}
		}
		for (Method method : list) {
			boolean isAccesiable = method.isAccessible();
			try {
				if (!isAccesiable) {
					method.setAccessible(true);
				}
				method.invoke(this, event);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (!isAccesiable) {
					method.setAccessible(false);
				}
			}
		}
	}

	@Override
	public void start() {
		Map<String, EventTarget> targets = new HashMap<String, EventTarget>();
		Method[] methods = AnnotationUtils.getAnnotationMethods(getClass(), EventHandler.class);
		EventHandler teh = this.getClass().getAnnotation(EventHandler.class);
		for (Method method : methods) {
			EventHandler meh = method.getAnnotation(EventHandler.class);
			String target = meh.target();
			if (target.isEmpty() && teh != null) {
				target = teh.target();
			}
			if (target.isEmpty()) {
				continue;
			}
			Object et = applicationContext.getBean(target);
			if (et instanceof EventTarget) {
				if (!targets.containsKey(target)) {
					((EventTarget) et).addEventListener(this);
					targets.put(target, (EventTarget) et);
				}
				for (String type : meh.types()) {
					String key = et + "#" + type.toLowerCase();
					Set<Method> list = (Set<Method>) handlers.get(key);
					if (list == null) {
						list = new LinkedHashSet<Method>();
						handlers.put(key, list);
					}
					list.add(method);
				}
			}
		}
		running = true;
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
		EventHandler teh = this.getClass().getAnnotation(EventHandler.class);
		return teh == null ? 0 : teh.order();
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		running = false;
		callback.run();
	}

}
