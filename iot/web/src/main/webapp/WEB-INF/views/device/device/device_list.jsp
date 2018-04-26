<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="frame_content">

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">
/*#list table td{
	line-height: 16px !important;
}*/
</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">设备管理</h3>
		</div>
		<div class="page-content">
			<div class="list_search_head">
						 <form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
							<div class="list_search_head_left">
								<div class="search_area">
								
								</div>
								<div>
									<label class="search_head control-label">查询条件：</label>
								</div>
								<div class="">
									<select class="form-control selectC" name="selectC">
                                        <option name="status" value="设备状态">设备状态</option>
										<option name="name" value="设备名称">设备名称</option>
										<option name="hardIdentity" value="硬件标识">硬件标识</option>
									</select>
								</div>
                                <div class="">
                                    <select class="form-control selectV" id="status_V">
                                        <option value=""></option>
                                        <option value="1">在线</option>
                                        <option value="0">离线</option>
                                    </select>
                                </div>

								<div class="">
									<input style="width: 135px;display:none" type="text" name="searchText" class="form-control searchText" placeholder="查询内容">
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
								<div class="dropdown" style="float:left;">
    								<button type="button" class="btn btn-small dropdown-toggle" data-toggle="dropdown">
     									更多 <span class="caret"></span>
    								</button>
								    <ul class="dropdown-menu">
								      <li><a href="javascript:inportData();">导入</a></li>
								      <li><a href="javascript:exportData();">导出</a></li>
								      <li><a href="javascript:exportData(1);">下载模板</a></li>
								    </ul>
  								</div>
							</div>
							
							<div class="list_search_head_right">
								
 
								<!-- <a href="javascript:insert_device()" id="inportBtn" class="btn search_head_searchBtn">
									<i class="search_head_export"></i>
									<span class="search_head_span">导入</span>
								</a> -->
								<a href="javascript:add_device();" id="addNeInfo" class="btn btn-sm search_a_add"> 
									<i class="search_head_add"></i> 
									<span class="search_head_span">新建</span>
								</a>
								<a href="javascript:view_device()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
									<i class="search_head_check"></i>
									<span class="search_head_span">查看</span>
								</a>
								<a href="javascript:edit_device();" class="btn btn-sm search_a_modify"> 
									<i class="search_head_modify"></i> 
									<span class="search_head_span">修改</span>
								</a> 
							<!--  	<a href="javascript:view_configParam();" class="btn btn-sm search_a_modify"> 
									<i class="search_head_modify"></i> 
									<span class="search_head_span">配置参数</span>
								</a>
								<a href="javascript:view_telemetryParam();" class="btn btn-sm search_a_modify"> 
									<i class="search_head_modify"></i> 
									<span class="search_head_span">遥测参数</span>
								</a> -->
								<a href="javascript:del_device()" class="btn btn-sm search_a_delete"> 
									<i class="search_head_delete"></i> 
									<span class="search_head_span">删除</span>
								</a>
								
							</div>
							<div style="clear: both;"></div>
						</form>
						
			</div>
		
			<div id="list"></div>
		
</div>
</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var devicePageGrid = new PageGrid({});
function inportData(){
	devicePageGrid.createModal("导入模板","${ctx}/device/insert",200,700);
}
function add_device(){
	devicePageGrid.createModal("新建设备","${ctx}/device/to_device_edit",400,900);
}
function view_device(){
	var ids = devicePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	devicePageGrid.createModal("查看设备","${ctx}/device/device_view?deviceId="+ids[0],320,900);
}
function edit_device(){
	var ids = devicePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	devicePageGrid.createModal("编辑设备","${ctx}/device/to_device_edit?deviceId="+ids[0],350,900);
}
function view_configParam(){
	var ids = devicePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能选择一条记录!"});return;
	}
	devicePageGrid.createModal("配置参数","${ctx}/configAttributes/list?devId="+ids[0],420,900);
}
function view_telemetryParam(){
	var ids = devicePageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能选择一条记录!"});return;
	}
	devicePageGrid.createModal("遥测参数","${ctx}/telemetryAttributes/list?id="+ids[0],450,900);
}

function del_device(){
	var ids = devicePageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/device/deleteDevice",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		devicePageGrid.pageRefresh();//刷新列表
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
	devicePageGrid.pageRefresh();//刷新列表
}
var columns,columns_export;
$(function() {

    var productId = "${CUR_PRODUCT_ID}";
	columns = [{
		field : "name",
		title : "设备名称",
		width : 100
		}
		,{
			field : "hardIdentity",
			title : "硬件标识",
			width : 100
		}
		,{
			field : "deviceTemplateName",
			title : "参数模板",
			width : 80
		}
		,{
			field : "sn",
			title : "设备编号",
			width : 100
		}
		,{
            field : "isGateWay",
            title : "是否网关",
            width : 100,
            formatter : function(value, rowData, rowIndex) {
				if(value==0){
					return "否";
				} else {
					return "是";
				}
			}
        }
		,{
			field : "statusStr",
			title : "当前状态",
			width : 80,
		},{
            field : "position",
            title : "设备位置",
            width : 80
        },{
            field : "isLoraStr",
            title : "同步lora",
            width : 80
        },{
            field : "appKey",
            title : "appKey",
            width : 80
        }, {
            field : "description",
            title : "设备描述",
            width : 100
        }, {
			field : "deviceCamera",
			title : "操作",
			width : 100,
			formatter : function(value, rowData, rowIndex) {
			    if(value){
                    var str='<a href="${ctx}/device/showCamera?devId='+rowData.devId+'"><i class="fa fa-cogs"></i>查看视频</a>';
                    return str;
			    }else{
                    var str='<a href="${ctx}/telemetryAttributes/list?id='+rowData.devId+'"><i class="fa fa-cogs"></i>参数管理</a>';
                    return str;
                }

			}
		}
		] ;
	columns_export = [{
		field : "name",
		title : "设备名称",
		width : 100
		}
		,{
			field : "hardIdentity",
			title : "硬件标识",
			width : 100
		}
		,{
			field : "deviceTemplateName",
			title : "参数模板",
			width : 80
		}
		,{
			field : "sn",
			title : "设备编号",
			width : 100
		}
		,{
            field : "isGateWayStr",
            title : "是否网关",
            width : 100,
        },
        {
            field : "position",
            title : "设备位置",
            width : 80
        },{
            field : "isLoraStr",
            title : "是否同步lora",
            width : 80
        },{
            field : "appKey",
            title : "设备appKey",
            width : 80
        },{
            field : "statusStr",
            title : "当前状态",
            width : 80,
        },{
			field : "description",
			title : "设备描述",
			width : 100
		},
		] ;
	devicePageGrid.paging("list","devId",columns,'${ctx}/device/datasByPage?productId='+productId);
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		devicePageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		devicePageGrid.pageQuery_2("searchForm"); 
    	}
    });
	/*$("#exportBtn").click(function(){
		devicePageGrid.config.columns=columns_export;
    	if($(".add-search-div").size()!=0){
    		devicePageGrid.pageExport('${ctx}/device/datasByExport?productId=${CUR_PRODUCT_ID}',"searchForm",'${ctx}/','设备信息',conditionQuery.getSearchArr());
    	}else{
    		devicePageGrid.pageExport('${ctx}/device/datasByExport?productId=${CUR_PRODUCT_ID}',"searchForm",'${ctx}/','设备信息'); 
    	}
    	devicePageGrid.config.columns=columns;
    });*/
});
function exportData(param){
	devicePageGrid.config.columns=columns_export;
	var url;
	if(param==1){
	    url='${ctx}/device/exportDeviceModelExcel';
    } else {
        url='${ctx}/device/datasByExport?productId=${CUR_PRODUCT_ID}';
    }
	if($(".add-search-div").size()!=0){
		devicePageGrid.pageExport(url,"searchForm",'${ctx}/','设备信息',conditionQuery.getSearchArr());
	}else{
		devicePageGrid.pageExport(url,"searchForm",'${ctx}/','设备信息');
	}
	devicePageGrid.config.columns=columns;
}

$(".selectC").change(function(){
    var selectName = $(this).find("option:selected").attr("name");
    $(".selectV").val("").hide();
    $(".searchText").val("").hide();
    if(selectName=="status"){
        $("#status_V").show();
    }else {
        $(".searchText").show();
    }
})
    
</script>
</html>