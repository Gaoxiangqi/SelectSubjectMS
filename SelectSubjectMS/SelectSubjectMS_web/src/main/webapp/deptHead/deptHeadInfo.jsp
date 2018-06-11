<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<style type="text/css">
		#table1 tr td{
			padding-bottom:20px;
			font-size: 18px;
		}
	</style>
</head>
<body>
	<div id="title" style="height: 70px">
			<h2 style="text-align: center;">个人信息</h2>
	</div>
	<div style="height:350px;">
				<table style="margin-left: 450px;border-collapse: separate;" id="table1">
					<tr>
						<td>账号：</td>
						<td>${currentUser.user.userName}</td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td>${currentUser.realName}</td>
					</tr>
					 <tr>
						<td>性别:</td>
						<td>${currentUser.sex}</td>
					 </tr>
					  <tr>
						<td>院系:</td>
						<td>${currentUser.yard.yardName}</td>
					 </tr>
					 
					<tr>
						<td>电话号码：</td>
						<td>${currentUser.phone}</td>
					</tr>
					<tr>
						<td>邮箱</td>
						<td>${currentUser.email}</td>
					</tr>
				</table>
	</div>
</body>
</html>