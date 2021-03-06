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
				<h3 class="panel-title">活动告警管理</h3>
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
                                <option name="devName">设备名称</option>
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
var activeAlarmPageGrid = new PageGrid({});


//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	activeAlarmPageGrid.pageRefresh();//刷新列表
}

$(function() {
    var productId = "${CUR_PRODUCT_ID}";

    var columns = [
	        {
				field : "devName",
				title : "设备名称",
				width : 150
			}
			, {
				field : "alarmName",
				title : "告警id",
				width : 150
			}
			, {
				field : "alarmContent",
				title : "告警内容",
				width : 150
			}
			, {
				field : "startTime",
				title : "告警时间",
				width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return activeAlarmPageGrid.formatDate(value);
                }
			}
		] ;
	activeAlarmPageGrid.paging("list","id",columns,'${ctx}/activeAlarm/datasByPage',{"productId":productId});
	$("#queryBtn").click(function(){
        if($(".add-search-div").size()!=0){
    		activeAlarmPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr(),{"productId":productId});
    	}else{
    		activeAlarmPageGrid.pageQuery_2("searchForm",{},{"productId":productId});
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		activeAlarmPageGrid.pageExport('${ctx}/activeAlarm/datasByExport',"searchForm",'${ctx}/','活动告警信息',conditionQuery.getSearchArr());
    	}else{
    		activeAlarmPageGrid.pageExport('${ctx}/activeAlarm/datasByExport',"searchForm",'${ctx}/','活动告警信息'); 
    	}
    });
});

</script>
</html>