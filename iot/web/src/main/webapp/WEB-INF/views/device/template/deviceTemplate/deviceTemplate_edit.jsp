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
.panel-title {
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
			<c:if test="${empty deviceTemplate.deviceTemplateId}">
			<h3 class="panel-title" style="text-align: left;display: block;padding-bottom: 30px;">
				新建参数模板
			</h3>
			</c:if>
				<div class="panel panel-default">
				<div class=" form" style="margin-top:40px;">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="tenantForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2 col-xs-offset-1"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
										<div class="col-xs-6">
										    <input name="deviceTemplateId" id="deviceTemplateId" type="hidden"  value="${deviceTemplate.deviceTemplateId }" />
											<input name="name" id="name" class="form-control"  value="${deviceTemplate.name }"  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2 col-xs-offset-1">所属设备类别 ：</label>
										<div class="col-xs-6">
											<!-- <input id="categoryId" name="categoryId"/>-->
											<select id="categoryId" name="categoryId" class="form-control"></select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2 col-xs-offset-1">备注：</label>
										<div class="col-xs-6">
											<input id="description" name="description" class="form-control" value="${deviceTemplate.description}"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_tenant"  class="btn btn_confirm">保存</button>
								<button type="button" id="cancel_btn" class="btn btn_cancel">返回</button>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var deviceTemplateId = '${deviceTemplate.deviceTemplateId}';
	var categoryId = '${deviceTemplate.categoryId }';
	$(document).ready(function(){
		if(deviceTemplateId){
			$("#save_tenant").html("保存");
			$("#cancel_btn").click(function(){
				returnByUrl(true);
			})
		}else {
			$("#save_tenant").html("下一步");
			$("#cancel_btn").click(function(){
				returnByUrl();
			})
		}
		initCategory();
		/*$('#categoryId').combotree({  
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
        });*/ 
		
		var formRules = {
			  name: {
                  required: true,
                  /*stringCheck: true,*/
                  maxlength: 30,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/deviceTemplate/validateDeviceTemplateName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  deviceTemplateId:function(){return $("#deviceTemplateId").val();}  
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
         	 	remote:"模板名称已存在"
         	 }
         };
		
		$("#save_tenant").bind("click",function(){
			if($("#tenantForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				var saveUrl = "";
				if(deviceTemplateId){
					saveUrl = "${ctx}/deviceTemplate/updateDeviceTemplate";
				}else {
					saveUrl = "${ctx}/deviceTemplate/addDeviceTemplate";
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
			        		//_closeModal(true);
			        		if(deviceTemplateId){
			        			new alertdialog({title:"提示",content:"保存成功!",confirmFun:function(){
						    		$(".btn_confirm").removeAttr("disabled");
								}});
			        		} else {
			        			window.location.href = "${ctx}/deviceTemplate/to_deviceTemplate_edit_index?deviceTemplateId="+data.relateData.deviceTemplateId+"&saveItem=true";
			        		}
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
	
	function initCategory(){
		$.ajax({
			url: "${ctx}/category/datasByPage?levelFlag=4",
		    datatype: 'json',
		    type: "Post",
		    async:false,
            data: {'pageSize': 999,'pageNo':1},
		    success: function (data) {
		    	items = data.result;
		    }
		});
		var str="" ;
		for(var i=0;i<items.length;i++){
			str+='<option value="'+items[i].categoryId+'">'+items[i].name+'</option>';
		}
		$("#categoryId").empty();
		$("#categoryId").append(str);
		if(categoryId){
			$("#categoryId").val(categoryId);
		}
	}
	//返回
	function returnByUrl(param){
		var returnUrl = '${returnUrl}';
		if(returnUrl=='' || returnUrl=='-1'){
			returnUrl = '/deviceTemplate/list';
		}
		if(param){
			window.parent.location.href="${ctx}" + returnUrl;
		} else {
			window.location.href="${ctx}" + returnUrl;
		}
	}
</script>
</body>
</html>