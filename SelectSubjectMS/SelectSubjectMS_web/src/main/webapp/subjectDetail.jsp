<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目信息</title>
     <style type="text/css">
     		#subject{
     		        border-collapse:collapse;
     		        width: 80%;
     		        margin-left: 10%;
     		}
     		#subject td,th{
     				font-size:1em;
					border:1px solid #98bf21;
					padding:3px 7px 2px 7px;
     		}
     		#subject td{
     				width: 50%;
     				height:50px;
     				text-align: center;
     		}
     </style>
</head>
<body>
       <h3 align="center">${currentSubject.title}</h3>
       <table id="subject">
           <tr>
              <td>标题</td>
              <td>${currentSubject.title}</td>
           </tr>
            <tr>
              <td>出题人</td>
              <td>${currentSubject.teacher.realName}</td>
           </tr>
            <tr>
              <td>方向</td>
              <td>${currentSubject.direction}</td>
           </tr>
            <tr>
              <td>关键字</td>
              <td>${currentSubject.keyWords}</td>
           </tr>
            <tr>
              <td>简要描述</td>
              <td>${currentSubject.summary}</td>
           </tr>
            <tr>
              <td>进度安排</td>
              <td>${currentSubject.schedule}</td>
           </tr>
            <tr>
              <td>关键字</td>
              <td>${currentSubject.keyWords}</td>
           </tr>
            <tr>
              <td>有关文档</td>
              <td>${currentSubject.referDoc}</td>
           </tr>
            <tr>
              <td>学生要求</td>
              <td>${currentSubject.studentRequire}</td>
           </tr>
            <tr>
              <td>出题时间</td>
              <td>${currentSubject.setTime}</td>
           </tr>
       </table>
</body>
</html>