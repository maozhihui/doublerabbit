<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>首页</title>

<link href="${ctx }/css/global.css" rel="stylesheet" data-role="global">
<link href="${ctx }/css/bee.css" rel="stylesheet" data-role="global">
<link href="${ctx }/css/product_list.css" rel="stylesheet" data-role="global">
<link href="${ctx }/css/left_menu.css" rel="stylesheet" data-role="global">
<script type="text/javascript" src="${ctx }/js/jquery-2.0.3.min.js"></script>


<head />
<body>
	<div id="pageWrapper">
		<div class="header">
			<div class="topnav" id="topnav">
				<h1>
					<a class="" href="" title="" data-hot="header.qcloudid"
						data-event="hot_tag_link"
						style="color: white; font-size: 18px; line-height: 45px;">物联网平台</a>
				</h1>
				<div class="top-panel">
					<ul class="top-menu pandect-menu">
						<li><a href="#" data-event="nav" data-hot="header.all"
							id="t_overview">首页</a></li>
						<li id="top_submenu" data-event="top_submenu"
							data-menu-type="product"><a href="javascript:;"
							style="cursor: default" id="t_product">产品总览<i
								class="top-menu-arrows"></i></a>
							<div class="drop-down-layer product-service" id="productService"
								style="left: -52px; display: none;">
								<div class="service-menu-wrapper">
									
									<div class="">
								         <div class="product-nav">
								            
								             <a href="" class="button expand fr  create j_create i"><i class="icon-product-add"></i>创建产品</a>
								         </div>
								
								         <div class="product-body">
											 <div class="p-wrap"><div class="product-list">
												<div class="product-content">
													<div class="product-name"><a href="" class="dev-name tooltip" title="" style="position: relative; display: inline-block;"><h3>产品111</h3><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: 15px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">体验用产品</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 32px;"></div></div></a> <span>其他</span><a href="javascript:;" class="fr icon-delete j_delete tooltip" data-pid="86994" title="" style="position: relative; display: inline-block;"><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: -3px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">删除</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 14px;"></div></div></a><a href="edit?pid=86994" class="fr icon-edit tooltip" title="" style="position: relative; display: inline-block;"><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: -3px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">编辑</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 14px;"></div></div></a></div>
													<p class="access-type">设备接入方式：HTTP</p>
													<p class="time">创建时间：2017-05-16 17:15</p>
													
												</div>
												</div>
												<div class="product-list">
													<div class="product-content">
														<div class="product-name"><a href="pid=82897" class="dev-name tooltip" title="" style="position: relative; display: inline-block;"><h3>产品222</h3><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: 13.5px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">HTTP_Test</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 31.5px;"></div></div></a> <span>智能家居</span><a href="javascript:;" class="fr icon-delete j_delete tooltip" data-pid="82897" title="" style="position: relative; display: inline-block;"><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: -3px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">删除</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 14px;"></div></div></a><a href="edit?pid=82897" class="fr icon-edit tooltip" title="" style="position: relative; display: inline-block;"><div class="tooltip" style="position: absolute; display: inline-block; top: -32px; opacity: 0; left: -3px;"><div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">编辑</div><div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 14px;"></div></div></a></div>
														<p class="access-type">设备接入方式：HTTP</p>
														<p class="time">创建时间：2017-03-23 10:20</p>
														
													</div>
												</div>                    
											</div>              
								         </div>
								     </div>
									
									
									
										
								</div>
							</div></li>
					</ul>
					<ul class="top-menu custom-menu nav-menu-list"
						style="max-width: 33%;">

					</ul>
					<a class="custom" data-event="nav-custom" href="javascript:;"><i
						class="ico-set-menu" id="t_custom">自定义</i></a>
					<ul class="top-menu user-menu" style="">
						<li id="userInfo" data-event="top_submenu" data-menu-type="user"
							class="link" style="position: relative" data-menu-inited="true">
							<a href="" data-event="nav" data-hot="header.user.center"
							title="管理员"><span class="user-name">管理员</span><i
								class="top-menu-arrows" style=""></i></a>
							<div id="userInfo_drop_down_layer"
								class="drop-down-layer admin-center" style="right: 0px;">
								<div class="admin-center-inf"></div>
								<div class="admin-center-list">
									<ul class="select-list clearfix">
										<li><a href="" data-event="nav"
											data-hot="header.user.developer">账户信息</a></li>
										<li><a href="" data-hot="header.user.permission">权限设置</a></li>
										<li style="" data-auth="project"><a href=""
											data-event="nav" data-hot="header.user.project">修改密码</a></li>
									</ul>
								</div>
								<div class="exit">
									<a href="javascript:void(0)" data-event="logout" class="btn-exit">退出</a>
								</div>
							</div>
						</li>

					</ul>
				</div>

			</div>
		</div>
		<div class="container container-deal" id="container" style="left: 0px;">
			<table style="width: 100%;height: 100%;" border="0">
				<tbody>
				<tr>
					<td style="width: 120px;">
						<div class="aside" id="sidebar">
							   <aside class="main-sidebar" style="padding-top:0px;">
							    <section class="sidebar">
							        <ul class="sidebar-menu">
							            <li class="active">
							                <a href="">
							                    <i class="fa fa-product-summary"></i>
							                    <span>产品概况</span>
							                </a>
							            </li>
							            <li>
							                <a href="">
							                    <i class="fa fa-product-manage"></i>
							                    <span>设备管理</span>
							                </a>
							            </li>
							            <li>
							                <a href="">
							                    <i class="fa fa-data-template"></i>
							                    <span>遥测数据</span>
							                </a>
							            </li>
							            <li>
							                <a href="">
							                    <i class="fa fa-online-debug"></i>
							                    <span>规则管理</span>
							                </a>
							            </li>
							            <li>
							                <a href="">
							                    <i class="fa fa-trigger-manage"></i>
							                    <span>策略管理</span>
							                </a>
							            </li>
							            <li>
							                <a href="">
							                    <i class="fa fa-app-manage"></i>
							                    <span>应用管理</span>
							                </a>
							            </li>
							             <li>
							                <a href="">
							                    <i class="fa fa-device-group"></i>
							                    <span>报表分析</span>
							                </a>
							            </li>
							        </ul>
							    </section>
							</aside>


							<a id="left_menu_control" class="btn-fold-menu"
								href="javascript:void(0);" title="收起" data-event="toggle_sidebar">收起</a>
						</div>
					</td>
					<td style="height: 100%">
						<iframe  id="myIframe" class="myIframe" src="${ctx}/product/index" name="content-iframe" 
						 style="width: 100%;height: 100%;overflow-y: auto;overflow-x: hidden;border: none;">
						</iframe>	
					</td>
				</tr>
				</tbody>
			</table>
			
		
	</div>
</div>
	

	<script type="text/javascript">
		$(function() {

			$("#top_submenu").mouseleave(function() {
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
			});

			$("#left_menu_control").click(function() {
				if ($("#left_menu_control").hasClass("retract")) {
					$("#container").css("left", "0px");
					$("#left_menu_control").removeClass("retract");
				} else {
					$("#container").css("left", "-120px");
					$("#left_menu_control").addClass("retract");
				}
			});
		});
		
		var base = "${ctx}" ;
		function iframeByMenuUrl(menuUrl){
			$("#myIframe").attr("src",base + menuUrl);
		}
	</script>
</body>
</html>