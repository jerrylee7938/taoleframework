/**
 * Project:member
 * File:FooCondition.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.condition;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

public class CardInfoCondition implements HibernateCondition {

	private String cardName;

	private String cardType;

	private String chargeType;
	private String [] objIds;
	private String code;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getObjIds() {
		return objIds;
	}

	public void setObjIds(String[] objIds) {
		this.objIds = objIds;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		this.code = string;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(cardName)) {
			criteria.add(Restrictions.ilike("cardName", cardName, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.ne("status", status));
		}
		if (objIds!=null && objIds.length>0) {
			criteria.add(Restrictions.in("cardId", objIds));
		}
		if (StringUtils.isNotBlank(cardType)) {
			criteria.add(Restrictions.eq("cardType", cardType));
		}
		
		if (StringUtils.isNotBlank(chargeType)) {
			criteria.add(Restrictions.eq("chargeType", chargeType));
		}
		
		if (StringUtils.isNotBlank(code)) {
			criteria.add(Restrictions.eq("code", code));
		}
	}

}
