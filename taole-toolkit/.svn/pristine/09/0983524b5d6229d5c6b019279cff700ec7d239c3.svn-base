/**
 * Project:taole-toolkit
 * File:ConfigService.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.config.service.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.service.ServiceResult;
import com.taole.toolkit.config.manager.ConfigManager;

/**
 * @author zhanzy
 * @version $Id: ConfigService.java 16 2014-01-17 17:58:24Z yedf $
 */
@RestService(name = "tk.Config")
public class ConfigService {
	@Autowired
	ConfigManager configManager;

	@SuppressWarnings("unused")
	private class ConfigEntity {
		private String name;
		private Object value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	};

	@ActionMethod(response = "json")
	public Object meta() {
		JSONObject json = new JSONObject();
		try {
			json.put("entityName", "tk.Config");
			JSONArray array = new JSONArray();
			json.put("fields", array);
			json.getJSONArray("fields").put(new JSONObject().put("name", "name").put("type", "string"));
			json.getJSONArray("fields").put(new JSONObject().put("name", "value").put("type", "object"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@ActionMethod(response = "json")
	public Object retrieve(@ResourceVariable String key) {
		Object configEntity = configManager.getConfig(key, null);
		if (configEntity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		return configEntity;
	}

	@ActionMethod(response = "ext-grid")
	public Object query(RequestParameters requestParameters) {
		Collection<String> colls = configManager.getConfigKeys();
		List<ConfigEntity> list = new ArrayList<ConfigEntity>();
		for (String key : colls) {
			if (!StringUtils.equals(key, "_id")) {
				ConfigEntity obj = new ConfigEntity();
				obj.setName(key);
				Object value = configManager.getConfig(key, null);
				obj.setValue(value);
				list.add(obj);
			}
		}
		return list;
	}

	@ActionMethod(response = "json")
	public Object updateAndCreate(RequestParameters requestParameters) {
		String oldkey = requestParameters.getParameter("oldkey", String.class);
		String key = requestParameters.getParameter("name", String.class);
		Object value = requestParameters.getParameter("value", Object.class);
		if (StringUtils.isBlank(key)) {
			return new ServiceResult(ReturnResult.FAILURE);
		}
		if (StringUtils.isNotBlank(oldkey)) {
			Object object = configManager.getConfig(oldkey, null);
			if (object != null) {
				configManager.removeConfig(oldkey);
			}
		}
		configManager.setConfig(key, value);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(response = "json")
	public Object delete(RequestParameters requestParameters) {
		String key = requestParameters.getParameter("name", String.class);
		if (StringUtils.isBlank(key)) {
			return new ServiceResult(ReturnResult.FAILURE);
		}
		configManager.removeConfig(key);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

}
