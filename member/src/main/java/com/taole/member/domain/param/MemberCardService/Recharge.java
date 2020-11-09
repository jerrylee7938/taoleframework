package com.taole.member.domain.param.MemberCardService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Recharge {
	@ApiModel(value = "会员卡充值请求参数")
	public static class MemberCardServiceRechargeReq {

		@ApiModelProperty(value = "店面ID", example = "456432u54")
		private String shopId;

		@ApiModelProperty(value = "开卡人", example = "765467i")
		private String operator;

		@ApiModelProperty(value = "会员卡id", example = "76342245i5432456")
		private String userCardId;

		@ApiModelProperty(value = "支付方式", example = "wx")
		private String payType;

		@ApiModelProperty(value = "备注")
		private String description;
		
		@ApiModelProperty(value = "流水金额")
		private Double consumeMoney;

		public Double getConsumeMoney() {
			return consumeMoney;
		}

		public void setConsumeMoney(Double consumeMoney) {
			this.consumeMoney = consumeMoney;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getUserCardId() {
			return userCardId;
		}

		public void setUserCardId(String userCardId) {
			this.userCardId = userCardId;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	@ApiModel(value = "会员卡充值返回参数")
	public static class MemberCardServiceRechargeResp {
		@ApiModelProperty(value = "用户会员卡ID")
		private String userCardId;
		
		@ApiModelProperty(value = "经办人ID", example = "456432u54")
		private String operator;

		@ApiModelProperty(value = "卡id")
		private String cardId;
		
		@ApiModelProperty(value = "开卡人所在店ID", example = "765467i")
		private String operatorShopId;

		@ApiModelProperty(value = "会员姓名", example = "张三")
		private String name;

		@ApiModelProperty(value = "会员手机号", example = "55446225235")
		private String telphone;

		@ApiModelProperty(value = "性别", example = "2")
		private Integer gender;

		@ApiModelProperty(value = "出生日期", example = "2018-10-10 ")
		private Date birthday;

		@ApiModelProperty(value = "卡号")
		private String cardNo;
		
		@ApiModelProperty(value = "备注")
		private String description;
		
		@ApiModelProperty(value = "支付方式")
		private String payType;

		@ApiModelProperty(value = "实付金额")
		private Double payMoney;
		@ApiModelProperty(value = "截止日期", example = "2018-10-10 ")
		private Date deadline;

		public Date getDeadline() {
			return deadline;
		}

		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}

		public Double getPayMoney() {
			return payMoney;
		}

		public void setPayMoney(Double payMoney) {
			this.payMoney = payMoney;
		}

		public String getUserCardId() {
			return userCardId;
		}

		public void setUserCardId(String userCardId) {
			this.userCardId = userCardId;
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTelphone() {
			return telphone;
		}

		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}

		public Integer getGender() {
			return gender;
		}

		public void setGender(Integer gender) {
			this.gender = gender;
		}

		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

	}
}
