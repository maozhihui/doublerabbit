<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">
html,.page-content{
	height:100%;
}
body{
    height: calc(100% - 56px);
    overflow: hidden;
}
#list {
    height: calc(100% - 111px);
}
.page-content{
	padding-top:0px;
	position:relative;
}

</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-content">
			<div class="list_search_head" style="margin-top:34px;">
				<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<div class="list_search_head_right">
						<a href="javascript:add_attributes();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_attributes()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
						<a href="javascript:edit_attributes();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_attributes()" class="btn btn-sm search_a_delete"> 
							<i class="search_head_delete"></i> 
							<span class="search_head_span">删除</span>
						</a>
					</div>
					<div class="list_search_head_left">
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="name" value="名称">参数标识</option>
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
						<!-- 	<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_export"></i>
								<span class="search_head_span">导出</span>
							</a> -->
						</div>
					</div>
					<div style="clear: both;"></div>
				</form>
			</div>
			<div id="list"></div>
			<div class="param_list"  style="display:none">
				<div class="dijitSplitterThumb"></div>
				<div id="show_param" style="height:150px">
				<table width="100%" id="detail_param">
					
				</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var attributesPageGrid = new PageGrid({});
var deviceTemplateId = getUrlParam("deviceTemplateId");
var isTelemetry = "${isTelemetry}";
function add_attributes(){
	var height;
	if(isTelemetry==1){
		height = 400;
	} else {
		height = 500;
	}
	attributesPageGrid.createModal("新建参数模板","${ctx}/attributesTemplate/to_attributesTemplate_edit?isTelemetry="+isTelemetry+"&deviceTemplateId="+deviceTemplateId,height,700);
}
function view_attributes(){
	var ids = attributesPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	var height;
	if(isTelemetry==1){
		height = 300;
	} else {
		height = 400;
	}
	attributesPageGrid.createModal("查看参数模板","${ctx}/attributesTemplate/attributesTemplate_view?attributesTemplateId="+ids[0]+"&isTelemetry="+isTelemetry+"&deviceTemplateId="+deviceTemplateId,height,600);
}
function edit_attributes(){
	var ids = attributesPageGrid.getSelectedRowIds();
	//console.log(ids)
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	var height;
	if(isTelemetry==1){
		height = 400;
	} else {
		height = 500;
	}
	attributesPageGrid.createModal("编辑参数模板","${ctx}/attributesTemplate/to_attributesTemplate_edit?attributesTemplateId="+ids[0]+"&isTelemetry="+isTelemetry+"&deviceTemplateId="+deviceTemplateId,height,700);
}
function del_attributes(){
	var ids = attributesPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/attributesTemplate/deleteAttributesTemplate",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		attributesPageGrid.pageRefresh();//刷新列表
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
	attributesPageGrid.pageRefresh();//刷新列表
}
/*function showParam(tid){
	$.ajax({
		url: "${ctx}/attributesTemplate/get_attributesTemplateData?attributesTemplateId="+tid,
	    datatype: 'json',
	    type: "Post",
	    success: function (data) {
	    	var str='<tr><td width="15%">参数名称:</td><td width="20%">'+data.name+'</td><td width="15%">参数数据类型:</td><td>'+data.valueType+'</td></tr>';
	    	str+='<tr><td>是否为遥测参数:</td><td>'+data.isTelemetry+'</td><td>读与写权限:</td><td>'+data.readOnly+'</td></tr>';
	    	str+='<tr><td>单位:</td><td>'+data.unit+'</td><td>参数描述:</td><td>'+data.description+'</td></tr>';
	    	$("#detail_param").empty();
	    	$("#detail_param").append(str);
	    	$(".param_list").appendTo($("#list")).show();
	    	$("#list_div").css("max-height","calc(100% - 161px)");
	    }
	});
}*/
$(function() {
	var columns;
	
	if(isTelemetry==1){
		columns = [{
			field : "name",
			title : "参数标识",
			width : 150
			},
			{
				field : "valueType",
				title : "参数数据类型",
				width : 150
			},
			{
				field : "unit",
				title : "单位",
				width : 150
			},
			
			{
				field : "description",
				title : "参数描述",
				width : 280
			}] ;
	} else {
		columns = [{
			field : "name",
			title : "参数标识",
			width : 120
			},
			{
				field : "valueType",
				title : "参数数据类型",
				width : 120
			},
			
			{
				field : "unit",
				title : "单位",
				width : 120
			},
			{
				field : "readOnly",
				title : "是否只读",
				width : 150,
				formatter:function(value,rowData){
					if(value==0){
						return '是';
					} else if(value==1){
						return '否';
					}
				}
			},
			{
				field : "description",
				title : "参数描述",
				width : 280
			}] ;
	}
	//attributesPageGrid.setConfig({rowClickFun_name:'showParam',rowClickFun_vals:['attributeTemplateId']});
	attributesPageGrid.paging("list","attributeTemplateId",columns,'${ctx}/attributesTemplate/datasByPage?isTelemetry='+isTelemetry+"&deviceTemplateId="+deviceTemplateId);
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		attributesPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		attributesPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		attributesPageGrid.pageExport('${ctx}/attributesTemplate/datasByExport',"searchForm",'${ctx}/','参数模板信息',conditionQuery.getSearchArr());
    	}else{
    		attributesPageGrid.pageExport('${ctx}/attributesTemplate/datasByExport',"searchForm",'${ctx}/','参数模板信息'); 
    	}
    });
});

</script>
</html>