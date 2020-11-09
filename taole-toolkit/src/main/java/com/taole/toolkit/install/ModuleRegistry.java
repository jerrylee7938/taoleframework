/**
 * Project:taole-toolkit
 * File:ModuleRegistry.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.install;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wch
 * @date 2013-11-21
 * @version $Id: ModuleRegistry.java 16 2014-01-17 17:58:24Z yedf $
 */
public class ModuleRegistry {

	Map<String, Module> table = new HashMap<String, Module>();

	public void register(String id, Module m) {
		table.put(id, m);
	}

	public Module getModule(String id) {
		return table.get(id);
	}

	public List<String> getIds() {
		return new ArrayList<String>(table.keySet());
	}

}
