<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@include file="/include/page.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>北京淘乐未来</title>
  <style type="text/css">
  
  	*{
	  	padding: 0;
	  	margin: 0;
	  	box-sizing: border-box;
  	}
	.top_log_wap{
	    height:140px;
	    background: #ffffff;
	}
	.top_log{
		width:1200px;
		margin:auto;
	 	line-height: 140px;
	    height:140px;
	    vertical-align: middle;
	}
	.web-img{
		height:100%;
		display: inline-block;
	}	
	.img-code{
		width: 120px;
		height: 50px;
	}
	img{
		vertical-align: middle;
	}
	.web-title{
		float:right;
		font-size: 18px;
		font-weight:bold;
		width: 400px;
		text-align: center;
	}
	.main_cn_wap{
		height:600px;
	    overflow: hidden;
	    font-size: 14px;
	}
	.main_ctn{
		width:1200px;
		height:600px;
		margin: auto;
		overflow: hidden;
	}
	.left_cn{
	    float: left;
	    line-height: 600px;
	    height: 600px;
	}
	.right_cn{
	    float: right;
	    width: 400px;
	    height: 435px;
	    background-color: #FFFFFF;
	    padding: 0 39px;
	    margin-top: 80px;
	}
	.input{
	    width: 320px;
	    height: 50px;
	    padding-left: 48px;
	    border-radius: 4px;
	    color: #a9a9a9;
	    border: 1px solid #D4D4D4;
	}
	.code,.message{
	    width: 180px;
	    height: 50px;
	    border-radius: 4px;
	    padding-left: 48px;
	    background-color: #fafafa;
	    border: 1px solid #D4D4D4;
	    color: #a9a9a9;
	}
	
	.use-title{
	    font-size: 16px;
	    font-weight: 600;
	}
	.register{
	    float: right;
	    color: #5EC5F7;
	}
	.use-name,.use-pass,.use-code,.use-wjmm,.use-btn{
	    padding: 10px 0px;
	}
	.use-name input{
	    background:url("images/useName.png") no-repeat 8px 9px;
	    background-color: #fafafa;
	}
	.use-pass input{
	    background:url("images/pass.png") no-repeat 8px 9px;
	    background-color: #fafafa;
	}
	.use-code input{
	    background:url("images/yzm.png") no-repeat 8px 9px;
	    background-color: #fafafa;
	}
	.btn-login{
	    height: 50px;
	    width: 320px;
	    border: none;
	    border-radius: 6px;
	}
	.jzzh{
	    padding-left: 20px;
	    background:url("image/jzmm.png") no-repeat 0px 1px ;
	    color: #757575;
	}
	.jzzh.active{
	    background:url("image/active.png") no-repeat 0px 1px ;
	}
	.wj-pass{
	    float: right;
	    color: #E5985D;
	}
	.footer{
	    height: 60px;
	    line-height: 60px;
	    text-align: center;
	    font-size: 14px;
	}
	.codeImg{
	    float: right;
	    width: 120px;
	    height: 50px;
	    border: 1px solid #000000;
	}
	#mycanvas{
	    /*width: 118px;*/
	    /*height: 48px;*/
	    padding: 0px;
	    margin: 0px;
	}
	.prompt{
	    padding-top: 10px;
	    color: red;
	    font-size: 16px;
	    font-weight: bold;
	}
	.login_choose{
		display: flex;
      flex-direction: row;
      line-height:60px;
	}
	.choose_pass,.choose_tel{
		  flex: 1;
		 text-align: center;
		 font-size:17px; 
		 font-weight: bold;
		 border-bottom: 1px solid #DDDDDD;
		  cursor:pointer; 
	}
	.login_choose .active{
		color: red;
		border-bottom: 1px solid red;
	}
	.telephone_login{
		display: none;
	}
	.getPsaaword{
		display: inline-block;
		width: 120px;
		font-size: 14px;
		text-align: center;
		line-height: 49px;
		border: none;
		border-radius: 4px;
	}
	
  </style>
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jumpUrl.js"></script>
  <script src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>
  <script type="text/javascript">
  		$(function() {
  			var loginType = '';
  	  		if(loginType == 'MOBILE_CODE_VALIDATE'){ //密码登录
  	  			$('.choose_pass').removeClass('active');
  	  			$('.choose_pass').attr( "data-num" ,'1' );
  	  			$('.choose_tel').hide();
  	  			$('.password_login').show();
	  			$('.telephone_login').hide();
	  	  		$('.password_login input').attr('disabled',false);
				$('.telephone_login input').attr('disabled',true);
				$('.wj-pass').show();
				$('#set_flag').val('1');
  	  		}else if (loginType =='MOBILE_CODE_VALIDATE') {//短信登录
  	  			$('.choose_pass').hide();
  	  			$('.choose_tel').attr( "data-num" ,'1' );
  	  			$('.password_login').hide();
  	  			$('.telephone_login').show();
	  	  		$('.password_login input').attr('disabled',true);
				$('.telephone_login input').attr('disabled',false);
				$('.wj-pass').hide();
				$('#set_flag').val('2');
  			}
		})
  
	  function jump(jumpType) {
	      window.location.href = jumpConfig[jumpType];
	  }
	  function resetInfo() {
	      document.getElementById("username").value = "";
	      document.getElementById("password").value = "";
	      document.getElementById("code").value = "";
	  }
	  function goRefurbish(codeurl,_this) {
	      var imgCode = document.getElementById(_this);
	      imgCode.src = codeurl + "?d=" + new Date();
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
	  function doLogin() {
		var dataValue =  $('.login_choose>.active').data('choose');
	  //    var code = document.getElementByClassName("code");
	  //    var account = document.getElementById("name");
	  //    var pwd = document.getElementById("password");
	   
	  var account = $('.'+dataValue+'_login .name').val();
	  var pwd = $('.'+dataValue+'_login .password').val();
	  var code = $('.'+dataValue+'_login .code').val();
	  var _code = getCookie("code");
	  var bool = false;
	  var msg = "";
 		if(dataValue == 'password'){
 			 if (account == "") {
 		          msg = "请输入用户帐号!";
 		      } else if (pwd == "") {
 		          msg = "请输入密码!";
 		      } else if (code == "") {
 		          msg = "请输入验证码";
 		      } else if (code != _code) {
 		          msg = "请输入正确的验证码!";
 		      } else {
 		          bool = true;
 		      }
		  }else if (dataValue == 'telephone') {
			  if (account == "") {
 		          msg = "请输入手机号!";
 		      } else if (pwd == "") {
 		          msg = "请输入短信验证码!";
 		      } else if (code == "") {
 		          msg = "请输入验证码";
 		      } else if (code != _code) {
 		          msg = "请输入正确的验证码!";
 		      } else {
 		          bool = true;
 		      }
		}
	      
	 /*     if (account.value == "") {
	          msg = "请输入用户帐号!";
	      } else if (pwd.value == "") {
	          msg = "请输入密码!";
	      } else if (code.value == "") {
	          msg = "请输入验证码";
	      } else if (code.value != _code) {
	          msg = "请输入正确的验证码!";
	      } else {
	          bool = true;
	      }
	 */
	      if (bool) {
	          document.getElementById("submit").click();
	      } else {
	          document.getElementById("clinetMsg").innerHTML = msg;
	          /*      alert(msg)*/
	          /* document.getElementById("msg1").innerHTML=msg;*/
	      }
	  }
	  
	  $(function(){
		  $('.chosse_type').click(function(){
			  var dataNum=$(this).data('num');
			  if(dataNum == 1)return;
			  document.getElementById("clinetMsg").innerHTML = '';
			  var dataValue=$(this).data('choose');
			  $(this).addClass('active').siblings().removeClass('active');
			  $('.'+dataValue+'_login').show().siblings().hide();
			  if(dataValue =='password'){
				 $('.password_login input').attr('disabled',false);
				 $('.telephone_login input').attr('disabled',true);
				 $('.wj-pass').show();
				 $('#set_flag').val('1');
			 }else if(dataValue =='telephone'){
				 $('.password_login input').attr('disabled',true);
				 $('.telephone_login input').attr('disabled',false);
				 $('.wj-pass').hide();
				 $('#set_flag').val('2');
			 }
		  })
		  
	  $('.getPsaaword').click(function(){
			  	var _this=this;
		    	var timeNum = 60;
	            var time =null;
	            var iphone =$('.telephone_login .name').val();
	            var myreg=/^[1][0-9]{10}$/;
	            if(!myreg.test(iphone)){
	            	 document.getElementById("clinetMsg").innerHTML = '请输入正确的手机号！';
	            	return;
	            }else{document.getElementById("clinetMsg").innerHTML = '';}
	            $(this).attr({"disabled":"disabled"});
	            time=window.setInterval(function(){
	                timeNum--;
	                $(_this).text('获验证码('+timeNum+'s)');
	                if(timeNum<=0){
	                    $(_this).text('获验短信证码');
	                    $(_this).removeAttr("disabled");
	                    window.clearInterval(time);
	                }
	            },1000);
	            $.ajax({
	                type:'post',
	                url: _url+'acct.Account/collection/getSmsPwd',
	                data:{account:iphone,verifyType:1},
	                headers:{Authorization:'Bearer '+token},
	                success:function (data) {
	                }
	            })
		  })	  
		  
	  })
	  
	  
  </script>
</head>
<body>

<div class="contain" style="background-image: url('<%=request.getContextPath()%>/images/')">
    <div class="top_log_wap">
	    <div class="top_log">
	   		<span class="web-img"><img src="<%=request.getContextPath()%>/images/LOGO.png" alt=""></span>
	     	<span class="web-title"></span>
	    </div>   
    </div>
    <div class="main_cn_wap" style="background-image: url('<%=request.getContextPath()%>/images/beijingtu2.png')">
    <div class="main_ctn">
      <div class="left_cn">
            <img src="<%=request.getContextPath()%>/images/beijingzhutu2.png" alt="">
        </div>
        <form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
	        <div class="right_cn">
	            <div class="title-dl">
		            <div class="login_choose">
		            	<span id='choose_pass' class="choose_pass chosse_type active" data-num='2' data-choose='password'>密码登录</span>
		            	<span id='choose_tel' class="choose_tel chosse_type" data-num='2' data-choose='telephone'>短信登录</span>	
		            </div>
	                <div class="prompt">
	                	<form:errors path="*" id="msg" element="div" cssStyle="color: red;"/>
	                	<p id="clinetMsg"></p>
                    </div>
	                
	            </div>
	            <div>
	            	<div class="password_login">
	               <div class="use-name">
	            	<input type="text" class="input name" placeholder="请输入登录账号" id="username" 
	            	     tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true"/>
	          		 </div>
			          <div class="use-pass">
			            <input type="password" class="input password" placeholder="请输入登录密码" id="password" tabindex="2" 
			            	    path="password" accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off"/>
			            </div>
			            <div class="use-code">
			                <input id="code" class="code" type="text" placeholder="请输入验证密码">
			                <span><a data-code='imgCode' href="javascript:goRefurbish('<%=request.getContextPath()%>/code.jsp','imgCode')"><img class="img-code" id="imgCode" src="<%=request.getContextPath()%>/code.jsp"/></a></span>
			            </div>
	          	  
	            </div>
	            <div class="telephone_login">
	               <div class="use-name">
	            	<input type="text" class="input name" placeholder="请输入手机号" id="username_tel" 
	            	     tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" disabled="disabled"/>
	          		 </div>
			          <div class="use-pass">
			            <input type="text" class="input password message" placeholder="请输入短信密码" id="password_tel" tabindex="2"  disabled="disabled"
			            	    path="password" accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off"/>
			            	    <button class="getPsaaword">获取短信验证码</button>
			            </div>
			            <div class="use-code">
			                <input id="code_tel" class="code" type="text" placeholder="请输入验证密码" disabled="disabled">
			                <span><a data-code='imgCode_tel' href="javascript:goRefurbish('<%=request.getContextPath()%>/code.jsp','imgCode_tel')"><img class="img-code" id="imgCode_tel" src="<%=request.getContextPath()%>/code.jsp"/></a></span>
			            </div>
	            </div>
	            </div>
	            <div class="use-wjmm">
	                <span class="active"><a href="http://portal.51taole.cn:80/portal/portal/Register/register.jsp">立即注册</a></span>
	                <span class="wj-pass"><a href="http://portal.51taole.cn:80/portal/portal/Register/getPassword.jsp">忘记密码?</a></span>
	            </div>
	            <div class="use-btn">
	                <span class="btn-login">
	                 <img src="<%=request.getContextPath()%>/images/c372c01f596f40fba3b0481eaff990e1.png" onclick="doLogin()" style="width: 100%;height: 100%;cursor: pointer">
	                </span>
	            </div>
	        </div>
	        <div style="display: none;">
                <input type="hidden" name="lt" value="${loginTicket}"/>
                <input type="hidden" id='set_flag' name="flag" value="1"/>
                <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                <input type="hidden" name="_eventId" value="submit"/>
                <input name="submit" id="submit" style="display:none" tabindex="4" type="submit"/>
            </div>
        </form:form>
    </div>
      
    </div>
</div>
<div class="footer">
    <p>© 2015-2025 北京淘乐未来科技有限公司 版权所有</p>
</div>

</body>
</html>
