/*
 * @(#)SystemInfo.java 0.1 Apr 10, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.system;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.util.FormatUtils;
import com.taole.framework.util.UUID;
import com.taole.toolkit.config.manager.ConfigManager;

public class SystemInfoManager {

	@Autowired
	private ConfigManager configManager;
	
	private SystemInfo info;

	public SystemInfo getCurrentSystemInfo () {
		checkAndInitSystemInfo();
		if (info == null) {
			info = new SystemInfo();
			String productId = System.getProperty("com.taole.license.client.product.code");
			if (productId == null) {
				String value = configManager.getConfig("SYS_INFO_ProductId", String.class);
				productId=StringUtils.isBlank(value)?UUID.generateUUID():value;
			} else {
				configManager.setConfig("SYS_INFO_ProductId", productId);
			}
			info.setProductId(productId);
			info.setProductName(configManager.getConfig("SYS_INFO_ProductName", String.class));
			info.setProductVersion(configManager.getConfig("SYS_INFO_ProductVersion", String.class));
			info.setBuildId(configManager.getConfig("SYS_INFO_BuildId", String.class));
			info.setDescription(configManager.getConfig("SYS_INFO_Description", String.class));
			info.setCopyright(configManager.getConfig("SYS_INFO_Copyright", String.class));
			info.setOrganization(configManager.getConfig("SYS_INFO_Organization", String.class));
			info.setUser(configManager.getConfig("SYS_INFO_User", String.class));
			info.setProductKey(configManager.getConfig("SYS_INFO_ProductKey", String.class));
			info.setMachineCode(configManager.getConfig("SYS_INFO_MachineCode", String.class));
			info.setSystemName(configManager.getConfig("SYS_INFO_SystemName", String.class));
			info.setLogo(configManager.getConfig("SYS_INFO_LOGO", String.class));
			info.setActived("true".equals(configManager.getConfig("SYS_INFO_Actived", String.class)));
			String installDate = configManager.getConfig("SYS_INFO_InstallDate", String.class);
			if (installDate != null) {
				info.setInstallDate(FormatUtils.parseDateTime(installDate));
			}
			String machineCode = configManager.getConfig("SYS_INFO_MachineCode", String.class);
			if (machineCode == null) {
				machineCode = SystemInfoPolicy.generateMachineCode();
				configManager.setConfig("SYS_INFO_MachineCode", machineCode);
				info.setMachineCode(machineCode);
			}
		}
		return info;
	}
	
	public void updateCurrentSystemInfo() {
		checkAndInitSystemInfo();
		if (info != null) {
			configManager.setConfig("SYS_INFO_ProductId", info.getProductId());
			configManager.setConfig("SYS_INFO_ProductName",info.getProductName());
			configManager.setConfig("SYS_INFO_ProductVersion",info.getProductVersion());
			configManager.setConfig("SYS_INFO_BuildId",info.getBuildId());
			configManager.setConfig("SYS_INFO_Description",info.getDescription());
			configManager.setConfig("SYS_INFO_Copyright",info.getCopyright());
			configManager.setConfig("SYS_INFO_User",info.getUser());
			configManager.setConfig("SYS_INFO_Organization",info.getOrganization());
			configManager.setConfig("SYS_INFO_ProductKey",info.getProductKey());
			configManager.setConfig("SYS_INFO_SystemName",info.getSystemName());
			configManager.setConfig("SYS_INFO_LOGO",info.getLogo());
			configManager.setConfig("SYS_INFO_MachineCode",info.getMachineCode());
			configManager.setConfig("SYS_INFO_Actived",info.isActived() ? "true" : "false");
			if (info.getInstallDate() != null) {
				configManager.setConfig("SYS_INFO_InstallDate", FormatUtils.formatDateTime(info.getInstallDate()));
			} else {
				configManager.removeConfig("SYS_INFO_InstallDate");
			}
		}
		
	}

	private void checkAndInitSystemInfo () {
		String inited = configManager.getConfig("SYS_INFO_inited", String.class);
		if (!"true".equals(inited)) {
			configManager.setConfig("SYS_INFO_InstallDate", FormatUtils.formatDateTime(new Date()));
			configManager.setConfig("SYS_INFO_inited", "true");
		}
	}
	
}
