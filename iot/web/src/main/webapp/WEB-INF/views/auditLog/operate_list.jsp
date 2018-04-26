<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
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
				<h3 class="panel-title">操作日志</h3>
			</div>
			<div class="list_search_head">
				<div id="collapse-group" class="">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="">
						 <form id="searchForm" name="searchForm" action="${ctx }/audit/operate_list" method="post" class="form-horizontal" >
							<div class="list_search_head_left" style="left:10px;">
								<div class="search_area">
								
								</div>
								<div>
									<label class="search_head control-label">查询条件：</label>
								</div>
								<div class="">
									<select class="form-control selectC" name="selectC">
										<option name="description" value="操作">操作</option>
										<option name="createBy" value="操作人">操作人</option>
										<option name="requestIp" value="操作 IP">操作 IP</option>		
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
								<div>
									<i class="fa fa-plus-circle" style="color: #4C99E4;cursor: pointer;height: 30px;width: 15px;line-height: 30px;" onclick="conditionQuery.addDiv();"></i>
								</div>
								<div class="">	
									<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
										<i class="search_head_search"></i>
										<span class="search_head_span">查询</span>
									</a>
									<a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
										<i class="search_head_search"></i>
										<span class="search_head_span">导出</span>
									</a>
								</div>
							</div>
						</form>
						</div>
					 </div>		    
			    </div>
			</div>
			<div id="list"></div>
		</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
</div>
</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var pageGrid = new PageGrid({});
$(function() {
	var columns = [{
		field : "description",
		title : "操作",
		width : 200
		}, {
			field : "createBy",
			title : "操作人",
			width : 120
		}, {
			field : "createDate",
			title : "操作时间",
			width : 80,
			formatter : function(value, rowData, rowIndex) {
				return pageGrid.formatDate(value);
			}
		}, {
			field : "requestIp",
			title : "操作IP",
			width : 150
		} ] ;
	pageGrid.setConfig({isShowCheckbox:1});
	pageGrid.paging("list","id",columns,'${ctx}/audit/datasByPage?type=0');
	$("#queryBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		pageGrid.pageQuery_2("searchForm",conditionQuery.getSearchArr());
    		//search_arr = [];
    		//$(".add-search-div").remove();
    	}else{
    		pageGrid.pageQuery_2("searchForm"); 
    	}
    });
    $("#exportBtn").click(function(){
    	if($(".add-search-div").size()!=0){
    		pageGrid.pageExport('${ctx}/audit/datasByExport?type=0',"searchForm",'${ctx}/','用户操作日志',conditionQuery.getSearchArr());
    	}else{
    		pageGrid.pageExport('${ctx}/audit/datasByExport?type=0',"searchForm",'${ctx}/','用户操作日志'); 
    	}
    });
    
});


</script>
</html>