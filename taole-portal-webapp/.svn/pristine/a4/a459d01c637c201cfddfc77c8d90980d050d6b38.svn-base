<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
	 <link type="text/css" href="<%=request.getContextPath() %>/css/css.css" rel="stylesheet" />
	 <link type="text/css" href="<%=request.getContextPath() %>/css/login.css" rel="stylesheet" />

	 <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/ext-all.js"></script>
	 <script type="text/javascript" src="<%=$commonRoot %>/extjs4.2/locale/ext-lang-zh_CN.js"></script>
	 <link rel="stylesheet" href="<%=$commonRoot %>/extjs4.2/resources/css/ext-all.css" type="text/css">
	 <script src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath() %>/include/service.js"></script>

<style>
	.x-border-box, .x-border-box *{
		box-sizing: content-box !important;
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
	<img src="../../image/login/step_1.png"/>
	</div>
	<div class="step_title">
	<span class="step_wz step_wz1">填写信息</span>
	<span class="step_wz step_wz2">提交申请</span>
	<span class="step_wz step_wz3">账号激活</span>
	</div>
</div>
<div class="main_login_3_2">
<form method="post" action="" id="form1">
  <table width="800px" class="reg_tab" border="0" cellspacing="0">
  	<tr>
    	<td align="right"><span class="f-c-red">*</span>姓名：</td>
    	<td><input class="kv_inp" type="text" id="realName" name="realName" onblur="validate(this);"/><span class="f-l" style="float:none;" id="info_realName"></span></td>
  	</tr>
  	<tr>
    	<td align="right"><span class="f-c-red">*</span>身份证号：</td>
    	<td><input class="kv_inp" type="text" id="cardId" name="cardId" onblur="validate(this);"/><span class="f-l" style="float:none;" id="info_cardId"></span></td>
  	</tr>
  	<tr>
    	<td align="right"><span class="f-c-red">*</span>手机号码：</td>
    	<td><input class="kv_inp" type="text" id="mobileNum" name="mobileNum" onblur="validate(this);"/><span class="f-l" style="float:none;" id="info_mobileNum"></span></td>
  	</tr>
  	<tr>
    	<td width="104" align="right"><span class="f-c-red">*</span>邮箱：</td>
    	<td width="482"><input class="kv_inp" type="text" id="email" name="email" onblur="validate(this);" /><span class="f-l" style="float:none;" id="info_email"></span></td>
  	</tr>
  	<tr>
    	<td align="right"><span class="f-c-red">*</span>密码：</td>
    	<td><input class="kv_inp" type="password" id="password"  name="password" onblur="validate(this);"/><span class="f-l" id="info_password" style="float:none;"></span></td>
    	
 	</tr>
  	<tr>
    	<td align="right"><span class="f-c-red">*</span>重复密码：</td>
    	<td><input class="kv_inp" type="password" id="repassword"  onblur="validate(this);"/><span class="f-l" style="float:none;" id="info_repassword"></span></td>
  	</tr>
</table>
</form>
<div class="btn_1">
	<a class="btn_img1" href="javascript:void(0);" onclick="doValidate();"><img src="../../image/login/register_btn.png" /></a>
</div>
<div class="f-l" style="padding-top:40px; padding-left:20px;"></div>
<div class="c-b"></div>
</div>
</div>
   <script type="text/javascript">
   function openLogin() {
		window.location.href = '<%=$ctx%>'+"/login.jsp";
	}
   	function validate(field){
   		var value = field.value;
   		var id = field.id;
   		var emailReg ='/^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$/';
   		var cardIdReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
   		var mobileNumReg = /^1[0-9]{10}$/;
   		var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
   		if(id =='cardId'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"身份证号不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else if(cardIdReg.test(value) === false){
   				$("#info_"+id).html(notice_img+"请输入正确的身份证号!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
   	   			$(field).addClass('kv_ture');
   			}
   		}
   		if(id =='realName'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"姓名不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
	   			$(field).addClass('kv_ture');
   			}
   		}
   		if(id =='mobileNum'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"手机号码不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else if(mobileNumReg.test(value) === false){
   				$("#info_"+id).html(notice_img+"请输入正确的手机号码!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
	   			$(field).addClass('kv_ture');
   			}
   		}
   		if(id =='email'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"邮箱不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else if(!eval(emailReg).test(value)){
   				$("#info_"+id).html(notice_img+"请输入正确的邮箱地址!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
	   			$(field).addClass('kv_ture');
   			}
   		}
   		if(id =='password'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"密码不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else if(value.length < 8){
   				$("#info_"+id).html(notice_img+"密码长度不能少于8位!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
	   			$(field).addClass('kv_ture');
   			}
   		}
   		if(id =='repassword'){
   			if(value ==''){
   				$("#info_"+id).html(notice_img+"重复密码不能为空!").css({'color':'red'});
   				$(field).removeClass('kv_ture');
   			}else{
   				$("#info_"+id).html("");
	   			$(field).addClass('kv_ture');
   			}
   		}
   	}
    Ext.onReady(function(){
    	
    	window.alertDiag = function(title,msg){
    		Ext.Msg.alert(title,msg);
    	}
    });
   	function doValidate(){
   		var realName = $("#realName").val();
   		var cardId = $("#cardId").val();
   		var email = $("#email").val();
   		var nickName = $("#nickName").val();
   		var password = $("#password").val();	
   		var repassword = $("#repassword").val();	
   		var mobileNum = $("#mobileNum").val();
   		var emailReg ='/^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$/';
   		var cardIdReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
   		var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
   		if(realName ==""){
   			$("#info_realName").html(notice_img+"姓名不能为空!").css({'color':'red'});
   			return false;
   		}
   		if(mobileNum ==""){
   			$("#info_mobileNum").html(notice_img+"手机号码不能为空!").css({'color':'red'});
   			return false;
   		}
   		if(cardId ==""){
   			$("#info_cardId").html(notice_img+"身份证号不能为空!").css({'color':'red'});
   			return false;
   		}else{
   			if(cardIdReg.test(cardId) === false){
   				$("#info_cardId").html(notice_img+"请输入正确的身份证号!").css({'color':'red'});
   	   			return false;
   			}
   		}
   		if(email ==""){
   			$("#info_email").html(notice_img+"邮箱不能为空!").css({'color':'red'});
   			return false;
   		}else{
   			if(!eval(emailReg).test(email)){
   				$("#info_email").html(notice_img+"请输入正确的邮箱地址!").css({'color':'red'});
   				return false;
   			}
   		}
   		if (password == '') {
   			$("#info_password").html(notice_img+"请输入密码!").css({'color':'red'});
   			return false;
   		}
   		if (password.length < 8) {
   			$("#info_password").html(notice_img+"密码长度不能少于8位!").css({'color':'red'});
   			return false;
   		}else{
   			$("#info_password").html('');
   		}
		if(password != repassword){
			$("#info_repassword").html(notice_img+"两次输入的密码不同!").css({'color':'red'});
			$('#repassword').removeClass('kv_ture');
			return false;
		}
   		$.post( $service.portal + "/us.User/collection/register", $( "#form1" ).serialize(), function(data){
   			if (data.code == 1) {
   				window.location.href ="regresult.jsp?email="+$("#email").val();
   			} else {
   				alertDiag("提示",'注册失败:' + data.description);
   			}
   		});
   	}
   </script>  

</div>
</body>
</html>
