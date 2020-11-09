package com.taole.member.domain.param.UserBillApplyService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Charge {
	@ApiModel(value = "卡充值请求参数")
	public static class UserBillApplyServiceChargeReq {

		@ApiModelProperty(value = "店面ID", example = "456432u54")
		private String shopId;

		@ApiModelProperty(value = "开卡人id", example = "765467i")
		private String operatorId;
		
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

		@ApiModelProperty(value = "充值张数")
		private Integer cardAmount;
		
		@ApiModelProperty(value = "充值次数")
		private Integer chargeNo;


		public Integer getChargeNo() {
			return chargeNo;
		}

		public void setChargeNo(Integer chargeNo) {
			this.chargeNo = chargeNo;
		}

		public String getOperatorId() {
			return operatorId;
		}

		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
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

		public Double getConsumeMoney() {
			return consumeMoney;
		}

		public void setConsumeMoney(Double consumeMoney) {
			this.consumeMoney = consumeMoney;
		}

		public Integer getCardAmount() {
			return cardAmount;
		}

		public void setCardAmount(Integer cardAmount) {
			this.cardAmount = cardAmount;
		}

	}

	@ApiModel(value = "卡充值返回参数")
	public static class UserBillApplyServiceChargeResp {
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

		@ApiModelProperty(value = "充值张数")
		private Integer cardAmount;

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

		public Double getConsumeMoney() {
			return consumeMoney;
		}

		public void setConsumeMoney(Double consumeMoney) {
			this.consumeMoney = consumeMoney;
		}

		public Integer getCardAmount() {
			return cardAmount;
		}

		public void setCardAmount(Integer cardAmount) {
			this.cardAmount = cardAmount;
		}

	}
}
