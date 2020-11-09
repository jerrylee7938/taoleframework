package com.taole.member.domain.param.UserBillService;

import java.util.Date;
import java.util.List;

import com.taole.member.domain.param.GoodsBillDetail.InStore.GoodsValue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Consume {
	@ApiModel(value = "会员消费请求参数")
	public static class UserBillServiceConsumeReq {

		private List<GoodsValue> goodsValues;
		

		@ApiModelProperty(value = "会员id", example = "765467i")
		private String userId;
		
		@ApiModelProperty(value = "消费单号")
		private String userBillNo;
		
		@ApiModelProperty(value = "操作人")
		private String operator;
		
		@ApiModelProperty(value = "消费者类型", example = "MEMBER")
		private String userType;
		
		@ApiModelProperty(value = "付款方式", example = "WX")
		private String payType;
		
		@ApiModelProperty(value = "会员卡id", example = "765467i")
		private String userCardId;

		@ApiModelProperty(value = "店id", example = "76342245i5432456")
		private String shopId;

		@ApiModelProperty(value = "刷卡次数", example = "2")
		private Integer swipeAmount;
		
		@ApiModelProperty(value = "剩余次数", example = "26")
		private Integer balanceNum;

		@ApiModelProperty(value = "备注")
		private String description;
		
		@ApiModelProperty(value = "流水金额")
		private Double consumeMoney;

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getUserBillNo() {
			return userBillNo;
		}

		public void setUserBillNo(String userBillNo) {
			this.userBillNo = userBillNo;
		}

		public String getUserType() {
			return userType;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

		public String getUserCardId() {
			return userCardId;
		}

		public void setUserCardId(String userCardId) {
			this.userCardId = userCardId;
		}

		public List<GoodsValue> getGoodsValues() {
			return goodsValues;
		}

		public void setGoodsValues(List<GoodsValue> goodsValues) {
			this.goodsValues = goodsValues;
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

		public Integer getSwipeAmount() {
			return swipeAmount;
		}

		public void setSwipeAmount(Integer swipeAmount) {
			this.swipeAmount = swipeAmount;
		}

		public Integer getBalanceNum() {
			return balanceNum;
		}

		public void setBalanceNum(Integer balanceNum) {
			this.balanceNum = balanceNum;
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

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

	}
	
	@ApiModel(value = "会员消费商品信息")
	public static class GoodsValue{
		
		@ApiModelProperty(value = "商品ID", example = "4a1b710f737d43d2b4d818855c8539bf")
		private String goodsId;

		@ApiModelProperty(value = "数量")
		private Double amount;

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

	}
	@ApiModel(value = "会员消费返回参数")
	public static class UserBillServiceConsumeResp {
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
