package com.taole.framework.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class MapCacheFactoryImpl implements CacheFactory {

	private Map<String, MapCacheImpl<?, ?>> pool = Maps.newConcurrentMap();

	@SuppressWarnings("unchecked")
	public Cache<Object, Object> getCache(String name) {
		synchronized (pool) {
			MapCacheImpl<Object, Object> cache = (MapCacheImpl<Object, Object>) pool.get(name);
			if (cache == null) {
				cache = new MapCacheImpl<Object, Object>();
				pool.put(name, cache);
			}
			return cache;
		}
	}

	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
		synchronized (pool) {
			MapCacheImpl<K, V> cache = (MapCacheImpl<K, V>) pool.get(name);
			if (cache == null) {
				cache = new MapCacheImpl<K, V>();
				pool.put(name, cache);
			}
			return cache;
		}
	}

	public Collection<Cache<?, ?>> allCaches() {
		return new ArrayList<Cache<?, ?>>(pool.values());
	}
}
