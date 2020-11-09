package com.taole.member.service.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.Constants.TransactionType;
import com.taole.member.Constants.UserBillApplyStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.MemberCardCondition;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.domain.CardInfo;
import com.taole.member.domain.MemberCard;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.User;
import com.taole.member.domain.UserBillApply;
import com.taole.member.domain.param.UserBillApplyService.Charge;
import com.taole.member.domain.param.UserBillApplyService.Query;
import com.taole.member.manager.CardInfoManager;
import com.taole.member.manager.MemberCardManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.UserBillApplyManager;
import com.taole.member.manager.UserManager;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "会员卡充值审核后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "UserBillApplyService")
@Controller
@RestService(name = ProjectConfig.PREFIX + "UserBillApplyService")
public class UserBillApplyService {
	private Logger logger = LoggerFactory.getLogger(UserBillApplyService.class);
	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "UserBillApply_";

	@Autowired
	private UserBillApplyManager userBillApplyManager;

	@Autowired
	private MemberCardManager memberCardManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private CardInfoManager cardInfoManager;
	
	@Autowired
	private ShopInfoManager shopInfoManager;

	/**
	 * 卡充值 w
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/charge", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "卡充值提交审核", httpMethod = "POST", response = Charge.UserBillApplyServiceChargeResp.class, notes = "卡充值提交审核")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "create.html") })
	@ReturnCodeInfo(value = "charge")
	public @ResponseBody Object charge(HttpServletRequest request,
			@RequestBody Charge.UserBillApplyServiceChargeReq createReq) {
		try {

			if (StringUtils.isEmpty(createReq.getOperatorId())) {
				return new ServiceResult(ReturnResult.FAILURE, "操作人id不能为空！");
			}
			if (StringUtils.isEmpty(createReq.getOperator())) {
				return new ServiceResult(ReturnResult.FAILURE, "操作人姓名不能为空！");
			}
			if (createReq.getCardAmount() == null) {
				return new ServiceResult(ReturnResult.FAILURE, "充值张数不能为空！");
			}
			// 定义充值卡片张数
			Integer cardAmount = createReq.getCardAmount();
			if (cardAmount == null || cardAmount < 1) {
				return new ServiceResult(ReturnResult.FAILURE, "充值张数参数必须大于1");
			}
			UserBillApply userBillApply = new UserBillApply();
			MemberCardCondition memberCardCondition = new MemberCardCondition();
			memberCardCondition.setUserCardId(createReq.getUserCardId());
			MemberCard memberCard = memberCardManager.findByCondition(memberCardCondition);
			userBillApply.setCardId(memberCard.getCardId());
			userBillApply.setShopId(createReq.getShopId());
			userBillApply.setAmount(createReq.getCardAmount());
			userBillApply.setUserCardId(createReq.getUserCardId());
			userBillApply.setTransType(TransactionType.CZ);
			userBillApply.setMoney(createReq.getConsumeMoney());
			userBillApply.setOperatorId(createReq.getOperatorId());
			userBillApply.setOperatorName(createReq.getOperator());
			userBillApply.setOperateTime(new Date());
			userBillApply.setPayType(createReq.getPayType());
			userBillApply.setDescription(createReq.getDescription());
			userBillApply.setChargeNo(createReq.getChargeNo());
			userBillApply.setStatus(UserBillApplyStatus.TOBEAUDITED);

			String applyId = userBillApplyManager.createUserBillApply(userBillApply);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(userBillApply);
			jsonObject.put("applyId", applyId);
			return new ServiceResult(ReturnResult.SUCCESS, "充值审核提交成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "UserBillApplyService" + ".charge:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "充值审核提交失败: " + e.getMessage());
		}
	}

	@RequestMapping(value = "collection" + "/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询充值信息", httpMethod = "GET", response = Query.UserBillApplyServiceQueryResp.class)
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	public @ResponseBody Object query(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.UserBillApplyServiceQueryReq req) {

		try {
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");
			String createTimeStart = req.getTimeStart();
			String createTimeEnd = req.getTimeEnd();
			String cardNo = req.getCardNo();
			String shopId = req.getShopId();
			String status = req.getStatus();
			String transType = req.getTransType();

			UserBillApplyCondition condition = new UserBillApplyCondition();
			if (StringUtils.isNotBlank(createTimeStart))
				condition.setStartTime(DateUtil.StringToDate(createTimeStart, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(createTimeEnd))
				condition.setEndTime(DateUtil.StringToDate(createTimeEnd, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotEmpty(cardNo)) {
				condition.setCardNo(cardNo);
			}

			if (StringUtils.isNotEmpty(shopId)) {
				condition.setShopId(shopId);
			}
			
			if (StringUtils.isNotEmpty(transType)) {
				condition.setTransType(transType);;
			}
			
			if (StringUtils.isNotBlank(status))
				condition.setStatus(status);

			JSONArray jsonArray = (JSONArray) userBillApplyManager.searchToJsonBySql(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userBillApplyManager.searchUserBilApplyCount(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "充值信息查询成功", jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "充值信息查询失败" + e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/{id}/retrieve", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取店面信息详情", httpMethod = "GET", response = Query.UserBillApplyServiceQueryResp.class, notes = "获取店面信息详情")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "retrieve.html") })
	@ReturnCodeInfo(value = "Retrieve")
	public Object retrieve(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Query.UserBillApplyServiceQueryReq req) {

		try {
			String userCardId = req.getUserCardId();
			UserBillApplyCondition condition = new UserBillApplyCondition();
			condition.setUserCardId(userCardId);
			UserBillApply userBillApply = userBillApplyManager.findByCondition(condition);
			
			if (null == userBillApply) {
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "没有信息！");
			}
			MemberCard memberCard = memberCardManager.getMemberCard(userCardId);
			User user = userManager.getUser(memberCard.getUserId());
			CardInfo cardInfo = cardInfoManager.getCardInfo(memberCard.getCardId());
			ShopInfo shopInfo = shopInfoManager.getShopInfo(memberCard.getOperatorShopId());
			JSONObject object = new JSONObject();
			Date time = userBillApply.getCreateTime();
			String createTime = DateUtil.DateToString(time, DateStyle.YYYY_MM_DD_HH_MM);
			object.put("createTime", createTime);
			object.put("cardNo", memberCard.getCardNo());
			object.put("cardName", cardInfo.getCardName());
			object.put("username", user.getName());
			object.put("consumeMoney", userBillApply.getMoney());
			object.put("balanceNum", cardInfo.getTotalNum());
			object.put("shopAddress", shopInfo.getAddress());
			object.put("shopTel", shopInfo.getContactTel());
			object.put("shopName", shopInfo.getName());
			
			//JSONObject object = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(userBillApply);
			
			return new ServiceResult(ReturnResult.SUCCESS, "获取信息成功", object);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "CardInfoService" + ".retrieve:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "获取信息失败", e.getMessage());
		}
	}
	
}
