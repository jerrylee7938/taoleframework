package com.taole.framework.cache;

import java.util.ArrayList;
import java.util.Collection;

public class NoneCacheFactory implements CacheFactory {

	public Cache<Object, Object> getCache(String name) {
		return new NoneCache<Object, Object>();
	}

	public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
		return new NoneCache<K, V>();
	}

	public Collection<Cache<?, ?>> allCaches() {
		return new ArrayList<Cache<?, ?>>();
	}
	
	public static class NoneCache<K, V> implements Cache<K, V> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public V get(K key) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void put(K key, V value) {
			// Do noting
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove(K key) {
			// Do noting
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Collection<K> keys() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Collection<V> values() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isIn(K key) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getSize() {
			return 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clear() {
			// Do noting
		}
		
	}
}
