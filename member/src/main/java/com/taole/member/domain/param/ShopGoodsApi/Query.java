package com.taole.member.domain.param.ShopGoodsApi;

import io.swagger.annotations.ApiModelProperty;

public class Query {

	public static class ShopGoodsApiQueryReq{
		
		@ApiModelProperty(value = "商品分类ID")
		private String catalogId;
		
		@ApiModelProperty(value = "店铺ID")
		private String shopId;

		public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
	}
	
	public class ShopGoodsApiQueryRsp {
		
		@ApiModelProperty(value = "商品ID")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称")
		private String goodsName;
		
		@ApiModelProperty(value = "商品价格")
		private Double money;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "商品描述")
		private String description;

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
}
