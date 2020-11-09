/**
 * Project:member
 * File:memberCardManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import com.taole.member.Constants.PayType;
import com.taole.member.Constants.TransactionType;
import com.taole.member.Constants.UserBillApplyStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.domain.UserBillApply;
import com.taole.member.sql.CommonSql;
import com.taole.member.sql.UserBillApplySql;
import com.taole.toolkit.dict.manager.DictionaryManager;

/**
 * @author Generator
 * @version $Id$
 */
@DomainEngine(types = UserBillApply.class)
@Transactional(readOnly = true)
public class UserBillApplyManager {

	@Resource(name = ProjectConfig.PREFIX + "userBillApplyDao")
	DomainObjectDao<UserBillApply> userBillApplyDao;

	@Autowired
	private DictionaryManager dictionaryManager;

	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createUserBillApply(UserBillApply userBillApply) {
		if (StringUtils.isBlank(userBillApply.getApplyId()))
			userBillApply.setApplyId(UUID.generateUUID());

		userBillApply.setCreateTime(new Date());
		return userBillApplyDao.createObject(userBillApply);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateUserBillApply(UserBillApply userBillApply) {
		userBillApplyDao.updateObject(userBillApply);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteUserBillApply(UserBillApply userBillApply) {
		userBillApplyDao.deleteObject(userBillApply);
	}

	@DomainEngine.R
	public UserBillApply getUserBillApply(String id) {
		return userBillApplyDao.loadObject(id);
	}

	public List<UserBillApply> list(UserBillApplyCondition condition) {
		return userBillApplyDao.listByCondition(condition);
	}

	public List<UserBillApply> list(UserBillApplyCondition condition, Sorter sorter) {
		return userBillApplyDao.listByCondition(condition, sorter);
	}

	public List<UserBillApply> list(UserBillApplyCondition condition, Sorter sorter, int limit) {
		return userBillApplyDao.listByCondition(condition, sorter, limit);
	}

	public PaginationSupport<UserBillApply> search(UserBillApplyCondition condition, Range range, Sorter sroter) {
		return userBillApplyDao.search(condition, range, sroter);
	}

	public int count(UserBillApplyCondition condition) {
		return userBillApplyDao.countByCondition(condition);
	}

	public UserBillApply findByCondition(UserBillApplyCondition condition) {
		List<UserBillApply> list = userBillApplyDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	
	public JSONArray searchToJsonBySql(final UserBillApplyCondition condition, final int start, final int limit) {
		List<?> userBilApplyList = ((HibernateDaoSupport) userBillApplyDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = UserBillApplySql.getUserBillApply(condition);
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setFirstResult(start);
				sqlQuery.setMaxResults(limit);
				List<?> list = sqlQuery.list();
				return list;
			}
		});

		JSONArray resultAry = new JSONArray();
		for(int i=0; i<userBilApplyList.size(); i++){
			Object[] objs = (Object[]) userBilApplyList.get(i);
			
			String transType = (String) objs[0];
			Double money = (Double) objs[1];
			String payType = (String) objs[2];
			Date createTime = (Date) objs[3];
			String status = (String) objs[4];
			String cardNo = (String) objs[5];
			String cardName = (String) objs[6];
			String userName = (String) objs[7];
			String shopName = (String) objs[8];
			String userCardId = (String) objs[9];
			Integer chargeNo = (Integer) objs[10];
			String applyId = (String) objs[11];
			Integer amount = (Integer) objs[12];
			String cardType = (String) objs[13];
			String description = (String) objs[14];
			JSONObject jsonObject = new JSONObject();
		
			
			jsonObject.put("transType", transType);
			jsonObject.put("transTypeName", dictionaryManager.getDictName(TransactionType.DICTIONARY_TRANSACTION_TYPE_CODE_ID, transType));
			jsonObject.put("money", money);
			jsonObject.put("payType", payType);
			jsonObject.put("payTypeName",
					dictionaryManager.getDictName(PayType.DICTIONARY_PAY_TYPE_CODE_ID, payType));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTime1 =  df.format(createTime);
			jsonObject.put("createTime", createTime1);
			jsonObject.put("status", status);
			jsonObject.put("statusName",
					dictionaryManager.getDictName(UserBillApplyStatus.DICTIONARY_USER_BILL_APPLY_CODE_ID, status));
			jsonObject.put("cardNo", cardNo);
			jsonObject.put("cardName", cardName);
			jsonObject.put("shopName", shopName);
			jsonObject.put("userName", userName);
			jsonObject.put("userCardId", userCardId);
			if(chargeNo == null){
				jsonObject.put("chargeNo", "");
			}else{
				jsonObject.put("chargeNo", chargeNo);
			}		
			jsonObject.put("applyId", applyId);
			if(amount == null){
				jsonObject.put("amount", "");
			}else{
				jsonObject.put("amount", amount);
			}	
			jsonObject.put("cardType", cardType);
			jsonObject.put("description", description);
			resultAry.add(jsonObject);
		}
		return resultAry;
	}
	public Integer searchUserBilApplyCount(final UserBillApplyCondition condition) throws Exception {
		List<?> list = ((HibernateDaoSupport) userBillApplyDao).getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback<List<?>>() {
					@Override
					public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
						String sql = UserBillApplySql.getUserBillApply(condition);
						sql = CommonSql.count(sql);
						SQLQuery sqlQuery = session.createSQLQuery(sql);
						List<?> list = sqlQuery.list();
						return list;
					}
				});
		return Integer.valueOf(list.get(0).toString());
	}

}