<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
	<script type="text/javascript" src="${ctx}/plugins/easyui/jquery.easyui.min.js"></script>

<style type="text/css">
.panel_min_height{
	min-height: 550px;
	margin-bottom: 5px;
}
.op_select_tip{
	background-color: rgb(174, 208, 236);
	padding: 7px;
	font-weight: bold;
}
.select_tree_div{
	height: 550px;
	overflow: auto;
}
.multi_headborder{
	border: 2px solid #3ABCC7;
	margin-top: 0px;
}
.multi_headcolor{
	padding: 10px 15px;
	background-color: #3ABCC7;
	color: #fff;
}
</style>	
</head>

<body class="page-header-fixed"> 
	<div class="clearfix"></div>
	<div class="page-container">
		<div id="myheading">
		<div class="content content-inner">
			<div class="content content-inner">
			<div class="panel panel-default headborder">
			<div class="panel-heading headcolor">
				<div class="headcolor-left"></div>
				<div class="headcolor-main"></div>
				<h3 class="panel-title">用户授权</h3>
			</div>
			<div class="list_search_head">
				<div id="collapse-group" class="">
				<form action="" class="">
					<div class="list_search_head_left" style="left:10px;">
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="userCode" value="用户账号" >用户账号</option>
								<option name="userName" value="真实姓名" >真实姓名</option>
							</select>
						</div>
						<div class="">
							<select class="form-control" style="display:none;">
								<option value="1" >包含</option>
								<option value="2" >等于</option>
							</select>
						</div>									
						<div class="">
							<input style="width: 135px;" type="text" name="searchText" class="form-control searchText" placeholder="查询内容">
						</div>
						<div class="">	
							<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_search"></i>
								<span class="search_head_span">查询</span>
							</a>
						</div>	
					</div>
					<div style="clear:both;"></div>
				</form>
				</div>
				
			</div>
			<table style="width: 100%;">
				<tr>
					<td style="width: 3px;"> &nbsp;</td>
					<td style="width: 50%;" valign="top">
						<div class="list_search_head_right" style="position: initial;">
							<a href="javascript:check_user();" class="btn btn-sm search_a_check">
								<i class="search_head_check"></i>
								<span class="search_head_span">查看</span>
							</a>
						</div>
						<div class="panel panel-default multi_headborder panel_min_height" style="margin-top: 0px;">
							<div class="multi_headcolor ">
								<h3 class="panel-title-boostrap">用户列表</h3>
							</div>
							<div id="userList" class="select_tree_div"></div>
						</div>
					</td>
					<td style="width: 5px;"> &nbsp;</td>
					<td style="width: 50%;" valign="top">
						<div class="list_search_head_right" style="position: initial;height: 33px;">
							<security:authorize code="userM_assign">
								<a href="javascript:saveUserRole();" class="btn btn-sm search_a_role" style="background: #e56a49;">
									<i class="search_head_role"></i>
									<span class="search_head_span">保存角色</span>
								</a>
							</security:authorize>				
						</div>
						<div class="panel panel-default multi_headborder panel_min_height" style="margin-top: 0px;">
							<div class="multi_headcolor ">
								<h3 class="panel-title-boostrap">角色列表<span id="userName_text"></span></h3>
							</div>
							<div id="roleList" class="select_tree_div"><div class="op_select_tip">请在左边用户列表中选中用户</div></div>
						</div>
					</td>
					<td style="width: 5px;"> &nbsp;</td>
				</tr>
			</table>
			
		</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>


  
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var userPageGrid = new PageGrid({});
var selectUserId = -1;//标识当前选中的用户ID
var selectRoleId = -1;//标识当前选中的角色ID
//通过用户获取角色
function getRoles(userId,userName,isSupperAdmin){
	if(isSupperAdmin=="1"){
		new alertdialog({title:"提示",content:"不允许修改超级管理员权限！"});return;
	}
	selectUserId = userId;
	$("#userName_text").html('【'+userName+'】');
	$("#roleList").html("");
	$("#roleList").tree({
		url : "${ctx}/role/getTreeDataByUserChecked?expand=1&userId="+userId,
		checkbox : true,
		method : "get",
		animate : true,
		panelHeight : 150,
		cascadeCheck : false,
		fitColumns : true,
		fit : true,
		animate : true,
		nowrap : false,
		onSelect : function(node) {
			
		},
		onCheck : function(node) {
			if(node.id==1 && node.checked){
				new alertdialog({title:"提示",content:"不允许分配超级管理员权限！"});
				$("#roleList").tree('uncheck', node.target);
				return false;
			}
			
		},
		onLoadSuccess : function() {

		},
		onLoadError : function(xhr, textStatus, error) {
		}
	});
}
//保存用户和角色关联
function saveUserRole(){
	if(selectUserId==-1){
		new alertdialog({title:"提示",content:"请选择需要分配角色的用户！"});
		return;
	}
	var nodes = $('#roleList').tree('getChecked');
	var roleIds = '';
	for(var i=0; i<nodes.length; i++){
		if (roleIds != '') roleIds += ',';
		roleIds += nodes[i].id;
	}
	_showCover();
	$.ajax({
        url: "${ctx}/user/save_user_role",
        datatype: 'json',
        type: "Post",
        error:function(){
        	_removeCover();
        },
        data:{userId:selectUserId,roleIds:roleIds},
        success: function (data) {
        	_removeCover();
        	if(data==1){
        		new alertdialog({title:"提示",content:"保存成功！"});
        	}else{
        		new alertdialog({title:"提示",content:"保存失败！"});
        	}
        }
	});
}


//编辑
function edit_user(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		userPageGrid.createModal("编辑用户","${ctx}/user/user_edit?userId="+selectedId,420,800);
	}
}
//查看
function check_user(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		userPageGrid.createModal("查看用户","${ctx}/user/user_check?userId="+selectedId,420,800);
	}
}
/*获取选中记录*/
function getSeletedId(){
	var ids = userPageGrid.getSelectedRowIds();
	if(ids.length==0){
		//alert("请选中一条记录！");return -1;
		new alertdialog({title:"提示",content:"请选中一条记录！"});return -1;
	}
	if(ids.length>1){
		//alert("只能操作一条记录！");return -1;
		new alertdialog({title:"提示",content:"只能操作一条记录！"});return -1;
	}
	return ids[0];
}


$(function() {
	var columns = [{
		field : "userCode",
		title : "用户账号",
		width : 100
		}, {
			field : "userName",
			title : "真实姓名",
			width : 100
		}, {
			field : "organization.name",
			title : "所属机构",
			width : 150
		}] ;
	
	userPageGrid.setConfig({noShowNumPerPage:1});
	userPageGrid.paging("userList","userId",columns,'${ctx}/user/datasByPage');
	userPageGrid.setRowClickFun("getRoles",["userId","userName","isSupperAdmin"]);
	
    $("#queryBtn").bind("click",function(){
    	userPageGrid.pageQuery_2("searchForm"); 
    });
});

//关闭窗口触发的操作
function hide_modal(tarId,isRefresh){
	if(tarId && $("#"+tarId)){
		$('#'+tarId).modal('hide');
		$("#"+tarId).empty();
	}
	if(isRefresh){
		userPageGrid.pageRefresh();//刷新列表
	}
}
</script>
</html>