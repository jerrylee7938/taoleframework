package com.taole.member.domain.param.CmsApi;

import io.swagger.annotations.ApiModelProperty;

public class Notice {

	public class CmsApiNoticeRsp {
		@ApiModelProperty(value = "作者")
		private String author;
		
		@ApiModelProperty(value = "公告内容")
		private String description;
		
		@ApiModelProperty(value = "公告发布时间")
		private String createTime;

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
	}
}
