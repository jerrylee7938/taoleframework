package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "goodsBillDetail")
@Table(name = "GOODS_BILL_DETAIL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class GoodsBillDetail implements DomainObject , Cloneable{

	private static final long serialVersionUID = -764879107863228444L;
	/**
	 * 出入库商品记录id
	 */
	@Id
	@PrimaryKey
	@Column(name = "GB_DETAIL_ID", unique = true, nullable = false)
	private String gbDetailId;
	
	@Column(name = "SHOP_BILL_ID")
	private String shopBillId;
	/**
	 * 商品ID
	 */
	@Column(name = "GOODS_ID")
	private String goodsId;
	/**
	 * 数量
	 */
	@Column(name = "AMOUNT")
	private Double amount;
	/**
	 * 单价
	 */
	@Column(name = "PRICE")
	private Double price;
	/**
	 * 单位
	 */
	@Column(name = "UNIT")
	private String unit;
	/**
	 * 店ID
	 */
	@Column(name = "SHOP_ID")
	private String shopId;
	/**
	 * 当前库存量
	 */
	@Column(name = "BALANCE")
	private Double balance;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	public String getGbDetailId() {
		return gbDetailId;
	}
	public void setGbDetailId(String gbDetailId) {
		this.gbDetailId = gbDetailId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getShopBillId() {
		return shopBillId;
	}
	public void setShopBillId(String shopBillId) {
		this.shopBillId = shopBillId;
	}
	
}
