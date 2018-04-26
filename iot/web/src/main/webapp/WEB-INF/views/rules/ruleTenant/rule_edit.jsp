<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<script src="${ctx}/plugins/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
<style type="text/css">
.panel-title{
    line-height: 30px;
    background: #fff;
    margin-left: 30px;
    margin-bottom: 15px;
    display: block;
    color: #3a6fd7;
    font-size: 16px;
    font-weight: bold;
}
</style>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content" align="center" style="padding-top: 10px;">
		
		<div class="panel panel-default">
			<div class=" form">
			<!-- BEGIN FORM-->
				<form action="#" class="form-horizontal" id="ruleForm" method="post" style="margin-left:55px;">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group">
									<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>规则名称：</label>
									<div class="col-xs-8">
										<input name="ruleId" id="ruleId" type="hidden"  value="${rule.ruleId }" />
										<input name="productId" id="product_select" type="hidden"  value="${CUR_PRODUCT_ID}" />
										<input name="name" id="name" class="form-control"  value="${rule.name }"  />
										<span class="help-block"></span>
									</div>
								</div>					
								<div class="form-group">
									<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>控制范围：</label>
									<div class="col-xs-8">
										<select  id="device_select" name="deviceId" class="form-control">
										</select>
										<span class="help-block"></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>属性条件：</label>
									<div class="col-xs-8">
										<div class="form-group" style="margin-bottom:0px">
											<div class="col-xs-4 form-group" style="margin:0;padding: 0 0 0 15px;">
												<select  id="attribute_select" name="attributeId" class="form-control">
												</select>
											</div>
											<div class=" col-xs-4">
												<select name="type_select" class="form-control" id="type_select">
													<option value=">">&gt;</option>
							                      	<option value=">=">&gt;=</option>
							                        <option value="<">&lt;</option>
							                        <option value="<=">&lt;=</option>
							                        <option value="==">==</option>
												</select>
											</div>
											<div class="col-xs-4 form-group" style="margin:0;padding: 0 15px 0 0;">
												<input class="form-control" id="threshold" name="threshold" placeholder="数据值" value="" type="text">
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
	                				<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>触发动作：</label>
									<div class="col-xs-8">
										<select name="trigger_Action" class="form-control" id="trigger_Action">
											<option value="8a8aeb955ce3b7ec015ce3ba50290001">短信</option>
										</select>
	                				</div>
	                			</div>
								<div class="form-group">
									<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>配置：</label>
									<div class="col-xs-8">
										<textarea name="msContent" id="msContent" class="form-control" rows="4">超过阀值，平台发送。</textarea>
										<span class="help-block"></span>
									</div>
								</div>
							</div>	
						</div>
						
						<div align="center">
							<button type="button" id="save_rule"  class="btn blue  btn_confirm">提交</button>
							<button type="button" class="btn blue  btn_cancel" onclick="_closeModal();">关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	
<script>
	var ruleId = '${rule.ruleId}';
	var returnFilters = '${rule.filters}';
	var returnAction = '${rule.action}';
	if(returnFilters){
		returnFilters = JSON.parse('${rule.filters}');
	}
	if(returnAction){
		returnAction = JSON.parse('${rule.action}');
	}
	
	$(document).ready(function(){
		initDevice();
		var formRules = {
			  name: {
                  required: true,
                /*  stringCheck: true,*/
                  maxlength: 30,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/ruleTenant/validateRuleName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  ruleId:function(){return $("#ruleId").val();}  
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
              deviceId:{
            	  required: true
              },
              attributeId:{
            	  required: true
              },
              threshold:{
            	  required: true
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空",
         	 	remote:"规则名称已存在"
         	 },
             deviceId:{
           	  	required: "设备不能为空"
             },
         	 attributeId:{
          	  	required: "属性值不能为空"
            	},
         	 threshold:{
          	  	required: "数据值不能为空"
             }
         };
		function initDevice(){
			var productId = $("#product_select").val();
			if(productId !=""){
				var items_d;
				$.ajax({
					url: "${ctx}/device/queryByProductId?productId="+productId,
					datatype: 'json',
					type: "GET",
					async:false,
					data:{"pageSize":9999},
					success: function (data) {
						items_d = data.result;
					}
				});
				
				var str_d="" ;
				for(var i=0;i<items_d.length;i++){
					str_d+='<option value="'+items_d[i].devId+'">'+items_d[i].name+'</option>';
				}
				$("#device_select").empty();
				//$("#device_select").append('<option value="">请选择</option>');
				$("#device_select").append(str_d);
				if(returnFilters){
					var selectedId = returnFilters[0].configuration.deviceId;
					$("#device_select").val(selectedId);
				}
				initAttribute();
				if(returnFilters){
					var selectedId = returnFilters[1].configuration.attributeId;
					$("#attribute_select").val(selectedId);
					$("#type_select").val(returnFilters[1].configuration.operator);
					$("#threshold").val(returnFilters[1].configuration.value);
					$("#msContent").text(returnAction.configuration.content);
				} else {
					$("#threshold").keyup(function(){
						$("#msContent").html("超过阀值"+$(this).val()+"，平台发送。");
					})
				}
			} else {
				$("#device_select").empty();
				$("#device_select").append('<option value="">请选择</option>');
			}
		}
		function initAttribute(){
			var devId = $("#device_select").val();
			if(devId != ""){
				var items_a;
				$.ajax({
					url: "${ctx}/telemetryAttributes/datasByPage?devId="+devId,
					datatype: 'json',
					type: "GET",
					async:false,
					data:{"pageSize":9999},
					success: function (data) {
						items_a = data.result;
					}
				});
				var str_a="" ;
				for(var i=0;i<items_a.length;i++){
					str_a+='<option value="'+items_a[i].telemetryAttributeId+'">'+items_a[i].attributeName+'</option>';
				}
				$("#attribute_select").empty();
				//$("#attribute_select").append('<option value="">请选择</option>');
				$("#attribute_select").append(str_a);
			} else {
				$("#attribute_select").empty();
				$("#attribute_select").append('<option value="">请选择</option>');
			}
		}
		/*function initProdcut(){
			var items;
			$.ajax({
				url: "${ctx}/product/datasByPage",
				datatype: 'json',
				type: "GET",
				async:false,
				success: function (data) {
				    items = data.result;
				}
			});
			var str="" ;
			for(var i=0;i<items.length;i++){
				var selectedId = "";
				if(returnFilters){
					selectedId = returnFilters[0].configuration.productId;
				}
				var selected="";
				if (items[i].productId == selectedId){
					selected="selected";
				} else {
					selected="";
				}
				str+='<option value="'+items[i].productId+'" '+selected+'>'+items[i].name+'</option>';
			}
			$("#product_select").empty();
			//$("#product_select").append('<option value="">请选择</option>');
			$("#product_select").append(str);
			initDevice();
			if(returnFilters){
				var selectedId = returnFilters[0].configuration.deviceId;
				$("#device_select").val(selectedId);
			}
			initAttribute();
			if(returnFilters){
				var selectedId = returnFilters[1].configuration.attributeId;
				$("#attribute_select").val(selectedId);
				$("#type_select").val(returnFilters[1].configuration.operator);
				$("#threshold").val(returnFilters[1].configuration.value);
				$("#msContent").text(returnAction.configuration.content);
			}
		}
		$("#product_select").change(function(){
			initDevice();
			initAttribute();
		})*/
		$("#device_select").change(function(){
			initAttribute();
		})
		$("#save_rule").bind("click",function(){
			if($("#ruleForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				//$("#pluginId").val($('#pluginId').combotree('getValue'));
				var saveUrl = "";
				if(ruleId){
					saveUrl = "${ctx}/ruleTenant/updateRule";
				}else {
					saveUrl = "${ctx}/ruleTenant/addRule";
				}
				var datas = {};

				datas.ruleId= $("#ruleId").val();
				datas.name= $("#name").val();
				datas.pluginId = $("#trigger_Action").val();
				datas.productId = $("#product_select").val();
				var filterA = [];
				var filter_1 = {};
				var filter_2 = {};
				var configuration_1 = {};
				var configuration_2 = {};
				configuration_1.productId = $("#product_select").val();
				configuration_1.deviceId = $("#device_select").val();
				configuration_1.deviceName = $("#device_select option:selected").text();
				filter_1.configuration = configuration_1;
				filter_1.clazz = "com.comba.server.extensions.core.filter.MyDeviceFilter";
				filter_1.name = "MyDeviceFilter";
                configuration_2.attributeId = $("#attribute_select").val();
                configuration_2.attributeName = $("#attribute_select option:selected").text();
                configuration_2.operator = $("#type_select").val();
                configuration_2.value = $("#threshold").val();
                filter_2.configuration = configuration_2;
                filter_2.clazz = "com.comba.server.extensions.core.filter.MyDeviceTelemetryFilter";
                filter_2.name = "MyDeviceFilter";
				filterA.push(filter_1);
				filterA.push(filter_2);
				datas.filters = filterA;
				datas.processor = null;
				var action = {};
				var configuration = {};
				configuration.dstNum = "";
				configuration.content = $("#msContent").val();
				action.configuration = configuration;
				action.clazz = "com.comba.server.extensions.core.action.sms.SendSmsAction";
				action.name = "SendSmsAction";
				datas.action = action;
				var additionalInfo = {};
				additionalInfo.description = "xxxx";
				datas.additionalInfo = additionalInfo;
				$.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:{datas:JSON.stringify(datas)},
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
		validateForm("ruleForm",formRules,formValidateMessages);
	});
	
	//返回
	/*function returnRules(){
		window.location.href="${ctx}/ruleTenant/list";
	}*/
	
	function hide_modal(tarId){
		if(tarId && $("#"+tarId)){
			$('#'+tarId).modal('hide');
			$("#"+tarId).empty();
		}
	}
</script>
</body>
</html>