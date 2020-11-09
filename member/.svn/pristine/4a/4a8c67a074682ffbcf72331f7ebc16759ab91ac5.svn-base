package com.taole.member.service.api;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.Constants;
import com.taole.member.Constants.ShopStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopInfoCondition;
import com.taole.member.config.MemberConfig;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.param.ShopInfoApi.NearestShop;
import com.taole.member.manager.ShopInfoManager;
import com.taole.toolkit.dict.domain.Dictionary;
import com.taole.toolkit.dict.manager.DictionaryManager;

@Controller
@Api(tags = { "客户端店铺接口管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "ShopInfoApi")
public class ShopInfoApi {
	
	private Logger logger = LoggerFactory.getLogger(ShopInfoApi.class);
	
	@Autowired
	private ShopInfoManager shopInfoManager;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@Autowired
	private MemberConfig memberConfig;

	@RequestMapping(value = "/collection/nearestShop", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "定位获取距离最近的店铺信息", httpMethod = "GET", response = NearestShop.ShopInfoApiNearestShopRsp.class)
	public @ResponseBody Object nearestShop(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "定位经度,定位失败传:0", name = "lng", required = true) @RequestParam Double lng,
			@ApiParam(value = "定位纬度,定位失败传:0", name = "lat", required = true) @RequestParam Double lat){
		
		try {
			
			JSONObject resultObject = new JSONObject();
			
			ShopInfo shopInfo = null;
			
			if(lng == 0 || lat == 0){//如果定位失败则获取默认店铺信息
				Dictionary dictionary = dictionaryManager.getDictionary(Constants.LOCATION_ERROR_DEFAULT_SHOP_CODE);
				if(dictionary != null){
					shopInfo = shopInfoManager.getShopInfo(dictionary.getValue());
				}
			}else{//获取距离定位信息最近的店铺信息
				shopInfo = shopInfoManager.nearestShop(lng, lat);
			}
			
			if(shopInfo != null){
				resultObject.put("shopId", shopInfo.getShopId());
				resultObject.put("name", shopInfo.getName());
			}
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺信息获取成功", resultObject);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopInfoApi" + ".nearestShop:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺信息获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/queryAll", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取所有店铺信息，供下拉框使用", httpMethod = "GET", response = NearestShop.ShopInfoApiNearestShopRsp.class)
	public @ResponseBody Object queryAll(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			ShopInfoCondition condition = new ShopInfoCondition();
			condition.setStatus(ShopStatus.OPEN);
			condition.setNeId(Constants.MP_WX_HOME_NOT_SHOW_SHOP_ID);
			
			List<ShopInfo> shopInfos = shopInfoManager.list(condition);
			JSONArray resultAry = new JSONArray();
			for(ShopInfo shopInfo : shopInfos){
				JSONObject shopinJsonObject = new JSONObject();
				shopinJsonObject.put("shopId", shopInfo.getShopId());
				shopinJsonObject.put("name", shopInfo.getName());
				resultAry.add(shopinJsonObject);
			}
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺信息获取成功", resultAry);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopInfoApi" + ".queryAll:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺信息获取失败: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/collection/shopInfo", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "获取店铺详细信息", httpMethod = "GET", response = com.taole.member.domain.param.ShopInfoApi.ShopInfo.ShopInfoApiShopInfoRsp.class)
	public @ResponseBody Object shopInfo(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "店铺ID", name = "shopId", required = true) @RequestParam String shopId){
		
		try {
			
			ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "无法根据店铺ID："+shopId+"获取店铺信息");
			
			JSONObject shopinJsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopInfo);
			shopinJsonObject = shopInfoManager.addProperForJson(shopinJsonObject);
			
			JSONObject resultObj = new JSONObject();
			resultObj.put("id", shopInfo.getShopId());
			resultObj.put("name", shopInfo.getName());
			resultObj.put("image", StringUtils.isNotBlank(shopInfo.getShopDescImage()) ? shopInfo.getShopDescImage() : memberConfig.getDefaultImage());
			resultObj.put("tel", StringUtils.isNotBlank(shopInfo.getContactTel()) ? shopInfo.getContactTel() : "");
			resultObj.put("address", shopinJsonObject.get("provinceTitle") + shopinJsonObject.getString("cityTitle") + shopInfo.getAddress());
			resultObj.put("openTime", shopInfo.getOpenTime());
			resultObj.put("closeTime", shopInfo.getCloseTime());
			resultObj.put("lat", shopInfo.getLat());
			resultObj.put("lng", shopInfo.getLng());
			
			return new ServiceResult(ReturnResult.SUCCESS, "店铺信息获取成功", resultObj);
		} catch (Exception e) {
			
			logger.error(ProjectConfig.PREFIX + "ShopInfoApi" + ".shopInfo:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "店铺信息获取失败: " + e.getMessage());
		}
	}
}
