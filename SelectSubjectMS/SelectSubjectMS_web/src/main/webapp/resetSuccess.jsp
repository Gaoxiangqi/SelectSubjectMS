<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码成功</title>
   <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.8.3.js"></script>
   <script type="text/javascript">
          $(function(){
        	 var second=$("#second").text();
        	 setInterval(function(){
        		 if(second==0){
        			 window.location.href="${pageContext.request.contextPath}/login.jsp";
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
    <div style="font-size: 15px;">您已成功重新设置密码！</div>
            温馨提示：<span id="second" style="color: blue">5</span>秒后自动跳转到登陆页面;<a href="${pageContext.request.contextPath}/login.jsp">点我立即跳转</a>
</body>
</html>