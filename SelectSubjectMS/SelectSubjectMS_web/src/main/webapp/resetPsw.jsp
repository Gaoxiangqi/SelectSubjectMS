<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>毕设选题系统密码找回</title>
 <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-1.8.3.js"></script>
	<link href="${pageContext.request.contextPath}/style/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/style/bootstrap/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
		body{
		    background: url("${pageContext.request.contextPath}/style/limg/ogin/1.jpg");
		    background-size: 100%;
		    width: 100%;
		    animation-name:myfirst;
		    animation-duration:12s;
		    /*变换时间*/
		    animation-delay:2s;
		    /*动画开始时间*/
		    animation-iteration-count:infinite;
		    /*下一周期循环播放*/
		    animation-play-state:running;
		    /*动画开始运行*/
		}
		@keyframes myfirst
		{
		    0%   {background:url("${pageContext.request.contextPath}/style/img/login/1.jpg");background-size: 100%;}
		    34%  {background:url("${pageContext.request.contextPath}/style/img/login/2.jpg");background-size: 100%;}
		    67%  {background:url("${pageContext.request.contextPath}/style/img/login/3.jpg");background-size: 100%;}
		    100% {background:url("${pageContext.request.contextPath}/style/img/login/1.jpg");background-size: 100%;}
		}
		#img1{
			width: 300px;
			height: 100px;
			color: #93A6B2;
			background: rgba(0,0,0,1);
		}
		#span1{
			font-family: KaiTi;
			font-size: 40px;
			letter-spacing: 10px;
			color: white;
			font-weight: 200;
		}
		#span2{
			font-family: KaiTi;
			font-size: 20px;
			color: white;
		}
		#span3{
			font-family: "微软雅黑";
			font-size: 60px;
			letter-spacing: 40px;
			color: white;
		}
		#error{
			color: red;
			font-size: 15px;
			text-align: center;
			letter-spacing: 5px;
		}
		#span5{
			color: #AA2A59;
		}
		#span6{
			color: #AA2A59;
		}
		.form-title{
			color: white;
		}
		.title1{
			background: rgba(255,255,255,0.2);
			margin:5% auto;
		}
		.form{background: rgba(255,255,255,0.2);width:400px;margin:3% auto;}
		.form2{background: rgba(255,255,255,0.4);width:400px;}
		/*阴影*/
		.fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
		input[type="text"],input[type="password"]{padding-left:26px;}
		.checkbox{padding-left:21px;}
		.fa-user{
            left:0;
			background-image: url(${pageContext.request.contextPath}/style/img/login/user.png);
			background-position: 0px 5px;
			width: 30px;
			height: 30px;
			background-repeat: no-repeat;
			
		}
		.fa-lock{
            left:0;
			background-image: url(${pageContext.request.contextPath}/style/img/login/Password.png);
			background-position: 0px 5px;
			width: 30px;
			height: 30px;
			background-repeat: no-repeat;
			
		}
		.fa-checkCode{
            left:0;
			background-image: url(${pageContext.request.contextPath}/style/img/login/checkCode.png);
			background-position: 0px 5px;
			width: 30px;
			height: 30px;
			background-repeat: no-repeat;
			position: absolute;
			z-index: 5;
			
		}
		#checkCode{
		   left:0;
			width: 100px;
           }
	</style>
	<script type="text/javascript">
		
	$(function(){
		//验证表单
		$("#form1").submit(function(){
			//验证用户名
			var passowrd=$("#password").val();
			var rePassword=$("#rePassword").val();
			if(passowrd==null||passowrd==""){
				$("#error").html("新密码不能为空！");
				return false;
			}
			if(rePassword==null||rePassword==""){
				$("#error").html("再次输入新密码不能为空！");
				return false;
			}
			if(passowrd != rePassword) {
				$("#error").html("两次输入密码不一致！");
				return false;
			}
		});
	});

	</script>
	</head>
<body>
          <div class="container" >
    	<div class="row title1" >
    	    <div class=" col-md-4 ">
    	    	 <span id="span1">中国地质大学</span><br>
    	    	 <span id="span2">China University of Geosciences</span>
    	    </div>
    	    <div class="col-md-8">
    	    	<span id="span3">毕设选题系统</span>
    	    </div>
    	</div>
        <div class="form row"  >
            <form action="${pageContext.request.contextPath}/user_resetPsw.action" method="post" id="form1">
            <div class="form-horizontal col-md-offset-3" id="login_form">
                <h3 class="form-title">Reset Password</h3>
                <div class="col-md-9">
                    <div class="form-group">
                            <i class="fa fa-lock fa-lg"></i>
                            <input class="form-control " type="password" placeholder="请输入新密码" id="password" name="user.password" maxlength="20" value="${user.password}"/>
                    </div>
                      <div class="form-group">
                            <i class="fa fa-lock fa-lg"></i>
                            <input class="form-control " type="password" placeholder="请再次输入新密码" id="rePassword"  maxlength="20" />
                    </div>
                    <div>
                    	<input type="hidden" name="validateCode" value="${validateCode}">
                    </div>
                    <div class="form-group col-md-offset-8" >
                    	<span id="error">${error}</span>
                        <button type="submit" class="btn btn-success pull-right" name="submit">确认</button>
                    </div>
                </div>
            </div> 
            </form>
        </div>
        <div class="row form2">
        	       	
        </div>
    </div>
</body>
</html>