package com.taole.toolkit.util;

public class ReturnCode {
	//公共错误部分
	public final static String[] SUCCESS = {"100","Success."};
	public final static String[] SERVER_ERROR = {"203","Get a 500 error from the server."};
	public final static String[] SESSION_OUT= {"204", "The session is timeout."};
	public final static String[] NOT_FOUND_OBJECT = {"206", "DataObject can not be found by Key."};
	public final static String[] REQUEST_PRARM_ERROR = {"301","The request param is error!"};
	public final static String[] REPEAT_SUBMIT = {"302","This doc is aready submitted.It is handling."};
	public final static String[] BUSINESS_ERROR = {"300","数据错误，请注意业务操作规则"};
	
	//Token错误,返回411前端会跳登录页面
	public final static String[] TOKEN_REQUEST_NULL = {"600","tokenRequest不能为空"};
	public final static String[] TOKEN_NO_PARAM = {"601","缺少token请求参数."};
	public final static String[] TOKEN_USER_NOLOGON = {"411","用户未登录."};
	public final static String[] TOKEN_MEM_HASNO= {"411", "Token在系统中不存在."};
	public final static String[] TOKEN_ERROR = {"411", "Token匹配失败，请重新登录."};
	public final static String[] TOKEN_EXPIRED = {"411","Token过期，请重新登录."};
	public final static String[] TOKEN_JSON_ERROR = {"411","json字符串转换失败."};
	public final static String[] TOKEN_DATE_ERROR = {"603","时间类型转换失败."};
	public final static String[] TOKEN_PASS = {"100","Token校验通过."};
}
