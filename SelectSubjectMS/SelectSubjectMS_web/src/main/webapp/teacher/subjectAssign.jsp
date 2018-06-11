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
         		var subjectId;
               //查看题目信息
               function titleDetail(value, row, index)
               {
                 return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
               }
               
               function openAssignDialog(){
            	   var selectedRows=$("#dg").datagrid('getSelections');
          	    	if(selectedRows.length==0){
          	    		$.messager.alert("系统提示","请选择你要指派学生的题目");
          	    		return;
          	    	}
          	    	if(selectedRows.length>1){
          	    		$.messager.alert("系统提示","请一次只选择一条题目信息进行指派");
          	    		return;
          	    	}
          	    	subjectId = selectedRows[0].subjectId;
          	    	$("#table1").html("<tr><td>学生姓名</td><td>学生志愿</td></tr>");
          	   	$.post(
	        			  "${pageContext.request.contextPath}/teacher_listStudentBySubjectId.action",
	        				{"subjectId":selectedRows[0].subjectId},
	        				function(result){
	        					$(".targetarea").html(selectedRows[0].studentName);
	        					for (var i=0; i<result.length; i++) {
	        						var sName = result[i].studentName;
	        						var sLevel = result[i].studentLevel;
	        						$("#table1").append("<tr><td><div class='dragitem'>"+sName+"</div></td><td>"+sLevel+"</td></tr>");							
	        					}
	        					$('.dragitem').draggable({
	        						revert:true,
	        						proxy:'clone'
	        					});
	        					$('.targetarea').droppable({
	        						accept:'.dragitem',
	        						onDrop:function(e,source){
	        							$(this).html($(source).html());
	        						}
	        					});
	        				},"Json"); 
            	$("#dlg").dialog("open").dialog("setTitle","选题指派");
   	    		url="${pageContext.request.contextPath}/teacher_subjectAssign.action";
               }
               
               function assignStudent() {
            	   var studentName = $(".targetarea").text();
             	   $.messager.confirm("系统提示","您确定要选定学生<font color=red>"+studentName+"</font>完成该毕设吗？",function(r){
      	        		if(r){
      	        			$.post(
      	        			  "${pageContext.request.contextPath}/teacher_assignStudent.action",
      	        				{"studentName":studentName,
      	        				  "subjectId":subjectId
      	        				},
      	        				function(result){
      	        					if (result.isExist == '1') {
      	        						$.messager.alert("系统提示","该学生已被指派,请选择其他学生进行指派！");
      	        					} else  {
      	        						if (result.flag == '0') {
          	        						$.messager.alert("系统提示","成功指派学生！");
          	        					} else {
          	        						$.messager.alert("系统提示","成功修改选题指派！");
          	        					}
      	        						$("#dlg").dialog("close");
          	        					$("#dg").datagrid('reload');
      	        					}	
      	        			},"Json");
      	        		}
      	        	});   
               }
              
               function closeDialog(){
            	   $("#dlg").dialog("close");
      			   $("#dg").datagrid("reload"); 
               }

         </script>
       <style type="text/css">
       #table1 tr td{
       		border-collapse:collapse;
       		text-align: center;
       		border: 1px solid red;
       		padding: 5px;
       		margin-bottom: 10px;
       }
      .proxy{
      		border:1px solid #ccc;
			width:80px;
			background:#fafafa;
      }
       .title{
       		text-align: center;
       		font-size: 15px;
       }
       .targetarea{
       		text-align: center;
       		font-size: 30px;
       		margin-top: 25%;
       		text-shadow: -1px -1px white,1px 1px #333;
            color:	#FF00FF;
            font-weight: bold;
       }
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
         	<table class="easyui-datagrid" title="个人选题" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/teacher_assignSubjectList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		            <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="subjectId" hidden="true">编号</th>
		                    <th field="title"  formatter=titleDetail>标题</th>
		                    <th field="selectNum" >选题人数</th>
		                    <th field="studentId"  hidden="true">学生编号</th>
		                    <th field="studentName" >已分配学生</th>    	               
		               </tr>
		           </thead>
		     </table>
		     
		 <div id="tb">
		       
		       	<div style="margin-left: 30px;padding: 3px ">       
	             <a href="javascript:openAssignDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">选题指派</span></a> 
	           </div>
	     </div>
	     
	<div id="dlg" class="easyui-dialog" fit=true  closed="true" buttons="#dlg-button">
	            <h2 align="center">选题指派</h2>
	<p align="center">将选定的学生拖拽到右边方框提交即可完成指派操作</p>
	
			<div style="float:left;width: 25%;margin-left: 20%;height: 250px;border: 5px solid orange;">
				<div class="title">学生志愿</div>
				<table style="border-collapse: separate;width: 100%" id="table1">
				</table>
			</div>
	
			<div style="float:left;width: 25%;margin-left: 55%;height: 220px;position: absolute;border: 5px solid orange;">
				<div class="title">老师指派</div>
				<div class="easyui-droppable targetarea" style="width: 100%;height:60%;" 
				data-options="
					accept: '.dragitem',
					onDragEnter:function(e,source){
						$(this).html('enter');
					},
					onDragLeave: function(e,source){
						$(this).html('leave');
					},
					onDrop: function(e,source){
						$(this).html($(source).html() + ' dropped');
					}
				">
		</div>
			</div>
			
			</div>
	      
	     <div id="dlg-button">	        
	             <table style="border-collapse:collapse;margin-left: 20%;width: 60%;">
	     				<tr>
	     					<td ><a href="javascript:assignStudent()"  class="easyui-linkbutton" iconcls="icon-ok" >保存</a></td>   							
	     					<td style="float: left;margin-left: 20%"><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel">关闭</a></td>
	     				</tr>
	     		</table>             
	     </div>
</body>
</html>