<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<style type="text/css">

</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<form id="template-form" name="template-form" class="form-horizontal normalForm" method="post" action="#" style="margin:30px auto;width:545px;" enctype="multipart/form-data">
			<div class="row">
				<div class="col-xs-12">
					<div class="form-group">
						<label class="control-label col-xs-3" style="vertical-align: middle;line-height: 24px;text-align:right">模板文件 ：</label>
						<div class="col-xs-6">
							<input type="file" id="templateFile" name="templateFile"   style="border:1px solid #ddd;padding:6px;" accept="application/vnd.ms-excel,.csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
							<span class="help-block"></span>
						</div>
					</div>
					<div align="center">
						<button type="button" id="save_template"  class="btn btn_confirm">提交</button>
						<button type="button" onclick="_closeModal(true);" class="btn btn_cancel">关闭</button>                              
					</div>
				</div>
			</div>
		</form>
		
	</div>
</body>
<script type="text/javascript">
$(function(){
	var formRules = {
			templateFile: {
	            required: true,
	        }
	    };
		var formValidateMessages = {
			templateFile:{
	   	 	required: "文件不能为空"
	   	 }
	   };
		validateForm("template-form",formRules,formValidateMessages);
		$("#save_template").bind("click",function(){
			if($("#template-form").valid()){
				$.ajaxFileUpload({
					url : '${ctx}/app/addAppInfo',
					secureuri : false,
					fileElementId : ['templateFile'],
					dataType : 'json',
					success : function(data, status) {
						if(data.flag=="success"){
							_closeModal(true);
					    } else {
					    	new alertdialog({title:"提示",content:"上传失败!"});
					    }
					}
				});
				
			}
		});
})
</script>
</html>