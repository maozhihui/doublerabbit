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
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-5 label-view-status">产品名称：</label>
										<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${product.name }
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-5 label-view-status">所属类型：</label>
										<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${product.categoryName }  
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-5 label-view-status">产品型号：</label>
										<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${product.model}
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-5 label-view-status">接入方式：</label>
										<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${product.accessProtocol }
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-5 label-view-status">产品简介：</label>
										<label style="text-align:left" class="control-label col-xs-7 label-viewcontent-color">
											${product.brief }
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
	</div>
</div>
</body>
</html>