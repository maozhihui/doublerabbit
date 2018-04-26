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
				<form action="#" class="form-horizontal" id="tenantForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group">
									<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
									<div class="col-xs-7">
										<input name="tenantId" id="tenantId" type="hidden"  value="${tenant.tenantId }" />
										<input name="name" id="name" class="form-control"  value="${tenant.name }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group">
									<label class="control-label col-xs-3">备注：</label>
									<div class="col-xs-7">
										<input name="additionalInfo" id="additionalInfo" class="form-control"  value="${tenant.additionalInfo }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
					</div>
					<div align="center" class="form_bottom">
						<button type="button" id="save_tenant"  class="btn blue  btn_confirm">提交</button>
						<button type="button" onclick="_closeModal();" class="btn default  btn_cancel">关闭</button>                              
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	
<script>
	var tenantId = '${tenant.tenantId}';
	$(document).ready(function(){
		var formRules = {
			  name: {
                  required: true,
                  /*stringCheck: true,*/
                  maxlength: 30,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/tenant/validateTenantName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  tenantId:function(){return $("#tenantId").val();}  
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
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空",
         	 	remote:"租户名称已存在"
         	 }
         };
		
		$("#save_tenant").bind("click",function(){
			if($("#tenantForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				var saveUrl = "";
				if(tenantId){
					saveUrl = "${ctx}/tenant/updateTenant";
				}else {
					saveUrl = "${ctx}/tenant/addTenant";
				}
				var datas = {};
				var formData = $("#tenantForm").serializeArray();
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
			        		new alertdialog({title:"提示",content:"保存失败!",confirmFun:function(){
					    		$(".btn_confirm").removeAttr("disabled");
							}});
			        	}
			        }
				 });
			}
		});
		validateForm("tenantForm",formRules,formValidateMessages);
	});
	

</script>
</body>
</html>