package com.taole.framework.cache;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadCacheFactory implements CacheFactory {

	static Log log = LogFactory.getLog(ThreadCacheFactory.class);

	static ThreadLocal<CacheFactory> tl = new ThreadLocal<CacheFactory>();

	static CacheFactory noCacheFactory = new NoneCacheFactory();

	public static void initCacheFactory (CacheFactory cacheFactory) {
		log.debug("Init cache factoy for thread:" + Thread.currentThread());
		tl.set(cacheFactory);
	}

	public static void destroyCacheFactory () {
		log.debug("Destroy cache factoy for thread:" + Thread.currentThread());
		tl.remove();
	}

	public Cache<Object, Object> getCache(String name) {
		CacheFactory cacheFactory = (CacheFactory) tl.get();
		if (cacheFactory != null) {
			return cacheFactory.getCache(name);
		} else {
			return null;
		}
	}

	public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
		CacheFactory cacheFactory = (CacheFactory) tl.get();
		if (cacheFactory != null) {
			return cacheFactory.getCache(name, keyType, valueType);
		} else {
			return null;
		}
	}

	public Collection<Cache<?, ?>> allCaches() {
		CacheFactory cacheFactory = (CacheFactory) tl.get();
		if (cacheFactory != null) {
			return cacheFactory.allCaches();
		} else {
			return null;
		}
	}
}
