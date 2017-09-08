<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">

<title>ScnuAlumni</title>
</head>
<body>
<%
	String erro=(String)request.getAttribute("snsUserInfo");
	if(null != erro){
		out.println(erro);
	}
	
	out.print("This is my ScnuAlumni");
%>
</body>
</html>