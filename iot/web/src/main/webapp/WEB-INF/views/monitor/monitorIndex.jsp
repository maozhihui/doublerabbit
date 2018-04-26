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

</style>

<body class="skin-blue sidebar-mini">
    <div class="wrapper clearfix">
        <div class="content-wrapper" style="/*min-height: 846px;*/">
	    	<div class="container-fluid main_con  zh_CN" style="padding-top: 15px;">
	        	<!-- 概览 -->
		        <div class="row row-synopsis css-overview">
		            
		        </div>
		        
		        
		        <div class="row">
		            <div class="col-md-12"  style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                    <div class="box-body chart-responsive" style="padding: 10px 10px 0px 10px;">
		                        <div class="row">
		                            <div class="col-md-7 col-xs-8 box-header ">
		                            	<h3 class="box-title" style="padding-bottom: 8px;">系统状态监控</h3>
		                            </div>
		                        </div>
		                        <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
					                <div class="info-box">
					                    <a href="#" style="float:left">
					                        <span class="info-box-icon"><i class="icon-device"></i></span>
					                    </a>
					                    <div class="info-box-content">
				                            <div class="info-box-text">
				                            		<span>系统正常运行时间</span>
				                            		<span class="info-box-number"><span id="uptime">1</span><small>分钟</small></span>
				                            </div>
					                    </div>
					                </div>
					            </div>
					          
					          <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
					                <div class="info-box">
					                	<a href="" style="float:left">
						                    <span class="info-box-icon"><i class="icon-key"></i></span>
					                    </a>
					                    <div class="info-box-content">
					                        <div class="info-box-text">
					                        	<span>当前软件版本</span>
					                        	<span class="info-box-number">
					                        		<span id="iotstp_version" style="font-size: 18px;">0</span>
					                        	</span>
					                        </div>
					                    </div>
					                </div>
					            </div>
					            
					            <!-- /.col -->
					            <div class="globleColumn_3th col-sm-6 col-xs-12" style="float: left;padding-right: 1px;">
					                <div class="info-box">
					                	<a style="float:left">
						                    <span class="info-box-icon"><i class="icon-voice"></i></span>
					                    </a>
					                    <div class="info-box-content">
					                        <div class="info-box-text">
					                        	<span>数据库状态</span>
					                        	<span class="info-box-number"><span id="database">正常</span><small></small></span>
					                        </div>
					                    </div>
					                </div>
					            </div>
					         
					         	<div class="clearfix visible-sm-block"></div>
		                    </div>
		            	</div>
		        	</div>
		        </div>  
		        
		        
		        
		         <div class="row">
		            <div class="col-md-12"  style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                    <div class="box-body chart-responsive" style="padding: 10px 10px 0px 10px;">
		                        <div class="row">
		                            <div class="col-md-7 col-xs-8 box-header ">
		                            	<h3 class="box-title" style="padding-bottom: 8px;">系统资源监控</h3>
		                            </div>
		                        </div>
		                        <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
					                <div class="info-box">
					                    <a href="" style="float:left">
					                        <span class="info-box-icon"><i class="icon-device"></i></span>
					                    </a>
					                    <div class="info-box-content">
				                            <div class="info-box-text">
				                            		<span>处理器数量</span>
				                            		<span class="info-box-number"><span id="processors">1</span><small>个</small></span>
				                            		<small class="pull-bottom"></small>
				                            </div>
					                    </div>
					                </div>
					            </div>
					          
					            <div class="globleColumn_3th col-sm-6 col-xs-12" style="">
					                <div class="info-box">
					                	<a href="" style="float:left">
						                    <span class="info-box-icon"><i class="icon-key"></i></span>
					                    </a>
					                    <div class="info-box-content">
					                        <div class="info-box-text">
					                        	<span>剩余磁盘</span>
					                        	<span class="info-box-number">
					                        		<span id="diskSpace_free">1</span><small>M</small>
					                        	</span>
					                        	<small class="pull-bottom">总磁盘：<span id="diskSpace_all">0</span>M</small>
					                        </div>
					                    </div>
					                </div>
					            </div>
					            
					            <!-- /.col -->
					            <div class="globleColumn_3th col-sm-6 col-xs-12" style="float: right;padding-right: 1px;padding-left: 10px;">
					                <div class="info-box">
					                	<a style="float:left">
						                    <span class="info-box-icon"><i class="icon-voice"></i></span>
					                    </a>
					                    <div class="info-box-content">
					                        <div class="info-box-text">
					                        	<span>线程数</span>
					                        	<span class="info-box-number"><span id="threads">0</span><small>条</small></span>
					                        	<small class="pull-bottom">线程峰值：<span id="threads_peak">0</span>条</small>
					                        </div>
					                    </div>
					                </div>
					            </div>
					         
					         	<div class="clearfix visible-sm-block"></div>
		                    </div>
		            	</div>
		        	</div>
		        </div>  
		        
		        
		        
		        
		        <div class="row">
		            <div class="col-md-12"  style="padding-left: 0px;padding-right: 10px;">
		                <div class="box">
		                    <div class="box-body chart-responsive">
		                        <div class="row">
		                            <div class="col-md-7 col-xs-8 box-header ">
		                            	<h3 class="box-title">系统空闲内存统计</h3><span> ( 系统分配内存 </span><span id="sysMem" style=""></span>
                                        <span>KB )</span></div>
		                            </div>
		                        <div class="row">
		                        	<div id="main" style="height: 300px;margin-left: 10px;"></div>
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
$(function(){
	
	init();
});

function init(){
	var totalMem = 100;

    $.ajax({
        type : "get",
        url : '${ctx}/version',
        async : false,
        success : function(data) {
            $("#iotstp_version").html(data);
        }
    });

	$.ajax({
		type : "get",
		url : '${ctx}/metrics',
		async : false,
		dataType : 'json',
		success : function(data) {
			totalMem = data['mem'];
			$("#uptime").html((data['uptime']/1000/60).toFixed(2));
		//	$("#httpsessions_active").html(data['httpsessions.active']);
			$("#processors").html(data['processors']);
			$("#threads").html(data['threads']);
			$("#threads_peak").html(data['threads.peak']);
			initMemChart(totalMem);
		}
	});
	
	$.ajax({
		type : "get",
		url : '${ctx}/health',
		async : false,
		dataType : 'json',
		success : function(data) {
			var dbStatus = "正常";
			if(data['db']['status']!='UP'){
				dbStatus = "异常";
			}
			$("#diskSpace_all").html((data['diskSpace']['total']/1024/1024).toFixed(2));
			$("#diskSpace_free").html((data['diskSpace']['free']/1024/1024).toFixed(2));
			$("#database").html(dbStatus);
			$("#sysMem").html(totalMem);
			initMemChart(totalMem);
		}
	});
}

function initMemChart(totalMem){
	var myChart = echarts.init(document.getElementById('main'));
	// 指定图表的配置项和数据
	var option = {
		xAxis : [{
		    data : (function (){
		        var now = new Date();
		        var res = [];
		        var len = 10;
		        while (len--) {
		            res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
		            now = new Date(now - 2000);
		        }
		        return res;
		    })()
		}],
		yAxis : [{
			    type : 'value',
			    name : ''
			}
		],
		series : [{
			    name:'系统空闲内存',
			    type:'line',
			    data:(function (){
			        var res = [];
			        var len = 10;
			        while (len--) {
			            res.push(0);
			        }
			        return res;
			    })()
                }]
	};
	myChart.setOption(option);
	var axisData;
	clearInterval(timeTicket);
	var timeTicket = setInterval(function (){
		axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
		$.ajax({
			type : "get",
			url : '${ctx}/metrics/mem.free',
			async : false,
			dataType : 'json',
			success : function(json) {
				// 动态数据接口 addData
				myChart.addData([
					[
					    0,        // 系列索引
					    json['mem.free'], // 新增数据
					    true,     // 新增数据是否从队列头部插入
					    false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						,axisData
					]
				]);
			}
		});
	}, 4100);
}

</script>

</body>
</html>
