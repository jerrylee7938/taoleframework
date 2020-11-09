package com.taole.member.domain.param.ShopCardApi;

import io.swagger.annotations.ApiModelProperty;

public class QueryForRecharge {

	public static class ShopCardApiQueryForRechargeReq {
		@ApiModelProperty(value = "所属店铺ID")
		private String shopId;
		
		@ApiModelProperty(value = "卡类型")
		private String type;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	public class ShopCardApiQueryForRechargeRsp {
		@ApiModelProperty(value = "会员卡ID")
		private String cardId;
		
		@ApiModelProperty(value = "会员卡名称")
		private String crrdName;
		
		@ApiModelProperty(value = "会员卡编号")
		private String code;
		
		@ApiModelProperty(value = "会员卡价格")
		private Double money;
		
		@ApiModelProperty(value = "会员卡图片")
		private String cardImage;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

		public String getCrrdName() {
			return crrdName;
		}

		public void setCrrdName(String crrdName) {
			this.crrdName = crrdName;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getCardImage() {
			return cardImage;
		}

		public void setCardImage(String cardImage) {
			this.cardImage = cardImage;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}
}
