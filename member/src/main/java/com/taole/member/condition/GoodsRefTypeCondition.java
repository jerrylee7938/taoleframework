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

public class GoodsRefTypeCondition implements HibernateCondition {

	private String typeId; 
	private String goodsId;
	
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(typeId)) {
			criteria.add(Restrictions.eq("typeId", typeId));
		}
		
		if (StringUtils.isNotBlank(goodsId)) {
			criteria.add(Restrictions.eq("goodsId", goodsId));
		}
	}
}
