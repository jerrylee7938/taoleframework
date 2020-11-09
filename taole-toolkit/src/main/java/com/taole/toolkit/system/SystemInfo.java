/*
 * @(#)SystemInfo.java 0.1 Apr 10, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.system;

import java.util.Date;

import com.taole.framework.bean.DomainObject;

public class SystemInfo implements DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1772037583081504270L;
	
	private String productId; // 产品ID
	private String productName; // 产品名称
	private String productVersion; // 产品版本
	private String buildId; // Build ID
	private String description; // 说明
	private String copyright; // 版权
	private String user; // 授权用户
	private String organization; // 授权组织;
	private String machineCode; // 机器码
	private String productKey; // 产品密钥
	private String systemName; // 系统名称
	private String logo; // LOGO
	private Date installDate; // 安装日期
	private boolean actived;	// 激活

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

}
