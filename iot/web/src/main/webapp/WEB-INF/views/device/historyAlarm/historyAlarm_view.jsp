<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
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
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">租户id：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${historyAlarm.tenantId }</label>
											</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">产品Id ：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${historyAlarm.productId }</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">设备id：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${historyAlarm.devId }</label>
											</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">告警id：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${historyAlarm.alarmId }</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">告警内容：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${historyAlarm.alarmContent }</label>
											</div>
										</div>
									</div>
							<div align="center" style="margin-top:20px;clear: both;">
								<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>