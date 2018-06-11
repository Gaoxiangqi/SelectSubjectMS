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
   	    			studentName:$("#s_studentName").val()
   	    		}); 
               }
               //设置为点击进入明细界面
               function titleDetail(value, row, index)
              {
                return "<a href='${pageContext.request.contextPath}/user_titleDetail.action?id=" + row.subjectId + "' target='_blank' style='font-size:15px;'>"+value+"</a>";
              }
               //导出结果
               function exportResult(){
     		    	url = "${pageContext.request.contextPath}/deptHead_exportResult.action";
     		    	window.open(url);
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
         	<table class="easyui-datagrid" title="最终结果" id="dg" data-options="pagination:true" fit="true" url="${pageContext.request.contextPath}/deptHead_finalResult.action" rownumbers="true"
		                    toolbar="#tb">
		           <thead>
		               <tr>
		                   	<th field="cb" checkbox="true"></th>
		                    <th field="studentId"  hidden="true">编号</th>
		                    <th field="majorName">专业名称</th>
		                    <th field="className" >所属班级</th>
		                    <th field="realName">姓名</th>
		                    <th field="title" formatter="titleDetail">选题</th>
		                 	<th field="direction">方向</th>      	               
		               </tr>
		           </thead>
		     </table>
		     
		<div id="tb">
			   <div style="padding:3px;">
	             <a href="javascript:exportResult()" class="easyui-linkbutton" iconCls="icon-export" plain="true" style="background-color: #E3EEFF"><span class="bspan">导出结果</span></a>       
	          </div>
	     </div>
</body>
</html>