package com.taole.member.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.UUID;
import com.taole.member.Constants.OrderType;
import com.taole.member.Constants.SaleObjectType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopGoodsCondition;
import com.taole.member.condition.ShopStoreSetCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.member.config.MemberConfig;
import com.taole.member.domain.GoodsBillDetail;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.Order;
import com.taole.member.domain.OrderService;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.ShopStoreSet;
import com.taole.member.domain.User;
import com.taole.member.domain.param.ShopGoodsApi.BuyGoods;
import com.taole.member.domain.param.ShopGoodsApi.BuyGoods.OrderGoods;
import com.taole.member.domain.param.ShopGoodsApi.BuyTicket.OrderTicket;
import com.taole.member.domain.param.ShopGoodsApi.Count;
import com.taole.member.domain.param.UserBillService.Consume.GoodsValue;
import com.taole.member.domain.param.ShopGoodsApi.BuyTicket;
import com.taole.member.domain.param.ShopGoodsApi.GoogsRetrieve;
import com.taole.member.domain.param.ShopGoodsApi.Query;
import com.taole.member.domain.param.ShopGoodsApi.QueryAll;
import com.taole.member.domain.param.ShopGoodsApi.QueryTicket;
import com.taole.member.manager.GoodsBillDetailManager;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.OrderManager;
import com.taole.member.manager.OrderServiceManager;
import com.taole.member.manager.ShopGoodsManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.ShopStoreSetManager;
import com.taole.member.manager.UserAttrManager;
import com.taole.member.manager.UserManager;
import com.taole.member.utils.RestClientUtil;
import com.taole.member.utils.ReturnCodeDefine;
import com.taole.toolkit.util.MathUtil;

import freemarker.core._RegexBuiltins.replace_reBI;

@Controller
@Api(tags = { "客户端店铺商品接口管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "ShopGoodsApi")
public class ShopGoodsApi {
	
	private Logger logger = LoggerFactory.getLogger(ShopGoodsApi.class);
	
	@Autowired
	private ShopGoodsManager shopGoodsManager;
	
	@Autowired
	private ShopInfoManager shopInfoManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private UserAttrManager userAttrManager;
	
	@Autowired
	private OrderServiceManager orderServiceManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private MemberConfig memberConfig;
	
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	
	@Autowired
	private ShopStoreSetManager shopStoreSetManager;
	
	@Autowired
	private GoodsBillDetailManager goodsBillDetailManager;

	@RequestMapping(value = "/collection/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺商品信息列表", httpMethod = "GET", response = Query.ShopGoodsApiQueryRsp.class)
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.ShopGoodsApiQueryReq req){
		
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			ShopGoodsCondition condition = new ShopGoodsCondition();
			condition.setShopId(req.getShopId());
			condition.setCatalogId(req.getCatalogId());
			condition.setType(SaleObjectType.GOODS);
			
			JSONArray resultAry = shopGoodsManager.shopGoodsList(condition, start, limit);
			
			JSONObject resultObj = new JSONObject();
			resultObj.put("items", resultAry);
			resultObj.put("total", shopGoodsManager.shopGoodsCount(condition));
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺商品获取成功", resultObj);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺商品获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/goodsCount", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取商品购买次数", httpMethod = "GET", response = Query.ShopGoodsApiQueryRsp.class)
	public @ResponseBody Object goodsCount(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Count.ShopGoodsApiGoodsCountReq req){
		
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			UserBillCondition  condition = new UserBillCondition();
			condition .setOperatorShopId(req.getShopId());
			condition.setUserId(req.getUserId());
			condition.setGoodsId(req.getGoodsId());
			
			//JSONArray resultAry = shopGoodsManager.shopGoodsTimeCount(condition);
			JSONObject resultObj = new JSONObject();
			resultObj.put("total", shopGoodsManager.shopGoodsTimeCount(condition));
			
			return new ServiceResult(ReturnResult.SUCCESS, "成功", resultObj);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "获取失败: " + e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/collection/queryForHome", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺首页所有商品信息列表", httpMethod = "GET", response = QueryAll.ShopGoodsApiQueryAllRsp.class)
	public @ResponseBody Object queryForHome(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute QueryAll.ShopGoodsApiQueryAllReq req){
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			JSONArray resultAry = shopGoodsManager.shopGoodsForHome(req.getShopId());
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺商品获取成功", resultAry);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".queryAll:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺商品获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/queryAll", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺所有商品信息列表", httpMethod = "GET", response = QueryAll.ShopGoodsApiQueryAllRsp.class)
	public @ResponseBody Object queryAll(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute QueryAll.ShopGoodsApiQueryAllReq req){
		try {
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			JSONArray resultAry = shopGoodsManager.shopGoodsAll(req.getShopId());
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺商品获取成功", resultAry);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".queryAll:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺商品获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/googsInfo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取商品信息详情", httpMethod = "GET", response = GoogsRetrieve.ShopGoodsApiGoogsRetrieveRsp.class)
	public @ResponseBody Object googsRetrieve(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute GoogsRetrieve.ShopGoodsApiGoogsRetrieveReq req){
		
		try {
			GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(req.getGoodsId());
			if(goodsInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "商品信息不存在！");
			
			ShopInfo shopInfo = shopInfoManager.getShopInfo(req.getShopId());
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "店铺信息不存在！");
			
			ShopStoreSetCondition condition = new ShopStoreSetCondition();
			condition.setObjectId(req.getGoodsId());
			condition.setObjectType(SaleObjectType.GOODS);
			condition.setShopId(req.getShopId());
			ShopStoreSet shopStoreSet = shopStoreSetManager.findByCondition(condition);
			if(shopStoreSet == null)
				return new ServiceResult(ReturnResult.FAILURE, "对不起，"+goodsInfo.getName()+"暂未在"+shopInfo.getName()+"销售！");
			
			JSONObject resultObject = new JSONObject();
			resultObject.put("goodsId", goodsInfo.getGoodsId());
			resultObject.put("name", goodsInfo.getName());
			resultObject.put("money", goodsInfo.getSaleMoney());
			resultObject.put("description", goodsInfo.getDescription());
			resultObject.put("originalCost", goodsInfo.getOriginalCost());
			resultObject.put("maxnumber", goodsInfo.getMaxnumber());
			if(StringUtils.isBlank(goodsInfo.getImage()))
				resultObject.put("image", memberConfig.getDefaultImage());
			else 
				resultObject.put("image", goodsInfo.getImage());
			
			Double balance = goodsBillDetailManager.getGoodsBalanceNum(req.getShopId(), req.getGoodsId());
			resultObject.put("balance", balance);
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺商品获取成功", resultObject);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".googsInfo:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺商品获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/buyGoods", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "购买商品", httpMethod = "POST", response = BuyGoods.ShopGoodsApiBuyGoodsRsp.class)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object buyGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestBody BuyGoods.ShopGoodsApiBuyGoodsReq req) {
		
		try {
			
			if(StringUtils.isBlank(req.getShopId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			
			if(StringUtils.isBlank(req.getUserId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户ID不能为空");
			
			if(req.getGoods() == null || req.getGoods().size() == 0)
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品信息不能为空");
			
			ShopInfo shopInfo = shopInfoManager.getShopInfo(req.getShopId());
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "店铺信息不存在");
			List<OrderGoods> orderGoodses = req.getGoods();
			for (OrderGoods orderGoods : orderGoodses) {
				String goodsId = orderGoods.getGoodsId();
				Integer amount = orderGoods.getNum();
				double num = Double.valueOf(amount);
				Double balance = goodsBillDetailManager.getGoodsBalanceNum(req.getShopId(), goodsId);
				if(balance <=  0){
					return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
				}
				if(num > balance){
					return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
				}
			}
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
			for(OrderGoods og : req.getGoods()){
				OrderService orderService = new OrderService();
				orderService.setOrderId(orderId);
				orderService.setServiceName(og.getGoodsName());
				orderService.setServiceId(og.getGoodsId());
				orderService.setMoney(og.getMoney());
				orderService.setNum(og.getNum());
				orderServiceManager.createOrderService(orderService);
				
				Double goodsTotalMoney = MathUtil.mul(og.getNum(), og.getMoney());
				orderAmount = MathUtil.add(orderAmount, goodsTotalMoney);
				orderNum = orderNum + og.getNum();
			}
			
			//创建订单信息
			Order order = orderManager.create(orderId, orderNum, orderAmount, 
					req.getShopId(), req.getUserId(), OrderType.GOODS);
			
			//调用支付平台下单支付
			String extraInfo = memberConfig.getMemberHost() + "/service/rest/member.Order/collection/orderCallback?orderId=" + orderId;
			JSONObject createPayOrderResult = RestClientUtil.createPayOrder(openId, orderId, orderAmount, extraInfo);
			order.setPayData(createPayOrderResult.toString());
			orderManager.updateOrder(order);
			
			return new ServiceResult(ReturnResult.SUCCESS, "订单创建成功", createPayOrderResult);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".buyGoods:{}", e);
			throw new RuntimeException();
		}
	}
	
	@RequestMapping(value = "/collection/queryTicket", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺门票信息列表", httpMethod = "GET", response = QueryTicket.ShopGoodsApiQueryTicketRsp.class)
	public @ResponseBody Object queryTicket(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute QueryTicket.ShopGoodsApiQueryTicketReq req){
		
		try {
			
			logger.info("get shop ticket");
			
			if(StringUtils.isBlank(req.getShopId())){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			}
			
			ShopGoodsCondition condition = new ShopGoodsCondition();
			condition.setShopId(req.getShopId());
			condition.setType(SaleObjectType.GOODS);
			
			JSONArray resultAry = shopGoodsManager.shopTickeyList(condition, start, limit);
			
			JSONObject resultObj = new JSONObject();
			resultObj.put("items", resultAry);
			resultObj.put("total", shopGoodsManager.shopTicketCount(condition));
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺门票获取成功", resultObj);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".queryTicket:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺门票获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/buyTicket", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "购买门票", httpMethod = "POST", response = BuyTicket.ShopGoodsApiBuyTicketRsp.class)
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object buyTicket(HttpServletRequest request, HttpServletResponse response,
			@RequestBody BuyTicket.ShopGoodsApiBuyTicketReq req) {
		
		try {
			
			if(StringUtils.isBlank(req.getShopId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店铺ID不能为空");
			
			if(StringUtils.isBlank(req.getUserId()))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户ID不能为空");
			
			if(req.getTikets() == null || req.getTikets().size() == 0)
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "门票信息不能为空");
			
			ShopInfo shopInfo = shopInfoManager.getShopInfo(req.getShopId());
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "店铺信息不存在");
			
			List<OrderTicket> orderTickets = req.getTikets();
			for (OrderTicket orderTicket : orderTickets) {
				String goodsId = orderTicket.getGoodsId();
				Integer amount = orderTicket.getNum();
				double num = Double.valueOf(amount);
				Double balance = goodsBillDetailManager.getGoodsBalanceNum(req.getShopId(), goodsId);
				if(balance <=  0){
					return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
				}
				if(num > balance){
					return new ServiceResult(ReturnResult.FAILURE,"库存不足！");
				}
			}
			
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
			for(OrderTicket og : req.getTikets()){
				OrderService orderService = new OrderService();
				orderService.setOrderId(orderId);
				orderService.setServiceName(og.getGoodsName());
				orderService.setServiceId(og.getGoodsId());
				orderService.setMoney(og.getMoney());
				orderService.setNum(og.getNum());
				orderServiceManager.createOrderService(orderService);
				
				Double ticketTotalMoney = MathUtil.mul(og.getNum(), og.getMoney());
				orderAmount = MathUtil.add(orderAmount, ticketTotalMoney);
				orderNum = orderNum + og.getNum();
			}
			
			//创建订单信息
			Order order = orderManager.create(orderId, orderNum, orderAmount, 
					req.getShopId(), req.getUserId(), OrderType.TICKET);
			
			//调用支付平台下单支付
			String extraInfo = memberConfig.getMemberHost() + "/service/rest/member.Order/collection/orderCallback?orderId=" + orderId;
			JSONObject createPayOrderResult = RestClientUtil.createPayOrder(openId, orderId, orderAmount, extraInfo);
			order.setPayData(createPayOrderResult.toString());
			orderManager.updateOrder(order);
			
			return new ServiceResult(ReturnResult.SUCCESS, "订单创建成功", createPayOrderResult);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopGoodsApi" + ".buyTicket:{}", e);
			throw new RuntimeException();
		}
	}
}
