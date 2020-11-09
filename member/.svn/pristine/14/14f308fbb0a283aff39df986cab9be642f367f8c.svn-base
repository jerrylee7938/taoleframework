package com.taole.member.domain.param.MemberCardApi;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Recharge {

	public static class MemberCardApiRechargeReq {
		
		@ApiModelProperty(value = "店铺ID")
		private String shopId;
		
		@ApiModelProperty(value = "用户ID")
		private String userId;
		
		@ApiModelProperty(value = "充值卡信息")
		private List<RechargeMemberCard> memberCards;

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

		public List<RechargeMemberCard> getMemberCards() {
			return memberCards;
		}

		public void setMemberCards(List<RechargeMemberCard> memberCards) {
			this.memberCards = memberCards;
		}
	}
	
	public static class RechargeMemberCard{
		@ApiModelProperty(value = "用户要充值会员卡ID")
		private String userCardId;
		
		@ApiModelProperty(value = "会员卡ID")
		private String cardId;

		public String getUserCardId() {
			return userCardId;
		}

		public void setUserCardId(String userCardId) {
			this.userCardId = userCardId;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
	}
	
	public class MemberCardApiRechargeRsp {
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
