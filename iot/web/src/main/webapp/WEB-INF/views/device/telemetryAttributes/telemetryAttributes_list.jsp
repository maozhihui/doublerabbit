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
				<li id="config_history" class="active">历史记录</li>
				<li id="config_value">遥测参数</li>
				<li id="config_param">配置参数</li>
			</ul>
			<div style="clear:both"></div>
		</nav>
		<div class="page-content">
			<div class="list_search_head" style="padding-right:10px;">
				<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<div class="list_search_head_left">
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">参数名称：</label>
						</div>
						<div style="margin-right:10px;">
							<select class="form-control selectC" name="telemetryAttributeId" id="telemetryAttributeId">
							</select>
						</div>
						<div>
			            	<label class="search_head control-label">更新时间：</label>
				        </div>
				        <div  style="margin-right:10px;">
				        	<input type="text" class="Wdate" id="startDate" style=""	name="startDate" ><span style="margin: 0px 10px;display: inline-block;">至</span><input type="text" class="Wdate" id="endDate" style=""	name="endDate">
				        </div>
						<div class="">
							<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_search"></i>
								<span class="search_head_span">查询</span>
							</a>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div class="list_search_head_right"></div>
				</form>
			</div>
			<div id="list"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var telemetryAttributesPageGrid = new PageGrid({});
var devId = getUrlParam("id");


//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	telemetryAttributesPageGrid.pageRefresh();//刷新列表
}

$(function() {
	initAttribute();
	var columns = [{
		field : "telemetryAttributeName",
		title : "参数标识",
		width : 220
		}, {
			field : "value",
			title : "参数值",
			width : 220
		}, {
			field : "updateTime",
			title : "更新时间",
			width : 220,
//			formatter : function(value, rowData, rowIndex) {
//				return dataFormat(rowData.updateTime,'yyyy-MM-dd hh:mm:ss');
//			}
		}] ;
	var now = new Date();
	var telemetryAttributeId = $("#telemetryAttributeId").val();
	var telemetryAttributeName = $("#telemetryAttributeId option:selected").text();
	var startDate = dataFormat(new Date(now.getTime() - 3 * 24 * 3600 * 1000),'yyyy-MM-dd');
	var endDate = dataFormat(new Date(now.getTime()),'yyyy-MM-dd');
	var startDate_2 = startDate+" 00:00:00";
	//时间加一
	var endDate_2 = dataFormat(new Date(now.getTime()+ 1 * 24 * 3600 * 1000),'yyyy-MM-dd 00:00:00');
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	//telemetryAttributesPageGrid.setConfig({rowClickFun_name:'showParam',rowClickFun_vals:['telemetryAttributeId']});
	if(telemetryAttributeName){
		telemetryAttributesPageGrid.paging("list","updateTime",columns,'${ctx}/historyData/datasByPage?devId='+devId,{'telemetryAttributeName':telemetryAttributeName,'startDate':startDate_2,'endDate':endDate_2});
	} else {
		telemetryAttributesPageGrid.paging("list","updateTime",columns,'${ctx}/historyData/datasByPage?devId='+devId,{'telemetryAttributeName':" ",'startDate':startDate_2,'endDate':endDate_2});
	}
	
	$("#queryBtn").click(function(){
		var telemetryAttributeId = $("#telemetryAttributeId").val();
		var telemetryAttributeName = $("#telemetryAttributeId option:selected").text();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var data = new Date(endDate);
		var startDate_2 = startDate+" 00:00:00";
		//时间加一
		var endDate_2 = dataFormat(new Date(data.getTime()+ 1 * 24 * 3600 * 1000),'yyyy-MM-dd 00:00:00');
    	if($(".add-search-div").size()!=0){
    		telemetryAttributesPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr(),{'telemetryAttributeName':telemetryAttributeName,'startDate':startDate_2,'endDate':endDate_2});
    	}else{
    		if(telemetryAttributeName){
    			telemetryAttributesPageGrid.pageQuery_2("searchForm",null,{'telemetryAttributeName':telemetryAttributeName,'startDate':startDate_2,'endDate':endDate_2}); 
    		} else {
    			telemetryAttributesPageGrid.pageQuery_2("searchForm",null,{'telemetryAttributeName':" ",'startDate':startDate_2,'endDate':endDate_2}); 
    		}
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		telemetryAttributesPageGrid.pageExport('${ctx}/telemetryAttributes/datasByExport',"searchForm",'${ctx}/','遥测参数信息',conditionQuery.getSearchArr());
    	}else{
    		telemetryAttributesPageGrid.pageExport('${ctx}/telemetryAttributes/datasByExport',"searchForm",'${ctx}/','遥测参数信息'); 
    	}
    });
	$("#startDate").bind("focus",function(){
		initStartDate("#endDate");
	})
	$("#endDate").bind("focus",function(){
		initEndDate("#startDate");
	})
});
function initAttribute(){
	var items_a;
	$.ajax({
		url: "${ctx}/attributesTemplate/queryByDevId?devId="+devId+"&type=1",
		datatype: 'json',
		type: "GET",
		async:false,
		success: function (data) {
			items_a = data.result;
		}
	});
	var str_a="" ;
	for(var i=0;i<items_a.length;i++){
		str_a+='<option value="'+items_a[i].attributesTemplateId+'">'+items_a[i].name+'</option>';
	}
	$("#telemetryAttributeId").empty();
	$("#telemetryAttributeId").append(str_a);
}
//返回
function returnByUrl(){
	var returnUrl = '${returnUrl}';
	if(returnUrl=='' || returnUrl=='-1'){
		returnUrl = '/device/list';
	}
	window.location.href="${ctx}" + returnUrl;
}
//tab 切换
$("#config_value").click(function(){
	window.location.href="${ctx}/telemetryAttributes/param?id="+devId;
})
$("#config_param").click(function(){
	window.location.href="${ctx}/configAttributes/list?id="+devId;
})
</script>
</html>