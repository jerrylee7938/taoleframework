<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>营销推荐码管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ablum.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/TimePickerField.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/DateTimePicker.js"></script>  	
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/DateTimeField.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadPicture.js"></script>
</head> 
  <body>   
	<script type="text/javascript">

	
		Ext.Loader.setConfig({
			enabled:true//, disableCaching: false
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.strategy.referralCode.controller.ReferralCodeCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'referralCodePanel'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
