<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.newttl.scnualumni_gz.bean.database.Activity"%>
<%@ page import="com.newttl.scnualumni_gz.util.DataBaseUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<!-- 校友活动添加到活动表中 -->
<body>
	<%
		String openid = (String) session.getAttribute("openid");
		String awho = (String) session.getAttribute("nickname");
		//防止中文乱码
		String aname = new String((request.getParameter("aname")).getBytes("ISO-8859-1"), "UTF-8");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String address = new String((request.getParameter("address")).getBytes("ISO-8859-1"), "UTF-8");
		String atip = new String((request.getParameter("atip")).getBytes("ISO-8859-1"), "UTF-8");

		Activity activity = new Activity();
		activity.setActivityName(aname);
		activity.setOpenID(openid);
		activity.setStartTime(start_time);
		activity.setEndTime(end_time);
		activity.setActivityHolder(awho);
		activity.setActivityIntro(atip);
		activity.setActivityAddress(address);

		DataBaseUtil dataBaseUtil = new DataBaseUtil();
		dataBaseUtil.saveActivity(activity);
	%>
	<jsp:include page="recent_activity.jsp" />
</html>