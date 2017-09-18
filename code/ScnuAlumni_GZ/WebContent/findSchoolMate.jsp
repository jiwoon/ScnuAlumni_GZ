<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.newttl.scnualumni_gz.util.DataBaseUtil"%>
<%@page import="com.newttl.scnualumni_gz.bean.database.Alumnus"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%!
	JSONObject alumnusJson=new JSONObject();
%>
<%
	request.setCharacterEncoding("UTF-8");
/* 
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 */
	List<Alumnus> alumnus=new ArrayList<Alumnus>();
	DataBaseUtil baseUtil=new DataBaseUtil();
	alumnus=baseUtil.getAllAlumnus();
	Map map=new HashMap();
	map.put("users", alumnus);
	alumnusJson=JSONObject.fromObject(map);
%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>查找校友</title>

<link rel="stylesheet" href="resources/css/weui.css">
<link rel="stylesheet" href="resources/css/jquery-weui.css">
<link rel="stylesheet" href="resources/css/weui.min.css">

<script src="resources/js/baiduTemplate.js"></script>
<script src="resources/js/jquery-2.1.4.js"></script>
<script src="resources/js/jquery-weui.js"></script>

<style type="text/css">

.inline{
 	float: left;
 }
 
 .m-weui-loadmore{
	 width:100%;
	 height:600px;
	 margin:0 auto;
	 background-color:rgba(0,0,0,0.3);
	 line-height:600px;
	 font-size:18px;
	 color:black;
	 text-align:center;
	 position:absolute;
	 top:0;
	 left:0;
	 z-index:999;
 }
 
 .m-content{
    min-height: 500px;
}

.foot{
    position:relative;
    width:100%;
    height: 50px;
}

 </style>

<script type="text/javascript">

//搜索数据
function onSearch() {
	var load=document.getElementById("loadMore");
	var name=$("#autoComplete").val();
	if (("" != name) && (name.indexOf(" ") < 0)) {
		load.style.visibility="visible";
		var jsonStr={'userName':name};
		$.ajax({
			type:"POST",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			url:"/ScnuAlumni_GZ/SignUpServlet",
			data:JSON.stringify(jsonStr),
			dataType:"json",
			success:function(data){
				load.style.visibility="hidden";
				/* 
				var resp=JSON.stringify(data);
				$.alert("data",resp);
				var jsonObj = JSON.parse(resp);
				 */
				var jsonLength=data.users.length;
				//使用模板 ,使用baidu.template命名空间
				var bt=baidu.template;
				//可以设置分隔符
				bt.LEFT_DELIMITER='<!';
				bt.RIGHT_DELIMITER='!>';

				//可以设置输出变量是否自动HTML转义
				//bt.ESCAPE = false;
				if (jsonLength <= 0) {
					$.alert("","不存在该校友!",function(){
						data=<%=alumnusJson%>;
						document.getElementById("autoComplete").value="";
						//显示全部的校友(最简使用方法)
						var html0=bt('resultmodel',data);
						//渲染
						document.getElementById('result').innerHTML=html0;
					});
				}else{
					//显示搜索到的校友(最简使用方法)
					var html=bt('resultmodel',data);
					//渲染
					document.getElementById('result').innerHTML=html;
				}
			}
		});
	}else {
		$.alert("","请输入正确的名字!");
	}
	
}

function alumniClick(i) {
	var formName="alumniform"+String.valueOf(i);
	document.formName.submit();
}

function alumnus(i) {
	var formName="alumnus"+String.valueOf(i);
	document.formName.submit();
}


</script>

<!-- 结果显示模板 -->
<script id="resultmodel" type="text/html">

<!
	
		for(var i=0;i<users.length;i++){
!>
			<form action="alumniInfo.jsp" method="post" name="alumniform<!=i!>">
			<div class="weui_cells weui_cells_form" style="margin-top: 0px">
			
			<div class="weui_cell">
				<div class="inline">
					<p><!=users[i].userName!></p>
				</div>
    			<div class="inline weui_cell_bd weui_cell_primary">
					<button class="weui_input" onclick="alumniClick(i);" value="<!=users[i].userName!>"></button>
					<input type="hidden" name="alumniName" value="<!=users[i].userName!>">
 					<input type="hidden" name="alumniOpenId" value=<!=users[i].openId!>>
				</div>
        		<div class="inline weui-cell_ft">
         		    <img src="<!=users[i].headImgUrl!>" style="height: 40px;width: 40px">
					<input type="hidden" name="alumniHeadImgUrl" value=<!=users[i].headImgUrl!>>
       			</div>
    		</div>
			</div>
			</form>
<!
		}
!>

</script>

</head>
<body style="height: 100%">

<div class="m-content">

<div class="m-weui-loadmore" id="loadMore" style="visibility: hidden;">
     <i class="weui-loading"></i>
     <span class="weui-loadmore__tips">正在搜索...</span>
</div>

<div class="weui_cells weui_cells_access"  style="margin-top: 0px;">
 	<div class="weui_cell">
 		<div class="weui_cell_bd weui_cell_primary">
	   		<input class="weui_input" type="text" id="autoComplete" placeholder="输入校友名字" onkeyup="value=value.replace(/\s/g,'')">
	   	</div>
 		
 		<div class="weui-cell_ft">
 			
 			<input class="weui_btn weui_btn_mini weui_btn_primary" type="button" name="btnSearch" onclick="onSearch();" value="搜索">
 		</div>
 	</div>
</div>

<div id="result">

<%
	if(alumnus.size() > 0){
		for(int i=0;i < alumnus.size();i++){
			
%>
			<form action="alumniInfo.jsp" method="post" name="alumnus<%=i%>">
			<div class="weui_cells weui_cells_form" style="margin-top: 0px">
			
			<div class="weui_cell">
				<div class="inline">
					<p><%=alumnus.get(i).getUserName()%></p>
				</div>
    			<div class="inline weui_cell_bd weui_cell_primary">
					<button class="weui_input" onclick="alumnus(i);" value="<%=alumnus.get(i).getUserName()%>"></button>
					<input type="hidden" name="alumniName" value="<%=alumnus.get(i).getUserName()%>">
 					<input type="hidden" name="alumniOpenId" value="<%=alumnus.get(i).getOpenId()%>">
				</div>
        		<div class="inline weui-cell_ft">
         		    <img src="<%=alumnus.get(i).getHeadImgUrl()%>" style="height: 40px;width: 40px">
					<input type="hidden" name="alumniHeadImgUrl" value="<%=alumnus.get(i).getHeadImgUrl()%>">
       			</div>
    		</div>
			</div>
			</form>
<%		
		}
	}
%>

</div>	

</div>
<!-- 
<script src="resources/js/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
 -->
<br>
<div class="weui-footer foot">
	<p class="weui-footer__links">
		<a href="#" class="weui-footer__link">华师校友通讯录</a>
	</p>
	<p class="weui-footer__text">Copyright © 2017 SCNU</p>
</div>
<br/>

</body>
</html>