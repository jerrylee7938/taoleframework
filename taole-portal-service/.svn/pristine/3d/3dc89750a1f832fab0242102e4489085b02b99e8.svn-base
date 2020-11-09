<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.taole.usersystem.Constants"%>
<%@ page import="java.util.Map"%>
<%
Map<String, String> userUrlRes = (Map<String, String>) session.getAttribute(Constants.USER_URL_RES_KEY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
<%
  if (userUrlRes!=null){
	  for (Map.Entry<String, String> entry : userUrlRes.entrySet()) {  
%>
      <%=entry.getKey()%>;<%=entry.getValue()%>;
<%
	  }
  }else{
%>
无权限session
<% 
  }
%>
</body>
</html>