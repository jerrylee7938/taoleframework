package com.taole.member.condition;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.taole.framework.dao.hibernate.HibernateCondition;
import com.taole.framework.util.StringUtils;

public class SmsVerifyCondition implements HibernateCondition {

	private String verify;
	private String telephone;
	private Integer country;
	private Integer verifyType;
	private Integer isUsed;
	private Integer isSend;
	
	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (!StringUtils.isEmpty(verify))
			criteria.add(Restrictions.eq("verify", verify));
		if (!StringUtils.isEmpty(telephone))
			criteria.add(Restrictions.eq("telephone", telephone));
		if (country != null)
			criteria.add(Restrictions.eq("country", country));
		if (verifyType != null)
			criteria.add(Restrictions.eq("verifyType", verifyType));
		if (isUsed != null)
			criteria.add(Restrictions.eq("isUsed", isUsed));
		if (isSend != null)
			criteria.add(Restrictions.eq("isSend", isSend));
	}
	
	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

}
