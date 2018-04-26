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
.form-content{
	width:95%;
}
.col-3th{
	width:21.6%;
	padding-right:0px;
}
</style>
</head>
<body class="page-header-fixed">
	<form action="#" class="form-horizontal normalForm" id="productForm" method="post">
		<input name="productId" id="productId" type="hidden"  value="${product.productId}" />
		<input name="tenantId" id="tenantId" type="hidden"  value="${product.tenantId}" />
		<div class="form-content">
			<div class="form-group">
				<label class="control-label col-xs-3 col-xs-offset-1"><em style="color:#FF0000;font-style:normal">*</em>产品名称：</label>
				<div class="col-xs-8">
					<input name="name" id="name" class="form-control"  value="${product.name }"  />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3 col-xs-offset-1"><em style="color:#FF0000;font-style:normal">*</em>所属类型：</label>
				<div class="col-xs-3 col-3th form-group" style="margin:0;padding:0 0 0 15px;">
					<!--  <input id="categoryId" name="categoryId"/>-->
					<select  name="categoryLarge" class="form-control productLevel"></select>
					<span class="help-block"></span>
				</div>
				<div class="col-xs-3 col-3th form-group" style="margin:0;padding:0 0 0 15px;">
					<select   name="categoryMedium" class="form-control productLevel" >
						<option value="">请选择</option>
					</select>
					<span class="help-block"></span>
				</div>
				<div class="col-xs-3 col-3th form-group" style="margin:0;padding:0 0 0 15px;">
					<select   name="categoryId" class="form-control productLevel">
						<option value="">请选择</option>
					</select>
					<span class="help-block"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3 col-xs-offset-1">产品型号：</label>
				<div class="col-xs-8">
					<input name="model" id="model" class="form-control"  value="${product.model}"  />
				</div>
			</div>				
			<div class="form-group">
				<label class="control-label col-xs-3 col-xs-offset-1"><em style="color:#FF0000;font-style:normal">*</em>接入方式：</label>
					<div class="col-xs-8">
						<select name="accessProtocol" id="accessProtocol" class="form-control" >
							<option value="HTTP" <c:if test="${product.accessProtocol=='HTTP'}">selected="selected"</c:if>>HTTP</option>
							<option value="HTTPS" <c:if test="${product.accessProtocol=='HTTPS'}">selected="selected"</c:if>>HTTPS</option>
							<option value="MQTT" <c:if test="${product.accessProtocol=='MQTT'}">selected="selected"</c:if>>MQTT</option>
							<option value="other" <c:if test="${product.accessProtocol=='other'}">selected="selected"</c:if>>其它</option>
						</select>
						<span class="help-block"></span>
					</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3 col-xs-offset-1">产品简介：</label>
				<div class="col-xs-8">
					<textarea rows="3" cols="20" class="form-control" name="brief" id="brief">${product.brief}</textarea>
					<span class="help-block"></span>
				</div>
			</div>
			
		</div>
		<div align="center">
			<button type="button" id="save_product"  class="btn btn_confirm">提交</button>
			<button type="button" onclick="_closeModal(true);" class="btn btn_cancel">关闭</button>                              
		</div>
	</form>
</body>
<script>
var pluginId = '${plugin.pluginId}';
var frameIndex = getUrlParam("frameIndex");
var categoryLarge = '${parentCategory.parentId}';
var categoryMid = '${parentCategory.categoryId}';
var categoryId = '${category.categoryId }';
$(function(){
	initCategory(2);
	var formRules = {
		  name: {
              required: true,
             /* stringCheck: true,*/
              maxlength: 30
          },
          categoryId:{
        	  required: true,
          },
          categoryMedium:{
        	  required: true,
          },
          categoryLarge:{
        	  required: true,
          }
     };
	var formValidateMessages = {
		 name:{
     	 	required: "名称不能为空"
     	 },
     	 categoryId:{
      	  	required: "产品类型不能为空",
         },
         categoryMedium:{
       	  required: "产品类型不能为空",
         },
         categoryLarge:{
       	  required: "产品类型不能为空",
         }
     };
	validateForm("productForm",formRules,formValidateMessages);
	
});

function initCategory(level,parentId){
	var url;
	if(level==2){
		url = "${ctx}/category/datasByPage?levelFlag="+level;
	} else {
		url = "${ctx}/category/datasByPage?levelFlag="+level+"&parentId="+parentId;
	}
	$.ajax({
		url: url,
	    datatype: 'json',
	    type: "Post",
	    async:false,
        data: {'pageSize': 999,'pageNo':1},
	    success: function (data) {
	    	items = data.result;
	    }
	});
	var str='<option value="">请选择</option>' ;
	for(var i=0;i<items.length;i++){
		str+='<option value="'+items[i].categoryId+'">'+items[i].name+'</option>';
	}
	$(".productLevel:eq("+(level-2)+")").empty();
	$(".productLevel:eq("+(level-2)+")").append(str);
	//$("#categoryId").empty();
	//$("#categoryId").append(str);
	if(categoryId && (level==2)){
		$(".productLevel:eq(0)").val(categoryLarge);
		initCategory(3,$(".productLevel:eq(0)").val());
		$(".productLevel:eq(1)").val(categoryMid);
		initCategory(4,$(".productLevel:eq(1)").val());
		$(".productLevel:eq(2)").val(categoryId);
		/*if(categoryId==3){
			$(".productLevel").attr("disabled",true);
		}*/
	}
}
$(".productLevel:eq(0)").change(function(){
	var parentId= $(this).val();
	initCategory(3,parentId);
	$(".productLevel:eq(2)").html('<option value="">请选择</option>');
})
$(".productLevel:eq(1)").change(function(){
	var parentId= $(this).val();
	initCategory(4,parentId);
})
$("#save_product").bind("click",function(){
	//$(".productLevel").removeAttr("disabled");
	if($("#productForm").valid()){
		var saveUrl = "";
		if('${product.productId}'){
			saveUrl = "${ctx}/product/updateProduct";
		}else {
			saveUrl = "${ctx}/product/addProduct";
		}
		var datas = {};
		var formData = $("#productForm").serializeArray();
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
	        async:false,
	        success: function (data) {
	        	if(data.flag=='success'){
	        		if(frameIndex==2){
	        			window.parent.parent.initProduct();
	        			window.parent.location.reload();
	        		} else {
	        			window.parent.initProduct();
	        			window.parent.frames["content-iframe"].location.reload();
	        		}
	        		$("#productService",window.parent.document).show();
	        		_closeModal();
		    	}else{
	        		new alertdialog({title:"提示",content:data.message});
	        	}
	        }
		 });
	}
});
</script>
</html>