package com.taole.framework.cache;

import java.io.Serializable;
import java.util.Collection;

public interface Cache<K, V> extends Serializable {

	/**
	 * 根据 key 取缓存中的值。
	 * @param key
	 * @return 取到返回对应的值，否则返回空。
	 */
	V get(K key);

	/**
	 * 将对象 value 以 key 放入缓存中。
	 * @param key
	 * @param value
	 */
	void put(K key, V value);

	/**
	 * 将缓存 key 对象的对象移除。
	 * @param key
	 */
	void remove(K key);

	/**
	 * 取出该缓存中所有的key。
	 * @return
	 */
	Collection<K> keys();

	/**
	 * 取出该缓存中所有的值。
	 * @return
	 */
	Collection<V> values();

	/**
	 * 判断该 key 是否存在缓存对象。
	 * @param key
	 * @return
	 */
	boolean isIn(K key);

	/**
	 * 取出该缓存的大小。
	 * @return
	 */
	int getSize();

	/**
	 * 清空该缓存对象。
	 */
	void clear();

}
