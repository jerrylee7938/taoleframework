package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "shopStoreSet")
@Table(name = "SHOP_STORE_SET")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ShopStoreSet implements DomainObject, Cloneable{

	private static final long serialVersionUID = 4610805249401255902L;

	/**
	 * 序号
	 */
	@Id
	@PrimaryKey
	@Column(name = "GOODS_SET_ID", unique = true, nullable = false)
	private String goodsSetId;
	
	/**
	 * 可售对象ID
	 */
	@Column(name = "OBJECT_ID")
	private String objectId;
	
	/**
	 * 可售对象类型（卡/商品）
	 */
	@Column(name = "OBJECT_TYPE")
	private String objectType;
	
	/**
	 * 店ID (portal group)
	 */
	@Column(name = "SHOP_ID")
	private String shopId;
	
	/**
	 * 库存量上限
	 */
	@Column(name = "MAX_AMOUNT")
	private Double maxAmount;
	
	/**
	 * 库存量下限
	 */
	@Column(name = "MIN_AMOUNT")
	private Double minAmount;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public String getGoodsSetId() {
		return goodsSetId;
	}

	public void setGoodsSetId(String goodsSetId) {
		this.goodsSetId = goodsSetId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
