package com.taole.member.domain.param.UserApi;

import io.swagger.annotations.ApiModelProperty;

public class WxLogin {

	public static class UserApiWxLoginReq{
		@ApiModelProperty(value = "微信用户code", required = true)
		private String code;
		
		@ApiModelProperty(value = "微信用户信息加密串", required = true)
		private String encryptedData;
		
		@ApiModelProperty(value = "偏移量", required = true)
		private String iv;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getEncryptedData() {
			return encryptedData;
		}

		public void setEncryptedData(String encryptedData) {
			this.encryptedData = encryptedData;
		}

		public String getIv() {
			return iv;
		}

		public void setIv(String iv) {
			this.iv = iv;
		}
	}
	
	public class UserApiWxLoginRsp{
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
