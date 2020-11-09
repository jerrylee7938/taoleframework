<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>微信平台账号管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ablum.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/TimePickerField.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/DateTimePicker.js"></script>  	
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/DateTimeField.js"></script>
</head> 
  <body>   
	<script type="text/javascript">

		gridRowEditing();
		Ext.Loader.setConfig({
			enabled:true//, disableCaching: false
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.wechat.wechatAccount.controller.WechatAccountCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'wechatAccountPanel'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
