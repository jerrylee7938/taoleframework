package com.taole.member.domain.param.UserApi;

import io.swagger.annotations.ApiModelProperty;

public class Login {

	public static class UserApiLoginReq{
		
		@ApiModelProperty(value = "登陆手机号", required = true)
		private String mobileNum;
		
		@ApiModelProperty(value = "手机验证码", required = true)
		private String verifyCode;

		public String getMobileNum() {
			return mobileNum;
		}

		public void setMobileNum(String mobileNum) {
			this.mobileNum = mobileNum;
		}

		public String getVerifyCode() {
			return verifyCode;
		}

		public void setVerifyCode(String verifyCode) {
			this.verifyCode = verifyCode;
		}
	}
	
	public class UserApiLoginRsp{
		@ApiModelProperty(value = "用户ID")
		private String id;
		
		@ApiModelProperty(value = "用户姓名")
		private String name;
		
		@ApiModelProperty(value = "手机号")
		private String telphone;
		
		@ApiModelProperty(value = "头像")
		private String avatar;
		
		@ApiModelProperty(value = "性别0 未知 1 男 2 女")
		private String gender;
		
		@ApiModelProperty(value = "生日")
		private String birthday;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTelphone() {
			return telphone;
		}

		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
	}
}
