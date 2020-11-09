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

public class ShopStoreSetCondition implements HibernateCondition {

	private String name;
	
	private String shopId;

	private String objectId;

	private String objectType;
	
	private String goodsName;
	

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	public String getShopId() {
		return shopId;
	}


	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public String getObjectId() {
		return objectId;
	}


	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}


	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		/*if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}*/
		
		if (StringUtils.isNotBlank(shopId)) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}
		
		if (StringUtils.isNotBlank(objectId)) {
			criteria.add(Restrictions.eq("objectId", objectId));
		}
		
		if (StringUtils.isNotBlank(objectType)) {
			criteria.add(Restrictions.eq("objectType", objectType));
		}
		
		/*if (StringUtils.isNotBlank(goodsName)) {
			criteria.add(Restrictions.ilike("goodsName", goodsName,MatchMode.ANYWHERE));
		}*/
	}

}
