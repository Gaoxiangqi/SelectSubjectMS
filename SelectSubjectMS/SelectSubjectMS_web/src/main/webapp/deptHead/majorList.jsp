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
	    	//打开添加专业对话框
	    	function openAddDialog(){
	    		resetValue();
	    		$("#dlg").dialog("open").dialog("setTitle","添加专业");
	    		url="${pageContext.request.contextPath}/deptHead_addMajor.action";
	    	}
	   	//打开修改专业对话框
	    function openModifyDialog(){
	    	var selectedRows=$("#dg").datagrid('getSelections');
	    	if(selectedRows.length==0){
	    		$.messager.alert("系统提示","请选择你要修改的专业");
	    		return;
	    	}
	    	if(selectedRows.length>1){
	    		$.messager.alert("系统提示","请选择一条专业信息进行修改");
	    		return;
	    	}
	    	$("#majorName").val(selectedRows[0].majorName);
	    	$("#majorDesc").val(selectedRows[0].majorDesc);
	    	$("#dlg").dialog("open").dialog("setTitle","修改专业");
    		url="${pageContext.request.contextPath}/deptHead_updateMajor.action?major.id="+selectedRows[0].majorId;
	    }
	  	//添加或修改专业
  		 function addOrSaveMajor(){
  			$("#fm").form("submit",{
  				url:url,
  				onSubmit:function(){
  				  return $(this).form("validate");
  				},
  				success:function(result){
  				 	var r=$.parseJSON(result);
  				 	if(r.type=='add'){
  					if(r.num=='0'){
  						$.messager.alert("系统提示","该专业已存在,无法添加！");
  					}
  					else{
  					$.messager.alert("系统提示","保存成功");
      				resetValue();
      				$("#dlg").dialog("close");
      				$("#dg").datagrid("reload"); 
  					} 
  				 	}
  				 	if(r.type=='update') {
  				 		if (r.num == '0') {
  				 			$.messager.alert("系统提示","该专业已存在,无法修改为该专业！");
  				 		} else {
  				 			$.messager.alert("系统提示","修改成功");
  	  	      				resetValue();
  	  	      				$("#dlg").dialog("close");
  	  	      				$("#dg").datagrid("reload");
  				 		}	 
  				 	}
  				}
  			});
  		}
  		//删除老师
 		function deleteMajor(){
 			var selectedRows=$("#dg").datagrid('getSelections');
	        	if(selectedRows.length==0){
	        		$.messager.alert("系统提示","请选择你需要删除的专业");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<selectedRows.length;i++){
	        		ids.push(selectedRows[i].majorId);
	        	}
	        	var strIds=ids.join(",");
	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
	        		if(r){
	        			$.post(
	        			  "${pageContext.request.contextPath}/deptHead_deleteMajor.action",
	        				{"delIds":strIds},
	        				function(result){
	        					$.messager.alert("系统提示","您已成功删除<font color=red>"+result+"</font>条专业数据");
	        					$("#dg").datagrid('reload');

	        			},"Json");
	        		}
	        	});          	
 		}
  		    //查询
  		    function search(){
  		    	$('#dg').datagrid('load',{
  		    		majorName : $("#s_majorName").val()
	    		});
  		    }
  		    
   		//关闭对话框，将文本内容清除
 		function closeDialog(){
 			resetValue();
 			$("#dlg").dialog("close");
 		}
 		//重置对话框
 		function resetValue(){
 			$("#majorName").val("");
 			$("#majorDesc").val("");
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
		<table class="easyui-datagrid" title="专业信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_majorList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="majorId"  hidden="true">编号</th>
		                    <th field="yardName">所属学院</th>
		                    <th field="majorName">专业名称</th>
		                    <th field="majorDesc">专业简介</th>
		               </tr>
		           </thead>
		     </table>
		    
		      <div id="tb">
	         <div style="padding-top: 3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteMajor()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>      
	          </div>
	          <div style="padding-bottom: 3px;">
	         	 	&nbsp;<span class="bspan">专业名称：</span>&nbsp;<input type="text" name="majorName" id="s_majorName" style="width: 100px">
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:350px;height: 400px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 30px;">
                               <tr> 
                                  <td>专业名称:</td>
                                  <td><input type="text" name="major.majorName"  class="easyui-validatebox" required="true" id="majorName"></td>
                               </tr>
                               <tr> 
                                  <td>专业简介:</td>
                                  <td><textarea name="major.majorDesc" id="majorDesc"></textarea></td>
                               </tr>
                              	 <tr>
                              	 	<td colspan="2" style="color: #850B4B;margin-top: 30px;">
                              	 		温馨提示：您当前添加的专业均默认且只能是您当前院系下的专业信息
                              	 	</td>
                              	 </tr>	 
                        </table>	            
	            </form>
	     </div>
	     
	     <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addOrSaveMajor()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
	     
</body>
</html>