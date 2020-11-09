package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;
@Entity(name = ProjectConfig.PREFIX + "userBill")
@Table(name = "USER_BILL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UserBill implements DomainObject, Cloneable{

	private static final long serialVersionUID = -6891922151359623309L;

	/**
	 * 流水id
	 */
	@Id
	@PrimaryKey
	@Column(name = "USER_BILL_ID", unique = true, nullable = false)
	private String userBillId;
	
	/**
	 * 流水单号
	 */
	@Column(name = "USER_BILL_NO")
	private String userBillNo;
	
	/**
	 * 销售单id(行为为消费时才有值)
	 */
	@Column(name = "SHOP_BILL_ID")
	private String shopBillId;
	
	/**
	 * 用户ID
	 */
	@Column(name = "USER_ID")
	private String userId;
	
	/**
	 * 用户会员卡id
	 */
	@Column(name = "USER_CARD_ID")
	private String userCardId;

	/**
	 * 流水金额
	 */
	@Column(name = "CONSUME_MONEY")
	private Double consumeMoney;
	
	/**
	 * 刷卡次数
	 */
	@Column(name = "SWIPE_AMOUNT")
	private Integer swipeAmount;
	
	/**
	 * 流水值单位
	 */
	@Column(name = "CONSUME_UNIT")
	private String consumeUnit;
	
	/**
	 * 流水行为类型（开卡、充值、消费、调账、退卡）
	 */
	@Column(name = "ACTION_TYPE_ID")
	private String actionTypeId;
	
	/**
	 * 进出帐类型
	 */
	@Column(name = "IN_OUT_TYPE")
	private String inOutType;
	
	/**
	 * 用户账户剩余金额（暂无用）
	 */
	@Column(name = "BALANCE")
	private Double balance;
	
	/**
	 * 用户卡剩余次数
	 */
	@Column(name = "BALANCE_NUM")
	private Integer balanceNum;
	
	/**
	 * 支付方式类型
	 */
	@Column(name = "PAY_TYPE")
	private String payType;
	
	/**
	 * 操作人
	 */
	@Column(name = "OPERATOR")
	private String operator;
	
	/**
	 * 操作人所在店
	 */
	@Column(name = "OPERATOR_SHOP_ID")
	private String operatorShopId;
	
	/**
	 * 操作时间
	 */
	@Column(name = "operate_time")
	private Date operateTime;
	
	/**
	 * 消费备注
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public String getUserBillId() {
		return userBillId;
	}

	public void setUserBillId(String userBillId) {
		this.userBillId = userBillId;
	}

	public String getUserBillNo() {
		return userBillNo;
	}

	public void setUserBillNo(String userBillNo) {
		this.userBillNo = userBillNo;
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

	public String getUserCardId() {
		return userCardId;
	}

	public void setUserCardId(String userCardId) {
		this.userCardId = userCardId;
	}

	public Double getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(Double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public Integer getSwipeAmount() {
		return swipeAmount;
	}

	public void setSwipeAmount(Integer swipeAmount) {
		this.swipeAmount = swipeAmount;
	}

	public String getConsumeUnit() {
		return consumeUnit;
	}

	public void setConsumeUnit(String consumeUnit) {
		this.consumeUnit = consumeUnit;
	}

	public String getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(String actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public String getInOutType() {
		return inOutType;
	}

	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getBalanceNum() {
		return balanceNum;
	}

	public void setBalanceNum(Integer balanceNum) {
		this.balanceNum = balanceNum;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorShopId() {
		return operatorShopId;
	}

	public void setOperatorShopId(String operatorShopId) {
		this.operatorShopId = operatorShopId;
	}


	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
