/*
 * @(#)SystemInfoService.java 0.1 Apr 10, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.system;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.bind.annotation.ParameterObject;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.JSONTransformer;

/**
 */
@RestService(name = "tk.SystemInfo")
public class SystemInfoService {

	@Autowired
	private SystemInfoManager systemInfoManager;

	@ActionMethod(response = "json")
	public ServiceResult updateSystemInfo(@ParameterObject JSONObject updateObj) {
		SystemInfo info = JSONTransformer.transformJso2Pojo(updateObj, SystemInfo.class);
		SystemInfo object = systemInfoManager.getCurrentSystemInfo();
		DomainObjectUtils.updateDomainObjectBasePropertiesByAnother(object, info);
		systemInfoManager.updateCurrentSystemInfo();
		return new ServiceResult(1, "update success.");
	}

	@ActionMethod(response = "json")
	public ServiceResult getSystemInfo(RequestParameters requestParameters) {
		SystemInfo object = systemInfoManager.getCurrentSystemInfo();
		return new ServiceResult(1, "get success.", object);
	}

	public int checkProductKey(String productId, String user, String machineCode, String productKey) {
		System.out.println("Check Product Key: " + productId + ", " + user + ", " + machineCode + ", " + productKey);
		int r = SystemInfoPolicy.checkProductKey(productId, user, machineCode, productKey);
		if (r == 1) {
			systemInfoManager.getCurrentSystemInfo().setActived(true);
			systemInfoManager.getCurrentSystemInfo().setUser(user);
			systemInfoManager.getCurrentSystemInfo().setProductKey(productKey);
			systemInfoManager.updateCurrentSystemInfo();
		}
		return r;
	}

}
