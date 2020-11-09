package com.taole.member.utils;

import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ReturnCode;

public class ReturnCodeDefine extends ReturnCode{
	
	//公共部分返回码 注：因公共返回码为所有方法共有返回码，数组长度为3不需要指明具体哪个方法使用
	public final static String[] SUCCESS =  {String.valueOf(ReturnResult.SUCCESS), "操作成功！", "操作成功！"};
	public final static String[] FAILURE = {String.valueOf(ReturnResult.FAILURE), "操作失败！", "操作失败！"};
	
	//群组管理返回码 注：此类返回码是具体到某个方法用到的返回码，数组长度为4 第一个参数为具体某个方法多个用逗号粉盒，命名方法是service名加上方法名
	public final static String[] NO_OPENID =  {"101", "用户没有绑定openId！", "用户没有绑定openId！"};
}
