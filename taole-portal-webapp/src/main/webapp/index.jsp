<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="include/header.jsp"></jsp:include>

	<script type="text/javascript" src="<%=$ctx %>/portal/viewport.js"></script>
	<script type="text/javascript" src="<%=$ctx%>/js/interfaces.js"></script>
	<script type="text/javascript">
		function fMain(id, param, address){
			commonOpenMenuParameterForAddress(id, param, address);
	    }
		function delTab(id){
			parent.removeTab(id);
		}
	</script>



<style type="text/css">
		.c-color-red{
			color: red !important;
		}
		.c-color-blue{
			background:blue;
		}
	table{width:100% !important;}
	.admin {
		display: none;
		float: left;
		position: relative;
		padding: 5px;
		width: 235px;
		-moz-box-shadow: inset 0 0 5px #CCC;
		-webkit-box-shadow: inset 0 0 5px #CCC;
		box-shadow: inset 0 0 5px #CCC;
	}
	.admin .image {
float: left;
width: 62px;
margin-right: 5px;
}
.img-polaroid {
padding: 4px;
background-color: #fff;
border: 1px solid #ccc;
border: 1px solid rgba(0, 0, 0, 0.2);
-webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
-moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

[class^="icon-"],
[class*=" icon-"] {
  display: inline-block;
  width: 14px;
  height: 14px;
  margin-top: 1px;
  *margin-right: .3em;
  line-height: 14px;
  vertical-align: text-top;
  background-image: url("image/glyphicons-halflings.png");
  background-position: 14px 14px;
  background-repeat: no-repeat;
}
.admin ul.control, .headInfo ul.control {
display: block;
float: left;
width: 142px;
list-style: none;
margin: 0px;
}
li {
line-height: 20px;
margin-top:1px;
}
.icon-comment {
background-position: -240px -120px;
}
.admin ul.control li a, .headInfo ul.control li a {
font-size: 11px;
color: #333;
-moz-text-shadow: 0 1px 0 #FFFFFF;
-webkit-text-shadow: 0 1px 0 #FFFFFF;
text-shadow: 0 1px 0 #FFFFFF;
}
.admin ul.control li .caption {
display: block;
float: right;
font-size: 10px;
background-color: #333;
color: #fff;
padding: 2px 4px 2px 3px;
line-height: 13px;
margin: 3px 0px 3px 5px;
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
border-radius: 3px;
text-decoration: none;
text-shadow: none;
}
ul, ol {
padding: 0;
margin: 0 0 0px 25px;
}
.icon-user{
background-position: -168px 0;
}
.icon-cog {
background-position: -432px 0;
}
.icon-share-alt {
background-position: -336px -96px;
}
.admin .info {
float: left;
padding: 5px;
font-size: 10px;
color: #666;
line-height: 14px;
-moz-text-shadow: 0 1px 0 #FFFFFF;
-webkit-text-shadow: 0 1px 0 #FFFFFF;
text-shadow: 0 1px 0 #FFFFFF;
}
</style>
</head>
<body>
</body>
</html>