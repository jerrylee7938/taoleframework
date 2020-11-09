<%@ page language="java" import="java.util.*" contentType="application/javascript"
    pageEncoding="UTF-8"%>	
	
	<%
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
	%>
	
	var $userId = '<%=session.getAttribute("usercode")%>';
	var $commonRoot ='http://service1.99melove.cn/common';//ext4.2公用包
	
    var $service = {
    //	rest: 'http://member.kidswe.com:8280/member-service/service/rest',
    //	portal: 'http://kids.kidswe.com/taole-portal-service/service/rest',
    //	member: 'http://member.kidswe.com/member-service/service/rest'
     	rest: 'http://kids.member.51taole.cn/member-service/service/rest',
     	portal: 'http://39.104.176.177:8060/taole-portal-service/service/rest',
  		member: 'http://39.104.176.177:8080/member-service/service/rest'
    	
    };
    var $syspath = 'http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>';//项目根路径
    var $username = '<%=session.getAttribute("realname")%>';//账号
    var $ctx = 'http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>';//web项目的发布路径
