/**
 * Project:member
 * File:memberCardManager.java 
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
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.UserGoodsOrderCondition;
import com.taole.member.domain.UserGoodsOrder;
import com.taole.member.sql.UserGoodsOrderSql;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = UserGoodsOrder.class)
@Transactional(readOnly = true)
public class UserGoodsOrderManager {
	
	@Resource(name = ProjectConfig.PREFIX + "userGoodsOrderDao")
	DomainObjectDao<UserGoodsOrder> userGoodsOrderDao;
	
	@Autowired
	private ShopInfoManager shopInfoManager;
	
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createUserGoodsOrder(UserGoodsOrder userGoodsOrder) {
		if(StringUtils.isBlank(userGoodsOrder.getId()))
			userGoodsOrder.setId(UUID.generateUUID());
		
		userGoodsOrder.setCreateTime(new Date());
		return userGoodsOrderDao.createObject(userGoodsOrder);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateUserGoodsOrder(UserGoodsOrder userGoodsOrder) {
		userGoodsOrderDao.updateObject(userGoodsOrder);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteUserGoodsOrder(UserGoodsOrder userGoodsOrder) {
		userGoodsOrderDao.deleteObject(userGoodsOrder);
	}
	
	@DomainEngine.R
	public UserGoodsOrder getUserGoodsOrder(String id) {
		return userGoodsOrderDao.loadObject(id);
	}
	
	public List<UserGoodsOrder> list(UserGoodsOrderCondition condition) {
		return userGoodsOrderDao.listByCondition(condition);
	}
	public List<UserGoodsOrder> list(UserGoodsOrderCondition condition, Sorter sorter, int limit) {
		return userGoodsOrderDao.listByCondition(condition, sorter, limit);
	}
	
	public PaginationSupport<UserGoodsOrder> search(UserGoodsOrderCondition condition, Range range, Sorter sroter) {
		return userGoodsOrderDao.search(condition, range, sroter);
	}
	
	public int count(UserGoodsOrderCondition condition){
		return userGoodsOrderDao.countByCondition(condition);
	}
	
	public UserGoodsOrder findByCondition(UserGoodsOrderCondition condition) {
		List<UserGoodsOrder> list = userGoodsOrderDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public String getBuyGoodsName(final String shopBillId){
		List<?> list = ((HibernateDaoSupport) userGoodsOrderDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = UserGoodsOrderSql.allGoodsNameSql();
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setParameter("shopBillId", shopBillId);
				List<?> list = sqlQuery.list();
				return list;
			}
		});
		
		return (String) list.get(0);
	}
	
	public JSONArray searchToJsonForGoods(UserGoodsOrderCondition condition, Range range, Sorter sroter){
		PaginationSupport<UserGoodsOrder> ps = search(condition, range, sroter);
		
		JSONArray resultaArray = new JSONArray();
		for(UserGoodsOrder userGoodsOrder : ps.getItems()){
			JSONObject obj = new JSONObject();
			obj.put("userGoodsOrderId", userGoodsOrder.getId());
			obj.put("shopBillId", userGoodsOrder.getShopBillId());
			obj.put("shopName", shopInfoManager.getShopInfo(userGoodsOrder.getShopId()).getName());
			obj.put("goodsName", getBuyGoodsName(userGoodsOrder.getShopBillId()));
			obj.put("orderTime", DateUtil.DateToString(userGoodsOrder.getOrderTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			
			resultaArray.add(obj);
		}
		return resultaArray;
	}
	
	public JSONArray searchToJsonForTicket(UserGoodsOrderCondition condition, Range range, Sorter sroter){
		PaginationSupport<UserGoodsOrder> ps = search(condition, range, sroter);
		
		JSONArray resultaArray = new JSONArray();
		for(UserGoodsOrder userGoodsOrder : ps.getItems()){
			JSONObject obj = new JSONObject();
			obj.put("userGoodsOrderId", userGoodsOrder.getId());
			obj.put("shopBillId", userGoodsOrder.getShopBillId());
			obj.put("shopName", shopInfoManager.getShopInfo(userGoodsOrder.getShopId()).getName());
			obj.put("goodsName", goodsInfoManager.getGoodsInfo(userGoodsOrder.getGoodsIds()).getName());
			obj.put("orderTime", DateUtil.DateToString(userGoodsOrder.getOrderTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			
			resultaArray.add(obj);
		}
		return resultaArray;
	}
}
