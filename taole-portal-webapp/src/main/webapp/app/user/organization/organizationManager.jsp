<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>组织管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/organizationManager.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
  	</head>
  <body>
	  <script type="text/javascript">
	  		var ORGNODE_TYPE_CODE = '<%=com.taole.usersystem.Constants.ORGNODE_TYPE_CODE%>';
	  		var ORGNODE_TYPE_DICT  = '<%=com.taole.usersystem.Constants.ORGNODE_TYPE_DICT %>';//组织目录
	  		var ORGNODE_TYPE_POST  = '<%=com.taole.usersystem.Constants.ORGNODE_TYPE_POST %>';//岗位
	  		Ext.Loader.setConfig({
				enabled:true//, disableCaching: false
			});
			Ext.application({
	   			name: 'Taole',
	   			appFolder: '<%=request.getContextPath() %>/app'
			});
	  	</script>
  </body>
</html>