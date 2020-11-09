<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"  pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf"%>
<%-- <%@include file="/include/constants.jsp" %> --%>
<%
	String title = request.getParameter("title");
	if (title!=null && !title.trim().equals("")  ) {
		title = $systemName + " => " + title;
	} else {
		title = $systemName;
	}
%>	
	<meta charset="UTF-8">
	<title><%=title %></title>
	
    <link rel="stylesheet" href="<%=$commonRoot %>/extjs4.2/resources/css/ext-all.css" type="text/css">
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ext-all.js"></script>
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ux/TreePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxhook.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/include/env.js.jsp"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jshelp/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/proxy.js"></script>
	<script type="text/javascript" src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>       
	