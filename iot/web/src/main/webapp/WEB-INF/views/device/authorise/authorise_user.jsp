<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<%@include file="/common/include_easyui.jsp"%>
<style type="text/css">
html{
	height:100%;
}
.form-actions.fluid{
	position:absolute !important;
	bottom: -4px !important;
}
.form-actions.fluid .data_count{
	display:none;
}
.form-actions.fluid .page_size{
	width:100%;
}
</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-content">
			<div class="list_search_head">
				<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<!--  <div class="list_search_head_left" style="margin-right:5px;">
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">用户账号：</label>
						</div>
						<div class="">
							<input style="width: 135px;" type="text" name="accout" class="form-control" placeholder="查询内容">
						</div>
						<div class="">
							<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_search"></i>
								<span class="search_head_span">查询</span>
							</a>
						</div>
					</div>-->
							
					<div class="list_search_head_right">
						<a href="javascript:save_authorise();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="fa fa-save" style="font-size:16px;vertical-align: 18%;"></i> 
							<span class="search_head_span">保存</span>
						</a>
					</div>
					<div style="clear:both"></div>
				</form>
			</div>
			<div style="height: 43px;background: #2795dc;color: white;line-height: 43px;padding-left: 20px;">组织列表</div>
			<!--  <div id="list"></div>-->
			<ul id="tree"  checkbox="true" style="margin-top:13px;"></ul> 
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var userPageGrid = new PageGrid({});

//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	userPageGrid.pageRefresh();//刷新列表
}
function save_authorise(){
	getChecked();
}
function getChecked(){
	var nodes = $('#tree').tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	alert(s);
}
function initTree(devId){
	$("#tree").empty();
	$("#tree").tree({  
	    url:"${ctx}/organization/getTreeGridData?devId="+devId,
	}); 
}
 
/*$(function() {
	var columns = [{
		field : "account",
		title : "用户账号",
		width : 180
		}, {
			field : "userName",
			title : "真实姓名",
			width : 180
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
});*/

</script>
</html>