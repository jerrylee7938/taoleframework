<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="<%=$ctx%>/include/env.js.jsp"></script>
		<script type="text/javascript">
			if($isCasLogin){
				window.location.href = "logout_cas.jsp";
			}else {
				window.location.href = "logout.jsp";
			}
		</script>
	</head>
	<body>
	</body>
</html>