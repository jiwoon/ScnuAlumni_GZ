<%@page import="java.util.ArrayList"%>
<%@page import="com.newttl.scnualumni_gz.util.DataBaseUtil"%>
<%@page import="com.newttl.scnualumni_gz.bean.database.Activity"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<link rel="stylesheet" href="resources/css/weui.min.css">
<link rel="stylesheet" href="resources/css/jquery-weui.css">
<link rel="stylesheet" href="resources/css/demos.css">
<script>
	function checkInput() {
		var activityName = document.formSub.aname.value;
		var activityAddress = document.formSub.address.value;
		var startTime = document.formSub.start_time.value;
		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
		var endTime = document.formSub.end_time.value;
		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
		var activityIntro = document.formSub.atip.value;

		if (activityName.length <= 0) {
			$.alert("活动名称不能为空!");
		} else if (startTime.length <= 0) {
			$.alert("开始时间不能为空!");
		} else if (endTime.length <= 0) {
			$.alert("结束时间不能为空!");
		} else if (start >= end) {
			$.alert("开始时间不能大于截止时间");
		} else if (activityAddress <= 0) {
			$.alert("活动地址不能为空");
		} else if (activityIntro <= 0) {
			$.alert("活动介绍不能为空");
		} else {
			//提交用户注册的信息
			$.toast("提交成功");
			document.formSub.submit();
		}
	}
</script>
<title>校友最新活动</title>
</head>
<!-- 获取要修改的活动信息 -->
<body>
	<div class="weui-btn-area">
		<a href="recent_activity.jsp"
			class="weui-btn weui-btn_mini weui-btn_plain-primary">近期活动</a> <a
			href="add_activity.jsp"
			class="weui-btn_mini weui-btn weui-btn_plain-primary">发起活动</a> <a
			href="my_activity.jsp"
			class="weui-btn_mini weui-btn weui-btn_primary"><h3>我的活动</h3></a>
	</div>
	<hr />
	<%
		String aid = request.getParameter("parmer");
		session.setAttribute("aid", aid);
		int id = Integer.parseInt(aid);

		Activity activity = new Activity();
		DataBaseUtil baseUtil = new DataBaseUtil();
		activity = baseUtil.getTheActivity(id);
	%>
	<div>
		<br />
		<h2 class="weui-form-preview__btn weui-form-preview__btn_primary">修改活动</h2>
	</div>
	<form action="do_update_activity.jsp" method="POST" name="formSub">
		<div class="weui-btn-area">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">活动名称<span style='color: red'>*</span></label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" type="text" name="aname"
						value='<%=activity.getActivityName()%>' placeholder="请输入活动名称" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label for="inline" class="weui-label">活动时间<span
						style='color: red'>*</span></label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="datetime-picker1" type="text"
						name="start_time" placeholder="请选择开始时间"
						value='<%=activity.getStartTime()%>'>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至 <input class="weui-input"
						id="datetime-picker2" type="text" name="end_time"
						placeholder="请选择截止时间" value='<%=activity.getEndTime()%>'>
				</div>
			</div>

			<div id="picker-container"></div>
			<script src="resources/js/jquery-2.1.4.js"></script>
			<script src="resources/js/jquery-weui.js"></script>
			<script>
				$("#datetime-picker1").datetimePicker();
			</script>
			<script>
				$("#datetime-picker2").datetimePicker();
			</script>

			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">活动地点<span style='color: red'>*</span></label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" type="text" name="address"
						value='<%=activity.getActivityAddress()%>' placeholder="请输入活动地点" />
				</div>
			</div>



			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" name="atip" placeholder="请输入活动介绍"
							rows="3"><%=activity.getActivityIntro()%></textarea>
						<div class="weui-textarea-counter">
							<span>0</span>/100
						</div>
					</div>
				</div>
			</div>
			<!-- 绿色Button -->
			<div class='demos-content-padded'>
				<a href="javascript:;" id='show-confirm'
					class="weui-btn weui-btn_primary">提交</a>
			</div>
			<br /> <br /> <br /> <br />
		</div>
	</form>
	<script src="resources/js/jquery-2.1.4.js"></script>
	<script src="resources/js/jquery-weui.js"></script>
	<script>
		$(document).on("click", "#show-confirm", function() {
			$.confirm("您确定要提交信息吗?", "提交信息", function() {
				//确认操作
				checkInput();
			}, function() {

			});
		});
	</script>
</body>
</html>