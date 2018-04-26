<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<link href="${ctx}/css/product_base.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/plugins/echart/echart_show.js"></script>
<script type="text/javascript" src="${ctx}/plugins/echart/echarts-plain.js"></script>
</head>
<style>
.info-box-content a{
	color:#333;
}

</style>

<body class="skin-blue sidebar-mini">
    <div class="wrapper clearfix">
        <div class="content-wrapper" style="/*min-height: 846px;*/">
	    	<div class="container-fluid main_con  zh_CN">
	        	<!-- 概览 -->
		        <div class="row row-synopsis css-overview">
		            <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
		                <div class="info-box">
		                    <div href="" style="float:left">
		                        <span class="info-box-icon" style="width:115px">
		                        	<!-- <i class="icon-device"></i> -->
		                        	<i class="fa fa-desktop icon-rounded" style="background: rgb(0, 172, 237);"></i>
		                        </span>
		                    </div>
		                    <div class="info-box-content">
		                        <!-- <a href=""> -->
		                            <div class="info-box-text">
		                            	<span>接入设备总数 <span id="devNum_all" class="info-box-number" style="display:inline-block;font-size:34px;"></span> 台</span>
		                            	<br/>
		                            	<span>网关设备数 <span id="devNum_gateWay" class="info-box-number" style="display:inline-block;font-size:34px;">1</span> 台</span>
		                            </div>
		                       <!-- </a> -->
		                    </div>
		                </div>
		            </div>
		          
		           <!--   <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
		                <div class="info-box">
		                	<a href="" style="float:left">
			                    <span class="info-box-icon"><i class="icon-key"></i></span>
		                    </a>
		                    <div class="info-box-content">
		                    	<a href="">
			                        <div class="info-box-text">
			                        	<span>规则数</span>
			                        	<span class="info-box-number"><span id="ruleNum"></span><small>个</small></span>
			                        </div>
			                      	<small class="pull-bottom"></small>
		                      	</a>
		                    </div>
		                </div>
		            </div>-->
		            
		            <!-- /.col -->
		            <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
		                <div class="info-box">
		                	<div style="float:left">
			                    <span class="info-box-icon">
			                    	<!--  <i class="icon-voice"></i>-->
			                    	<i class="fa fa-users icon-rounded" style="background: #5e84b5;"></i>
			                    </span>
		                    </div>
		                    <div class="info-box-content">
		                    	<!--  <a href="">-->
		                        	<div class="info-box-text">
		                        		<span>用户数</span>
		                        		<span class="info-box-number"><span id="userNum"></span><small>人</small></span>
		                        	</div>
		                        	<small class="pull-bottom">在线用户数：<span id="onlineUser"></span>人</small>
		                    	<!--</a>-->
		                    </div>
		                </div>
		            </div>
		         
		            <div class="clearfix visible-sm-block"></div>
		        </div>
		        <!-- 设备趋势分析 -->
		        <div class="row">
		            <div class="col-md-12"  style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                	<h3 class="box-title">新增设备统计</h3>
		                    <div class="box-body chart-responsive">
		                        <div class="row">
		                            <div class="col-md-3 col-xs-4 justify-align">
		                                <button id="deviceStas_month" onclick="getDeviceStat('month');" type="button" class="button expand" >最近一月</button>&nbsp;&nbsp;
		                                <button id="deviceStas_week" onclick="getDeviceStat('week');" type="button" class="button expand" style="background: rgb(150, 151, 160);">最近一周</button>
		                            </div>
		                            <div class="col-md-2 col-xs-4 justify-align add-button-area"></div>
		                        </div>
		                        <div class="row">
		                        	<div id="deviceStas" style="height: 300px;margin-left: 10px;"></div>
		                        </div>
		                    </div>
		            	</div>
		        	</div>
		        </div>  
	        	<!-- 数据点上传趋势 -->
	        <!--<div class="row">
		            <div class="col-md-12">
		                <div class="box">
		                    <div class="box-body chart-responsive">
		                        <div class="row">
			                    	<div class="box-header">
					                	<h3 class="box-title">遥测数据统计</h3>
					            	</div>
				            	</div>
		                        <div class="row ">
		                            <div class="col-md-7 col-xs-8">
		                                <div class="input-group">
		                                    <div class="input-group-addon">
		                                        <i class="icon-calendar-img"></i>
		                                    </div>
		                                    <input type="text" class="form-control active text-center j-sense-range calendar-input">
		                                </div>
		                            </div>
		                            <div class="col-md-3 col-xs-4 justify-align">
		                                <button type="button" class="button expand j-current-month" data-block="sense">本月</button>&nbsp;&nbsp;
		                                <button type="button" class="button expand cold  hollow j-current-week" data-block="sense">本周</button>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		             </div>
	        	</div> -->
    		</div>
        </div>
        <div class="control-sidebar-bg" style="position: fixed; height: auto;"></div>
    </div>

<script type="text/javascript">
$(function(){
	getDeviceStat('month');
	initNumber();
	onlineNumber();
});
//数量统计展示
function initNumber(){
	$.ajax({
	    url: '${ctx}/homeStatis_admin',
	    datatype: 'json',
	    type: "Post",
	    success: function (data) {
	    	$("#devNum_all").html(data.devNum_notGateWay + data.devNum_gateWay);
	    	$("#devNum_gateWay").html(data.devNum_gateWay);
	    	$("#ruleNum").html(data.ruleNum);
	    	$("#userNum").html(data.userNum);
	    }
	});
}
//在线用户数量
function onlineNumber(){
	$.ajax({
	    url: '${ctx}/activeSession',
	    datatype: 'json',
	    type: "GET",
	    success: function (data) {
	    	$("#onlineUser").html(data);
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
	dataUrl = dataUrl + '?startTime='+startTime+'&endTime='+endTime+'&random='+Math.random();
	$('#deviceStas').showChart_line({url:dataUrl,title:""});
}
</script>

</body>
</html>
