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

public class GoodsBillDetailCondition implements HibernateCondition {

	private String catalogId;
	
	private String shopBillId;

	private String goodsName;
	private String goodsId;
	private String shopId;

	public String getShopBillId() {
		return shopBillId;
	}

	public void setShopBillId(String shopBillId) {
		this.shopBillId = shopBillId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		/*if (StringUtils.isNotBlank(catalogId)) {
			criteria.add(Restrictions.eq("catalogId", catalogId));
		}*/
		/*if (StringUtils.isNotBlank(shopName)) {
			criteria.add(Restrictions.ilike("shopName", shopName, MatchMode.ANYWHERE));
		}*/
		if (StringUtils.isNotBlank(shopId)) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}
		if (StringUtils.isNotBlank(shopBillId)) {
			criteria.add(Restrictions.eq("shopBillId", shopBillId));
		}
		if (StringUtils.isNotBlank(goodsId)) {
			criteria.add(Restrictions.eq("goodsId", goodsId));
		}

	}

}
