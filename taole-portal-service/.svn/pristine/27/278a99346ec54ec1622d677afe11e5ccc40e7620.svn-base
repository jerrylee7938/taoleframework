<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String $ctx = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>#p_template_main_title</title>
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
            width: 100%;
            display: inline-block;
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jumpUrl.js"></script>
    <script type="text/javascript">

        function jump(jumpType) {
            window.location.href = jumpConfig[jumpType];
        }

        function resetInfo() {
            document.getElementById("username").value = "";
            document.getElementById("password").value = "";
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
            var code = document.getElementById("code");
            var account = document.getElementById("username");
            var pwd = document.getElementById("password");
            var _code = getCookie("code");
            var bool = false;
            var msg = "";
            if (account.value == "") {
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
            if (bool) {
                document.getElementById("submit").click();
            } else {
                document.getElementById("clinetMsg").innerHTML = msg;
                /*      alert(msg)*/
                /* document.getElementById("msg1").innerHTML=msg;*/
            }
        }
    </script>
</head>


<body style="background-image: url('<%=request.getContextPath()%>/#p_template_back_main')">
<div class="container" id="tem1_container">
    <div align="center" id="top">
        <div class="header" align="left"><img src="<%=request.getContextPath()%>/#p_template_back_logo"></div>
        <div class="header_solgin" align="left" style="font-family: 微软雅黑">
            <span>#p_template_text_slogan</span></div>
    </div>
    <div align="center" class="section" nowarp>
        <table>
            <tr>
                <td colspan="3">
                    <div style="display: inline-block;width:50%;float: left;padding-top: 5%"><img
                            src="<%=request.getContextPath()%>/#p_template_back_mini"></div>
                </td>

                <td>
                    <form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}"
                               htmlEscape="true">
                        <div style="display: inline-block;float: left;padding-left: 15%;position: relative">
                            <table>
                                <tr>
                                    <td colspan="3" width="270" height="20" valign="top" align="left"><span
                                            style="font-weight:bold;">#p_template_text_name</span></td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="20" width="270"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <div style=""><form:errors path="*" id="msg" element="div"
                                                                   cssStyle="color: red;"/><p style="color: red"
                                                                                              id="clinetMsg"></p></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="20" width="270"></td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="20" width="270" valign="bottom" align="right"><a
                                            style="text-decoration: none;font-size: 15px"
                                            href="#p_template_url_register">立即注册</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="5" width="270"></td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="35" width="270" align="left"><form:input
                                            cssStyle="width: 100%;height: 100%" placeholder="请输入邮箱/工号/手机号/身份证"
                                            id="username" tabindex="1" accesskey="${userNameAccessKey}" path="username"
                                            autocomplete="false" htmlEscape="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="20" width="270"></td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="35" width="270" align="left"><form:password
                                            cssStyle="width: 100%;height: 100%" placeholder="请输入密码" id="password"
                                            tabindex="2" path="password" accesskey="${passwordAccessKey}"
                                            htmlEscape="true" autocomplete="off"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="20" width="270"></td>
                                </tr>
                                <tr>
                                    <td colspan="2" height="35" width="170" align="left"><input id="code" name="code"
                                                                                                placeholder="验证码"
                                                                                                type="text"
                                                                                                style="width: 100%;height: 100%"/>
                                    </td>
                                    <td height="35" width="100" align="right"><span><img id="imgCode"
                                                                                         onClick="goRefurbish('<%=request.getContextPath()%>/code.jsp');"
                                                                                         src="<%=request.getContextPath()%>/code.jsp"/></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="10" width="270"></td>
                                </tr>
                                <tr>
                                    <td colspan="2" height="35" width="170" align="left"><a
                                            style="font-size: 10px;color: #00CCFF;text-decoration: none"
                                            href="#p_template_url_findpsd">忘记密码</a>
                                    </td>
                                    <td height="35" width="100" align="right"><a
                                            style="font-size: 10px;color: #00CCFF;text-decoration: none"
                                            href="javascript:goRefurbish('<%=request.getContextPath()%>/code.jsp')">看不清楚</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" height="30" width="270">
                                        <img
                                                src="<%=request.getContextPath()%>/#p_template_back_login" width="100%"
                                                height="100%"
                                                style="margin: 0;cursor: pointer"
                                                onclick="doLogin()"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div style="display: none;">
                            <input type="hidden" name="lt" value="${loginTicket}"/>
                            <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                            <input type="hidden" name="_eventId" value="submit"/>
                            <input name="submit" id="submit" style="display:none" tabindex="4" type="submit"/>
                                <%--    <input  name="reset" accesskey="c" class="btn2" value="重 置" tabindex="5" type="reset" />--%>
                        </div>
                    </form:form>
                </td>

            </tr>
        </table>
    </div>
    <div class="footer"><span>#p_template_text_bottpm_des</span></div>
    <div class="footer_copyright"><span>#p_template_text_bottpm_copyright</span></div>
</div>
<!--隐藏域-->
<input type="hidden" id="indexurl" name="indexurl" value="#p_template_url_index"><!--首页url-->
</body>
</html>
