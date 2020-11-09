package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "userBillApply ")
@Table(name = "USER_BILL_APPLY")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UserBillApply implements DomainObject, Cloneable {

	private static final long serialVersionUID = 3821409306234424865L;

	/**
	 * 审核记录id
	 */
	@Id
	@PrimaryKey
	@Column(name = "APPLY_ID", unique = true, nullable = false)
	private String applyId;

	/**
	 * 店面ID
	 */
	@Column(name = "SHOP_ID")
	private String shopId;

	/**
	 * 商品卡ID
	 */
	@Column(name = "CARD_ID")
	private String cardId;

	/**
	 * 张数
	 */
	@Column(name = "AMOUNT")
	private Integer amount;
	
	/**
	 * 充值次数
	 */
	@Column(name = "CHARGE_NO")
	private Integer chargeNo;

	/**
	 * 用户会员卡ID
	 */
	@Column(name = "USER_CARD_ID")
	private String userCardId;

	/**
	 * 交易类型(充值)
	 */
	@Column(name = "TRANS_TYPE")
	private String transType;

	/**
	 * 交易金额
	 */
	@Column(name = "MONEY")
	private Double money;

	/**
	 * 操作人
	 */
	@Column(name = "OPERATOR_ID")
	private String operatorId;

	/**
	 * 操作人姓名
	 */
	@Column(name = "OPERATOR_NAME")
	private String operatorName;

	/**
	 * 操作时间
	 */
	@Column(name = "OPERATE_TIME")
	private Date operateTime;

	/**
	 * 审核人
	 */
	@Column(name = "AUDITOR_ID")
	private String auditorId;

	/**
	 * 审核人姓名
	 */
	@Column(name = "AUDITOR_NAME")
	private String auditorName;

	/**
	 * 审核时间
	 */
	@Column(name = "AUDITOR_TIME")
	private Date auditorTime;

	/**
	 * 支付方式
	 */
	@Column(name = "PAY_TYPE")
	private String payType;

	/**
	 * 状态(待审核/审核通过/审核不通过)
	 */
	@Column(name = "STATUS")
	private String status;

	/**
	 * 备注
	 */
	@Column(name = "DESCRIPTION")
	private String description;

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

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getUserCardId() {
		return userCardId;
	}

	public void setUserCardId(String userCardId) {
		this.userCardId = userCardId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Date getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(Date auditorTime) {
		this.auditorTime = auditorTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(Integer chargeNo) {
		this.chargeNo = chargeNo;
	}

}
