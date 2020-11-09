package com.taole.member.domain.param.GoodsInfoService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class QueryForSale {
	@ApiModel(value = "查询商店可售商品请求参数")
	public static class GoodsInfoServiceQueryForSaleReq {

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
	public static class GoodsInfoServiceQueryForSaleResp {
		@ApiModelProperty(value = "商品Id")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称", example = "可乐")
		private String name;

		@ApiModelProperty(value = "商品编码", example = "43254")
		private String goodsCode;

		@ApiModelProperty(value = "商品分类ID", example = "3213")
		private String catalogId;

		@ApiModelProperty(value = "商品售价", example = "600")
		private String saleMoney;

		@ApiModelProperty(value = "销价单位", example = "次")
		private String unit;

		@ApiModelProperty(value = "商品描述")
		private String description;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "有效截止日期")
		private Date deadline;
		
		@ApiModelProperty(value = "创建时间")
		private String createTime;
		
		@ApiModelProperty(value = "修改时间")
		private String updateTime;

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

		public String getGoodsCode() {
			return goodsCode;
		}

		public void setGoodsCode(String goodsCode) {
			this.goodsCode = goodsCode;
		}

		public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}

		public String getSaleMoney() {
			return saleMoney;
		}

		public void setSaleMoney(String saleMoney) {
			this.saleMoney = saleMoney;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getDeadline() {
			return deadline;
		}

		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
	}
}
