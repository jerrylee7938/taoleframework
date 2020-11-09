package com.taole.member.domain.param.ShopGoodsApi;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class BuyGoods {

	public static class ShopGoodsApiBuyGoodsReq {
		
		@ApiModelProperty(value = "店铺ID", required = true)
		private String shopId;
		
		@ApiModelProperty(value = "用户ID", required = true)
		private String userId;
		
		@ApiModelProperty(value = "商品信息列表", required = true)
		private List<OrderGoods> goods;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public List<OrderGoods> getGoods() {
			return goods;
		}

		public void setGoods(List<OrderGoods> goods) {
			this.goods = goods;
		}
	}
	
	@ApiModel(value = "订单会员卡信息")
	public static class OrderGoods{
		
		@ApiModelProperty(value = "商品ID", required = true)
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称", required = true)
		private String goodsName;
		
		@ApiModelProperty(value = "商品金额", required = true)
		private Double money;
		
		@ApiModelProperty(value = "商品量", required = true)
		private Integer num;

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

		public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}
	}
	
	public class ShopGoodsApiBuyGoodsRsp {
		
		@ApiModelProperty(value = "支付订单返回信息", required = true)
		private data data;

		public data getData() {
			return data;
		}

		public void setData(data data) {
			this.data = data;
		}
	}
	
	public class data{
		@ApiModelProperty(value = "流水号(用户系统内唯一)", required = true)
		private String requestNum;
		
		@ApiModelProperty(value = "支付流水号", required = true)
		private String bankRequestNum;
		
		@ApiModelProperty(value = "订单编号", required = true)
		private String orderNum;
		
		@ApiModelProperty(value = "支付信息", required = true)
		private BankRequest bankRequest;

		public String getRequestNum() {
			return requestNum;
		}

		public void setRequestNum(String requestNum) {
			this.requestNum = requestNum;
		}

		public String getBankRequestNum() {
			return bankRequestNum;
		}

		public void setBankRequestNum(String bankRequestNum) {
			this.bankRequestNum = bankRequestNum;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public BankRequest getBankRequest() {
			return bankRequest;
		}

		public void setBankRequest(BankRequest bankRequest) {
			this.bankRequest = bankRequest;
		}
	}
	
	public class BankRequest{
		@ApiModelProperty(value = "随机字符串", required = true)
		private String NONCESTR;
		
		@ApiModelProperty(value = "公众号Id", required = true)
		private String APPID;
		
		@ApiModelProperty(value = "订单详情扩展字符串", required = true)
		private String PACKAGE;
		
		@ApiModelProperty(value = "时间戳", required = true)
		private String TIMESTAMP;
		
		@ApiModelProperty(value = "签名方式", required = true)
		private String SIBGTYPE;
		
		@ApiModelProperty(value = "签名", required = true)
		private String PAYSIGN;

		public String getNONCESTR() {
			return NONCESTR;
		}

		public void setNONCESTR(String nONCESTR) {
			NONCESTR = nONCESTR;
		}

		public String getAPPID() {
			return APPID;
		}

		public void setAPPID(String aPPID) {
			APPID = aPPID;
		}

		public String getPACKAGE() {
			return PACKAGE;
		}

		public void setPACKAGE(String pACKAGE) {
			PACKAGE = pACKAGE;
		}

		public String getTIMESTAMP() {
			return TIMESTAMP;
		}

		public void setTIMESTAMP(String tIMESTAMP) {
			TIMESTAMP = tIMESTAMP;
		}

		public String getSIBGTYPE() {
			return SIBGTYPE;
		}

		public void setSIBGTYPE(String sIBGTYPE) {
			SIBGTYPE = sIBGTYPE;
		}

		public String getPAYSIGN() {
			return PAYSIGN;
		}

		public void setPAYSIGN(String pAYSIGN) {
			PAYSIGN = pAYSIGN;
		}
	}
}
