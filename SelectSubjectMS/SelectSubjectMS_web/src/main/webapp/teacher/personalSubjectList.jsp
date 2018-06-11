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
               var url;
               //条件搜索
               function search(){
            	   $('#dg').datagrid('load',{
   	    			teacherName: $("#s_teacherName").val(),
   	    			title:$("#s_title").val(),
   	    			direction:$("#s_direction").combobox("getValue"),
   	    			state:$("#s_state").combobox("getValue")
   	    		}); 
               }
               //查看题目信息
               function titleDetail(value, row, index)
               {
                 return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
               }
               
               function openAddDialog(){
            	   resetValue();
            	$("#dlg").dialog("open").dialog("setTitle","添加题目");
   	    		url="${pageContext.request.contextPath}/teacher_addSubject.action";
               }
               
           	//打开修改系主任对话框
       	    function openModifyDialog(){
       	    	var selectedRows=$("#dg").datagrid('getSelections');
       	    	if(selectedRows.length==0){
       	    		$.messager.alert("系统提示","请选择你要修改的题目");
       	    		return;
       	    	}
       	    	if(selectedRows.length>1){
       	    		$.messager.alert("系统提示","请只选择一条题目信息进行修改");
       	    		return;
       	    	}
       	    	$.post(
	        			  "${pageContext.request.contextPath}/teacher_getSubjectOfJSON.action",
	        				{"subjectId":selectedRows[0].subjectId},
	        				function(result){
	        					$("#title").val(result.title);
	        					$("#keyWords").val(result.keyWords);
	        					if (result.direction == '应用方向') {
	        						$("#cbox1").combobox("setValue","1");
	        					} else {
	        						$("#cbox1").combobox("setValue","2");
	        					}
	        					$("#summary").val(result.summary);
	        					$("#schedule").val(result.schedule);
	        					$("#referDoc").val(result.referDoc);
	        					$("#studentRequire").val(result.studentRequire);

	        			},"Json");
           		url="${pageContext.request.contextPath}/teacher_updateSubject.action?subjectId="+selectedRows[0].subjectId; 
           		$("#dlg").dialog("open").dialog("setTitle","修改题目");
       	    }
               //添加题目
               function saveSubject(){
            	   $("#fm").form("submit",{
         				url:url,
         				onSubmit:function(){
         				  var value = $("#cbox1").combobox("getValue");
         				  if (value == "0") {
         					 $.messager.alert("系统提示","请先选择题目方向！");
         					  return false;
         				  }
         				  return $(this).form("validate");
         				},
         				success:function(result){
         				 	var r=$.parseJSON(result);
         				 	if (r.warn == '1') {
         				 		$.messager.alert("系统提示","您当前添加题目已达上限,无法添加！");
         				 	} else {
         				 		if(r.type=='add'){
                 					if(r.num=='0'){
                 						$.messager.alert("系统提示","该题目标题已存在,无法添加！");
                 					}
                 					else{
                 					$.messager.alert("系统提示","保存成功");
                     				$("#dlg").dialog("close");
                     				$("#dg").datagrid("reload"); 
                 					} 
                 				 	}
                 				 	if(r.type=='update') {
                 				 		$.messager.alert("系统提示","修改成功");
                 	      				$("#dlg").dialog("close");
                 	      				$("#dg").datagrid("reload"); 
                 				 	}
         				 	}
         				}
         			});
            	   
               }
              
               function deleteSubject(){
        			var selectedRows=$("#dg").datagrid('getSelections');
       	        	if(selectedRows.length==0){
       	        		$.messager.alert("系统提示","请选择你需要删除的题目");
       	        		return;
       	        	}
       	        	var ids=[];
       	        	for(var i=0;i<selectedRows.length;i++){
       	        		ids.push(selectedRows[i].subjectId);
       	        	}
       	        	var strIds=ids.join(",");
       	         	$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>数据吗？",function(r){
       	        		if(r){
       	        			$.post(
       	        			  "${pageContext.request.contextPath}/admin_deleteSubject.action",
       	        				{"delIds":strIds},
       	        				function(result){
       	        					$.messager.alert("系统提示","您已成功删除<font color=red>"+result+"</font>条数据");
       	        					$("#dg").datagrid('reload');

       	        			},"Json");
       	        		}
       	        	});          	
        		}
               
               function closeDialog(){
            	   resetValue();
            	   $("#dlg").dialog("close");
      			   $("#dg").datagrid("reload"); 
               }
               
               function resetValue(){
            	 $("#title").val("");       	 
            	 $("#keyWords").val("");
            	 $("#cbox1").combobox("setValue","0");
            	 $("#summary").val("");
            	 $("#schedule").val("");
            	 $("#referDoc").val("");
            	 $("#studentRequire").val("");  
               }
         </script>
       <style type="text/css">
       #dlg{
         
       }
       .td1{
           font-size: 20px
       }
       .td2{
           height: 20px
       }
       .div1{
           padding-top: 10px;
           padding-left: 40%;
           width:100%;
           height:50px;
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
         	<table class="easyui-datagrid" title="所有选题" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/teacher_personalSubjectList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="subjectId" width=0 hidden="true">编号</th>
		                    <th field="yardId" width=0 hidden="true">学院编号</th>
		                    <th field="yardName" width=100>所属学院</th>
		                    <th field="teacherName" width=100>出题人</th>
		                    <th field="title" width=400 formatter=titleDetail>标题</th>
		                    <th field="selectNum" width=100>选题人数</th>
		                    <th field="direction" width=100>题目方向</th>
		                    <th field="state" width=100>审核状态</th>          	               
		               </tr>
		           </thead>
		     </table>
		     
		 <div id="tb">
		       <div style="padding-top: 3px;">
	             <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconcls="icon-add" plain="true" style="background-color:#E3EEFF"><span class="bspan" >添加</span></a>
	             <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconcls="icon-edit" plain="true" style="background-color:#E3EEFF"><span class="bspan">修改</span></a>
	             <a href="javascript:deleteSubject()" class="easyui-linkbutton" iconcls="icon-remove" plain="true" style="background-color:#E3EEFF"><span class="bspan">删除</span></a>         
	           </div>
	          <div style="padding-bottom: 3px;">  
	         	 	&nbsp;<span class="bspan">标题：</span>&nbsp;<input type="text" name="s_title" id="s_title" style="width: 100px">
	          		&nbsp;<span class="bspan">方向：</span>&nbsp;<select class="easyui-combobox" id="s_direction" style="width: 100px">
	          					<option value="0">请选择</option>
	          					<option value="1">应用方向</option>
	          					<option value="2">理论方向</option>
	          		</select>
	          		&nbsp;<span class="bspan">审核状态：</span>&nbsp;<select class="easyui-combobox"  id="s_state" style="width: 120px" >
	          				    <option value="0">请选择</option>
	          				    <option value="1">未审核</option>
	          					<option value="2">审核通过</option>
	          					<option value="3">审核未通过</option>
	          				    
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	        <div id="dlg" class="easyui-dialog" fit=true style="width:600px; padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post"> 
                       <table style="border-collapse:separate;border-spacing:10px 10px;" align="center">
                             <tr> 
                                 <td class="td1">标题:</td>
                                 <td class="td2"><input type="text" name="subject.title"  class="easyui-validatebox" required="true" id="title" style="width: 300px;height: 25px;"></td>
                            </tr>
                             <tr> 
                                 <td class="td1">关键字:</td>
                                 <td class="td2"><input type="text" name="subject.keyWords"  class="easyui-validatebox" required="true" id="keyWords" style="width: 300px;height: 25px;"></td>
                            </tr>
                             <tr>
                                 <td class="td1">方向:</td>
                                 <td class="td2"><select class="easyui-combobox" id="cbox1" panelHeight="auto" style="width: 120px" name="subject.direction" editable="false">
                                  		<option value="0">请选择</option>
                                  		<option value="1">应用方向</option>
                                  		<option value="2">理论方向</option>
                                  </select></td>
                             </tr>  
                             <tr>
                              	 <td class="td1">题目概要：</td>
                              	 <td><textarea  name="subject.summary" style="width: 400px;height: 100px" id="summary"></textarea></td>    
                             </tr>
                             <tr>
                              	 <td class="td1">进度安排：</td>
                              	 <td><textarea  rows="10" name="subject.schedule" style="width: 400px;height: 100px" id="schedule"></textarea></td>    
                             </tr>
                             <tr>
                              	 <td class="td1">参考文档</td>
                              	 <td><textarea rows="10" name="subject.referDoc" style="width: 400px;height: 100px" id="referDoc"></textarea></td>    
                             </tr>
                             <tr>
                              	 <td class="td1">学生要求：</td>
                              	 <td><textarea rows="10" name="subject.studentRequire" style="width: 400px;height: 100px" id="studentRequire"></textarea></td>    
                             </tr> 	 
                        </table>	                                      
	            </form>
	     </div>
	     
	     <div id="dlg-button">	        
	                    <table style="border-collapse:collapse;margin-left: 20%;width: 60%;">
	     						<tr>
	     							<td ><a href="javascript:saveSubject()"  class="easyui-linkbutton" iconcls="icon-ok" >保存</a></td>   							
	     							<td style="float: left;margin-left: 20%"><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel">关闭</a></td>
	     						</tr>
	     				</table>             
	     </div>
</body>
</html>