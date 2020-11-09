package com.taole.member.domain.param.CardInfoService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Update {
	@ApiModel(value = "修改会员卡信息请求参数")
	public static class CardInfoServiceUpdateReq {

		@ApiModelProperty(value = "卡名称", example = "金卡")
		private String cardName;

		@ApiModelProperty(value = "卡分类", example = "期限卡")
		private String cardType;

		@ApiModelProperty(value = "计费类型 (次卡/周期卡)", example = "季卡")
		private String chargeType;

		@ApiModelProperty(value = "有效周期(单位：天)", example = "5")
		private int periodOfValidity;

		@ApiModelProperty(value = "有效次数", example = "20")
		private int totalNum;

		@ApiModelProperty(value = "售价", example = "200")
		private Double money;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "code", example = "1")
		private String code;
		
		@ApiModelProperty(value = "卡图片")
		private String cardImage;
		
		@ApiModelProperty(value = "线上是否显示1显示 0不显示")
		private String onlineShow;
		
		@ApiModelProperty(value = "显示是否可以充值 1可以 0不可以")
		private String onlineRecharge;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;

		public String getCardImage() {
			return cardImage;
		}

		public void setCardImage(String cardImage) {
			this.cardImage = cardImage;
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

		public int getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(int totalNum) {
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

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getOnlineShow() {
			return onlineShow;
		}

		public void setOnlineShow(String onlineShow) {
			this.onlineShow = onlineShow;
		}

		public String getOnlineRecharge() {
			return onlineRecharge;
		}

		public void setOnlineRecharge(String onlineRecharge) {
			this.onlineRecharge = onlineRecharge;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}

	@ApiModel(value = "修改会员卡信息返回参数")
	public static class CardInfoServiceUpdateResp {
		@ApiModelProperty(value = "卡Id")
		private String cardId;
		@ApiModelProperty(value = "卡名称", example = "金卡")
		private String cardName;

		@ApiModelProperty(value = "卡分类", example = "期限卡")
		private String cardType;

		@ApiModelProperty(value = "计费类型 (次卡/周期卡)", example = "季卡")
		private String chargeType;

		@ApiModelProperty(value = "有效周期(单位：天)", example = "5")
		private int periodOfValidity;

		@ApiModelProperty(value = "有效次数", example = "20")
		private int totalNum;

		@ApiModelProperty(value = "售价", example = "200")
		private Double money;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "创建时间")
		private String createTime;
		
		@ApiModelProperty(value = "code")
		private String code;
		
		@ApiModelProperty(value = "卡图片")
		private String cardImage;
		
		
		public String getCardImage() {
			return cardImage;
		}

		public void setCardImage(String cardImage) {
			this.cardImage = cardImage;
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

		public int getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(int totalNum) {
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

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}


	}
}
