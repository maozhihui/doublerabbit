<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${_title }</title>
<%@include file="/common/include.jsp"%>
<link href="${ctx}/css/global.css" rel="stylesheet" data-role="global">
<link href="${ctx}/css/bee.css" rel="stylesheet" data-role="global">
<link href="${ctx}/css/product_list.css" rel="stylesheet" data-role="global">
<link href="${ctx}/css/left_menu.css" rel="stylesheet" data-role="global">
<style type="text/css">
.container{
	padding-left:0;
	padding-right:0;
}
#pageWrapper{
	width:100%;
	height:100%;
}
.header{
	width:100%;
	min-width:1100px;
}
#container{
	width:100%;
	min-width:1100px;	
}
#myIframe{
	width: calc(100% - 165px);
	margin-left:165px;
	height: 100%;
	overflow-y: auto;
	overflow-x: hidden;
	border: none;
}
a,a:hover{
  text-decoration:none;
}

</style>
</head>
<body>
	<div id="pageWrapper">
		<div class="header">
			<div class="topnav" id="topnav">
				<h1>
					<a class="" href="" title="" data-hot="header.qcloudid" data-event="hot_tag_link" style="color: white; font-size: 18px; line-height: 45px;">物联网平台</a>
				</h1>
				<div class="top-panel">
					<ul class="top-menu custom-menu nav-menu-list"	style="max-width: 33%;"></ul>
					
					<ul class="top-menu user-menu" style="">
						<li id="userInfo" data-event="top_submenu" data-menu-type="user" class="link" style="position: relative" data-menu-inited="true">
							<a href="" data-event="nav" data-hot="header.user.center" title="管理员">
								<span class="user-name">${_displayName }</span>
								<i class="top-menu-arrows" style=""></i>
							</a>
							<div id="userInfo_drop_down_layer" class="drop-down-layer admin-center" style="right: 0px;display:none;">
								<div class="admin-center-inf"></div>
								<div class="admin-center-list">
									<ul class="select-list clearfix">
										<li>
											<a href="javascript:view_user()" data-event="nav" data-hot="header.user.developer"><i class="fa fa-user"></i>账户信息</a>
										</li>
										<li style="" data-auth="project">
											<a href="javascript:modifyPW()" data-event="nav" data-hot="header.user.project"><i class="fa fa-pencil"></i>修改密码</a>
										</li>
									</ul>
								</div>
								<div class="exit">
									<a href="${ctx}/loginOut" data-event="logout" class="btn-exit"><i class="fa fa-power-off"></i>退出</a>
								</div>
							</div>
						</li>
					</ul>
				</div>

			</div>
		</div>
		<div class="container container-deal" id="container" style="left: 0px;">
			<div class="aside" id="sidebar">
				<aside class="main-sidebar" style="padding-top:0px;">
					<section class="sidebar">
						<ul id="mainMenu_ul" class="sidebar-menu">
							 <li id="device_list">
							      <a href="javascript:iframeByMenuUrl('/device/list');">
							          <i class="fa fa-cog"></i>
							          <span>设备管理</span>
							      </a>
							  </li>
							  <li>
							       <a href="javascript:iframeByMenuUrl('/monitor/analysis');">
							            <i class="fa fa-line-chart"></i>
							            <span>统计分析</span>
							       </a>
							  </li>
						 </ul>
					  </section>
				</aside>
				<a id="left_menu_control" class="btn-fold-menu" href="javascript:void(0);" title="收起" data-event="toggle_sidebar">收起</a>
			</div>
			<iframe  id="myIframe" class="myIframe" src="${ctx}/device/list" name="content-iframe"></iframe>
		</div>
	</div>
	

<script type="text/javascript">
	$(function(){
		$("#device_list").addClass("active");
		$("#userInfo").hover(function(){
			$("#userInfo_drop_down_layer").show();
		},function(){
			$("#userInfo_drop_down_layer").hide();
		})
	})
	var base = "${ctx}" ;
	function iframeByMenuUrl(menuUrl){
		$("#myIframe").attr("src",base + menuUrl);
	}
	//菜单样式切换
	$("#mainMenu_ul li").click(function(){
		$("#mainMenu_ul").children().siblings().removeClass("active");  //遍历删除样式
        $(this).addClass("active");
	})
	//关闭窗口触发的操作
	function hide_modal(tarId){
		if(tarId && $("#"+tarId)){
			$('#'+tarId).modal('hide');
			$("#"+tarId).empty();
		}
	}
  // 修改密码
	var passwordPageGrid = new PageGrid({});
	function modifyPW(){
		passwordPageGrid.createModal("修改密码","${ctx}/user/user_setPassword",380,700);
	}

 //帐户信息
	var userPageGrid = new PageGrid({});
	function view_user(){
		userPageGrid.createModal("账户信息","${ctx}/user/to_view_info",250,900);
	}
	</script>
</body>
</html>