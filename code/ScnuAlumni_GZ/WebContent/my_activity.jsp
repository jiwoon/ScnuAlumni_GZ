<%@page import="java.util.ArrayList"%>
<%@page import="com.newttl.scnualumni_gz.util.DataBaseUtil"%>
<%@page import="com.newttl.scnualumni_gz.bean.database.Activity"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<link rel="stylesheet" href="resources/css/demos.css">
<link rel="stylesheet" href="resources/css/weui.min.css">
<link rel="stylesheet" href="resources/css/jquery-weui.css">
<script src="resources/js/jquery-2.1.4.js"></script>
<script src="resources/js/jquery-weui.js"></script>

<title>校友近期活动</title>
<!-- 显示我发起过的活动 -->
</head>

<script>

	$(document).on("click", "#show-alert", function() {
	    $.alert("亲，请先到公众号菜单栏【个人中心】进行注册后，再发起活动！");
	  });
	
	function onCreatePoster(openID, actID) {
		$.showLoading("正在生成海报...");
		var posterData={'openId':openID,'activityId':actID};
		$.ajax({
			type:"POST",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			url:"/ScnuAlumni/SendPosterServlet",
			data:JSON.stringify(posterData),
			dataType:"json",
			success:function(data){
				$.hideLoading();
				$.alert("你的活动邀请海报已经生成，请回到公众号查看！");
			}
		});
	};
	
</script>

<body>
	<%!public static final int PAGESIZE = 3;
	int pageCount = 0;
	%>

	<%
		String openid = (String) session.getAttribute("openid");
		//判断用户是否已经注册
		DataBaseUtil baseUtil2 = new DataBaseUtil();
		boolean Signed = baseUtil2.isSigned(openid);
		
%>

<div class="weui-btn-area">
		<a href="recent_activity.jsp"
			class="weui-btn weui-btn_mini weui-btn_plain-primary">近期活动</a> 
			<% if(!Signed) {%>
			<a href="javascript:;" id='show-alert' class="weui-btn_mini weui-btn weui-btn_plain-primary"  >发起活动</a> 
		    <% } else { %>
			<a href="add_activity.jsp" class="weui-btn_mini weui-btn weui-btn_plain-primary">发起活动</a> 
			<%} %>
			<a href="my_activity.jsp" class="weui-btn_mini weui-btn weui-btn_primary"><h3>我的活动</h3></a>
	</div>

	<hr />
		<div class="weui-form-preview__item">
		<br />
		<h2>&nbsp;我发起过的活动</h2>
	</div>
	
<% 
		List<Activity> activitys = new ArrayList<Activity>();
		DataBaseUtil baseUtil = new DataBaseUtil();
		activitys = baseUtil.getSomeActivity(openid);
		//获取最后一行的行号
		int size = activitys.size();
		pageCount = (size % PAGESIZE == 0) ? (size / PAGESIZE) : (size / PAGESIZE + 1);
		//当前显示的页数
		int curPage = 1;
		String tmp = request.getParameter("curPage");
		if (tmp == null) {
			tmp = "1";
		}
		curPage = Integer.parseInt(tmp);
		if (curPage >= pageCount)
			curPage = pageCount;
		if (curPage <= 1)
			curPage = 1;
		int count = 1;
		int i = (curPage-1)*3;
		while(i < size) {
			if (count > PAGESIZE)
				break;
			count++;
	%>

	<div class="weui-form-preview">
		<!-- head 部分 -->
		<div class="weui-form-preview__hd">
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">活动名称</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getActivityName()%></span>
			</div>
		</div>
		<!-- body 部分 -->
		<div class="weui-form-preview__bd">
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">活动开始时间</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getStartTime()%></span>
			</div>
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">活动结束时间</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getEndTime()%></span>
			</div>
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">活动地点</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getActivityAddress()%></span>
			</div>
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">活动介绍</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getActivityIntro()%></span>
			</div>
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">发起人</label> <span
					class="weui-form-preview__value"><%=activitys.get(i).getActivityHolder()%></span>
			</div>
		</div>
	</div>

	<div class="weui-form-preview__ft">
		<a href="delete_activity.jsp?parmer=<%=activitys.get(i).getId()%>"
			onclick="return confirm('确定将此记录删除?')"
			class="weui-form-preview__btn weui-form-preview__btn_default">删除</a>

		<a href="update_activity.jsp?parmer=<%=activitys.get(i).getId()%>"
			class="weui-form-preview__btn weui-form-preview__btn_default">修改</a>
			
		<a href="javascript:void(0);" 
			class="weui-form-preview__btn weui-form-preview__btn_primary" 
			onclick="onCreatePoster('<%=openid %>',<%=activitys.get(i).getId()%>)">生成活动海报</a>
			
	</div>
	<hr />

	<!-- foot 	部分 -->
		<% i++;}%>
	<!-- return confirm('确定将此记录删除?') -->

	<br />
	<div style="text-align: center">
		<a href="my_activity.jsp?curPage=1" class="weui-footer__link">首页</a> <a
			href="my_activity.jsp?curPage=<%=curPage - 1%>"
			class="weui-footer__link">上一页</a> <a
			href="my_activity.jsp?curPage=<%=curPage + 1%>"
			class="weui-footer__link">下一页</a> <a
			href="my_activity.jsp?curPage=<%=pageCount%>"
			class="weui-footer__link">尾页</a> <br />
		<div class="weui-footer">
			<p class="weui-footer__text">
				第<%=curPage%>页/共<%=pageCount%>页
			</p>

		</div>
		<br /> <br />
	</div>

	<div class="weui-footer ">
		<p class="weui-footer__links">
			<a href="#" class="weui-footer__link">华师校友通讯录</a>
		</p>
		<p class="weui-footer__text">Copyright © 2017 SCNU</p>
	</div>
	<br />
</body>
</html>