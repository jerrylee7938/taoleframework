package com.taole.member.service.rest;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.dataprivilege.manager.UserDataPrivilegeManager;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.MemberCardCondition;
import com.taole.member.condition.UserCondition;
import com.taole.member.domain.MemberCard;
import com.taole.member.domain.param.UserService.Query;
import com.taole.member.domain.param.UserService.Query4OPerator;
import com.taole.member.manager.MemberCardManager;
import com.taole.member.manager.UserManager;
import com.taole.toolkit.dict.manager.DictionaryManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "用户后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "User")
@Controller
@RestService(name = ProjectConfig.PREFIX + "User")
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "User_";
	@Autowired
	private DictionaryManager dictionaryManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private MemberCardManager memberCardManager;

	@RequestMapping(value = "collection" + "/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询用户列表", httpMethod = "GET", response = Query.UserServiceQueryResp.class)
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	public @ResponseBody Object query(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.UserServiceQueryReq req) {
		System.out.println("=========="+req.toString());
		try {
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");

			UserCondition condition = new UserCondition();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotEmpty(req.getName())) {
				condition.setName(req.getName());
			}
			if (StringUtils.isNotEmpty(req.getTelphone())) {
				condition.setTelphone(req.getTelphone());
			}

			JSONArray jsonArray = (JSONArray) userManager.searchToJson(condition, range, sorter);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "用户信息查询成功", jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "用户信息查询失败" + e.getMessage());
		}

	}
	
	@RequestMapping(value = "collection" + "/query4Operator", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询用户列表", httpMethod = "GET", response = Query4OPerator.UserServiceQuery4OPeratorResp.class)
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query4Operator.html") })
	public @ResponseBody Object query4Operator(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query4OPerator.UserServiceQuery4OPeratorReq req) {
		System.out.println("=========="+req.toString());
		try {
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");
			JSONObject userDataObj =
					UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
			String[] shopIdArry = memberCardManager.getShopIdByUser(userDataObj);
			
			MemberCardCondition memberCardCondition = new MemberCardCondition();
			memberCardCondition.setOperatorShopIds(shopIdArry);
			List<MemberCard> list = memberCardManager.list(memberCardCondition);
			String[] userIds = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				userIds[i] = list.get(i).getUserId();
			}
				
			UserCondition condition = new UserCondition();
			condition.setIds(userIds);
			condition.setOperatorShopIds(shopIdArry);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotEmpty(req.getName())) {
				condition.setName(req.getName());
			}
			if (StringUtils.isNotEmpty(req.getTelphone())) {
				condition.setTelphone(req.getTelphone());
			}

			JSONArray jsonArray = (JSONArray) userManager.searchToJsonBySql(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "用户信息查询成功", jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "用户信息查询失败" + e.getMessage());
		}

	}
	

}
