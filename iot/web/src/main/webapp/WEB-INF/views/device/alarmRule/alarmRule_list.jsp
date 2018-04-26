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
				<h3 class="panel-title">告警规则管理</h3>
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
                                <option name="name">规则名称</option>
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
						<a href="javascript:add_alarmRule();" id="addBtn" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:edit_alarmRule();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_alarmRule()" class="btn btn-sm search_a_delete"> 
							<i class="search_head_delete"></i> 
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
var alarmRulePageGrid = new PageGrid({});
function add_alarmRule(){
	alarmRulePageGrid.createModal("新建告警规则","${ctx}/alarmRule/to_alarmRule_edit",450,900);
}
function view_alarmRule(){
	var ids = alarmRulePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	alarmRulePageGrid.createModal("查看告警规则","${ctx}/alarmRule/to_alarmRule_view?id="+ids[0],450,900);
}
function edit_alarmRule(){
	var ids = alarmRulePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	alarmRulePageGrid.createModal("编辑告警规则","${ctx}/alarmRule/to_alarmRule_edit?id="+ids[0],450,900);
}
function del_alarmRule(){
	var ids = alarmRulePageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/alarmRule/deleteAlarmRule",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		alarmRulePageGrid.pageRefresh();//刷新列表
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
	alarmRulePageGrid.pageRefresh();//刷新列表
}

$(function() {
    var productId = "${CUR_PRODUCT_ID}";
	var columns = [
			{
				field : "name",
				title : "规则名称",
				width : 150
			}
			, {
				field : "attribute",
				title : "属性",
				width : 150
			}, {
                field : "ruleCondition",
                title : "条件",
                width : 150
            }, {
                field : "value",
                title : "值",
                width : 150
            }
			, {
				field : "type",
				title : "类型",
				width : 150,
                formatter : function(value, rowData, rowIndex) {
                    if(value==1){
                        return "告警通知";
                    } else if(value==2){
                        return "告警恢复";
                    }
                }
			}
			, {
				field : "alarmContent",
				title : "告警内容",
				width : 150
			}
			, {
				field : "createTime",
				title : "创建时间",
				width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return alarmRulePageGrid.formatDate(value);
                }
			}
		] ;
	alarmRulePageGrid.paging("list","id",columns,'${ctx}/alarmRule/datasByPage',{"productId":productId});
	$("#queryBtn").click(function(){
        if($(".add-search-div").size()!=0){
    		alarmRulePageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr(),{"productId":productId});
    	}else{
    		alarmRulePageGrid.pageQuery_2("searchForm",{},{"productId":productId});
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		alarmRulePageGrid.pageExport('${ctx}/alarmRule/datasByExport',"searchForm",'${ctx}/','告警规则信息',conditionQuery.getSearchArr());
    	}else{
    		alarmRulePageGrid.pageExport('${ctx}/alarmRule/datasByExport',"searchForm",'${ctx}/','告警规则信息'); 
    	}
    });
});

</script>
</html>