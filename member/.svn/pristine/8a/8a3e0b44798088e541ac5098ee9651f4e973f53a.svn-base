/**
 * Project:member
 * File:memberCardManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsRefTypeCondition;
import com.taole.member.domain.GoodsRefType;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = GoodsRefType.class)
@Transactional(readOnly = true)
public class GoodsRefTypeManager {
	
	@Resource(name = ProjectConfig.PREFIX + "goodsRefTypeDao")
	DomainObjectDao<GoodsRefType> goodsRefTypeDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String createGoodsRefType(GoodsRefType goodsRefType) {
		if(StringUtils.isBlank(goodsRefType.getId()))
			goodsRefType.setId(UUID.generateUUID());
		
		goodsRefType.setCreateTime(new Date());
		goodsRefType.setUpdateTime(new Date());
		return goodsRefTypeDao.createObject(goodsRefType);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteGoodsRefType(String goodsId) {
		GoodsRefTypeCondition condition = new GoodsRefTypeCondition();
		condition.setGoodsId(goodsId);
		goodsRefTypeDao.deleteByCondition(condition);
	}
	
	public GoodsRefType getGoodsRefType(String goodsId, String typeId){
		GoodsRefTypeCondition condition = new GoodsRefTypeCondition();
		condition.setGoodsId(goodsId);
		condition.setTypeId(typeId);
		List<GoodsRefType> goodsRefTypes = goodsRefTypeDao.listByCondition(condition, 1);
		if(goodsRefTypes.size() > 0)
			return goodsRefTypes.get(0);
		
		return null;
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void createGoodsRefTypes(List<String> types, String goodsId){
		for(String typeId : types){
			GoodsRefType goodsRefType = getGoodsRefType(goodsId, typeId);
			if(goodsRefType == null){
				goodsRefType = new GoodsRefType();
				goodsRefType.setGoodsId(goodsId);
				goodsRefType.setTypeId(typeId);
				createGoodsRefType(goodsRefType);
			}
		}
	}
	
	/**
	 * 根据商品ID获取商品首页类型
	 * @param goodsId
	 * @return
	 */
	public JSONArray queryForGoods(String goodsId){
		GoodsRefTypeCondition goodsRefTypeCondition = new GoodsRefTypeCondition();
		goodsRefTypeCondition.setGoodsId(goodsId);
		List<GoodsRefType> goodsRefTypes = goodsRefTypeDao.listByCondition(goodsRefTypeCondition);
		JSONArray resultAry = new JSONArray();
		for(GoodsRefType grt : goodsRefTypes){
			resultAry.add(grt.getTypeId());
		}
		return resultAry;
	}
}
