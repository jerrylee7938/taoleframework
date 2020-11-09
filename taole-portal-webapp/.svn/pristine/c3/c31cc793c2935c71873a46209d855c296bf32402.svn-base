<%@ page language="java" contentType="text/html; charset=UTF-8"%><%@
	include
	file="/include/page.jspf"%>
<%
	String error = request.getParameter("error");
	String message = request.getParameter("message");
	boolean success = false;
	boolean needSetPassword = false;
	if (request.getAttribute("needSetPassword") != null) {
		needSetPassword = (Boolean) request
				.getAttribute("needSetPassword");
	}
	System.out.print(message);
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><%=$systemName%></title>
	<link type="text/css" href="<%=request.getContextPath()%>/css/css.css" rel="stylesheet" />
	<link type="text/css" href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet" />
	<script src="<%=$commonRoot%>/jquery/jquery-1.6.4.js"></script>
</head>

<body>
	<div class="box">
		<div class="kv_logo_box">
			<iframe src="<%=request.getContextPath() %>/portal/Register/top.html" width="900 " height="0" frameborder="0"
				scrolling="no"></iframe>
		</div>
		<div class="mian">
			<div class="tab_tit">
				<div class="tab_tit_1">用户激活</div>
			</div>
			<div class="main_login">
				<table width="698" class="kv_tab" border="0" cellspacing="0"
					 id="table">
					<tr>
						<td width="696" align="center" class="p-l-20">
							<%if(error != null && error != "null") { %>
								<strong>激活失败</strong>
								<p><%=error %></p>
							<%} else if (message != null && message != "null") {
					  			success = true;
					  		%>
					  			<strong>激活成功</strong>
					  		<%} else{%>
					  			<strong>请设置密码</strong>
					  		<%} %>
			  			</td>
					</tr>
					<%if(success){ %>
						<tr>
						  	<td align="center"><a href="<%=request.getContextPath()%>/login.jsp">现在就登录</a></td>
						  </tr>
					<%} %>
					<%if (needSetPassword) { %>
						<tr>
							<td align="left" class="p-l-20" height="750">
								<form action="<%=request.getContextPath()%>/service/rest/us.User/collection/active">
									<input type="hidden" name="token" value="<%=request.getAttribute("token") %>" />
									<table>
										
										<tr>
											<td>
												<div class="f-l" style="width: 180px; text-align: right;">密码：</div>
												<div class="f-l p-t-5">
													<input type="password" name="password" id="password"  class="kv_tx" />
												</div>
												<div id="info"></div>	
											</td>
										</tr>
										<tr>
											<td>
												<div class="f-l" style="width: 180px; text-align: right;">确认密码：</div>
												<div class="f-l p-t-5">
													<input type="password" name="rePassword" id="rePassword" class="kv_tx" />
												</div>
												<div id="info"></div>	
											</td>
										</tr>
										<tr>
											<td align="center">
												<input type="submit" class="sureBtn" value="激活" />
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					<%} %>
				</table>
				<div class="c-b"></div>
			</div>
			<div class="c-b"></div>
			<div class="footer_box">
				<iframe src="<%=request.getContextPath() %>/portal/Register/footer.html" width="900 " height="60" frameborder="0"
					scrolling="no"></iframe>
			</div>
		</div>


	</div>
</body>
</html>