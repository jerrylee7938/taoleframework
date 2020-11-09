<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/session.jspf" %>
<%@include file="/include/page.jspf"%>
<%
    String title = request.getParameter("title");
    if (title != ""&&title!= " "&&title!=null) {
    	title = $systemName + " => " + title;
    } else {
    	title = $systemName;
    }
%>	
    <meta charset="UTF-8">
	<title><%=title %></title>
	
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ext-all.js"></script>
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ux/TreePicker.js"></script>
    <script type="text/javascript" src="<%=$ctx%>/js/ajaxhook.min.js"></script>
	<script type="text/javascript" src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>

    <link rel="stylesheet" href="<%=$commonRoot %>/extjs4.2/resources/css/ext-all.css" type="text/css">
    <!-- 如果去掉注释，让此配置生效的话，弹窗的关闭功能将不起作用
    <link rel="stylesheet" href="<%=$commonRoot %>/css/sweb-all.css" type="text/css"></link> 
     -->
	
	<script type="text/javascript" src="<%=$ctx%>/js/jquerySession.js"></script>
	<script type="text/javascript" src="<%=$ctx%>/include/env.js.jsp"></script>
	<script type="text/javascript" src="<%=$ctx%>/js/jauery-form.js"></script>
    <script type="text/javascript" src="<%=$ctx%>/js/common.js"></script>
    <script type="text/javascript" src="<%=$ctx%>/include/service.js"></script>
    <script type="text/javascript" src="<%=$ctx%>/js/interfaces.js"></script>
