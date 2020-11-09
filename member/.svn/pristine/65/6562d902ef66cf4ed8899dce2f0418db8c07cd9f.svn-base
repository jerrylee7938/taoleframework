package com.taole.member.domain.param.UserBillService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class QueryGoodsInfo {
	@ApiModel(value = "查詢出入库明细列表消費商品请求参数")
	public static class UserBillServiceQueryGoodsInfoReq {

		@ApiModelProperty(value = "流水id")
		private String shopBillId;

		public String getShopBillId() {
			return shopBillId;
		}

		public void setShopBillId(String shopBillId) {
			this.shopBillId = shopBillId;
		}


	}

	@ApiModel(value = "查詢出入库明细消費商品列表返回参数")
	public static class UserBillServiceQueryGoodsInfoResp {
		@ApiModelProperty(value = "出入库商品记录id")
		private String shopBillId;
		@ApiModelProperty(value = "备注")
		private String description;

		@ApiModelProperty(value = "创建时间")
		private Date createTime;

		public String getShopBillId() {
			return shopBillId;
		}

		public void setShopBillId(String shopBillId) {
			this.shopBillId = shopBillId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}
}
