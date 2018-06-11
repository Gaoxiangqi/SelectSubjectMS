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
               //条件查询
               function search(){
            	   $('#dg').datagrid('load',{
   	    			teacherName:$("#s_realName").val(),
   	    			title:$("#s_title").val(),
   	    			direction:$("#s_direction").combobox("getValue"),
   	    			state:$("#s_state").combobox("getValue")
   	    		}); 
               }
               //设置为点击进入明细界面
                function titleDetail(value, row, index)
               {
                 return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
               }
               //设置点击审核
               function setLink(value, row,index) {
            	   return "<a href='javascript:openExamineDialog("+row.subjectId+")' style='font-size:15px;'>"+value+"</a>"
               }
               function openExamineDialog(subjectId){
            	  $.post(
            		"${pageContext.request.contextPath}/deptHead_getSubjectById.action",
            		{"subjectId":subjectId},
            		function(result){
            			$("#subjectId").val(result.id);
            			$("#title").val(result.title);
            			$("#cbox").combobox("setValue",result.state);
            		},
            		"json"
            	  );
            	  $("#dlg").dialog("open").dialog("setTitle","修改审核");
               }
               
               function updateState(){
            		$("#fm").form("submit",{
          				url:"${pageContext.request.contextPath}/deptHead_updateState.action",
          				onSubmit:function(){
          						if($("#cbox").combobox('getValue') == '0'){
          							$.messager.alert("系统提示","请选择审核状态");
          							return false && $(this).form("validate");
          						}		
          					else  return $(this).form("validate");
          				},
          				success:function(result){
          					$.messager.alert("系统提示","修改成功！");
          					$("#dlg").dialog("close");
              				$("#dg").datagrid("reload"); 
          					
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
         	<table class="easyui-datagrid" title="所有选题" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_subjectList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="subjectId"  hidden="true">编号</th>
		                    <th field="yardId"  hidden="true">学院编号</th>
		                    <th field="yardName" >所属学院</th>
		                    <th field="teacherName">出题人</th>
		                    <th field="title" formatter=titleDetail>标题</th>
		                    <th field="selectNum">选题人数</th>
		                    <th field="direction">题目方向</th>
		                    <th field="state" hidden="true"></th>
		                    <th field="stateDesc">审核状态</th>	
		                    <th field="examine"formatter=setLink>审核操作</th>            	               
		               </tr>
		           </thead>
		     </table>
		     
		    <div id="tb">
	          <div style="padding: 3px;">
	         	 	&nbsp;<span class="bspan">出题人：</span>&nbsp;<input type="text" name="s_teacherName" id="s_realName" style="width: 100px">
	         	 	&nbsp;<span class="bspan">标题：</span>&nbsp;<input type="text" name="s_title" id="s_title" style="width: 100px">
	          		&nbsp;<span class="bspan">方向：</span>&nbsp;<select class="easyui-combobox" id="s_direction" style="width: 100px" panelHeight="auto" editable="false">
	          					<option value="0">请选择</option>
	          					<option value="1">应用方向</option>
	          					<option value="2">理论方向</option>
	          		</select>
	          		&nbsp;<span class="bspan">审核状态：</span>&nbsp;<select class="easyui-combobox"  id="s_state" style="width: 120px" panelHeight="auto" editable="false">
	          				    <option value="0">请选择</option>
	          				    <option value="1">未审核</option>
	          					<option value="2">审核通过</option>
	          					<option value="3">审核未通过</option>
	          				    
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:400px;height: 400px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 30px;">
                               <tr> 
                                  <td></td>
                                  <td><input type="hidden" name="subjectId" class="easyui-validatebox" required="true" id="subjectId" ></td>
                               </tr>
                               <tr> 
                                  <td>标题:</td>
                                  <td><input type="text" name="title"  class="easyui-validatebox" required="true" id="title" disabled="disabled" style="width: 250px;"></td>
                               </tr>
                               <tr>
                        	  		<td>审核状态:</td>
                        	  		<td><select class="easyui-combobox" id="cbox" style="width: 100px" name="state" panelHeight="auto">
                        	  			   <option value="0">请选择</option>
				          				   <option value="1">未审核</option>
				          				   <option value="2">审核通过</option>
				          				   <option value="3">审核未通过</option>
                        	  		</select></td>
                        	  </tr>
                        	     <tr> 
                                  <td colspan="2" style="color: #850B4B">您需要修改审核下拉框改变题目的审核状态！</td>
                                 
                               </tr>
                        </table>	            
	            </form>
	     </div>
	     
	       <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:updateState()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
</body>
</html>