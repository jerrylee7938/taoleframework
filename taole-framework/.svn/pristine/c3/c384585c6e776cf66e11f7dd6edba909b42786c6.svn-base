/*
 * @(#)DomainObjectDao.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao;

import java.util.Collection;
import java.util.List;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.dao.util.Condition;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.exception.NotUniqueException;

public interface DomainObjectDao<T extends DomainObject> {

    // ~ Methods ============================================================================================

    public Class<T> getEntityType();

    public String getEntityName();

    public String getEntityPK();

    // ~ CRUD of domain object ==============================================================================

    public T loadObject(String id);

    public String createObject(T entity);

    public void saveOrUpdate(T entity);


    public void updateObject(T entity);

    public void deleteObject(String id);

    public void deleteObject(T entity);

    public void deleteObjects(String[] ids);

    public void deleteObjects(T[] entities);

    public void deleteObjects(Collection<T> entities);

    public void deleteAll();

    /**
     * 这里根据属性返回唯一的对象。
     *
     * @param propName
     * @param propValue
     * @return
     * @throws NotUniqueException 当有多条结果的时候抛出。
     */
    public T getObject(final String propName, final Object propValue);

    public Collection<T> findAll();

    /**
     * 提供给Hibernate使用的查询。直接通过hql查询。对于mongo可以忽略参数values,直接通过json查询
     *
     * @param query
     * @param values
     * @return
     */
    public List<?> listByQuery(String query, Object... values);

    public List<?> listByQuery(String query);

    public List<T> listByProperty(String propName, Object propValue);

    public PaginationSupport<T> search(Condition condition, Range range, Sorter sorter);

    public List<T> listByCondition(Condition condition);

    public List<T> listByCondition(Condition condition, Sorter sorter);

    public int deleteByCondition(Condition condition);

    /**
     * 根据条件查询有限记录列表
     *
     * @param condition 查询条件
     * @param size      查询条数
     * @return 记录列表
     */
    public List<T> listByCondition(Condition condition, int size);

    /**
     * 根据条件、排序查询有限记录列表
     *
     * @param condition 查询条件
     * @param sorter    排序规则
     * @param size      查询条数
     * @return 记录列表
     */
    public List<T> listByCondition(Condition condition, Sorter sorter, int size);

    /**
     * 根据条件统计记录数
     *
     * @param condition 统计条件
     * @return 统计记录数
     */
    public int countByCondition(Condition condition);

}
