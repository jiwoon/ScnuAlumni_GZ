<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.newttl.scnualumni_gz.util.DataBaseUtil" %>
<%@page import="com.newttl.scnualumni_gz.bean.database.SignedUser" %>
<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">

<link rel="stylesheet" href="resources/css/weui.css">
<link rel="stylesheet" href="resources/css/weui.min.css">
<link rel="stylesheet" href="resources/css/jquery-weui.css">

<style type="text/css">
 .p{
 	line-height:48px;
 	color: black;
 	font-size: large;
 }
 .inline{
 	float: left;
 }
.my-label{
	color: gray;
 }
 </style>

<!-- <title>校友个人信息</title> -->

</head>

<body>

<%
	
	String alumniOpenId=request.getParameter("alumni");
	String contactType="";
	DataBaseUtil baseUtil=new DataBaseUtil();
	SignedUser signedUser=baseUtil.getSigned(alumniOpenId);
	String type=signedUser.getContactType();
	if("1".equals(type)){
		contactType="QQ";
	}else if("2".equals(type)){
		contactType="邮箱";
	}else if("3".equals(type)){
		contactType="手机";
	}
	//校友已经注册了，显示校友的信息 
	/* if(null != signedUser){ */
%>

<script type="text/javascript">
	document.title="<%=signedUser.getUserName()%>个人信息";
</script>

<form name="formInfo" style="height: 100%">
<div class="page__bd">
 <div class="weui_cells weui_cells_access"  style="margin-top: 0px">
 	<div class="weui_cell">
 		<div class="weui_cell_bd weui_cell_primary" style="height: 48px">
 			<p class="p"><%=signedUser.getUserName()%></p>
 		</div>
 		<div class="weui-cell_ft" style="height: 48px">
 			<img src="<%=signedUser.getHeadImgUrl() %>" style="height: 45px;width: 45px">
 		</div>
 	</div>
 </div>	
			    
<div class="weui_cells weui_cells_form" style="margin-top: 0px">

	<div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label">性别</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <p ><%=signedUser.getSex() %></p>
        </div>
    </div>
	
	<div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label">学院</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <p ><%=signedUser.getCollege()%></p>
        </div>
    </div>
    
    <div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label">年级</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <p><%=signedUser.getGrade()%></p>
        </div>
    </div>
    
    <div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label">班级</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <p ><%=signedUser.getUserClass() %></p>
        </div>
    </div>

	<div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label"><%=contactType %></label></div>
        <div class="weui_cell_bd weui_cell_primary"">
            <p ><%=signedUser.getContact() %></p>
        </div>
    </div>
    
    <div class="weui_cell">
    	<div class="weui_cell_hd"><label class="weui-label my-label">城市</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <p ><%=signedUser.getCity()%></p>
        </div>
    </div>

</div>

<div class="weui_cells weui_cells_form" style="margin-top: 0px">

	<div class="weui_cell">
	   	<div class="weui_cell_hd"><label class="weui-label my-label">行业</label></div>
	    <div class="weui_cell_bd weui_cell_primary">
	        <p ><%=signedUser.getIndustry() %></p>
	    </div>
	</div>
	
	<div class="weui_cell">
	   	<div class="weui_cell_hd"><label class="weui-label my-label">爱好</label></div>
	    <div class="weui_cell_bd weui_cell_primary">
	        <p ><%=signedUser.getHobby() %></p>
	    </div>
	</div>
	
	<div class="weui_cell">
	   	<div class="weui_cell_hd"><label class="weui-label my-label">职业</label></div>
	    <div class="weui_cell_bd weui_cell_primary">
	        <p ><%=signedUser.getProfession() %></p>
	    </div>
	</div>
	
</div>

</div>

</form>

<br>
<div class="weui-footer">
	<p class="weui-footer__links">
		<a href="#" class="weui-footer__link">华南师大校友会</a>
	</p>
	<p class="weui-footer__text">Copyright © 2017 SCNU</p>
</div>
<br/>

</body>
</html>