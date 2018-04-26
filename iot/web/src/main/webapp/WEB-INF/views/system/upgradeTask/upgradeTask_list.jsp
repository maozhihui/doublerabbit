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
							
					<div class="list_search_head_right">
						<a href="javascript:add_updateTask();" id="addBtn" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
                        <a href="javascript:del_updateTask()" class="btn btn-sm search_a_check"  >
                            <i class="search_head_check"></i>
                            <span class="search_head_span">删除</span>
                        </a>
                        <a href="javascript:edit_updateTask()" class="btn btn-sm search_a_check"  >
                            <i class="search_head_check"></i>
                            <span class="search_head_span">修改</span>
                        </a>
						<a href="javascript:startTask()"  id="viewBtn" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">启动任务</span>
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
var updateTaskPageGrid = new PageGrid({});
function add_updateTask(){
	updateTaskPageGrid.createModal("新建","${ctx}/upgradeTask/to_upgradeTask_edit",350,900);
}

function edit_updateTask(){
    var ids = updateTaskPageGrid.getSelectedRowIds();
    if(ids.length==0){
        new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
    }
    if(ids.length>1){
        new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
    }
    updateTaskPageGrid.createModal("编辑","${ctx}/upgradeTask/to_upgradeTask_edit?id="+ids[0],350,900);
}

function del_updateTask(){
    var ids = updateTaskPageGrid.getSelectedRowIds();
    if(ids.length>0){
        new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
            $.ajax({
                url: "${ctx}/upgradeTask/deleteUpgradeTask",
                datatype: 'json',
                type: "Post",
                data:{"ids":ids},
                success: function (data) {
                    if(data.flag=='success'){
                        updateTaskPageGrid.pageRefresh();//刷新列表
                    }else{
                        new alertdialog({title:"提示",content:"删除失败!"+data.message});
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
	updateTaskPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [
			{
				field : "name",
				title : "任务名称",
				width : 150
			}
			, {
				field : "version",
				title : "版本号",
				width : 150
			}
			, {
				field : "status",
				title : "任务状态",
				width : 150,
                formatter: function (value, rowData, rowIndex) {
                    if(value == '0'){
                        return "未开启";
                    }else if (value == '1'){
                        return "任务执行中";
                    }else if (value == '2'){
                        return "任务已完成";
                    }
                }
			}
			, {
				field : "deviceNum",
				title : "升级设备总数",
				width : 150
			}
			, {
				field : "successNum",
				title : "升级成功数",
				width : 150
			}
			, {
				field : "createTime",
				title : "创建时间",
				width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return updateTaskPageGrid.formatDate(value);
                }
			}
            , {
                field : "taskDesc",
                title : "描述",
                width : 150
            }
		] ;
	updateTaskPageGrid.paging("list","id",columns,'${ctx}/upgradeTask/datasByPage');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		updateTaskPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		updateTaskPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		updateTaskPageGrid.pageExport('${ctx}/upgradeTask/datasByExport',"searchForm",'${ctx}/','信息',conditionQuery.getSearchArr());
    	}else{
    		updateTaskPageGrid.pageExport('${ctx}/upgradeTask/datasByExport',"searchForm",'${ctx}/','信息');
    	}
    });
});

function startTask() {
    var ids = updateTaskPageGrid.getSelectedRowIds();
    if(ids.length==0){
        new alertdialog({title:"提示",content:"请选中需要选择的记录!"});return;
    }
    if(ids.length>1){
        new alertdialog({title:"提示",content:"只能选择一条记录!"});return;
    }
    $.ajax({
        url:'${ctx}/upgradeTask/start',
        datatype: 'json',
        type: "Post",
        data:{taskId: ids[0]},
        success: function (data) {
            if(data.flag == 'success'){
                updateTaskPageGrid.pageRefresh();
                new alertdialog({title:"提示",content:"任务开启成功"});
            }else {
                new alertdialog({title:"提示",content:"任务开启失败，"+data.message});
            }
        }
    })

}

</script>
</html>