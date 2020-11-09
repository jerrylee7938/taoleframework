<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.jasig.cas.client.util.AbstractCasFilter"%>
<%@ page import="org.jasig.cas.client.validation.Assertion"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal"%>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

	String ssoid = null;
	if (assertion != null) {
		ssoid = assertion.getPrincipal().getName();
	}
	
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
	Map attributes = principal.getAttributes();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>

</head>
<body>
SSOID(account):<%=ssoid%><br>
usercode:<%=attributes.get("usercode")%><br>
nickname:<%=attributes.get("nickname")%><br>

form session:
account:<%=session.getAttribute("account")%><br>
usercode:<%=session.getAttribute("usercode")%><br>
nickname:<%=session.getAttribute("nickname")%><br>
realname:<%=session.getAttribute("realname")%><br>

<br>
SERVER INFO<br>
path:<%=path%><br>
basePath:<%=basePath%><br>
</body>
</html>
