package com.taole.member.domain.param.ReportService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query {
	@ApiModel(value = "报表管理查询列表请求参数")
	public static class ReportServiceQueryReq {

		@ApiModelProperty(value = "日期")
		private String statDate;
		
		@ApiModelProperty(value = "商店id")
		private String shopId;
		
		@ApiModelProperty(value = "统计维度类型（goods商品、tradeType交易类型，payType支付类型）")
		private String dimens;
		
		@ApiModelProperty(value = "统计周期类型（day日报表，month月报表）")
		private String circleType;

		public String getStatDate() {
			return statDate;
		}

		public void setStatDate(String statDate) {
			this.statDate = statDate;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getDimens() {
			return dimens;
		}

		public void setDimens(String dimens) {
			this.dimens = dimens;
		}

		public String getCircleType() {
			return circleType;
		}

		public void setCircleType(String circleType) {
			this.circleType = circleType;
		}
		
	}

	@ApiModel(value = "报表管理查询列表返回参数")
	public static class ReportServiceQueryResp {
		@ApiModelProperty(value = "日期")
		private String statDate;
		
		@ApiModelProperty(value = "商店id")
		private String shopId;
		
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
