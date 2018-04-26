<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<%@include file="/common/include_easyui.jsp"%>
</head>
<body class="page-header-fixed">
	<div class="content content-inner">
		<div id="myheading">
			<div class="panel panel-default headborder">
				<!-- Default panel contents -->
				<div class="panel-heading headcolor">
					<div class="headcolor-left"></div>
					<div class="headcolor-main"></div>
					<h3 class="panel-title">角色管理</h3>
				</div>
				<div class="list_search_head">

					<div id="collapse-group" class="">
						<div class="panel panel-default" style="margin-bottom: 0px;">
							<div class="">
								<form id="roleSearchform" name="roleSearchform" action=""
									method="post" class="form-horizontal">
									<div class="list_search_head_left">
										<div>
											<label class="search_head control-label">查询条件：</label>
										</div>
										<div class="">
											<select class="form-control selectC" name="selectC">
												<option name="name" value="机构名称" >角色名称</option>
												<option name="code" value="机构编码" >角色编码</option>
											</select>
										</div>
										<div class="">
											<select class="form-control" style="display:none;">
												<option value="1">包含</option>
												<option value="2">等于</option>
											</select>
										</div>
										<div class="">
											<input style="width: 135px;" type="text" name="searchText" class="form-control searchText" placeholder="查询内容">
										</div>
										<div class="">
											<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
												<i class="search_head_search"></i>
												<span class="search_head_span">查询</span>
											</a>
											<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
												<i class="search_head_export"></i>
												<span class="search_head_span">导出</span>
											</a>
										</div>
									</div>
									<div style="clear: both;"></div>
									<div class="list_search_head_right">
										<security:authorize code="roleM_add">
											<a href="javascript:add_role();" id="addDialog" class="btn btn-sm search_a_add"> 
												<i class="search_head_add"></i> 
												<span class="search_head_span">新建</span>
											</a>
										</security:authorize>
											<a href="javascript:check_role();" class="btn btn-sm search_a_check">
												<i class="search_head_check"></i>
												<span class="search_head_span">查看</span>
											</a> 
										<security:authorize code="roleM_edit">
											<a href="javascript:edit_role();" class="btn btn-sm search_a_modify"> 
												<i class="search_head_modify"></i> 
												<span class="search_head_span">修改</span>
											</a> 
										</security:authorize>
										<security:authorize code="roleM_del">
											<a href="javascript:delete_role();" class="btn btn-sm search_a_delete"> 
												<i class="search_head_delete"></i> 
												<span class="search_head_span">删除</span>
											</a>
										</security:authorize>
										<security:authorize code="roleM_assign">
											<a href="javascript:assign_pri()" class="btn btn-sm search_a_role"> 
												<i class="search_head_role"></i> 
												<span class="search_head_span">分配权限</span>
											</a>
										</security:authorize>
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
				<div style="margin: 0px;"></div>
				<div style="min-height: 680px; height: 100%;">
					<table id="roleList"></table>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
var conditionQuery = new ConditionQuery({});
var treeGrid = new treeTableGrid({});
function add_role(){
	var $row = treeGrid.getSelectedRow();
	var parentId = -1;
	if($row){
		parentId = $row.id;
	}
	treeGrid.createModal("新建角色","${ctx}/role/role_edit?parentId="+parentId,300,800);
}
//编辑
function edit_role(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.id==1){
			new alertdialog({title:"提示",content:"根结点不能编辑!"}); return;
		}
		treeGrid.createModal("编辑角色 ","${ctx}/role/role_edit?roleId="+$row.id + "&parentId="+$row.parent,300,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要编辑的角色!"});
	}
}
//查看
function check_role(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		treeGrid.createModal("查看角色 ","${ctx}/role/role_check?roleId="+$row.id + "&parentId="+$row.parent,300,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要查看的角色!"});
	}
}
//分配权限
function assign_pri(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		var name = $row.name;
		treeGrid.createModal('分配权限【'+name+'】',"${ctx}/role//asign_pri?roleId="+$row.id,420,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要操作的角色!"});
	}
}
//删除角色 
function delete_role(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.id==1){
			new alertdialog({title:"提示",content:"根结点不能删除!"}); return;
		}
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			var checkdel = [];
			checkdel.push($row.id);
			var data = {};
			data.roleIds = checkdel;
			$.ajax({
		        url: "${ctx}/role/deleteRole",
		        datatype: 'json',
		        type: "Post",
		        data:data,
		        success: function (data) {
		        	if(data==1){
		        		treeGrid.treeQuery("roleSearchform");//刷新列表
		        	}else if(data == -2){
		        		new alertdialog({title:"提示",content:"该角色与用户绑定，不能删除!"});
		        	}else {
		        		new alertdialog({title:"提示",content:"未知错误，不能删除!"});
		        	}
		        }
			 });
		}});
		$("#roleList").treegrid('unselectAll');
	}else{
		new alertdialog({title:"提示",content:"请选中你需要操作的记录!"});
	}
}
//关闭窗口触发的操作
function hide_modal(tarId,isRefresh){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	if(isRefresh){
		treeGrid.treeQuery("roleSearchform");//刷新列表
	}
}

$(function() {
	var columns = [ [ {
		field : "name",
		title : "角色名称",
		width : 200
	}, {
		field : "code",
		title : "角色编码",
		width : 120
	}, {
		field : "position",
		title : "排序",
		width : 30
	}, {
		field : "remark",
		title : "备注",
		width : 150
	} ] ];
	
	treeGrid.initTreeGrid("roleList",columns,"${ctx}/role/getTreeGridData");
	$("#queryBtn").bind("click",function(){
		treeGrid.treeQuery_2("roleSearchform");
	});
	$("#exportBtn").bind("click",function(){
		treeGrid.pageExport('${ctx}/role/datasByExport',"searchForm",'${ctx}/','角色信息'); 
	});
});



</script>

</html>
