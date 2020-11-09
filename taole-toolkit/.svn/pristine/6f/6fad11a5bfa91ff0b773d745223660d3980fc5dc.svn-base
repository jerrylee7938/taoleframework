package com.taole.toolkit.cache.manager;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.hibernate.CachedHibernateDaoSupport;
import com.taole.framework.dao.hibernate.HibernateNodeDao;

@Service
public class CacheManager {
	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 清理缓存
	 */
	public void clearAllCache() {
		clearCache(null);
	}

	/**
	 * 清理缓存
	 * 
	 * @param moduleName
	 *            模组名称，即各模组的ProjectConfig.PREFIX，若不填写则清理所有模组。
	 */
	@SuppressWarnings("rawtypes")
	public void clearCache(String moduleName) {
		Map<String, DomainObjectDao> daoMap = applicationContext.getBeansOfType(DomainObjectDao.class);
		for (Map.Entry<String, DomainObjectDao> entry : daoMap.entrySet()) {
			String daoBeanName = entry.getKey();
			DomainObjectDao dao = entry.getValue();
			if (dao instanceof CachedHibernateDaoSupport) {
				if (StringUtils.isBlank(moduleName) || daoBeanName.startsWith(moduleName)) {
					CachedHibernateDaoSupport cacheDao = (CachedHibernateDaoSupport) dao;
					cacheDao.refresh();
				}
			} else if (dao instanceof HibernateNodeDao) {
				if (StringUtils.isBlank(moduleName) || daoBeanName.startsWith(moduleName)) {
					HibernateNodeDao nodeDao = (HibernateNodeDao) dao;
					nodeDao.refresh();
				}
			}
		}
	}
}
