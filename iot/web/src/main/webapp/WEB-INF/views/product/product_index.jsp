<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<link href="${ctx}/css/product_base.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/plugins/echart/echart_show.js"></script>
<script type="text/javascript" src="${ctx}/plugins/echart/echarts-plain.js"></script>
<style type="text/css">
.button.to-create,
.button.hollow{
	border-radius:0px;
	margin:10px 10px 10px 0px;
}
	
</style>
</head>


<body class="skin-blue sidebar-mini">
    <div class="wrapper clearfix">
        <div class="content-wrapper" style="min-height: 846px;">
		    <div class="container-fluid main_con  zh_CN">
		        <div class="row callout callout-info">
		            <div class="col-md-10 col-xs-10">
		                <div class="message-top">
		                    <h4>
		                        <label>产品名称：${product.name }</label>
		                    </h4>
		                    <a class="button hollow expand to-create product_operate" id="create" href="javascript:edit_product('${product.productId }')">
		                        <i class="fa fa-pencil" style="margin-right: 6px;"></i></i>编辑
		                    </a>
		                    <a class="button hollow expand icon-info j_details" href="javascript:view_product('${product.productId }')">
		                    	<i class="fa fa-navicon" style="margin-right: 6px;"></i>产品详情
		                    </a>
		                </div>
		                <p class="pro-describe">产品简介：${product.brief }</p>
		                <p class="pro-info">设备接入协议：<span>${product.accessProtocol }</span></p><span class="vertical">|</span>
		                <p class="pro-info">创建时间：<span>${product.createTime }</span></p><span class="vertical">|</span>
		            </div>
		        </div>
		        <!-- 概览 -->
		        <div class="row row-synopsis css-overview">
		            <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
		                <div class="info-box">
		                    <a href="" style="float:left">
		                        <span class="info-box-icon" style="width:115px">
		                        	<!--  <i class="icon-device"></i>-->
		                        	<i class="fa fa-desktop icon-rounded" style="background: rgb(0, 172, 237);"></i>
		                        </span>
		                    </a>
		                    <div class="info-box-content">
		                       <!--  <a href=""> -->
		                            <div class="info-box-text">
		                            	<span>接入设备总数 <span id="devNum_all" class="info-box-number" style="display:inline-block;font-size:34px;"></span> 台</span>
		                            	<br/>
		                            	<span>网关设备数 <span id="devNum_gateWay" class="info-box-number" style="display:inline-block;font-size:34px;"></span> 台</span>
		                            </div>
		                       <!--   </a>-->
		                    </div>
		                </div>
		            </div>
		          	<!-- /.col -->
		            <div class="globleColumn_3th">
		                <div class="info-box">
		                	<a href="" style="float:left">
		                    	<span class="info-box-icon">
		                    		<!-- <i class="icon-light"></i> -->
		                    		<i class="fa fa-line-chart icon-rounded" style="background: #5e84b5;"></i>
		                    	</span>
		                    </a>
		                    <div class="info-box-content">
		                        <div class="info-box-text">
		                        	<span>今日遥测数据</span>
		                        	<span class="info-box-number"><span id="dataNum"></span><small>条</small></span>
		                        </div>
		                        <small class="pull-bottom"></small>
		                    </div>
		                </div>
		            </div>
		            <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
		                <div class="info-box">
		                	<a href="" style="float:left">
			                    <span class="info-box-icon">
			                    	<!--  <i class="icon-key"></i>-->
			                    	<i class="fa fa-info icon-rounded" style="background:#ce875c;"></i>
			                    </span>
		                    </a>
		                    <div class="info-box-content">
		                        <div class="info-box-text">
		                        	<span>活动告警数</span>
		                        	<span class="info-box-number"><span id="ruleNum"></span><small>个</small></span>
		                        </div>
		                      	<small class="pull-bottom"></small>
		                    </div>
		                </div>
		            </div>
		            
		            
		         
		            <div class="clearfix visible-sm-block"></div>
		        </div>
		     
		      
		      
		        <div class="row">
		            <div class="col-md-12" style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                    <!-- /.box-header -->
		                    <h3 class="box-title">新增设备统计</h3>
		                    <div class="box-body chart-responsive">
		                        <div class="row">
		                           <!--   <div class="col-md-7 col-xs-8 box-header">
		                            	<h3 class="box-title">新增设备统计</h3>
		                            </div>-->
		                            <div class="col-md-3 col-xs-4 justify-align">
		                                <button id="deviceStas_month" onclick="getDeviceStat('month');" type="button" class="button expand" >最近一月</button>&nbsp;&nbsp;
		                                <button id="deviceStas_week" onclick="getDeviceStat('week');" type="button" class="button expand" style="background: rgb(150, 151, 160);">最近一周</button>
		                            </div>
		                            <div class="col-md-8 col-xs-7 justify-align add-button-area" style="text-align:right">
		                                <a class="button expand i" id="addDevice_b" href="javascript:add_device();">
		                                    <i class="fa fa-plus" style="font-size:14px;top:8px;left:15px;"></i>添加设备
		                                </a>
		                            </div>
		                        </div>
		                        <div class="row">
		                        	<div id="deviceStas" style="height: 300px;margin-left: 10px;"></div>
		                        </div>
		                    </div>
		            	</div>
		        	</div>
		        </div>
		        <!-- 数据点上传趋势 -->
		        <div class="row">
		            <div class="col-md-12" style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                	<h3 class="box-title">遥测数据统计</h3>
		                    <!-- /.box-header -->
		                    <div class="box-body chart-responsive" >
		                        <div class="row ">
		                            <!--  <div class="col-md-7 col-xs-8 box-header">
		                            	<h3 class="box-title">遥测数据统计</h3>
		                            </div>-->
		                            <div class="col-md-3 col-xs-4 justify-align">
		                            	<button id="dataStas_month" onclick="getDataStas('month');" type="button" class="button expand" >最近一月</button>&nbsp;&nbsp;
		                                <button id="dataStas_week" onclick="getDataStas('week');" type="button" class="button expand" style="background: rgb(150, 151, 160);">最近一周</button>
		                            </div>
		                        </div>
		                        <div class="row">
		                        	<div id="dataStas" style="height: 300px;margin-left: 10px;"></div>
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
var productId = '${product.productId }';
$(function(){
	getDeviceStat('month');
	getDataStas('month');
	initNumber();
});
//数量统计展示
function initNumber(){
	$.ajax({
        url: '${ctx}/product/product_statis?productId='+productId+'&startTime='+dataFormat(getCurrentMonthLast(),'yyyy-MM-dd hh:mm:ss')+'&endTime='+dataFormat(getCurrentMonthFirst(),'yyyy-MM-dd hh:mm:ss'),
        datatype: 'json',
        type: "Post",
        async: false,
        success: function (data) {
        	$("#devNum_all").html(data.devNum_notGateWay + data.devNum_gateWay);
        	$("#devNum_gateWay").html(data.devNum_gateWay);
        	$("#ruleNum").html(data.ruleNum);
            $("#dataNum").html('...');
        }
	});

    initDeviceData();
}

//数量统计展示
function initDeviceData(){
    $.ajax({
        url: '${ctx}/product/productDeviceData?productId='+productId,
        datatype: 'json',
        type: "Post",
        success: function (data) {
            $("#dataNum").html(data.dataNum);
        }
    });
}

function dateToString(date){
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()  
		+ ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}
function getDeviceStat(scale){
	var startTime = '';
	var endTime = '';
	var dataUrl = '${ctx}/device/deviceStatisByDay';
	var now = new Date();
	if(scale=='week'){
		endTime = dateToString(now);
		startTime = dateToString(new Date(now.getTime() - 7 * 24 * 3600 * 1000));
		$("#deviceStas_month").css("background-color","rgb(150, 151, 160)");
		$("#deviceStas_week").css("background-color","#2795dc");
	}else if(scale=='month'){
		endTime = dateToString(now);
		startTime = dateToString(new Date(now.getTime() - 30 * 24 * 3600 * 1000));
		$("#deviceStas_month").css("background-color","#2795dc");
		$("#deviceStas_week").css("background-color","rgb(150, 151, 160)");
	}
	dataUrl = dataUrl + '?productId='+productId+'&startTime='+startTime+'&endTime='+endTime+'&random='+Math.random();
	$('#deviceStas').showChart_line({url:dataUrl,title:""});
}
function getDataStas(scale){
	var startTime = '';
	var endTime = '';
	var dataUrl = '${ctx}/telemetryAttributes/statisByDay';
	var now = new Date();
	if(scale=='week'){
		endTime = dateToString(now);
		startTime = dateToString(new Date(now.getTime() - 7 * 24 * 3600 * 1000));
		$("#dataStas_month").css("background-color","rgb(150, 151, 160)");
		$("#dataStas_week").css("background-color","#2795dc");
	}else if(scale=='month'){
		endTime = dateToString(now);
		startTime = dateToString(new Date(now.getTime() - 30 * 24 * 3600 * 1000));
		$("#dataStas_month").css("background-color","#2795dc");
		$("#dataStas_week").css("background-color","rgb(150, 151, 160)");
	}
	dataUrl = dataUrl + '?productId=${CUR_PRODUCT_ID}&startTime='+startTime+'&endTime='+endTime+'&random='+Math.random();
	$('#dataStas').showChart_line({url:dataUrl,title:""});
}

// 30天前
function getCurrentMonthFirst(){
 	var date=new Date();
 	date.getTime();
 	return date;
}
// 今天
function getCurrentMonthLast(){
 	var date=new Date();
 	date.getTime();
 	return (date - 30 * 24 * 3600 * 1000);
} 

var productPageGrid = new PageGrid({});
//编辑产品
function edit_product(id){
	productPageGrid.createModal("编辑产品","${ctx}/product/product_edit?productId="+id+'&frameIndex=2' ,450,700);
}
//查看产品
function view_product(id){
	productPageGrid.createModal("查看产品","${ctx}/product/product_view?productId="+id ,350,700);
}
//添加设备
function add_device(){
	productPageGrid.createModal("添加设备","${ctx}/device/to_device_edit?frompage=product" ,400,900);
}
//关闭窗口触发的操作
function hide_modal(tarId){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
}
</script>



</body>
</html>
