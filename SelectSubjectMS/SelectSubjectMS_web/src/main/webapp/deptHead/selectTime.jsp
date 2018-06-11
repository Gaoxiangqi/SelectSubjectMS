<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/icon.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/demo/demo.css">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
			$(function(){
			 var flag =	"${flag}";
			 if (flag == 'update') {
				 $.messager.alert("系统提示","修改成功！");
			 }
			});
		</script>
		<style type="text/css">
			#table{
				width:60%;
				margin-left:20%;
				margin-top:2%;
				border-collapse:separate;
				border-spacing:30px 20px;
			}
			th{
			text-align: center;
			}
			.t1{
				font-size: 18px;
				padding-bottom: 2%;
			}
			#button{
				margin-left: 50%;
				margin-top:2%;
				width: 100px;
			}
			.panel-title{
	    		font-size: 18px;
	    		padding: 6px;
	    	}
		</style>
</head>
<body>
	<div class="easyui-panel" title="选题阶段" fit="true" >
			<form action="${pageContext.request.contextPath}/deptHead_updateSetting.action" method="post" >
		 	<table id="table">
		 		<tr>
		 			<th class="t1">阶段</th>
		 			<th class="t1">开始时间</th>
		 			<th class="t1">结束时间</th>
		 		</tr>
		 		<tr>
		 			<th>教师出题阶段</th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.teacherSetStartTime}" name="setting.teacherSetStartTime"></th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.teacherSetEndTime}" name="setting.teacherSetEndTime"></th>
		 		</tr>
		 		<tr>
		 			<th>学生选题阶段</th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.studentSelectStartTime}" name="setting.studentSelectStartTime"></th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.studentSelectEndTime}" name="setting.studentSelectEndTime"></th>
		 		</tr>
		 		<tr>
		 			<th>教师选择学生阶段</th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.teacherSelectStartTime}" name="setting.teacherSelectStartTime"></th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.teacherSelectEndTime}" name="setting.teacherSelectEndTime"></th>
		 		</tr>
		 		<tr>
		 			<th>系主任调整阶段</th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.deptHeadAdjustStartTime}" name="setting.deptHeadAdjustStartTime"></th>
		 			<th><input class="easyui-datebox" data-options="sharedCalendar:'#cc'" value="${currentUser.yard.setting.deptHeadAdjustEndTime}" name="setting.deptHeadAdjustEndTime"></th>
		 		</tr>
		 	</table>
		 	<div>
				<input  type="submit" value="修改" id="button">
			</div>
		 	<div id="cc" class="easyui-calendar" style="display: none;"></div>
		 	</form>
	</div>
			
			 
</body>
</html>