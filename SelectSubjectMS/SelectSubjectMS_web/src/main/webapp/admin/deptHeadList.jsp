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
	   	 	//当打开对话框时，异步动态初始化学院
    			$("#s_yard").combobox({
    				url:"${pageContext.request.contextPath}/admin_yardComboBox.action",
    				 valueField:'yardId',    
    				  textField:'yardName',
    				  editable:false
    			});
	   	    });
	   	</script>
	    <script type="text/javascript">
	    	var url; //便于改变地址
	    	//打开添加系主任对话框
	    	function openAddDialog(){
	    		$("#userName").attr("disabled",false);
	    		resetValue();
	    		//当打开对话框时，异步动态初始化学院
	    			$("#cbox").combobox({
	    				url:"${pageContext.request.contextPath}/admin_yardComboBox.action",
	    				 valueField:'yardId',    
	    				  textField:'yardName',
	    				  editable:false
	    			});
	    		$("#dlg").dialog("open").dialog("setTitle","添加系主任");
	    		url="${pageContext.request.contextPath}/admin_addDeptHead.action";
	    	}
	   	//打开修改系主任对话框
	    function openModifyDialog(){
	   		$("#userName").attr("disabled",true);
	    	var selectedRows=$("#dg").datagrid('getSelections');
	    	if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择你要修改的系主任");
	    		return;
	    	}
	    	if(selectedRows.length>1){
	    		$.messager.alert("系统提示","请选择一条系主任信息进行修改");
	    		return;
	    	}
	    	$("#userName").val(selectedRows[0].userName);
	    	$("#password").val(selectedRows[0].password);
	    	$("#realName").val(selectedRows[0].realName);
	    	$(':radio[name="deptHead.sex"]').attr("checked",selectedRows[0].sex);
	    	$("#phone").val(selectedRows[0].phone);
	    	$("#email").val(selectedRows[0].email);
	    	$("#cbox").combobox({
				url:"${pageContext.request.contextPath}/admin_yardComboBox.action",
				 valueField:'yardId',    
				  textField:'yardName',
				  editable:false
				 
			});
	    	$("#cbox").combobox("setValue",selectedRows[0].yardId);
	    	$("#dlg").dialog("open").dialog("setTitle","修改系主任");
    		url="${pageContext.request.contextPath}/admin_updateDeptHead.action?deptHead.id="+selectedRows[0].deptHeadId;
	    }
	  	//添加或修改系主任
  		 function addOrSaveDeptHead(){
  			$("#fm").form("submit",{
  				url:url,
  				onSubmit:function(){
  					if($("#cbox").combobox('getValue')=='0'){
  						$.messager.alert("系统提示","请先选择学院！");
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
  						$.messager.alert("系统提示","该系主任已存在,无法添加！");
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
  		//删除院系
 		function deleteDeptHead(){
 			var selectedRows=$("#dg").datagrid('getSelections');
	        	if(selectedRows.length==0){
	        		$.messager.alert("系统提示","请选择你需要删除的系");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<selectedRows.length;i++){
	        		ids.push(selectedRows[i].deptHeadId);
	        	}
	        	var strIds=ids.join(",");
	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
	        		if(r){
	        			$.post(
	        			  "${pageContext.request.contextPath}/admin_deleteDeptHead.action",
	        				{"delIds":strIds},
	        				function(result){
	        					$.messager.alert("系统提示","您已成功删除<font color=red>"+result+"</font>条数据");
	        					$("#dg").datagrid('reload');

	        			},"Json");
	        		}
	        	});          	
 		}
  		    //查询院系
  		    function search(){
  		    	$('#dg').datagrid('reload',{
	    			deptHeadName: $("#s_deptHeadName").val(),
	    			yardId:$("#s_yard").combobox("getValue")
	    		});
  		    }
  		    //导出系主任
  		    function exportDeptHeadList(){
  		    	url = "${pageContext.request.contextPath}/admin_exportDeptHeads.action";
  		    	window.open(url);
  		    }
  		    //打开导入对话框
  		    function openImportDeptHeadsDialog(){
  		    	$('#dlg2').dialog("open").dialog("setTitle","批量导入数据");
  		    }
  		    
  		 	 function downloadTemplate(){
  				window.open('${pageContext.request.contextPath}/admin/template/deptHeadExporTemplate.xls');
  			}
  		    //导入系主任
  		    function importDeptHeadList(){
  		    	$("#uploadForm").form("submit",{
  					success:function(result){
  						var result=$.parseJSON(result);
  						if(result.errorMsg){
  							$.messager.alert("系统提示",result.errorMsg);
  						}else{
  							$.messager.alert("系统提示","您已成功删除<font color=red>"+result.num+"</font>条数据");
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
	    		font-size: 15px;
	    	}
	    </style>
</head>
<body>
		<table class="easyui-datagrid" title="系主任信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/admin_deptHeadList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="deptHeadId"  hidden="true">编号</th>
		                    <th field="yardId" hidden="true">院系编号</th>
		                    <th field="yardName">院系名称</th>
		                    <th field="realName">姓名</th>
		                    <th field="userName">账号</th>
		                    <th field="password"  hidden="true" hidden="true">密码</th>
		                    <th field="sex">性别</th>
		                    <th field="phone">电话号码</th>
		                    <th field="email">电子邮箱</th>
		               </tr>
		           </thead>
		     </table>
		    
		      <div id="tb">
	         <div style="padding-top: 3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteDeptHead()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>
	             <a href="javascript:exportDeptHeadList()" class="easyui-linkbutton" iconCls="icon-export" plain="true" style="background-color:#E3EEFF"><span class="bspan">批量导出数据</span></a>
	             <a href="javascript:openImportDeptHeadsDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">用模版批量导入数据</span></a>       
	          </div>
	          <div style="padding-bottom: 3px;">
	         	 	&nbsp;<span class="bspan">系主任名称：</span>&nbsp;<input type="text" name="deptHeadName" id="s_deptHeadName" style="width: 100px">
	          		&nbsp;<span class="bspan">所属院系：</span>&nbsp;<select class="easyui-combobox" id="s_yard" style="width: 160px">
	          					<option value="0">请选择</option>
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:400px;height: 400px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 15px;">
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
                                  <td><input type="text" name="deptHead.realName"  class="easyui-validatebox" required="true" id="realName"></td>
                               </tr>
                                 <tr> 
                                  <td>性别:</td>
                                  <td><input type="radio" name="deptHead.sex"  class="easyui-validatebox" required="true" value="男" >男
                                  	<input type="radio" name="deptHead.sex"  class="easyui-validatebox" required="true" value="女" >女
                                  </td>
                               </tr>
                               	  <tr> 
                                  <td>电话号码:</td>
                                  <td><input type="text" name="deptHead.phone"  class="easyui-validatebox" id="phone"></td>
                              	 </tr>
                              	  <tr> 
                                  <td>电子邮箱:</td>
                                  <td><input type="text" name="deptHead.email"  class="easyui-validatebox" id="email"></td>
                              	 </tr>
                              	  <tr> 
                                  <td>所属院:</td>
                                  <td><select class="easyui-combobox" id="cbox"  style="width: 160px" name="yardId">
                                  		<option value="0">请选择</option>
                                  </select></td>
                              	 </tr> 	 
                        </table>	            
	            </form>
	     </div>
	     
	     <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addOrSaveDeptHead()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
	     
	     <div id="dlg2" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons2">
        <form id="uploadForm" action="${pageContext.request.contextPath}/admin_importDeptHeads.action" method="post" enctype="multipart/form-data">
        	<table>
        		<tr>
        			<td>下载模版：</td>
        			<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="downloadTemplate()">导入模版</a><span style="color: #AA2A59">点击下载导出模板</span></td>
        		</tr>
        		<tr>
        			<td>上传文件：</td>
        			<td><input type="file" name="deptHeadUploadFile"></td>
        		</tr>    
        	</table><br>
        	   <span style="color: #AA2A59">温馨提示：导入请先下载模板严格按照模板方式导入数据</span>
        </form>
	</div>
    
	<div id="dlg-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="importDeptHeadList()">上传</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">关闭</a>
	</div>
</body>
</html>