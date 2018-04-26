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
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content" align="center">
		<div class="panel panel-default">
			<div class="form normalForm" style="padding:0px 20px;">
			<!-- BEGIN FORM-->
				<form action="#" class="form-horizontal" id="deviceForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">设备名称：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${device.name}
									</label>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">当前状态：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										<c:if test="${device.status==0}">离线</c:if>
										<c:if test="${device.status==1}">在线</c:if>	
									</label>
								</div>
							</div>	
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">硬件标识：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										${device.hardIdentity }
									</label>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">参数模板 ：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										${device.deviceTemplateName }
									</label>
								</div>
							</div>
						
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">设备编号：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										${device.sn }
									</label>
								</div>
							</div>
							
						
							<!--  <div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">是否网关：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										<c:if test="${device.isGateWay==0}">否</c:if>
										<c:if test="${device.isGateWay==1}">是</c:if>
									</label>				
								</div>	
							</div>	-->	
						
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">设备描述：</label>
									<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
										${device.description }
									</label>
								</div>
							</div>
						</div>
					</div>
					
					<div align="center" style="margin-top:20px;">
						<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	
<script>


</script>
</body>
</html>