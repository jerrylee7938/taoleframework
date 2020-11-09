package com.taole.member.domain.param.MemberCardService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveCardIndoByNo {
	@ApiModel(value = "通过卡号获取卡信息详情请求参数")
	public static class MemberCardServiceRetrieveCardIndoByNoReq {

		@ApiModelProperty(value = "卡号", example = "765")
		private String cardNo;

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

	}

	@ApiModel(value = "通过卡号获取卡信息详情返回参数")
	public static class MemberCardServiceRetrieveCardIndoByNoResp {
		@ApiModelProperty(value = "卡号")
		private String cardNo;
		
		@ApiModelProperty(value = "次数")
		private String cardNum;
		
		@ApiModelProperty(value = "经办人所在店ID")
		private String operatorShopId;

		@ApiModelProperty(value = "截止日期", example = "2019-11-06 12:13:14")
		private Date deadline;
		
		@ApiModelProperty(value = "卡状态", example = "1")
		private String status;
		@ApiModelProperty(value = "开卡日期", example = "2019-11-06 12:13:14")
		private Date approverTime;	
		
		@ApiModelProperty(value = "姓名")
		private String name;
		
		public String getCardNum() {
			return cardNum;
		}

		public void setCardNum(String cardNum) {
			this.cardNum = cardNum;
		}

		public String getOperatorShopId() {
			return operatorShopId;
		}

		public void setOperatorShopId(String operatorShopId) {
			this.operatorShopId = operatorShopId;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public Date getDeadline() {
			return deadline;
		}

		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getApproverTime() {
			return approverTime;
		}

		public void setApproverTime(Date approverTime) {
			this.approverTime = approverTime;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
