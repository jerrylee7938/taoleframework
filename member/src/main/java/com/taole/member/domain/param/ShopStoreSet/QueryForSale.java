package com.taole.member.domain.param.ShopStoreSet;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class QueryForSale {
	@ApiModel(value = "查询商店可售商品请求参数")
	public static class ShopStoreSetServiceQueryForSaleReq {

		@ApiModelProperty(value = "商品分类id", example = "76523i76527")
		private String goodsTypeId;
		
		@ApiModelProperty(value = "商店id", example = "76523i76527")
		private String shopId;
		
		@ApiModelProperty(value = "商品名称", example = "可乐")
		private String goodsName;

		public String getGoodsTypeId() {
			return goodsTypeId;
		}

		public void setGoodsTypeId(String goodsTypeId) {
			this.goodsTypeId = goodsTypeId;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

	}

	@ApiModel(value = "查询商店可售商品列表返回参数")
	public static class ShopStoreSetServiceQueryForSaleResp {
		@ApiModelProperty(value = "商品Id")
		private String goodsId;
		
		@ApiModelProperty(value = "店id", example = "43254")
		private String shopId;

		@ApiModelProperty(value = "商品分类ID", example = "3213")
		private String catalogId;

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}

	}
	}
