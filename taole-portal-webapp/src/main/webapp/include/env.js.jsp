<%@ page language="java" import="java.util.*" contentType="application/javascript"  pageEncoding="UTF-8"%>	
var $ctx = 'http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>';//web项目的发布路径
//应用的根http访问地址（js文件使用）

//js公共模组的访问地址（js文件使用）
var $commonRoot = 'http://admin.99melove.com/common';

//是否使用cas登录
var $isCasLogin = false;