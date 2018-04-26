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
.page-content {
    height: calc(100% - 90px);
}
.param_content {
    height: calc(100% - 79px);
}
.dijitSplitterThumb {
	background-color:#fff;
	border: 1px solid RGB(167,211,246);
}

.fa{
	vertical-align:middle;
}
.list_search_head{
	height:20px;
}
</style>
</head>

<body class="page-header-fixed">
	<div class="page-container">
		<div style="padding:10px">
			<img onclick="javascript:returnByUrl();" alt="返回" src="${ctx}/images/icon/return.png" style="display: inline;width: 25px;margin-bottom: 10px;cursor: pointer;">
			<a href="javascript:returnByUrl();" style="font-size: 12px;color: rgb(165, 165, 165);">返回</a>
		</div>
		<nav class="config_toggle">
			<ul style="list-style-type:none">
				<li id="config_history">历史记录</li>
				<li id="config_value">遥测参数</li>
				<li id="config_param"   class="active">配置参数</li>
			</ul>
			<div style="clear:both"></div>
		</nav>
		<div class="page-content">
			<div class="list_search_head">
				<!--  <form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<div class="list_search_head_left">
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="name" value="名称">名称</option>
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
						<div>
			            	<label class="search_head control-label">上报时间：</label>
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
				</form>-->
			</div>
			<div class="param_content">
				<div id="list" class="hasControl"></div>
				<div class="param_operate">
					<button class="btn btn-operate"  onclick="javascript:configAttributesPageGrid.pageRefresh()">
						<i class="fa fa-plus"></i>
						<span class="search_head_span" >刷新</span>
					</button>
					<button class="btn btn-operate"  onclick="currentOperate('query');">
						<i class="fa fa-plus"></i>
						<span class="search_head_span" >实时查询</span>
					</button>
					<button class="btn btn-operate"  onclick="currentOperate('set');">
						<i class="fa fa-plus"></i>
						<span class="search_head_span" >实时设置</span>
					</button>
				</div>
				<div class="dijitSplitterThumb"></div>
				<div class="operate_list">
					<div id="show_control" style="width:100%;">
						<table id="control_param" style="width:100%;height:100%;display:block">
						<thead>
							<tr>
								<th>操作</th>
								<th>时间</th>
								<th>消息内容</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var configAttributesPageGrid = new PageGrid({});
var devId = getUrlParam("id");
function add_configAttributes(){
	configAttributesPageGrid.createModal("新建设备配置属性","${ctx}/configAttributes/to_configAttributes_edit",450,900);
}
function view_configAttributes(){
	var ids = configAttributesPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要查看的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能查看一条记录!"});return;
	}
	configAttributesPageGrid.createModal("查看设备配置属性","${ctx}/configAttributes/configAttributes_view?id="+ids[0],450,900);
}
function edit_configAttributes(){
	var ids = configAttributesPageGrid.getSelectedRowIds();
	if(ids.length==0){
		new alertdialog({title:"提示",content:"请选中需要编辑的记录!"});return;
	}
	if(ids.length>1){
		new alertdialog({title:"提示",content:"只能编辑一条记录!"});return;
	}
	configAttributesPageGrid.createModal("编辑设备配置属性","${ctx}/configAttributes/to_configAttributes_edit?id="+ids[0],450,900);
}
function del_configAttributes(){
	var ids = configAttributesPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/configAttributes/deleteConfigAttributes",
			    datatype: 'json',
			    type: "Post",
			    data:{"ids":ids},
			    success: function (data) {
			    	if(data.flag=='success'){
			    		configAttributesPageGrid.pageRefresh();//刷新列表
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
var setTime;
function control_log(operate,devId,configAttributeId,idsName){
	setTime = window.setTimeout(function(){control_log(operate,devId,configAttributeId,idsName)}, 5000);
	$.ajax({
		url:'${ctx}/configAttributes/respConfigAttributes',
		datatype:'json',
		type:'post',
		async:false,
		data:{"configAttributeId":configAttributeId,"devId":devId},
		success:function(data){
			if(data.length>0){
				//window.clearTimeout(setTime);
				var operateText;
				var responseText;
				if(operate=="set"){
					operateText= "实时设置";
				} else {
					operateText= "实时查询";
				}
				if(data[0].flag=="success"){
					responseText="命令下发成功";
					configAttributesPageGrid.pageRefresh();//刷新列表
				} else {
					responseText="命令下发超时";
				}
				$("#control_param tbody").append('<tr><td>'+operateText+'</td><td>'+dataFormat(data[0].time,'yyyy-MM-dd hh:mm:ss')+'</td><td>'+responseText+'</td></tr>');
				$("#control_param tbody").get(0).scrollTop = $("#control_param tbody").get(0).scrollHeight;
			}
		}
	})
}
//实时查询 、实时设置
function currentOperate(operate){
	window.clearTimeout(setTime);
	var data={};
	var url;
	var ids = configAttributesPageGrid.getSelectedRowIds();
	if(ids.length==0 /*&& operate=="set"*/){
		new alertdialog({title:"提示",content:"请选中你需要操作的记录!"});return;
	} else if(ids.length>1 && operate=="query"){
		new alertdialog({title:"提示",content:"只能操作一条记录!"});return;
	} else {
		if(operate=="set"){
		    var attributes=[];
		    for(var index in ids){
                if($("input#"+ids[index]).val()==""){
                    new alertdialog({title:"提示",content:"设置值不能为空!"});return;
                }
                var attribute = {};
                attribute.configAttributeId = ids[index];
                attribute.value = $("input#"+ids[index]).val();
                attribute.attributeName = $("input#"+ids[index]).attr("name");
                attributes.push(attribute);
            }
            data.configAttributes=JSON.stringify(attributes);
			data.devId = devId;
			url = "${ctx}/configAttributes/setConfigAttributes";
		} else {
			data.configAttributeId = ids[0];
			data.devId = devId;
			url = "${ctx}/configAttributes/queryConfigAttributes";
		}
		$.ajax({
			url:url,
			datatype:'json',
			type:'post',
			data:data,
			async:false,
			success:function(data){
				var responseText;
				var time = new Date();
				time = time.getTime();
				if(operate=="set"){
					responseText="实时设置";
				} else {
					responseText="实时查询";
				}
			//	$("#control_param tbody").empty();
				if(data.flag=='success'){
					$("#control_param tbody").append('<tr><td>'+responseText+'</td><td>'+dataFormat(time,'yyyy-MM-dd hh:mm:ss')+'</td><td>命令正在下发中</td></tr>');
					$("#control_param tbody").get(0).scrollTop = $("#control_param tbody").get(0).scrollHeight;
				}else{
		    		$("#control_param tbody").append('<tr><td>'+responseText+'</td><td>'+dataFormat(time,'yyyy-MM-dd hh:mm:ss')+'</td><td>'+data.message+'</td></tr>');
		    		$("#control_param tbody").get(0).scrollTop = $("#control_param tbody").get(0).scrollHeight;
		        }
			}
		})
	}
}

//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	configAttributesPageGrid.pageRefresh();//刷新列表
}

$(function() {
	var columns = [
		{
		field : "attributeName",
		title : "参数标识",
		width : 160
		},
		{
			field : "value",
			title : "参数值",
			width : 160
			},
		{
			field : "newValue",
			title : "设置值",
			width : 160,
			formatter:function(value, rowData, rowIndex){
				if(rowData.readOnly==0){ //0是只读
					return '<span></span>';
				} else {
					return '<div style="padding-right:5px;"><input type="text" class="form-control" id="'+rowData.configAttributeId+'" name="'+rowData.attributeName+'"/></div>';
				}
			}
		},
		{
			field : "createTime",
			title : "更新时间",
			width : 160,
			formatter : function(value, rowData, rowIndex) {
				return dataFormat(rowData.updateTime,'yyyy-MM-dd hh:mm:ss');
			}	
		},
		{
			field : "description",
			title : "描述",
			width : 160
		}
		] ;
	//configAttributesPageGrid.setConfig({rowClickFun_name:'showParam',rowClickFun_vals:['configAttributeId']});
	configAttributesPageGrid.paging("list","configAttributeId",columns,'${ctx}/configAttributes/datasByPage?devId='+devId);
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		configAttributesPageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    	}else{
    		configAttributesPageGrid.pageQuery_2("searchForm"); 
    	}
    });
	$("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		configAttributesPageGrid.pageExport('${ctx}/configAttributes/datasByExport?devId='+devId,"searchForm",'${ctx}/','设备配置属性信息',conditionQuery.getSearchArr());
    	}else{
    		configAttributesPageGrid.pageExport('${ctx}/configAttributes/datasByExport?devId='+devId,"searchForm",'${ctx}/','设备配置属性信息');
    	}
    });
	$("#startDate").bind("focus",function(){
		var minDate;
		var maxDate;
		if($("#endDate").val()!==""){
			maxDate = $("#endDate").val();
		} else {
			maxDate ='%y-%M-%d';
		}
		WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate: maxDate});
	})
	$("#endDate").bind("focus",function(){
		var minDate;
		if($("#startDate").val()!==""){
			minDate = $("#startDate").val();
			WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate: minDate,maxDate: '%y-%M-%d'});
		} else {
			WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate: '%y-%M-%d'});
		}
		
	})
});
/*$("#control_param").colResizable({
	liveDrag:true, 
	gripInnerHtml:"<div class='grip'></div>", 
	draggingClass:"dragging", 
	resizeMode:'overflow'
});*/

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
$("#config_value").click(function(){
	window.location.href="${ctx}/telemetryAttributes/param?id="+devId;
})

</script>
</html>