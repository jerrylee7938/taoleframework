/**
 * Copyright 2004-2009  Co., Ltd. All rights reserved.
 */
package com.taole.framework.dao.hibernate;

import org.hibernate.criterion.DetachedCriteria;

import com.taole.framework.dao.util.Condition;

/**
 * @author rory
 * @since Jul 17, 2009 11:55:20 AM
 * @version $Id: HibernateCondition.java 5 2014-01-15 13:55:28Z yedf $
 */
public interface HibernateCondition extends Condition {

	/**
	 * 根据condition装配查询条件
	 * @param criteria
	 */
	public void populateDetachedCriteria(DetachedCriteria criteria);
}
