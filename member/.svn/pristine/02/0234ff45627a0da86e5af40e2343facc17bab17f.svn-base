package com.taole.member.domain.param.GoodsBillDetail;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class OutStore {
	@ApiModel(value = "商品出库请求参数")
	public static class GoodsBillDetailServiceOutStoreReq {

		private List<GoodsValue> goodsValues;

		@ApiModelProperty(value = "出库单号", example = "98765432")
		private String shopBillNo;

		@ApiModelProperty(value = "操作人ID", example = "543456893tr1")
		private String operator;

		@ApiModelProperty(value = "店ID", example = "JCK673678")
		private String shopId;

		@ApiModelProperty(value = "出库备注", example = "商品入库")
		private String description;


		public List<GoodsValue> getGoodsValues() {
			return goodsValues;
		}

		public void setGoodsValues(List<GoodsValue> goodsValues) {
			this.goodsValues = goodsValues;
		}

		public String getShopBillNo() {
			return shopBillNo;
		}

		public void setShopBillNo(String shopBillNo) {
			this.shopBillNo = shopBillNo;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}



	}
	@ApiModel(value = "商品出库请求数据")
	public static class GoodsValue {

		@ApiModelProperty(value = "商品ID", example = "4a1b710f737d43d2b4d818855c8539bf")
		private String goodsId;

		@ApiModelProperty(value = "数量")
		private Double amount;

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

	}

	@ApiModel(value = "商品出库返回参数")
	public static class GoodsBillDetailServiceOutStoreResp {

		@ApiModelProperty(value = "平台返回码 ", example = "1", required = true)
		private String code;

		@ApiModelProperty(value = "平台返回码说明 ", example = "修改模板元素成功", required = true)
		private String description;

		@ApiModelProperty(value = "ext组件专用标志 ", example = "true", required = true)
		private boolean structSupport;

		@ApiModelProperty(value = "返回结果 ", example = "null", required = false)
		private boolean result;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isStructSupport() {
			return structSupport;
		}

		public void setStructSupport(boolean structSupport) {
			this.structSupport = structSupport;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

	}
}
