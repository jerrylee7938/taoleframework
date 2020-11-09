/**
 * Project:taole-heaframework
 * File:MemcachedCache.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.framework.cache;

import java.util.Date;
import java.util.concurrent.Callable;

import org.springframework.cache.*;
import org.springframework.cache.support.SimpleValueWrapper;

import com.danga.MemCached.MemCachedClient;

/**
 * @author wch
 * @date 2013-8-8
 * @version $Id: MemcachedCache.java 9632 2017-04-28 07:51:33Z chengjun $
 */
public class MemcachedCache implements org.springframework.cache.Cache {

	private MemCachedClient client;

	private String name;

	public static final Date EXPIRY = new Date(Long.MAX_VALUE);

	public MemcachedCache(String name, MemCachedClient client) {
		this.name = name;
		this.client = client;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return this.client;
	}

	public ValueWrapper get(Object key) {
		Object value = this.client.get(toCacheKey(key));
		return (value != null ? new SimpleValueWrapper(value) : null);
	}

	public void put(Object key, Object value) {
		this.client.set(toCacheKey(key), value, EXPIRY);
	}

	public void evict(Object key) {
		this.client.delete(toCacheKey(key));
	}

	public void clear() {
		client.flushAll();
	}

	public void setClient(MemCachedClient client) {
		this.client = client;
	}

	public MemCachedClient getClient() {
		return client;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 转换成memcached保存的key
	 * 
	 * @param key
	 * @return
	 */
	public String toCacheKey(Object key) {
		return "___cache." + name + "." + String.valueOf(key);
	}

	@Override
	public <T> T get(Object o, Class<T> aClass) {
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object o, Object o1) {
		return null;
	}
}
