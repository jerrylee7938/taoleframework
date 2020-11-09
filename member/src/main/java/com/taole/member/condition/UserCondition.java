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

public class UserCondition implements HibernateCondition {

	private String cardNo;

	private String telphone;
	
	private String name;
	
	private String id;
	private String [] ids;
	
	private String [] operatorShopIds;
	
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getOperatorShopIds() {
		return operatorShopIds;
	}

	public void setOperatorShopIds(String[] operatorShopIds) {
		this.operatorShopIds = operatorShopIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		if (StringUtils.isNotBlank(cardNo)) {
			criteria.add(Restrictions.ilike("cardNo", cardNo, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(telphone)) {
			criteria.add(Restrictions.ilike("telphone", telphone, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if (ids!=null && ids.length>0) {
			criteria.add(Restrictions.in("id", ids));
		}
	}

}
