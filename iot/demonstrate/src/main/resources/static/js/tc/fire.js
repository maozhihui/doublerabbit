
function readData(dev,type){
	$.ajax({
		url: "/api/v1/"+dev+"/realData",
		datatype: 'json',
		type: "GET",
		success: function (resp) {
			var resp = JSON.parse(resp);
			if(type==23){
			    console.log("switch"+resp.dataStream[0].dataPoint.switch);
				if(resp.dataStream[0].dataPoint.switch == 1){
					$("#switchState").attr("src",'/img/on.png');
					$("#switch_1").attr("src",'/img/zd_1.jpg');
					$("#switch_2").attr("src",'/img/zd_1.jpg');
					$("#switch_3").attr("src",'/img/zd_1.jpg');
				} else {
					$("#switchState").attr("src",'/img/off.png');
                    $("#switch_1").attr("src",'/img/sd_1.jpg');
                    $("#switch_2").attr("src",'/img/sd_1.jpg');
                    $("#switch_3").attr("src",'/img/sd_1.jpg');
				}
			}
			$(".updateTime").html(dataFormat(resp.dataStream[0].time,'yyyy-MM-dd hh:mm:ss'))
		}
	});
}
var titleColor = 'RGB(251,195,84)';
var textColor = '#212121';
var titleTop = '5';
var splitLineColor = '#464444';
function initChart(target,dev,type){
	var data_switch = [];
	var dataTime = [];
	var start,end;
	var date = new Date();
	var fullYear = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var h = date.getHours();
	var m = date.getMinutes();
	var s = date.getSeconds();
	month=(month>=10)?month:"0"+month;
	day=(day>=10)?day:"0"+day;
	h=(h>=10)?h:"0"+h;
	m=(m>=10)?m:"0"+m;
	s=(s>=10)?s:"0"+s;
	start = fullYear + "-" + month + "-" + day + " 00:00:00";
	end = fullYear + "-" + month + "-" + day + " "+h+":"+m+":"+s;
	var startTime = new Date(start).getTime();
	var endTime = new Date(end).getTime();
	function initTH(){
		$.ajax({
			url: "/api/v1/"+dev+"/historyData?startTime="+startTime+"&endTime="+endTime,
			datatype: 'json',
			type: "GET",
			async:false,
			success: function (resp) {
				//data = resp.datapoint.temperature;
				//data_2 = resp.datapoint.hustriy;
				var resp = JSON.parse(resp);
				var allTime = [];
				var switchData = [];
				if(resp.dataStream){
					for(var i=0;i<resp.dataStream.length;i++){
                        if(type==23){
							if(resp.dataStream[i].dataPoint){
								var time = dataFormat(resp.dataStream[i].time,'hh:mm');
								allTime.push(time);
								switchData.push(resp.dataStream[i].dataPoint.switch);
							}
						}
					}
				} else {
					switchData=[];
				}
				dataTime = allTime;
				if(type==23){
					data_switch = switchData;
				}
			}
		});
	};
	initTH();
	var optionScroll = {
	   /* title: {
			text: '告警信息',
			textStyle:{color:titleColor},
			x:'center',
			top:titleTop,
		},*/
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data:['设备']
		},

		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: dataTime,
			axisLabel:{
				textStyle:{
					color:textColor
				}
			},
			axisLine:{
				lineStyle:{
					color:textColor
				}
			}
		},
		yAxis: {
			type: 'value',
			axisLabel:{
				textStyle:{
					color:textColor
				}
			},
			axisLine:{
				lineStyle:{
					color:textColor
				}
			},
			splitLine:{
				lineStyle:{
					color:splitLineColor,
				}
			}
		},
		series: [
			{
				name:'开关',
				type:'line',
				smooth:true,  //这句就是让曲线变平滑的
				stack: '总量',

			}
		]
	};
	var myChart,myChart_2;
	if(type==23){
		optionScroll.series[0].data=data_switch;
		//optionScroll.yAxis.min=5;
		myChart = echarts.init(document.getElementById(target[0]));
		myChart.setOption(optionScroll);
		intervalHy = setInterval(function () {
			initTH();
			optionScroll.series[0].data=data_switch;
			myChart.setOption(optionScroll);
		}, 600000);
		//$(".ph .echart_cont").hide();
	}

}
//获取链接参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function initGauge(target,title,innerTitle,data,type,startAngle,endAngle){
	var myChart = echarts.init(document.getElementById(target));
	var option = {
		title: {
			text: title,
			left: 'center',
			top:'-5',
			textStyle:{color:'#fff',fontSize:'15'},
		},
		tooltip : {
			formatter: "{a} <br/>{c} {b}"
		},
		series : [
			{
				name:innerTitle,
				type:'gauge',
				min:0,
				max:2,
				//startAngle: 180,
				//endAngle: 0,
				//splitNumber:5,
				axisLine: {            // 坐标轴线
					lineStyle: {       // 属性lineStyle控制线条样式
					//	color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
						width: 8,
						shadowColor : '#fff', //默认透明
						shadowBlur: 10
					}
				},
				axisLabel: {            // 坐标轴小标记
					textStyle: {       // 属性lineStyle控制线条样式
						fontWeight: 'bolder',
					   // color: '#fff',
						shadowColor : '#fff', //默认透明
						shadowBlur: 10
					}
				},
				axisTick: {            // 坐标轴小标记
					length :10,        // 属性length控制线长
					lineStyle: {       // 属性lineStyle控制线条样式
						color: 'auto',
						shadowColor : '#fff', //默认透明
						shadowBlur: 10
					}
				},
				splitLine: {           // 分隔线
					length :20,         // 属性length控制线长
					lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
					   // width:3,
						color: '#fff',
						shadowColor : '#fff', //默认透明
						shadowBlur: 10
					}
				},
				pointer: {           // 分隔线
					shadowColor : '#fff', //默认透明
					shadowBlur: 3,
					length: '60%'
				},
				title : {
					textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						fontWeight: 'bolder',
						fontSize: 10,
						fontStyle: 'italic',
						color: '#fff',
						shadowColor : '#fff', //默认透明
						shadowBlur: 10
					}
				},
				detail : {
					backgroundColor:'RGB(47,255,247)',
					shadowBlur: 5,
					width:'40px',
					height:'20px',
					offsetCenter: [0, '60%'],       // x, y，单位px
					textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						fontWeight: 'bolder',
						color: 'red',
						fontSize:'20px'
					}
				},
				data:data
			}
		]
	};
	if(startAngle!==undefined){
		option.series[0].startAngle=startAngle;
	}
	if(endAngle!==undefined){
		option.series[0].endAngle = endAngle;
	}
	if(type==1){
		option.series[0].axisLine.lineStyle.color=[[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']];
	}
	myChart.setOption(option);
	timeTicket = setInterval(function (){
		option.series[0].data[0].value = (Math.random()*2).toFixed(2) - 0;
		myChart.setOption(option);
	},8000)
}
initGauge("mainYl","温度一","压力",[{value: 0.8, name: 'MPa'}],1);


var myChartScroll = echarts.init(document.getElementById('mainYj'));
var now = new Date();
var alertTime=['容积','容积'];
function randomData(){
	return parseInt(Math.random()*10);
}
var data = [40,40];
var optionScroll = {
	 /*   title : {
	        text: '平台流量趋势图',
	       // subtext: '数据来自西安兰特水电测控技术有限公司',
	        x: 'center',
	        align: 'right',
			top:titleTop,
	    },*/
	    grid: {
	        bottom: 60,
	        left:40,
	        top:10
	    },

	    tooltip : {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'cross',
	            animation: false,
	            label: {
	                backgroundColor: '#505765',
	            }
	        },
		formatter:function(params){}
	    },
	   /* legend: {
	        data:['流量'],
	        x: 'left'
	    },*/

	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: {onZero: false},
	            data : alertTime,
				/*axisLabel:{
					interval:0 ,
					rotate:20,
					margin:5
				}*/
				axisLabel:{
					textStyle:{
						color:'#fff'
					}
				},
				axisLine:{
					lineStyle:{
						color:textColor
					}
				}
	        }
	    ],
	    yAxis: [
	        {
	           // name: '万条',
	            type: 'value',
		    splitNumber:2,
	           	max: 60,
				/*axisLabel:{
					textStyle:{
						color:textColor
					}
				},
				axisLine:{
					lineStyle:{
						color:textColor
					}
				},
				splitLine:{
					lineStyle:{
						color:splitLineColor,
					}
				}*/
	        }
	    ],
	    series: [
	        {
	            name:'容积',
	            type:'line',
	            animation: false,
	            smooth:true,  //这句就是让曲线变平滑的
	            areaStyle: {
	                normal: {
						color:'lightblue'
					}
	            },
	            lineStyle: {
	                normal: {
	                    width: 1,
	                    color:'#78b7c5'
	                }
	            },
	           /* markArea: {
	                silent: true,
	                data: [[{
	                    xAxis: '2009/9/12\n7:00'
	                }, {
	                    xAxis: '2009/9/22\n7:00'
	                }]]
	            },*/
	            data:data,
	            symbol: false,
	        }
	    ]
	};



myChartScroll.setOption(optionScroll);
