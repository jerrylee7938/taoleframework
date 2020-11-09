package com.taole.member.domain.param.UserApi;

import io.swagger.annotations.ApiModelProperty;

public class UserGoodsRetrieve {

	public static class UserApiUserGoodsRetrieveRsp {
		@ApiModelProperty(value = "购买商品名称")
		private String goodsName;
		
		@ApiModelProperty(value = "购买商品数量")
		private Integer num;

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}
	}
}
