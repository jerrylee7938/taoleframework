/**
 * Project:member
 * File:membergoodsManager.java 
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
import com.taole.member.Constants.InOutType;
import com.taole.member.Constants.ShopBillType;
import com.taole.member.Constants.UnitType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.member.domain.GoodsBill;
import com.taole.member.domain.GoodsBillDetail;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.UserBill;
import com.taole.member.sql.CommonSql;
import com.taole.member.sql.GoodsBillDetailSql;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.ArrayListUtil;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = GoodsBillDetail.class)
@Transactional(readOnly = true)
public class GoodsBillDetailManager {
	
	@Autowired
	private ShopInfoManager shopInfoManager;
	@Autowired
	private GoodsBillManager goodsBillManager;
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	
	@Autowired
	private DictionaryManager dictionaryManager;

	@Resource(name = ProjectConfig.PREFIX + "goodsBillDetailDao")
	DomainObjectDao<GoodsBillDetail> goodsBillDetailDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createGoodsBillDetail(GoodsBillDetail goodsBillDetail) {
		if(StringUtils.isBlank(goodsBillDetail.getGbDetailId()))
			goodsBillDetail.setGbDetailId(UUID.generateUUID());
		
		goodsBillDetail.setCreateTime(new Date());
		return goodsBillDetailDao.createObject(goodsBillDetail);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateGoodsBillDetail(GoodsBillDetail goodsBillDetail) {
		goodsBillDetailDao.updateObject(goodsBillDetail);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteGoodsBillDetail(GoodsBillDetail goodsBillDetail) {
		goodsBillDetailDao.deleteObject(goodsBillDetail);
	}
	
	@DomainEngine.R
	public GoodsBillDetail getGoodsBillDetail(String id) {
		return goodsBillDetailDao.loadObject(id);
	}
	
	public List<GoodsBillDetail> list(GoodsBillDetailCondition condition) {
		return goodsBillDetailDao.listByCondition(condition);
	}
	public List<GoodsBillDetail> list(GoodsBillDetailCondition condition, Sorter sorter, int limit) {
		return goodsBillDetailDao.listByCondition(condition, sorter, limit);
	}
	public List<GoodsBillDetail> list(GoodsBillDetailCondition condition, Sorter sorter) {
		return goodsBillDetailDao.listByCondition(condition, sorter);
	}
	
	public PaginationSupport<GoodsBillDetail> search(GoodsBillDetailCondition condition, Range range, Sorter sroter) {
		return goodsBillDetailDao.search(condition, range, sroter);
	}
	
	public int count(GoodsBillDetailCondition condition){
		return goodsBillDetailDao.countByCondition(condition);
	}
	public GoodsBillDetail findByCondition(GoodsBillDetailCondition condition) {
		List<GoodsBillDetail> list = goodsBillDetailDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/*public Object searchToJson(GoodsBillDetailCondition condition, Range range, Sorter sroter){
		PaginationSupport<GoodsBillDetail> ps = search(condition, range, sroter);
		JSONArray resultAry = new JSONArray();
		for(GoodsBillDetail goodsBillDetail : ps.getItems()){
			ShopInfo shopInfo = shopInfoManager.getShopInfo(goodsBillDetail.getShopId());
			
			
			JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsBillDetail);
			obj.put("shopName", shopInfo.getName());
			resultAry.add(obj);
		}
		return resultAry;
	}*/
	public JSONArray searchToJson(final GoodsBillDetailCondition condition, final int start, final int limit) {
		List<?> goodsList = ((HibernateDaoSupport) goodsBillDetailDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = GoodsBillDetailSql.getGoodsBillDetail(condition);
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
			
			String shopBillId = (String) objs[0];
			String goodsId = (String) objs[1];
			Double amount = (Double) objs[3];
			String unit = (String) objs[4];
			Double balance = (Double) objs[5];
			String shopId = (String) objs[2];
			Date createTime = (Date) objs[6];
			String shopBillType = (String) objs[8];
			
			GoodsBill goodsBill = goodsBillManager.getGoodsBill(shopBillId);
			GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(goodsId);
			ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsBill);
			jsonObject.put("shopBillNo", goodsBill.getShopBillNo());
			jsonObject.put("shopName", shopInfo.getName());
			jsonObject.put("shopBillType", shopBillType);
			jsonObject.put("shopBillTypeName", dictionaryManager.getDictName(ShopBillType.DICTIONARY_SHOP_BILL_TYPE_CODE_ID,shopBillType));
			jsonObject.put("inOutType", goodsBill.getInOutType());
			jsonObject.put("inOutTypeName", dictionaryManager.getDictName(InOutType.DICTIONARY_IN_OUT_TYPE_CODE_ID,goodsBill.getInOutType()));
			jsonObject.put("operator", goodsBill.getOperator());
			jsonObject.put("catalogId", goodsInfo.getCatalogId());
			jsonObject.put("catalogName", dictionaryManager.getDictName(GoodsType.DICTIONARY_GOODS_TYPE_CODE_ID,goodsInfo.getCatalogId()));
			jsonObject.put("name", goodsInfo.getName());
			jsonObject.put("goodsCode", goodsInfo.getGoodsCode());
			jsonObject.put("amount", amount);
			if(balance == null){
				jsonObject.put("balance", 0);
			}
			jsonObject.put("balance", balance);
			jsonObject.put("unit", unit);
			jsonObject.put("unitName", dictionaryManager.getDictName(UnitType.DICTIONARY_UNIT_TYPE_CODE_ID,goodsInfo.getUnit()));

			jsonObject.put("createTime", createTime);
			
			resultAry.add(jsonObject);
		}
		return resultAry;
	}
	public Integer searchGoodsDetailCount(final GoodsBillDetailCondition condition) throws Exception {
		List<?> list = ((HibernateDaoSupport) goodsBillDetailDao).getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback<List<?>>() {
					@Override
					public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
						String sql = GoodsBillDetailSql.getGoodsBillDetail(condition);
						sql = CommonSql.count(sql);
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						List<?> list = sqlQuery.list();
						return list;
					}
				});
		return Integer.valueOf(list.get(0).toString());
	}

	
	/*public JSONObject addProperForJson(JSONObject object) throws JSONException {
		
		if(StringUtils.isNotBlank(object.getString("avatar")))
			object.put("path", commonConfig.getBizFileReadUrl() + object.getString("avatar"));
		
		if(StringUtils.isNotBlank(object.getString("province"))){
			JSONObject provinceObj = RestClientUtil.getPortalDictRetrieve(object.getString("province"));
			object.put("provinceTitle", provinceObj == null ? "" : provinceObj.getString("name"));
		}
		
		if(StringUtils.isNotBlank(object.getString("city"))){
			JSONObject cityObj = RestClientUtil.getPortalDictRetrieve(object.getString("city"));
			object.put("cityTitle", cityObj == null ? "" : cityObj.getString("name"));
		}
		
		return object;
	}*/
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
	
	
	/**
	 * 获取库存剩余量
	 * 
	 * @return
	 */
	public Double getGoodsBalanceNum(String shopId,String goodsId) {
		
		
		GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
		condition.setGoodsId(goodsId);
		//condition.setShopId(shopId);
		List<GoodsBillDetail> list = this.list(condition, new Sorter().desc("createTime"));
		Double balanceNum = 0.00;
		if (!ArrayListUtil.isEmpty(list)) {
			//GoodsBillDetail userBill = list.get(0);
			GoodsBillDetail goodsBillDetail = list.get(0);
			if (null != goodsBillDetail && null != goodsBillDetail.getBalance()) {
				balanceNum = goodsBillDetail.getBalance();
			}
		}
		return balanceNum;
	}
}
