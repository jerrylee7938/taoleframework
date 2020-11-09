package com.taole.member.domain.param.GoodsInfoService;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Create {
	@ApiModel(value = "新增商品信息请求参数")
	public static class GoodsInfoServiceCreateReq {

		@ApiModelProperty(value = "商品名称", example = "可乐")
		private String name;

		@ApiModelProperty(value = "商品编码", example = "1")
		private String goodsCode;

		@ApiModelProperty(value = "商品分类ID", example = "3213")
		private String catalogId;

		@ApiModelProperty(value = "商品售价", example = "600")
		private Double saleMoney;
		
		@ApiModelProperty(value = "销价单位", example = "次")
		private String unit;

		@ApiModelProperty(value = "商品描述")
		private String description;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "有效截止日期")
		private String deadline;
		
		@ApiModelProperty(value = "商品原价")
		private Double originalCost;
		
		@ApiModelProperty(value = "购卡限制")
		private Integer maxnumber;
		
		@ApiModelProperty(value = "开始日期")
		private String beginDate;
		
		@ApiModelProperty(value = "结束日期")
		private String endDate;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "线上是否可售 1可售 0不可售")
		private String onlineBuy;
		
		@ApiModelProperty(value = "首页配置类别ID")
		private List<String> homeTypes;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;


		public Integer getMaxnumber() {
			return maxnumber;
		}

		public void setMaxnumber(Integer maxnumber) {
			this.maxnumber = maxnumber;
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


		public Double getSaleMoney() {
			return saleMoney;
		}

		public void setSaleMoney(Double saleMoney) {
			this.saleMoney = saleMoney;
		}

		public Double getOriginalCost() {
			return originalCost;
		}

		public void setOriginalCost(Double originalCost) {
			this.originalCost = originalCost;
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


		public String getDeadline() {
			return deadline;
		}

		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}

		public String getBeginDate() {
			return beginDate;
		}

		public void setBeginDate(String beginDate) {
			this.beginDate = beginDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getOnlineBuy() {
			return onlineBuy;
		}

		public void setOnlineBuy(String onlineBuy) {
			this.onlineBuy = onlineBuy;
		}

		public List<String> getHomeTypes() {
			return homeTypes;
		}

		public void setHomeTypes(List<String> homeTypes) {
			this.homeTypes = homeTypes;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}

	@ApiModel(value = "新增商品信息返回参数")
	public static class GoodsInfoServiceCreateResp {
		@ApiModelProperty(value = "商品Id")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称", example = "可乐")
		private String name;

		@ApiModelProperty(value = "商品编码", example = "1")
		private String goodsCode;

		@ApiModelProperty(value = "商品分类ID", example = "3213")
		private String catalogId;

		@ApiModelProperty(value = "商品售价", example = "600")
		private Double saleMoney;

		@ApiModelProperty(value = "销价单位", example = "次")
		private String unit;

		@ApiModelProperty(value = "商品描述")
		private String description;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "有效截止日期")
		private String deadline;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "创建时间")
		private String createTime;
		
		@ApiModelProperty(value = "修改时间")
		private String updateTime;

		@ApiModelProperty(value = "商品原价")
		private Double originalCost;
		
		@ApiModelProperty(value = "购卡限制")
		private Integer maxnumber;
		
		@ApiModelProperty(value = "开始日期")
		private String beginDate;
		
		@ApiModelProperty(value = "结束日期")
		private String endDate;

		public Integer getMaxnumber() {
			return maxnumber;
		}

		public void setMaxnumber(Integer maxnumber) {
			this.maxnumber = maxnumber;
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


		public Double getSaleMoney() {
			return saleMoney;
		}

		public void setSaleMoney(Double saleMoney) {
			this.saleMoney = saleMoney;
		}

		public Double getOriginalCost() {
			return originalCost;
		}

		public void setOriginalCost(Double originalCost) {
			this.originalCost = originalCost;
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


		public String getDeadline() {
			return deadline;
		}

		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}

		public String getBeginDate() {
			return beginDate;
		}

		public void setBeginDate(String beginDate) {
			this.beginDate = beginDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
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
