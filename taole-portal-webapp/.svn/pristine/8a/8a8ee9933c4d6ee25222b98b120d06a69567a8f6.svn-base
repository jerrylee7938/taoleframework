<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %>
<%
String email = request.getParameter("email");
%><!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ext-all.js"></script>
	<script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/locale/ext-lang-zh_CN.js"></script>
	<link rel="stylesheet" href="<%=$commonRoot %>/extjs4.2/resources/css/ext-all.css" type="text/css">
	<link type="text/css" href="<%=request.getContextPath() %>/css/css.css" rel="stylesheet" />
	<link type="text/css" href="<%=request.getContextPath() %>/css/login.css" rel="stylesheet" />
	<script src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript">
var emailLoginUrlArrar = ['@gmail.com=http://mail.google.com/',
      '@163.com=http://mail.163.com/',
      '@126.com=http://mail.126.com/',
      '@hotmail.com=http://www.hotmail.com/',
      '@sina.com=http://mail.sina.com/',
      '@vip.sina.com=http://mail.sina.com/',
      '@tom.com=http://mail.tom.com/',
      '@qq.com=http://mail.qq.com/',
      '@139. com=http://mail.10086.cn/',
      '@msn.com=https://login.live.com/login.srf',
      '@jkmedical.cn=https://qiye.163.com/login/',
      '@sohu.com=http://mail.sohu.com/'];

  function getEmailLoginUrl(email) {

      email = email.toLowerCase();
      if (email == "") {
          return null;
      }
      var index = email.indexOf("@");
      var emailSurfix = email.substring(index, email.length);
      for (var i = 0; i < emailLoginUrlArrar.length; i++) {
          if (emailLoginUrlArrar[i].indexOf(emailSurfix) == 0) {
              return emailLoginUrlArrar[i].split("=")[1];
          }
      }
      return null;
  }
  
  window.onload = function(){
	  var email = $("#email").html();
	  var mailbox = getEmailLoginUrl(email);
	  $("#mailbox").attr("href",mailbox);
	  
  }
  function openLogin() {
		window.location.href = '<%=$ctx%>'+"/login.jsp";
	}
</script>
<style>
	.x-border-box, .x-border-box *{
		box-sizing: content-box !important;
	}
	.kv_cg{
		font-size:16px;
		text-indent:25px;
		text-align:center;
		line-height: 32px;
		width: 700px;
		padding-top:30px;
		font-weight: bold;
	}
	.kv_btn_2 a {
		color:black;
	}
</style>
</head>

<body>
<div class="box">
	<div class="mian">
		<div class="tab_tit">
			<div class="tab_tit_1" onclick="openLogin();">登录</div>
			<div class="tab_tit_1 tab_reg">注册</div>
		</div>
		<div class="step">
	<div class="step_img">
		<img src="../../image/login/step_2.png"/>
		</div>
		<div class="step_title">
		<span class="step_wz step_wz1">填写信息</span>
		<span class="step_wz step_wz2 step_wz22">提交申请</span>
		<span class="step_wz step_wz3">账号激活</span>
		</div>
	</div>
		<div class="main_login_3_2">
			<div class="kv_cg" style="">感谢您注册，我们已将激活码发送到您的注册邮箱<span style="text-decoration:underline;"><font color="#f60" id="email"><%=email%></font></span><br>请在24小时内完成帐号激活流程</div>
			<div class="kv_txt_2"> <br/><br/>
				<div class="kv_btn_2"><a href="javascript:void(0);" id="mailbox"><img src="../../image/login/loginEmail.png" /></a></div>
				<div class="f-l" style="padding-top:40px; padding-left:20px;"></div>
				<div class="c-b"></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>