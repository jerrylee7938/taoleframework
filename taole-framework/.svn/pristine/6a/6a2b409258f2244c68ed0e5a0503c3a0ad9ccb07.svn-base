/*
 * @(#)MD5Util.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * @since 2006-5-19 11:44:38
 */
public abstract class DESUtil {

	public static Key getKey(String strKey) {
		return getKey(strKey.getBytes());
	}

	public static Key getKey(byte[] bytesKey) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(bytesKey);
			generator.init(secureRandom);
			return generator.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Key randomKey() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom());
			return generator.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] encrypt(Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public static byte[] decrypt(Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	/**
	 * for debug
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Key key = randomKey();
		byte[] bs = encrypt(key, new byte[]{1,2,3});
		for (byte b : bs) {
		System.out.println(b);
		}
	}

}
