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
		<div class="page-content" align="center">
				<div class="panel panel-default">
				<div class=" form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="telemetryAttributesForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
										<div class="col-xs-10">
										    <input name="telemetryAttributeId" id="telemetryAttributeId" type="hidden"  value="${telemetryAttributes.telemetryAttributeId }" />
											<input name="name" id="name" class="form-control"  value="${telemetryAttributes.name }"  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2">备注：</label>
										<div class="col-xs-10">
											<input name="additionalInfo" id="additionalInfo" class="form-control"  value="${telemetryAttributes.additionalInfo }"  maxlength=100 />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_telemetryAttributes"  class="btn blue">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default">关闭</button>                              
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var telemetryAttributeId = '${telemetryAttributes.telemetryAttributeId}';
	$(document).ready(function(){
		var formRules = {
			  name: {
                  required: true,
                  /*stringCheck: true,*/
                  maxlength: 30
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空"
         	 }
         };
		
		$("#save_telemetryAttributes").bind("click",function(){
			if($("#telemetryAttributesForm").valid()){
				var saveUrl = "";
				if(telemetryAttributeId){
					saveUrl = "${ctx}/telemetryAttributes/updateTelemetryAttributes";
				}else {
					saveUrl = "${ctx}/telemetryAttributes/addTelemetryAttributes";
				}
				var datas = {};
				var formData = $("#telemetryAttributesForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				 $.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:datas,
			        success: function (data) {
			        	if(data.flag=='success'){
			        		_closeModal(true);
				    	}else{
			        		new alertdialog({title:"提示",content:"保存失败!"});
			        	}
			        }
				 });
			}
		});
		validateForm("telemetryAttributesForm",formRules,formValidateMessages);
	});
	

</script>
</body>
</html>