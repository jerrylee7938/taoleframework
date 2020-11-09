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

import com.taole.framework.dao.hibernate.HibernateCondition;

public class MemberCardCondition implements HibernateCondition {

	private String cardNo;
	
	private String userId;
	
	private String [] userIds;
	
	private String [] operatorShopIds;
	
	private String operatorShopId;
	
	private String userCardId;
	
	private String status;
	private String [] statuss;
	
	private String cardId;
	
	

	private Date startTime, endTime;
	

	public String[] getStatuss() {
		return statuss;
	}

	public void setStatuss(String[] statuss) {
		this.statuss = statuss;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String getOperatorShopId() {
		return operatorShopId;
	}

	public void setOperatorShopId(String operatorShopId) {
		this.operatorShopId = operatorShopId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String[] getOperatorShopIds() {
		return operatorShopIds;
	}

	public void setOperatorShopIds(String[] operatorShopIds) {
		this.operatorShopIds = operatorShopIds;
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

	public String getUserCardId() {
		return userCardId;
	}

	public void setUserCardId(String userCardId) {
		this.userCardId = userCardId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(cardNo)) {
			criteria.add(Restrictions.eq("cardNo", cardNo));
		}
		if (operatorShopIds!=null && operatorShopIds.length>0) {
			criteria.add(Restrictions.in("operatorShopId", operatorShopIds));
		}
		if (StringUtils.isNotBlank(operatorShopId)) {
			criteria.add(Restrictions.eq("operatorShopId", operatorShopId));
		}
		if (StringUtils.isNotBlank(userId)) {
			criteria.add(Restrictions.ilike("userId", userId,MatchMode.ANYWHERE));
		}
		if (userIds!=null && userIds.length>0) {
			criteria.add(Restrictions.in("userId", userIds));
		}
		if (statuss!=null && statuss.length>0) {
			criteria.add(Restrictions.in("status", statuss));
		}
		if (StringUtils.isNotBlank(userCardId)) {
			criteria.add(Restrictions.eq("userCardId", userCardId));
		}
		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (StringUtils.isNotBlank(cardId)) {
			criteria.add(Restrictions.eq("cardId", cardId));
		}
		if (startTime != null && endTime != null)
			criteria.add(Restrictions.and(Restrictions.ge("approverTime", startTime), Restrictions.le("approverTime", endTime)));
		else if (startTime != null)
			criteria.add(Restrictions.ge("approverTime", startTime));
		else if (endTime != null)
			criteria.add(Restrictions.le("approverTime", endTime));
	}

}
