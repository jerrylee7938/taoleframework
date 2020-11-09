/**
 * Project:taole-heaframework
 * File:MemcachedCacheManager.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.framework.cache;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import com.danga.MemCached.MemCachedClient;

/**
 * @author wch
 * @date 2013-8-8
 * @version $Id: MemcachedCacheManager.java 5 2014-01-15 13:55:28Z yedf $
 */
public class MemcachedCacheManager extends AbstractCacheManager {

	@Autowired
	private MemCachedClient client;

	public MemCachedClient getClient() {
		return client;
	}

	public void setClient(MemCachedClient client) {
		this.client = client;
	}

	protected Collection<? extends org.springframework.cache.Cache> loadCaches() {
		Collection<String> names = getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.size());
		for (String name : names) {
			caches.add(getCache(name));
		}
		return caches;
	}

	public org.springframework.cache.Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			cache = new MemcachedCache(name, client);
			addCache(cache);
		}
		return cache;
	}

}
