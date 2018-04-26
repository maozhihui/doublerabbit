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
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label form-right col-xs-4 label-view-status">权限名称：</label>
										<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${priviliege.name }</label>
									</div>
								</div>
								<!--/span-->
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label form-right col-xs-4 label-view-status">权限编码：</label>
										<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${priviliege.code }</label>
									</div>
								</div>
								<!--/span-->
							</div>
							<div class="row" >
								<div class="col-xs-6" >
									<div class="form-group">
										<label class="control-label form-right col-xs-4 label-view-status">父级结点：</label>
										<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">
										     <c:choose>
										     	<c:when test="${empty priParent }">无父级结点</c:when>
										     	<c:otherwise>${priParent.name }</c:otherwise>
										     </c:choose>	
									    </label>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label form-right col-xs-4 label-view-status">排序：</label>
										<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${priviliege.position }</label>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-2 label-view-status">备注：</label>
										<label style="text-align:left" class="control-label col-xs-8 label-viewcontent-color">${priviliege.remark }</label>							
									</div>
								</div>
							</div> 
						</div>
						<div class="form-actions right"> 
							<div class="col-xs-offset-3 col-xs-9">
								<button type="button" onclick="_closeModal();" class="btn default">关闭</button>                              
							</div>
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