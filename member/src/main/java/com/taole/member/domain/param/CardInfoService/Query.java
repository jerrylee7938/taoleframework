package com.taole.member.domain.param.CardInfoService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query {
	@ApiModel(value = "查詢会员卡信息列表请求参数")
	public static class CardInfoServiceQueryReq {

		@ApiModelProperty(value = "卡名称", example = "金卡")
		private String cardName;

		@ApiModelProperty(value = "卡分类", example = "期限卡")
		private String cardType;
		
		@ApiModelProperty(value = "计费类型", example = "季卡")
		private String chargeType;

		public String getChargeType() {
			return chargeType;
		}

		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
		}

		public String getCardName() {
			return cardName;
		}

		public void setCardName(String cardName) {
			this.cardName = cardName;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}


	}

	@ApiModel(value = "查詢会员卡信息列表返回参数")
	public static class CardInfoServiceQueryResp {
		@ApiModelProperty(value = "卡Id")
		private String cardId;
		@ApiModelProperty(value = "卡名称", example = "金卡")
		private String cardName;

		@ApiModelProperty(value = "卡分类id", example = "qxk")
		private String cardType;
		
		@ApiModelProperty(value = "卡分类", example = "期限卡")
		private String cardTypeName;

		@ApiModelProperty(value = "计费类型 ", example = "HALFYEARCARD")
		private String chargeType;
		
		@ApiModelProperty(value = "计费类型 (次卡/周期卡)", example = "季卡")
		private String cardExpireName;

		@ApiModelProperty(value = "有效周期(单位：天)", example = "5")
		private int periodOfValidity;

		@ApiModelProperty(value = "有效次数", example = "20")
		private Integer totalNum;

		@ApiModelProperty(value = "售价", example = "200")
		private Double money;

		@ApiModelProperty(value = "状态")
		private String status;
		
		@ApiModelProperty(value = "状态(启用/停用)")
		private String cardStatusName;
		
		@ApiModelProperty(value = "创建时间")
		private String createTime;
		
		@ApiModelProperty(value = "上架时间")
		private String beginTime;
		
		@ApiModelProperty(value = "下架时间")
		private String endTime;
		@ApiModelProperty(value = "卡图片")
		private String cardImage;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;
		
		
		public String getCardImage() {
			return cardImage;
		}

		public void setCardImage(String cardImage) {
			this.cardImage = cardImage;
		}

		public String getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getCardTypeName() {
			return cardTypeName;
		}

		public void setCardTypeName(String cardTypeName) {
			this.cardTypeName = cardTypeName;
		}

		public String getCardStatusName() {
			return cardStatusName;
		}

		public void setCardStatusName(String cardStatusName) {
			this.cardStatusName = cardStatusName;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

		public String getCardName() {
			return cardName;
		}

		public void setCardName(String cardName) {
			this.cardName = cardName;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public String getChargeType() {
			return chargeType;
		}

		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
		}

		public int getPeriodOfValidity() {
			return periodOfValidity;
		}

		public void setPeriodOfValidity(int periodOfValidity) {
			this.periodOfValidity = periodOfValidity;
		}


		public Integer getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getCardExpireName() {
			return cardExpireName;
		}

		public void setCardExpireName(String cardExpireName) {
			this.cardExpireName = cardExpireName;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}

	}
}
