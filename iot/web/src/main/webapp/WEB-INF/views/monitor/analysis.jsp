<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<link href="${ctx}/css/product_base.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${ctx}/plugins/bootstrap-multiselect/css/bootstrap-multiselect.css" type="text/css">
<script type="text/javascript" src="${ctx}/plugins/echart/echart_show.js"></script>
<script type="text/javascript" src="${ctx}/plugins/echart/echarts-plain.js"></script>
<script type="text/javascript" src="${ctx}/plugins/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="${ctx}/plugins/bootstrap-multiselect/js/bootstrap-multiselect-collapsible-groups.js"></script>
</head>
<style>
html{
	height:100%;
}
#searchForm .control-label{
	padding-right:0px;
	padding-left:0px;
	width:70px;
}
ul{list-style:none;}
ul,li{margin:0px;padding:0px;}
.slect-in { width:100%; border:1px solid #ccc;overflow:hidden }
.option-box { display:none; width:100%; padding:0 10px; border:1px solid #2795dc;position: absolute;background: #fff;z-index:10}
.option-box li { height:24px; line-height:24px; }
.option-box li input[type='checkbox'] { margin-right:5px;zoom:150% ;margin-top:0px;vertical-align:middle;}
.option-box label{vertical-align:middle;margin-bottom:0px;}

button.multiselect.btn{
	position:relative;
	text-align:left;
}
.multiselect-container label{
	text-align:left !important;
}
.multiselect .caret{
	position:absolute;
	right:5px;
	top:13px;
}
.open > .dropdown-menu {
	width:100%;
}
</style>

<body class="skin-blue sidebar-mini">
    <div class="wrapper clearfix">
        <div class="content-wrapper" style="/*min-height: 846px;*/">
	    	<div class="container-fluid main_con  zh_CN">
		        <!-- 设备趋势分析 -->
		        <div class="row">
		            <div class="col-md-12"  style="padding-left: 0px;padding-right: 10px;">
		                <div class="box" style="height:420px;">
		                	<h3 class="box-title">统计分析</h3>
		                    <div class="box-body chart-responsive">
		                        <form action="#" class="form-horizontal" id="searchForm" method="post" style="margin-top:20px">
			                        <div class="list_search_head_left" style="float:left">
			                        	<div>
											<label class="control-label" style="width:40px;">设备:</label>
										</div>
										<div style="min-width:130px;">
											<select name="device_select" id="device_select" class="form-control"></select>
										</div>
										<div>
											<label class="control-label">设备参数:</label>
										</div>
										<div>
										    <div id="attribute_multi" style="width:150px;"></div>
										</div>
										<div>
											<label class="control-label">时间选择:</label>
										</div>
										<div>
											<input type="text" class="Wdate" id="startDate" style="width:135px;"	name="startDate" ><span style="margin: 0px 10px;display: inline-block;">至</span><input type="text" class="Wdate" id="endDate" style="width:135px"	name="endDate">
										</div>
										<div style="margin-left:5px;margin-top:-1px;">
											<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn">
												<i class="search_head_search"></i>
												<span class="search_head_span">查询</span>
											</a>
											<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
												<i class="search_head_export"></i>
												<span class="search_head_span">导出</span>
											</a>
										</div>
			                        </div>
		                        </form>
		                        <div style="clear:both"></div>
		                        <div class="row">
		                        	<div id="analysis" style="height: 300px;margin-left: 10px;"></div>
		                        </div>
		                    </div>
		            	</div>
		        	</div>
		        </div> 
    		</div>
        </div>
        <div class="control-sidebar-bg" style="position: fixed; height: auto;"></div>
    </div>

<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var analysisPageGrid = new PageGrid({});
$(function(){
	initDevice();
	getAnalysis();
	$("#device_select").change(function(){
		initAttribute();
	})
	$("#startDate").bind("focus",function(){
		initStartDate("#endDate");
	})
	$("#endDate").bind("focus",function(){
		initEndDate("#startDate");
	})
});
function initDevice(){
	var items_d;
	$.ajax({
		url: "${ctx}/device/datasByPage?productId=${CUR_PRODUCT_ID}",
		datatype: 'json',
		type: "Post",
		async:false,
		data:{pageSize:9999},
		success: function (data) {
			items_d = data.result;
		}
	});
	var str_d="" ;
	for(var i=0;i<items_d.length;i++){
		str_d+='<option value="'+items_d[i].devId+'">'+items_d[i].name+'</option>';
	}
	$("#device_select").empty();
	$("#device_select").append(str_d);
}
function initAttribute(){
	var devId = $("#device_select").val();
	if($("#device_select").val()!="" && $("#device_select").val()!=null){
		var items_a;
		$.ajax({
            url: "${ctx}/attributesTemplate/queryByDevId?devId="+devId+"&type=1",
			datatype: 'json',
			type: "Post",
			async:false,
			data:{pageSize:9999},
			success: function (data) {
				items_a = data.result;
			}
		});
		var str_a='<select name="attribute_select" id="attribute_select" class="form-control"  multiple="multiple">' ;
		for(var i=0;i<items_a.length;i++){
			str_a+='<option value="'+items_a[i].name+'">'+items_a[i].name+'</option>';
		}
		str_a += '</select>';
		$("#attribute_multi").empty();
		$("#attribute_multi").append(str_a);
		$("#attribute_select").find("option:first-child").attr("selected",true);
		$('#attribute_select').multiselect({buttonWidth: '100%',});
	} else {
		var str_a='<select name="attribute_select" id="attribute_select" class="form-control"  multiple="multiple">' ;
		str_a += '<option value="">请选择</option>';
		str_a += '</select>';
		$("#attribute_multi").empty();
		$("#attribute_multi").append(str_a);
        $('#attribute_select').multiselect({buttonWidth: '100%',});
	}
}

function initAnalysis(startTime,endTime,device,attribute){
	var dataUrl = '${ctx}/telemetryAttributes/statisByDevAndAttributeName';
	dataUrl = dataUrl + '?startTime='+startTime+'&endTime='+endTime+'&devId='+device+'&attributeName='+attribute;
	$('#analysis').showChart_line({url:dataUrl,title:""});
}
function getAnalysis(){
	initAttribute();
	var now = new Date();
	endDate = dataFormat(now.getTime(),'yyyy-MM-dd');
	startDate = dataFormat(new Date(now.getTime() - 7 * 24 * 3600 * 1000),'yyyy-MM-dd');
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	$("#queryBtn").trigger("click");
}
$("#queryBtn").click(function(){
	var device = $("#device_select").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var startTime = startDate+' 00:00:00';
	var endTime = endDate+' 23:59:59';
	var scale = (new Date(endDate).getTime() - new Date(startDate).getTime())/(24 * 3600 * 1000);
	if(startDate=="" || endDate==""){
		new alertdialog({title:"提示",content:"请选择时间段"});return;
	}
	if(scale>30){
		new alertdialog({title:"提示",content:"时间间隔不能超过30天"});return;
	}
	if(!$("#attribute_select").val()){
		new alertdialog({title:"提示",content:"请选择设备参数"});return;
	}
	if($("#attribute_select").val().length>3){
		new alertdialog({title:"提示",content:"最多只能选择三个设备参数"});return;
	}
	var attribute = $("#attribute_select").val().join(",");
	initAnalysis(startTime,endTime,device,attribute);
})
var columns = [{
		field : "deviceNum",
		title : "数据量",
		width : 100
		}
		,{
			field : "uploadTime",
			title : "上报时间",
			width : 100
		}
		] ;
analysisPageGrid.config.columns=columns;
$("#exportBtn").click(function(){
	if(!$("#attribute_select").val()){
		new alertdialog({title:"提示",content:"请选择设备参数"});return;
	}
	var params = {};
	params.startTime = $("#startDate").val()+' 00:00:00';
	params.endTime = $("#endDate").val()+' 23:59:59';
	params.devId = $("#device_select").val();
	params.attributeName = $("#attribute_select").val().join(",");
	params.pageSize = 999999;
	params.orderBy = "attributeName";
	analysisPageGrid.pageExport('${ctx}/telemetryAttributes/exportTelemetryAttributesByDay',"searchForm",'${ctx}/','统计分析数据',null,params);
});



</script>

</body>
</html>
