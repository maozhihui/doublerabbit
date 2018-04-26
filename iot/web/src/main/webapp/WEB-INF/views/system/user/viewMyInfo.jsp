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
						<form action="#" class="form-horizontal" id="userInfo">
							<div class="form-body">
								<div class="row">
									<div class="col-xs-5 col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">登录名：</label>
											<label name="account" style="text-align:left" class="control-label col-xs-8 label-viewcontent-color"></label>
										</div>
									</div>
									<!--/span-->
									<div class="col-xs-5">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">用户姓名：</label>
											<label name="userName" style="text-align:left" class="control-label col-xs-8 label-viewcontent-color"></label>
										</div>
									</div>
									<!--/span-->
								
									<div class="col-xs-5 col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">联系电话：</label>
											<label name="msisdn" style="text-align:left" class="control-label col-xs-8 label-viewcontent-color"></label>		
										</div>
									</div>
									<div class="col-xs-5  ">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">邮箱：</label>
											<label name="email" style="text-align:left" class="control-label col-xs-8 label-viewcontent-color"></label>	
										</div>
									</div>
									<!--/span-->
									<div class="col-xs-5  col-xs-offset-1">
										<div class="form-group">
											<label class="control-label form-right col-xs-4 label-view-status">详细地址：</label>
											<label name="detailAddress" style="text-align:left" class="control-label col-xs-8 label-viewcontent-color"></label>										
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
<script type="text/javascript">
$.ajax({
    url: "${ctx}/user/currentUser",
    datatype: 'json',
    type: "GET",
    success: function (data) {
    	$("#userInfo label.label-viewcontent-color").each(function(){
			$this = $(this);
			$.each(data, function(key, val) {
				if($this.attr("name")==key){
					if(val!=null){
						$this.text(val);
					} else {
						$this.text("");
					}
				}
			});
		})
    }
 });
</script>
</body>
</html>