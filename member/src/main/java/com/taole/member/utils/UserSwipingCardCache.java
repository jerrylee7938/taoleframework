package com.taole.member.utils;

import java.util.HashMap;
import java.util.Map;

public class UserSwipingCardCache {

	private static UserSwipingCardCache userSwipingCardCache = null;
	
	private Map<String, String> memberCardTokenMap = new HashMap<String, String>();
	
	private UserSwipingCardCache(){}
	
	public static UserSwipingCardCache getInstance(){
		if(userSwipingCardCache == null){
			synchronized (UserSwipingCardCache.class) {
				if(userSwipingCardCache == null){
					userSwipingCardCache = new UserSwipingCardCache();
				}
				return userSwipingCardCache;
			}
		}
		
		return userSwipingCardCache;
	}

	public Map<String, String> getMemberCardTokenMap() {
		return memberCardTokenMap;
	}

	public void setMemberCardTokenMap(Map<String, String> memberCardTokenMap) {
		this.memberCardTokenMap = memberCardTokenMap;
	}
}
