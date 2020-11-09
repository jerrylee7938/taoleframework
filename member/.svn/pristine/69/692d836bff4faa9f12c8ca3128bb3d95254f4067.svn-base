package com.taole.member.domain.param.ShopGoodsApi;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class QueryAll {

	public static class ShopGoodsApiQueryAllReq{
		
		@ApiModelProperty(value = "店铺ID")
		private String shopId;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
	}
	
	public class ShopGoodsApiQueryAllRsp {
		@ApiModelProperty(value = "商品分类ID")
		private String id;
		
		@ApiModelProperty(value = "商品分类名称")
		private String name;
		
		@ApiModelProperty(value = "商品列表")
		private List<Goods> goods;

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

		public List<Goods> getGoods() {
			return goods;
		}

		public void setGoods(List<Goods> goods) {
			this.goods = goods;
		}
	}
	
	public class Goods{
		
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
		
		@ApiModelProperty(value = "商品原价")
		private String originalCost;
		
		@ApiModelProperty(value = "商品库存")
		private String balance;
		@ApiModelProperty(value = "限购数量")
		private Integer maxnumner;
		

		public Integer getMaxnumner() {
			return maxnumner;
		}

		public void setMaxnumner(Integer maxnumner) {
			this.maxnumner = maxnumner;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
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
