<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="frame_content">

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">
</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">用户管理</h3>
		</div>
		<div class="page-content">
			<div class="list_search_head">
				<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<div class="list_search_head_left">
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="name" value="名称">名称</option>
								<option name="additionalInfo" value="备注">备注</option>
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
						<div>
							<i class="fa fa-plus-circle" style="color: #4C99E4;cursor: pointer;height: 30px;width: 15px;line-height: 30px;" onclick="conditionQuery.addDiv();"></i>
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
							
					<div class="list_search_head_right">
						<a href="javascript:add_user();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_user()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
						<a href="javascript:edit_user();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_user()" class="btn btn-sm search_a_delete"> 
							<i class="search_head_delete"></i> 
							<span class="search_head_span">删除</span>
						</a>
					</div>
					<div style="clear:both"></div>
				</form>
			</div>
			<div id="list"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var userPageGrid = new PageGrid({});
function add_user(){
	userPageGrid.createModal("新建用户","${ctx}/user/to_user_add",400,900);
}
function view_user(){
	var ids = userPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	userPageGrid.createModal("查看用户","${ctx}/user/to_user_view?userId="+ids[0],250,900);
}
function edit_user(){
	var ids = userPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	userPageGrid.createModal("编辑用户","${ctx}/user/to_user_edit?userId="+ids[0],300,900);
}
function del_user(){
	var ids = userPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/user/deleteUser",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		userPageGrid.pageRefresh();//刷新列表
			    	}else{
			        	new alertdialog({title:"提示",content:"删除失败!"});
			        }
			    }
			});
		}});
	}else{
		new alertdialog({title:"提示",content:"请选中你需要操作的记录!"});
	}
}

//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	userPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [{
		field : "account",
		title : "用户账号",
		width : 100
		}, {
			field : "userName",
			title : "真实姓名",
			width : 100
		}, {
			field : "msisdn",
			title : "联系电话",
			width : 100
		}, {
			field : "email",
			title : "邮箱",
			width : 100
		}] ;
	userPageGrid.paging("list","userId",columns,'${ctx}/user/datasByPageOfTenant');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		userPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		userPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		userPageGrid.pageExport('${ctx}/user/datasByExportOfTenant',"searchForm",'${ctx}/','用户信息',conditionQuery.getSearchArr());
    	}else{
    		userPageGrid.pageExport('${ctx}/user/datasByExportOfTenant',"searchForm",'${ctx}/','用户信息'); 
    	}
    });
});

</script>
</html>