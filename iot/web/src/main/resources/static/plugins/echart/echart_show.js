/**
 * showChart_Pie生成饼图：$('#testPie').showChart_Pie({url:"${ctx}/demo/getPieData",title:"占比"});
 * 传入数据的格式为：[{value:3,name:'t1'}...]
 * @param $
 */
(function($){
	$.fn.showChart_pie = function(options) {
		var _self = this;
		var defaults = {url:'',title:'2',name: ''};
		var opts = $.extend(defaults, options);
		if(!$(this)[0] || !$(this)[0].id){
			alert("需要生成图表的对象必须有id"); return;
		}
		var titleMaxLength = 1000;
		if(options && options.titleMaxLength){
			titleMaxLength = options.titleMaxLength;
		}
		var tarId = $(this)[0].id;
		$.ajax({
			url:opts.url,
			type:'post',
			success:function(chartData){
				$('#'+tarId).empty();
				 var myChart = echarts.init(document.getElementById(tarId));
				 var option = {
				    title : {
				    	text: opts.title,
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"//鼠标放上去显示的文字内容
				    },
				    calculable : false,
				    label: {
		                normal: {
		                    formatter: function (obj) {
		                        return "11"
		                    },
		                    show: true,
		                    position: 'inner',
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal'
		                    }
		                },
		                emphasis: {
		                    //show: true,
		                    position: 'inner',
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal'
		                    }
		                }
		            },
				    series : [
				        {
				            name: opts.name,
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '50%'],
				            data:chartData,
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
				myChart.setOption(option);
			}
		});
	},
	/**
	 * showChart_line生成线状图：$('#testPie').showChart_Pie({url:"${ctx}/demo/getLineData",title:"占比"});
	 * 传入数据的格式为：{legends:[],xaxis:[],datas:[]}
	 * @param $
	 */
	$.fn.showChart_line = function(options) {
		var _self = this;
		var defaults = {url:'',title:'2',name: '',isToolbox:false,isDataZoom:false};
		var opts = $.extend(defaults, options);
		if(!$(this)[0] || !$(this)[0].id){
			alert("需要生成图表的对象必须有id"); return;
		}
		var tarId = $(this)[0].id;
		$.ajax({
			url:opts.url,
			type:'post',
			success:function(chartData){
				var legendArray=chartData.legends;//显示多个线条内容 
				var xaxisArray=chartData.xaxis;
				var statdata = chartData.datas;
			    var myChart = echarts.init(document.getElementById(tarId));
			     if(!xaxisArray || xaxisArray.length==0){
			    	 $("#"+tarId).html('<div style="text-align: center;padding-top: 20px;">暂无统计数据！</div>');
			    	 return false;
			     }
			     var dataZoom = {
						show : true,
						realtime : true,
						start : 20,
						end : 80
				 };
			     
				 var toolbox = {
							 orient:'vertical',
							 x:'right',
							 y:40,
					      show : true,
					      feature : {
					          mark : {show: true},
					          magicType: {
					              show : true,
					              title : {
					                  line : '动态类型切换-折线图',
					                  bar : '动态类型切换-柱形图'
					              },
					              type : ['line', 'bar'],
					          },
					          dataView : {
					              show : true,
					              title : '数据视图',
					              readOnly: false,
					              lang : ['数据视图', '关闭', '刷新']
					          },
					          dataZoom : {
					              show : true,
					              title : {
					                  dataZoom : '区域缩放',
					                  dataZoomReset : '区域缩放-后退'
					              }
					          },
					          restore : {show: true},
					          saveAsImage : {show: true}
					      }
					  };
			     
			     var option={
			         title : {
			    	     x:'center',
			             text: opts.title,
			         },
			         tooltip : {
			             trigger: 'axis'
			         },
			         legend: {
			        	 y:30,
			             data:[]
			         },
			         calculable : false,
			         xAxis : [
			             {
			                 type : 'category',
			                 boundaryGap : false,
			                 data : xaxisArray,
			                 show:true,
			                 axisLabel:{  
				            	 interval:0 ,  
				            	 rotate:45,
				                 margin:2,
				            	 formatter:function(val){  
				            		  var valSplis = val.split("");
				            		  var valShow = "";
				            		  var xLableLenth = xaxisArray.length;
				            		  if(val.length<=10){
				            			  return val;
				            		  }
				            		  if(xLableLenth<=6){
				            			  return val;
				            		  }
				            		  for(var i in valSplis){
				            			  valShow += valSplis[i];
				            			  if(i%11==0){
				            				  valShow += "\n";
				            			  }
				            		  }
				            		  return valShow;
				            	}  
			            	}
			             }
			         ],
			         yAxis : [
			             {
			                 type : 'value',
			             }
			         ],
			         series : []
			     };
			     
			     if(opts.isDataZoom){
			    	 option.dataZoom = dataZoom;
			     }
			     if(opts.isToolbox){
			    	 option.toolbox = toolbox;
			     }
			     myChart.setOption(option);   
			     for ( var i = 0; i < statdata.length; i++) {
			    	 option.legend.data.push(legendArray[i]);
			    	 option.series.push({
			    	         name: legendArray[i], // 系列名称
			    	         type: 'line', // 图表类型，折线图line、散点图scatter、柱状图bar、饼图pie、雷达图radar
			    	         data: statdata[i],
			    	         markPoint : {
			    	                data : [
			    	                    {type : 'max', name: '最大值'},
			    	                    {type : 'min', name: '最小值'}
			    	                ]
			    	            },
			    	     /*       markLine : {
			    	                data : [
			    	                    {type : 'average', name: '平均值'}
			    	                ]
			    	        }*/
			    	 });
			    	 myChart.setOption(option);
			     }
				
			}
		});
	},
	/**
	 * showChart_bar生成柱状图：$('#testPie').showChart_Pie({url:"${ctx}/demo/getLineData",title:"占比"});
	 * 传入数据的格式为：{legends:[],xaxis:[],datas:[]}
	 * @param $
	 */
	$.fn.showChart_bar = function(options) {
		var _self = this;
		var defaults = {url:'',title:'2',name: '',isToolbox:false,isDataZoom:false};
		var opts = $.extend(defaults, options);
		if(!$(this)[0] || !$(this)[0].id){
			alert("需要生成图表的对象必须有id"); return;
		}
		var tarId = $(this)[0].id;
		$.ajax({
			url:opts.url,
			type:'post',
			success:function(chartData){
				var legendArray=chartData.legends;//显示多个线条内容 
				var xaxisArray=chartData.xaxis;
				var statdata = chartData.datas;
			    var myChart = echarts.init(document.getElementById(tarId));
			     
			    var toolbox =  {
			   		 orient:'vertical',
			   		 x:'right',
			   		 y:40,
			            show : true,
			            feature : {
			                mark : {show: true},
			                magicType: {
			                    show : true,
			                    title : {
			                        line : '动态类型切换-折线图',
			                        bar : '动态类型切换-柱形图',
			                        stack : '动态类型切换-堆积',
			                        //tiled : '动态类型切换-平铺'
			                    },
			                    //type : ['line', 'bar', 'stack', 'tiled'],
			                    type : ['line', 'bar','stack'],
			                },
			                dataView : {
			                    show : true,
			                    title : '数据视图',
			                    readOnly: false,
			                    lang : ['数据视图', '关闭', '刷新']
			                },
			                dataZoom : {
			                    show : true,
			                    title : {
			                        dataZoom : '区域缩放',
			                        dataZoomReset : '区域缩放-后退'
			                    }
			                },
			                restore : {show: true},
			                saveAsImage : {show: true}
			            }
			        };
			        var dataZoom = {
			   			show : true,
			   			realtime : true,
			   			start : 20,
			   			end : 80
			   	 };
			   	 
			        var option={
			            title : {
			       	     x:'center',
			                text: opts.title,
			            },
			            tooltip : {
			                trigger: 'axis'
			            },
			            legend: {
			           	 y:30,
			                data:[]
			            },
			            calculable : false,
			            xAxis : [
			                {
			                    type : 'category',
			                    boundaryGap : true,
			                    data : xaxisArray,
			                    show:true,
			                    axisLabel:{  
			   	            	 interval:0 ,  
			   	            	 rotate:45,
			   	                 margin:2,
			   	            	 formatter:function(val){  
			   	            		  var valSplis = val.split("");
			   	            		  var valShow = "";
			   	            		  var xLableLenth = xaxisArray.length;
			   	            		  if(val.length<=8){
			   	            			  return val;
			   	            		  }
			   	            		  if(xLableLenth<=6){
			   	            			  return val;
			   	            		  }
			   	            		  for(var i in valSplis){
			   	            			  valShow += valSplis[i];
			   	            			  if(i%9==0){
			   	            				  valShow += "\n";
			   	            			  }
			   	            		  }
			   	            		  return valShow;
			   	            	}  
			               	}
			                }
			            ],
			            yAxis : [
			                {
			                    type : 'value',
			                }
			            ],
			            series : [
			                  
			              ]
			        };
			        
			        if(opts.isDataZoom){
			       	 option.dataZoom = dataZoom;
			        }
			        if(opts.isToolbox){
			       	 option.toolbox = toolbox;
			        }
			        
			        
			        myChart.setOption(option);
			        for ( var i = 0; i < statdata.length; i++) {
			       	 option.legend.data.push(legendArray[i]);
			       	 option.series.push({
			       	         name: legendArray[i], // 系列名称
			       	         type: 'bar', // 图表类型，折线图line、散点图scatter、柱状图bar、饼图pie、雷达图radar   
			       	         data: statdata[i],
			       	         markPoint : {
			       	                data : [
			       	                    {type : 'max', name: '最大值'},
			       	                    {type : 'min', name: '最小值'}
			       	                ]
			       	         },
			       	         markLine : {
			       	                data : [
			       	                    {type : 'average', name: '平均值'}
			       	                ]
			       	         }
			       	 });
			       	 myChart.setOption(option);
			        }
			}
		});
	}
})(jQuery);