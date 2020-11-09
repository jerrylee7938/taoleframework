<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/include/page.jspf" %>
<html>
<head>
    <title>登录模板管理</title>
    <jsp:include page="/include/header.jsp"></jsp:include>
</head>
<body>
<script src="common/common.js"></script>
<script type="text/javascript">

    Ext.Loader.setConfig({
        enabled: true//, disableCaching: false
    });

    Ext.application({
        name: 'Taole',
        appFolder: '<%=request.getContextPath()%>/app/template',
        controllers: [
            'PageElementCtr'
        ],
        launch: function () {
            Ext.create("Ext.Viewport", {
                layout: 'fit',
                items: [
                    {
                        xtype: 'elementDetail'
                    }
                ]
            })
        }
    });
</script>
</body>
</html>
