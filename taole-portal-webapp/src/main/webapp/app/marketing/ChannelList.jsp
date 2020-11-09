<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>外部推荐渠道</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="<%=$ctx%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=$ctx%>/js/ux/form/TimePickerField.js"></script>
	<script type="text/javascript" src="<%=$ctx%>/js/ux/DateTimePicker.js"></script>
	<script type="text/javascript" src="<%=$ctx%>/js/ux/form/DateTimeField.js"></script>
</head> 
  <body>   
	<script type="text/javascript">
		Ext.QuickTips.init();
		Ext.tip.QuickTipManager.init();
		Ext.Loader.setConfig({
			enabled:true
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.marketing.controller.ChannelCtr'
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'channelMain'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
