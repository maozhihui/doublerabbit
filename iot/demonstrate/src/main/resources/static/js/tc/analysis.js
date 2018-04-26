
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
        text: '全国设备Top10',
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
            data: ['吉林','山东','湖南','北京','广东','广西','江苏','湖北','四川','重庆'],
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
            name:'设备数',
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
        text: '城市设备Top10',
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
            data: ['桂林','长沙','广州','武汉','宿迁','成都','北京','重庆','济宁','通化'],
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
            name:'设备数',
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
        text: '设备告警统计',
        //subtext: '纯属虚构',
        right:'100',
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
        data:['告警总数','待确认','已确认'],
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
            data : ['周一','周二','周三','周四','周五','周六','周日'],
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
            name:'告警总数',  
            type:'bar',  
            data:[100, 50, 20, 40, 10, 70, 65]  
        },  
        {  
            name:'待确认',  
            type:'bar',  
            data:[50, 10, 12, 26, 2, 40, 20]  
        } ,  
        {  
            name:'已确认',  
            type:'bar',  
            data:[50, 40, 8, 14, 8, 30, 45]  
        }  
    ]  
}; 
myChartAlert.setOption(optionAlert);			
//地图
var myChartMap = echarts.init(document.getElementById('mainMap'));
var data = [
	{name: '北京',value: 330,value2:100},
	{name: '天津',value: 0,value2:0},
	{name: '上海',value: 0,value2:0},
	{name: '重庆',value: 110,value2:90},
	{name: '河北',value: 0,value2:0},
	{name: '河南',value: 0,value2:0},
	{name: '云南',value: 0,value2:0},
	{name: '辽宁',value: 0,value2:0},
	{name: '黑龙江',value: 0,value2:0},
	{name: '湖南',value: 200,value2:140},
	{name: '安徽',value: 0,value2:0},
	{name: '山东',value: 52,value2:40},
	{name: '新疆',value: 0,value2:0},
	{name: '江苏',value: 220,value2:200},
	{name: '浙江',value: 0,value2:0},
	{name: '江西',value: 0,value2:0},
	{name: '湖北',value: 150,value2:100},
	{name: '广西',value: 334,value2:330},
	{name: '甘肃',value: 0,value2:0},
	{name: '山西',value: 0,value2:0},
	{name: '内蒙古',value: 0,value2:0},
	{name: '陕西',value: 300,value2:280},
	{name: '吉林',value: 10,value2:10},
	{name: '福建',value: 0,value2:0},
	{name: '贵州',value: 0,value2:0},
	{name: '广东',value: 390,value2:370},
	{name: '青海',value: 0,value2:0},
	{name: '西藏',value: 0,value2:0},
	{name: '四川',value: 130,value2:100},
	{name: '宁夏',value: 0,value2:0},
	{name: '海南',value: 0,value2:0},
	{name: '台湾',value: 0,value2:0},
	{name: '香港',value: 0,value2:0},
	{name: '澳门',value: 0,value2:0},
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
            name: '设备数',
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
        text: '终端设备占比',
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
            name: '终端设备',
            type: 'pie',
            radius : '45%',
            center: ['50%', '60%'],
            data:[
                {value:135, name:'烟感'},
                {value:200, name:'电表'},
                {value:234, name:'水表'},
                {value:135, name:'煤气表'},
                {value:248, name:'地磁'},
				{value:708, name:'温度传感器'},
				{value:566, name:'湿度传感器'}
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
        text: '平台流量趋势图',
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
            name: '万条',
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
            name:'流量',
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
        text: '告警信息',
		textStyle:{color:titleColor,fontSize:titleSize},
		x:'center',
		top:titleTop,
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['设备']
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
            name:'告警信息',
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
		str = "用户"+random+"-登录系统"
	} else if(type==2){
		str = "设备"+random+"-注册成功"
	} else if(type==3) {
		str = "设备"+random+"-产生告警"
	} else if(type==4) {
		str = "设备"+random+"-上报数据"
	} else if(type==5) {
		str = "用户"+random+"-退出系统"
	} 
	$("#ulScroll").append("<li>"+time+"&nbsp&nbsp;"+str+"</li>");
	$("#ulScroll").get(0).scrollTop = $("#ulScroll").get(0).scrollHeight;
}
