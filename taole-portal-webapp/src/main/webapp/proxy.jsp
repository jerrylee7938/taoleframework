<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%
	  	String id = request.getParameter("id");
	    String param = request.getParameter("param");
	    String address = request.getParameter("address");
	%> 
	<html>
		 <head>
		  <meta http-equiv="content-type" content="text/html; charset=utf-8">
		 </head>
		
		 <body>
		 	<script type="text/javascript">
		 		var id = '<%=id%>';
		 		var param = '<%=param%>';
		 		var address = '<%=address%>';
		 		if(param == "referrer=removeTab"){
		 			parent.parent.parent.delTab(id);
		 		}else{
		 			parent.parent.parent.addTab(id, param, address);
		 		}
				
			</script>
		 </body>
	</html>
