<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="frame_content">

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">插件管理</h3>
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
						<a href="javascript:add_plugin();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_plugin()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
						<a href="javascript:edit_plugin();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_plugin()" class="btn btn-sm search_a_delete"> 
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
var pluginPageGrid = new PageGrid({});
function add_plugin(){
	pluginPageGrid.createModal("新建插件","${ctx}/plugin/to_plugin_edit",450,900);
}
function view_plugin(){
	var ids = pluginPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	pluginPageGrid.createModal("查看插件","${ctx}/plugin/plugin_view?pluginId="+ids[0],300,900);
}
function edit_plugin(){
	var ids = pluginPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	pluginPageGrid.createModal("编辑插件","${ctx}/plugin/to_plugin_edit?pluginId="+ids[0],450,900);
}
function del_plugin(){
	var ids = pluginPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/plugin/deletePlugin",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		pluginPageGrid.pageRefresh();//刷新列表
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
	pluginPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [{
		field : "name",
		title : "插件名称",
		width : 160
		}
		,{
			field : "clazz",
			title : "插件类型",
			width : 200,
			formatter:clazzFormatter
		}
		,{
			field : "additionalinfo",
			title : "插件描述",
			width : 280
		}
		] ;
	pluginPageGrid.paging("list","pluginId",columns,'${ctx}/plugin/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		pluginPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		pluginPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		pluginPageGrid.pageExport('${ctx}/plugin/datasByExport',"searchForm",'${ctx}/','插件信息',conditionQuery.getSearchArr());
    	}else{
    		pluginPageGrid.pageExport('${ctx}/plugin/datasByExport',"searchForm",'${ctx}/','插件信息'); 
    	}
    });
});

var pluginTypes = {};
<c:forEach var="item" items="${pluginTypes }">
pluginTypes['${item.key}']='${item.value}';
</c:forEach>

function clazzFormatter(valT){
	if(pluginTypes && pluginTypes[valT]){
		return pluginTypes[valT];
	}
	return '';
}
</script>
</html>