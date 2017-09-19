<%@page import="com.newttl.scnualumni_gz.util.CommonUtil"%>
<%@page import="com.newttl.scnualumni_gz.bean.pojo.Token"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.newttl.scnualumni_gz.util.DataBaseUtil"%>
<%@page import="com.newttl.scnualumni_gz.bean.database.Activity"%>
<%@page import="java.util.List"%>
<%@page import="com.newttl.scnualumni_gz.util.AdvancedUtil"%>
<%@page import="com.newttl.scnualumni_gz.weixin.WeiXinCommon"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/js/jquery-2.1.4.js"></script>
<script src="resources/js/jquery-weui.js"></script>

</head>
<body>
	<script>
		$.alert("你的活动邀请海报已经生成，请回到公众号查看！")
	</script>
	<jsp:include page="recent_activity.jsp" />

<%
	    String openid =(String) session.getAttribute("openid");
	
		String aid = request.getParameter("aid");
		int  id = Integer.parseInt(aid);

		Activity activity = new Activity();
		DataBaseUtil baseUtil = new DataBaseUtil();
		activity = baseUtil.getTheActivity(id);
		
		String aname = activity.getActivityName();
		String start_time = activity.getStartTime();
		String end_time = activity.getEndTime();
		String address = activity.getActivityAddress();
		String atip = activity.getActivityIntro();
		String awho = activity.getActivityHolder();
		
		//客服信息内容
		String content = aname + "/" + start_time + " - " + end_time + "/" + address + "/" + atip + "/" + awho;
		
		//通过凭证 appID appsecret获取 access_token
		Token token=CommonUtil.getToken();
		
		// 发送客服文本消息
		AdvancedUtil customMessage = new AdvancedUtil();
		String jsonTextMsg = customMessage.getAdvancedMethod().makeTextCustomMessage(openid, "你的活动邀请海报生成啦！快转发到校友群通知大家参加吧[呲牙]");
		customMessage.getAdvancedMethod().sendCustomMessage(token.getAccess_token(), jsonTextMsg);

		// 发送客服图片消息
		String jsonImageMsg = customMessage.getAdvancedMethod()
				.makeImageCustomMessage(openid,customMessage.getAdvancedMethod().getActivityImgId(content,token.getAccess_token()));
		customMessage.getAdvancedMethod().sendCustomMessage(token.getAccess_token(), jsonImageMsg);
		
%>

</body>
</html>