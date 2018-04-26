<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>

<style type="text/css">
html{
	height:100%;
}
#device_container{
	width:50%;
	height:100%;
	float:left;
	position:relative;
}
#device_container:after{
	content: "";
    width: 2px;
    top: 0px;
    bottom: 0px;
    right: -2%;
    position: absolute;
    border-right: 1px dashed #2795dc;
}
#user_container{
	width:48%;
	height:100%;
	float:right;
}

.form-actions.fluid{
	width:50% !important;
	left:0px;
}
.form-actions.fluid .data_count{
	display:none;
}
.form-actions.fluid .page_size{
	width:100%;
}
#list{
	height: calc(100% - 103px);
} 

</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">设备授权</h3>
		</div>
		<div class="page-content">
			<div id="device_container">
				<div class="list_search_head">
					<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
						<div class="list_search_head_left" style="margin-right:5px;">
							<div class="search_area"></div>
							<div>
								<label class="search_head control-label">设备名称：</label>
							</div>
							<div class="">
								<input style="width: 135px;" type="text" name="name" class="form-control" placeholder="查询内容">
							</div>
							<div class="">
								<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
									<i class="search_head_search"></i>
									<span class="search_head_span">查询</span>
								</a>
							</div>
						</div>
						<div style="clear: both;"></div>
					</form>
				</div>
				<div style="height: 43px;background: #2795dc;color: white;line-height: 43px;padding-left: 20px;">设备列表</div>
				<div id="list"></div>
			</div>
			<div id="user_container">
				<iframe src="${ctx}/device/authorise_user"  style="overflow:auto;height:100%" width="100%" id="authoriseUser" name="authoriseUser" scrolling="no"  frameborder="0"></iframe>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var devicePageGrid = new PageGrid({});
//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	devicePageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [{
		field : "name",
		title : "设备名称",
		width : 180
		}
		,{
			field : "deviceTemplateName",
			title : "参数模板",
			width : 180
		}
		] ;
	devicePageGrid.setConfig({rowClickFun_name:'resetOrg',rowClickFun_vals:['devId']});
	devicePageGrid.paging("list","devId",columns,'${ctx}/device/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		devicePageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		devicePageGrid.pageQuery_2("searchForm"); 
    	}
    });
});
function resetOrg(devId){
	window.frames['authoriseUser'].initTree(devId);
}
</script>
</html>