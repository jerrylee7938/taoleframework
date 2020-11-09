package com.taole.member.manager;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.events.EventMethod;
import com.taole.member.ProjectConfig;
import com.taole.member.domain.CardNo;

@DomainEngine(types = CardNo.class)
@Transactional(readOnly = true)
public class CardNoManager {
	
	private final static String CARD_NO_START = "777";
	
	@Resource(name = ProjectConfig.PREFIX + "cardNoDao")
	DomainObjectDao<CardNo> cardNoDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createCardInfo(CardNo cardNo) {
		return cardNoDao.createObject(cardNo);
	}
	
	@Transactional
	public String getCardNo(){
		CardNo cardNo = new CardNo();
		String cardNoId = createCardInfo(cardNo);
		if(cardNoId.length() < 6){
			String cardNoBase = "000000";
			
			cardNoBase = cardNoBase.substring(0, (cardNoBase.length() - cardNoId.length()));
			return CARD_NO_START + cardNoBase + cardNoId;
		}else {
			return CARD_NO_START + cardNoId;
		}
	}
}
