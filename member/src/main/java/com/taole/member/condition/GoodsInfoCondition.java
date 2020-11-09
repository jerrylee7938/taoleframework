/**
 * Project:member
 * File:FooCondition.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.condition;

import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

public class GoodsInfoCondition implements HibernateCondition {

	private String name;

	private String catalogId;
	private String goodsId;
	
	
	public String getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public String getCatalogId() {
		return catalogId;
	}


	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(catalogId)) {
			criteria.add(Restrictions.eq("catalogId", catalogId));
		}
		if (StringUtils.isNotBlank(goodsId)) {
			criteria.add(Restrictions.eq("goodsId", goodsId));
		}
		
	}

}
