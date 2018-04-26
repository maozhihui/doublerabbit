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
					<form action="#" class="form-horizontal" id="pluginForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
										<div class="col-xs-9">
										    <input name="pluginId" id="pluginId" type="hidden"  value="${plugin.pluginId }" />
											<input name="name" id="name" class="form-control"  value="${plugin.name }"  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>	
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>插件类型：</label>
										<div class="col-xs-9">
											<select name="clazz" id="clazz" class="form-control" >
											<!--  	<c:forEach var="item" items="${pluginTypes }">
													<option value="${item.key}" <c:if test="${plugin.clazz==item.key}">selected="selected"</c:if>>${item.value}</option>
												</c:forEach>-->
										   </select>
											<span class="help-block"></span>
										</div>
									</div>	
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>插件配置：</label>
										<div class="col-xs-9">
											<div class="row">
												<div class="col-xs-12">
													<div class="form-group">
														<label class="control-label col-xs-2">手机号码：</label>
														<div class="col-xs-10">
															<input name="configuration" id="configuration" class="form-control"  value="${plugin.configuration }"  maxlength=100 />
															<span class="help-block"></span>
														</div>
													</div>
												</div>
											</div>	
										</div>
									</div>
								</div>
							</div>	
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2">备注：</label>
										<div class="col-xs-9">
											<input name="additionalinfo" id="additionalinfo" class="form-control"  value="${plugin.additionalinfo }"  maxlength=100 />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_plugin"  class="btn blue btn_confirm">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var pluginId = '${plugin.pluginId}';
	var pluginTypes = '${pluginTypes }';
	$(document).ready(function(){
		initPluginTypes();
		if(pluginId){
			$("#clazz").val('${plugin.clazz}');
		}
		var formRules = {
			  name: {
                  required: true,
                  stringCheck: true,
                  maxlength: 30
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空"
         	 }
         };
		
		$("#save_plugin").bind("click",function(){
			if($("#pluginForm").valid()){
				var saveUrl = "";
				if(pluginId){
					saveUrl = "${ctx}/plugin/updatePlugin";
				}else {
					saveUrl = "${ctx}/plugin/addPlugin";
				}
				var datas = {};
				var formData = $("#pluginForm").serializeArray();
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
		validateForm("pluginForm",formRules,formValidateMessages);
	});
	function initPluginTypes(){
		var str="";
		pluginTypes = pluginTypes.replace(/\{/,"").replace(/\}/,"");
		var array = pluginTypes.split(",");
		for(var i=0;i< array.length;i++){
			var thisPlugin = array[i].split("=");
			str += '<option value="'+thisPlugin[0].trim()+'" >'+thisPlugin[1]+'</option>';
		}
		$("#clazz").append(str);
	}

</script>
</body>
</html>