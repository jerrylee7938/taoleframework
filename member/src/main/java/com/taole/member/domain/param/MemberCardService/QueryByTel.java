package com.taole.member.domain.param.MemberCardService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class QueryByTel {
	@ApiModel(value = "通过手机号查询会员卡请求参数")
	public static class MemberCardServiceQueryByTelReq {

		@ApiModelProperty(value = "telphone", example = "13621293735")
		private String telphone;

		public String getTelphone() {
			return telphone;
		}

		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}

	}

	@ApiModel(value = "通过手机号查询会员卡返回参数")
	public static class MemberCardServiceQueryByTelResp {
		@ApiModelProperty(value = "卡号")
		private String cardNo;

		@ApiModelProperty(value = "次数")
		private String cardNum;

		@ApiModelProperty(value = "经办人所在店ID")
		private String operatorShopId;

		@ApiModelProperty(value = "经办人")
		private String operator;

		@ApiModelProperty(value = "截止日期", example = "2019-11-06 12:13:14")
		private Date deadline;

		@ApiModelProperty(value = "卡状态", example = "1")
		private String status;
		@ApiModelProperty(value = "开卡日期", example = "2019-11-06 12:13:14")
		private Date approverTime;
		@ApiModelProperty(value = "审核日期", example = "2019-11-06 12:13:14")
		private Date auditTime;

		@ApiModelProperty(value = "姓名")
		private String name;
		@ApiModelProperty(value = "性别")
		private String gender;
		@ApiModelProperty(value = "生日")
		private Date birthday;

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

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

		public Date getAuditTime() {
			return auditTime;
		}

		public void setAuditTime(Date auditTime) {
			this.auditTime = auditTime;
		}

	}
}
