package com.taole.member.condition;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

public class RptSalesByPayTypeCondition implements HibernateCondition {
	private String shopId;
	private String payTypeId;
	private Date statDate;
	

	public Date getStatDate() {
		return statDate;
	}


	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}


	public String getShopId() {
		return shopId;
	}


	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public String getPayTypeId() {
		return payTypeId;
	}


	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}


	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {

		if (StringUtils.isNotBlank(shopId)) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}

		if (StringUtils.isNotBlank(payTypeId)) {
			criteria.add(Restrictions.eq("payTypeId", payTypeId));
		}
		
		if (statDate != null) {
			criteria.add(Restrictions.eq("statDate", statDate));
		}
	}
}
