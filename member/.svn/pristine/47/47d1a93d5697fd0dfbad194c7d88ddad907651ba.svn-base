package com.taole.member.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.Constants.OrderType;
import com.taole.member.condition.ShopCardCondition;
import com.taole.member.config.MemberConfig;
import com.taole.member.domain.Order;
import com.taole.member.domain.OrderService;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.User;
import com.taole.member.domain.param.ShopCardApi.BuyCard;
import com.taole.member.domain.param.ShopCardApi.BuyCard.Card;
import com.taole.member.domain.param.ShopCardApi.Query;
import com.taole.member.domain.param.ShopCardApi.QueryForRecharge;
import com.taole.member.manager.OrderManager;
import com.taole.member.manager.OrderServiceManager;
import com.taole.member.manager.ShopCardManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.UserAttrManager;
import com.taole.member.manager.UserManager;
import com.taole.member.utils.RestClientUtil;
import com.taole.member.utils.ReturnCodeDefine;
import com.taole.toolkit.util.MathUtil;

@Controller
@Api(tags = { "客户端店铺会员卡接口管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "ShopCardApi")
public class ShopCardApi {
	
	private Logger logger = LoggerFactory.getLogger(ShopCardApi.class);
	
	@Autowired
	private ShopCardManager shopCardManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private OrderServiceManager orderServiceManager;
	
	@Autowired
	private ShopInfoManager shopInfoManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private MemberConfig memberConfig;
	
	@Autowired
	private UserAttrManager userAttrManager;
	
	@RequestMapping(value = "/collection/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺会员卡信息列表", httpMethod = "GET", response = Query.ShopCardApiQueryRsp.class)
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Query.ShopCardApiQueryReq req){
		
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			JSONArray resultAry = shopCardManager.allShopCard(req.getShopId());
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺会员卡获取成功", resultAry);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺会员卡获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/queryForRecharge", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺可充值会员卡信息列表", httpMethod = "GET", response = QueryForRecharge.ShopCardApiQueryForRechargeRsp.class)
	public @ResponseBody Object queryForRecharge(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute QueryForRecharge.ShopCardApiQueryForRechargeReq req){
		
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			ShopCardCondition condition = new ShopCardCondition();
			condition.setCardType(req.getType());
			condition.setShopId(req.getShopId());
			
			JSONArray resultAry = shopCardManager.shopCardListForRecharge(condition);
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺会员卡获取成功", resultAry);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺会员卡获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/buyCard", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "用户购卡接口", httpMethod = "POST", response = BuyCard.ShopCardApiBuyCardRsp.class)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object buyCard(HttpServletRequest request, HttpServletResponse response,
			@RequestBody BuyCard.ShopCardApiBuyCardReq req){
		
		try {
			
			if(StringUtils.isBlank(req.getShopId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			
			if(StringUtils.isBlank(req.getUserId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户ID不能为空");
			
			if(req.getCards() == null || req.getCards().size() == 0)
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "会员卡信息不能为空");
			
			ShopInfo shopInfo = shopInfoManager.getShopInfo(req.getShopId());
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "店铺信息不存在");
			
			User user = userManager.getUser(req.getUserId());
			if(user == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "用户信息不存在");
			
			String openId = userAttrManager.getUserOpenId(user.getId());
			if(StringUtils.isBlank(openId))
				return new ServiceResult(Integer.valueOf(ReturnCodeDefine.NO_OPENID[0]), "用户没有绑定openId");
			
			String orderId = UUID.generateUUID();
			
			//创建订单商品信息
			Double orderAmount = 0D;
			Integer orderNum = 0;
			for(Card card : req.getCards()){
				for(int i=0; i<card.getNum(); i++){
					OrderService orderService = new OrderService();
					orderService.setOrderId(orderId);
					orderService.setServiceName(card.getCardName());
					orderService.setServiceId(card.getCardId());
					orderService.setMoney(card.getMoney());
					orderService.setNum(1);
					orderServiceManager.createOrderService(orderService);
				}
				
				Double cardTotalMoney = MathUtil.mul(card.getNum(), card.getMoney());
				orderAmount = MathUtil.add(orderAmount, cardTotalMoney);
				orderNum = orderNum + card.getNum();
			}
			
			//创建订单信息
			Order order = orderManager.create(orderId, orderNum, orderAmount, 
					req.getShopId(), req.getUserId(), OrderType.CARD);
			
			//调用支付平台下单支付
			String extraInfo = memberConfig.getMemberHost() + "/service/rest/member.Order/collection/orderCallback?orderId=" + orderId;
			JSONObject createPayOrderResult = RestClientUtil.createPayOrder(openId, orderId, orderAmount, extraInfo);
			order.setPayData(createPayOrderResult.toString());
			orderManager.updateOrder(order);
			
			return new ServiceResult(ReturnResult.SUCCESS, "订单创建成功", createPayOrderResult);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopCardApi" + ".query:{}", e);
			throw new RuntimeException();
		}
	}
}
