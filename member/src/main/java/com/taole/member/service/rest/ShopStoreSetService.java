package com.taole.member.service.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Evaluator.IsEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.member.Constants;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopStoreSetCondition;
import com.taole.member.domain.CardInfo;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.ShopStoreSet;
import com.taole.member.domain.param.ShopStoreSet.Create;
import com.taole.member.domain.param.ShopStoreSet.QueryForSale;
import com.taole.member.manager.CardInfoManager;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.ShopStoreSetManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "店面设置后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "ShopStoreSet")
@Controller
@RestService(name = ProjectConfig.PREFIX + "ShopStoreSet")
public class ShopStoreSetService {
	private Logger logger = LoggerFactory.getLogger(ShopInfoService.class);

	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "ShopStoreSet_";

	@Autowired
	private ShopInfoManager shopInfoManager;

	@Autowired
	private ShopStoreSetManager shopStoreSetManager;

	@Autowired
	private GoodsInfoManager goodsInfoManager;

	@Autowired
	private CardInfoManager cardInfoManager;

	private List<ShopStoreSet> list;

	/**
	 * 为多个店面指定一种可售物资
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/create", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "设置店面信息", httpMethod = "POST", response = Create.ShopStoreSetServiceCreateResp.class, notes = "设置店面信息")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "create.html") })
	@ReturnCodeInfo(value = "create")
	public @ResponseBody Object create(HttpServletRequest request,
			@RequestBody Create.ShopStoreSetServiceCreateReq createReq) {
		System.out.println("==========" + createReq.toString());
		try {
			String shopIds = createReq.getShopIds();// 店面ID
			String objectType = createReq.getObjectType();// 可售商品类型
			String objectId = createReq.getObjectId();// 可售商品Id

			/*if (StringUtils.isBlank(shopIds)) {
				return new ServiceResult(ReturnResult.FAILURE, "必须指定可售店面 ");
			}*/
			if (StringUtils.isBlank(createReq.getObjectId())) {
				return new ServiceResult(ReturnResult.FAILURE, "必须指定可售商品ID");
			}

			if (objectType.equals(Constants.SaleObjectType.GOODS)) {
				GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(objectId);
				if (goodsInfo == null) {
					return new ServiceResult(ReturnResult.FAILURE, "指定的可售商品不存在");
				}
			} else if (objectType.equals(Constants.SaleObjectType.CARD)) {
				CardInfo cardInfo = cardInfoManager.getCardInfo(objectId);
				if (cardInfo == null) {
					return new ServiceResult(ReturnResult.FAILURE, "指定的可售卡不存在");
				}
			}

			//将设置过该种商品的所有店面配置全部删除
			ShopStoreSetCondition sssCondition = new ShopStoreSetCondition();
			sssCondition.setObjectId(objectId);
			shopStoreSetManager.deleteShopStoreSetByCondition(sssCondition);
			if(StringUtils.isBlank(shopIds)){
				return new ServiceResult(ReturnResult.SUCCESS, "设置店面信息成功");
			}
			//为每一个店面重新设置该种物资
			String[] shopIdArr = shopIds.split(",");
			for (String shopId : shopIdArr) {
				ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
				if (shopInfo == null) {
					return new ServiceResult(ReturnResult.FAILURE, "无法找到ID为" + shopId + "的店面信息");
				}

				ShopStoreSet shopStoreSet = new ShopStoreSet();
				shopStoreSet.setShopId(shopId);
				shopStoreSet.setObjectId(objectId);
				shopStoreSet.setObjectType(objectType);
				shopStoreSetManager.createshopStoreSet(shopStoreSet);
			}

			return new ServiceResult(ReturnResult.SUCCESS, "设置店面信息成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopStoreSetService" + ".create:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店面可售物资设置失败: " + e.getMessage());
		}
	}

	/**
	 * 查询商品信息列表
	 * 
	 * @param request
	 * @param response
	 * @param start
	 * @param limit
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping(value = "/collection/queryForSale", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询商店可售商品信息列表", httpMethod = "GET", response = QueryForSale.ShopStoreSetServiceQueryForSaleResp.class, notes = "查询商店可售商品信息列表")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "queryForSale.html") })
	@ReturnCodeInfo(value = "queryForSale")
	public @ResponseBody Object queryForSale(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute QueryForSale.ShopStoreSetServiceQueryForSaleReq queryCondition) {
		try {
			Range range = new Range(start, limit);
			if (StringUtils.isEmpty(queryCondition.getShopId())) {
				return new ServiceResult(ReturnResult.FAILURE, "商店id不能为空！");
			}
			ShopStoreSetCondition condition = new ShopStoreSetCondition();
			condition.setShopId(queryCondition.getShopId());
			if (null != queryCondition.getGoodsTypeId()) {
				condition.setObjectType(queryCondition.getGoodsTypeId());
			}
			System.out.println(condition.getShopId());
			JSONArray jsonArray = (JSONArray) shopStoreSetManager.searchToJson(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("total", shopStoreSetManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "查询商店可售商品信息列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询商店可售商品信息列表失败: " + e.getMessage());
		}
	}

}
