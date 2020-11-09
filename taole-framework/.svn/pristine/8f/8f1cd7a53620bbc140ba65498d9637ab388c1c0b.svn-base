package com.taole.framework.cache;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class MapCacheImpl<K, V> implements Cache<K, V> {

	private Map<K, V> map = Maps.newConcurrentMap();

	public V get(K key) {
		synchronized (map) {
			return map.get(key);
		}
	}

	public void put(K key, V value) {
		synchronized (map) {
			map.put(key, value);
		}
	}

	public void clear() {
		synchronized (map) {
			map.clear();
		}
	}

	public void remove(K key) {
		synchronized (map) {
			map.remove(key);
		}
	}

	public int getSize() {
		return map.size();
	}

	public boolean isIn(K key) {
		return map.containsKey(key);
	}

	public Collection<K> keys() {
		return map.keySet();
	}
	
	public Collection<V> values() {
		return map.values();
	}
	
}
