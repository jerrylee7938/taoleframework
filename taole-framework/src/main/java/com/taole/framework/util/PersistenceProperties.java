/*
 * @(#)PersistenceProperties.java 0.1 2010-3-24
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;


public class PersistenceProperties  {

	private String storePath = "tk-persistence.properties";

	/**
	 * @return Returns the storeDir.
	 */
	public String getStorePath() {
		return storePath;
	}

	/**
	 * @param storeDir
	 *            The storeDir to set.
	 */
	public void setStorePath(String arg0) {
		this.storePath = arg0;
	}

	private String fileSystemRoot;

	/**
	 * @return the fileSystemRoot
	 */
	public String getFileSystemRoot() {
		return fileSystemRoot;
	}

	/**
	 * @param fileSystemRoot
	 *            the fileSystemRoot to set
	 */
	public void setFileSystemRoot(String fileSystemRoot) {
		this.fileSystemRoot = fileSystemRoot;
	}

	public String getFilePath() {
		return getStorePath().replaceAll("\\%\\{filesystem-root\\}",
				getFileSystemRoot());
	}

	private Properties properties = null;

	protected Properties getProperties() {
		if (properties == null) {
			properties = load();
		}
		return properties;
	}

	protected synchronized Properties load() {
    	Properties props = new Properties();
    	try {
    		props.load(new FileReader(getFilePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
    }

	protected synchronized void save() {
		try {
			getProperties().store(new FileWriter(getFilePath())
				, "saved by " + this.getClass().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void setProperty(String key, String value) {
		getProperties().setProperty(key, value);
		save();
	}

	public synchronized Set<String> getPropertyNames() {
		return getProperties().stringPropertyNames();
	}
	
	
}
