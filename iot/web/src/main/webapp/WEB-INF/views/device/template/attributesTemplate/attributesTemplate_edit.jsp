<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<style type="text/css">

</style>	
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
		<div class="page-content" align="center">
				<div class="panel panel-default">
				<div class=" form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="attributesTemplateForm" method="post">
						<div class="form-body">
							<!-- <div class="row"> -->
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>参数标识：</label>
										<div class="col-xs-8">
										    <input name="attributesTemplateId" id="attributesTemplateId" type="hidden"  value="${attributesTemplate.attributesTemplateId}" />
											<input name="deviceTemplateId" id="deviceTemplateId" type="hidden"  value="" />
											<input name="name" id="name" class="form-control"  value="${attributesTemplate.name}"  />
											<span class="help-block"></span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>参数数据类型：</label>
										<div class="col-xs-8">
											<select name="valueType" id="valueType" class="form-control">
												<option value="string" <c:if test="${attributesTemplate.valueType=='string'}">selected="selected"</c:if>>string</option>
												<option value="int" <c:if test="${attributesTemplate.valueType=='int'}">selected="selected"</c:if>>int</option>
												<option value="float" <c:if test="${attributesTemplate.valueType=='float'}">selected="selected"</c:if>>float</option>
											</select>
											<span class="help-block"></span>
										</div>
									</div>
									
								<!--  	<div class="form-group" id="isTelemetry">
										<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>参数值：</label>
										<div class="col-xs-8">
											<input name="value" id="value" class="form-control"  value=""  maxlength=100 />
											<span class="help-block"></span>
										</div>
									</div>-->
									
									<div class="form-group" id="isread">
										<label class="control-label col-xs-3">是否只读：</label>
										<div class="col-xs-8">
											<select name="readOnly" id="readOnly" class="form-control">
												<option value="1" <c:if test="${attributesTemplate.readOnly==1}">selected="selected"</c:if>>否</option>
												<option value="0" <c:if test="${attributesTemplate.readOnly==0}">selected="selected"</c:if>>是</option>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-xs-3">单位：</label>
										<div class="col-xs-8">
											<input name="unit" id="unit" class="form-control"  value="${attributesTemplate.unit}"  maxlength=100 />
											<span class="help-block"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-xs-3">描述：</label>
										<div class="col-xs-8">
											<input name="description" id="description" class="form-control"  value="${attributesTemplate.description}"  maxlength=100 />
											<span class="help-block"></span>
										</div>
									</div>
								<!--  </div>-->
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_attributesTemplate"  class="btn blue btn_confirm">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var attributesTemplateId = '${attributesTemplate.attributesTemplateId}';
	var isTelemetry = getUrlParam("isTelemetry");
	var deviceTemplateId = getUrlParam("deviceTemplateId");
	$(document).ready(function(){
		var validateNameUrl;
		$("#deviceTemplateId").val(deviceTemplateId);
		var formRules = {
			  name: {
                  required: true,
                  /*stringCheck: true,*/
                  maxlength: 30,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/attributesTemplate/validateAttributeTemplateName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  deviceTemplateId:function(){return $("#deviceTemplateId").val();} ,
		            	  attributeTemplateId:function(){return $("#attributesTemplateId").val();},
		            	  isTelemetry:isTelemetry
		              },  
		              dataFilter: function(data, type) {  
		            	data = JSON.parse(data);
		                if (data.flag == "success")  
		                    return true;  
		                else {
		                    return false;
			            }  
		             }  
		          }  
              },
              valueType:{
            	  required: true,
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空",
         	 	remote:"参数名称已存在"
         	 },
			valueType:"参数数据类型不能为空"
         };
		if(isTelemetry==1){
			//$("#isTelemetry").remove();
			$("#isread").remove();
		} else {
			formRules.value={required: true};
			formValidateMessages.value={required: "参数值不能为空"};
		}
		
		$("#save_attributesTemplate").bind("click",function(){
			if($("#attributesTemplateForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				var saveUrl = "";
				if(attributesTemplateId){
					saveUrl = "${ctx}/attributesTemplate/updateAttributesTemplate";
				}else {
					saveUrl = "${ctx}/attributesTemplate/addAttributesTemplate";
				}
				var datas = {};
				var formData = $("#attributesTemplateForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				datas.isTelemetry = isTelemetry;
				 $.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:datas,
			        success: function (data) {
			        	if(data.flag=='success'){
			        		_closeModal(true);
				    	}else{
			        		new alertdialog({title:"提示",content:"保存失败!",confirmFun:function(){
					    		$(".btn_confirm").removeAttr("disabled");
							}});
			        	}
			        }
				 });
			}
		});
		validateForm("attributesTemplateForm",formRules,formValidateMessages);
	});
	

</script>
</body>
</html>