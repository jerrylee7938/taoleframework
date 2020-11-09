package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "userGoodsOrder")
@Table(name = "USER_GOODS_ORDER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UserGoodsOrder implements DomainObject {
	private static final long serialVersionUID = 159251418075429876L;

	/**
    * 记录id
    */
	@Id
	@PrimaryKey
	@Column(name = "RECORD_ID")
    private String id;

    /**
    * 用户流水记录id，通过哪条流水记录衍生出来的
    */
	@Column(name = "USER_BILL_ID")
    private String userBillId;

    /**
    * 对应的出库单记录id
    */
	@Column(name = "SHOP_BILL_ID")
    private String shopBillId;

    /**
    * 用户id
    */
	@Column(name = "USER_ID")
    private String userId;
	
	/**
	 * 店铺id
	 */
	@Column(name = "SHOP_ID")
	private String shopId;

    /**
    * 商品id，针对门票1张一个id记录，针对其他商品，所有id组合成字符串
    */
	@Column(name = "GOODS_IDS")
    private String goodsIds;

    /**
    * 订单类型：1：门票 2：普通商品
    */
	@Column(name = "ORDER_TYPE")
    private String orderType;

    /**
    * 原始数量
    */
	@Column(name = "INIT_AMOUNT")
    private Integer initAmount;

    /**
    * 领取数量
    */
	@Column(name = "RECEIVE_AMOUNT")
    private Integer receiveAmount;

    /**
    * 1：待使用 2：已使用
    */
	@Column(name = "STATUS")
    private String status;

    /**
    * 购买时间
    */
	@Column(name = "ORDER_TIME")
    private Date orderTime;

    /**
    * 使用时间
    */
	@Column(name = "USED_TIME")
    private Date usedTime;

    /**
    * 修改时间
    */
	@Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
    * 创建时间
    */
	@Column(name = "CREATE_TIME")
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserBillId() {
		return userBillId;
	}

	public void setUserBillId(String userBillId) {
		this.userBillId = userBillId;
	}

	public String getShopBillId() {
		return shopBillId;
	}

	public void setShopBillId(String shopBillId) {
		this.shopBillId = shopBillId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(Integer initAmount) {
		this.initAmount = initAmount;
	}

	public Integer getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Integer receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
