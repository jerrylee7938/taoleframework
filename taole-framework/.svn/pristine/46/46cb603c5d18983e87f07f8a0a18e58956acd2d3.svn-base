package com.taole.framework.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.cache.memcached.SpyMemcachedClientSource;

public class SpyMemcachedCacheFactory implements CacheFactory {

	@Autowired
	private SpyMemcachedClientSource clientSource;
	
	private Map<String, SpyMemcachedCache<?, ?>> pool = new HashMap<String, SpyMemcachedCache<?, ?>>();

	@SuppressWarnings("unchecked")
	public Cache<Object, Object> getCache(String name) {
		synchronized (pool) {
			SpyMemcachedCache<Object, Object> cache = (SpyMemcachedCache<Object, Object>) pool.get(name);
			if (cache == null) {
				try {
					MemcachedClient client = clientSource.getMemcachedClient();
					cache = new SpyMemcachedCache<Object, Object>(name, client);
					pool.put(name, cache);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			return cache;
		}
	}

	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
		synchronized (pool) {
			SpyMemcachedCache<K, V> cache = (SpyMemcachedCache<K, V>) pool.get(name);
			if (cache == null) {
				try {
					MemcachedClient client = clientSource.getMemcachedClient();
					cache = new SpyMemcachedCache<K, V>(name, client);
					pool.put(name, cache);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			return cache;
		}
	}

	public Collection<Cache<?, ?>> allCaches() {
		return new ArrayList<Cache<?, ?>>(pool.values());
	}

}
