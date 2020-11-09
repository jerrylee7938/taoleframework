/**
 * Project:member
 * File:FooCondition.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.condition;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

public class UserGoodsOrderCondition implements HibernateCondition {

	private String userId;
	private String orderType;
	private String status;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		
		if (StringUtils.isNotBlank(userId)) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		
		if (StringUtils.isNotBlank(orderType)) {
			criteria.add(Restrictions.eq("orderType", orderType));
		}
	}

}
