<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/page.jspf"%>
<%
	String tokenId = request.getParameter("token");
	System.out.print(tokenId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>重置密码</title>
		<script type="text/javascript" src="<%=$ctx%>/include/service.js"></script>
		<script type="text/javascript" src="<%=$commonRoot%>/extjs4.2/ext-all.js"></script>
		<script type="text/javascript" src="<%=$commonRoot%>/extjs4.2/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=$commonRoot%>/jquery/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="<%=$ctx%>/js/jquerySession.js"></script>
	
		<link type="text/css" href="<%=$commonRoot%>/extjs4.2/resources/css/ext-all.css" rel="stylesheet"/>
		<link type="text/css" href="<%=request.getContextPath()%>/css/css.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet" />
		<style>
			.x-border-box, .x-border-box * {
				box-sizing: content-box !important;
			}
			
			.hide {
				display: none;
			}
		</style>
		<script type="text/javascript">
			window.token = '<%=tokenId%>';
			
			Ext.onReady(function(){
				
				window.alertDiag = function(title,msg){
		    		Ext.Msg.alert(title,msg);
		    	}
				
				window.getTokenData = function(tokenId){
					var resultData;
					Ext.Ajax.request({
						url:$service.portal+'/us.UserSystem/'+tokenId+'/retrieveForToken',
						async: false,
						success:function(resp, opts){
							var r = eval("(" + resp.responseText + ")");
							resultData = r;
						},
						failure:function(resp, opts){
							Ext.Msg.alert("提示",'获取Token信息失败！：' + resp.responseText);
						}
					});
					return resultData;
				};
				
				window.getUserData = function(userId){
					var resultData;
					Ext.Ajax.request({
						url:$service.portal+'/us.User/'+userId+'/retrieve',
						async: false,
						success:function(resp, opts){
							var r = eval("(" + resp.responseText + ")");
							resultData = r;
						},
						failure:function(resp, opts){
							Ext.Msg.alert("提示",'获取用户信息失败！：' + resp.responseText);
						}
					});
					return resultData;
				};
				
				window.changePwd = function(newPwd, url){
		    		Ext.Ajax.request({
						url:$service.portal+'/us.User/collection/changepassword?newPwd='+newPwd + "&token="+token,
						method:'post',
						success:function(resp, opts){
							var r = eval("(" + resp.responseText + ")");
							if (r.code != 1) {
								Ext.Msg.alert("提示",'修改密码失败：' + r.description);
							} else {
							/*	Ext.Msg.alert("提示",'新密码设置成功请用新密码登录!',function(){
									window.location.href = url+"/login.jsp";
								});*/
								$("#pass-form").hide();
								$(".btn_1_2").hide();
								$("#step-finish2").hide();
								$("#step-finish3").show();
								$(".step_wz3").addClass('step_wz22');
								$(".finish-detal").show();
							}
						},
						failure:function(resp, opts){
							Ext.Msg.alert("提示",'修改密码失败：' + resp.responseText);
						}
					})
		    	}
				
				if(token != null && token != "null" && $.trim(token)!="" && token != undefined){
					var tokenData = getTokenData(token);
					if(tokenData.code == -1){
						$("#token_nofound").show();
					}else if(!tokenData.result.isValid){
						$("#token_invalid").show();
					}else{
						var userData = getUserData(tokenData.result.owner);
						if(userData != null && userData != "null"){
							$("#pass-form").show();
							$(".btn_1_2").show();
							$("#user_realName").text(userData.realName);
						}else{
							$("#user_nofound").show(); 
						}
						
					}
				}else {
					$("#token_null").show();
				}
			});
			
			function formSubmit(){
				var newPwd = $("#newPwd").val().trim();
				var rePwd = $("#rePwd").val().trim();
				var notice_img ='<img class="promptA" src="image/login/prompt1.png"/>';
				if(newPwd ==""){
					$("#info-newPwd").html(notice_img+"请输入密码!").css({'color':'red'});
					return false;
				}else if (rePwd=="") {
					$("#info-rePwd").html(notice_img+"请重新输入密码!").css({'color':'red'});
					return false;
				}else if (newPwd !=rePwd) {
					$("#info-rePwd").html(notice_img+"新密码和确认密码不匹配!").css({'color':'red'});
					return false;
				} else {
					changePwd(newPwd, '<%=$ctx%>');
			    }
			}
			function openLogin() {
				window.location.href = '<%=$ctx%>'+"/login.jsp";
			}
			
			function validate(field){
		   		var value = field.value;
		   		var id = field.id;
		   		var notice_img ='<img class="promptA" src="../../image/login/prompt1.png"/>';
		   		if(id =='newPwd'){
		   			if(value ==''){
		   				$("#info-"+id).html(notice_img+"密码不能为空!").css({'color':'red'});
		   				$(id).removeClass('kv_ture');
		   			}else{
		   				$("#info-"+id).html("");
		   				$(id).addClass('kv_ture');
		   			}
		   		}
		   		if(id =='rePwd'){
		   			if(value ==''){
		   				$("#info-"+id).html(notice_img+"确认密码不能为空!").css({'color':'red'});
		   				$(id).removeClass('kv_ture');
		   			}else{
		   				$("#info-"+id).html("");
		   				$(id).addClass('kv_ture');
		   			}
		   		}
		   	}
			
		</script>
	</head>

	<body>
		<div class="box">
			<div class="mian">
				<div class="tab_tit">
					<div class="tab_tit_1 tab_reg">找回密码</div>
				</div>
				<div class="step">
					<div class="step_img">
					<img id="step-finish2" src="image/login/step_2.png"/>
					<img class="hide" id="step-finish3" src="image/login/step_3.png"/>
					</div>
					<div class="step_title">
					<span class="step_wz step_wz1">身份认证</span>
					<span class="step_wz step_wz2 step_wz22">重置密码</span>
					<span class="step_wz step_wz3">完成</span>
					</div>
				</div>
				<div class="main_login">
					
					<h2 id="token_nofound" class="hide" style="text-align: center;">错误，未找到令牌！</h2>
					
					<h2 id="token_invalid" class="hide" style="text-align: center;">错误，令牌已失效！</h2>
					
					<h2 id="user_nofound" class="hide" style="text-align: center;">系统错误，未找到用户！</h2>
					
					<h3 id="token_null" class="hide" style="text-align: center;">错误，缺少令牌参数！</h3>
					
					<form id="pass-form" action="#" method="post" class="hide">
						<table width="800" class="reg_tab" border="0" cellspacing="0">
							<tr>
								<td width="60" align="right">姓名：</td>
								<td width="280" id="user_realName"></td>
							</tr>
							<tr>
								<td width="60" align="right">密码：</td>
								<td width="280"><input class="kv_inp" type="password"
									name="newPwd" id="newPwd" class="validate[required,minSize[6]]"
									maxlength="20" onblur="validate(this)";/><span id='info-newPwd'></span></td>
							</tr>
							<tr>
								<td width="60" align="right">确认密码：</td>
								<td width="280"><input type="password" class="kv_inp" name="rePwd"
									id="rePwd" class="validate[required,minSize[6]]" maxlength="20" onblur="validate(this)" /><span id='info-rePwd'></span>
									</td>
							</tr>
						</table>
					</form>
					<div class="btn_1_2 hide">
						<input type="image" src="image/login/next1.png" onclick="return formSubmit();" />
					</div>
					<div class="hide finish-detal">
						<div class="finish-ctn">
							<img src="image/login/true2.png"/>
							<span class="finish-title">新密码设置成功请用新密码登录</span>
						</div>
						<div class="finish-btn1">
							<input type="image" src="image/login/login_ower.png" onclick="openLogin()" />
						</div>
					</div>
					<div class="f-l" style="padding-top: 40px; padding-left: 20px;"></div>
					<div class="c-b"></div>
				</div>
				<div class="footer_box">
					<iframe src="portal/Register/footer.html" width="800 " height="60" frameborder="0" scrolling="no"></iframe>
				</div>
			</div>
		</div>
	</body>
</html>