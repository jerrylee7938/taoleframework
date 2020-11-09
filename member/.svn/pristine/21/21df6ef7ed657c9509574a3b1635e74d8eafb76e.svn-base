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
import com.taole.member.Constants.CardExpire;
import com.taole.member.Constants.CardStatus;
import com.taole.member.Constants.CardType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.CardInfoCondition;
import com.taole.member.domain.CardInfo;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.ArrayListUtil;
import com.taole.toolkit.util.IdUtils;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = CardInfo.class)
@Transactional(readOnly = true)
public class CardInfoManager {
	
	@Resource(name = ProjectConfig.PREFIX + "cardInfoDao")
	DomainObjectDao<CardInfo> cardInfoDao;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createCardInfo(CardInfo cardInfo) {
		if(StringUtils.isBlank(cardInfo.getCardId()))
			cardInfo.setCardId(UUID.generateUUID());
		
		cardInfo.setCreateTime(new Date());
		return cardInfoDao.createObject(cardInfo);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateCardInfo(CardInfo cardInfo) {
		cardInfoDao.updateObject(cardInfo);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteCardInfo(CardInfo cardInfo) {
		cardInfoDao.deleteObject(cardInfo);
	}
	
	@DomainEngine.R
	public CardInfo getCardInfo(String id) {
		return cardInfoDao.loadObject(id);
	}
	
	public List<CardInfo> list(CardInfoCondition condition) {
		return cardInfoDao.listByCondition(condition);
	}
	public List<CardInfo> list(CardInfoCondition condition, Sorter sorter, int limit) {
		return cardInfoDao.listByCondition(condition, sorter, limit);
	}
	
	public PaginationSupport<CardInfo> search(CardInfoCondition condition, Range range, Sorter sroter) {
		return cardInfoDao.search(condition, range, sroter);
	}
	
	public int count(CardInfoCondition condition){
		return cardInfoDao.countByCondition(condition);
	}
	
	public CardInfo findByCondition(CardInfoCondition condition) {
		List<CardInfo> list = cardInfoDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public Object searchToJson(CardInfoCondition condition, Range range, Sorter sroter){
		PaginationSupport<CardInfo> ps = search(condition, range, sroter);
		JSONArray resultAry = new JSONArray();
		for(CardInfo cardInfo : ps.getItems()){
			JSONObject obj = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(cardInfo);
			obj.put("cardTypeName", dictionaryManager.getDictName(CardType.DICTIONARY_CARD_TYPE_CODE_ID, cardInfo.getCardType()));
			obj.put("cardStatusName", dictionaryManager.getDictName(CardStatus.DICTIONARY_CARD_STATUS_CODE_ID, cardInfo.getStatus()));
			obj.put("cardExpireName", dictionaryManager.getDictName(CardExpire.DICTIONARY_CARD_EXPIRE_CODE_ID, cardInfo.getChargeType()));
			resultAry.add(obj);
		}
		return resultAry;
	}
	
	/**
	 * 获取卡信息表最大code
	 * 
	 * @return
	 */
	/*public String getCardCode() {
		String code = "0";
		CardInfoCondition condition = new CardInfoCondition();
		List<CardInfo> list = this.list(condition, new Sorter().desc("code"), 1);
		if (!ArrayListUtil.isEmpty(list)) {
			CardInfo cardInfo = list.get(0);
			if (null != cardInfo && null != cardInfo.getCode()) {
				code = cardInfo.getCode();
			}
		}
		
		IdUtils.generateId();
		return code;
	}*/
}
