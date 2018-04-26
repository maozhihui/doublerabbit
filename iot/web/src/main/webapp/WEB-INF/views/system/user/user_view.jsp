<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
									<div class="col-xs-5 col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">登录名：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.account }</label>
										</div>
									</div>
									<!--/span-->
									<div class="col-xs-5">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">用户姓名：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.userName }</label>
										</div>
									</div>
									<!--/span-->
								</div>
								<div class="row">
									<div class="col-xs-5 col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">联系电话：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.msisdn }</label>		
										</div>
									</div>
									<!--/span-->
								<!--  <div class="col-xs-5">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">租户：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.tenantName }</label>		
										</div>
									</div>-->	
									<div class="col-xs-5">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">邮箱：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.email }</label>	
										</div>
									</div>
								</div>
								
								<div class="row">
									
									<!--/span-->
									<div class="col-xs-5  col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">详细地址：</label>
											<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${user.detailAddress }</label>										
										</div>
									</div>
								</div>
								
								
							</div>
							<div align="center" style="margin-top:20px">
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