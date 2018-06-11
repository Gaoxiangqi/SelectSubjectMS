<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>毕设选题系统登录</title>
 <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.8.3.js"></script>
	<link href="${pageContext.request.contextPath}/style/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/style/bootstrap/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
		.title1{
			margin-top: 5%;
			background-color: 
		}
		#span1{
			font-size: 40px;
			color: #62ACA9;
			margin-left: 20%;
			
		}
		#span2{
		font-size: 25px;
		color: red;
		}
		#span3{
		font-size: 20px;
		color: blue;
		}
		.div3{
			margin-top: 30px;
		}
	</style>
	</head>
<body>
          <div class="container" >
    	<div class="row title1" >
    	    <div class="col-md-7">
    	    	<span id="span1">访问受限</span>
    	    </div>
    	</div>
        <div class=" row" >
           <div class="form-group col-md-offset-4">
           		<img  src="${pageContext.request.contextPath}/style/img/main/warn.png">
           		<span id="span2">您没有访问权限！</span>
           </div>
           <div class="form-group col-md-offset-4 div3">
           		<span id="span3">该资源只对指定用户类型开放</span>
           </div>
        </div>
    </div>
</body>
</html>