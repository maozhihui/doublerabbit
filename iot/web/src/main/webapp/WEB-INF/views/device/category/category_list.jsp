<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="frame_content">
 <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<%@include file="/common/include_easyui.jsp"%>
<style type="text/css">
.page-content{
	height: calc(100% - 55px);
}
.datagrid-header-inner,
.datagrid-header-inner > table,
.datagrid-body,
.datagrid-body  table {
  width:100%;
}
.datagrid-header, .datagrid-td-rownumber{
	background: rgb(227, 228, 228);
}
</style>
</head>
<body class="page-header-fixed">
	<div class="page-container">
		<div class="page-heading">
			<h3 class="page-title">设备类别管理</h3>
		</div> 
		<div class="page-content">
			<div class="list_search_head">
				 <form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
				 	<div class="list_search_head_left">
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="name" value="产品类别名称" >设备类别名称</option>
							<!--  	<option name="code" value="产品类别编码" >设备类别编码</option>-->
							</select>
						</div>
						<div class="">
							<select class="form-control" style="display:none;">
								<option value="1" >包含</option>
								<option value="2" >等于</option>
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
						<!-- 	<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_export"></i>
								<span class="search_head_span">导出</span>
							</a> -->
						</div>
					</div>
					<div class="list_search_head_right">
						<security:authorize code="areaM_add">
							<a href="javascript:add();"  id="addDialog" class="btn btn-sm search_a_add"  >
								<i class="search_head_add"></i>
								<span class="search_head_span">新建</span>
							</a>
						</security:authorize>
							<a href="javascript:check();" class="btn btn-sm search_a_check">
								<i class="search_head_check"></i>
								<span class="search_head_span">查看</span>
							</a>
						<security:authorize code="areaM_edit">
							<a href="javascript:edit();"  class="btn btn-sm search_a_modify" >
								<i class="search_head_modify"></i>
								<span class="search_head_span">修改</span>
							</a>
						</security:authorize>
						<security:authorize code="areaM_del">
							<a href="javascript:deleteRow();" class="btn btn-sm search_a_delete" >
								<i class="search_head_delete"></i>
								<span class="search_head_span">删除</span>
							</a>
						</security:authorize>
					</div>
					<div style="clear:both;"></div>
				</form>
	 		</div>
		<!--   <div style="margin: 0px;"></div>-->
		 <div style="min-height: 380px;height:calc(100% - 56px);">
		     <table id="categoryList" style="width:100%"></table>
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
	if($row && $row.depth>=4){
		new alertdialog({title:"提示",content:"不能添加超过4级"});
		return;
	}
	treeGrid.createModal("新建设备类别","${ctx}/category/category_edit?parentId="+parentId,300,800);
}
//编辑
function edit(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.depth==1){
			new alertdialog({title:"提示",content:"根结点不能编辑!"}); return;
		}
		treeGrid.createModal("编辑设备类别","${ctx}/category/category_edit?categoryId="+$row.id + "&parentId="+$row.parentId,300,800);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要编辑的记录!"});
	}
}
//查看
function check(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		treeGrid.createModal("查看设备类别","${ctx}/category/category_view?categoryId="+$row.id + "&parentId="+$row.parentId,200,700);
	}else{
		new alertdialog({title:"提示",content:"请选中你需要查看的记录!"});
	}
}
//删除
function deleteRow(){
	var $row = treeGrid.getSelectedRow();
	if($row){
		if($row.depth==1){
			new alertdialog({title:"提示",content:"根结点不能删除!"}); return;
		}
		if($row.children.length > 0){
			new alertdialog({title:"提示",content:"存在子设备类别，不能删除!"}); return;
		}
		if($row.id==3){
			new alertdialog({title:"提示",content:"此设备类别，不能删除!"}); return;
		}
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			var categoryId = $row.id;
			$.ajax({
                headers:{"X-CSRF-TOKEN":token},
		        url: "${ctx}/category/deleteCategory",
		        datatype: 'json',
		        type: "Post",
		        data:{categoryId:categoryId},
		        success: function (data) {
		        	if(data && data.flag=='success'){
		        		treeGrid.treeQuery("searchForm");//刷新列表
		        	} else{
			        	new alertdialog({title:"提示",content:data.message});
			        }
		        }
			 });
		}});
		$("#categoryList").treegrid('unselectAll');
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
		title : "设备类别名称",
		width : 200
	}] ];
	
	treeGrid.initTreeGrid("categoryList",columns,"${ctx}/category/getTreeGridData");
	
	$("#queryBtn").bind("click",function(){
		treeGrid.treeQuery_2("searchForm");
	});
	$("#exportBtn").bind("click",function(){
		treeGrid.pageExport('${ctx}/category/datasByExport',"searchForm",'${ctx}/','设备类别信息'); 
	});
});

</script>

</html>
