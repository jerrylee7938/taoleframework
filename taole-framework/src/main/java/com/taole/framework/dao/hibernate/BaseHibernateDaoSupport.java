/*
 * @(#)BaseHibernateDaoSupport.java 0.1 Nov 28, 2007 Copyright 2004-2007 
 * Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.Condition;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.dao.util.Sorter.Sort;
import com.taole.framework.exception.PrimaryKeyUndefinedException;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.UUID;

/**
 * @since 2008-4-31
 * @author wch116
 * $Id: BaseHibernateDaoSupport.java 9632 2017-04-28 07:51:33Z chengjun $
 */
public class BaseHibernateDaoSupport<T extends DomainObject> extends HibernateDaoSupport implements DomainObjectDao<T> {

	protected Class<T> entityClass;
	private String entityName;
	private String entityPK;

	@Autowired
	public final void setHibernateSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public BaseHibernateDaoSupport(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getEntityType() {
		if (entityClass == null) {
			ParameterizedType pmType = (ParameterizedType) getClass().getGenericSuperclass();
			entityClass = (Class<T>) pmType.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	public String getEntityName() {
		if (entityName == null) {
			entityName = DomainObjectUtils.getEntityName(getEntityType());
		}
		return entityName;
	}

	public String getEntityPK() {
		if (entityPK == null) {
			entityPK = AnnotationUtils.getPrimaryKeyName(getEntityType());
			if (entityPK == null) {
				throw new PrimaryKeyUndefinedException("Primary key is undefined: " + getEntityName());
			}
		}
		return entityPK;
	}

	public int countByCondition(Condition condition) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		criteria.setProjection(Projections.rowCount());
		List<?> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.get(0) instanceof Long) {
			return ((Long) result.get(0)).intValue();
		}
		return ((Integer) result.get(0)).intValue();
	}
	
	protected String checkAndInitEntityPK(T entity) {
		String pk = getEntityPK();
		BeanMap map = BeanMap.create(entity);
		String entityKey = (String) map.get(pk);
		if (StringUtils.isBlank(entityKey)) {
			entityKey = UUID.generateUUID();
			map.put(pk, entityKey);
		}
		return entityKey;
	}
	
	/**
	 * 取entity的主键值
	 * @param entity
	 * @return
	 */
	protected String getEntityPrimaryKeyValue(T entity) {
		String pk = getEntityPK();
		BeanMap map = BeanMap.create(entity);
		return (String) map.get(pk);
	}
	
	public String createObject(T entity) {
		String entityId = (String) getHibernateTemplate().save(entity);
		return entityId;
	}
	
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void deleteAll() {
		Collection<T> entities = findAll();
		getHibernateTemplate().deleteAll(entities);
	}

	public void deleteObject(String id) {
		deleteObject(loadObject(id));
	}

	public void deleteObject(T entity) {
		getHibernateTemplate().delete(entity);
	}


	public void deleteObjects(String[] ids) {
		for (String id : ids) {
			deleteObject(id);
		}
	}

	public void deleteObjects(T[] entities) {
		getHibernateTemplate().deleteAll(Arrays.asList(entities));
	}

	public void deleteObjects(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> findAll() {
		return (Collection<T>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(getEntityType()));
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCondition(Condition condition) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCondition(Condition condition, Sorter sorter) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport<T> search(Condition condition, Range range, Sorter sorter) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return HibernateUtils.findByCriteria(getHibernateTemplate(), criteria, range.getStart(), range.getLimit());
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCondition(Condition condition, int size) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		return (List<T>) getHibernateTemplate().findByCriteria(criteria, -1, size);
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCondition(Condition condition, Sorter sorter, int size) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		doSort(criteria, sorter);
		return (List<T>) getHibernateTemplate().findByCriteria(criteria, -1, size);
	}

	@SuppressWarnings("unchecked")
	public List<T> listByProperty(String propName, Object propValue) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
		criteria.add(Restrictions.eq(propName, propValue));
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public T getObject(String propName, Object propValue) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
		criteria.add(Restrictions.eq(propName, propValue));
		List<?> list = getHibernateTemplate().findByCriteria(criteria, -1, 1);
		Object entity = list.isEmpty() ? null : list.get(0);
		return (T) entity;
	}

	public T loadObject(String id) {
		return (T) getHibernateTemplate().get(getEntityType(), id);
	}

	public void updateObject(T entity) {
		getHibernateTemplate().merge(entity);
	}

	protected void applyPropertyFilterToDetachedCriteria(DetachedCriteria criteria, String paramName, Object propValue) throws HibernateException {
		if (propValue instanceof Collection<?>) {
			criteria.add(Restrictions.in(paramName, (Collection<?>) propValue));
		} else if (propValue instanceof Object[]) {
			criteria.add(Restrictions.in(paramName, (Object[]) propValue));
		} else {
			criteria.add(Restrictions.eq(paramName, propValue));
		}
	}

	protected DetachedCriteria populateDetachedCriteria(Condition condition) {
		return populateDetachedCriteria(condition, DetachedCriteria.forClass(getEntityType()));
	}

	protected DetachedCriteria populateDetachedCriteria(Condition condition, DetachedCriteria criteria) {
		if (criteria == null) {
			criteria = DetachedCriteria.forClass(getEntityType());
		}
		HibernateCondition hibernateCondition = (HibernateCondition) condition;
		hibernateCondition.populateDetachedCriteria(criteria);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	protected void doSort(DetachedCriteria criteria, Sorter sorter) {
		if (sorter != null) {
			for (Sort sort : sorter.getSorts()) {
				criteria.addOrder(sort.isAscending() ? Order.asc(sort.property) : Order.desc(sort.property));
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> listByQuery(String query, Object... values) {
		return getHibernateTemplate().find(query, values);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> listByQuery(String query) {
		return getHibernateTemplate().find(query);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int deleteByCondition(Condition condition) {
		DetachedCriteria criteria = populateDetachedCriteria(condition);
		List<T> list = (List<T>) getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(list);
		return list.size();
	}

}
