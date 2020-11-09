/*
 * @(#)RSAKeyGenerator.java 0.1 Dec 24, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * Class <code>RSAKeyGenerator</code> is ...
 * 
 * @author 
 * @version 0.1, Dec 24, 2008
 */
public class RSAUtil {

	public static void generateKeyFiles(int in, String savePath)
			throws NoSuchAlgorithmException {

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(in);
		KeyPair kp = kpg.genKeyPair();

		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		ObjectOutputStream oos1 = null;
		try {
			oos1 = new ObjectOutputStream(new FileOutputStream(savePath
					+ "/public_key.dat"));
			oos1.writeObject(publicKey);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos1 != null) {
					oos1.close();
				}
			} catch (IOException e) {

			}
		}

		ObjectOutputStream oos2 = null;
		try {
			oos2 = new ObjectOutputStream(new FileOutputStream(savePath
					+ "/private_key.dat"));
			oos2.writeObject(privateKey);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos2 != null) {
					oos2.close();
				}
			} catch (IOException e) {

			}
		}

	}

	public static Key getKey(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		return getKey(new FileInputStream(file));
	}

	public static Key getKey(InputStream is) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(is);
		Key key = (Key) ois.readObject();
		return key;
	}

	public static byte[] encrypt(Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}

	}

	public static byte[] decrypt(Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public static void main(String[] args) {
		try {
			generateKeyFiles(2048, "c:");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
