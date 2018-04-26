<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
<script type="text/javascript" src="${ctx}/plugins/easyui/jquery.easyui.min.js"></script>

<style type="text/css">
.panel_min_height{
	min-height: 735px;
	height: 100%;
	margin-bottom: 5px;
}
.op_select_tip{
	background-color: rgb(174, 208, 236);
	padding: 7px;
	font-weight: bold;
}
.select_tree_div{
	height: 100%;
	overflow: auto;
}
.multi_headborder{
	border: 2px solid #06f;
	border-radius: 5px;
	margin-top: 0px;
}
.multi_headcolor{
	padding: 10px 15px;
	background-color: #3a6fd7;
	color: #fff;
}
.opration_group{
	padding-left: 5px;
}
.opration_group a{
	color: #fff;
	padding: 7px 12px 5px 12px;
}
</style>	
</head>

<body class="page-header-fixed"> 
	<div class="clearfix"></div>
	<div class="page-container">
		<div id="myheading">
		<div class="content content-inner headborder">
			<div class="content content-inner">
			<div class="panel-heading headcolor">
				<div class="headcolor-left"></div>
				<div class="headcolor-main"></div>
				<h3 class="panel-title">用户管理</h3>
			</div>
			<table style="width: 100%;">
				<tr>
					<td style="width: 3px;"> &nbsp;</td>
					<td style="width: 35%;" valign="top">
						<div class="panel panel-default multi_headborder panel_min_height" style="margin-top: 0px;">
							<div class="multi_headcolor ">
								<div style="float: right;" >
									<input type="checkbox" id="isQueryChild" name="isQueryChild" value="1" />
									是否包括子机构
								</div>
								<h3 class="panel-title-boostrap">机构列表</h3>
								
							</div>
							<div id="organList" class="select_tree_div"></div>
						</div>
					</td>
					<td style="width: 5px;"> &nbsp;</td>
					<td style="width: 65%;" valign="top">
						<div class="panel panel-default multi_headborder panel_min_height" style="margin-top: 0px;">
							<div class="multi_headcolor ">
								<h3 class="panel-title-boostrap">用户列表<span id="organName_text"></span></h3>
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
												<option name="userCode" value="用户账号">用户账号</option>
												<option name="userName" value="真实姓名">真实姓名</option>
											</select>
										</div>
										<div class="">
											<select class="form-control" style="display:none;">
												<option value="1" >包含</option>
												<option value="2" >等于</option>
											</select>
										</div>									
										<div class="">
											<input type="text" name="searchText" class="form-control searchText" placeholder="查询内容">
										</div>
										<div class="">	
											<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
												<i class="search_head_search"></i>
												<span class="search_head_span">查询</span>
											</a>
											<a href="javascript:export_user();"  class="btn search_head_searchBtn"  >
												<i class="search_head_export"></i>
												<span class="search_head_span">导出</span>
											</a>
										</div>	
									</div>
									<div style="clear:both;"></div>
								</form>
								</div>
							</div>
							<div id="opration_group" class="opration_group" style="display: none;">
								<security:authorize code="userM_add">
									<a href="javascript:add_user();"  id="addDialog" class="btn btn-sm search_a_add"  >
										<i class="search_head_add"></i>
										<span class="search_head_span">新建</span>
									</a>
								</security:authorize>
									<a href="javascript:check_user();" class="btn btn-sm search_a_check">
										<i class="search_head_check"></i>
										<span class="search_head_span">查看</span>
									</a>
								<security:authorize code="userM_edit">
									<a href="javascript:edit_user();"  class="btn btn-sm search_a_modify" >
										<i class="search_head_modify"></i>
										<span class="search_head_span">修改</span>
									</a>
								</security:authorize>
								<security:authorize code="userM_del">
									<a href="javascript:delete_user();" class="btn btn-sm search_a_delete" >
										<i class="search_head_delete"></i>
										<span class="search_head_span">删除</span>
									</a>
								</security:authorize>
								<security:authorize code="userM_reset">
						            <a href="javascript:reset();" class="btn btn-sm search_a_modify" >
							            <i class="search_head_modify"></i>
							            <span class="search_head_span">重置密码</span>
						            </a>		
					           </security:authorize>		
							</div>
							<div id="userList" class="select_tree_div"><div class="op_select_tip">请在左边机构列表中选择用户所属机构</div></div>
							
						</div>
					</td>
					<td style="width: 3px;"> &nbsp;</td>
				</tr>
			</table>
			
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
var selectOrganId = -1;//标识当前选中的角色ID

function add_user(){
	userPageGrid.createModal("新建用户","${ctx}/user/user_add?selectOrganId="+selectOrganId,480,800);
}
//编辑
function edit_user(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		userPageGrid.createModal("编辑用户","${ctx}/user/user_edit?userId="+selectedId,400,800);
	}
}
//查看
function check_user(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		userPageGrid.createModal("查看用户","${ctx}/user/user_check?userId="+selectedId,350,800);
	}
}
//分配角色
function assign_role(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		var userName = userPageGrid.getValByKeyAndField(selectedId,"userName");
		userPageGrid.createModal('分配角色【'+userName+'】',"${ctx}/user//asign_role?userId="+selectedId,420,800);
	}
}
//删除用户
function delete_user(){
	var ids = userPageGrid.getSelectedRowIds();
	if(ids.length>0){
		new comformDialog({title:"提示",content:"是否确认删除选中的记录？",confirm:function(){
			$.ajax({
		        url: "${ctx}/user/deleteUser",
		        datatype: 'json',
		        type: "Post",
		        data:{"userIds":ids},
		        success: function (data) {
		        	if(data==1){
		        		userPageGrid.pageRefresh();//刷新列表
		        	}else if(data==2){
		        		new alertdialog({title:"提示",content:"不可删除!"});
		        	}else{
		        		new alertdialog({title:"提示",content:"删除失败!"});
		        	}
		        }
			 });
		}});
	}else{
		new alertdialog({title:"提示",content:"请选中你需要操作的记录!"});
	}
}
//密码重置
function reset(){
	var selectedId = getSeletedId();
	if(selectedId && selectedId>0){
		new comformDialog({title:"提示",content:"是否确认重置选中用户的密码？",confirm:function(){
			$.ajax({
		        url: "${ctx}/user/updPassword",
		        datatype: 'json',
		        type: "Post",
		        data:{"userId":selectedId},
		        success: function (data) {
		        	if(data.length>0){
		        		userPageGrid.pageRefresh();//刷新列表
		        		new alertdialog({title:"提示",content:"密码重置成功，新密码为："+data});
		        	}else{
		        		new alertdialog({title:"提示",content:"重置密码失败 ！"});
		        	}
		        }
			 });
		}});
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

function getUserListByOrgan(organId){
	var isQueryChild = 0;
	if ($("#isQueryChild").get(0).checked) {
		isQueryChild = 1;
	}
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
		}, {
			field : "isLeader",
			title : "是否机构负责人",
			width : 120,
			exportFormatter:'isOrNotFormatter',
			formatter : function(value, rowData, rowIndex) {
				if(value==1){
					return "是";
				}else{
					return "否";
				}
			}
		}, {
			field : "duty",
			title : "职务",
			width : 100
		}, {
			field : "sex",
			title : "性别",
			width : 50,
			exportFormatter:'sexFormatter',
			formatter : function(value, rowData, rowIndex) {
				if(value==1){
					return "男";
				}else{
					return "女";
				}
			}
		}, {
			field : "userMobile",
			title : "联系电话",
			width : 100
		}] ;
	
	userPageGrid.setConfig({noShowNumPerPage:1});
	userPageGrid.paging("userList","userId",columns,'${ctx}/user/datasByPageOfOrga?organId='+organId + '&isQueryChild='+isQueryChild);
    
}
$(function() {
	$("#organList").tree({
		url : "${ctx}/org/getTreeData",
		checkbox : false,
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
		onClick : function(node) {//通过角色获取拥有的权限
			var organId = node.id;
			selectOrganId = organId;
			var organName = node.text;
			$("#organName_text").html('【'+organName+'】');
			$("#opration_group").show();
			getUserListByOrgan(organId);
		},
		onLoadSuccess : function() {

		},
		onLoadError : function(xhr, textStatus, error) {
		}
	});
	
	$("#queryBtn").bind("click",function(){
    	userPageGrid.pageQuery_2("searchForm"); 
    });
	$("#isQueryChild").bind("click",function(){
		if(selectOrganId==-1){
			return;
		}
		getUserListByOrgan(selectOrganId);
	});
	
});
function export_user(){
	var isQueryChild = 0;
	if ($("#isQueryChild").get(0).checked) {
		isQueryChild = 1;
	}
	userPageGrid.pageExport('${ctx}/user/datasByPageOfOrgaExport?organId='+selectOrganId 
			+ '&isQueryChild='+isQueryChild,"searchForm",'${ctx}/','用户信息'); 
}
</script>
</html>