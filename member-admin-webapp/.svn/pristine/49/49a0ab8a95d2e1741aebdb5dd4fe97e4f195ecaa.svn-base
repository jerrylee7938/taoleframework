<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>店面管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ablum.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/TimePickerField.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/DateTimePicker.js"></script>  	
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/DateTimeField.js"></script>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/HtmlEditor.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/HtmlEditor.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadPicture.js"></script>
	 <style>
  		
		.x-grid-cell-inner{white-space:normal;word-wrap:break-all;line-height: 150%;};
		#l_grid-body td,#c_grid-body td,#j_grid-body td,#f_grid-body td{height: 32px;
			vertical-align: middle;
		}
		.x-grid-td{vertical-align:middle;}
  	</style>
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
	       		'Taole.store.storeManager.controller.StoreCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'storePanel'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
