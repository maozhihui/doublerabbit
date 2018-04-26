<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${_title }</title>
<%@include file="/common/meta.jsp"%>
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
.sub_menu .fa-chevron-circle-right:before{
    font-size:20px;
}

</style>
</head>
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
						<!--  <li class="common_list">
							<a href="javascript:iframeByMenuUrl('${ctx}/product/index');" data-event="nav" data-hot="header.all"
							id="t_overview">首页</a>
						</li>	-->			
						<li id="top_submenu" data-event="top_submenu" data-menu-type="product">
							<a href="javascript:;" style="cursor: default" id="t_product">
							产品总览<i class="top-menu-arrows"></i>
							</a>
							<div class="drop-down-layer product-service" id="productService" style="left: -52px; display: none">
								<div class="product-nav">
								     <a href="javascript:add_product();" id="add_product" class="button expand fr  create j_create i">
								     	<i class="fa fa-plus" style="line-height: 27px;"></i>创建产品
								     </a>
								</div>
								<div class="product-body">
									<div class="p-wrap" id="p-wrap"></div>
								</div>
							</div>
						</li>
					</ul>
					<ul class="top-menu custom-menu nav-menu-list"	style="max-width: 33%;"></ul>
					
					<ul class="top-menu user-menu" style="">
						<li id="userInfo" data-event="top_submenu" data-menu-type="user" class="link" style="position: relative" data-menu-inited="true">
							<a href="" data-event="nav" data-hot="header.user.center" title="管理员">
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
			<div class="aside" id="sidebar" style="overflow-y:auto;padding-bottom: 60px;">
				<aside class="main-sidebar" style="padding-top:0px;">
					<section class="sidebar">
						<ul id="mainMenu_ul" class="sidebar-menu ul-list">
							 <li class="active" id="product_index">
							      <a href="javascript:iframeByMenuUrl('/product/index');">
							          <i class="fa fa-wpforms"></i>
							          <span>产品概况</span>
							      </a>
							 </li>
							 <li>
							      <a href="javascript:iframeByMenuUrl('/device/list');">
							          <i class="fa fa-cog"></i>
							          <span>设备管理</span>
							      </a>
							  </li>
							   <!--<li>
							       <a href="javascript:iframeByMenuUrl('/ruleTenant/list');">
							           <i class="fa fa-tag"></i>
							           <span>规则管理</span>
							       </a>
							   </li> -->
                            <li class="treeview">
                                <a class="inactive" target="content-iframe">
                                    <i class="fa fa-user"></i> <span>告警管理</span>
                                </a>
                                <ul class="sub_menu" style="display:none;">
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/alarmRule/alarmRule_list');"  >
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>规则管理</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/activeAlarm/activeAlarm_list');">
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>活动告警</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/historyAlarm/historyAlarm_list');">
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>历史告警</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
							       <a href="javascript:iframeByMenuUrl('/monitor/analysis');">
							            <i class="fa fa-line-chart"></i>
							            <span>统计分析</span>
							       </a>
                            </li>
                            <li class="treeview">
                                <a class="inactive" target="content-iframe">
                                    <i class="fa fa-user"></i> <span>升级管理</span>
                                </a>
                                <ul class="sub_menu" style="display:none;">
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/appVersion/list');"  >
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>版本管理</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/upgradeTask/upgradeTask_list');">
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>升级任务</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:iframeByMenuUrl('/upgradeRecord/upgradeRecord_list');">
                                            <i class="fa fa-chevron-circle-right"></i>
                                            <span>升级记录</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
						 </ul>
					  </section>
				</aside>
				<a id="left_menu_control" class="btn-fold-menu" href="javascript:void(0);" title="收起" data-event="toggle_sidebar">收起</a>
			</div>
			<iframe  id="myIframe" class="myIframe" src="${ctx}/product/index" name="content-iframe"></iframe>
		</div>
	</div>
	

<script type="text/javascript">
	$(function() {
		var passwordType='${sessionScope.pwdType}';
		if(passwordType==1){
			new alertdialog({title:"提示",content:"请修改初始密码！",confirmFun:function(){
				modifyPW(true);
			}});
		}
		$("#top_submenu").hover(function(){
			$("#productService").show();
		},function(){
			$("#productService").hide();
		})
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

        $(".treeview").click(function(){
            if($(this).children("a").attr("class") != "inactives"){
                var className = $(this).parent().attr("class");
                if($(this).children("a").siblings("ul").css("display") == "none"){
                    if(className == "sidebar-menu ul-list"){
                        $(this).children("a").removeClass("inactive").addClass("inactives");
                        $(this).children("a").parents("li").children("ul").slideDown(100);
                    }

                    $(this).children("a").parents("li").siblings().children("ul").slideUp(100);
                    $(this).children("a").parents("li").siblings().children("ul").siblings("a").removeClass("inactives").addClass("inactive");
                    $(".ul-list-li-1").find("a").removeClass("inactives").addClass("inactive");
                }
            }else if($(this).children("a").attr("class") == "inactives"){
                $(this).children("a").siblings("ul").hide();
                $(this).children("a").removeClass("inactives").addClass("inactive");
            }
        });
        //菜单鼠标点击样式
        $(".treeview li").click(function(event){
            $(".treeview li").removeClass("left-menu-change");
            $(this).addClass("left-menu-change");
            $(".treeview li a").removeClass("active");
            event.stopPropagation();
        });

		initProduct();
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
	function changeProductIndex(productId){
	   var productUrl = '${ctx}/product/index?productId='+productId;
	   $("#myIframe").attr("src",productUrl);
	   $(".pandect-menu li").removeClass("active"); 
	   $("#mainMenu_ul").children().siblings().removeClass("active");
	   $("#product_index").addClass("active");
	   $("#productService").hide();
   }	
	var productPageGrid = new PageGrid({});
	//添加产品
	function add_product(){
		productPageGrid.createModal("新建产品","${ctx}/product/product_edit",450,800);
	}
	//编辑产品
	function edit_product(id){
		productPageGrid.createModal("编辑产品","${ctx}/product/product_edit?productId="+id ,450,800);
	}
	//查看产品
	function view_product(id){
		productPageGrid.createModal("编辑产品","${ctx}/product/product_view?productId="+id ,450,700);
	}
	//删除产品
	function delete_product(id){
		console.log(id);
		var ids = [];
		ids.push(id);
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
				url: "${ctx}/product/deleteProduct",
				datatype: 'json',
				type: "Post",
				data:{"ids":ids},
				success: function (data) {
				   if(data.flag=='success'){
					   initProduct();
					   window.frames["content-iframe"].location.reload();
				    }else{
				        new alertdialog({title:"提示",content:"删除失败!"});
				    }
				 }
			});
		}});
	}
	//关闭窗口触发的操作
	function hide_modal(tarId){
		if(tarId && $("#"+tarId)){
			$('#'+tarId).modal('hide');
			$("#"+tarId).empty();
		}
	}
		
	//
	function initProduct(){
		$.ajax({
			url: "${ctx}/product/datasByPage",
			datatype: 'json',
			type: "Post",
			async:false,
			success: function (data) {
			    items = data.result;
			}
		});
    var str="" ;
	for(var i=0;i<items.length;i++){
		str+='<div class="product-list">';
			str+='<div class="product-content" onclick="changeProductIndex(\''+items[i].productId+'\');" >';
				str+='<div class="product-name">';
					str+='<span>'+items[i].name+'</span><span class="list_catory">'+items[i].categoryName+'</span>';
				str+='</div>';
				str+='<div class="inner-content">';
					str+='<p class="access-type">设备接入方式：'+items[i].accessProtocol+'</p>';
					str+='<p class="time">创建时间：'+dataFormat(items[i].createTime,'yyyy-MM-dd hh:mm:ss')+'</p>';
				str+='<div>';
				str+='<a class="access-type" style="text-align: right;float: right;margin-left:5px;" href="javascript:delete_product(\''+items[i].productId+'\');"><i class="fa fa-minus-square fa-lg"></i>&nbsp;删除</a>';
				str+='<a class="access-type" style="text-align: right;float: right;" href="javascript:edit_product(\''+items[i].productId+'\');"><i class="fa fa-edit fa-lg"></i>&nbsp;编辑&nbsp;</a>';
				str+='</div>';
			str+='</div>';
		   str+='</div>';
		str+='</div>';
	}
	$("#p-wrap").empty();
	$("#p-wrap").append(str);
  }
	
   
	
  // 修改密码
	var passwordPageGrid = new PageGrid({});
	function modifyPW(param){
		var param = param?1:0;
		passwordPageGrid.createModal("修改密码","${ctx}/user/user_setPassword?autoset="+param,380,700);
	}

 //帐户信息
	var userPageGrid = new PageGrid({});
	function view_user(){
		userPageGrid.createModal("账户信息","${ctx}/user/to_view_info",250,900);
	}
	</script>
</body>
</html>