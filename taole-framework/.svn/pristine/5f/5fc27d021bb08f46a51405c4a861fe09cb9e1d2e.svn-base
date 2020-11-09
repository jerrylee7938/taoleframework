package com.taole.framework.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ReturnCode {

	/**
	 * 自动生成返回码页面调用方法，根据方法名返回返回码信息
	 * @param apiName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 */
	public static String getReturnCodeForSwagger(String apiName, String className) throws IllegalArgumentException, 
		IllegalAccessException, SecurityException, ClassNotFoundException{
		Map<String, String> returnCodeMap = new HashMap<String, String>();
		
		// 获取所有的变量
		Field[] fields = Class.forName(className).getDeclaredFields();
		
		// 循环处理变量
		for(Field field : fields){
			field.setAccessible(true);
			Object value = field.get(ReturnCode.class);// 取变量的值
//			System.out.println("变量名称为：" + field.getName());
			if (value.getClass().isArray()) { // 判断是否是数组
				String[] arr = (String[]) value; // 装换成数组
//				System.out.println("变量值等于：" + Arrays.toString(arr));
				if(arr.length == 4){
					String name = arr[3];
					for(String key : name.split(",")){
						StringBuffer stringBuffer = new StringBuffer();
						stringBuffer.append("&emsp;&emsp;" + arr[0] + "：" + (StringUtils.isNotBlank(arr[2]) ? arr[2] : arr[1]) + "</br>");
						if(returnCodeMap.containsKey(key)){
							returnCodeMap.put(key, returnCodeMap.get(key)+stringBuffer.toString());
						}else{
							returnCodeMap.put(key, stringBuffer.toString());
						}
					}
				}
			}
		}
		
		// 循环处理变量
		for(Field field : fields){
			field.setAccessible(true);
			Object value = field.get(ReturnCode.class);// 取变量的值
			if (value.getClass().isArray()) { // 判断是否是数组
				String[] arr = (String[]) value; // 装换成数组
				if(arr.length == 3){
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("&emsp;&emsp;" + arr[0] + "：" + (StringUtils.isNotBlank(arr[2]) ? arr[2] : arr[1]) + "</br>");
					if(!returnCodeMap.containsKey(apiName)){
						returnCodeMap.put(apiName, stringBuffer.toString());
					}else{
						for (Map.Entry<String, String> entry : returnCodeMap.entrySet()) {
							returnCodeMap.put(entry.getKey(), stringBuffer.toString() + entry.getValue());
						}
					}
				}
			}
		}
		
		return returnCodeMap.get(apiName);
	}
	
	/**
	 * 将返回码数组组合成返回信息
	 * @param returnCode
	 * @return
	 */
	public static Object getServiceResult(String[] returnCode){
		int code = 1;
		String desc = null;
		if(returnCode.length == 3){
			code = Integer.valueOf(returnCode[0]);
			desc = returnCode[2];
			if(StringUtils.isBlank(desc))
				desc = returnCode[1];
		}else if(returnCode.length == 4){
			code = Integer.valueOf(returnCode[1]);
			desc = returnCode[3];
			if(StringUtils.isBlank(desc))
				desc = returnCode[2];
		}
			
		return new ServiceResult(code, desc);
	}
	
	/**
	 * 将返回码数组组合成返回信息
	 * @param returnCode
	 * @return
	 */
	public static Object getServiceResult(String[] returnCode, String desc){
		int code = 1;
		if(returnCode.length == 3){
			code = Integer.valueOf(returnCode[0]);
		}else if(returnCode.length == 4){
			code = Integer.valueOf(returnCode[1]);
		}
			
		return new ServiceResult(code, desc);
	}
	
	/**
	 * 将返回码数组组合成返回信息
	 * @param returnCode
	 * @return
	 */
	public static Object getServiceResult(String[] returnCode, Object object){
		int code = 1;
		String desc = null;
		if(returnCode.length == 3){
			code = Integer.valueOf(returnCode[0]);
			desc = returnCode[2];
			if(StringUtils.isBlank(desc))
				desc = returnCode[1];
		}else if(returnCode.length == 4){
			code = Integer.valueOf(returnCode[1]);
			desc = returnCode[3];
			if(StringUtils.isBlank(desc))
				desc = returnCode[2];
		}
			
		return new ServiceResult(code, desc, object);
	}

}
