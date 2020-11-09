package com.taole.member.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.util.MD5Util;
import com.taole.member.config.MemberConfig;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

@Component
public class SmsUtil {

	@Autowired
	private MemberConfig memberConfig;
	
	//获取短信token接口地址
	private static String getSmsTokenUrl;
	//发送短信账户
	private static String smsAccount;
	//发送短信APP KEY
	private static String smsAppKey;
	//发送短信密码
	private static String smsPwd;
	
	@PostConstruct
	public void init() {
		getSmsTokenUrl = memberConfig.getGetSmsTokenUrl();
		smsAccount = memberConfig.getSmsAccount();
		smsAppKey = memberConfig.getSmsAppKey();
		smsPwd = memberConfig.getSmsPwd();
	}
	
	public String getTaoleSmsToken() throws org.json.JSONException{
		//获取token池
		Map<String, String> tokenPool = SmsTokenPool.getInstance().getTokenPool();
		
		if(tokenPool.size() == 0){//token池中没有token
			System.out.println("sms token pool is null");
			tokenPool = getNewToken();
			SmsTokenPool.getInstance().setTokenPool(tokenPool);
			return this.getTaoleSmsToken();
		}else{//token池中有token
			String token = null;   //token
			String tokenExpiresTime = null; //过期时间
			
			for(String key : tokenPool.keySet()) {
				token = key;
				tokenExpiresTime = tokenPool.get(key);
			}
			
			System.out.println("sms token : ==========" + token);
			System.out.println("sms token expires time: ==========" + tokenExpiresTime);
			
			if(DateUtil.StringToDate(tokenExpiresTime).before(new Date())){//token已过期
				System.out.println("sms token expires");
				tokenPool.remove(token);
				SmsTokenPool.getInstance().setTokenPool(getNewToken());
				
				return this.getTaoleSmsToken();
			}else{//token正常
				return token;
			}
		}
	}
	
	/**
	 * 获取token
	 * @param account
	 * @param appKey
	 * @param pwd
	 * @param tokenUrl
	 * @return
	 * @throws JSONException
	 * @throws org.json.JSONException 
	 */
	private Map<String, String> getNewToken() throws org.json.JSONException{
		
		org.json.JSONObject paramJson = new org.json.JSONObject();
		paramJson.put("account", smsAccount);
		paramJson.put("appKey", smsAppKey);
		paramJson.put("pwd", MD5Util.encryptPassword(smsPwd));
		
		Map<String, String> tokenPool = new HashMap<String, String>();
		JSONObject tokenObj = RestClientUtil.getTaoleSmsToken(getSmsTokenUrl, paramJson);
		int expireHours = tokenObj.getIntValue("expire");
		String expireDate = DateUtil.DateToString(DateUtils.addMinutes(new Date(), expireHours), DateStyle.YYYY_MM_DD_HH_MM_SS);
		tokenPool.put(tokenObj.getString("token"), expireDate);
		
		return tokenPool;
	}
}
