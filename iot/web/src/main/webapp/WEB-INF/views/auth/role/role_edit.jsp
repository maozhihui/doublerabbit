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
					<form action="#" class="form-horizontal" id="infoForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>角色名称：</label>
										<div class="col-xs-8">
											<input name="id" id="id" type="hidden"  value="${role.id }" />
											<input name="name" id="name" class="form-control"  value="${role.name }"  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>角色编码：</label>
										<div class="col-xs-8">
											<input name="code" id="code" class="form-control"  value="${role.code }" />
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
											<input id="parent" name="parent"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-4">排序：</label>
										<div class="col-xs-8">
											<input name="position" id="position" class="form-control"  value="${role.position }"/>
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
											<input name="remark" id="remark" class="form-control"  value="${role.remark }"/>
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
		var roleId = '${role.id }';
		var parent = '${role.parent}';
		var parentId = '${parentId}';
		var curParentId = -1;
		$('#parent').combotree({  
            url: "${ctx}/role/getTreeData",  
            required: false,
            method : "get",
            cascadeCheck:false,
            width : 225,
            panelHeight : 150,
            editable : false,
            onSelect:function(node){
            	if(node.id==roleId){
            		new alertdialog({title:"提示",content:"不能选择当前结点作为父结点!"});
            		$('#parent').combotree('setValue', curParentId);
            	}
            	return false;
            },
            onLoadSuccess:function(node,data){ 
                if(parent){
                	$('#parent').combotree('setValue', parent);
                	curParentId = parent;
                }else{
                	if(parentId && parentId!='-1'){
                		$('#parent').combotree('setValue', parentId);
                		curParentId = parentId;
                	}else if(data && data[0]){
                		$('#parent').combotree('setValue', data[0].id);
                		curParentId = data[0].id;
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
	                 url:"${ctx}/role/validateCode",  
	                 type:"post",  
	                 data:{  
	                	 code:function(){return $("#code").val();},id: $("#id").val()
	                 },  
	                 dataFilter: function(data, type) {  
	                      if (data == 1){
	                    	  return true;  
	                      }else{
	                    	  if (data == 1)  
		                          return true;  
		                      else {
		                    	  return false;
			                   }  
		                  }  
	                 }  
	              }  
              },
              name: {
             	 minlength: 2,
             	 maxlength: 26,
             	 isNameGroup: true,
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
         	 	required: "角色编码不能为空",
         	 	remote:"角色已存在请重新填写"
         	 },
         	 name:{
         		required: "角色不能为空"
             },
             position:{
            	 digits:"请输入一个整数"
             },
             remark:{
            	 maxlength:"备注不能超过200个字符"
             }
         };
		
		$("#save").bind("click",function(){
			if($("#infoForm").valid()){
				$("#parent").val($('#parent').combotree('getValue'));
				var saveUrl = "";
				if(roleId){
					saveUrl = "${ctx}/role/updateRole";
				}else {
					saveUrl = "${ctx}/role/addRole";
				}
				var datas = {};
				var formData = $("#infoForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				_showCover();
				 $.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:datas,
			        error:function(){
			        	_removeCover();
			        },
			        success: function (data) {
			        	_removeCover();
			        	if(data){
			        		_closeModal(true);
			        	}else{
			        		alert("保存失败");
			        	}
			        }
				 });
			}
		});
		validateForm("infoForm",formRules,formValidateMessages);
	});
	

</script>
</body>
</html>