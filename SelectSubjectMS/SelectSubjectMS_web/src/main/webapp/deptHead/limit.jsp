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
			
			function update (id1,id2,id3) {
				$("#"+id1+"").form('submit',{
				url:"${pageContext.request.contextPath}/deptHead_updateLimit.action",
				onSubmit:function(){
					var r = /^\+?[1-9][0-9]*$/;
					var str = $("#"+id2+"").val();
					if (! r.test(str)) {
						$.messager.alert("系统提示","请确定您输入为正整数！");
						return ;
					}
					return $(this).form("validate");
				},
				success:function(result){
					var str = $("#"+id2+"").val();	 		
  				 	$("#"+id3+"").text(str);
  				 	$.messager.alert("系统提示","保存成功");	
      				$("#dlg").dialog("close");
      				$("#dg").datagrid("reload"); 
  					} 
				});
			}
			
		</script>
		<style type="text/css">
			table{
				background-color:#E7F0FF;
				border-collapse:separate;
				border:1px solid blue;
				width: 60%;
				margin-left: 20%;
				margin-top: 2%;
			}
			table tr th{
				border:1px solid green;
				padding-top: 20px;
			}
			.title{
				font-size: 20px;
				text-align: center;
			}
			.t1 th{
				font-size: 10px;
				text-align: center;
			}
			th{
				padding-bottom: 20px;
			}
			.panel-title{
	    		font-size: 18px;
	    		padding: 6px;
	    	}
		</style>
</head>
<body>
	<div class="easyui-panel" title="上限选择" fit="true" >
	
			<form id="fm1">
				<table>
						<tr>
							<th colspan="2" class="title" valign="middle">志愿扎堆提醒</th>
						</tr>
						<tr class="t1">
							<th>当前提醒下界</th>
							<th id="value1">${currentUser.yard.setting.warnNum}</th>
						</tr>
						<tr class="t1">
							<th>设置提醒下界</th>
							<th><input type="text" name="currentUser.yard.setting.warnNum"  class="easyui-validatebox" id="warnNum"></th>
						</tr>
						<tr >
							<th colspan="2" style="color: #850B4B">
								（注：设置扎堆提醒志愿数，当选择某一题目的学生数超过提醒下界时，选择该题的学生将收到提醒，但是仍然可以选择此题。）
							</th>
						</tr>
						<tr class="t1"><th colspan="2"><a href="javascript:update('fm1','warnNum','value1')"  class="easyui-linkbutton" iconcls="icon-ok">修改设置</a></th></tr>
				</table>
			</form>
			
			<form id="fm2">
				<table>
						<tr >
							<th colspan="2" class="title">每道题目被选的上限</th>
						</tr>
						<tr class="t1">
							<th>每道题目被选的上限</th>
							<th id="value2">${currentUser.yard.setting.maxSelectNum}</th>
						</tr>
						<tr class="t1">
							<th>设置选择上限</th>
							<th><input type="text" name="setting.maxSelectNum"  class="easyui-validatebox" id="maxSelectNum"></th>
						</tr>
						<tr>
							<th colspan="2" style="color: #850B4B">
								（注：设置每道题被选上限，当某道题被选择的学生数超过限制时，其他学生不能再选择该题。）
							</th>
						</tr>
						<tr class="t1"><th colspan="2"><a href="javascript:update('fm2','maxSelectNum','value2')"  class="easyui-linkbutton" iconcls="icon-ok">修改设置</a></th></tr>
				</table>
			</form>
			
				<form id="fm3">
				<table>
						<tr>
							<th colspan="2" class="title">老师出题上限</th>
						</tr>
						<tr class="t1">
							<th>当前老师出题上限</th>
							<th id="value3">${currentUser.yard.setting.maxSetNum}</th>
						</tr>
						<tr class="t1">
							<th>设置出题上限</th>
							<th><input type="text" name="setting.maxSetNum"  class="easyui-validatebox" id="maxSetNum"></th>
						</tr>
						<tr>
							<th colspan="2" style="color: #850B4B">
								（注：设置出题上限，每个老师只能出少于或等于该个数的题目，超过不允许出题）
							</th>
						</tr>
						<tr class="t1"><th colspan="2"><a href="javascript:update('fm3','maxSetNum','value3')"  class="easyui-linkbutton" iconcls="icon-ok">修改设置</a></th></tr>
				</table>
			</form>
				
				<form id="fm5">
				<table>
						<tr>
							<th colspan="2" class="title">学生选题上限</th>
						</tr>
						<tr class="t1">
							<th>当前学生选题上限</th>
							<th id="value5">${currentUser.yard.setting.maxStuSelectNum}</th>
						</tr>
						<tr class="t1">
							<th>设置学生选题上限</th>
							<th><input type="text" name="setting.maxStuSelectNum  class="easyui-validatebox" id="maxStuSelectNum"></th>
						</tr>
						<tr>
							<th colspan="2" style="color: #850B4B">
								（注：设置选题上限，学生只能选择该数以内的题目）
							</th>
						</tr>
						<tr class="t1"><th colspan="2"><a href="javascript:update('fm5','maxStuSelectNum','value5')"  class="easyui-linkbutton" iconcls="icon-ok">修改设置</a></th></tr>
				</table>
			</form>
			
				<form id="fm4">
				<table>
						<tr>
							<th colspan="2" class="title">设置选题题年级</th>
						</tr>
						<tr class="t1">
							<th>当前选题年级</th>
							<th id="value4">${currentUser.yard.setting.currentGrade}</th>
						</tr>
						<tr class="t1">
							<th>设置选题年级</th>
							<th><input type="text" name="setting.currentGrade"  class="easyui-validatebox" id="currentGrade"></th>
						</tr>
						<tr>
							<th colspan="2" style="color: #850B4B">
								（注：设置毕业生年级之后，其他年级的学生不能登录使用此毕业选题系统。）
							</th>
						</tr>
						<tr class="t1"><th colspan="2"><a href="javascript:update('fm4','currentGrade','value4')"  class="easyui-linkbutton" iconcls="icon-ok">修改设置</a></th></tr>
				</table>
			</form>
	</div>		 
</body>
</html>