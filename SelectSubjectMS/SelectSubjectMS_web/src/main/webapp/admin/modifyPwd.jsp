<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro"  uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/demo/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			$.extend($.fn.validatebox.defaults.rules, {
					equals : {// 验证密码是否一致
			     		    validator : function(value,param){
			                return  value==$(param[0]).val();
			           	},
			             message : '两次输入密码不一致'
			        }
			});
		});
		function modifyPwd(){
			$("#ff").form("submit",{
	    		 url:"${pageContext.request.contextPath}/admin_modifyPwd.action", 
	    		onSubmit:function(){
       			return  $(this).form("validate");  //在提交之前验证信息是否合法
       		},
       		success:function(data){
       			var d=$.parseJSON(data);
       			if(d.result==1){
       				$.messager.alert("系统提示",d.message);
       				window.location.href="${pageContext.request.contextPath}/modifySuccess.jsp";
       			}
       			else{
       				$.messager.alert("系统提示",d.message);
       			}
       		}
	    	});
		}
	</script>
</head>
<body>
		<div class="easyui-panel" title="修改密码" data-options="pagination:true" fit="true">
		
	<div style="margin: 20px 0;"></div>
	<div style="width: 400px; padding: 30px 60px; margin-left: 300px">
		<form id="ff" data-options="novalidate:true" method="post">
			<div style="margin-bottom: 20px">
				<div>原密码:</div>
				<input class="easyui-validatebox" style="width: 100%; height: 32px" required="true"
					 type="password" name="password">
			</div>
			<div style="margin-bottom: 20px">
				<div>新密码:</div>
				<input class="easyui-validatebox" style="width: 100%; height: 32px" 
					required="true" type="password" name="rePassword" id="Pwd">
			</div>
			<div style="margin-bottom: 20px">
				<div>再次输入新密码:</div>
				<input class="easyui-validatebox"  style="width: 100%; height: 32px"
					required="true" type="password" id="rePwd" validType="equals['#Pwd']">
			</div>
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="modifyPwd()">保存</a>
			</div>
		</form>
		</div>
	</div>
</body>
</html>