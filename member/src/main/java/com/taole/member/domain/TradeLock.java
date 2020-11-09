package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "tradeLock")
@Table(name = "TRADE_LOCK")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class TradeLock implements DomainObject, Cloneable{

	private static final long serialVersionUID = -9193342141720487654L;
	@Id
	@PrimaryKey
	@Column(name = "CARD_NO", unique = true, nullable = false)
	private String cardNo;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
