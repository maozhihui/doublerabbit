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
							<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_export"></i>
								<span class="search_head_span">导出</span>
							</a>
						</div>
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
var updateRecordPageGrid = new PageGrid({});

//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	updateRecordPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [
            {
                field : "taskName",
                title : "任务名",
                width : 150
            }
            ,
            {
				field : "version",
				title : "升级版本号",
				width : 150
			}
			, {
				field : "deviceName",
				title : "设备名称",
				width : 150
			}
			, {
				field : "status",
				title : "升级状态",
				width : 150,
                formatter : function (value, rowData, rowIndex) {
                    if (value == 0){
                        return "任务未下发"
                    } else if (value == 1){
                        return "已通知设备"
                    }else if (value == 2){
                        return "下发消息超时"
                    }else if (value == 3){
                        return "设备升级成功"
                    }else if (value == 4){
                        return "设备升级失败"
                    }
                }
			}
			, {
				field : "createTime",
				title : "创建时间",
				width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return updateRecordPageGrid.formatDate(value);
                }
			}
		] ;
	updateRecordPageGrid.paging("list","id",columns,'${ctx}/upgradeRecord/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		updateRecordPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		updateRecordPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		updateRecordPageGrid.pageExport('${ctx}/upgradeRecord/datasByExport',"searchForm",'${ctx}/','信息',conditionQuery.getSearchArr());
    	}else{
    		updateRecordPageGrid.pageExport('${ctx}/upgradeRecord/datasByExport',"searchForm",'${ctx}/','信息');
    	}
    });
});

</script>
</html>