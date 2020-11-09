package com.taole.member.service.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.taole.dataprivilege.manager.UserDataPrivilegeManager;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.member.ProjectConfig;

@Controller
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "Test")
public class TestService {
	
	private Logger logger = LoggerFactory.getLogger(TestService.class);

	public Object getDataPrivilegeDemo(HttpServletResponse response, HttpServletRequest request){
		try {
			//获取当前登录用户ID
			String userId = UserDataPrivilegeManager.getUserId(request);
			logger.info("当前登录用户ID==============" + userId);
			
			//获取某个用户对应的key的数据权限，key可为空
			JSONObject userDataObj = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, userId);
			logger.info("某个用户对应key的数据权限==============" + userDataObj.toString());
			
			//获取当前登录用户对应的key的数据权限，key可为空
			JSONObject userDataObj1 = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
			logger.info("当前登录用户对应key的数据权限==============" + userDataObj1.toString());
			
			return new ServiceResult(ReturnResult.SUCCESS, "数据获取成功！");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "TestService" + ".getDataPrivilegeDemo:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "数据获取失败: " + e.getMessage());
		}
	}
}
