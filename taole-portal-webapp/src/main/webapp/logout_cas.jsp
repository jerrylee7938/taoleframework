<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@include file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登出</title>
</head>
<body>
<%
String casLogoutService = getServletContext().getInitParameter("casServerLogoutUrl"); 
String casClientLoginPage =  getServletContext().getInitParameter("casClientLoginUrl"); 

session.invalidate();
response.sendRedirect(casLogoutService+"?service="+casClientLoginPage);
%>
</body>
</html>