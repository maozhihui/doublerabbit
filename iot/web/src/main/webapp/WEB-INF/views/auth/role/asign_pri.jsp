<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
	<script type="text/javascript" src="${ctx}/plugins/easyui/jquery.easyui.min.js"></script>

</head>
<body class="page-header-fixed"> 
 <div class="page-container" id="dgformdiv">
	<div class="page-content">
		<div class="row inbox">
			<div class="panel panel-default">
				<div class=" form">
					<div style="margin: 0px;"></div>
					<div style="min-height: 315px;height:80%;">
					    <div id="priList"></div>
					</div>
				</div>
				<div class="form-actions right"> 
					<div class="col-xs-offset-3 col-xs-9">
						<button type="button" id="save"  class="btn green">提交</button>
						<button type="button" onclick="_closeModal();" class="btn default">关闭</button>                              
					</div>
				</div>
			</div>
		</div>
	</div>
 </div>
	
</body>
<script>
$(function() {
	var roleId = '${roleId}';
	$("#priList").tree({
		url : "${ctx}/pri/getTreeDataByRoleChecked?expand=1&roleId="+roleId,
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
		onLoadSuccess : function() {

		},
		onLoadError : function(xhr, textStatus, error) {
		}
	});
	
	$("#save").bind("click",function(){
		var nodes = $('#priList').tree('getChecked');
		var priIds = [];
		for(var i=0; i<nodes.length; i++){
			priIds.push(nodes[i].id);
		}
		$.ajax({
	        url: "${ctx}/role/save_role_pri",
	        datatype: 'json',
	        type: "Post",
	        data:{roleId:roleId,priIds:priIds},
	        success: function (data) {
	        	if(data==1){
	        		_closeModal(true);
	        	}else{
	        		new alertdialog({title:"提示",content:"保存失败!"});
	        	}
	        }
		});
	});
	
});

</script>

</html>
