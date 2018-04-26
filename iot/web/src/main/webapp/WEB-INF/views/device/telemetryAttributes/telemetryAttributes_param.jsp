<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">
html,
body,
page-header-fixed,
page-container{
	height:100%;
}

/*this page*/
.page-content {
    height: calc(100% - 90px);
}
#list {
    height: calc(100% - 110px);
}
.list_search_head{
	height:20px;
}

</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<!-- <div class="page-heading">
				<h3 class="panel-title">遥控参数</h3>
		</div> -->
		<div style="padding:10px">
			<img onclick="javascript:returnByUrl();" alt="返回" src="${ctx}/images/icon/return.png" style="display: inline;width: 25px;margin-bottom: 10px;cursor: pointer;">
			<a href="javascript:returnByUrl();" style="font-size: 12px;color: rgb(165, 165, 165);">返回</a>
		</div>
		<nav class="config_toggle">
			<ul style="list-style-type:none">
				<li id="config_history">历史记录</li>
				<li id="config_value"  class="active">遥测参数</li>
				<li id="config_param">配置参数</li>
			</ul>
			<div style="clear:both"></div>
		</nav>
		<div class="page-content">
			<div class="list_search_head">
			</div>
			<div id="list"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
//var id = getUrlParam("id");
var attributesPageGrid = new PageGrid({});
var devId = getUrlParam("id");
$(function() {
	var columns = [{
		field : "name",
		title : "参数标识",
		width : 140
		}, {
			field : "valueType",
			title : "值类型",
			width : 140
		}, {
			field : "unit",
			title : "单位",
			width : 140
		}, {
			field : "updateTime",
			title : "更新时间",
			width : 140,
			formatter : function(value, rowData, rowIndex) {
				return dataFormat(rowData.updateTime,'yyyy-MM-dd hh:mm:ss');
			}	
		}, {
			field : "description",
			title : "描述",
			width : 140
		}] ;
	attributesPageGrid.paging("list","attributeTemplateId",columns,'${ctx}/attributesTemplate/queryByDevId?devId='+devId+'&type=1');
});
//返回
function returnByUrl(){
	var returnUrl = '${returnUrl}';
	if(returnUrl=='' || returnUrl=='-1'){
		returnUrl = '/device/list';
	}
	window.location.href="${ctx}" + returnUrl;
}
//tab 切换
$("#config_history").click(function(){
	window.location.href="${ctx}/telemetryAttributes/list?id="+devId;
})
$("#config_param").click(function(){
	window.location.href="${ctx}/configAttributes/list?id="+devId;
})
</script>
</html>