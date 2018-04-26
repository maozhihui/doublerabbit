
var height=$(window).height()/3-27;
$("#mainBarAll,#mainBarCity,#mainBarAlert,#mainPie,#mainLine,#mainScroll").height(height);
$("#mapBg").height(height*2+12);
$("#logText").height(height);
function maxsize(){
	$("#toggleMenu").slideUp();
}

var titleColor = 'RGB(251,195,84)';
var titleSize = '18';
var textColor = '#fff';
var titleTop = '5';
var splitLineColor = '#464444';
//全国设备柱状图
var myChartAll = echarts.init(document.getElementById('mainBarAll'));
var optionAll = {
	title: {
        text: 'Nationwide devices TOP 10',
        //subtext: '纯属虚构',
        left: 'center',
		top:titleTop,
		textStyle:{color:titleColor,fontSize:titleSize}
    },
    color: ['#3398DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data: ['JL','SD','HN','BJ','GD','GX','JS','HB','SC','CQ'],
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
    yAxis : [
        {
            type : 'value',
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
				//show:true  ,
				lineStyle:{
					color:splitLineColor,
				}
			}
        }
    ],
    series : [
        {
            name:'Number of devices',
            type:'bar',
            barWidth: '60%',
            data:[10,52,200,330,390,334,220,150,130,110],
			//配置样式
            itemStyle: {   
                //通常情况下：
                normal:{  
                    color: function (params){
                        var colorList = ['RGB(200,122,99)','RGB(91,149,160)','RGB(193,204,210)','RGB(39,223,221)','RGB(40,64,76)','RGB(192,126,40)','RGB(112,155,128)','RGB(136,188,166)','RGB(109,110,115)','RGB(40,64,76)'];
                        return colorList[params.dataIndex];
                    }
                }
            },
        }
    ]
};
myChartAll.setOption(optionAll);
//城市设备柱状图
var myChartCity = echarts.init(document.getElementById('mainBarCity'));
var optionCity = {
	title: {
        text: 'City devices TOP 10',
        //subtext: '纯属虚构',
        left: 'center',
		top:titleTop,
		textStyle:{color:titleColor,fontSize:titleSize}
    },
    color: ['RGB(212,114,111)'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data: ['GL','CS','GZ','WH','SQ','CD','BJ','CQ','JN','TH'],
            axisTick: {
                alignWithLabel: true
            },
			axisLabel:{
				textStyle:{
					color:textColor,
				},
				interval:0   
			},
			axisLine:{
				lineStyle:{
					color:textColor
				}
			}
        }
    ],
    yAxis : [
        {
            type : 'value',
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
    series : [
        {
            name:'Number of devices',
            type:'bar',
            barWidth: '60%',
            data:[300,160, 343, 135,154,117,310,90,40,10],
			//配置样式
            itemStyle: {   
                //通常情况下：
                normal:{  
                    color: function (params){
                        var colorList = ['RGB(187,163,153)','RGB(91,149,160)','RGB(43,137,227)','RGB(40,64,76)','RGB(192,126,40)','RGB(112,155,128)','RGB(136,188,166)','RGB(193,204,210)','RGB(109,110,115)','RGB(40,64,76)'];
                        return colorList[params.dataIndex];
                    }
                }
            },
        },
    ]
};
myChartCity.setOption(optionCity);
//设备告警数柱状图
var myChartAlert = echarts.init(document.getElementById('mainBarAlert'));
var optionAlert=  { 
	title: {
        text: 'Device alarm statistics',
        //subtext: '纯属虚构',
        left: 'center',
		top:titleTop,
		textStyle:{color:titleColor,fontSize:titleSize}
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {  
		//orient: 'vertical',
        left: '5',
		top:30,
        data:['Total alarm','To be confirmed','Confirmed'],
		textStyle:{color:textColor,fontSize:'14'}		
    }, 
	calculable : true,  
	grid:{
		top:70,
		left:40,
		bottom:35
	},
    xAxis : [  
        {  
            type : 'category',  
            data : ['Mon','Tues','Wed','Thur','Fri','Sat','Sun'],
			axisLabel:{
				textStyle:{
					color:textColor
				},
				interval:0   //x标签全部显示
			},
			axisLine:{
				lineStyle:{
					color:textColor
				}
			}
        }  
    ],  
    yAxis : [  
        {  
			type : 'value',  
            // splitArea : {show : true} ,
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
    series : [  
        {  
            name:'Total alarm',  
            type:'bar',  
            data:[100, 50, 20, 40, 10, 70, 65]  
        },  
        {  
            name:'To be confirmed',  
            type:'bar',  
            data:[50, 10, 12, 26, 2, 40, 20]  
        } ,  
        {  
            name:'Confirmed',  
            type:'bar',  
            data:[50, 40, 8, 14, 8, 30, 45]  
        }  
    ]  
}; 
myChartAlert.setOption(optionAlert);			
//地图
var myChartMap = echarts.init(document.getElementById('mainMap'));
var data = [
	{name: 'BJ',value: 330,value2:100},
	{name: 'TJ',value: 0,value2:0},
	{name: 'Shanghai',value: 0,value2:0},
	{name: 'CQ',value: 110,value2:90},
	{name: 'HE',value: 0,value2:0},
	{name: 'HA',value: 0,value2:0},
	{name: 'YN',value: 0,value2:0},
	{name: 'LN',value: 0,value2:0},
	{name: 'HL',value: 0,value2:0},
	{name: 'HN',value: 200,value2:140},
	{name: 'AH',value: 0,value2:0},
	{name: 'SD',value: 52,value2:40},
	{name: 'XJ',value: 0,value2:0},
	{name: 'JS',value: 220,value2:200},
	{name: 'ZJ',value: 0,value2:0},
	{name: 'JX',value: 0,value2:0},
	{name: 'HB',value: 150,value2:100},
	{name: 'GX',value: 334,value2:330},
	{name: 'GS',value: 0,value2:0},
	{name: 'SX',value: 0,value2:0},
	{name: 'NM',value: 0,value2:0},
	{name: 'SN',value: 300,value2:280},
	{name: 'JL',value: 10,value2:10},
	{name: 'FJ',value: 0,value2:0},
	{name: 'GZ',value: 0,value2:0},
	{name: 'GD',value: 390,value2:370},
	{name: 'QH',value: 0,value2:0},
	{name: 'XZ',value: 0,value2:0},
	{name: 'SC',value: 130,value2:100},
	{name: 'NX',value: 0,value2:0},
	{name: 'HI',value: 0,value2:0},
	{name: 'TW',value: 0,value2:0},
	{name: 'HK',value: 0,value2:0},
	{name: 'MC',value: 0,value2:0},
	{name: '南海诸岛',value: 0,value2:0}
			];
var count=0;
var curentCount=0;
for(var i=0;i<data.length;i++){
	count+=data[i].value;
	curentCount+=data[i].value2;
};
$("#countAll").html("("+count+")");
$("#countCurent").html("("+curentCount+")");
var optionMap = {
   /* title: {
        text: '设备总数'+ count +'/在线数量'+curentCount,
        //subtext: '纯属虚构',
        left: 'center',
		top:titleTop,
		textStyle:{color:titleColor}
    },*/
    tooltip: {
        trigger: 'item',
	/*	formatter: function (params) {
			//return params.name + '<br/>' + '设备总数:'+params.value + '<br/>在线设备数:' +params.data.value2;
			return params.name +'<br/>' + '设备总数:'+curentCount ;
        }*/
    },
    visualMap: {
        min: 0,
        max: 500,
        left: '2',
        bottom: '0',
		//color:['red','yellow'],  
		splitNumber: 5,
        inRange: {
            color: ['RGB(43,137,227)','RGB(39,223,221)','RGB(214,228,241)'].reverse()
        },
       // text: ['高','低'],
       // calculable: true,
		textStyle:{color:textColor,fontSize:'12'}
    },
    series: [
        {
            name: 'Number of devices',
            type: 'map',
            mapType: 'china',
           // roam: true,
			zoom: 1,
            label: {
                normal: {
                    show: true,
                },
                emphasis: {
                    show: true
                }
            },
			itemStyle: {
                normal: {//未选中状态
                    //borderWidth:2,//边框大小
                    borderColor:'#666',
                   // areaColor: 'orange',//背景颜色
                    label: {
                        show: true,//显示名称
						textStyle: {
							//color: 'RGB(132,0,0)'
                        }
                    }
                },
                emphasis: {// 也是选中样式
                   // borderWidth:2,
                    borderColor:'#fff',
                   // areaColor: 'red',
                    label: {
                        show: true,
                        textStyle: {
                           // color: '#fff'
                        }
                    }
                }    
            },
            data: data
        }
    ]
};
myChartMap.setOption(optionMap);
setTimeout(function () {
    /*myChartMap.on('mouseup', function (params) {
        if (!down) {
            return;
        }
        down = false;
        var e = params.event;
        var geoCoord = myChartMap.convertFromPixel('series', [e.offsetX, e.offsetY]);
        myChartMap.setOption({
            series: [{
                center: geoCoord,
                zoom: 2,
                animationDurationUpdate: 1000,
                animationEasingUpdate: 'cubicInOut'
            }]
        });
    });
    var down;
    myChartMap.on('mousedown', function () {
        down = true;
    });
    myChartMap.on('mousemove', function () {
        down = false;
    });*/

}, 0);
//不同终端设备占比饼图
var myChartPie = echarts.init(document.getElementById('mainPie'));
var optionPie  = {
    title : {
        text: 'Terminals proportion pie chart',
        //subtext: '纯属虚构',
        x:'center',
		top:titleTop,
		textStyle:{color:titleColor,fontSize:titleSize}
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
  /*  legend: {
        orient: 'vertical',
        left: 'left',
        data: ['烟感','电表','水表','煤气表','地磁','温湿度传感器'],
		textStyle:{color:textColor}
    },*/
    series : [
        {
            name: 'Terminals proportion',
            type: 'pie',
            radius : '45%',
            center: ['50%', '60%'],
            data:[
                {value:135, name:'Smoke sensor'},
                {value:200, name:'Electricity meter'},
                {value:234, name:'Water meter'},
                {value:135, name:'Gas meter'},
                {value:248, name:'Geomagnetism'},
				{value:708, name:'Temperature sensor'},
				{value:566, name:'Humidity sensor'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
myChartPie.setOption(optionPie);
//平台流量趋势图
var myChartLine = echarts.init(document.getElementById('mainLine'));
var now = new Date();
var endDate = dataFormat(now.getTime(),'yyyy/MM/dd');
var timeData = [];
var liuData = [97,191,81,200,300,130,40,10,100,80,20,80,60,80,180,280,80,30,90,20,240,280,60,80,300,80,90,280,180,80,40];
for(var i=30;i>=0;i--){
	var startDate = dataFormat(new Date(now.getTime() - i * 24 * 3600 * 1000),'yyyy/MM/dd');
	timeData.push(startDate);
}
var optionLine = {
    title : {
        text: 'Platform traffic trend chart',
       // subtext: '数据来自西安兰特水电测控技术有限公司',
        x: 'center',
        align: 'right',
		top:titleTop,
		textStyle:{color:titleColor,fontSize:titleSize}
    },
    grid: {
        bottom: 60,
        left:40
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
   /* legend: {
        data:['流量'],
        x: 'left'
    },*/
    dataZoom: [
        {
            show: true,
            realtime: true,
            start: 65,
            end: 100,
			left:80,
			right:80,
			bottom:0,
			height:20,//组件高度
			textStyle:{color:'#fff'}
        }
    ],
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
            name: 'Thousand',
            type: 'value',
            max: 500,
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
            name:'flow',
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
           /* markArea: {
                silent: true,
                data: [[{
                    xAxis: '2009/9/12\n7:00'
                }, {
                    xAxis: '2009/9/22\n7:00'
                }]]
            },*/
            data:liuData
        }
    ]
};
myChartLine.setOption(optionLine);
//告警滚动
var myChartScroll = echarts.init(document.getElementById('mainScroll'));
var alertTime=[];
for(var i=6;i>=0;i--){
	var time = dataFormat(new Date(now.getTime() - i *5*60* 1000),'hh:mm');
	alertTime.push(time);
}
function randomData(){
	return parseInt(Math.random()*10);
}
var data = [0, 0, 0, 0, 3, 0, 0];
var optionScroll = {
    title: {
        text: 'Alarm info',
		textStyle:{color:titleColor,fontSize:titleSize},
		x:'center',
		top:titleTop,
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['device']
    },
    grid:{
		bottom:35
	},
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: alertTime,
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
        max:'10',
		axisLabel:{
			textStyle:{
				color:'#fff'
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
            name:'Alarm info',
            type:'line',
            stack: '总量',
            data:data
        }
    ]
};
 
myChartScroll.setOption(optionScroll);
setInterval(function () {
	var newData = randomData();
	var newTime = dataFormat(new Date(),'hh:mm');
    data.shift();
    alertTime.shift();
    data.push(newData);
    alertTime.push(newTime);
    myChartScroll.setOption(optionScroll);
}, 300000);

//设备日志
window.setInterval('control_log(1)', 60000);
window.setInterval('control_log(2)', 120000);
window.setInterval('control_log(3)', 180000);
window.setInterval('control_log(4)', 2000);
window.setInterval('control_log(5)', 300000);
function randomWord(number){
    var str = "";
	var arr="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    // 随机产生
    for(var i=0; i<number; i++){
        pos = Math.ceil(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}
function control_log(type){
	var str;
	var time;
	var date = new Date();
	var fullYear = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var h = date.getHours();
	var m = date.getMinutes();
	var s = date.getSeconds();
	h=(h>=10)?h:"0"+h;
	m=(m>=10)?m:"0"+m;
	s=(s>=10)?s:"0"+s;
	time = fullYear + "/" + month + "/" + day + " "+ h +":"+m+":"+s;
	var random = randomWord(3);
	if(type==1){
		str = "User"+random+"-login system"
	} else if(type==2){
		str = "Device"+random+"-register success"
	} else if(type==3) {
		str = "Device"+random+"-alarm"
	} else if(type==4) {
		str = "Device"+random+"-report data"
	} else if(type==5) {
		str = "User"+random+"-logout system"
	} 
	$("#ulScroll").append("<li>"+time+"&nbsp&nbsp;"+str+"</li>");
	$("#ulScroll").get(0).scrollTop = $("#ulScroll").get(0).scrollHeight;
}
