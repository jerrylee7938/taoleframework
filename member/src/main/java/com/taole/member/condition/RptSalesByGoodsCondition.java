package com.taole.member.condition;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;

public class RptSalesByGoodsCondition implements HibernateCondition {
	private String shopId;
	private String goodsId;
	private Date statDate;
	private Date firstDay, lastDay;
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}


	public Date getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	public Date getLastDay() {
		return lastDay;
	}

	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {

		if (StringUtils.isNotBlank(shopId)) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}

		if (StringUtils.isNotBlank(goodsId)) {
			criteria.add(Restrictions.eq("goodsId", goodsId));
		}
		
		if (statDate!=null) {
			criteria.add(Restrictions.eq("statDate", statDate));
		}
		if (firstDay != null && lastDay != null)
			criteria.add(Restrictions.and(Restrictions.ge("statDate", firstDay), Restrictions.le("statDate", lastDay)));
	
	}
}
