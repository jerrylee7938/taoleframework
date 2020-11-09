<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>消息配置</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/noticeDeploy.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
  	</head>
  <body>
	  <script type="text/javascript">
	  		var NOTICE_TYPE = '<%=com.taole.usersystem.Constants.NOTICE_SENDTYPE_CODE%>';
	  		var NOTICE_TYPE_CODE = '<%=com.taole.usersystem.Constants.NOTICE_TYPE_CODE%>';
	  	</script>
  </body>
</html>