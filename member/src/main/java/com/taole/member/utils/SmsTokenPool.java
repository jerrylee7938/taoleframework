package com.taole.member.utils;

import java.util.HashMap;
import java.util.Map;

public class SmsTokenPool {
	
	private static SmsTokenPool smsTokenPool = null;

	public Map<String, String> tokenPool = new HashMap<String, String>();

	public static SmsTokenPool getInstance() {
		if (smsTokenPool == null) {
			smsTokenPool = new SmsTokenPool();
		}
		return smsTokenPool;
	}
	
	public Map<String, String> getTokenPool() {
		return tokenPool;
	}

	public void setTokenPool(Map<String, String> tokenPool) {
		this.tokenPool = tokenPool;
	}
}
