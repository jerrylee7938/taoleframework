package com.taole.member.domain.param.UserBillService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query {
	@ApiModel(value = "查询流水账明细请求参数")
	public static class UserBillServiceQueryReq {

		@ApiModelProperty(value = "交易类型")
		private String actionTypeId;
		
		@ApiModelProperty(value = "商店id", example = "76523i76527")
		private String shopId;
		
		@ApiModelProperty(value = "付款方式")
		private String payType;
		
		@ApiModelProperty(value = "卡号")
		private String cardNo;
		
		@ApiModelProperty(value = "姓名")
		private String userId;
		
		@ApiModelProperty(value = "开始时间")
		private String timeStart;
		
		@ApiModelProperty(value = "结束时间")
		private String timeEnd;

		
		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}


		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getTimeStart() {
			return timeStart;
		}

		public void setTimeStart(String timeStart) {
			this.timeStart = timeStart;
		}

		public String getTimeEnd() {
			return timeEnd;
		}

		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}


		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getActionTypeId() {
			return actionTypeId;
		}

		public void setActionTypeId(String actionTypeId) {
			this.actionTypeId = actionTypeId;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}


	}

	@ApiModel(value = "查询流水账明细返回参数")
	public static class UserBillServiceQueryResp {
		@ApiModelProperty(value = "交易单号")
		private String userBillNo;
		
		@ApiModelProperty(value = "日期")
		private Date createTiem;

		@ApiModelProperty(value = "剩余次数")
		private Integer balanceNum;
		
		@ApiModelProperty(value = "交易金额")
		private Double consumeMoney;
		
		@ApiModelProperty(value = "流水行为类型")
		private String actionTypeId;
		
		@ApiModelProperty(value = "支付方式")
		private String payType;
		
		@ApiModelProperty(value = "操作人")
		private String operator;

		public String getUserBillNo() {
			return userBillNo;
		}

		public void setUserBillNo(String userBillNo) {
			this.userBillNo = userBillNo;
		}

		public Date getCreateTiem() {
			return createTiem;
		}

		public void setCreateTiem(Date createTiem) {
			this.createTiem = createTiem;
		}

		public Integer getBalanceNum() {
			return balanceNum;
		}

		public void setBalanceNum(Integer balanceNum) {
			this.balanceNum = balanceNum;
		}

		public Double getConsumeMoney() {
			return consumeMoney;
		}

		public void setConsumeMoney(Double consumeMoney) {
			this.consumeMoney = consumeMoney;
		}

		public String getActionTypeId() {
			return actionTypeId;
		}

		public void setActionTypeId(String actionTypeId) {
			this.actionTypeId = actionTypeId;
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

	}
	}
