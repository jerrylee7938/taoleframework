/**
 * Project:taole-heaframework
 * File:Global.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.framework.cluster;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * @author wch
 * @date 2013-8-8
 * @version $Id: Global.java 5 2014-01-15 13:55:28Z yedf $
 */
public abstract class Global {

	private static Global instance;
	private static Node local;

	public static Global getInstance() {
		return Global.instance;
	}

	public static Node getLocal() {
		return Global.local;
	}

	@PostConstruct
	public void init() {
		Global.instance = this;
	}

	public abstract Object get(String key);

	public abstract void put(String key, Object value);

	public abstract void remove(String key);

	public abstract List<Node> getNodeList();

	public abstract boolean lock(String name);

	public abstract boolean unlock(String name);

	public abstract boolean islock(String name);

	public void register(Node node) {
		Global.local = node;
	}

}
