/**
 * Project:member
 * File:membershopManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.framework.util.UUID;
import com.taole.member.Constants;
import com.taole.member.Constants.ShopStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopInfoCondition;
import com.taole.member.domain.ShopInfo;
import com.taole.member.utils.MapUtil;
import com.taole.member.utils.RestClientUtil;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.ArrayListUtil;
import com.taole.toolkit.util.MathUtil;

/**
 * @author Generator
 * @version $Id$
 */
@DomainEngine(types = ShopInfo.class)
@Transactional(readOnly = true)
public class ShopInfoManager {

	@Resource(name = ProjectConfig.PREFIX + "shopInfoDao")
	DomainObjectDao<ShopInfo> shopInfoDao;

	@Autowired
	private DictionaryManager dictionaryManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createShopInfo(ShopInfo shopInfo) {
		if (StringUtils.isBlank(shopInfo.getShopId()))
			shopInfo.setShopId(UUID.generateUUID());

		shopInfo.setCreateTime(new Date());
		return shopInfoDao.createObject(shopInfo);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateShopInfo(ShopInfo shopInfo) {
		shopInfo.setUpdateTime(new Date());
		shopInfoDao.updateObject(shopInfo);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteShopInfo(ShopInfo shopInfo) {
		shopInfoDao.deleteObject(shopInfo);
	}

	@DomainEngine.R
	public ShopInfo getShopInfo(String id) {
		return shopInfoDao.loadObject(id);
	}

	public List<ShopInfo> list(ShopInfoCondition condition) {
		return shopInfoDao.listByCondition(condition);
	}
	
	public List<ShopInfo> list(ShopInfoCondition condition, Sorter sorter, int limit) {
		return shopInfoDao.listByCondition(condition, sorter, limit);
	}

	public PaginationSupport<ShopInfo> search(ShopInfoCondition condition, Range range, Sorter sroter) {
		return shopInfoDao.search(condition, range, sroter);
	}

	public int count(ShopInfoCondition condition) {
		return shopInfoDao.countByCondition(condition);
	}
	
	public Object searchToJsonForMember(JSONObject userDataObj) {
		ShopInfoCondition condition = new ShopInfoCondition();
		
		JSONArray dataPriv = new JSONArray();
		if(userDataObj.containsKey("result_data") && userDataObj.getJSONObject("result_data").containsKey("dataPriv")){
			dataPriv = userDataObj.getJSONObject("result_data").getJSONArray("dataPriv");
		}
		
		if(dataPriv.size() > 0){
			JSONArray priv = dataPriv.getJSONObject(0).getJSONArray("priv");
			String[] shopIds = new String[priv.size()];
			for(int i=0; i<priv.size(); i++){
				JSONObject dataPrivObj = priv.getJSONObject(i);
				shopIds[i] = dataPrivObj.getString("id");
			}
			
			condition.setIds(shopIds);
		}
		
		List<ShopInfo> shopInfoList = list(condition);
		JSONArray resultAry = (JSONArray) SerializableJSONTransformer.transformPojo2Jso(shopInfoList);
		return resultAry;
	}
	
	public Object searchToJson(ShopInfoCondition condition, Range range, Sorter sroter) {
		PaginationSupport<ShopInfo> ps = search(condition, range, sroter);
		JSONArray resultAry = new JSONArray();
		for (ShopInfo shopInfo : ps.getItems()) {
			JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopInfo);
			obj.put("shopStatusName",dictionaryManager.getDictName(ShopStatus.DICTIONARY_SHOP_STATUS_CODE_ID, shopInfo.getStatus()));
			resultAry.add(addProperForJson(obj));
		}
		return resultAry;
	}

	public JSONObject addProperForJson(JSONObject object) throws JSONException {

		if (StringUtils.isNotBlank(object.getString("province"))) {
			JSONObject provinceObj = RestClientUtil.getPortalDictRetrieve(object.getString("province"));
			object.put("provinceTitle", provinceObj == null ? "" : provinceObj.getString("name"));
		}

		if (StringUtils.isNotBlank(object.getString("city"))) {
			JSONObject cityObj = RestClientUtil.getPortalDictRetrieve(object.getString("city"));
			object.put("cityTitle", cityObj == null ? "" : cityObj.getString("name"));
		}

		return object;
	}

	/**
	 * 获取商品表最大code
	 * 
	 * @return
	 */
	public int getShopCode() {
		int code = 0;
		ShopInfoCondition condition = new ShopInfoCondition();
		List<ShopInfo> list = this.list(condition, new Sorter().desc("code"), 1);
		if (!ArrayListUtil.isEmpty(list)) {
			ShopInfo shopInfo = list.get(0);
			if (null != shopInfo && null != shopInfo.getCode()) {
				code = shopInfo.getCode();
			}
		}
		return code;
	}
	
	/**
	 * 根据定位经纬度获取距离最近的开业店铺
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public ShopInfo nearestShop(Double lng, Double lat){
		
		ShopInfoCondition condition = new ShopInfoCondition();
		condition.setStatus(ShopStatus.OPEN);
		condition.setNeId(Constants.MP_WX_HOME_NOT_SHOW_SHOP_ID);
		List<ShopInfo> shopInfos = list(condition);
		
		shopInfos = sortShopInfo(shopInfos, lng, lat);
		
		if(shopInfos != null && shopInfos.size() > 0)
			return shopInfos.get(0);
		
		return null;
	}
	
	/**
	 * 根据定位信息排序店铺信息(不支持分页)
	 * @param shopInfos 店铺数据
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	private List<ShopInfo> sortShopInfo(List<ShopInfo> shopInfos, Double lng, Double lat){
		
		if(shopInfos.size() == 0 || lng == null || lat == null || lng == 0 || lat == 0)
			return null;
		
		//存放所有店铺到定位信息的距离(单位:米)
		List<Integer> distanceList = new ArrayList<Integer>();
		//距离信息的店铺map
		Map<Integer, ShopInfo> shopInfoMap = new HashMap<Integer, ShopInfo>();
		
		//没有位置信息的店铺集合
		List<ShopInfo> noLocationShopInfos = new ArrayList<ShopInfo>();
		
		for(ShopInfo shopInfo : shopInfos){
			if(shopInfo.getLat() != null && shopInfo.getLng() != null && 
					shopInfo.getLat() != 0 && shopInfo.getLng() != 0){//有位置信息的店铺
				Point2D userPoint = new Point2D.Double(lng,lat);//定位位置
		        Point2D shopPoint = new Point2D.Double(shopInfo.getLng(),shopInfo.getLat());//店铺位置
		        Double m = MapUtil.getDistance(userPoint, shopPoint);//两个位置的距离，单位:米
		        
		        distanceList.add(MathUtil.convertsToInt(m));
		        
		        shopInfo.setDistance(m);
		        shopInfoMap.put(MathUtil.convertsToInt(m), shopInfo);
		        
			}else{
				noLocationShopInfos.add(shopInfo);
			}
		}
		
		//存放所有店铺到定位信息的距离(单位:米),供排序使用
		int[] distances = new int[distanceList.size()];
		for(int d=0; d<distanceList.size(); d++){
			distances[d] = distanceList.get(d);
		}
		//重新排序，距离越近越靠前
		MapUtil.bubbleSort(distances);
		
		//将排序后的店铺信息放到总的list里面
		List<ShopInfo> sortShopInfos = new ArrayList<ShopInfo>();
		for(int m : distances){
			sortShopInfos.add(shopInfoMap.get(m));
		}
		
		//将没有位置信息的店铺放到总的list里面
		for(ShopInfo shopInfo : noLocationShopInfos){
			sortShopInfos.add(shopInfo);
		}
		
		return sortShopInfos;
	}
}

