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
	<div class="page-content">
	 	<div class="row inbox">
			<div class="col-xs-12">
				<div class="panel panel-default">
			  		<div class=" form">
						<form action="#" class="form-horizontal" id="infoForm" method="post">
							<div class="form-body">
								<div class="row">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>设备类别名称：</label>
											<div class="col-xs-8">
												<input name="categoryId" id="categoryId" type="hidden"  value="${category.categoryId }" />
												<input name="code" id="code" type="hidden"  value="${category.code }" />
												<input name="name" id="name" class="form-control"  value="${category.name }"  />
												<span class="help-block"></span>
											</div>
										</div>
										
										<div class="form-group">
											<label class="control-label col-xs-3">父级结点：</label>
											<div class="col-xs-8">
												<input id="parentId" name="parentId"/>
												<span class="help-block"></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div align="center">
								<button type="button" id="save"  class="btn blue  btn_confirm">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default  btn_cancel">关闭</button>                              
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
		var categoryId = '${category.categoryId }';
		var parent = '${category.parentId}';
		var parentId = getUrlParam("parentId");
		var curParentId = -1;
		$('#parentId').combotree({
            headers:{"X-CSRF-TOKEN":token},
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
            		$('#parentId').combotree('setValue', curParentId);
            	}
            	return false;
            },
            onLoadSuccess:function(node,data){ 
                if(parent){
                	curParentId = parent;
                	$('#parentId').combotree('setValue', parent);
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
	 		/*code: {
                  minlength: 2,
                  maxlength: 20,
                  required: true,
                  isCodeGroup:true,
                  remote:{//自带远程验证存在的方法  
	                 url:"${ctx}/category/validateCode",  
	                 type:"post",  
	                 data:{  
	                	 code:function(){return $("#code").val();},id: $("#id").val() 
	                 },  
	                 dataFilter: function(data, type) {  
	                      if (data == 1){
	                    	  return true; 
	                      }else {
	                    	  return false;
		                   }  
	                 }  
	              }  
              },*/
              name: {
             	  minlength: 2,
                  required: true,
                  /*stringCheck:true,*/
                  maxlength: 33,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/category/validateCategoryName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  categoryId:function(){return $("#categoryId").val();}  
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
           	/* code:{
           		minlength:"产品类别编码不能少于2个字符",
         	 	required: "编码不能为空",
         	 	remote:"已存在请重新填写"
         	 },*/
         	 name:{
         		minlength:"产品类别名称不能少于2个字符",
         		maxlength:"产品类别名称不能大于33个字符",
         		required: "名称不能为空",
         		remote:"设备类别名称已存在"	
             }
         };
		validateForm("infoForm",formRules,formValidateMessages);
		
		$("#save").bind("click",function(){
			if($("#infoForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				$("#parentId").val($('#parentId').combotree('getValue'));
				var saveUrl = "";
				if(categoryId){
					saveUrl = "${ctx}/category/updateCategory";
				}else {
					saveUrl = "${ctx}/category/addCategory";
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
			        	if(data && data.flag=='success'){
			        		_closeModal(true);
			        	}else{
			        		new alertdialog({title:"提示",content:data.message,confirmFun:function(){
					    		$(".btn_confirm").removeAttr("disabled");
							}});
			        	}
			        }
				 });
			}
		});
		
	});
	

</script>
</body>
</html>