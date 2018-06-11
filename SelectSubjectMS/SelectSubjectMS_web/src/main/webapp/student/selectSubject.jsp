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
			 //设置为点击进入明细界面
		       function titleDetail(value, row, index)
		      {
		        return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
		      }
		       //设置为点击进入明细界面
               function setLink(value, row, index)
              {	
		    	if (row.studentLevel != null && row.studentLevel != '') {
		    		value = '修改志愿';
		    	} else {
		    		value = '选择志愿';
		    	}
                return "<a href='javascript:openLevelDialog("+row.subjectId+")' style='font-size:15px;'>"+value+"</a>";
              }
		 	 //设置删除志愿点击明细
		 	 function deleteLevelDetial(value, row, index)
             {	
		    	if (row.studentLevel != null && row.studentLevel != '') {
		    		value = '删除志愿';
		    	} else value = '';
               return "<a href='javascript:deleteLevel("+row.subjectId+")' style='font-size:15px;'>"+value+"</a>";
             }
		 function search() {
			 $('#dg').datagrid('load',{
	    			teacherName:$("#s_teacher").val(),
	    			title:$("#s_title").val(),
	    			direction:$("#s_direction").combobox("getValue")
		 });
		 }
		 
		 function openLevelDialog(subjectId) {
		    	   $.post(
		            		"${pageContext.request.contextPath}/student_getSubjectById.action",
		            		{"subjectId":subjectId},
		            		function(result){
		            			if (result.flag == '0') {
		            			$("#title").attr("disabled",false);
		            			$("#subjectId").val(result.subjectId);
		            			$("#title").val(result.title);
		            			$("#title").attr("disabled",true);
		            			}
		            			if (result.flag == '1') {
		            				$("#title").attr("disabled",false);
			            			$("#subjectId").val(result.subjectId);
			            			$("#title").val(result.title);
			            			$("#title").attr("disabled",true);
			            			$("#warn").text("警告：您当前所选题目选择人数过多,您还可以选择，但很可能选不上！");
		            			}
		            			if (result.flag == '2') {
		            				$.messager.alert("系统提示","当前题目选择人数已达上限，您不能选择！");
		            			}
		            			
		            			if (result.flag == '0' || result.flag == '1') {
		            				  $("#cbox").combobox({
		      							url:"${pageContext.request.contextPath}/student_levelComboBox.action?subjectId="+subjectId,
		      							 valueField:'studentLevel',    
		      							  textField:'levelDesc',
		      							  editable:false 
		      						});
		            				  
		      		    	   $("#dlg").dialog("open").dialog("setTitle","选择志愿");
		            			}
		            		},
		            		"json"
		            	  );
		       }
		       //更新志愿
		       function updateLevel(){
		        			$("#fm").form("submit",{
		          				url:"${pageContext.request.contextPath}/student_updateLevel.action",
		          				onSubmit:function(){
		          						if($("#cbox").combobox("getValue") == '0'){
		          							$.messager.alert("系统提示","请选择您的题目志愿");
		          							return false;
		          						}	
		          				},
		          				success:function(result){
		          					if (result.warn == '1') {
		          						$.messager.alert("系统提示","您当前可选题目数已达上限，不能继续选择！");
		          					} else {
		          						$.messager.alert("系统提示","修改保存志愿成功！");
		          						$("#warn").text('');
		          						$("#dlg").dialog("close");
			              				$("#dg").datagrid("reload"); 
		          					}	
		          				}
		          			});
		        		}
		      	function deleteLevel(subjectId) {
		      	 	$.messager.confirm("系统提示","您确定要删除该题目所选志愿吗？",function(r){
		        		if(r){
		        			$.post(
		        			  "${pageContext.request.contextPath}/student_deleteLevel.action",
		        				{"subjectId":subjectId},
		        				function(result){
		        					if (result.num == 1) {
		        						$.messager.alert("系统提示","您已成功删除该题目所选志愿！");
		        						$("#dg").datagrid('reload');
		        					} else {
		        						$.messager.alert("系统提示","您已被老师指派题目，无法删除！");
		        					}
		        			},"Json");
		        		}
		        	}); 
		      	}
		       function closeDialog(){
		    	   $("#dlg").dialog("close");
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
		<table class="easyui-datagrid" title="所有选题" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/student_subjectList.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                    <th field="cb" checkbox="true"></th>
		                    <th field="subjectId" hidden="true">题目编号</th>
		                    <th field="selectSubjectId" hidden="true">选题志愿编号</th>
		                    <th field="teacherName">出题人</th>
		                    <th field="title" formatter=titleDetail>标题</th>
		                    <th field="selectNum">已选人数</th>
		                    <th field="direction">题目方向</th>
		                    <th field="studentLevel">当前志愿</th>
		                    <th field="chooseLevel" formatter=setLink>操作</th>   
		                    <th field="deleteLevel" formatter=deleteLevelDetial>操作</th>    	               
		               </tr>
		           </thead>
		     </table>
		     
		<div id="tb">
	          <div style="padding: 3px;">  
	         	 	&nbsp;<span class="bspan">标题：</span>&nbsp;<input type="text" name="s_title" id="s_title" style="width: 100px">
	         	 	&nbsp;<span class="bspan">出题人：</span>&nbsp;<input type="text" name="s_teacher" id="s_teacher" style="width: 100px">
	          		&nbsp;<span class="bspan">方向：</span>&nbsp;<select class="easyui-combobox" id="s_direction" style="width: 100px">
	          					<option value="0">请选择</option>
	          					<option value="1">应用方向</option>
	          					<option value="2">理论方向</option>
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
	     
	      <div id="dlg" class="easyui-dialog" style="width:400px;height: 350px;padding: 20px 20px" closed="true" 
	          buttons="#dlg-button">
	            <form id="fm" method="post">
                        <table style="border-collapse:separate;border-spacing:10px 30px;">
                               <tr> 
                                  <td></td>
                                  <td><input type="hidden" name="subjectId" class="easyui-validatebox" required="true" id="subjectId" ></td>
                               </tr>
                               <tr> 
                                  <td>标题:</td>
                                  <td><input type="text" name="title"  class="easyui-validatebox" required="true" id="title" style="width: 200px"></td>
                               </tr>
                               <tr>
                        	  		<td>选择志愿:</td>
                        	  		<td><select class="easyui-combobox" id="cbox" style="width: 100px" name="studentLevel" panelHeight="auto">
                        	  		<option value="0">请选择</option>
                        	  		</select></td>
                        	  </tr>
                        	  <tr>
                        	  		<td colspan="2" style="color: red;" id="warn">
                        	  		</td>
                        	  </tr>
                        </table>	            
	            </form>
	     </div>
	     
	       <div id="dlg-button" >	        
	           <table>
	     						<tr>
	     							<td width="15%"></td>
	     							<td><a href="javascript:updateLevel()"  class="easyui-linkbutton" iconcls="icon-ok" width=250px>保存</a></td>   							
	     							<td><a href="javascript:closeDialog()"  class="easyui-linkbutton" iconcls="icon-cancel" width=350px>关闭</a></td>
	     							<td width="15%"></td>
	     						</tr>
	     				</table>             
	     </div>
</body>
</html>