/**
 * Project:lenchy-loms
 * File:Module.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.install;

/**
 * @author wch
 * @date 2013-10-21
 * @version $Id: Module.java 16 2014-01-17 17:58:24Z yedf $
 */
public interface Module {

	public String getName();
	
	public boolean execute(Context ctx);
	
	public boolean isEnable();

}
