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
         		 student = new Object();
               //条件查询
               function search(){
            	   $('#dg').datagrid('load',{
   	    			studentName:$("#s_studentName").val()
   	    		}); 
               }
               //设置为点击进入明细界面
               function titleDetail(value, row, index)
              {
                return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
              }
               //设置点击指派
               function assignLink(value, row,index) {
            	   value = "指派学生";
            	   return '<a href="javascript:openAssignDialog(\''+row.studentId+'\',\''+row.realName+'\')" style="font-size:15px;">'+value+'</a>';
               }
               function openAssignDialog(studentId, studentName){
            	 student.id = studentId;
            	 student.name = studentName;
            	  	$("#dg2").datagrid({
     	    			url:"${pageContext.request.contextPath}/deptHead_unChooseSubjectList.action",
     	    			rownumbers  : true,
     	    			 pagination : true,
     	    			pageList : [100,50,20,10,5]
     	    		});  
            	 $("#dlg").dialog("open").dialog("setTitle","指派学生:"+student.name);
               }
               
               function assign(){
            	 var selectedRows=$("#dg2").datagrid('getSelections');
   	        	if(selectedRows.length==0){
   	        		$.messager.alert("系统提示","请选择你要指派给<font color=red>"+student.name+"</font>的题目");
   	        		return;
   	        	}
   	        	if(selectedRows.length>1){
   	        		$.messager.alert("系统提示","请只选择一个你要指派给<font color=red>"+student.name+"</font>的题目");
   	        		return;
   	        	}
   	         	$.messager.confirm("系统提示","您确定要把题目<font color=blue>"+selectedRows[0].title+"</font>指派给<font color=red>"+student.name+"</font>吗？",function(r){
   	        		if(r){
   	        			$.post(
   	        		 	  "${pageContext.request.contextPath}/deptHead_assign.action",
   	        				{"studentId":student.id,"subjectId":selectedRows[0].subjectId},
   	        				function(result){
   	        					alert(result.num);
   	        					$.messager.alert("系统提示","落选学生<font color=red>"+student.name+"</font>已指派题目！");
   	        					$("#dlg").dialog("close");
   	        					$("#dg").datagrid("reload"); 
   	        			},"Json"); 
   	        		}
   	        	});          	
               }
         </script>
              <style type="text/css">
	    	.panel-title{
	    		font-size: 18px;
	    		padding: 6px;
	    	}
	    	.datagrid-header .datagrid-cell span{
	    		font-size:18px;
	    		padding: 6px;
	    		text-align: center;
	    	}
	    	.datagrid-cell {
	    		text-align: center;
	    		font-size:15px;
	    		padding:4px;
	    		padding-left: 8px;
	    		padding-right: 8px;
	    	}
	    	.bspan{
	 			font-size: 15px;
	 		}
	    </style>
</head>
<body>
         	<table class="easyui-datagrid" title="落选学生" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_unAssignStudentList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                   	<th field="cb" checkbox="true"></th>
		                    <th field="studentId"  hidden="true">编号</th>
		                    <th field="majorId"  hidden="true">专业编号</th>
		                    <th field="majorName" >专业名称</th>
		                    <th field="classId"  hidden="true">班级编号</th>
		                    <th field="className" >所属班级</th>
		                    <th field="realName">姓名</th>
		                    <th field="userName">账号</th>
		                    <th field="sex" >性别</th>
		                    <th field="phone">电话号码</th>
		                    <th field="email">电子邮箱</th> 
		                    <th field="assign" formatter="assignLink">指派题目</th>          	               
		               </tr>
		           </thead>
		     </table>
		     
		<div id="tb">
	          <div style="padding: 3px;">
	         	 	&nbsp;<span class="bspan">学生姓名：</span>&nbsp;<input type="text" name="s_studentName" id="s_studentName" style="width: 100px">
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog"  closed="true"  buttons="#dlg-button" style="width: 800px;height: 400px">
	        	  <table class="easyui-datagrid" id="dg2" title="落选题目">
	        	  		           <thead>
		                   	 <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="subjectId" hidden="true">编号</th>
		                    <th field="teacherName" >出题人</th>
		                    <th field="title"  formatter=titleDetail>标题</th>
		                    <th field="direction" >题目方向</th>         	               
		               </tr>         	               
		           </thead>
		     		</table>
	     </div>
	     
	       <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:assign()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>确认指派</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
</body>
</html>