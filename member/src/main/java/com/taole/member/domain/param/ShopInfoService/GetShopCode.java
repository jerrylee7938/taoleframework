package com.taole.member.domain.param.ShopInfoService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class GetShopCode {

	@ApiModel(value = "查询店面code返回参数")
	public static class ShopInfoServiceGetShopCodeResp {
		@ApiModelProperty(value = "店面code")
		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
}
