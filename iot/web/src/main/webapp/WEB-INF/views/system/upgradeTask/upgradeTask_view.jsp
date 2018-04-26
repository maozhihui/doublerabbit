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
												<label class="control-label form-right col-xs-4 label-view-status">任务名称：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.name }</label>
											</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">版本id：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.versionId }</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">任务状态：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.status }</label>
											</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">升级设备总数：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.deviceNum }</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">升级成功数：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.successNum }</label>
											</div>
										</div>
										<div class="col-xs-6">
											<div class="form-group">
												<label class="control-label form-right col-xs-4 label-view-status">描述：</label>
												<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${updateTask.taskDesc }</label>
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