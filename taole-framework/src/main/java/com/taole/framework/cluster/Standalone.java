/**
 * Project:taole-heaframework
 * File:Global.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.framework.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wch
 * @date 2013-8-8
 * @version $Id: Standalone.java 5 2014-01-15 13:55:28Z yedf $
 */
public class Standalone extends Global {

	Map<String, Object> map = new HashMap<String, Object>();

	Map<String, Node> nodes = new HashMap<String, Node>();

	public Object get(String key) {
		return map.get(key);
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public void remove(String key) {
		map.remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(Node node) {
		super.register(node);
		nodes.put(node.getName(), node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Node> getNodeList() {
		return new ArrayList<Node>(nodes.values());
	}

	private Map<String, Boolean> locks = new ConcurrentHashMap<String, Boolean>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean lock(String name) {
		if (islock(name)) {
			return false;
		} else {
			locks.put(name, Boolean.TRUE);
			return true;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean unlock(String name) {
		try {
			locks.put(name, Boolean.FALSE);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean islock(String name) {
		return locks.get(name) == Boolean.TRUE;
	}
}
