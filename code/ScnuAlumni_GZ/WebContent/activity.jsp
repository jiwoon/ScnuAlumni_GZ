<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.newttl.scnualumni_gz.bean.pojo.SNSUserInfo"%>
<!DOCTYPE html>
<html>
<head>
<title>校友最新活动</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">

<link rel="stylesheet" href="resources/css/weui.min.css">
<link rel="stylesheet" href="resources/css/jquery-weui.css">
<link rel="stylesheet" href="resources/css/demos.css">

</head>
<body ontouchstart>
	<%
		// 获取由OAuthServlet中传入的参数
		SNSUserInfo user = (SNSUserInfo) request.getAttribute("snsUserInfo");
		if (null != user) {
			session.setAttribute("openid", user.getOpenId());
			session.setAttribute("nickname", user.getNickName());
		}
	%>
	<jsp:include page="recent_activity.jsp" />
</body>
</html>
