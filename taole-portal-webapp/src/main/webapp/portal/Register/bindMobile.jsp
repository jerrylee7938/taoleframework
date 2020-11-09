<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
	<link type="text/css" href="<%=request.getContextPath() %>/css/css.css" rel="stylesheet" />
	<link type="text/css" href="<%=request.getContextPath() %>/css/login.css" rel="stylesheet" />
</head>

<body>
	<div class="box">
		<div class="kv_logo_box">
			<iframe src="top.html" width="900 " height="80" frameborder="0" scrolling="no"></iframe>
		</div>
		<div class="mian">
			<div class="tab_tit">
				<div class="tab_tit_1">绑定手机</div>
			</div>
			<div class="main_login_4">
		  		<table width="598" class="kv_tab" border="0" cellspacing="0">
			  		<tr>
			    		<td width="596" align="left" class="p-l-20">
			    			<div class="f-l" style="width:180px;text-align:right;">请输入注册时所用邮箱：</div>
			    			<div class="f-l p-t-5"><input type="text" class="kv_tx" /></div>
			    			<span class="f-1"></span>
			    		</td>
			    	</tr>
			  		<tr>
			    		<td width="596" align="left" class="p-l-20">
			    			<div class="f-l" style="width:180px;text-align:right;">手机号码：</div>
			    			<div class="f-l p-t-5">
			    				<input type="text" class="kv_tx" />
			    			</div>
			    			<span class="f-l"><a href="#" class="kv_link p-l-10">获取验证码</a></span>
			    		</td>
			    	</tr>
			
			  		<tr>
			    		<td width="596" align="left" class="p-l-20">
			    			<div class="f-l" style="width:180px;text-align:right;">短信验证码：</div>
			    			<div class="f-l p-t-5"><input type="text" class="kv_tx" /></div>
			    			
			    		</td>
			    	</tr>
			  		<tr>
			    		<td align="left" class="p-l-20" style="text-align:center;">
			    			<input type="button" class="sureBtn" value="确定"/></td>
			    	</tr>
			    	<tr>
			    		<td align="center">&nbsp;</td>
			    	</tr>
				</table>
			<div class="c-b"></div>
		</div>
			<div class="c-b"></div>
			<div class="footer_box"><iframe src="footer.html" width="900 " height="60" frameborder="0" scrolling="no"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
