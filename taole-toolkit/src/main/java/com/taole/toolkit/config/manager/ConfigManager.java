/*
 * @(#)ConfigDataManager.java 0.1 Jan 21, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.config.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.taole.toolkit.config.dao.ConfigDao;

/**
 * Class: ConfigDataManager Remark:
 * @author: wch
 */
@Transactional(readOnly = true)
public class ConfigManager {

	@Autowired
	private ConfigDao configDao;

	/**
	 * @param key
	 * @return
	 * @see com.taole.toolkit.config.dao.ConfigDao#getConfig(java.lang.String)
	 */
	public <T> T getConfig(String key, Class<T> clz) {
		return configDao.getConfig(key, clz);
	}

	/**
	 * @return
	 * @see com.taole.toolkit.config.dao.ConfigDao#getConfigKeys()
	 */
	public Collection<String> getConfigKeys() {
		return configDao.getConfigKeys();
	}

	/**
	 * @param key
	 * @param value
	 * @see com.taole.toolkit.config.dao.ConfigDao#setConfig(java.lang.String, java.lang.Object)
	 */
	@Transactional
	public void setConfig(String key, Object value) {
		configDao.setConfig(key, value);
	}

	/**
	 * @param key
	 * @see com.taole.toolkit.config.dao.ConfigDao#removeConfig(java.lang.String)
	 */
	@Transactional
	public void removeConfig(String key) {
		configDao.removeConfig(key);
	}

}
