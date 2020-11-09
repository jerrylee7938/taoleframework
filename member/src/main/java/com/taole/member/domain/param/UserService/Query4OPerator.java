package com.taole.member.domain.param.UserService;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query4OPerator {
	@ApiModel(value = "查询用户请求参数")
	public static class UserServiceQuery4OPeratorReq {

		@ApiModelProperty(value = "id")
		private String id;
		
		@ApiModelProperty(value = "名字")
		private String name;
		
		@ApiModelProperty(value = "电话")
		private String telphone;
		
		@ApiModelProperty(value = "操作员id")
		private String operatorId;

		
		public String getOperatorId() {
			return operatorId;
		}

		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
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

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "UserServiceQueryReq [id=" + id + ", name=" + name + ", telphone=" + telphone + "]";
		}

	}

	@ApiModel(value = "查询用户返回参数")
	public static class UserServiceQuery4OPeratorResp {
		@ApiModelProperty(value = "用户id")
		private String id;

		@ApiModelProperty(value = "电话")
		private String telephone;

		@ApiModelProperty(value = "名字")
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
