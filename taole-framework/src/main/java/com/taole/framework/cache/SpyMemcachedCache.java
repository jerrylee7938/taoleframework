package com.taole.framework.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

public class SpyMemcachedCache<K, V> implements Cache<K, V> {

	private MemcachedClient client;
	private String name;

	public SpyMemcachedCache(String name, MemcachedClient client) {
		this.name = name;
		this.client = client;
	}

	public String getCacheName() {
		return "___cache.all." + name + "___";
	}

	@SuppressWarnings("unchecked")
	protected Map<K, Long> getCacheMap() {
		String cacheName = getCacheName();
		Map<K, Long> map = (Map<K, Long>) client.get(cacheName);
		if (map == null) {
			map = new HashMap<K, Long>(); 
			client.add(cacheName, 0, map);
		}
		return map;
	}

	protected void saveCacheMap(Map<K, Long> map) {
		client.replace(getCacheName(), 0, map);
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		return (V) client.get(String.valueOf(key));
	}

	public void put(K key, V value) {
		client.add(String.valueOf(key), 0, value);
		Map<K, Long> map = getCacheMap();
		map.put(key, System.currentTimeMillis());
		saveCacheMap(map);
	}

	public void clear() {
		Map<K, Long> map = getCacheMap();
		for (K key : map.keySet()) {
			client.delete(String.valueOf(key));
		}
		map.clear();
		saveCacheMap(map);
	}

	public void remove(K key) {
		client.delete(String.valueOf(key));
		Map<K, Long> map = getCacheMap();
		map.remove(key);
		saveCacheMap(map);
	}

	public int getSize() {
		return getCacheMap().size();
	}

	public boolean isIn(K key) {
		return getCacheMap().containsKey(String.valueOf(key));
	}

	public Collection<K> keys() {
		return getCacheMap().keySet();
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		List<V> list = new ArrayList<V>();
		Map<K, Long> map = getCacheMap();
		for (K key : map.keySet()) {
			V value = (V) client.get(String.valueOf(key));
			list.add(value);
		}
		return list;
	}

}
