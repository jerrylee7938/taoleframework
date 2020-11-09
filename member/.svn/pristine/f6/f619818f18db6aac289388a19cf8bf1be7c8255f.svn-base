/**
 * Project:member
 * File:membershopManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
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
import com.taole.member.Constants.GoodsType;
import com.taole.member.Constants.UnitType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopStoreSetCondition;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopStoreSet;
import com.taole.member.sql.ShopStoreSetSql;
import com.taole.toolkit.dict.manager.DictionaryManager;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = ShopStoreSet.class)
@Transactional(readOnly = true)
public class ShopStoreSetManager {
	
	@Resource(name = ProjectConfig.PREFIX + "shopStoreSetDao")
	DomainObjectDao<ShopStoreSet> shopStoreSetDao;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	
	@Autowired
	private GoodsBillDetailManager goodsBillDetailManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createshopStoreSet(ShopStoreSet shopStoreSet) {
		if(StringUtils.isBlank(shopStoreSet.getGoodsSetId()))
			shopStoreSet.setGoodsSetId(UUID.generateUUID());
		
		shopStoreSet.setCreateTime(new Date());
		return shopStoreSetDao.createObject(shopStoreSet);
	}

	/*@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateShopStoreSet(ShopStoreSet shopStoreSet) {
		shopStoreSet.setUpdateTime(new Date());
		shopStoreSetDao.updateObject(shopStoreSet);
	}*/
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteShopStoreSet(List<ShopStoreSet> shopList) {
		shopStoreSetDao.deleteObjects(shopList);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteShopStoreSet(ShopStoreSet shopStoreSet) {
		shopStoreSetDao.deleteObject(shopStoreSet);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteShopStoreSetByCondition(ShopStoreSetCondition condition) {
		shopStoreSetDao.deleteByCondition(condition);
	}
	
	@DomainEngine.R
	public ShopStoreSet getshopStoreSet(String id) {
		return shopStoreSetDao.loadObject(id);
	}
	
	public List<ShopStoreSet> list(ShopStoreSetCondition condition) {
		return shopStoreSetDao.listByCondition(condition);
	}
	
	public PaginationSupport<ShopStoreSet> search(ShopStoreSetCondition condition, Range range, Sorter sroter) {
		return shopStoreSetDao.search(condition, range, sroter);
	}
	
	public int count(ShopStoreSetCondition condition){
		return shopStoreSetDao.countByCondition(condition);
	}
	
	public ShopStoreSet findByCondition(ShopStoreSetCondition condition) {
		List<ShopStoreSet> list = shopStoreSetDao.listByCondition(condition,1);
		if (list.size()==0) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/*public Object searchToJson(ShopStoreSetCondition condition, Range range, Sorter sroter){
		PaginationSupport<ShopStoreSet> ps = search(condition, range, sroter);
		JSONArray resultAry = new JSONArray();
		for(ShopStoreSet shopStoreSet : ps.getItems()){
			
			GoodsInfo goodsInfo =new GoodsInfo();
			goodsInfo = goodsInfoManager.getGoodsInfo(shopStoreSet.getObjectId());
			
			GoodsBillDetailCondition detailCondition = new GoodsBillDetailCondition();
			detailCondition.setGoodsId(goodsInfo.getGoodsId());
			detailCondition.setShopId(shopStoreSet.getShopId());
			GoodsBillDetail goodsBillDetail = goodsBillDetailManager.findByCondition(detailCondition);
			
			
			JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopStoreSet);
		//	obj.put("shopStatusName", dictionaryManager.getDictName(ShopStatus.DICTIONARY_SHOP_STATUS_CODE_ID, shopStoreSet.getStatus()));
			obj.put("goodsCode", goodsInfo.getGoodsCode());
			obj.put("goodsType", dictionaryManager.getDictName(GoodsType.DICTIONARY_GOODS_TYPE_CODE_ID, goodsInfo.getCatalogId()));
			obj.put("unitName", dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID, goodsInfo.getUnit()));
			obj.put("goodsName", goodsInfo.getName());
			obj.put("desc", goodsInfo.getDescription());
			obj.put("balance", goodsBillDetail.getBalance());
			resultAry.add(obj);
		}
		return resultAry;
	}*/
	public JSONArray searchToJson(final ShopStoreSetCondition condition, final int start, final int limit) {
		List<?> goodsList = ((HibernateDaoSupport) shopStoreSetDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = ShopStoreSetSql.getGoodsDetail(condition);
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setFirstResult(start);
				sqlQuery.setMaxResults(limit);
				List<?> list = sqlQuery.list();
				return list;
			}
		});

		JSONArray resultAry = new JSONArray();
		for(int i=0; i<goodsList.size(); i++){
			Object[] objs = (Object[]) goodsList.get(i);
			
			String objectId = (String) objs[0];
			String objectType = (String) objs[1];
			String shopId = (String) objs[3];
			String goodsId = (String) objs[4];
			String goodsCode = (String) objs[5];
			String goodsname = (String) objs[6];
			String desc = (String) objs[7];
			String unit = (String) objs[8];
			Double balance = (Double) objs[9];
			
			GoodsInfo goodsInfo =new GoodsInfo();
			goodsInfo = goodsInfoManager.getGoodsInfo(goodsId);
					
			//GoodsBill goodsBill = goodsBillManager.getGoodsBill(shopBillId);
			//GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(goodsId);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsInfo);
			jsonObject.put("goodsCode", goodsCode);
			jsonObject.put("goodsType", dictionaryManager.getDictName(GoodsType.DICTIONARY_GOODS_TYPE_CODE_ID, goodsInfo.getCatalogId()));
			jsonObject.put("catalogId", goodsInfo.getCatalogId());
			jsonObject.put("name", goodsname);
			jsonObject.put("desc", desc);
			if(balance == null){
				balance=0.00;
			}
			jsonObject.put("balance", balance);
			jsonObject.put("unit", unit);
			jsonObject.put("unitName", dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID, goodsInfo.getUnit()));

			
			resultAry.add(jsonObject);
		}
		return resultAry;
	}
}
