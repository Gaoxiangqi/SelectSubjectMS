<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/icon.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/demo/demo.css">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>	    
	    <script type="text/javascript">
	    	var url; //便于改变地址
	    	//打开添加教师对话框
	    	function openAddDialog(){
	    		$("#userName").attr("disabled",false);
	    		resetValue();
	    		$("#dlg").dialog("open").dialog("setTitle","添加教师");
	    		url="${pageContext.request.contextPath}/deptHead_addTeacher.action";
	    	}
	   	//打开修改老师对话框
	    function openModifyDialog(){
	   		$("#userName").attr("disabled",true);
	    	var selectedRows=$("#dg").datagrid('getSelections');
	    	if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择你要修改的教师");
	    		return;
	    	}
	    	if(selectedRows.length>1){
	    		$.messager.alert("系统提示","请选择一条教师信息进行修改");
	    		return;
	    	}
	    	$("#userName").val(selectedRows[0].userName);
	    	$("#password").val(selectedRows[0].password);
	    	$("#realName").val(selectedRows[0].realName);
	    	$(':radio[name="teacher.sex"]').attr("checked",selectedRows[0].sex);
	    	$("#phone").val(selectedRows[0].phone);
	    	$("#email").val(selectedRows[0].email);
	    	$("#dlg").dialog("open").dialog("setTitle","修改教师");
    		url="${pageContext.request.contextPath}/deptHead_updateTeacher.action?teacher.id="+selectedRows[0].teacherId;
	    }
	  	//添加或修改教师
  		 function addOrSaveTeacher(){
  			$("#fm").form("submit",{
  				url:url,
  				onSubmit:function(){
  						if(!isPhone($("#phone").val())){
  							$.messager.alert("系统提示","请输入正确的电话！");
  							return false;
  						}
  						if(!isEmail($("#email").val())){
  							$.messager.alert("系统提示","请输入正确的邮箱！");
  							return false;
  						}
  						
  					else  return $(this).form("validate");
  				},
  				success:function(result){
  				 	var r=$.parseJSON(result);
  				 	if(r.type=='add'){
	  					if(r.num=='0'){
	  						$.messager.alert("系统提示","该教师已存在,无法添加！");
	  					}
	  					else{
	  					$.messager.alert("系统提示","保存成功");
	      				resetValue();
	      				$("#dlg").dialog("close");
	      				$("#dg").datagrid("reload"); 
	  					} 
  				 	}
  				 	if(r.type=='update') {
  				 		$.messager.alert("系统提示","修改成功");
  	      				resetValue();
  	      				$("#dlg").dialog("close");
  	      				$("#dg").datagrid("reload"); 
  				 	}
  				}
  			});
  		}
  		//删除老师
 		function deleteTeacher(){
 			var selectedRows=$("#dg").datagrid('getSelections');
	        	if(selectedRows.length==0){
	        		$.messager.alert("系统提示","请选择你需要删除的教师");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<selectedRows.length;i++){
	        		ids.push(selectedRows[i].teacherId);
	        	}
	        	var strIds=ids.join(",");
	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
	        		if(r){
	        			$.post(
	        			  "${pageContext.request.contextPath}/deptHead_deleteTeacher.action",
	        				{"delIds":strIds},
	        				function(result){
	        					$.messager.alert("系统提示","您已成功删除<font color=red>"+result+"</font>条数据");
	        					$("#dg").datagrid('reload');

	        			},"Json");
	        		}
	        	});          	
 		}
  		    //查询
  		    function search(){
  		    	$('#dg').datagrid('load',{
	    			teacherName: $("#s_teacherName").val()
	    		});
  		    }
  		    //导出教师信息
  		    function exportTeacherList(){
  		    	url = "${pageContext.request.contextPath}/deptHead_exportTeachers.action";
  		    	window.open(url);
  		    }
  		    //打开导入对话框
  		    function openImportTeachersDialog(){
  		    	$('#dlg2').dialog("open").dialog("setTitle","批量导入数据");
  		    }
  		    
  		 	 function downloadTemplate(){
  				window.open('${pageContext.request.contextPath}/deptHead/template/teacherImportTemplate.xls');
  			}
  		    //导入系主任
  		    function importTeacherList(){
  		    	$("#uploadForm").form("submit",{
  					success:function(result){
  						var result=$.parseJSON(result);
  						if(result.errorMsg){
  							$.messager.alert("系统提示",result.errorMsg);
  						}else{
  							$.messager.alert("系统提示","您已成功导入<font color=red>"+result.num+"</font>条老师数据");
  							$("#dlg2").dialog("close");
  							$("#dg").datagrid("reload");
  						}
  					}
  				});
  		    }
  		    
   		//关闭对话框，将文本内容清除
 		function closeDialog(){
 			resetValue();
 			$("#dlg").dialog("close");
 		}
 		//重置对话框
 		function resetValue(){
 			$("#userName").val("");
 			$("#password").val("");
 			$("#realName").val("");
 			$("#phone").val("");
 			$("#email").val("");
 			$("#cbox2").combobox("clear");
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
	    		font-size:15px;
	    	}
	    </style>
</head>
<body>
		<table class="easyui-datagrid" title="老师信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_teacherList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="teacherId" hidden="true">编号</th>
		                    <th field="yardId" hidden="true">学院编号</th>
		                    <th field="yardName">所属学院</th>
		                    <th field="realName">姓名</th>
		                    <th field="userName">账号</th>
		                    <th field="password"  hidden="true" hidden="true">密码</th>
		                    <th field="sex">性别</th>
		                    <th field="phone" >电话号码</th>
		                    <th field="email">电子邮箱</th>
		                    <th field="setNum">已出题数</th>
		                    <th field="maxSetNum">最大题数</th>
		               </tr>
		           </thead>
		     </table>
		    
		   <div id="tb">
	         <div style="padding-top:3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteTeacher()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan" >删除</span></a>
	             <a href="javascript:exportTeacherList()" class="easyui-linkbutton" iconCls="icon-export" plain="true" style="background-color:#E3EEFF"><span class="bspan">批量导出数据</span></a>
	             <a href="javascript:openImportTeachersDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">用模版批量导入数据</span></a>       
	          </div>
	          <div style="padding-bottom:3px;">
	         	 	&nbsp;<span class="bspan">教师名称：</span>&nbsp;<input type="text" name="teacherName" id="s_teacherName" style="width: 100px">
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:300px;height: 400px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 10px;">
                               <tr> 
                                  <td>账号:</td>
                                  <td><input type="text" name="user.userName"  class="easyui-validatebox" required="true" id="userName"></td>
                               </tr>
                               <tr> 
                                  <td>密码:</td>
                                  <td><input type="password" name="user.password"  class="easyui-validatebox" required="true" id="password"></td>
                               </tr>
                               <tr> 
                                  <td>姓名:</td>
                                  <td><input type="text" name="teacher.realName"  class="easyui-validatebox" required="true" id="realName"></td>
                               </tr>
                                 <tr> 
                                  <td>性别:</td>
                                  <td><input type="radio" name="teacher.sex"  class="easyui-validatebox" required="true" value="男" >男
                                  	<input type="radio" name="teacher.sex"  class="easyui-validatebox" required="true" value="女" >女
                                  </td>
                               </tr>
                               	  <tr> 
                                  <td>电话号码:</td>
                                  <td><input type="text" name="teacher.phone"  class="easyui-validatebox" id="phone"></td>
                              	 </tr>
                              	  <tr> 
                                  <td>电子邮箱:</td>
                                  <td><input type="text" name="teacher.email"  class="easyui-validatebox" id="email"></td>
                              	 </tr> 
                              	 <tr>
                              	 	<td colspan="2" style="color: #850B4B;margin-top: 30px;">
                              	 		温馨提示：您当前添加的教师均默认且只能是您当前院系下的教师
                              	 	</td>
                              	 </tr>	 
                        </table>	            
	            </form>
	     </div>
	     
	     <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addOrSaveTeacher()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
	     
	     <div id="dlg2" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons2">
        <form id="uploadForm" action="${pageContext.request.contextPath}/deptHead_importTeachers.action" method="post" enctype="multipart/form-data">
        	<table>
        		<tr>
        			<td>下载模版：</td>
        			<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="downloadTemplate()">导入模版</a><span style="color: #AA2A59">点击下载导出模板</span></td>
        		</tr>
        		<tr>
        			<td>上传文件：</td>
        			<td><input type="file" name="teacherUploadFile"></td>
        		</tr>    
        	</table><br>
        	   <span style="color: #AA2A59">温馨提示：导入请先下载模板严格按照模板方式导入数据</span>
        </form>
	</div>
    
	<div id="dlg-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="importTeacherList()">上传</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">关闭</a>
	</div>
</body>
</html>