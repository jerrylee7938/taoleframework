package com.taole.framework.cache;

import java.util.Collection;

public interface CacheFactory {

	Cache<Object, Object> getCache(String name);

	<K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType);

	Collection<Cache<?, ?>> allCaches();
}
