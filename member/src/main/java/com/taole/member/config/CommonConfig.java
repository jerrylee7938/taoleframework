package com.taole.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {

	/**
	 * 根据字典ID获取portal系统字典详情
	 */
	@Value("#{configProperties['common.portal.dictionary.properties.url']}")
	private String portalDictRetrieve;

	public String getPortalDictRetrieve() {
		return portalDictRetrieve;
	}

	public void setPortalDictRetrieve(String portalDictRetrieve) {
		this.portalDictRetrieve = portalDictRetrieve;
	}
	
	/**
	 * 获取头像路径
	 */
	@Value("#{configProperties['tk.file.nfs.download.url']}")
	private String getAvatarUrl;

	public String getGetAvatarUrl() {
		return getAvatarUrl;
	}

	public void setGetAvatarUrl(String getAvatarUrl) {
		this.getAvatarUrl = getAvatarUrl;
	}
	@Value("#{configProperties['get.portal.userInfo.url']}")
	private String getUserInfoUrl;

	public String getGetUserInfoUrl() {
		return getUserInfoUrl;
	}

	public void setGetUserInfoUrl(String getUserInfoUrl) {
		this.getUserInfoUrl = getUserInfoUrl;
	}
	
	
}
