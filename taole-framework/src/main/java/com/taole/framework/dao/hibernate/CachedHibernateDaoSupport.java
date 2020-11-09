package com.taole.framework.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.cache.Cache;
import com.taole.framework.cache.CacheFactoryFactory;
import com.taole.framework.dao.util.Condition;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.PredicateCondition;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.PageUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: CachedHibernateDaoSupport.java 9197 2016-08-31 07:57:37Z
 *          tonytang $
 */
public class CachedHibernateDaoSupport<T extends DomainObject> extends BaseHibernateDaoSupport<T> {

	static final String ALL_CACHED_FLAG_KEY = "___all_cached___";

	private String cachedFlagKey = ALL_CACHED_FLAG_KEY;

	@Autowired
	protected CacheFactoryFactory cacheFactoryFactory;

	/**
	 * @param entityClass
	 */
	public CachedHibernateDaoSupport(Class<T> entityClass) {
		super(entityClass);
	}

	public Collection<T> findAll() {
		if (!isCachedAll()) {
			Collection<T> all = super.findAll();
			setCachedAll(true);
			for (T domain : all) {
				getEntityCache(entityClass).put((Serializable) DomainObjectUtils.getPrimaryKeyValue(domain), domain);
			}
			return new HashSet<T>(all);
		} else {
			Set<T> list = new HashSet<T>();
			list.addAll(getEntityCache(entityClass).values());
			return list;
		}
	}

	protected void setCachedAll(boolean cached) {
		Cache<String, Boolean> flagCache = cacheFactoryFactory.getCacheFactory().getCache(cachedFlagKey, String.class, Boolean.class);
		if (flagCache != null) {
			flagCache.put(getEntityName(), cached);
		}
	}

	protected boolean isCachedAll() {
		Cache<String, Boolean> flagCache = cacheFactoryFactory.getCacheFactory().getCache(cachedFlagKey, String.class, Boolean.class);
		Boolean cached = flagCache.get(getEntityName());
		return cached != null ? cached.booleanValue() : false;
	}

	public void clearCache() {
		setCachedAll(false);
	}

	protected Cache<Serializable, T> getEntityCache(Class<T> clazz) {
		return cacheFactoryFactory.getCacheFactory().getCache("__" + getEntityName() + "__", Serializable.class, clazz);
	}

	public void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
		getEntityCache(entityClass).put((Serializable) DomainObjectUtils.getPrimaryKeyValue(entity), entity);
	}

	public String createObject(T entity) {
		String id = super.createObject(entity);
		getEntityCache(entityClass).put(id, entity);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T loadObject(String id) {
		Collection<T> all = findAll();
		for (T t : all) {
			if (DomainObjectUtils.getPrimaryKeyValue(t).equals(id)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteByCondition(Condition condition) {
		List<T> entities = listByCondition(condition);
		for (T t : entities) {
			deleteObject(t);
		}
		return entities.size();
	}

	public void updateObject(T entity) {
		super.updateObject(entity);
		getEntityCache(entityClass).put((Serializable) DomainObjectUtils.getPrimaryKeyValue(entity), entity);
	}

	public void deleteObject(T entity) {
		Serializable key = (Serializable) DomainObjectUtils.getPrimaryKeyValue(entity);
		super.deleteObject(entity);
		getEntityCache(entityClass).remove(key);
	}

	@Override
	public void deleteAll() {
		super.deleteAll();
		getEntityCache(entityClass).clear();
	}

	/**
	 * Tony add
	 */
	public void refresh() {
		getEntityCache(entityClass).clear();
		setCachedAll(false);
	}

	@Override
	public void deleteObject(String id) {
		T t = loadObject(id);
		if (t != null) {
			deleteObject(t);
		}
	}

	@Override
	public void deleteObjects(Collection<T> entities) {
		for (T t : entities) {
			deleteObject(t);
		}
	}

	@Override
	public void deleteObjects(T[] entities) {
		for (T t : entities) {
			deleteObject(t);
		}
	}

	@Override
	public void deleteObjects(String[] ids) {
		for (String id : ids) {
			deleteObject(id);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<T> listByCondition(Condition condition) {
		if (condition instanceof PredicateCondition) {
			return PageUtils.getList(findAll(), (PredicateCondition) condition);
		} else {
			return super.listByCondition(condition);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCondition(Condition condition, Sorter sorter) {
		if (condition instanceof PredicateCondition) {
			return PageUtils.getList(findAll(), (PredicateCondition<T>) condition, sorter);
		} else {
			return super.listByCondition(condition);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countByCondition(Condition condition) {
		if (condition instanceof PredicateCondition) {
			return PageUtils.getList(findAll(), (PredicateCondition<T>) condition).size();
		}
		return super.countByCondition(condition);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PaginationSupport<T> search(Condition condition, Range range, Sorter sorter) {
		if (condition instanceof PredicateCondition) {
			return PageUtils.getPaginationSupport(findAll(), (PredicateCondition) condition, range, sorter);
		} else {
			return super.search(condition, range, sorter);
		}
	}

}
