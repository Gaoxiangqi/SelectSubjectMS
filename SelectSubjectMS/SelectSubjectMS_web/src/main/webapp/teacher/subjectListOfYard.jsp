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
         	<table class="easyui-datagrid" title="所有选题" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/teacher_subjectListOfYard.action" rownumbers="true"
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
	          <div style="padding: 3px;">
	         	 	&nbsp;<span class="bspan">出题人：</span>&nbsp;<input type="text" name="s_teacherName" id="s_realName" style="width: 100px">
	         	 	&nbsp;<span class="bspan">标题：</span>&nbsp;<input type="text" name="s_title" id="s_title" style="width: 100px">
	          		&nbsp;<span class="bspan">方向：</span>&nbsp;<select class="easyui-combobox" id="s_direction" style="width: 100px">
	          					<option value="0">请选择</option>
	          					<option value="1">应用方向</option>
	          					<option value="2">理论方向</option>
	          		</select>
	          		&nbsp;<span class="bspan">审核状态：</span>&nbsp;<select class="easyui-combobox"  id="s_state" style="width: 120px">
	          				    <option value="0">请选择</option>
	          				    <option value="1">未审核</option>
	          					<option value="2">审核通过</option>
	          					<option value="3">审核未通过</option>
	          				    
	          		</select>
	          		&nbsp;&nbsp;&nbsp;
	         	    <a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search" plain="true" style="background-color:#E3EEFF"><span class="bspan">查询</span></a>
	          </div>
	     </div>
</body>
</html>