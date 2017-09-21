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
						searchClick();
					});
				}else{
					//显示搜索到的校友(最简使用方法)
					var html=bt('resultmodel',data);
					//渲染
					document.getElementById('result').innerHTML=html;
					searchClick();
				}
			}
		});
	}else {
		$.alert("","请输入正确的名字!");
	}
	
}

</script>

<!-- 结果显示模板 -->
<script id="resultmodel" type="text/html">

<div id="mysearch">
<!
	
		for(var i=0;i<users.length;i++){
!>
			<form action="alumniInfo.jsp?alumni=<!=users[i].openId!>" method="post">
			<div class="weui_cells weui_cells_access" style="margin-top: 0px">
			
			<div class="weui_cell">
				<div class="inline weui_cell_bd weui_cell_primary">
					<p><!=users[i].userName!></p>
				</div>
				
       			<div class="weui-cell_ft" style="height: 48px">
					<img src="<!=users[i].headImgUrl!>" style="height: 40px;width: 40px">
		 		</div>
       			
    		</div>
    		
    		
			</div>
			
			
			</form>
<!
		}
!>
</div>

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
	   		<input class="weui_input" type="text" id="autoComplete" placeholder="输入校友名字" onkeyup="widthCheck(this, 32);value=value.replace(/\s/g,'');">
	   	</div>
 		
 		<div class="weui-cell_ft">
 			
 			<input class="weui_btn weui_btn_mini weui_btn_primary" type="button" name="btnSearch" onclick="onSearch();" value="搜索">
 		</div>
 	</div>
</div>

<div id="result">
<div id="first">
<%
	if(alumnus.size() > 0){
		for(int i=0;i < alumnus.size();i++){
			
%>
			<form action="alumniInfo.jsp?alumni=<%=alumnus.get(i).getOpenId() %>" method="post">
			<div class="weui_cells weui_cells_access" style="margin-top: 0px">
			
			<div class="weui_cell">
				<div class="inline weui_cell_bd weui_cell_primary">
					<p><%=alumnus.get(i).getUserName()%></p>
				</div>
				
       			<div class="weui-cell_ft" style="height: 48px">
					<img src="<%=alumnus.get(i).getHeadImgUrl()%>" style="height: 40px;width: 40px">
		 		</div>
       			
    		</div>
    		
			</div>
			
			
			</form>
<%		
		}
	}
%>

</div>

<script>
		var box = document.getElementById("first");
		var divs = box.children;
		for (var k = 0; k < divs.length; k++) {
			divs[k].index = k;
			divs[k].onclick = function(){
				this.submit();
			}
		}
</script>

</div>
 
</div>

<script>
function searchClick() {
	var box = document.getElementById("mysearch");
	var divs = box.children;
	for (var k = 0; k < divs.length; k++) {
		divs[k].index = k;
		divs[k].onclick = function(){
			this.submit();
		}
	}
}

//限制输入框字节数
function widthCheck(str,maxlen) {
	var width=0;
	//获取字符数(不区分英汉)
	for (var i = 0; i < str.value.length; i++) {
		//遍历获取某个字符的编码
		var code=str.value.charCodeAt(i);
		
		if ((code >= 0x0001 && code <= 0x007e) || (0xff60<=code && code<=0xff9f)) {
			//单字节的加1
			width++;
		}else {
			//双字节的加2
			width+=2;
		}
		if (width > maxlen) {
			str.value=str.value.substr(0,i);
			break;
		}
	}
}
</script>

<br>
<div class="weui-footer foot">
	<p class="weui-footer__links">
		<a href="#" class="weui-footer__link">华南师大校友会</a>
	</p>
	<p class="weui-footer__text">Copyright © 2017 SCNU</p>
</div>
<br/>

</body>
</html>