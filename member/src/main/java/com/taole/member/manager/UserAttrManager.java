/**
 * Project:member
 * File:memberCardManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.UserAttrCondition;
import com.taole.member.domain.UserAttr;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = UserAttr.class)
@Transactional(readOnly = true)
public class UserAttrManager {
	
	@Resource(name = ProjectConfig.PREFIX + "userAttrDao")
	DomainObjectDao<UserAttr> userAttrDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createUserAttr(UserAttr userAttr) {
		if(StringUtils.isBlank(userAttr.getId()))
			userAttr.setId(UUID.generateUUID());
		
		return userAttrDao.createObject(userAttr);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateUserAttr(UserAttr userAttr) {
		userAttrDao.updateObject(userAttr);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteUserAttr(UserAttr userAttr) {
		userAttrDao.deleteObject(userAttr);
	}
	
	@DomainEngine.R
	public UserAttr getUserAttr(String id) {
		return userAttrDao.loadObject(id);
	}
	
	public List<UserAttr> list(UserAttrCondition condition) {
		return userAttrDao.listByCondition(condition);
	}
	public List<UserAttr> list(UserAttrCondition condition, Sorter sorter, int limit) {
		return userAttrDao.listByCondition(condition, sorter, limit);
	}
	
	public PaginationSupport<UserAttr> search(UserAttrCondition condition, Range range, Sorter sroter) {
		return userAttrDao.search(condition, range, sroter);
	}
	
	public int count(UserAttrCondition condition){
		return userAttrDao.countByCondition(condition);
	}
	
	public UserAttr findByCondition(UserAttrCondition condition) {
		List<UserAttr> list = userAttrDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public String getUserOpenId(String userId){
		UserAttrCondition condition = new UserAttrCondition();
		condition.setUserId(userId);
		condition.setAttrName("open_id");
		List<UserAttr> list = userAttrDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			UserAttr userAttr = list.get(0);
			return userAttr.getAttrValue();
		}
	}
	
	@Transactional
	public void saveUserOpenId(String userId, String openId){
		UserAttrCondition condition = new UserAttrCondition();
		condition.setUserId(userId);
		condition.setAttrName("open_id");
		
		UserAttr userAttr = findByCondition(condition);
		if(userAttr == null){
			userAttr = new UserAttr();
			userAttr.setAttrName("open_id");
			userAttr.setAttrValue(openId);
			userAttr.setUserId(userId);
			this.createUserAttr(userAttr);
		}else{
			userAttr.setAttrValue(openId);
			this.updateUserAttr(userAttr);
		}
		
	}
}
