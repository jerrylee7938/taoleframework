package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "rptSalesByTradeType")
@Table(name = "rpt_sales_by_trade_type")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class RptSalesByTradeType implements DomainObject, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2831061826936744560L;

	/**
	 * 统计记录ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "stat_id")
	private String statId;

	/**
	 * 店ID
	 */
	@Column(name = "shop_id")
	private String shopId;

	/**
	 * 店名称
	 */
	@Column(name = "shop_name")
	private String shopName;

	/**
	 * 统计日期
	 */
	@Column(name = "stat_date")
	private Date statDate;

	/**
	 * 交易类型id
	 */
	@Column(name = "bill_type")
	private String billType;

	/**
	 * 交易类型名称
	 */
	@Column(name = "bill_type_name")
	private String billTypeName;

	/**
	 * 统计金额
	 */
	@Column(name = "money")
	private Double money;

	/**
	 * 统计数量
	 */
	@Column(name = "amount")
	private Integer amount;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	public String getStatId() {
		return statId;
	}

	public void setStatId(String statId) {
		this.statId = statId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
