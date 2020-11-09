/**
 * Project:member
 * File:FooCondition.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.condition;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import com.taole.framework.dao.hibernate.HibernateCondition;

public class UserBillApplyCondition implements HibernateCondition {

	private String cardNo;
	
	private String applyId;

	private String shopId;

	private String status;
	private String transType;
	private String userCardId;

	private Date startTime, endTime;
	


	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getUserCardId() {
		return userCardId;
	}

	public void setUserCardId(String userCardId) {
		this.userCardId = userCardId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(cardNo)) {
			criteria.add(Restrictions.ilike("cardNo", cardNo, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(shopId)) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}
		if (StringUtils.isNotBlank(userCardId)) {
			criteria.add(Restrictions.eq("userCardId", userCardId));
		}
		
		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (StringUtils.isNotBlank(transType)) {
			criteria.add(Restrictions.eq("transType", transType));
		}
		if (StringUtils.isNotBlank(applyId)) {
			criteria.add(Restrictions.eq("applyId", applyId));
		}
		if (startTime != null && endTime != null)
			criteria.add(
					Restrictions.and(Restrictions.ge("createTime", startTime), Restrictions.le("createTime", endTime)));
		else if (startTime != null)
			criteria.add(Restrictions.ge("createTime", startTime));
		else if (endTime != null)
			criteria.add(Restrictions.le("createTime", endTime));
	}

}
