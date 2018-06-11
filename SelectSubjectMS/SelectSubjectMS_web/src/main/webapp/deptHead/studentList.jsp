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
	    		 $(function(){
	    		 	$("#s_major").combobox({
	    		 		url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
	    				valueField:'majorId', 
	    	            textField:'majorName', 
	    	            panelHeight:'auto',   
	    	            editable:false, 
	    	            onSelect:function(){  
	    	                $('#s_class').combobox('setValue','0');
	    	                var majorId = $("#s_major").combobox('getValue');
	    	                if (majorId == '0') {
	    	                	$('#s_class').combobox('disable','true');
	    	                } else {
	    	                	 $('#s_class').combobox({	  
	 		    	               	url:"${pageContext.request.contextPath}/deptHead_classCombobox.action?majorId="+majorId,  
	 		    	                valueField:'classId', //值字段  
	 		    	                textField:'className', //显示的字段  
	 		    	                panelHeight:'auto',  
	 		    	                editable:false//不可编辑，只能选择   
	 	    					});
	    	                }
	    				}
	    			}); 
	    		 });
	    </script>
	    <script type="text/javascript">
	    	var url; //便于改变地址
	    	//打开添加学生对话框
	    	function openAddDialog(){
	    		$("#userName").attr("disabled",false);
	    		resetValue();
	    		//系班级二级联动
	    		 	$("#majorId").combobox({
	    		 		url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
	    				valueField:'majorId', 
	    	            textField:'majorName', 
	    	            panelHeight:'auto',   
	    	            editable:false, 
	    	            onSelect:function(){  
	    	                $('#classId').combobox('setValue','0');
	    	                var majorId = $("#majorId").combobox('getValue');
	    	                if (majorId == '0') {
	    	                	$('#classId').combobox('disable','true');
	    	                } else {
	    	                	 $('#classId').combobox({	  
	 		    	               	url:"${pageContext.request.contextPath}/deptHead_classCombobox.action?majorId="+majorId,  
	 		    	                valueField:'classId', //值字段  
	 		    	                textField:'className', //显示的字段  
	 		    	                panelHeight:'auto',  
	 		    	                editable:false//不可编辑，只能选择   
	 	    					});
	    	                }
	    				}
	    			});
	    		$("#dlg").dialog("open").dialog("setTitle","添加学生");
	    		url="${pageContext.request.contextPath}/deptHead_addStudent.action";
	    	}
	   	//打开修改老师对话框
	    function openModifyDialog(){
	   		$("#userName").attr("disabled",true);
	    	var selectedRows=$("#dg").datagrid('getSelections');
	    	if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择你要修改的学生");
	    		return;
	    	}
	    	if(selectedRows.length>1){
	    		$.messager.alert("系统提示","请选择一条学生信息进行修改");
	    		return;
	    	}
	    	//系班级二级联动
		 	$("#majorId").combobox({
		 		url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
				valueField:'majorId', 
	            textField:'majorName', 
	            panelHeight:'auto',   
	            editable:false, 
	            onSelect:function(){  
	                $('#classId').combobox('setValue','0');
	                var majorId = $("#majorId").combobox('getValue');
	                if (majorId == '0') {
	                	$('#classId').combobox('disable','true');
	                } else {
	                	 $('#classId').combobox({	  
		    	               	url:"${pageContext.request.contextPath}/deptHead_classCombobox.action?majorId="+majorId,  
		    	                valueField:'classId', //值字段  
		    	                textField:'className', //显示的字段  
		    	                panelHeight:'auto',  
		    	                editable:false//不可编辑，只能选择   
	    					});
	                }
				}
			});
	    	$("#userName").val(selectedRows[0].userName);
	    	$("#password").val(selectedRows[0].password);
	    	$("#realName").val(selectedRows[0].realName);
	    	$(':radio[name="teacher.sex"]').attr("checked",selectedRows[0].sex);
	    	$("#majorId").combobox('setValue',selectedRows[0].majorId);
	    	 $('#classId').combobox({	  
	               	url:"${pageContext.request.contextPath}/deptHead_classCombobox.action?majorId="+selectedRows[0].majorId,  
	                valueField:'classId', //值字段  
	                textField:'className', //显示的字段  
	                panelHeight:'auto',  
	                editable:false//不可编辑，只能选择   
				});
	    	$("#classId").combobox('setValue',selectedRows[0].classId);
	    	$("#phone").val(selectedRows[0].phone);
	    	$("#email").val(selectedRows[0].email);
	    	$("#graduateYear").val(selectedRows[0].graduateYear);
	    	$("#dlg").dialog("open").dialog("setTitle","修改学生");
    		url="${pageContext.request.contextPath}/deptHead_updateStudent.action?student.id="+selectedRows[0].studentId;
	    }
	  	//添加或修改学生
  		 function addOrSaveStudent(){
  			$("#fm").form("submit",{
  				url:url,
  				onSubmit:function(){
  						if ($("#majorId").combobox('getValue') == '0') {
							$.messager.alert("系统提示","请选择班级信息！");
							return false;
						}
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
  						$.messager.alert("系统提示","该学生已存在,无法添加！");
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
 		function deleteStudent(){
 			var selectedRows=$("#dg").datagrid('getSelections');
	        	if(selectedRows.length==0){
	        		$.messager.alert("系统提示","请选择你需要删除的学生");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<selectedRows.length;i++){
	        		ids.push(selectedRows[i].studentId);
	        	}
	        	var strIds=ids.join(",");
	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
	        		if(r){
	        			$.post(
	        			  "${pageContext.request.contextPath}/deptHead_deleteStudent.action",
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
  		    		studentName: $("#s_studentName").val(),
  		    		majorId:$("#s_major").combobox('getValue'),
  		    		classId:$("#s_class").combobox('getValue')
	    		});
  		    }
  		    //导出学生信息
  		    function exportStudentList(){
  		    	url = "${pageContext.request.contextPath}/deptHead_exportStudents.action";
  		    	window.open(url);
  		    }
  		    //打开导入对话框
  		    function openImportTeachersDialog(){
  		    	$('#dlg2').dialog("open").dialog("setTitle","批量导入数据");
  		    }
  		    
  		 	 function downloadTemplate(){
  				window.open('${pageContext.request.contextPath}/deptHead/template/studentImportTemplate.xls');
  			}
  		    //导入系主任
  		    function importTeacherList(){
  		    	$("#uploadForm").form("submit",{
  					success:function(result){
  						var result=$.parseJSON(result);
  						if(result.errorMsg){
  							$.messager.alert("系统提示",result.errorMsg);
  						}else{
  							$.messager.alert("系统提示","您已成功导入<font color=red>"+result.num+"</font>条学生数据");
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
 			$("#majorId").combobox('setValue','0');
 			$("#classId").combobox('setValue','0');
 			$("#phone").val("");
 			$("#email").val("");
 			$("#graduateYear").val("");
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
	    		font-size: 15px;
	    	}
	    </style>
</head>
<body>
		<table class="easyui-datagrid" title="学生信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_studentList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="studentId"  hidden="true">编号</th>
		                    <th field="yardName" >所属学院</th>
		                    <th field="majorId"  hidden="true">专业编号</th>
		                    <th field="majorName" >专业名称</th>
		                    <th field="classId"  hidden="true">班级编号</th>
		                    <th field="className" >所属班级</th>
		                    <th field="realName">姓名</th>
		                    <th field="userName">账号</th>
		                    <th field="password" hidden="true">密码</th>
		                    <th field="sex" >性别</th>
		                    <th field="phone">电话号码</th>
		                    <th field="email">电子邮箱</th>
		                    <th field="selectSubjectNum">已选题数</th>
		                    <th field="graduateYear" hidden="true">已选题数</th>
		               </tr>
		           </thead>
		     </table>
		    
		      <div id="tb">
	         <div style="padding-top:3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteStudent()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>
	             <a href="javascript:exportStudentList()" class="easyui-linkbutton" iconCls="icon-export" plain="true" style="background-color:#E3EEFF"><span class="bspan">批量导出数据</span></a>
	             <a href="javascript:openImportTeachersDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">用模版批量导入数据</span></a>       
	          </div>
	          <div style="padding-bottom:3px;">
	         	 	&nbsp;<span class="bspan">学生姓名：</span>&nbsp;<input type="text" name="studentName" id="s_studentName" style="width: 100px">
	          		&nbsp;&nbsp;&nbsp;
	          		&nbsp;<span class="bspan">所属专业：</span>&nbsp;<select class="easyui-combobox" id="s_major" style="width: 150px">
	    				  		<option value="0">请选择</option>
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	          		&nbsp;<span class="bspan">所属班级：</span>&nbsp;<select class="easyui-combobox" id="s_class" style="width: 100px">
	    				  		<option value="0">请选择</option>
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:350px;height: 400px;padding: 20px 20px" closed="true" 
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
                                  <td><input type="text" name="student.realName"  class="easyui-validatebox" required="true" id="realName"></td>
                               </tr>
                                 <tr> 
                                  <td>性别:</td>
                                  <td><input type="radio" name="student.sex"  class="easyui-validatebox" required="true" value="男" >男
                                  	<input type="radio" name="student.sex"  class="easyui-validatebox" required="true" value="女" >女
                                  </td>
                               </tr>
                               <tr> 
                                  <td>所属专业:</td>
                                  <td><select class="easyui-combobox" id="majorId" style="width: 150px">
	    				  					<option value="0">请选择</option>
	          						 </select></td>
                              	 </tr>
                              	 <tr> 
                                  <td>所属班级:</td>
                                  <td><select class="easyui-combobox" id="classId" style="width: 150px" name="classId">
	    				  					<option value="0">请选择</option>
	          						 </select></td>
                              	 </tr>
                               	  <tr> 
                                  <td>电话号码:</td>
                                  <td><input type="text" name="student.phone"  class="easyui-validatebox" id="phone"></td>
                              	 </tr>
                              	  <tr> 
                                  <td>电子邮箱:</td>
                                  <td><input type="text" name="student.email"  class="easyui-validatebox" id="email"></td>
                              	 </tr>
                              	 <tr> 
                                  <td>毕业年份:</td>
                                  <td><input type="text" name="student.graduateYear"  class="easyui-validatebox" id="graduateYear" required="true"></td>
                              	 </tr>
                              	  <tr>
                              	 	<td colspan="2" style="color: red;margin-top: 30px;">
                              	 		注意：您当前添加的学生请务必设置毕业年份，否则该学生无法登录选题！
                              	 	</td>
                              	 </tr>	
                              	 <tr>
                              	 	<td colspan="2" style="color: #850B4B;">
                              	 		温馨提示：您当前添加的学生均默认且只能是您当前院系下的学生
                              	 	</td>
                              	 </tr>	 
                        </table>	            
	            </form>
	     </div>
	     
	     <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addOrSaveStudent()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
	     
	     <div id="dlg2" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons2">
        <form id="uploadForm" action="${pageContext.request.contextPath}/deptHead_importStudents.action" method="post" enctype="multipart/form-data">
        	<table>
        		<tr>
        			<td>下载模版：</td>
        			<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="downloadTemplate()">导入模版</a><span style="color: #AA2A59">点击下载导出模板</span></td>
        		</tr>
        		<tr>
        			<td>上传文件：</td>
        			<td><input type="file" name="studentUploadFile"></td>
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