package com.taole.member.domain.param.MemberCardApi;

import io.swagger.annotations.ApiModelProperty;

public class UserMemberCardForRecharge {
	
	public static class MemberCardApiUserMemberCardForRechargeReq{
		@ApiModelProperty(value = "用户ID", required = true)
		private String userId;
		
		@ApiModelProperty(value = "店铺ID", required = true)
		private String shopId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
	}

	public class MemberCardApiUserMemberCardForRechargeRsp{
		
		@ApiModelProperty(value = "用户会员卡ID")
		private String userCardId;
		
		@ApiModelProperty(value = "用户会员卡号")
		private String userCardNo;
		
		@ApiModelProperty(value = "过期时间")
		private String deadline;
		
		@ApiModelProperty(value = "卡名称")
		private String cardName;
		
		@ApiModelProperty(value = "用户会员卡图片")
		private String cardImage;
		
		@ApiModelProperty(value = "用户会员卡类型")
		private String cardType;
		
		@ApiModelProperty(value = "用户会员卡类型名称")
		private String cardTypeName;
		
		@ApiModelProperty(value = "用户会员卡可用次数")
		private Integer blance;
		
		@ApiModelProperty(value = "会员卡ID")
		private String cardId;

		public String getUserCardId() {
			return userCardId;
		}

		public void setUserCardId(String userCardId) {
			this.userCardId = userCardId;
		}

		public String getUserCardNo() {
			return userCardNo;
		}

		public void setUserCardNo(String userCardNo) {
			this.userCardNo = userCardNo;
		}

		public String getDeadline() {
			return deadline;
		}

		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}

		public String getCardName() {
			return cardName;
		}

		public void setCardName(String cardName) {
			this.cardName = cardName;
		}

		public String getCardImage() {
			return cardImage;
		}

		public void setCardImage(String cardImage) {
			this.cardImage = cardImage;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public String getCardTypeName() {
			return cardTypeName;
		}

		public void setCardTypeName(String cardTypeName) {
			this.cardTypeName = cardTypeName;
		}

		public Integer getBlance() {
			return blance;
		}

		public void setBlance(Integer blance) {
			this.blance = blance;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
	}
}
