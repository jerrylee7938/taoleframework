package com.taole.member.domain.param.ShopCardApi;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Query {

	public static class ShopCardApiQueryReq {
		@ApiModelProperty(value = "所属店铺ID")
		private String shopId;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
	}
	
	public class ShopCardApiQueryRsp {
		@ApiModelProperty(value = "会员卡分类ID")
		private String id;
		
		@ApiModelProperty(value = "会员卡分类名称")
		private String name;
		
		@ApiModelProperty(value = "会员卡")
		private List<Card> cards;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Card> getCards() {
			return cards;
		}

		public void setCards(List<Card> cards) {
			this.cards = cards;
		}
	}
	
	public class Card {

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
		
		@ApiModelProperty(value = "有效期")
		private Integer periodOfValidity;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;
		
		@ApiModelProperty(value = "限购数量")
		private Integer maxnumner;


		public Integer getMaxnumner() {
			return maxnumner;
		}

		public void setMaxnumner(Integer maxnumner) {
			this.maxnumner = maxnumner;
		}

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

		public Integer getPeriodOfValidity() {
			return periodOfValidity;
		}

		public void setPeriodOfValidity(Integer periodOfValidity) {
			this.periodOfValidity = periodOfValidity;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}
}
