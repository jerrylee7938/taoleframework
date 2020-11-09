package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Table(name = "`ORDER`")
@Entity(name = ProjectConfig.PREFIX + "order")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Order implements DomainObject, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
    * 订单id
    */
    @Id
    @PrimaryKey
    @Column(name = "ORDER_ID")
    private String id;

    /**
    * 订单编号
    */
    @Column(name = "ORDER_NO")
    private String orderNo;

    /**
    * 商品/服务数量
    */
    @Column(name = "NUM")
    private Integer num;

    /**
    * 订单金额
    */
    @Column(name = "AMOUNT")
    private Double amount;

    /**
    * 店铺id
    */
    @Column(name = "SHOP_ID")
    private String shopId;

    /**
    * 购买者id
    */
    @Column(name = "ACCOUNT_ID")
    private String accountId;

    /**
    * 订单类型
    */
    @Column(name = "TYPE")
    private String type;

    /**
    * 订单状态; 0 待支付 1已完成 2已结束 3已关闭
    */
    @Column(name = "STATE")
    private Integer state;

    /**
    * 用户评价
    */
    @Column(name = "COMMENT")
    private String comment;
    
    /**
     * 支付渠道
     */
     @Column(name = "PAY_CHANNEL")
    private String payChannel;
    
    /**
     * 支付数据
     */
     @Column(name = "PAY_DATA")
    private String payData;

    /**
    * 订单创建时间
    */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
    * 订单最后修改时间
    */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @Transient
    private String serviceName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayData() {
		return payData;
	}

	public void setPayData(String payData) {
		this.payData = payData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNo=" + orderNo + ", num=" + num
				+ ", amount=" + amount + ", shopId=" + shopId + ", accountId="
				+ accountId + ", type=" + type + ", state=" + state
				+ ", comment=" + comment + ", payChannel=" + payChannel
				+ ", payData=" + payData + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", serviceName=" + serviceName
				+ "]";
	}
}
