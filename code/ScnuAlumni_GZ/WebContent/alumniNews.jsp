<%@page import="com.newttl.scnualumni_gz.weixin.MenuManager"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>校友风采</title>
</head>

<body>
<%
	String alumni=(String)request.getParameter("alumni");
%>

<p><%=alumni %></p>
</body>
</html>