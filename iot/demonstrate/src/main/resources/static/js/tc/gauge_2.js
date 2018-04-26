
//折线图
var titleColor = 'RGB(251,195,84)';
var textColor = '#fff';
var titleTop = '5';
var splitLineColor = '#464444';

var now = new Date();
var startTime,endTime;
startTime = dataFormat(new Date(now.getTime() - 7 * 24 * 3600 * 1000),'YYYY-MM-dd');
//startTime = new Date(startTime+" 00:00:00").getTime();
startTime = new Date(startTime);
startTime.setHours(0);  
startTime.setMinutes(0);  
startTime.setSeconds(0); 
startTime = new Date(startTime).getTime();
endTime = dataFormat(new Date(now.getTime() - 1 * 24 * 3600 * 1000),'YYYY-MM-dd');
//endTime = new Date(endTime+" 23:59:59").getTime();
endTime = new Date(endTime);
endTime.setHours(23);  
endTime.setMinutes(59);  
endTime.setSeconds(59); 
endTime = new Date(endTime).getTime();
function initLine(target,legend){
	var line = echarts.init(document.getElementById(target));
	var titleName,timeData = [];
	var url,data = [];
	if(target=="lineWD"){
		url="/api/v1/stat/tempertureAverage";
	} else if(target=="lineSD"){
		url="/api/v1/stat/humidityAverage";
	} else if(target=="linePM") {
		url="/api/v1/stat/pmAverage";
	} else if(target=="linePH") {
		url="/api/v1/stat/phAverage";
	}
	$.ajax({
		url: url+"?startTime="+startTime+"&endTime="+endTime,
		datatype: 'json',
		type: "GET",
		async:false,
		success: function (resp) {
			var resp = JSON.parse(resp);
			var datas = resp[0].averageStat;
			$.each(datas,function(key,value){
				timeData.push(dataFormat(key,'MM/dd'));
				data.push(value);
			})
		}
	});
	var optionLine = {
			grid: {
		        left: '40',
		        right: '35',
		        bottom: '30',
		        top:'40'
		    },
		   
		    tooltip : {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            animation: false,
		            label: {
		                backgroundColor: '#505765',
		            }
		        }
		    },
		    title: {
		        text: legend,
		        //subtext: '纯属虚构',
		        left: 'center',
				top:'5',
				textStyle:{color:'RGB(251,195,84)',fontWeight:'normal',fontSize:'18'}
		    },
		   /* legend: {
				data:legend,
				top:'5',
				textStyle:{
					color:textColor
				}
			},*/
		   
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            axisLine: {onZero: false},
		            data : timeData.map(function (str) {
		                return str.replace(' ', '\n')
		            }),
					/*axisLabel:{  
						interval:0 ,  
						rotate:20,
						margin:5
					}*/
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
		        }
		    ],
		    yAxis: [
		        {
		           // name: '流量',
		            type: 'value',
		            //max: 500,
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
		        }
		    ],
		    series: [
		        {
		          //  name:'流量',
		            type:'line',
		            animation: false,
		            smooth:true,  //这句就是让曲线变平滑的
		            areaStyle: {
		                normal: {
							color:'RGB(255,132,255)'
						}
		            },
		            lineStyle: {
		                normal: {
		                    width: 1
		                }
		            },
		            data:data
		        }
		    ]
		};
	
	var items=[];
	for(var i=0;i<legend.length;i++){
		var item={};
		item.name = legend[i];
		item.type = "line";
		item.data = data;
		item.stack = '总量',
		items.push(item);
	}
	if(target=="lineWD"){
		optionLine.yAxis[0].min=20;
	} else if(target=="lineSD"){
		optionLine.yAxis[0].min=40;
	} else if(target=="linePM") {
		optionLine.yAxis[0].min=50;
	} else if(target=="linePH") {
		optionLine.yAxis[0].min=5;
	}
	optionLine.series = items;
	line.setOption(optionLine);
}

//initLine("lineEG",['能耗平均值']);


function initBar(target,title){
	var bar = echarts.init(document.getElementById(target));
	var url,xAxis=[],data = [],dataLine=[];
	var nowDate = new Date();
	var startToday = dataFormat(nowDate.getTime(),'YYYY-MM-dd');
	//startToday = new Date(startToday+" 00:00:00").getTime();
	startToday =new Date(startToday);
	startToday.setHours(0);  
	startToday.setMinutes(0);  
	startToday.setSeconds(0); 
	startToday =new Date(startToday).getTime();
	var endNow = nowDate.getTime();
	if(target=="barWD"){
		url="/api/v1/stat/tempertureTop";
	} else if(target=="barSD"){
		url="/api/v1/stat/humidityTop";
	}
	$.ajax({
		url: url+"?startTime="+startToday+"&endTime="+endNow,
		datatype: 'json',
		type: "GET",
		async:false,
        data: {type:1},
		success: function (resp) {
			var resp = JSON.parse(resp);
			for(var i=0;i<resp.currentValue.length;i++){
				xAxis.push(resp.currentValue[i].key);
				data.push(resp.currentValue[i].value);
				dataLine.push(resp.avgValue[i].value);
			}
		}
	});
	var  optionBar = {
			title: {
		        text: title,
		        //subtext: '纯属虚构',
		        left: 'center',
				top:0,
				textStyle:{color:'RGB(251,195,84)',fontWeight:'normal',fontSize:'18'}
		    },
			tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['当前值','平均值'],
                top:20,
                left:'left',
                textStyle:{color:textColor,fontWeight:'normal',fontSize:'13'}
            },
            grid: {
		        left: '20',
		        right: '35',
		        bottom: '10',
		        top:'50',
		        containLabel: true
		    },
            xAxis: [
                {
                    type: 'category',
                    data: xAxis,
                    axisTick: {
		                alignWithLabel: true,
		            },
					axisLabel:{
						textStyle:{
							color:textColor
						},
						interval:0   //显示所有x轴标签
					},
					axisLine:{
						lineStyle:{
							color:textColor,
						}
					},
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    /*name: '水量/ml',
                    min: 0,
                    max: 250,
                    interval: 50,*/
                    axisLabel:{
						textStyle:{
							color:textColor
						},
						formatter: '{value} '
					},
					axisLine:{
						lineStyle:{
							color:textColor
						}
					},
					splitLine:{  
						//show:true  ,
						lineStyle:{
							color:splitLineColor,
						}
					}
                }
            ],
            series: [
                 
                {
                    name:'当前值',
                    type:'bar',
                    barWidth: '50%',
                    /*设置柱状图颜色*/
                    itemStyle: {   
		                //通常情况下：
		                normal:{  
		                    color: function (params){
		                        var colorList = ['RGB(200,122,99)','RGB(91,149,160)','RGB(193,204,210)','RGB(39,223,221)','RGB(40,64,76)','RGB(192,126,40)','RGB(112,155,128)','RGB(136,188,166)','RGB(109,110,115)','RGB(40,64,76)'];
		                        return colorList[params.dataIndex];
		                    }
		                }
		            },
                    data:data
                },
                {
                    name:'平均值',
                    type:'line',
                    itemStyle : {  /*设置折线颜色*/
                        normal : {
                           /* color:'#c4cddc'*/
                        }
                    },
                    data:dataLine
                }
            ]
    };
	bar.setOption(optionBar);
}


//信息滚动
function control_log(){
	var deviceName,level,info,time;
	$.ajax({
		url: "/api/v1/statusInfo",
		datatype: 'json',
		type: "GET",
		aysnc:false,
        data: {type:1},
		success: function (resp) {
			var resp = JSON.parse(resp);
			var str="";
			for(var i=0;i<resp.length;i++){
				deviceName = resp[i].devName;
				time= dataFormat(resp[i].time,'yyyy-MM-dd hh:mm:ss'); 
				if(resp[i].alarmLevel=="正常"){
					level = '<div>'+resp[i].alarmLevel+'</div>';
				} else {
					level = '<div style="color:red">'+resp[i].alarmLevel+'</div>';
				}
				info = resp[i].alarmCause;
				str += "<li><div>"+time+"</div><div><a href='device?id="+resp[i].devId+"'>"+deviceName+"</a></div>"+level+"<div>"+info+"</div><div style='clear:both'></div></li>";
			}
			$("#scrollUl").empty();
			$("#scrollUl").append(str);
			//$("#scrollUl").get(0).scrollTop = $("#scrollUl").get(0).scrollHeight;
		}
	});
}
function getTemperate(){
	var heats=[1,3,5,7,8,9,10,11,12,13,14,15,16,17];
	for(var i=0;i<heats.length;i++){
		var id = heats[i];
		$.ajax({
			url: "/api/v1/"+heats[i]+"/realData",
			datatype: 'json',
			type: "GET",
			async:false,
			success: function (resp) {
				var resp = JSON.parse(resp);
				if(resp.dataStream){
					var temperture = resp.dataStream[0].dataPoint.temperture;
					if(temperture<=23){
						$("#grad_"+id).attr("class","").addClass("grad grade_23");
					} else if(temperture>23 && temperture<=24){
						$("#grad_"+id).attr("class","").addClass("grad grade_24");
					} else if(temperture>24 && temperture <=25){
						$("#grad_"+id).attr("class","").addClass("grad grade_25");
					} else if(temperture>25 && temperture <=26){
						$("#grad_"+id).attr("class","").addClass("grad grade_26");
					} else if(temperture>26 && temperture <=27){
						$("#grad_"+id).attr("class","").addClass("grad grade_27");
					} else if(temperture>27 && temperture <=28){
						$("#grad_"+id).attr("class","").addClass("grad grade_28");
					} else if(temperture>28 && temperture <=29){
						$("#grad_"+id).attr("class","").addClass("grad grade_29");
					} else if(temperture>28 && temperture <=30){
						$("#grad_"+id).attr("class","").addClass("grad grade_30");
					} else if(temperture>30 && temperture <=31){
						$("#grad_"+id).attr("class","").addClass("grad grade_31");
					} else if(temperture>31){
						$("#grad_"+id).attr("class","").addClass("grad grade_32");
					}
				}
			}
		});
	}
}



initLine("lineWD",['温度平均值']);
initLine("lineSD",['湿度平均值']);
initLine("linePM",['PM2.5 平均值']);
initLine("linePH",['PH 平均值']);

initBar("barWD",'区域-温度Top5');
initBar("barSD",'区域-湿度Top5');

control_log();
getTemperate();

window.setInterval(function(){
	getTemperate();
	control_log();
	initBar("barWD",'区域-温度Top5');
	initBar("barSD",'区域-湿度Top5');
},5000)

