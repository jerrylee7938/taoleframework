<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>APP版本管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
</head> 
  <body>   
	<script type="text/javascript">

	
		var APP_VER_SYSTEM_TYPE = "<%=request.getContextPath() %>/app/appManager/appVer/data/systemType.json";
		var APP_VER_UPDATE_TYPE = "<%=request.getContextPath() %>/app/appManager/appVer/data/updateType.json";
		var APP_VER_APP_STATUS = "<%=request.getContextPath() %>/app/appManager/appVer/data/appStatus.json";
	
		Ext.Loader.setConfig({
			enabled:true//, disableCaching: false
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.appManager.appVer.controller.AppDownLoadCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'appDownLoadPanel'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
