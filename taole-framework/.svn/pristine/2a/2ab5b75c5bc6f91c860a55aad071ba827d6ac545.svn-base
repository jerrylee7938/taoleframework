/*
 * @(#)MD5Util.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * @since 2006-5-19 11:44:38
 */
public abstract class MD5Util {

	public static String encryptPassword(String password) {
		return digest(password);
	}

	public static String digest(String myinfo, String encoding) {
		try {
			if (myinfo == null) {
				myinfo = "";
			}
			byte[] bin = new byte[0];
			try {
				bin = myinfo.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {

			}
			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(bin);
			byte[] digesta = alga.digest();
			return byte2hex(digesta);
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
	}

	public static String digest(String myinfo) {
		return digest(myinfo, "utf-8");
	}

	public static String digest(byte[] b) {
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(b);
			byte[] digesta = alga.digest();
			return byte2hex(digesta);
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
	}

	public static String hmac(String info, String key) {
		try {
			SecretKey sk = new javax.crypto.spec.SecretKeySpec(key.getBytes(),
					"HmacMD5");
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(sk);
			byte[] digesta = mac.doFinal(info.getBytes());
			return byte2hex(digesta);
		} catch (Exception e) {
			return info;
		}
	}

	/**
	 * 二进制转字符串
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + "";
			}
		}
		return hs.toLowerCase();
	}

	/**
	 * for debug
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MD5Util.encryptPassword("123456"));
	}

}
