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
				<div class="panel-heading headcolor">
					<div class="headcolor-left"></div>
					<div class="headcolor-main"></div>
					<h3 class="panel-title">权限管理</h3>
				</div>
				<div class="list_search_head">

					<div id="collapse-group" class="">
						<div class="panel panel-default" style="margin-bottom: 0px;">
							<div class="">
								<form id="form1" name="form1"
									action="${ctx }/user/user-list.izhbg" method="post"
									class="form-horizontal">
									<div class="list_search_head_left">
										<div>
											<label class="search_head control-label">查询条件：</label>
										</div>
										<div class="">
											<select class="form-control selectC" name="selectC">
												<option name="name" value="权限名称" >权限名称</option>
												<option name="code" value="权限编码" >权限编码</option>
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
<%-- 										<security:authorize code="pri_add"> --%>
<!-- 											<a href="javascript:add();" id="addDialog" class="btn btn-sm search_a_add">  -->
<!-- 												<i class="search_head_add"></i>  -->
<!-- 												<span class="search_head_span">新建</span> -->
<!-- 											</a> -->
<%-- 										</security:authorize> --%>
											
<%-- 										<security:authorize code="pri_edit"> --%>
<!-- 											<a href="javascript:edit();" class="btn btn-sm search_a_modify">  -->
<!-- 												<i class="search_head_modify"></i>  -->
<!-- 												<span class="search_head_span">修改</span> -->
<!-- 											</a>  -->
<%-- 										</security:authorize> --%>
<%-- 										<security:authorize code="pri_del"> --%>
<!-- 											<a href="javascript:deleteRow();" class="btn btn-sm search_a_delete">  -->
<!-- 												<i class="search_head_delete"></i>  -->
<!-- 												<span class="search_head_span">删除</span> -->
<!-- 											</a> -->
<%-- 										</security:authorize> --%>
										
										<a href="javascript:check();" class="btn btn-sm search_a_check">
											<i class="search_head_check"></i>
											<span class="search_head_span">查看</span>
										</a> 
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
				<div style="margin: 0px;"></div>
				<div style="min-height: 680px; height: 100%;">
					<table id="priList"></table>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
var conditionQuery = new ConditionQuery({});
var treeGrid = new treeTableGrid({});
//新建
function add(){
	var $row = treeGrid.getSelectedRow();
	var parentId = -1;
	if($row){
		parentId = $row.id;
	}
	treeGrid.createModal("新建权限","${ctx}/pri/pri_edit?parentId="+parentId,300,800);
}
//编辑
function edit(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.id==1){
			new alertdialog({title:"提示",content:"根结点不能编辑!"}); return;
		}
		treeGrid.createModal("编辑权限","${ctx}/pri/pri_edit?orgId="+$row.id + "&parentId="+$row.parent,300,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要编辑的记录!"});
	}
}
//查看
function check(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		treeGrid.createModal("查看权限","${ctx}/pri/pri_check?priId="+$row.id + "&parentId="+$row.parent,300,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要查看的记录!"});
	}
}
//删除
function deleteRow(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.id==1){
			new alertdialog({title:"提示",content:"根结点不能删除!"}); return;
		}
		if($row.children.length > 0){
			new alertdialog({title:"提示",content:"存在子权限，不能删除!"}); return;
		}
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			var data = {priId:$row.id};
			$.ajax({
		        url: "${ctx}/pri/deletePri",
		        datatype: 'json',
		        type: "Post",
		        data:data,
		        success: function (data) {
		        	if(data==1){
		        		treeGrid.treeQuery("searchForm");//刷新列表
		        	}else if(data == -2){
		        		new alertdialog({title:"提示",content:"该权限有角色已绑定，不能删除!"});
		        	}else {
		        		new alertdialog({title:"提示",content:"未知错误，不能删除!"});
		        	}
		        }
			 });
		}});
		$("#priList").treegrid('unselectAll');
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
		treeGrid.treeQuery("searchForm");//刷新列表
	}
}
$(function() {
	var columns = [ [ {
		field : "name",
		title : "权限名称",
		width : 200
	}, {
		field : "code",
		title : "权限编码",
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
	
	treeGrid.initTreeGrid("priList",columns,"${ctx}/pri/getTreeGridData");
	$("#queryBtn").bind("click",function(){
		treeGrid.treeQuery_2("searchForm");
	});
	$("#exportBtn").bind("click",function(){
		treeGrid.pageExport('${ctx}/pri/datasByExport',"searchForm",'${ctx}/','权限信息'); 
	});
});

</script>

</html>
