package com.taole.toolkit.config.dao;

import java.util.Collection;

public interface ConfigDao {

	<T> T getConfig(String key, Class<T> t);

	void setConfig(String key, Object value);

	void removeConfig(String key);

	Collection<String> getConfigKeys();
}
