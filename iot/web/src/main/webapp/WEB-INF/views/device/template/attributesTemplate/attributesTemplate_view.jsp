<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	
	<link href="${ctx}/plugins/jquery-validation/jquery.validate.css" rel="stylesheet">
	<script type="text/javascript" src="${ctx}/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
	
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
		<div class="page-content">
				<div class="row inbox">
				 <div class="col-xs-12">
				<div class="panel panel-default">
				<div class=" form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-6 label-view-status">参数标识：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											${attributesTemplate.name}
										</label>
									</div>
									<div class="form-group">
										<label class="control-label form-right col-xs-6 label-view-status">参数数据类型：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											${attributesTemplate.valueType}
										</label>
									</div>
									<div class="form-group" id="isTelemetry">
										<label class="control-label form-right col-xs-6 label-view-status">参数值：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											${attributesTemplate.value}
										</label>
									</div>
									<div class="form-group" id="isread">
										<label class="control-label form-right col-xs-6 label-view-status">是否只读：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											<c:if test="${attributesTemplate.readOnly==0}">是</c:if>
											<c:if test="${attributesTemplate.readOnly==1}">否</c:if>
										</label>
									</div>
									<div class="form-group">
										<label class="control-label form-right col-xs-6 label-view-status">单位：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											${attributesTemplate.unit}
										</label>
									</div>
									<div class="form-group">
										<label class="control-label form-right col-xs-6 label-view-status">备注：</label>
										<label style="text-align:left" class="control-label col-xs-6 label-viewcontent-color">
											${attributesTemplate.description}
										</label>
									</div>
								</div>
							</div>
						</div>
						<div align="center" class="form_bottom">
							<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
var isTelemetry = getUrlParam("isTelemetry");
if(isTelemetry==1){
	$("#isTelemetry").remove();
	$("#isread").remove();
}
</script>
</body>
</html>