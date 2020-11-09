<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<!DOCTYPE html>
<html>
<head>
	<link type="text/css" href="<%=request.getContextPath() %>/css/css.css" rel="stylesheet" />
	<link type="text/css" href="<%=request.getContextPath() %>/css/login.css" rel="stylesheet" />
	<script src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/include/service.js"></script>
</head>


<body>
<div class="box">
<div class="mian">
<div class="tab_tit">
	<div class="tab_tit_1 tab_reg">找回密码</div>
</div>
<div class="step">
	<div class="step_img">
	<img src="../../image/login/step_1.png"/>
	</div>
	<div class="step_title">
	<span class="step_wz step_wz1">身份认证</span>
	<span class="step_wz step_wz2">重置密码</span>
	<span class="step_wz step_wz3">完成</span>
	</div>
</div>
<div class="main_login">
<table width="800" class="kv_tab" border="0" cellspacing="0" id="table">
  	<tr>
	    <td align="left" class="p-l-20">
	    	<div class="f-l"  style="width:250px;text-align:right;">请输入注册时所用邮箱：</div>
	    	<div class="f-l"><input type="text" class="kv_tx" id="email" onblur="validateEmail();"/></div>
	    	<div id="info"></div>
	    </td>
   	</tr>

  	<tr>
	    <td align="left" class="p-l-20">
	    	<div class="f-l"  style="width:250px;text-align:right;">验证码：</div>
	    	<div class="f-l"><input type="text" class="kv_tx1"  id="code"/></div>
	    	<div class="kv_yzm" style="height: 25px;padding-right: 10px;padding-top: 5px;"><img id="imgCode" onClick="goRefurbish();" src="<%=request.getContextPath() %>/code.jsp"  border="1" style="cursor:pointer" style="height:20px;margin-top: -10px;"/></div>
	    	<span class="f-l"><a href="javascript:void(0);" onclick="goRefurbish()" class="kv_link">看不清</a></span>
	    	<div id="code_info" style="margin-left: 430px;"></div>
	    </td>
  	</tr>
  	<tr>
    	<td align="left" class="p-l-20" style="text-align:center;">
			<a class="btn_img1" style="margin-top: 30px;" href="javascript:void(0);" onclick="GetPassword();"><img src="../../image/login/next1.png" /></a>
		</td>
  	</tr>
</table>
<table width="800" class="kv_tab" border="0" cellspacing="0" id="message"  style="display:none;">
	<tr>
		 <td class="p-l-20" style="width:750px; line-height:20px;color:red;text-indent:25px; line-height:30px;text-align: center;">
		已向您的邮箱发送了重置密码的链接，请查收注册邮箱<br>如在10分钟之内没有收到，请
		 <a href="javascript:void(0)" onclick ="resent();">重新操作</a>
		 </td>
	</tr>
</table>
<div class="c-b"></div>
</div>
<div class="c-b"></div>
</div>


</div>
<script type="text/javascript">
	function goRefurbish(){    
		var imgCode = document.getElementById("imgCode");
		//页面刷新需要重新请求，就是相同URL，不同的参数，这也是Ajax请求的机制.
		imgCode.src = "<%=request.getContextPath()%>/code.jsp?d="+new Date();
	}
	function resent(){
		$("#message").css({'display':'none'});
		
		$("#table").css({'display':'block'});
	}
	function validateEmail(){
		var emailReg ='/^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$/';
		var email = document.getElementById("email").value;
		var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
		if(email==null || email==""){
			$("#info").html(notice_img+"邮箱地址不能为空!").css({'color':'red'});
			$("#email").removeClass('kv_ture');
			return false;
		}else if(!eval(emailReg).test(email)){
			$("#info").html(notice_img+"请输入正确的邮箱地址!").css({'color':'red'});
			$("#email").removeClass('kv_ture');
		}else{
			$("#info").html("");
			$("#email").addClass('kv_ture');
			return true;
		}
	}
	
	//读取cookie中指定name的值
	function getCookie(name) {
	    var strCookie = document.cookie;
	    var arrCookie = strCookie.split(";")
	    for (var i = 0; i < arrCookie.length; i++) {
	        var arr = arrCookie[i].split("=");

	        if (arr[0].trim() == name)
	            return arr[1];
	    }
	    return "";
	}
	
	function GetPassword(){
		var email = document.getElementById("email").value;
		var code = document.getElementById("code").value;
		var _code = getCookie("code");
		var bool = false;
		var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
		if(email==null || email==""){
			$("#info").html(notice_img+"请输入邮箱地址!").css({'color':'red'});
			bool = false;			
		}else{
			bool = validateEmail();
		}
		if(bool){
			if(code==""){
				$("#code_info").html(notice_img+"请输入验证码").css({'color':'red'});
				bool = false;
			}else if(code !=_code){
				$("#code_info").html(notice_img+"请输入正确的验证码").css({'color':'red'});
				bool = false;
			}else{
				bool = true;
			}
		}
		var number = 600;
		var count = 0;
		if(bool){
			$.ajax({
				type:'post',
				url:$service.portal+'/us.User/collection/findpassword?email='+email,
				success:function(data){
					
					if(data.code==1){
						$(".sureBtn").attr("disabled",true); 
						$("#message").css({'display':'block'});
						$("#table").css({'display':'none'});
					}else{
						var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
						$("#info").html(notice_img+data.description).css({'color':'red'});
						$("#email").removeClass('kv_ture');
					}
					
				},
				failure:function(){
					
				}
			});
		}
		
	}
</script>
</body>
</html>
