/**
 * Project:doctorplus1-account
 * File:TokenUtil.java
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Co., Ltd. All rights reserved.
 */

package com.taole.member.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.taole.framework.util.StringUtils;

/**
 * @author zhangyq-pc
 * @date 2016年7月12日
 */

@Component
public class TokenUtil {

	private static String secret = "member_card_token";
	
	/**
	 * 生成JWT
	 * @param iss The issuer of the token
	 * @param aud The audience that the JWT is intended for
     * @param nbf A "not process before" timestamp defining an allowed start time for processing
     * @param exp 有效时间，单位秒（s）, 0 永不过期
     * @param jti Some kind of unique ID for the token
     * @param typ A "type" of token. In this case it's URL but it could be a media type like these
     * @param others Custom parameters
	 * @return String
	 */
	public static String genJWT(String iss, String aud, String nbf, long exp, String jti, String typ, Map<String, Object> others) {
		final JWTSigner signer = new JWTSigner(secret);
		final Map<String, Object> claims = new HashMap<String, Object>();
		final long iat = System.currentTimeMillis() / 1000l; // issued at claim 
		
		if (!StringUtils.isEmpty(iss))
			claims.put("iss", iss);
		if (!StringUtils.isEmpty(aud))
			claims.put("aud", aud);
		if (!StringUtils.isEmpty(nbf))
			claims.put("nbf", nbf);
		if (!StringUtils.isEmpty(jti))
			claims.put("jti", jti);
		if (!StringUtils.isEmpty(typ))
			claims.put("typ", typ);
		claims.put("iat", iat);
		
		if (exp > 0) {
			claims.put("exp", iat + exp);
		}
		
		if(others != null){
			for (String key : others.keySet()) {
				claims.put(key, others.get(key));
			}
		}
		
		return signer.sign(claims);
	}
	/**
	 * 验证token有效性
	 * @param token
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalStateException
	 * @throws SignatureException
	 * @throws IOException
	 * @throws JWTVerifyException
	 */
	public static Map<String, Object> verify(String token) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException {
		final JWTVerifier verifier = new JWTVerifier(secret);
	    return verifier.verify(token);
	}
}
