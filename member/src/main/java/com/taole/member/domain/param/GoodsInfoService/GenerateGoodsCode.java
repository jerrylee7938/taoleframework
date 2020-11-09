package com.taole.member.domain.param.GoodsInfoService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class GenerateGoodsCode {

	@ApiModel(value = "生成商品编码返回参数")
	public static class GoodsInfoServiceCreateGoodsCodeResp {

		@ApiModelProperty(value = "商品编码", example = "43254")
		private String goodsCode;


		public String getGoodsCode() {
			return goodsCode;
		}

		public void setGoodsCode(String goodsCode) {
			this.goodsCode = goodsCode;
		}


	}
}
