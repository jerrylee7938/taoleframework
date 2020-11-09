/*
 * @(#)SystemInfoChecker.java 0.1 Apr 10, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.taole.framework.util.MD5Util;
import com.taole.framework.util.StringUtils;
import com.taole.framework.util.UUID;

public abstract class SystemInfoPolicy {

	public static int checkSystemInfo(SystemInfo info) {
		return checkProductKey(info.getProductId(), info.getUser(), info.getMachineCode(), info.getProductKey());
	}

	public static int checkProductKey(String productId, String user, String machineCode, String productKey) {
		if (StringUtils.isEmpty(productId)) {
			return -1;
		}
		if (StringUtils.isEmpty(user)) {
			return -2;
		}
		if (StringUtils.isEmpty(machineCode)) {
			return -3;
		}
		if (StringUtils.isEmpty(productKey) || productKey.length() != 16) {
			return -4;
		}
		String vc1 = getValidCode1(productId, user, machineCode);
		if (!StringUtils.compareIgnoreCase(productKey.substring(8, 13), vc1)) {
			return -5;
		}
		String vc2 = getValidCode2(productKey.substring(0, 8), vc1);
		if (!StringUtils.compareIgnoreCase(productKey.substring(13, 16), vc2)) {
			return -6;
		}
		return 1;

	}

	private static String getValidCode1(String productId, String user, String machineCode) {
		String vc0 = MD5Util.digest(productId + "|" + user + "|" + machineCode, "utf-8");
		return vc0.substring(1, 2) + vc0.substring(11, 12) + vc0.substring(3, 4) + vc0.substring(13, 14) + vc0.substring(30, 31);
	}

	private static String getValidCode2(String str, String vc1) {
		String vc2 = MD5Util.digest(str + "|" + vc1, "GBK");
		return vc2.substring(5, 6) + vc2.substring(15, 16) + vc2.substring(19, 20);
	}

	public static String generateMachineCode() {
		return UUID.generateUUID().substring(20);
	}

	public static String generateProductKey(String licNumber, String productId, String user, String machineCode) {
		if (StringUtils.isEmpty(licNumber)) {
			licNumber = UUID.generateUUID();
		} else {
			licNumber = licNumber + "000000000";
		}
		licNumber = licNumber.substring(0, 8);
		String vc1 = getValidCode1(productId, user, machineCode);
		String vc2 = getValidCode2(licNumber, vc1);
		String pk = licNumber + vc1 + vc2;
		return pk;
	}

	public static void main(String[] args) {
		// String pk = generateProductKey("", "HOMOLO-LENCHY-FAMILY-SBA001", "上海市律师协会", "d4b800b0fb4b");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter Custom Info or 'Enter'" + ":");
			String info = br.readLine();
			System.out.print("Enter Product ID:");
			String productId = br.readLine();
			System.out.print("Enter User:");
			String user = br.readLine();
			System.out.print("Enter MachineCode:");
			String machineCode0 = br.readLine();
			System.out.print("Confirm MachineCode:");
			String machineCode1 = br.readLine();
			if (!machineCode0.equals(machineCode1)) {
				System.out.println("error machinecode !");
				return;
			}
			String productKey = generateProductKey(info, productId, user, machineCode0);
			System.out.print("Product Key:" + productKey);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
