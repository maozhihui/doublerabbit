<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<script src="${ctx}/plugins/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
<style type="text/css">
.panel-title{
    line-height: 30px;
    background: #fff;
    margin-left: 30px;
    margin-bottom: 15px;
    display: block;
    color: #3a6fd7;
    font-size: 16px;
    font-weight: bold;
}
</style>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content" align="center" style="padding-top: 10px;">
		<div class="panel panel-default">
			<div class=" form">
				<!-- BEGIN FORM-->
				<form action="#" class="form-horizontal" id="ruleForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group">
									<label class="control-label form-right col-xs-5 label-view-status">规则名称：</label>
									<label style="text-align:left" class="control-label col-xs-5 label-viewcontent-color">
											${rule.name }
									</label>
								</div>
								<div class="form-group">
									<label class="control-label form-right col-xs-5 label-view-status">控制范围：</label>
									<label style="text-align:left" class="control-label col-xs-5 label-viewcontent-color" id="deviceName">
											
									</label>
								</div>
								<div class="form-group">
									<label class="control-label form-right col-xs-5 label-view-status">属性条件：</label>
									<label style="text-align:left" class="control-label col-xs-5 label-viewcontent-color" id="attribureAction">
											
									</label>
								</div>
								<div class="form-group">
									<label class="control-label form-right col-xs-5 label-view-status">触发动作：</label>
									<label style="text-align:left" class="control-label col-xs-5 label-viewcontent-color">
											短信
									</label>
								</div>
								<div class="form-group">
									<label class="control-label form-right col-xs-5 label-view-status">配置：</label>
									<label style="text-align:left" class="control-label col-xs-5 label-viewcontent-color" id="msContent">
											
									</label>
								</div>
							</div>	
						</div>
					</div>
					<div align="center">
						<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//返回
function returnRules(){
	window.location.href="${ctx}/ruleTenant/list";
}
var rule = '${rule.filters}';
var returnAction = '${rule.action}';
var deviceName = JSON.parse(rule)[0].configuration.deviceName;
var attributeName = JSON.parse(rule)[1].configuration.attributeName;
var typeSelect = JSON.parse(rule)[1].configuration.operator;
var threshold = JSON.parse(rule)[1].configuration.value;
var msContent = JSON.parse(returnAction).configuration.content;
$("#deviceName").text(deviceName);
$("#attribureAction").text(attributeName+typeSelect+threshold);
$("#msContent").text(msContent);
</script>
</body>
</html>