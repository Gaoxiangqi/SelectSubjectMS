<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/themes/icon.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/demo/demo.css">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/style/js/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script>
					// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:"欢迎登录，管理员  ${currentUser.realName}！ <a href='javascript:void' onclick='top.showAbout();'>联系管理员</a>",
				timeout:5000
			});
		},3000);
		/*************/
	 	$(function(){
			 //树菜单的数据
			var treeData2=[			               
			   {
				text:"查看个人信息",
				attributes:{
 					url:"${pageContext.request.contextPath}/admin/adminInfo.jsp"
 				}	
			   },
			   {
					text:"修改个人信息 ",
					attributes:{
	 					url:"${pageContext.request.contextPath}/admin/adminUpdate.jsp"
	 				}	
				   },
				   {
						text:"修改密码",
						attributes:{
		 					url:"${pageContext.request.contextPath}/admin/modifyPwd.jsp"
		 				}	
					   }
			            ];
			 
			 var treeData3=[
			         {
			        	 text:"学院管理",
			        	 state:"closed",  
			        	 children:[
			        	            {
			        					text:"查看院系",
			        					attributes:{
			        	 					url:"${pageContext.request.contextPath}/admin/yardList.jsp"
			        	 				}	
			        				   }
			        	            ]
			        		  		        	 
			         },{
			        	 text:"专业管理",
			        	 state:"closed", 
			        	 children:[
			        	            {
			        					text:"查看专业",
			        					attributes:{
			        	 					url:"${pageContext.request.contextPath}/admin/majorList.jsp"
			        	 				}	
			        				   }
			        	            ]
			         }      
			                ];
			 
			 var treeData4=[			               
			 			   {
			 				text:"查看系主任",
			 				attributes:{
			  					url:"${pageContext.request.contextPath}/admin/deptHeadList.jsp"
			  				}	
			 			   }
			 			            ];
			 			 
			//实例化树菜单
			$("#tree2").tree({
				data:treeData2,
				onClick:function(node){
			 		if(node.attributes){
			 			openTab(node.text,node.attributes.url);
			 		}
			 	}
			}); 
			$("#tree3").tree({
				data:treeData3,
				onClick:function(node){
			 		if(node.attributes){
			 			openTab(node.text,node.attributes.url);
			 		}
			 	}
			});
			$("#tree4").tree({
				data:treeData4,
				onClick:function(node){
			 		if(node.attributes){
			 			openTab(node.text,node.attributes.url);
			 		}
			 	}
			});
			 //新增tabs
			   function openTab(text,url){
	    	    	 if($("#tabs").tabs("exists",text)){
	    	    		 $("#tabs").tabs("select",text);
	    	    	 }
	    	    	 else{
	    	    		 var content="<iframe src="+url+" width='100%' height='93%' frameborder=0 scrolling='auto'></iframe>";
	    	    	 $("#tabs").tabs('add',{
	    	    		 title:text,
	    	    		 closable:true,
	    	    		 content:content
	    	    	 }
	    	    			 );
	    	    	 }
	    	     }
		})
		
		   function logout(){
	 	 	$.messager.confirm("系统提示","您确定要退出本系统吗？",function(r){
        		if(r){
        			location.href = "${pageContext.request.contextPath}/user_logout.action";
        		}
        	}); 
		}
		</script>
		<style type="text/css">
				#ol1 li{
				 font-size: 14px;
				 margin: 10px;
				}
		</style>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height: 90px;background-color: #33a3dc;">
			 <div style="float: left"><img src="${pageContext.request.contextPath}/style/img/main/logo.png" height="85px"></div>
			 <div><font style="font-size: 60px;letter-spacing: 15px;color: white;">双向选题系统</font></div>
		<div style="position: absolute;right: 5px;top:10px;">
			[<strong>${currentUser.realName }</strong>]，欢迎你！
		</div>
		<div style="position: absolute;right: 150px;top:20px;">
			<iframe style="float: right;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
		</div>
	 	<div style="position: absolute; right: 5px; bottom: 10px; ">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#paneMenu',iconCls:'icon-help'">控制面板</a>
		</div>

		<div id="paneMenu" style="width: 100px; display: none;">
			<div class="menu-sep"></div>
			<div onclick="logout()">退出系统</div>
		</div> 
		</div>
		<div region="west" style="width: 150px;" title="导航菜单"  split="false">
			  	<div class="easyui-accordion" fit="true" border="false" >
			<div title="注意事项" data-options="iconCls:'icon-mini-add'" style="overflow:auto" >
				<ul id="tree1" ></ul>
			</div>
			<div title="个人信息" data-options="iconCls:'icon-mini-add'" style="overflow:auto" >
				<ul id="tree2" class="easyui-tree">
				</ul>
			</div>
			<div title="院系管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto" >
				<ul id="tree3" class="easyui-tree"></ul>
			</div>
			<div title="系主任管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="tree4"></ul>
			</div>
			<div title="通知公告" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="tree5">查看通告</ul>
			</div>
		</div>
		</div>
		<div region="center">
				<div style="height: 30px">
					<marquee style="behavior: scroll; color:#9B1CEB;margin-top: 2px;font-size: 18px;background-color:#E0ECFF;border: 1px solid #95B8E7;">现在是学生选题时间</marquee>
				</div>
				<div class="easyui-tabs" id="tabs"  fit="true" style="width=100%;">
					<div title="注意事项(必读)" iconcls="icon-tip">
						 <div id="p" class="easyui-panel" title="注意事项" fit="true">
							<p style="font-size:25px">选题系统注意事项.</p>
							<ol id="ol1">
								<li>系统分为教师出题,学生选题,教师指派,系主任调整几个阶段.</li>
								<li>教师出的题目必须由系主任审核通过学生才能进行选择.</li>
								<li>学生选题上限，题目被选上限，教师出题上限都是各院系系主任单独设置.</li>
								<li>教师根据学生的志愿排序进行选择,主要选择权在老师手中.</li>
								<li>鼓励学生私下联系教师，达成意愿一致性,促进出题者和选题者之间的交流以及选题的完成.</li>
							</ol>
	                     </div>
					</div>
				</div>
		</div>
		<div region="south" style="height: 50px"></div>
</body>
</html>
