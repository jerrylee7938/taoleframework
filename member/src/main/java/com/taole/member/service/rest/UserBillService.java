package com.taole.member.service.rest;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.taole.dataprivilege.manager.UserDataPrivilegeManager;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.framework.util.UUID;
import com.taole.member.Constants;
import com.taole.member.Constants.ActionType;
import com.taole.member.Constants.AuditFlag;
import com.taole.member.Constants.CardType;
import com.taole.member.Constants.CustomerType;
import com.taole.member.Constants.InOutType;
import com.taole.member.Constants.MemberCardStatus;
import com.taole.member.Constants.PayType;
import com.taole.member.Constants.ShopBillType;
import com.taole.member.Constants.TransactionType;
import com.taole.member.Constants.UnitType;
import com.taole.member.Constants.UserBillApplyStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.member.domain.CardInfo;
import com.taole.member.domain.GoodsBill;
import com.taole.member.domain.GoodsBillDetail;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.MemberCard;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.User;
import com.taole.member.domain.UserBill;
import com.taole.member.domain.UserBillApply;
import com.taole.member.domain.param.ShopGoodsApi.BuyTicket.OrderTicket;
import com.taole.member.domain.param.UserBillService.Consume;
import com.taole.member.domain.param.UserBillService.Consume.GoodsValue;
import com.taole.member.domain.param.UserBillService.GenerateConsumeBillCode;
import com.taole.member.domain.param.UserBillService.Query;
import com.taole.member.domain.param.UserBillService.QueryGoodsInfo;
import com.taole.member.domain.param.UserBillService.RechargeAudit;
import com.taole.member.domain.param.UserBillService.Retrieve;
import com.taole.member.manager.CardInfoManager;
import com.taole.member.manager.GoodsBillDetailManager;
import com.taole.member.manager.GoodsBillManager;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.MemberCardManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.UserBillApplyManager;
import com.taole.member.manager.UserBillManager;
import com.taole.member.manager.UserManager;
import com.taole.member.utils.RestClientUtil;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.ArrayListUtil;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;
import com.taole.toolkit.util.IdCardUtil;
import com.taole.toolkit.util.ReturnCode;
import com.taole.toolkit.util.biz.ConvertUtil;
import com.taole.toolkit.util.excel.XlsJxlAction;
import com.taole.toolkit.util.excel.XlsObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jxl.write.WritableWorkbook;

@Api(tags = { "会员管理后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "UserBill")
@Controller
@RestService(name = ProjectConfig.PREFIX + "UserBill")
public class UserBillService {
	private Logger logger = LoggerFactory.getLogger(UserBillService.class);
	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "UserBill_";
	@Autowired
	private DictionaryManager dictionaryManager;
	@Autowired
	private UserBillManager userBillManager;

	@Autowired
	private MemberCardManager memberCardManager;

	@Autowired
	private CardInfoManager cardInfoManager;

	@Autowired
	private GoodsBillDetailManager goodsBillDetailManager;

	@Autowired
	private GoodsBillManager goodsBillManager;

	@Autowired
	private GoodsInfoManager goodsInfoManager;

	@Autowired
	private ShopInfoManager shopInfoManager;

	@Autowired
	private UserManager userManager;

	@Autowired
	private UserBillApplyManager userBillApplyManager;
	
	@Autowired
	private MemberCardService_b memberCardService;
	

	/**
	 * 卡充值
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/applyAudit", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "会员卡充值审核", httpMethod = "POST", response = RechargeAudit.UserBillServiceRechargeAuditResp.class, notes = "充值等流程审核接口")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "applyAudit.html") })
	@ReturnCodeInfo(value = "applyAudit")
	public @ResponseBody Object applyAudit(HttpServletRequest request,
			@RequestBody RechargeAudit.UserBillServiceRechargeAuditReq req) {
		try {
			UserBillApplyCondition userBillApplyCondition = new UserBillApplyCondition();
			userBillApplyCondition.setApplyId(req.getApplyId());
			UserBillApply userBillApply = userBillApplyManager.findByCondition(userBillApplyCondition);
			if (userBillApply == null) {
				return new ServiceResult(ReturnResult.FAILURE, "该充值审核信息不存在！");
			}
			if (StringUtils.isBlank(req.getApplyId())) {
				return new ServiceResult(ReturnResult.FAILURE, "审核记录id参数不能为空！");
			}
			
			if(userBillApply.getTransType().equals(TransactionType.CZ)){
				
				// 封装userbill信息
				UserBill userBill = new UserBill();
				userBill.setUserCardId(userBillApply.getUserCardId());
				userBill.setOperatorShopId(userBillApply.getShopId());
				if (StringUtils.isBlank(userBillApply.getOperatorName())) {
					String userId = UserDataPrivilegeManager.getUserId(request);
					JSONObject userInfo = RestClientUtil.getUserInfo(userId);
					String operator = userInfo.getString("realName");
					userBill.setOperator(operator);
				}
				userBill.setOperator(userBillApply.getOperatorName());
				userBill.setPayType(userBillApply.getPayType());
				userBill.setDescription(userBillApply.getDescription());
				userBill.setShopBillId(UUID.generateUUID());
				userBill.setConsumeMoney(userBillApply.getMoney());
				userBill.setConsumeUnit(UnitType.YUAN);
				userBill.setActionTypeId(Constants.TransactionType.CZ);
				userBill.setInOutType(Constants.InOutType.IN);
				userBill.setOperateTime(new Date());
				String billNo = userBillManager.getUserBillNo(Constants.ActionType.CONSUME);
				userBill.setUserBillNo(billNo);
				userBill.setUserBillId(billNo);
				
				// 封装userbillApply信息
				// UserBillApplyCondition userBillApplyCondition2 = new
				// UserBillApplyCondition();
				// userBillApplyCondition2.setUserCardId(req.getUserCardId());
				userBillApply.setAuditorId(req.getAuditorId());
				userBillApply.setAuditorName(req.getAuditorName());
				if (AuditFlag.YES.equals(req.getAuditFlag())) {
					userBillApply.setStatus(UserBillApplyStatus.AUDITPASS);
				}
				if (AuditFlag.NO.equals(req.getAuditFlag())) {
					userBillApply.setStatus(UserBillApplyStatus.AUDITNOTPASS);
				}
				userBillApply.setAuditorTime(new Date());
				userBillApply.setUpdateTime(new Date());
				
				// 封装memberCard信息
				MemberCard memberCard = memberCardManager.getMemberCard(userBillApply.getUserCardId());
				if (memberCard == null) {
					return new ServiceResult(ReturnResult.FAILURE, "无法根据id找到要充值的会员卡: " + userBillApply.getUserCardId());
				}
				memberCard.setUpdateTime(new Date());
				
				
				// 判断充值那种卡
				CardInfo cardInfo = cardInfoManager.getCardInfo(memberCard.getCardId());
				
				// 定义充值卡片张数
				Integer cardAmount = userBillApply.getAmount();
				/*if (cardAmount == null || cardAmount < 1) {
				return new ServiceResult(ReturnResult.FAILURE, "充值张数参数必须大于1");
			}*/
				
				if (cardInfo == null) {
					return new ServiceResult(ReturnResult.FAILURE, "卡信息不存在！");
				}
				if (cardInfo.getCardType().equals(Constants.CardType.TERM)) {
					userBillManager.chargeForQxCard(userBill, memberCard, userBillApply, cardAmount);
				} else if (cardInfo.getCardType().equals(Constants.CardType.TIMES_COUNT)) {
					userBillManager.chargeJcCard(userBill, memberCard, userBillApply, cardAmount);
				} else {
					return new ServiceResult(ReturnResult.FAILURE, "无法识别的会员卡类型: " + cardInfo.getCardType());
				}
				
				return new ServiceResult(ReturnResult.SUCCESS, "会员卡充值成功！");
			}else {
				MemberCard memberCard = memberCardManager.getMemberCard(userBillApply.getUserCardId());
				if (StringUtils.isEmpty(userBillApply.getUserCardId())) {
					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "卡id不能为空！");
				}
				if (memberCard == null){
					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "卡信息不存在！");
				}
				if(AuditFlag.YES.equals(req.getAuditFlag())){
					memberCard.setStatus(MemberCardStatus.TO_BE_ACTIVE);
				}
				if(AuditFlag.NO.equals(req.getAuditFlag())){
					memberCard.setStatus(MemberCardStatus.NOT_APPROVE);
				}
				
				memberCard.setAuditTime(new Date());
				memberCardManager.updateMemberCard(memberCard);
				
				UserBill userBill = new UserBill(); 
				if (StringUtils.isBlank(memberCard.getOperator())) {
					String userId = UserDataPrivilegeManager.getUserId(request);
					JSONObject userInfo = RestClientUtil.getUserInfo(userId);
					String operator = userInfo.getString("realName");
					userBill.setOperator(operator);
				}
				
				String bizNo = memberCardManager.generateCreateCardCode();
				userBill.setUserBillId(bizNo);
				userBill.setConsumeMoney(userBillApply.getMoney());
				userBill.setPayType(userBillApply.getPayType());
				userBill.setDescription(memberCard.getDescription());
				userBill.setOperator(memberCard.getOperator());
				String operatorShopId = memberCard.getOperatorShopId();
				if (StringUtils.isBlank(operatorShopId)){
					return new ServiceResult(ReturnResult.FAILURE, "必须提交店面参数");
				}
				userBill.setOperatorShopId(operatorShopId);
				userBill.setUserBillNo(bizNo);
				userBill.setShopBillId(null);
				userBill.setUserCardId(userBillApply.getUserCardId());
				userBill.setUserId(memberCard.getUserId());
				userBill.setConsumeUnit("YUAN");
				
				CardInfo cardInfo = cardInfoManager.getCardInfo(memberCard.getCardId());
				
				userBill.setBalanceNum(cardInfo.getTotalNum());
				userBill.setBalance(0.0);
				userBill.setActionTypeId("BK");
				userBill.setInOutType("WJWC");
				userBill.setOperateTime(new Date());
				userBill.setCreateTime(new Date());
				String userBillID = userBillManager.createUserBill(userBill);
			
				userBillApply.setStatus(UserBillApplyStatus.AUDITPASS);
				userBillApplyManager.updateUserBillApply(userBillApply);
			}
			return new ServiceResult(ReturnResult.SUCCESS, "会员卡办卡成功！");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "UserBillService" + ".rechargeAudit:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "操作失败,审核未通过: " + e.getMessage());
		}

	}

	@RequestMapping(value = "/collection/generateConsumeBillCode", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "生成消费单据编号", httpMethod = "GET", response = GenerateConsumeBillCode.ConsumeBillDetailCreateGoodsCodeResp.class, notes = "生成入库单据编号")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "generateShopBillCode.html") })
	@ReturnCodeInfo(value = "generateConsumeBillCode")
	public @ResponseBody Object generateConsumeBillCode(HttpServletRequest request) {
		try {
			// String code = IdUtils.generateId();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
			String code = formatter.format(date);
			code = Constants.TransactionType.XF + code;
			return new ServiceResult(ReturnResult.SUCCESS, "生成单据编号成功", code);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "GoodsInfoService" + ".generateGoodsCode:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "生成单据编号失败: " + e.getMessage());
		}
	}

	/**
	 * 消费
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/consume", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "用户消费", httpMethod = "POST", response = Consume.UserBillServiceConsumeResp.class, notes = "会员消费")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "create.html") })
	@ReturnCodeInfo(value = "consume")
	public @ResponseBody Object consume(HttpServletRequest request,
			@RequestBody Consume.UserBillServiceConsumeReq req) {

		if (StringUtils.isBlank(req.getShopId())) {
			return new ServiceResult(ReturnResult.FAILURE, "必须指定消费店面！");
		}

		if (StringUtils.isBlank(req.getPayType())) {
			return new ServiceResult(ReturnResult.FAILURE, "支付方式不能为空！");
		}

		
		ShopInfo shopInfo = shopInfoManager.getShopInfo(req.getShopId());
		if (shopInfo == null) {
			return new ServiceResult(ReturnResult.FAILURE, "无法找到对应店面信息！店面ID：" + req.getShopId());
		}

		if (StringUtils.isBlank(req.getUserBillNo())) {
			return new ServiceResult(ReturnResult.FAILURE, "缺少交易单号参数！");
		}

	/*	List<GoodsValue> goodsValues = req.getGoodsValues();
		for (GoodsValue goodsValue : goodsValues) {
			String goodsId = goodsValue.getGoodsId();
			Double balance = goodsBillDetailManager.getGoodsBalanceNum(req.getShopId(), goodsId);
			if(balance <=  0){
				return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
			}
		}*/
		
		// 建立消费记录对象
		UserBill userBill = new UserBill();
		String billNo = userBillManager.getUserBillNo(Constants.ActionType.CONSUME);
		userBill.setUserBillId(billNo);
		userBill.setUserBillNo(billNo);
		userBill.setUserId(req.getUserId());
		userBill.setUserCardId(req.getUserCardId());
		userBill.setConsumeMoney(req.getConsumeMoney());
		userBill.setSwipeAmount(req.getSwipeAmount());
		userBill.setConsumeUnit(UnitType.YUAN);
		userBill.setActionTypeId(ActionType.CONSUME);
		userBill.setInOutType(InOutType.OUT);
		userBill.setBalance(0.00);
		userBill.setPayType(req.getPayType());// 支付类型不能为空
		if (StringUtils.isBlank(req.getOperator())) {
			String userId = UserDataPrivilegeManager.getUserId(request);
			JSONObject userInfo = RestClientUtil.getUserInfo(userId);
			String operator = userInfo.getString("realName");
			userBill.setOperator(operator);
		}
		userBill.setOperator(req.getOperator());
		userBill.setOperatorShopId(req.getShopId());
		userBill.setOperateTime(new Date());
		userBill.setCreateTime(new Date());
		userBill.setDescription(req.getDescription());

		// 建立出库单对象
		GoodsBill goodsBill = new GoodsBill();
		goodsBill.setShopBillNo(req.getUserBillNo());
		goodsBill.setOperator(req.getOperator());
		goodsBill.setShopId(req.getShopId());
		goodsBill.setShopBillType(ShopBillType.SALEOUT);
		goodsBill.setInOutType(InOutType.OUT);
		goodsBill.setOperatorTime(new Date());
		goodsBill.setDescription(req.getDescription());
		goodsBill.setStatus("");
		goodsBill.setCreateTime(new Date());

		// 建立出库明细对象
		List<GoodsValue> goodsValues = req.getGoodsValues();
		List<GoodsBillDetail> goodsBillDetailList = new ArrayList<GoodsBillDetail>();
		for (GoodsValue goodsValue : goodsValues) {
			String goodsId = goodsValue.getGoodsId();
			Double amount = goodsValue.getAmount();
			Double balance = goodsBillDetailManager.getGoodsBalanceNum(req.getShopId(), goodsId);
			if(balance <=  0){
				return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
			}
			if(amount > balance){
				return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
			}
			GoodsBillDetail goodsBillDetail = new GoodsBillDetail();
			goodsBillDetail.setShopId(req.getShopId());
			GoodsInfo goodsInfo = new GoodsInfo();
			goodsInfo = goodsInfoManager.getGoodsInfo(goodsValue.getGoodsId());
			if (goodsInfo == null) {
				return new ServiceResult(ReturnResult.FAILURE, "无法根据商品ID找到对应商品信息！id:" + goodsValue.getGoodsId());
			}
			goodsBillDetail.setPrice(goodsInfo.getSaleMoney());
			goodsBillDetail.setUnit(goodsInfo.getUnit());
			goodsBillDetail.setGoodsId(goodsValue.getGoodsId());
			goodsBillDetail.setAmount(goodsValue.getAmount());
			goodsBillDetailList.add(goodsBillDetail);
		}

		//String userBillId = "";
		String userBillId  = billNo;
		if (req.getUserType().equals(CustomerType.NOTMEMBER)) {// 散客消费
			if (ArrayListUtil.isEmpty(goodsValues)) {
				return new ServiceResult(ReturnResult.FAILURE, "散客消费的商品列表不能为空！");
			}
			userBill.setUserBillId(billNo);
			userBill.setUserId(CustomerType.NOTMEMBER);// 非会员
			userBillId = userBillManager.consumeForIndividual(userBill, goodsBill, goodsBillDetailList);
		} else if (req.getUserType().equals(CustomerType.MEMBER)) { // 会员消费
			MemberCard memberCard = memberCardManager.getMemberCard(req.getUserCardId());// 获取会员卡记录
			if (memberCard == null) {
				return new ServiceResult(ReturnResult.FAILURE, "卡号为：" + req.getUserCardId() + "的会员卡不存在！");
			}
			if(memberCard.getDeadline() != null){
				
				Date firstDate = new Date();
				Date secondDate = memberCard.getDeadline();
				int compare = DateUtil.compare(firstDate, secondDate);
				if(compare > 0){
					
					Date date = memberCard.getDeadline();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String string = simpleDateFormat.format(date);
					return new ServiceResult(ReturnResult.FAILURE, "该卡已过期！"+ "到期日期为："+string);
				}
			}
			if (req.getSwipeAmount() != null && req.getSwipeAmount() > 0) {// 进行刷卡操作
				String status = memberCard.getStatus();
				if (!(status.equals(MemberCardStatus.TO_BE_ACTIVE) || status.equals(MemberCardStatus.ACTIVE))) {
					return new ServiceResult(ReturnResult.FAILURE, "卡号为：" + req.getUserCardId() + "的会员卡状态不正确，无法刷卡！");
				}
			}
			CardInfo cardInfo = cardInfoManager.getCardInfo(memberCard.getCardId());
			String cardType = cardInfo.getCardType();
			// String chargeType = cardInfo.getChargeType();
			if (cardType.equals(CardType.TERM)) {
				UserBillCondition userBillCondition = new UserBillCondition();
				userBillCondition.setUserCardId(memberCard.getUserCardId());
				String nowDate = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
				userBillCondition.setStartTime(DateUtil.StringToDate(nowDate + " 00:00:00"));
				userBillCondition.setEndTime(DateUtil.StringToDate(nowDate + " 23:59:59"));
				userBillCondition.setActionTypeId(TransactionType.XF);
				UserBill userBill2 = userBillManager.findByCondition(userBillCondition);
				if (userBill2 != null) {
					MemberCard memberCard2 = memberCardManager.getMemberCard(userBill2.getUserCardId());
					return new ServiceResult(ReturnResult.FAILURE,
							"卡号为：" + memberCard2.getCardNo() + "的会员卡为期限卡，当天只能刷一次卡！");
				}
			}
			userBillId = userBillManager.consumeForMember(userBill, goodsBill, goodsBillDetailList, memberCard);
		} else {
			return new ServiceResult(ReturnResult.FAILURE, "消费者类型错误！");
		}
		JSONObject resultJo = new JSONObject();
		resultJo.put("userBillId", userBillId);
		return new ServiceResult(ReturnResult.SUCCESS, "消费成功！", resultJo);

	}

	@RequestMapping(value = "collection" + "/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询流水明細信息", httpMethod = "GET", response = Query.UserBillServiceQueryResp.class)
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	public @ResponseBody Object query(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.UserBillServiceQueryReq req) {

		try {
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");
			String createTimeStart = req.getTimeStart();
			String createTimeEnd = req.getTimeEnd();
			String userId = req.getUserId();
			String cardNo = req.getCardNo();
			String payType = req.getPayType();
			String shopId = req.getShopId();
			String actionTypeId = req.getActionTypeId();

			UserBillCondition condition = new UserBillCondition();
			JSONObject userDataObj = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
			String[] shopIdArry = memberCardManager.getShopIdByUser(userDataObj);
			if (StringUtils.isNotBlank(createTimeStart))
				condition.setStartTime(DateUtil.StringToDate(createTimeStart, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(createTimeEnd))
				condition.setEndTime(DateUtil.StringToDate(createTimeEnd, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(userId))
				condition.setUserId(userId);

			if (StringUtils.isNotEmpty(cardNo)) {
				condition.setCardNo(cardNo);
			}

			if (StringUtils.isNotEmpty(shopId)) {
				condition.setOperatorShopId(shopId);
			} else {
				condition.setOperatorShopIds(shopIdArry);
			}
			if (StringUtils.isNotBlank(payType))
				condition.setPayType(payType);

			if (StringUtils.isNotEmpty(actionTypeId)) {
				condition.setActionTypeId(actionTypeId);
			}

			JSONArray jsonArray = (JSONArray) userBillManager.searchToJsonBySql(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userBillManager.searchUserBilCount(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "用户信息查询成功", jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "用户信息查询失败" + e.getMessage());
		}

	}

	@RequestMapping(value = "collection" + "/exportExcel", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "导出流水明細信息", httpMethod = "GET", notes = "导出文件")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "exportExcel.html") })
	public @ResponseBody Object exportExcel(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute Query.UserBillServiceQueryReq req) {
		com.taole.toolkit.util.ReturnResult returnResult = new com.taole.toolkit.util.ReturnResult();
		try {
			JSONArray dataJa = new JSONArray();
			String fileName = "";
			// 获取请求参数
			Integer start = 0;
			Integer limit = 99999;
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");
			String createTimeStart = req.getTimeStart();
			String createTimeEnd = req.getTimeEnd();
			String userId = req.getUserId();
			String cardNo = req.getCardNo();
			String payType = req.getPayType();
			String shopId = req.getShopId();
			String actionTypeId = req.getActionTypeId();

			UserBillCondition condition = new UserBillCondition();
			JSONObject userDataObj = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
			String[] shopIdArry = memberCardManager.getShopIdByUser(userDataObj);
			if (StringUtils.isNotBlank(createTimeStart))
				condition.setStartTime(DateUtil.StringToDate(createTimeStart, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(createTimeEnd))
				condition.setEndTime(DateUtil.StringToDate(createTimeEnd, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(userId))
				condition.setUserId(userId);
			;

			if (StringUtils.isNotEmpty(cardNo)) {
				condition.setCardNo(cardNo);
			}

			if (StringUtils.isNotEmpty(shopId)) {
				condition.setOperatorShopId(shopId);
			} else {
				condition.setOperatorShopIds(shopIdArry);
			}
			if (StringUtils.isNotBlank(payType))
				condition.setPayType(payType);

			if (StringUtils.isNotEmpty(actionTypeId)) {
				condition.setActionTypeId(actionTypeId);
			}

			JSONArray jsonArray = (JSONArray) userBillManager.searchToJsonBySql(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userBillManager.searchUserBilCount(condition));
			dataJa = jsonObject.getJSONArray("items");
			fileName = "交易流水报表";

			XlsObject xlsObject = query4ExcelData(dataJa);
			request.getSession().setAttribute("xlsObject", xlsObject);
			returnResult.setCode(ReturnCode.SUCCESS);
			// 下载excel
			XlsJxlAction jxlAction = new XlsJxlAction();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			String titleStr = fileName + "-" + ConvertUtil.DateUtils
					.getFormattedString(new Date(System.currentTimeMillis()), DateStyle.YYYY_MM_DD.getValue());
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(titleStr.getBytes("GB2312"), "8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			WritableWorkbook book = jxlAction.getWritableWorkbook(os);
			jxlAction.generateXls(xlsObject, book, fileName);

		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "用户信息查询失败" + e.getMessage());
		}
		response.setHeader("code", returnResult.getCode());
		response.setHeader("desc", returnResult.getDesc());
		return null;

	}

	// 制作excel表格信息
	private XlsObject query4ExcelData(JSONArray resultAry) throws Exception {
		int columnNum = 10;
		List<String[]> rowList = new ArrayList<String[]>();
		for (int i = 0; i < resultAry.size(); i++) {
			JSONObject jsonObject = resultAry.getJSONObject(i);
			String[] rowData = new String[10];
			try {
				rowData[0] = jsonObject.getString("userBillNo");
			} catch (Exception e) {
				rowData[0] = "";
			}
			try {
				rowData[1] = jsonObject.getString("createTime");
			} catch (Exception e) {
				rowData[1] = "";
			}
			try {
				rowData[2] = (null != jsonObject.getString("shopName") ? jsonObject.getString("shopName") : "");
			} catch (Exception e) {
				rowData[2] = "";
			}
			try {
				rowData[3] = (null != jsonObject.getString("userName") ? jsonObject.getString("userName") : "");
			} catch (Exception e) {
				rowData[3] = "";
			}
			try {
				rowData[4] = (null != jsonObject.getString("cardNo") ? jsonObject.getString("cardNo") : "");
			} catch (Exception e) {
				rowData[4] = "";
			}
			try {
				rowData[5] = (null != jsonObject.getString("swipeAmount") ? jsonObject.getString("swipeAmount") : "");
			} catch (Exception e) {
				rowData[5] = "";
			}
			try {
				rowData[6] = (null != jsonObject.getString("consumeMoney") ? jsonObject.getString("consumeMoney") : "");
			} catch (Exception e) {
				rowData[6] = "";
			}
			try {
				rowData[7] = (null != jsonObject.getString("actionTypeName") ? jsonObject.getString("actionTypeName")
						: "");
			} catch (Exception e) {
				rowData[7] = "";
			}
			try {
				rowData[8] = (null != jsonObject.getString("payTypeName") ? jsonObject.getString("payTypeName") : "");
			} catch (Exception e) {
				rowData[8] = "";
			}
			try {
				rowData[9] = (null != jsonObject.getString("operator") ? jsonObject.getString("operator") : "");
			} catch (Exception e) {
				rowData[9] = "";
			}
			rowList.add(rowData);
		}
		// 制作title数据
		String[] titles = new String[columnNum];
		titles[0] = "交易单号";
		titles[1] = "交易时间";
		titles[2] = "店名";
		titles[3] = "会员姓名";
		titles[4] = "卡号";
		titles[5] = "划卡次数";
		titles[6] = "交易金额";
		titles[7] = "交易类型";
		titles[8] = "付款方式";
		titles[9] = "经办人";

		XlsObject xlsObject = new XlsObject();
		xlsObject.setXlsTitles(titles);
		xlsObject.setRowList(rowList);
		return xlsObject;
	}

	@RequestMapping(value = "/collection/queryGoodsInfo", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询流水明细消費商品列表", httpMethod = "GET", response = QueryGoodsInfo.UserBillServiceQueryGoodsInfoResp.class, notes = "查询出入库明细列表")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "queryGoodsInfo.html") })
	@ReturnCodeInfo(value = "queryGoodsInfo")
	public @ResponseBody Object queryGoodsInfo(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute QueryGoodsInfo.UserBillServiceQueryGoodsInfoReq queryCondition) {
		try {
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");

			if (queryCondition.getShopBillId() == null) {

			}

			GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
			condition.setShopBillId(queryCondition.getShopBillId());
			List<GoodsBillDetail> list = goodsBillDetailManager.list(condition);
			JSONArray resultAry = new JSONArray();
			for (GoodsBillDetail goodsBillDetail : list) {

				JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsBillDetail);
				obj.put("prive", goodsBillDetail.getPrice());
				obj.put("unit", goodsBillDetail.getUnit());
				obj.put("amount", goodsBillDetail.getAmount());
				obj.put("unitName", dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID,
						goodsBillDetail.getUnit()));
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo = goodsInfoManager.getGoodsInfo(goodsBillDetail.getGoodsId());
				obj.put("goodsCode", goodsInfo.getGoodsCode());
				obj.put("goodsName", goodsInfo.getName());
				resultAry.add(obj);
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", resultAry);
			jsonObject.put("total", goodsBillDetailManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "查询流水明细消費商品列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询流水明细消費商品列表失败: " + e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{id}/retrieve", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取userBill流水信息详情", httpMethod = "GET", response = Retrieve.UserBillServiceRetrieveResp.class, notes = "获取userBill流水信息详情详情")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "retrieve.html") })
	@ReturnCodeInfo(value = "Retrieve")
	public Object retrieve(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "id", value = "流水id") @PathVariable String id) {

		try {
			UserBill userBill = new UserBill();
			userBill = userBillManager.getUserBill(id);
			if (null == userBill) {
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "没有流水信息！");
			}
			JSONObject object = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(userBill);
			// fanyi
			object.put("unitName",
					dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID, userBill.getConsumeUnit()));
			object.put("actionName", dictionaryManager.getDictName(TransactionType.DICTIONARY_TRANSACTION_TYPE_CODE_ID,
					userBill.getActionTypeId()));
			object.put("inOutName",
					dictionaryManager.getDictName(InOutType.DICTIONARY_IN_OUT_TYPE_CODE_ID, userBill.getInOutType()));
			object.put("payTypeName",
					dictionaryManager.getDictName(PayType.DICTIONARY_PAY_TYPE_CODE_ID, userBill.getPayType()));

			if (StringUtils.isNotBlank(userBill.getShopBillId())) {
				GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
				condition.setShopBillId(userBill.getShopBillId());
				List<GoodsBillDetail> list = goodsBillDetailManager.list(condition);
				JSONArray resultAry = new JSONArray();
				for (GoodsBillDetail goodsBillDetail : list) {

					JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsBillDetail);
					obj.put("prive", goodsBillDetail.getPrice());
					obj.put("unit", goodsBillDetail.getUnit());
					obj.put("amount", goodsBillDetail.getAmount());
					obj.put("unitName", dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID,
							goodsBillDetail.getUnit()));
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo = goodsInfoManager.getGoodsInfo(goodsBillDetail.getGoodsId());
					obj.put("goodsCode", goodsInfo.getGoodsCode());
					obj.put("goodsName", goodsInfo.getName());
					resultAry.add(obj);
				}
				object.put("goodsList", resultAry);
				object.put("total", goodsBillDetailManager.count(condition));
			}
			if (StringUtils.isNotBlank(userBill.getUserId()) && !userBill.getUserId().equals(CustomerType.NOTMEMBER)) {
				User user = userManager.getUser(userBill.getUserId());
				MemberCard memberCard = memberCardManager.getMemberCard(userBill.getUserCardId());
				if (user == null) {
					object.put("username", "散客");
				}
				//object.put("username", user.getName());
				object.put("username", memberCard.getUserName());
				if (StringUtils.isBlank(user.getAddress())) {
					object.put("userAddress", "");
				}
				if (StringUtils.isBlank(user.getTelphone())) {
					object.put("userTel", "");
				}
				object.put("userAddress", user.getAddress());
				object.put("userTel", user.getTelphone());
			}
			if (StringUtils.isNotBlank(userBill.getUserCardId())) {
				MemberCard memberCard = memberCardManager.getMemberCard(userBill.getUserCardId());
				object.put("cardNo", memberCard.getCardNo());
				CardInfo cardInfo = cardInfoManager.getCardInfo(memberCard.getCardId());
				object.put("cardName", cardInfo.getCardName());
				object.put("cardType", cardInfo.getCardType());
				object.put("cardTypeName",
						dictionaryManager.getDictName(CardType.DICTIONARY_CARD_TYPE_CODE_ID, cardInfo.getCardType()));
			}
			if (StringUtils.isNotBlank(userBill.getOperatorShopId())) {
				ShopInfo shopInfo = shopInfoManager.getShopInfo(userBill.getOperatorShopId());
				object.put("shopName", shopInfo.getName());
				if (StringUtils.isBlank(shopInfo.getName())) {
					object.put("shopName", "");
				}
				object.put("shopAddress", shopInfo.getAddress());
				if (StringUtils.isBlank(shopInfo.getAddress())) {
					object.put("shopAddress", "");
				}
				object.put("shopTel", shopInfo.getContactTel());
				if (StringUtils.isBlank(shopInfo.getContactTel())) {
					object.put("shopTel", "");
				}
			}
			return new ServiceResult(ReturnResult.SUCCESS, "获取userBill流水信息详情成功", object);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "MemberCardService" + ".retrieve:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "获取userBill流水信息详情失败", e.getMessage());
		}
	}

	@RequestMapping(value = "collection" + "/exportGoodsExcel", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "导出userBill商品流水信息详情", httpMethod = "GET", response = Retrieve.UserBillServiceRetrieveResp.class, notes = "导出userBill流水信息详情详情")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "exportGoodsExcel.html") })
	@ReturnCodeInfo(value = "exportGoodsExcel")
	public @ResponseBody Object exportGoodsExcel(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute Query.UserBillServiceQueryReq req) {
		com.taole.toolkit.util.ReturnResult returnResult = new com.taole.toolkit.util.ReturnResult();
		try {
			JSONArray dataJa = new JSONArray();
			String fileName = "";
			// 获取请求参数
			Integer start = 0;
			Integer limit = 99999;
			Range range = new Range(start, limit);
			Sorter sorter = new Sorter().desc("createTime");
			String createTimeStart = req.getTimeStart();
			String createTimeEnd = req.getTimeEnd();
			String userId = req.getUserId();
			String cardNo = req.getCardNo();
			String payType = req.getPayType();
			String shopId = req.getShopId();
			String actionTypeId = req.getActionTypeId();

			UserBillCondition condition = new UserBillCondition();
			JSONObject userDataObj = UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, null);
			String[] shopIdArry = memberCardManager.getShopIdByUser(userDataObj);
			if (StringUtils.isNotBlank(createTimeStart))
				condition.setStartTime(DateUtil.StringToDate(createTimeStart, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(createTimeEnd))
				condition.setEndTime(DateUtil.StringToDate(createTimeEnd, DateStyle.YYYY_MM_DD));

			if (StringUtils.isNotBlank(userId))
				condition.setUserId(userId);
			;

			if (StringUtils.isNotEmpty(cardNo)) {
				condition.setCardNo(cardNo);
			}

			if (StringUtils.isNotEmpty(shopId)) {
				condition.setOperatorShopId(shopId);
			} else {
				condition.setOperatorShopIds(shopIdArry);
			}
			if (StringUtils.isNotBlank(payType))
				condition.setPayType(payType);

			if (StringUtils.isNotEmpty(actionTypeId)) {
				condition.setActionTypeId(actionTypeId);
			}

			JSONArray jsonArray = (JSONArray) userBillManager.searchToJsonBySql4Goods(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("totalCount", userBillManager.searchUserBilCount(condition));
			dataJa = jsonObject.getJSONArray("items");
			fileName = "交易流水商品信息报表";

			XlsObject xlsObject = query4ExcelGoodsData(dataJa);
			request.getSession().setAttribute("xlsObject", xlsObject);
			returnResult.setCode(ReturnCode.SUCCESS);
			// 下载excel
			XlsJxlAction jxlAction = new XlsJxlAction();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			String titleStr = fileName + "-" + ConvertUtil.DateUtils
					.getFormattedString(new Date(System.currentTimeMillis()), DateStyle.YYYY_MM_DD.getValue());
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(titleStr.getBytes("GB2312"), "8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			WritableWorkbook book = jxlAction.getWritableWorkbook(os);
			jxlAction.generateXls(xlsObject, book, fileName);

		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, "用户信息查询失败" + e.getMessage());
		}
		response.setHeader("code", returnResult.getCode());
		response.setHeader("desc", returnResult.getDesc());
		return null;
	}

	// 制作excel表格信息
	private XlsObject query4ExcelGoodsData(JSONArray resultAry) throws Exception {
		int columnNum = 11;
		List<String[]> rowList = new ArrayList<String[]>();
		for (int i = 0; i < resultAry.size(); i++) {
			JSONObject jsonObject = resultAry.getJSONObject(i);
			//JSONObject jsonObject = goodsAry.getJSONObject(i);
			String[] rowData = new String[11];
			try {
				rowData[0] = jsonObject.getString("userBillNo");
			} catch (Exception e) {
				rowData[0] = "";
			}
			try {
				rowData[1] = jsonObject.getString("createTime");
			} catch (Exception e) {
				rowData[1] = "";
			}
			try {
				rowData[2] = (null != jsonObject.getString("shopName") ? jsonObject.getString("shopName") : "");
			} catch (Exception e) {
				rowData[2] = "";
			}
			try {
				rowData[3] = (null != jsonObject.getString("userName") ? jsonObject.getString("userName") : "");
			} catch (Exception e) {
				rowData[3] = "";
			}
			try {
				rowData[4] = (null != jsonObject.getString("cardNo") ? jsonObject.getString("cardNo") : "");
			} catch (Exception e) {
				rowData[4] = "";
			}
			try {
				rowData[5] = (null != jsonObject.getString("goodsName") ? jsonObject.getString("goodsName") : "");
			} catch (Exception e) {
				rowData[5] = "";
			}
			try {
				rowData[6] = (null != jsonObject.getString("saleMoney") ? jsonObject.getString("saleMoney") : "");
			} catch (Exception e) {
				rowData[6] = "";
			}
			try {
				rowData[7] = (null != jsonObject.getString("amount") ? jsonObject.getString("amount") : "");
			} catch (Exception e) {
				rowData[7] = "";
			}
			try {
				rowData[8] = (null != jsonObject.getString("goodsMoneyTotal") ? jsonObject.getString("goodsMoneyTotal") : "");
			} catch (Exception e) {
				rowData[8] = "";
			}
			try {
				rowData[9] = (null != jsonObject.getString("payTypeName") ? jsonObject.getString("payTypeName") : "");
			} catch (Exception e) {
				rowData[9] = "";
			}
			try {
				rowData[10] = (null != jsonObject.getString("operator") ? jsonObject.getString("operator") : "");
			} catch (Exception e) {
				rowData[10] = "";
			}
			rowList.add(rowData);
		}
		// 制作title数据
		String[] titles = new String[columnNum];
		titles[0] = "交易单号";
		titles[1] = "交易时间";
		titles[2] = "店名";
		titles[3] = "会员姓名";
		titles[4] = "卡号";
		titles[5] = "商品名称";
		titles[6] = "商品单价";
		titles[7] = "商品数量";
		titles[8] = "消费小计";
		titles[9] = "付款方式";
		titles[10] = "经办人";

		XlsObject xlsObject = new XlsObject();
		xlsObject.setXlsTitles(titles);
		xlsObject.setRowList(rowList);
		return xlsObject;
	}
}
