package com.taole.toolkit.config.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.taole.framework.dao.hibernate.CachedHibernateDaoSupport;
import com.taole.framework.util.JSONTransformer;
import com.taole.toolkit.config.dao.ConfigDao;
import com.taole.toolkit.config.domain.Config;

@Repository
public class ConfigDaoImpl extends CachedHibernateDaoSupport<Config> implements ConfigDao {

	public ConfigDaoImpl() {
		super(Config.class);
	}
	
	public ConfigDaoImpl(Class<Config> entityClass) {
		super(Config.class);
	}

	public <T> T getConfig(String key, Class<T> clz) {
		Config config = get(key);
		if (config != null) {
			try {
				return JSONTransformer.transformJso2Pojo(new JSONObject(config.getValue()), clz);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Collection<String> getConfigKeys() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
		@SuppressWarnings("unchecked")
		List<Config> configs = (List<Config>) getHibernateTemplate().findByCriteria(criteria);
		List<String> list = new ArrayList<String>();
		for (Config config : configs) {
			list.add(config.getKey());
		}
		return list;
	}
	
	public Config get(String key) {
		Collection<Config> configs = findAll();
		for (Config config : configs) {
			if (StringUtils.equals(key, config.getKey())) {
				return config;
			}
		}
		return null;
	}

	public void setConfig(String key, Object value) {
		Object obj = null;
		try {
			obj = JSONTransformer.transformPojo2Jso(value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Config exist = get(key);
		if (exist != null) {
			exist.setValue(obj == null ? null : obj.toString());
			updateObject(exist);
		} else {
			Config config = new Config();
			config.setKey(key);
			config.setValue(obj == null ? null : obj.toString());
			createObject(config);
		}
	}

	public void removeConfig(String key) {
		Config config = get(key);
		if (config != null) {
			deleteObject(config);
		}
	}

}
