package com.taole.toolkit.cache.service.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.toolkit.ProjectConfig;
import com.taole.toolkit.cache.manager.CacheManager;

@RestService(name = ProjectConfig.PREFIX + "Cache")
public class CacheService {
	@Autowired
	private CacheManager cacheManager;
	
	@ActionMethod(response = "json")
	public Object refreshCache(RequestParameters params) {
		// 清理缓存
		cacheManager.clearAllCache();

		return new ServiceResult(ReturnResult.SUCCESS);
	}
}
