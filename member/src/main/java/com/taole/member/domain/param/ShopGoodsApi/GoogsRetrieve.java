package com.taole.member.domain.param.ShopGoodsApi;

import io.swagger.annotations.ApiModelProperty;

public class GoogsRetrieve {
	
	public static class ShopGoodsApiGoogsRetrieveReq {
		@ApiModelProperty(value = "店铺ID", required = true)
		private String shopId;
		
		@ApiModelProperty(value = "商品Id", required = true)
		private String goodsId;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}
	}

	public class ShopGoodsApiGoogsRetrieveRsp {
		@ApiModelProperty(value = "商品ID")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称")
		private String name;
		
		@ApiModelProperty(value = "商品价格")
		private Double money;
		
		@ApiModelProperty(value = "描述")
		private String description;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "剩余数量")
		private Integer balance;
		@ApiModelProperty(value = "商品原价")
		private String originalCost;
		@ApiModelProperty(value = "限购数量")
		private Integer maxnumner;
		
		public Integer getMaxnumner() {
			return maxnumner;
		}

		public void setMaxnumner(Integer maxnumner) {
			this.maxnumner = maxnumner;
		}

		public String getOriginalCost() {
			return originalCost;
		}

		public void setOriginalCost(String originalCost) {
			this.originalCost = originalCost;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Integer getBalance() {
			return balance;
		}

		public void setBalance(Integer balance) {
			this.balance = balance;
		}
	}
}
