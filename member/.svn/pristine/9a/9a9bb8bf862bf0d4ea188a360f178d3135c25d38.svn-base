package com.taole.member.utils;

import java.util.Map;

public class UserSwipingCardUtil {
	
	public static void put(String memberCardId, String token){
		Map<String, String> map = UserSwipingCardCache.getInstance().getMemberCardTokenMap();
		map.put(memberCardId, token);
	}
	
	public static void delete(String memberCardId){
		Map<String, String> map = UserSwipingCardCache.getInstance().getMemberCardTokenMap();
		map.remove(memberCardId);
	}
	
	public static String get(String memberCardId){
		Map<String, String> map = UserSwipingCardCache.getInstance().getMemberCardTokenMap();
		return map.get(memberCardId);
	}
}
