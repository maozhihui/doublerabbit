<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="frame_content">

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">
.page-content {
    height: calc(100% - 67px);
}

</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="page-title">参数模板管理</h3>
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
								<option name="name" value="name">参数模板名称</option>
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
						<!--  	<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_export"></i>
								<span class="search_head_span">导出</span>
							</a>-->
						</div>
					</div>
							
					<div class="list_search_head_right">
						<!--  <a href="javascript:insert_deviceTemplate()" id="inportBtn" class="btn search_head_searchBtn">
							<i class="search_head_export"></i>
							<span class="search_head_span">导入</span>
						</a>-->
						<a href="javascript:add_deviceTemplate();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_deviceTemplate()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
						<a href="javascript:edit_deviceTemplate();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_deviceTemplate()" class="btn btn-sm search_a_delete"> 
							<i class="search_head_delete"></i> 
							<span class="search_head_span">删除</span>
						</a>
						
					</div>
					<div style="clear: both;"></div>
				</form>
			</div>
			<div id="list"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var deviceTemplatePageGrid = new PageGrid({});
function insert_deviceTemplate(){
	deviceTemplatePageGrid.createModal("导入模板","${ctx}/deviceTemplate/insert",200,700);
}
function add_deviceTemplate(){
// 	deviceTemplatePageGrid.createModal("新建设备模板","${ctx}/deviceTemplate/to_deviceTemplate_edit_index",250,700);
	window.location.href = "${ctx}/deviceTemplate/to_deviceTemplate_edit";
}
function view_deviceTemplate(){
	var ids = deviceTemplatePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	window.location.href = "${ctx}/deviceTemplate/to_deviceTemplate_view_index?deviceTemplateId="+ids[0];
}
function edit_deviceTemplate(){
	var ids = deviceTemplatePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	window.location.href = "${ctx}/deviceTemplate/to_deviceTemplate_edit_index?deviceTemplateId="+ids[0];
}
function del_deviceTemplate(){
	var ids = deviceTemplatePageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/deviceTemplate/deleteDeviceTemplate",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		deviceTemplatePageGrid.pageRefresh();//刷新列表
			    	}else{
			        	new alertdialog({title:"提示",content:data.message});
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
	deviceTemplatePageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [{
		field : "name",
		title : "参数模板名称",
		width : 320
		}, {
			field : "categoryName",
			title : "设备类别",
			width : 320
		}] ;
	deviceTemplatePageGrid.paging("list","deviceTemplateId",columns,'${ctx}/deviceTemplate/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		deviceTemplatePageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		deviceTemplatePageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		deviceTemplatePageGrid.pageExport('${ctx}/deviceTemplate/datasByExport',"searchForm",'${ctx}/','设备模板信息',conditionQuery.getSearchArr());
    	}else{
    		deviceTemplatePageGrid.pageExport('${ctx}/deviceTemplate/datasByExport',"searchForm",'${ctx}/','设备模板信息'); 
    	}
    });
});

</script>
</html>