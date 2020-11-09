/**
 * Project:doctorplus1-account
 * File:FooManager.java 
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
import com.taole.framework.util.DateUtils;
import com.taole.framework.util.UUID;
import com.taole.member.Constants;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.SmsVerifyCondition;
import com.taole.member.domain.SmsVerify;
import com.taole.toolkit.dict.domain.Dictionary;
import com.taole.toolkit.dict.manager.DictionaryManager;

/**
 * @author Generator
 * @version $Id$
 */
@DomainEngine(types = SmsVerify.class)
@Transactional(readOnly = true)
public class SmsVerifyManager {
	
	@Resource(name = ProjectConfig.PREFIX + "smsVerifyDao")
	DomainObjectDao<SmsVerify> smsVerifyDao;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createSmsVerify(SmsVerify object) {
		
		if(StringUtils.isBlank(object.getId()))
			object.setId(UUID.generateUUID());
		object.setCreatedTime(new Date());
		
		return smsVerifyDao.createObject(object);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateSmsVerify(SmsVerify object) {
		smsVerifyDao.updateObject(object);
	}

	@EventMethod
	@DomainEngine.R
	@Transactional
	public SmsVerify getSmsVerify(String id) {
		return smsVerifyDao.loadObject(id);
	}

	public SmsVerify getSmsVerify(SmsVerifyCondition condition) {
		List<SmsVerify> SmsVerifys = smsVerifyDao.listByCondition(condition, 1);
		if (SmsVerifys != null && SmsVerifys.size() > 0)
			return SmsVerifys.get(0);
		else
			return null;
	}
	
	@Transactional
	public String validateVerifyCode(String verifyCode, String mobileNum){
		
		Dictionary dictionary = dictionaryManager.getDictionary(Constants.SUPER_VERIFY_CODE);
		if(dictionary != null && verifyCode.equals(dictionary.getValue())){
			return "100";
		}
		
		SmsVerifyCondition condition = new SmsVerifyCondition();
		condition.setCountry(86);
		condition.setTelephone(mobileNum);
		condition.setVerify(verifyCode);
		condition.setVerifyType(1);
		SmsVerify smsVerify = getSmsVerify(condition);
		
		if(smsVerify == null)
			return "输入的验证码有误，请检查！";
		
		if (smsVerify.getExpiredTime() != null && DateUtils.beforeNow(smsVerify.getExpiredTime()))
			return "输入的验证码已过期，请重新获取";
		
		if (smsVerify.getIsUsed() != null && smsVerify.getIsUsed() == 1)
			return "输入的验证码已被使用，请重新获取";
		
		smsVerify.setIsUsed(1);
		updateSmsVerify(smsVerify);
		
		return "100";
	}
}
