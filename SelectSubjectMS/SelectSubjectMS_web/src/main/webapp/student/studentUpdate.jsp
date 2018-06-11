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
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js">
<script type="text/javascript">
				//修改个人信息
				function updatePersonalInfo(){
					$("#ff").form("submit",{
			    		 url:"${pageContext.request.contextPath}/student_updatePersonalInfo.action", 
			    		onSubmit:function(){
			    			if(!isPhone($("#phone").val())){
	  							$.messager.alert("系统提示","请输入正确的电话！");
	  							return false;
	  						}
	  						if(!isEmail($("#email").val())){
	  							$.messager.alert("系统提示","请输入正确的邮箱！");
	  							return false;
	  						}
	  						else return $(this).form("validate");  //在提交之前验证信息是否合法
		        		},
		        		success:function(result){
		        				$.messager.alert("系统提示","保存成功");
		        				
		        		}
			    	});
				}
				//电话号码匹配函数
	  			function isPhone(phone){
	  				var test=/^1[345789]\d{9}$/;
	  				return test.test(phone);
	  			}
	    		//邮箱匹配函数
	  			function isEmail(mail){
	  				var szReg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/; 
	  				var bChk=szReg.test(mail);
	  				return bChk;
	  			}
		</script>
</head>
<body>
	<h2 style="text-align: center;">修改个人信息</h2>
	<p style="text-align: center;">修改文本框的信息点击保存即可完成修改信息操作</p>
	<div style="margin: 20px 0; border: 1px solid"></div>
	<div style="width: 400px; padding: 30px 60px; margin-left: 300px">
		<form id="ff" data-options="novalidate:true" method="post">
			<div style="margin-bottom: 20px">
				<div>账号:</div>
				<input class="easyui-textbox" style="width: 100%; height: 32px"
					value="${currentUser.user.userName}" disabled="disabled">
			</div>
			<div style="margin-bottom: 20px">
				<div>姓名:</div>
				<input class="easyui-validatebox" name="student.realName"
					style="width: 100%; height: 32px" value="${currentUser.realName}"
					required="true">
			</div>
			<div style="margin-bottom: 20px">
				<div>所属专业:</div>
				<input class="easyui-textbox" style="width: 100%; height: 32px"
					value="${currentUser.schoolClass.major.majorName}" disabled="disabled">
			</div>
			<div style="margin-bottom: 20px">
				<div>所属班级:</div>
				<input class="easyui-textbox" style="width: 100%; height: 32px"
					value="${currentUser.schoolClass.className}" disabled="disabled">
			</div>
			<div style="margin-bottom: 20px">
				<div>电话号码:</div>
				<input class="easyui-validatebox" name="student.phone" id="phone"
					value="${currentUser.phone}" style="width: 100%; height: 32px"
					required="true" >
			</div>
			<div style="margin-bottom: 20px">
				<div>邮箱:</div>
				<input class="easyui-validatebox" name="student.email" id="email"
					value="${currentUser.email}" style="width: 100%; height: 32px"
					required="true" >
			</div>

			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok"
					onclick="updatePersonalInfo()">保存</a>
			</div>
		</form>
	</div>
</body>
</html>