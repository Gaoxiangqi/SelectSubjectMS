<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% SecurityUtils.getSubject().logout(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
   <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.8.3.js"></script>
   <script type="text/javascript">
          $(function(){
        	 var second=$("#second").text();
        	 setInterval(function(){
        		 if(second==0){
        			 window.location.href="${pageContext.request.contextPath}/user_logout.action";
        		 }
        		 else {
        			 second--;
        			 $("#second").html(second);
        		 }
        	 }, 1000);
        	
          });
   </script>
</head>
<body>
             密码修改成功！
             温馨提示：<span id="second">5</span>秒后自动跳转到登陆页面;<a href="${pageContext.request.contextPath}/user_logout.action">点我立即跳转</a>
</body>
</html>