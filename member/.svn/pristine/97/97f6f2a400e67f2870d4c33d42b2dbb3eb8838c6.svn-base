/**
 * Project:member
 * File:membergoodsManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsBillCondition;
import com.taole.member.condition.GoodsInfoCondition;
import com.taole.member.condition.ShopInfoCondition;
import com.taole.member.domain.GoodsBill;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopInfo;
import com.taole.toolkit.util.ArrayListUtil;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = GoodsBill.class)
@Transactional(readOnly = true)
public class GoodsBillManager {
	
	@Resource(name = ProjectConfig.PREFIX + "goodsBillDao")
	DomainObjectDao<GoodsBill> goodsBillDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createGoodsBill(GoodsBill goodsBill) {
		if(StringUtils.isBlank(goodsBill.getShopBillId()))
			goodsBill.setShopBillId(UUID.generateUUID());
		
		goodsBill.setCreateTime(new Date());
		return goodsBillDao.createObject(goodsBill);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateGoodsBill(GoodsBill goodsBill) {
		goodsBill.setUpdateTime(new Date());
		goodsBillDao.updateObject(goodsBill);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteGoodsBill(GoodsBill goodsBill) {
		goodsBillDao.deleteObject(goodsBill);
	}
	
	@DomainEngine.R
	public GoodsBill getGoodsBill(String id) {
		return goodsBillDao.loadObject(id);
	}
	
	public List<GoodsBill> list(GoodsBillCondition condition) {
		return goodsBillDao.listByCondition(condition);
	}
	public List<GoodsBill> list(GoodsBillCondition condition, Sorter sorter, int limit) {
		return goodsBillDao.listByCondition(condition, sorter, limit);
	}
	
	public PaginationSupport<GoodsBill> search(GoodsBillCondition condition, Range range, Sorter sroter) {
		return goodsBillDao.search(condition, range, sroter);
	}
	
	public int count(GoodsBillCondition condition){
		return goodsBillDao.countByCondition(condition);
	}
	
	public Object searchToJson(GoodsBillCondition condition, Range range, Sorter sroter){
		PaginationSupport<GoodsBill> ps = search(condition, range, sroter);
		JSONArray resultAry = new JSONArray();
		for(GoodsBill goodsBill : ps.getItems()){
			JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsBill);
			resultAry.add(obj);
		}
		return resultAry;
	}
	
	//生成单据id
	public String getShopBillId(){
		String shopBillId = UUID.generateUUID();
		return shopBillId;
	}
	/**
	 * 获取商品表最大code
	 * 
	 * @return
	 *//*
	public int getGoodsCode() {
		int code = 0;
		GoodsInfoCondition condition = new GoodsInfoCondition();
		List<GoodsInfo> list = this.list(condition, new Sorter().desc("goodsCode"), 1);
		if (!ArrayListUtil.isEmpty(list)) {
			GoodsInfo goodsInfo = list.get(0);
			if (null != goodsInfo  && null != goodsInfo.getGoodsCode()) {
				code = goodsInfo.getGoodsCode();
			}
		}
		return code;
	}*/
	
}
