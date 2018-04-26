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
				<form action="#" class="form-horizontal" id="infoForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>权限名称：</label>
									<div class="col-xs-8">
										<input name="id" id="id" type="hidden"  value="${priviliege.id }" />
										<input name="name" id="name" class="form-control"  value="${priviliege.name }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>权限编码：</label>
									<div class="col-xs-8">
										<input name="code" id="code" class="form-control"  value="${priviliege.code }" />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">父级结点：</label>
									<div class="col-xs-8">
										<input id="parentId" name="parentId"/>
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-4">排序：</label>
										<div class="col-xs-8">
											<input name="position" id="position" class="form-control"  value="${priviliege.position }"/>
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
											<input name="remark" id="remark" class="form-control"  value="${priviliege.remark }"/>
											<span class="help-block"></span>
										</div>
										
									</div>
								</div>
						</div>
						
						
					</div>
					<div class="form-actions right"> 
						<div class="col-xs-offset-3 col-xs-9">
							<button type="button" id="save"  class="btn blue">提交</button>
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
	
	<script>

	$(document).ready(function(){
		var priId = '${priviliege.id }';
		var parent = '${priviliege.parentId}';
		var parentId = '${parentId}';
		var curParentId = -1;
		$('#parentId').combotree({  
            url: "${ctx}/pri/getTreeData",  
            required: false,
            method : "get",
            cascadeCheck:false,
            width : 225,
            panelHeight : 150,
            editable : false,
            onSelect:function(node){
            	if(node.id==priId){
            		new alertdialog({title:"提示",content:"不能选择当前结点作为父结点!"});
            		$('#parentId').combotree('setValue', curParentId);
            	}
            	return false;
            },
            onLoadSuccess:function(node,data){ 
                if(parent){
                	$('#parentId').combotree('setValue', parent);
                	curParentId = parent;
                }else{
                	if(parentId && parentId!='-1'){
                		$('#parentId').combotree('setValue', parentId);
                		curParentId = parentId;
                	}else if(data && data[0]){
                		curParentId = data[0].id;
                		$('#parentId').combotree('setValue', data[0].id);
                	}
                }
            }
        }); 
		
		var formRules = {
	 		code: {
                  minlength: 2,
                  maxlength: 20,
                  isCodeGroup: true,
                  required: true,
                  remote:{//自带远程验证存在的方法  
	                 url:"${ctx}/pri/validateCode",  
	                 type:"post",  
	                 data:{  
	                	 code:function(){return $("#code").val();},id: $("#id").val()
	                 },  
	                 dataFilter: function(data, type) {  
	                      if (data == 1)  
	                          return true;  
	                      else {
	                    	  return false;
		                   }  
	                 }  
	              }  
              },
              name: {
             	 minlength: 2,
             	 maxlength: 20,
             	 stringCheck: true,
                  required: true
              },
              position:{
            	  digits: true,
            	  maxlength: 20
              },
              remark:{
            	  maxlength: 200
              }
             
         };
		var formValidateMessages = {
           	 code:{
         	 	required: "权限编码不能为空",
         	 	remote:"权限编码已存在请重新填写"
         	 },
         	 name:{
         		required: "权限不能为空"
             },
             position:{
            	 digits:"请输入一个整数"
             },
             remark:{
            	 maxlength:"备注不能超过200个字符"
             }
         };
		
		validateForm("infoForm",formRules,formValidateMessages);
		
		$("#save").bind("click",function(){
			if($("#infoForm").valid()){
				$("#parentId").val($('#parentId').combotree('getValue'));
				var saveUrl = "";
				if(priId){
					saveUrl = "${ctx}/pri/updatePri";
				}else {
					saveUrl = "${ctx}/pri/addPri";
				}
				var datas = {};
				var formData = $("#infoForm").serializeArray();
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
			        	if(data){
			        		_closeModal(true);
			        	}else{
			        		alert("保存失败");
			        	}
			        }
				 });
			}
		});
		
	});
	

</script>
</body>
</html>