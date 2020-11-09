package com.taole.member.domain.param.GoodsBillDetail;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query {
	@ApiModel(value = "查詢出入库明细列表请求参数")
	public static class GoodsBillDetailServiceQueryReq {

		@ApiModelProperty(value = "店面id")
		private String shopId;

		@ApiModelProperty(value = "商品类型")
		private String catalogId;
		
		@ApiModelProperty(value = "商品名称")
		private String name;
		
		@ApiModelProperty(value = "商品id")
		private String goodsId;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@ApiModel(value = "查詢出入库明细列表返回参数")
	public static class GoodsBillDetailServiceQueryResp {
		@ApiModelProperty(value = "出入库商品记录id")
		private String gbDetailId;
		@ApiModelProperty(value = "商品ID")
		private String goodsID;

		@ApiModelProperty(value = "数量")
		private Double amount;
		
		@ApiModelProperty(value = "单价")
		private Double price;

		@ApiModelProperty(value = "单位 ")
		private String unit;
		
		@ApiModelProperty(value = "店ID")
		private String shopId;

		@ApiModelProperty(value = "当前库存量")
		private Double balance;

		@ApiModelProperty(value = "创建时间")
		private Date createTime;

		public String getGbDetailId() {
			return gbDetailId;
		}

		public void setGbDetailId(String gbDetailId) {
			this.gbDetailId = gbDetailId;
		}

		public String getGoodsID() {
			return goodsID;
		}

		public void setGoodsID(String goodsID) {
			this.goodsID = goodsID;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}
}
