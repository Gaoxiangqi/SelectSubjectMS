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
	    		resetValue();
	    		$("#majorId").combobox({
    		 		url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
    				valueField:'majorId', 
    	            textField:'majorName', 
    	            panelHeight:'auto',   
    	            editable:false
	    		});
	    		$("#dlg").dialog("open").dialog("setTitle","添加班级");
	    		url="${pageContext.request.contextPath}/deptHead_addClass.action";
	    	}
	   	//打开修改班级对话框
	    function openModifyDialog(){
	    	var selectedRows=$("#dg").datagrid('getSelections');
	    	if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择你要修改的班级信息");
	    		return;
	    	}
	    	if(selectedRows.length>1){
	    		$.messager.alert("系统提示","请选择一条班级信息进行修改");
	    		return;
	    	}
	    	$("#majorId").combobox({
		 		url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
				valueField:'majorId', 
	            textField:'majorName', 
	            panelHeight:'auto',   
	            editable:false
    		});
	    	$("#majorId").combobox('setValue',selectedRows[0].majorId);
	    	$("#className").val(selectedRows[0].className);
	    	$("#dlg").dialog("open").dialog("setTitle","修改班级");
    		url="${pageContext.request.contextPath}/deptHead_updateClass.action?classId="+selectedRows[0].classId;
	    }
	  	//添加或修改班级
  		 function addOrSaveClass(){
  			$("#fm").form("submit",{
  				url:url,
  				onSubmit:function(){
  						if($("#majorId").combobox("getValue")=='0'){
  							$.messager.alert("系统提示","请选择班级的专业信息");
  							return false;
  						}
  					else  return $(this).form("validate");
  				},
  				success:function(result){
  				 	var r=$.parseJSON(result);
  				 	if(r.type=='add'){
	  					if(r.num=='0'){
	  						$.messager.alert("系统提示","该班级已存在,无法添加！");
	  					} else {
			  					$.messager.alert("系统提示","保存成功");
			      				resetValue();
			      				$("#dlg").dialog("close");
			      				$("#dg").datagrid("reload"); 
  								} 
  				 	}
  				 	if(r.type=='update') {
  				 		if(r.num=='0'){
	  						$.messager.alert("系统提示","修改后的班级已存在,您无法修改为该班级！");
	  					} else {
	  						$.messager.alert("系统提示","修改成功");
	  	      				$("#dlg").dialog("close");
	  	      				$("#dg").datagrid("reload");
	  					}
  				 		 
  				 	}
  				}
  			});
  		}
  		//删除班级
 		function deleteClass(){
 			var selectedRows=$("#dg").datagrid('getSelections');
	        	if(selectedRows.length==0){
	        		$.messager.alert("系统提示","请选择你需要删除的班级");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<selectedRows.length;i++){
	        		ids.push(selectedRows[i].classId);
	        	}
	        	var strIds=ids.join(",");
	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
	        		if(r){
	        			$.post(
	        			  "${pageContext.request.contextPath}/deptHead_deleteClass.action",
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
	    			className: $("#s_className").val(),
	    			majorId: $("#s_major").combobox('getValue')
	    		});
  		    }
  		   	    
   		//关闭对话框，将文本内容清除
 		function closeDialog(){
 			resetValue();
 			$("#dlg").dialog("close");
 		}
 		//重置对话框
 		function resetValue(){
 			$("#majorId").combobox('setValue', '0');
 			$("#className").val('');
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
		<table class="easyui-datagrid" title="班级信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_classList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="classId"  hidden="true">编号</th>
		                    <th field="className" >班级名称</th>
		                    <th field="majorId" hidden="true">专业编号</th>
		                    <th field="majorName" >专业名称</th>
		                    <th field="studentNum" >班级人数</th>
		               </tr>
		           </thead>
		     </table>
		    
		      <div id="tb">
	         <div style="padding-top:3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteClass()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>
	          </div>
	          <div style="padding-bottom:3px;">
	         	 	&nbsp;<span class="bspan">班级名称：</span>&nbsp;<input type="text" name="className" id="s_className" style="width: 100px">
	          		&nbsp;&nbsp;&nbsp;
	          		&nbsp;<span class="bspan">所属专业：</span>&nbsp;<select class="easyui-combobox" id="s_major" style="width: 120px" data-options="
	          					url:'${pageContext.request.contextPath}/deptHead_majorComboBox.action',
	          					valueField:'majorId',    
	    				  		textField:'majorName',
	    				  		editable:false" panelHeight="auto">
	    				  		<option value="0">请选择</option>
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:300px;height: 400px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 30px;">
                               <tr> 
                                  <td>所属专业:</td>
                                  <td><select class="easyui-combobox" id="majorId" style="width: 150px" name="majorId">
	    				  					<option value="0">请选择</option>
	          						 </select></td>
                               </tr>
                               <tr> 
                                  <td>班级名称:</td>
                                  <td><input type="text" name="className"  class="easyui-validatebox" required="true" id="className"></td>
                               </tr>
                              	 <tr>
                              	 	<td colspan="2" style="color: #850B4B;margin-top: 30px;">
                              	 		温馨提示：您当前添加的班级均默认且只能是您当前院系下的班级
                              	 	</td>
                              	 </tr>	 
                        </table>	            
	            </form>
	     </div>
	     
	     <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addOrSaveClass()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
</body>
</html>