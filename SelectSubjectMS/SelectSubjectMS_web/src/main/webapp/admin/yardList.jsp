<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
	   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/icon.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/demo/demo.css">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
	    <script type="text/javascript">
	    		var url;
	    		//打开添加对话框
	    		function openAddDialog(){
	    			$("#yardName").val('');
	    			$("#dlg").dialog("open").dialog("setTitle","添加学院");
	    			url="${pageContext.request.contextPath}/admin_addYard.action";
	    		}
	    	    //打开修改对话框
	    		function openUpdateDialog(){
	    			$("#dlg").dialog("open").dialog("setTitle","修改学院信息");
	    			var selectedRows=$("#dg").datagrid('getSelections');
		        	if(selectedRows.length==0){
		        		$.messager.alert("系统提示","请选择你需要修改的学院");
		        		return;
		        	}
		        	if(selectedRows.length>1){
		        		$.messager.alert("系统提示","请只选择一条修改的学院");
		        		return;
		        	}
		        	$("#yardName").val(selectedRows[0].yardName);
	    			url="${pageContext.request.contextPath}/admin_updateYard.action?yard.id="+selectedRows[0].yardId;
	    		}
	    		//添加学院
	    		function addYard(){
	    			$("#form").form("submit",{
	    				url:url,
	    				onSubmit:function(){	
	    					return $(this).form("validate");
	    				},
	    				success:function(data){
	    					var result=$.parseJSON(data);
	    					if (result.type == 'add') {
	    						if(result.num == '0'){
		    						$.messager.alert("系统提示","该学院已存在，无法添加");
		    					} else { 
		    							$.messager.alert("系统提示","保存成功");
				    					resetValue();
				        				$("#dlg").dialog("close");
				        				$("#dg").datagrid("reload");
		    						}
	    					}
	    					if (result.type == 'update') {
		    						if(result.num == '0'){
			    						$.messager.alert("系统提示","该学院名称已有学院使用!");
			    						
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
	    		//删除学院
	    		function deleteYard(){
	    			var selectedRows=$("#dg").datagrid('getSelections');
		        	if(selectedRows.length==0){
		        		$.messager.alert("系统提示","请选择你需要删除的学院");
		        		return;
		        	}
		        	var ids=[];
		        	for(var i=0;i<selectedRows.length;i++){
		        		ids.push(selectedRows[i].yardId);
		        	}
		        	var strIds=ids.join(",");
		         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
		        		if(r){
		        			$.post(
		        			  "${pageContext.request.contextPath}/admin_deleteYard.action",
		        				{"delIds":strIds},
		        				function(result){
		        					$.messager.alert("系统提示","您已成功删除<font color=red>"+result+"</font>条数据");
		        					$("#dg").datagrid('reload');

		        			},"Json");
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
	    			$("#yardName").val("");
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
	    	}
	    	.bspan{
	    		font-size:15px;
	    	}
	    </style>
</head>
<body>
			<table class="easyui-datagrid" title="学院信息" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/admin_yardList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="yardId"  hidden="true">编号</th>
		                    <th field="yardName" >所属学院</th>
		               </tr>
		           </thead>
		     </table>
		     
		 <div id="tb">
	         <div style="padding: 3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan">添加</span></a>
	             <a href="javascript:openUpdateDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteYard()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>         
	          </div>
	     </div>
	     
	     	 <div id="dlg" class="easyui-dialog" style="width:320px;height: 300px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="form" method="post" >
                        <table style="border-collapse:separate;border-spacing:10px 20px;">
                               <tr height="60px"> 
                                  <td>学院名称:</td>
                                  <td><input id="yardName" name="yard.yardName"  class="easyui-validatebox" required="true" ></td>
                               </tr>
          
                        </table>	            
	            </form>
	     </div>
	     <div id="dlg-button">	        
	          <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:addYard()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>            
	     </div>
</body>
</html>