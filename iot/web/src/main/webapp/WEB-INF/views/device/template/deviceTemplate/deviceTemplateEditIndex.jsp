<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	
	<style type="text/css">
		
		html,body,#myheading,.content-inner{
			height:100%;
		}
		.tab_name{

		}
		.tab_border_right{
			border-right: 1px solid #4598E4;
			height: 42px;
		}
		.tab_border_right a{
			color:#000;
		}
		#menu .tab_border_right a{
			padding-top: 11px;
			height: 42px;
			width: 120px;
			text-align: center;	
		}
		#menu .tab_border_right.active{
			background: #4D99E4;
			
		}
		#menu .tab_border_right.active a{
			color: #fff;
		}
		#menu .tab_border_right a > span:hover{
			color: #fff;
		}
		.dropdown-menu-second{
			background-color: #f4f4f4;
			border-bottom: 1px solid #FFFFFF;
			border-top: 1px solid #E7E7E7;
			display: block;
			line-height: 40px;
			padding: 0 10px;
		}
		
		.nav .open > a, .nav .open > a:hover, .nav .open > a:focus{
			background-color: #4D99E4 !important;
			border-color: #428bca;
			color: #fff;
		}
		.nav > li > a:hover, .nav > li > a:focus{
			text-decoration: none;
			background-color: #4D99E4 !important;
			color: #fff;
		}
		.nav > li{
				
		}
		.info_main{
			height:calc(100% - 134px);
		}
	</style>
</head>
<body class="page-header-fixed" style="overflow-x: hidden;"> 
	<div class="page-container">
		<div id="myheading">
			<div class="content content-inner">
				<div style="padding:20px 20px 0;">
					<h3 class="panel-title" style="margin-left:0px;">
						编辑参数模板【${deviceTemplate.name }】
					</h3>
					<div id="menu">
						<ul id="top_bar_ul" class="nav navbar-nav pull-left hidden-xs" style="background-color: #fff;border: 1px solid #4598E4;">
						 	<li class="tab_border_right  item_info">
								<a href="javascript:iframeByMenuUrl('${ctx}/deviceTemplate/to_deviceTemplate_edit?deviceTemplateId=${deviceTemplate.deviceTemplateId }')" style="" class="team-status-toggle dropdown-toggle tip-bottom" data-toggle="tooltip" >
									<i class="fa fa-book"></i>
									<span class="tab_name">基本信息</span>
								</a>
						  	</li>
						    <li class="tab_border_right item_param">
								<a href="javascript:iframeByMenuUrl('${ctx}/attributesTemplate/list?isView=0&isTelemetry=0&deviceTemplateId=${deviceTemplate.deviceTemplateId }');" class="team-status-toggle dropdown-toggle tip-bottom " data-toggle="tooltip" >
									<i class="fa fa-cog"></i>
									<span class="tab_name">控制参数</span>
								</a>
							</li>
							<li class="tab_border_right">
								<a href="javascript:iframeByMenuUrl('${ctx}/attributesTemplate/list?isView=0&isTelemetry=1&deviceTemplateId=${deviceTemplate.deviceTemplateId }');" class="team-status-toggle dropdown-toggle tip-bottom" data-toggle="tooltip" >
									<i class="fa fa-bookmark"></i>
									<span class="tab_name">遥测参数</span>
								</a>
							</li>
						</ul>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="info_main">
					<iframe src="${ctx}/deviceTemplate/to_deviceTemplate_edit?deviceTemplateId=${deviceTemplate.deviceTemplateId }"  onload="/*changeCaseFrameHeight();*/" style="overflow:auto;height:100%" width="100%" id="deviceTemplateIframe" scrolling="no"  frameborder="0">
					</iframe>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">

function iframeByMenuUrl(menuUrl){
	$("#deviceTemplateIframe").attr("src",menuUrl);
}


/*function changeCaseFrameHeight(){
	
    var ifm= document.getElementById("deviceTemplateIframe"); 
    var clientHeight = document.documentElement.clientHeight;
	var src = ifm.src;
    ifm.height=clientHeight;
    $("#deviceTemplateIframe").css("height",clientHeight);
    
}*/

$(function(){
	if(getUrlParam("saveItem")){
		iframeByMenuUrl('${ctx}/attributesTemplate/list?isTelemetry=0&deviceTemplateId=${deviceTemplate.deviceTemplateId }');
	}
	if(getUrlParam("saveItem")){
		$(".item_param").addClass("active");
	} else {
		$(".item_info").addClass("active");
	}
	$("li.tab_border_right").each(function(){
		$(this).bind("click",function(){
			$("li.tab_border_right").removeClass("active");
			$(this).addClass("active");
		})
	})
})

//返回
function returnByUrl(){
	var returnUrl = '${returnUrl}';
	if(returnUrl=='' || returnUrl=='-1'){
		returnUrl = '/deviceTemplate/list';
	}
	window.location.href="${ctx}" + returnUrl;
}
</script>
</body>
</html>