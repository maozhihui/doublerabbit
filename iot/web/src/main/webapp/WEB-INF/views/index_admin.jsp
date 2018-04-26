<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${_title }</title>
<!--  字体 -->
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

</style>

<head />
<body>
	<div id="pageWrapper">
		<div class="header">
			<div class="topnav" id="topnav">
				<h1>
					<img src="${ctx}/images/logoIcon.png" style="width: 48px; margin-right: 6px;margin-left: 5px;">
					<a class="" href="" title="" data-hot="header.qcloudid" data-event="hot_tag_link" style="color: #fbb706; font-size: 18px; line-height: 45px;">物联网管理平台</a>
				</h1>
				<div class="top-panel">
					<ul class="top-menu pandect-menu">
						<li class="common_list active">
							<a href="javascript:iframeByMenuUrl('/mainContent_admin');" data-event="nav" data-hot="header.all"
							id="t_overview">首页</a>
						</li>
					</ul>
					<ul class="top-menu user-menu" style="">
						<li id="userInfo" data-event="top_submenu" data-menu-type="user" class="link" style="position: relative" data-menu-inited="true">
							<a href="" data-event="nav" data-hot="header.user.center"
							title="管理员">
								<span class="user-name">${_displayName }</span>
								<i class="top-menu-arrows" style=""></i>
							</a>
							<div id="userInfo_drop_down_layer" class="drop-down-layer admin-center" style="right: 0px;display:none;">
								<div class="admin-center-inf"></div>
								<div class="admin-center-list" style="box-shadow: 3px 3px 3px #a59e9e;">
									<ul class="select-list clearfix">
										<li>
											<a href="javascript:view_user()" data-event="nav" data-hot="header.user.developer"><i class="fa fa-user"></i>账户信息</a>
										</li>
										<li style="" data-auth="project">
											<a href="javascript:modifyPW()" data-event="nav" data-hot="header.user.project"><i class="fa fa-pencil"></i>修改密码</a>
										</li>
										<li style="border-top: 1px solid #b9b3b3;">
											<a href="${ctx}/loginOut" data-event="logout" class="btn-exit"><i class="fa fa-power-off"></i>退出</a>
										</li>
									</ul>
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
							<li>
							    <a href="javascript:iframeByMenuUrl('/user/user-list');">
							        <i class="fa fa-user-circle-o"></i>
							        <span>用户管理</span>
							     </a>
							 </li>
							<!--   <li>
							    <a href="javascript:iframeByMenuUrl('/tenant/list');">
							        <i class="fa fa-users"></i>
							        <span>租户管理</span>
							    </a>
							 </li>-->
							 <li>
							    <a href="javascript:iframeByMenuUrl('/category/list');">
							         <i class="fa fa-th-list"></i>
							         <span>设备类别管理</span>
							    </a>
							 </li>
							 <li>
							    <a href="javascript:iframeByMenuUrl('/deviceTemplate/list');">
							       <i class="fa fa-file-word-o"></i>
							       <span>参数模板管理</span>
							    </a>
							 </li>
							<!--  <li>
							    <a href="javascript:iframeByMenuUrl('/plugin/list');">
							        <i class="fa fa-level-down"></i>
							        <span>插件管理</span>
							    </a>
							 </li>
							 <li>
							     <a href="javascript:iframeByMenuUrl('/rule/list');">
							        <i class="fa fa-tag"></i>
							        <span>规则管理</span>
							     </a>
							 </li>-->
							 <li >
							    <a href="javascript:iframeByMenuUrl('/audit/audit_list');">
							       <i class="fa fa-file-text-o"></i>
							       <span>系统日志</span>
							    </a>
							 </li>
							 <li >
							    <a href="javascript:iframeByMenuUrl('/monitor/index');">
							       <i class="fa fa-line-chart"></i>
							       <span>系统监控</span>
							    </a>
							 </li>
						</ul>
					</section>
				</aside>
				<a id="left_menu_control" class="btn-fold-menu"	href="javascript:void(0);" title="收起" data-event="toggle_sidebar">收起</a>
			</div>
			<iframe  id="myIframe" class="myIframe" src="${ctx}/mainContent_admin" name="content-iframe" ></iframe>	
		</div>
	</div>
	

	<script type="text/javascript">
		$(function() {

			/*$("#top_submenu").mouseleave(function() {
				$("#productService").hide();
			});
			$("#top_submenu").mouseover(function() {
				$("#productService").show();
			});
			$("#userInfo").mouseleave(function() {
				$("#userInfo_drop_down_layer").hide();
			});
			$("#userInfo").mouseover(function() {
				$("#userInfo_drop_down_layer").show();
			});*/
			
			$("#userInfo").hover(function(){
				$("#userInfo_drop_down_layer").show();
			},function(){
				$("#userInfo_drop_down_layer").hide();
			})
			$("#left_menu_control").click(function() {
				if ($("#left_menu_control").hasClass("retract")) {
					$("#container").css("left", "0px");
					$("#myIframe").css("width","calc(100% - 165px)");
					$("#left_menu_control").removeClass("retract");
				} else {
					$("#container").css("left", "-165px");
					$("#myIframe").css("width","100%");
					$("#left_menu_control").addClass("retract");
				}
			});
		});
		
		var base = "${ctx}" ;
		function iframeByMenuUrl(menuUrl){
			$("#myIframe").attr("src",base + menuUrl);
		}
		//菜单样式切换
		$("#mainMenu_ul li").click(function(){
			$(".pandect-menu li").removeClass("active"); 
			$("#mainMenu_ul").children().siblings().removeClass("active");  //遍历删除样式
            $(this).addClass("active");
		})
		$(".common_list").each(function(){
			$(this).click(function(){
				$(".pandect-menu li").removeClass("active"); 
				$("#mainMenu_ul").children().siblings().removeClass("active"); 
				$(this).addClass("active");
			})
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