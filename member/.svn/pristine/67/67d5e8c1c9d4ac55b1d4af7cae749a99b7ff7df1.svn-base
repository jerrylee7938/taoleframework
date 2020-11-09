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

public class TradeLockCondition implements HibernateCondition {

	private String cardNo;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}



	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		
		if (StringUtils.isNotBlank(cardNo)) {
			criteria.add(Restrictions.eq("cardNo", cardNo));
		}
	}

}
