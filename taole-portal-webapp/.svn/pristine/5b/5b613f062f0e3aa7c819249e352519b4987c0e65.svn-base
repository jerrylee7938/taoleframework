<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<%
	String appId = request.getParameter("appId");
%>
<html>
<head>
	<title>数据权限管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
</head> 
  <body>   
	<script type="text/javascript">

		
		var appId = '<%=appId%>';
		Ext.Loader.setConfig({
			enabled:true//, disableCaching: false
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.dataPrivilege.dataPrivilegeManager.controller.DataPrivilegeCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
			    		{
			    			xtype: 'dataPrivilegePanel',
			    			appId: appId
			    		}
		    		]
		    	})
		    }
		});
		function configValidate(){
			Ext.create('Taole.dataPrivilege.dataPrivilegeManager.controller.AddOrEditDataPrivilegeCtrl').configValidate();
		}
	</script> 
  </body>
</html>
