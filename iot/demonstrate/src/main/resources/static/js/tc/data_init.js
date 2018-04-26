
	function readData(dev,type){
		$.ajax({
			url: "/api/v1/"+dev+"/realData",
			datatype: 'json',
			type: "GET",
			success: function (resp) {
				var resp = JSON.parse(resp);
				if(type==4){
					$(".wd .data_number").html(resp.dataStream[0].dataPoint.temperture);
					$(".sd .data_number").html(resp.dataStream[1].dataPoint.humidity);
				} else if(type==19){
					if(resp.dataStream[0].dataPoint.alarmStatus=="false"){
						$(".bj .data_number").html("正常");
					} else {
						$(".bj .data_number").html("报警");
					}
					$(".dy .data_number").html(resp.dataStream[1].dataPoint.voltage);
				}  else if(type==12){
					if(resp.dataStream[0].dataPoint.isAvailable=="false"){
						$(".tczt .data_number").html("己停车");
					} else {
						$(".tczt .data_number").html("未停车");
					}
					$(".tcdc .data_number").html(resp.dataStream[1].dataPoint.temperture);
				} else if(type==20){
					$(".pm .data_number").html(resp.dataStream[0].dataPoint["pm2.5"]);
				} else if(type==21){
					$(".ph .data_number").html(resp.dataStream[0].dataPoint.ph);
				} else if(type==23){
					if(resp.dataStream[0].dataPoint.data==1){
						$("#switchState").attr("src",'/img/on.png');
					} else {
						$("#switchState").attr("src",'/img/off.png');
					}
				}else if(type==13){
                    $(".ammeter .data_number").html(resp.dataStream[0].dataPoint.electricity );
                }else if(type==14){
                    if(resp.dataStream[0].dataPoint.switch==1){
                        $("#switchState").attr("src",'/img/on.png');
                    } else {
                        $("#switchState").attr("src",'/img/off.png');
                    }
                }else if(type==15){
                    $(".watermeter .data_number").html(resp.dataStream[0].dataPoint.volume );
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
		var data_wd = [],data_sd = [],data_dy=[],data_pm=[],data_ph=[],data_switch = [],data_ammeter= [],data_switch_test = [],data_watermeter=[];
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
					var tpData = [],hdData = [],dyData=[],dcData=[],pmData=[],phData=[],switchData = [],ammeter = [],switchTestData = [],watermeterData=[];
					if(resp.dataStream){
						for(var i=0;i<resp.dataStream.length;i++){
							if(type==4){
								if(resp.dataStream[i].dataPoint.temperture){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									allTime.push(time);
									tpData.push(resp.dataStream[i].dataPoint.temperture);
								} else {
									hdData.push(resp.dataStream[i].dataPoint.humidity);
								}
							} else if(type==19){
								if(resp.dataStream[i].dataPoint.voltage){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									allTime.push(time);
									dyData.push(resp.dataStream[i].dataPoint.voltage);
								}
							} else if(type==12){
								if(resp.dataStream[i].dataPoint.temperture){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									if(resp.dataStream[i].dataPoint.temperture){
										allTime.push(time);
										dcData.push(resp.dataStream[i].dataPoint.temperture);
									}
								}
							} else if(type==20){
								if(resp.dataStream[i].dataPoint["pm2.5"]){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									allTime.push(time);
									pmData.push(resp.dataStream[i].dataPoint["pm2.5"]);
								}
							}else if(type==21){
								if(resp.dataStream[i].dataPoint.ph){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									allTime.push(time);
									phData.push(resp.dataStream[i].dataPoint.ph);
								}
							} else if(type==23){
								if(resp.dataStream[i].dataPoint){
									var time = dataFormat(resp.dataStream[i].time,'hh:mm');
									allTime.push(time);
                                    switchTestData.push(resp.dataStream[i].dataPoint.data);
								}
							} else if(type==13){
                                if(resp.dataStream[i].dataPoint.electricity){
                                    var time = dataFormat(resp.dataStream[i].time,'hh:mm');
                                    allTime.push(time);
                                    ammeter.push(resp.dataStream[i].dataPoint.electricity);
                                }
                            } else if(type==14){
                                if(resp.dataStream[i].dataPoint.switch){
                                    var time = dataFormat(resp.dataStream[i].time,'hh:mm');
                                    allTime.push(time);
                                    switchData.push(resp.dataStream[i].dataPoint.switch);
                                }
                            }else if(type==15){
                                if(resp.dataStream[i].dataPoint.volume){
                                    var time = dataFormat(resp.dataStream[i].time,'hh:mm');
                                    allTime.push(time);
                                    watermeterData.push(resp.dataStream[i].dataPoint.volume);
                                }
                            }
						}
					} else {
						allTime = [];
						tpData = [];
						hdData = [];
						dyData=[];
						dcData=[];
						pmData=[];
						phData=[];
						switchData=[];
                        ammeter=[];
                        switchTestData=[];
                        watermeterData=[];
					}
					dataTime = allTime;
					if(type==4){
						data_wd = tpData;
						data_sd = hdData;
					} else if(type==19){
						data_dy = dyData;
					} else if(type==12){
						data_dc = dcData;
					} else if(type==20){
						data_pm = pmData;
					}  else if(type==21){
						data_ph = phData;
					}  else if(type==23){
                        data_switch_test = switchTestData;
					} else if(type==13){
                        data_ammeter = ammeter;
                    }else if(type==14){
                        data_switch = switchData;
                    }else if(type==15){
                        data_watermeter = watermeterData;
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
					name:'温度',
					type:'line',
					smooth:true,  //这句就是让曲线变平滑的
					stack: '总量',
					
				}
			]
		};
		/*var optionScroll_2 = {
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
					name:'湿度',
					type:'line',
					stack: '总量',
					smooth:true,  //这句就是让曲线变平滑的
					data:data_sd
				}
				]
		};*/
		var myChart,myChart_2;
		if(type==4){
			var optionScroll_2= $.extend(true,{},optionScroll);
			optionScroll.series[0].data=data_wd;
			optionScroll_2.series[0].data=data_sd;
			optionScroll_2.series[0].name="湿度";
			optionScroll.yAxis.min=0;
			optionScroll_2.yAxis.min=40;
			myChart = echarts.init(document.getElementById(target[0]));
			myChart_2= echarts.init(document.getElementById(target[1]));
			myChart.setOption(optionScroll);
			myChart_2.setOption(optionScroll_2);
			intervalHy = setInterval(function () {
				initTH();
				myChart.setOption(optionScroll);
				myChart_2.setOption(optionScroll_2);
			}, 600000);
			//$(".wd .echart_cont,.sd .echart_cont").hide();
		} else if((type==19)){
			optionScroll.series[0].data=data_dy;
			optionScroll.series[0].name="电压";
			myChart = echarts.init(document.getElementById(target[0]));
			myChart.setOption(optionScroll);
			intervalHy = setInterval(function () {
				initTH();
				optionScroll.series[0].data=data_dy;
				myChart.setOption(optionScroll);
			}, 600000);
			//$(".dy .echart_cont").hide();
		} else if(type==12){
			optionScroll.series[0].data=data_dc;
			optionScroll.yAxis.min=20;
			myChart = echarts.init(document.getElementById(target[0]));
			myChart.setOption(optionScroll);
			intervalHy = setInterval(function () {
				initTH();
				optionScroll.series[0].data=data_dc;
				myChart.setOption(optionScroll);
			}, 600000);
			//$(".tcdc .echart_cont").hide();
		} else if(type==20){
			optionScroll.series[0].data=data_pm;
			optionScroll.yAxis.min=50;
			myChart = echarts.init(document.getElementById(target[0]));
			myChart.setOption(optionScroll);
			intervalHy = setInterval(function () {
				initTH();
				optionScroll.series[0].data=data_pm;
				myChart.setOption(optionScroll);
			}, 600000);
			//$(".pm .echart_cont").hide();
		} else if(type==21){
			optionScroll.series[0].data=data_ph;
			optionScroll.yAxis.min=5;
			myChart = echarts.init(document.getElementById(target[0]));
			myChart.setOption(optionScroll);
			intervalHy = setInterval(function () {
				initTH();
				optionScroll.series[0].data=data_ph;
				myChart.setOption(optionScroll);
			}, 600000);
			//$(".ph .echart_cont").hide();
		} else if(type==23){
			optionScroll.series[0].data=data_switch_test;
			optionScroll.yAxis.min=50;
			myChart = echarts.init(document.getElementById(target[0]));
			myChart.setOption(optionScroll);
			intervalHy = setInterval(function () {
				initTH();
				optionScroll.series[0].data=data_switch_test;
				myChart.setOption(optionScroll);
			}, 600000);
			//$(".ph .echart_cont").hide();
		} else if(type==13){
            optionScroll.series[0].data=data_ammeter;
            optionScroll.series[0].name="电量";
            optionScroll.yAxis.min=0;
            myChart = echarts.init(document.getElementById(target[0]));
            myChart.setOption(optionScroll);
            intervalHy = setInterval(function () {
                initTH();
                optionScroll.series[0].data=data_ammeter;
                myChart.setOption(optionScroll);
            }, 600000);
        } else if(type==14){
            optionScroll.series[0].data=data_switch;
            optionScroll.series[0].name="开关量";
            optionScroll.yAxis.min=0;
            myChart = echarts.init(document.getElementById(target[0]));
            myChart.setOption(optionScroll);
            intervalHy = setInterval(function () {
                initTH();
                optionScroll.series[0].data=data_switch;
                myChart.setOption(optionScroll);
            }, 600000);
        }else if(type==15){
            optionScroll.series[0].data=data_watermeter;
            optionScroll.series[0].name="水量";
            optionScroll.yAxis.min=0;
            myChart = echarts.init(document.getElementById(target[0]));
            myChart.setOption(optionScroll);
            intervalHy = setInterval(function () {
                initTH();
                optionScroll.series[0].data=data_watermeter;
                myChart.setOption(optionScroll);
            }, 600000);
        }
		 
	}
	//获取链接参数
	function getUrlParam(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	    if (r != null) return unescape(r[2]); return null; //返回参数值
	}
