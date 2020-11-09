<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/include/page.jspf"%>
<!DOCTYPE html>
<html>
<head>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>portal管理系统</title>
        <script type="text/javascript" src="<%=$ctx%>/include/service.js"></script>
        <script src="<%=$commonRoot %>/jquery/jquery-1.6.4.js"></script>
        <script type="text/javascript" src="<%=$ctx%>/js/jquerySession.js"></script>
        <!--网页初始化-->
        <style>
            html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
                margin: 0;
                padding: 0;
                border: 0;
                font-size: 100%;
                font: inherit;
                vertical-align: baseline;
            }

            @font-face {
                font-family: 'MyNewFont';   /*字体名称*/
                src: url('../../css/Haninstrumentclassical.TTF');       /*字体源文件*/
            }

            article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {
                display: block;
            }

            body {
                line-height: 1;
            }

            ol, ul {
                list-style: none;
            }

            blockquote, q {
                quotes: none;
            }

            blockquote:before, blockquote:after, q:before, q:after {
                content: '';
                content: none;
            }

            table {
                border-collapse: collapse;
                border-spacing: 0;
            }
        </style>
        <!--逻辑样式-->
        <style>
            .container {
                text-align: center;
                margin-left: auto;
                margin-right: auto;
                width: 100%;
                height: 80%;
                
            }

            .header {
                padding-top: 5%;
                padding-left: 10%;
            }

            .header_solgin {
                padding-top: 1%;
                padding-left: 10%;
            }

            .section {
                padding-top: 10%;
                width: 100%;
                height: 80%;
            }

            .footer {
                padding-top: 8%;
                width: 100%;
                display: inline-block;
            }

            .footer_copyright {
                padding-top: 2%;
                padding-bottom: 2%;
                width: 100%;
                display: inline-block;
            }
        </style>
    </head>
    <script type="text/javascript">
        /*原生js ajax*/
        function createXHR() {
            if (typeof XMLHttpRequest != "undefined") { // 非IE6浏览器
                return new XMLHttpRequest();
            } else if (typeof ActiveXObject != "undefined") {   // IE6浏览器
                var version = [
                    "MSXML2.XMLHttp.6.0",
                    "MSXML2.XMLHttp.3.0",
                    "MSXML2.XMLHttp",
                ];
                for (var i = 0; i < version.length; i++) {
                    try {
                        return new ActiveXObject(version[i]);
                    } catch (e) {
                        //跳过
                    }
                }
            } else {
                throw new Error("您的系统或浏览器不支持XHR对象！");
            }
        }
        // 转义字符
        function params(data) {
            var arr = [];
            for (var i in data) {
                arr.push(encodeURIComponent(i) + "=" + encodeURIComponent(data[i]));
            }
            return arr.join("&");
        }
        // 封装ajax
        function ga_ajax(obj) {
            var xhr = createXHR();
            obj.url = obj.url
//            obj.url = obj.url + "?rand=" + Math.random(); // 不使用缓冲信息
            obj.data = params(obj.data);      // 转义字符串
            if (obj.method === "get") {      // 判断使用的是否是get方式发送
                obj.url += obj.url.indexOf("?") == "-1" ? "?" + obj.data : "&" + obj.data;
            }
            // 异步
            if (obj.async === true) {
                // 异步的时候需要触发onreadystatechange事件
                xhr.onreadystatechange = function () {
                    // 执行完成
                    if (xhr.readyState == 4) {
                        callBack();
                    }
                }
            }
            xhr.open(obj.method, obj.url, obj.async);  // false是同步 true是异步 // "demo.php?rand="+Math.random()+"&name=ga&ga",
            if (obj.method === "post") {
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send(obj.data);
            } else {
                xhr.send(null);
            }
            // xhr.abort(); // 取消异步请求
            // 同步
            if (obj.async === false) {
                callBack();
            }
            // 返回数据
            function callBack() {
                // 判断是否返回正确
                if (xhr.status == 200) {
                    obj.success(xhr.responseText);
                } else {
                    obj.Error("获取数据失败，错误代号为：" + xhr.status + "错误信息为：" + xhr.statusText);
                }
            }
        }
        /*ajax-END*/
        /*自动写入本地保存的用户名*/
        window.onload = function () {
            var account = getCookie("account");
            document.getElementById('account').value = account;
        }
        function resetInfo() {
            document.getElementById("account").value = "";
            document.getElementById("pwd").value = "";
            document.getElementById("code").value = "";
        }
        function goRefurbish(codeurl) {
            var imgCode = document.getElementById("imgCode");
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
            var bool = false;
            var code = document.getElementById("code");
            var account = document.getElementById("account");
            var pwd = document.getElementById("pwd");
            var _code = getCookie("code");

            if (account.value == "") {
                alert("请输入用户帐号!");
            } else if (pwd.value == "") {
                alert("请输入密码!");

            } else if (code.value == "") {
                alert("请输入验证码!");
            } else if (code.value != _code) {
                alert("请输入正确的验证码!");
                bool = false;
            } else {
                bool = true;
            }
            if (bool) {
                ga_ajax({
                    "method": "post",
                    "url": $service.portal + "/us.User/collection/login",
                    "data": {"loginId": account.value, "password": pwd.value},
                    "success": function (r) {
                        r = eval("(" + r + ")")
                        if (r.code == 1) {
                            document.cookie = "account=" + escape(account.value);
                            $.session.set('userId', r.result.id);
                            $.session.set("realName", r.result.realName);
                            // window.location.href = document.getElementById('indexurl').value;
                            window.location.href = "<%=request.getContextPath() %>/index.jsp";
                        } else {
                            alert("登陆失败！ " + r.description);
                        }
                    },
                    "Error": function (text) {
                        alert(text);
                    },
                    "async": false
                });
            }
        }
    </script>
<body style="background-image: url('image/login/back_img.png');background-size:100%; background-repeat: no-repeat;">
<div class="container" id="tem1_container">
    <div align="center" id="top">
        <div class="header" align="left"><img src="image/login/logo.png"></div>
        <div class="header_solgin" align="left" style="font-family: 微软雅黑">
            <span>数据驱动的医疗和保险平台</span></div>
    </div>
    <div align="center" class="section" nowarp>
        <table>

            <tr>
                <td colspan="3">
                    <div style="display: inline-block;width:50%;float: left;padding-top: 5%"><img
                            src="image/login/back_black.png"></div>
                </td>

                <td>
                    <div style="display: inline-block;float: left;padding-left: 15%;position: relative">
                        <table>
                            <tr>
                                <td colspan="3" width="270" height="20" valign="top" align="left"><span
                                        style="font-weight:bold;">一生佳运营平台</span></td>
                            </tr>
                            <tr>
                                <td colspan="3" height="20" width="270"></td>
                            </tr>
                            <tr>
                                <td colspan="3" height="20" width="270" valign="bottom" align="right"><a
                                        style="text-decoration: none;font-size: 15px"
                                        href="<%=request.getContextPath()%>/portal/Register/register.jsp">立即注册</a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" height="5" width="270"></td>
                            </tr>
                            <tr>
                                <td colspan="3" height="35" width="270" align="left"><input id="account"
                                                                                            placeholder="姓名/邮箱/手机号/身份证号"
                                                                                            style="width: 100%;height: 100%;"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" height="20" width="270"></td>
                            </tr>
                            <tr>
                                <td colspan="3" height="35" width="270" align="left"><input id="pwd" placeholder="密码"
                                                                                            type="password"
                                                                                            style="width: 100%;height: 100%"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" height="20" width="270"></td>
                            </tr>
                            <tr>
                                <td colspan="2" height="35" width="170" align="left"><input id="code" placeholder="验证码"
                                                                                            type="text"
                                                                                            style="width: 100%;height: 100%"/>
                                </td>
                                <td height="35" width="100" align="right"><span><img id="imgCode"
                                                                                     src="<%=request.getContextPath()%>/code.jsp"/></span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" height="10" width="270"></td>
                            </tr>
                            <tr>
                                <td colspan="2" height="35" width="170" align="left"><a
                                        style="font-size: 10px;color: #00CCFF;text-decoration: none"
                                        href="<%=request.getContextPath()%>/portal/Register/getPassword.jsp">忘记密码</a>
                                </td>
                                <td height="35" width="100" align="right"><a
                                        style="font-size: 10px;color: #00CCFF;text-decoration: none"
                                        href="javascript:goRefurbish('<%=request.getContextPath()%>/code.jsp')">看不清楚</a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" height="30" width="270">
                                    <img
                                            src="image/login/btn_login.png" width="100%" height="100%"
                                            style="margin: 0;cursor: pointer"
                                            onclick="doLogin()"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>

            </tr>
        </table>
    </div>
    <div class="footer"><span>北京一生佳科技发展有限公司</span></div>
    <div class="footer_copyright"><span>Copyright©2014 All Rights Reserved</span></div>
</div>
<!--隐藏域-->
<input type="hidden" id="indexurl" name="indexurl" value="http://admin.yishengjia1.com/portal"><!--首页url-->
</body>
</html>
