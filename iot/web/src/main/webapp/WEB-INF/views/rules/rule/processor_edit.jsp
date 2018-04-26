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
					<form action="#" class="form-horizontal" id="processForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
										<div class="col-xs-9">
											<input name="name" id="name" class="form-control"  value=""  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>处理器类型：</label>
										<div class="col-xs-9">
											<select name="processType" id="processType" class="form-control" >
											<!--  	<c:forEach var="item" items="${processTypes }">
													<option value="${item.key}" >${item.value}</option>
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
										<label class="control-label col-xs-2">配置：</label>
										<div class="col-xs-9">
											<textarea name="additionalInfo" id="additionalInfo" class="form-control" rows="5" cols="10"></textarea>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_process"  class="btn blue btn_confirm">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var processId = '${processId}';
	var processTypes= '${processTypes }';
	$(document).ready(function(){
		initProcessTypes();
		initProcess(processId);
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
		
		$("#save_process").bind("click",function(){
			if($("#processForm").valid()){
				var datas = {};
				var formData = $("#processForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				datas.processTypeText = $("#processType").find("option:selected").text(); 
				if(!processId){
					processId = new Date().getTime();
				}
				datas.id = processId;
				/*if(window.parent.addProcessDatas){
					window.parent.addProcessDatas(datas);
				}*/
				if(window.parent.editProcessDatas){
					window.parent.editProcessDatas(datas);
				}
				_closeModal();
			}
		});
		validateForm("processForm",formRules,formValidateMessages);
	});
	function initProcess(id){
		if(id && window.parent.getProcess){
			var initData = window.parent.getProcess(id);
			if(initData){
				$("#name").val(initData.name);
				$("#additionalInfo").val(initData.additionalInfo);
				$("#processType").val(initData.processType);
			}
		}
	}
	function initProcessTypes(){
		var str="";
		processTypes = processTypes.replace(/\{/,"").replace(/\}/,"");
		var array = processTypes.split(",");
		for(var i=0;i< array.length;i++){
			var thisProcess = array[i].split("=");
			str += '<option value="'+thisProcess[0].trim()+'" >'+thisProcess[1]+'</option>';
		}
		$("#processType").append(str);
	}
</script>
</body>
</html>