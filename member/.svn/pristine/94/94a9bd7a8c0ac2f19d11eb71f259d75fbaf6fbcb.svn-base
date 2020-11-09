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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.TradeLockCondition;
import com.taole.member.domain.TradeLock;
import com.taole.toolkit.dict.manager.DictionaryManager;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = TradeLock.class)
@Transactional(readOnly = true)
public class TradeLockManager {
	
	@Resource(name = ProjectConfig.PREFIX + "tradeLockDao")
	DomainObjectDao<TradeLock> tradeLockDao;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createTradeLock(TradeLock tradeLock) {
		if(StringUtils.isBlank(tradeLock.getCardNo()))
			tradeLock.setCardNo(UUID.generateUUID());
		
		tradeLock.setCreateTime(new Date());
		return tradeLockDao.createObject(tradeLock);
	}

	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteTradeLock(TradeLock tradeLock) {
		tradeLockDao.deleteObject(tradeLock);
	}
	
	@DomainEngine.R
	public TradeLock getTradeLock(String cardNo) {
		return tradeLockDao.loadObject(cardNo);
	}
	
	public TradeLock findByCondition(TradeLockCondition condition) {
		List<TradeLock> list = tradeLockDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	
}
