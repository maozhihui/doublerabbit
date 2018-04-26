<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">管理</h3>
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
						</div>
					</div>
							
					<div class="list_search_head_right">
						<a href="javascript:add_appVersion();" class="btn btn-sm search_a_add">
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_appVersion()"   class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
                        <a href="javascript:del_appVersion()" class="btn btn-sm search_a_check"  >
                            <i class="search_head_check"></i>
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
var appVersionPageGrid = new PageGrid({});
function add_appVersion(){
	appVersionPageGrid.createModal("新建","${ctx}/appVersion/to_appVersion_edit",350,900);
}
function view_appVersion(){
	var ids = appVersionPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	appVersionPageGrid.createModal("查看","${ctx}/appVersion/to_appVersion_view?id="+ids[0],250,900);
}
function edit_appVersion(){
	var ids = appVersionPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	appVersionPageGrid.createModal("编辑","${ctx}/appVersion/to_appVersion_edit?id="+ids[0],300,900);
}
function del_appVersion(){
	var ids = appVersionPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/appVersion/deleteAppVersion",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		appVersionPageGrid.pageRefresh();//刷新列表
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
	appVersionPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [
            {
                field : "fileName",
                title : "文件名",
                width : 150
            },
            {
				field : "version",
				title : "版本名称",
				width : 150
			}
			, {
				field : "fileSize",
				title : "文件大小",
				width : 150
			}
            , {
                field : "createTime",
                title : "创建时间",
                width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return appVersionPageGrid.formatDate(value);
                }
            }
			, {
				field : "fileDesc",
				title : "文件描述",
				width : 150
			}

		] ;
	appVersionPageGrid.paging("list","id",columns,'${ctx}/appVersion/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		appVersionPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		appVersionPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		appVersionPageGrid.pageExport('${ctx}/appVersion/datasByExport',"searchForm",'${ctx}/','信息',conditionQuery.getSearchArr());
    	}else{
    		appVersionPageGrid.pageExport('${ctx}/appVersion/datasByExport',"searchForm",'${ctx}/','信息'); 
    	}
    });
});

</script>
</html>