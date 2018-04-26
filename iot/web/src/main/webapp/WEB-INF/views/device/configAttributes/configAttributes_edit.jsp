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
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content" align="center">
		<div class="panel panel-default">
			<div class=" form">
				<!-- BEGIN FORM-->
				<form action="#" class="form-horizontal" id="configAttributesForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
									<div class="col-xs-8">
										<input name="devId" id="devId" type="hidden"  value="${configAttributes.devId }" />
										<input name="name" id="name" class="form-control"  value="${configAttributes.name }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>	
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>mac：</label>
									<div class="col-xs-8">
										<input name="mac" id="mac" class="form-control"  value="${configAttributes.additionalInfo }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>设备配置属性编号：</label>
									<div class="col-xs-8">
										<input name="sn" id="sn" class="form-control"  value="${configAttributes.sn }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">设备配置属性标识：</label>
									<div class="col-xs-8">
										<input name="configAttributesId" id="configAttributesId" class="form-control"  value="${configAttributes.configAttributesId }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
									
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">鉴权用户名：</label>
									<div class="col-xs-8">
										<input name="userName" id="userName" class="form-control"  value="${configAttributes.userName }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>			
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">鉴权密钥：</label>
									<div class="col-xs-8">
										<input name="secretKey" id="secretKey" class="form-control"  value="${configAttributes.secretKey }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">是否网关：</label>
									<div class="col-xs-8">
										<select name="isGateWay" id="isGateWay" class="form-control" >
											<option value="0" <c:if test="${configAttributes.isGateWay==0}">selected="selected"</c:if>>否</option>
											<option value="1"   <c:if test="${configAttributes.isGateWay==1}">selected="selected"</c:if> >是</option>
										</select>
										<span class="help-block"></span>
									</div>
								</div>	
							</div>		
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">所属设备配置属性类别 ：</label>
									<div class="col-xs-8">
										<input id="categoryId" name="categoryId"/>
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
										<input name="description" id="description" class="form-control"  value="${configAttributes.description }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div align="center">
						<button type="button" id="save_configAttributes"  class="btn blue">提交</button>
						<button type="button" onclick="_closeModal();" class="btn default">关闭</button>                              
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	
	<script>
	var configAttributesId = '${configAttributes.devId}';
	var categoryId = '${configAttributes.categoryId }';
	$(document).ready(function(){
		
		$('#categoryId').combotree({  
            url: "${ctx}/category/getTreeData",  
            required: false,
            method : "get",
            cascadeCheck:false,
            width : 225,
            panelHeight : 150,
            editable : false,
            onSelect:function(node){
            	if(node.id==categoryId){
            		new alertdialog({title:"提示",content:"不能选择当前结点作为父结点!"});
            		$('#categoryId').combotree('setValue', curParentId);
            	}
            	return false;
            },
            onLoadSuccess:function(node,data){ 
                if(categoryId){
                	$('#categoryId').combotree('setValue', categoryId);
                }else{
                	$('#categoryId').combotree('setValue', data[0].id);
                }
            }
        }); 
		
		var formRules = {
			  name: {
                  required: true,
                 /* stringCheck: true,*/
                  maxlength: 30
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空"
         	 }
         };
		
		$("#save_configAttributes").bind("click",function(){
			if($("#configAttributesForm").valid()){
				var saveUrl = "";
				if(configAttributesId){
					saveUrl = "${ctx}/configAttributes/updateConfigAttributes";
				}else {
					saveUrl = "${ctx}/configAttributes/addConfigAttributes";
				}
				var datas = {};
				var formData = $("#configAttributesForm").serializeArray();
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
		validateForm("configAttributesForm",formRules,formValidateMessages);
	});
	

</script>
</body>
</html>