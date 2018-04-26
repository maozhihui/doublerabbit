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
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/additional-methods.js" ></script>
<!-- md5加密 -->
<script src="${ctx}/plugins/md5andbase64.js" type="text/javascript"></script>
<style type="text/css">
.form-horizontal .form-group{
	margin-right:0px;
    margin-left: 0px;
}
#userinfoForm label{
	text-align:right;
}
</style>
</head>
<div class="page-content">
	<form action="#" class="form-horizontal" id="userinfoForm" method="post">
		<div class="form-body">
			<div class="form-group">
				<label class="control-label col-xs-3">登录名：</label>
				<div class="col-xs-8">
					<input name="userId" id="userId" type="hidden"  value="" />
					<input name="account" id="account" class="form-control"  value="" disabled="disabled"  />
					<span class="help-block"></span>
				</div>
			</div>
			<div class="form-group">	
				<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>初始密码：</label>
				<div class="col-xs-8">
					<input name="oldPassword"  type="password" class="form-control" id="oldPassword" autocomplete="off" />
					<span class="help-block"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>新密码：</label>
				<div class="col-xs-8">
					<input name="newPassword" type="password"  class="form-control" id="newPassword"  maxlength="16" autocomplete="off"/>
					<span class="help-block"></span>
				</div>
				<table style="margin-left:33.3%;">
					<tr>
						<td id="level" class="pw-strength pw-defule">
							<div class="pw-bar">
								<div class="pw-bar-on"></div>
							</div>
							<div class="pw-txt">
								<span>弱</span>
								<span>中</span>
								<span>强</span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3"><em style="color:#FF0000;font-style:normal">*</em>确认密码：</label>
				<div class="col-xs-8">
					<input name="confirmPassword" type="password"  class="form-control" id="confirmPassword"  maxlength="16" autocomplete="off"/>
					<span class="help-block"></span>
				</div>
			</div>
		</div>	
		<div align="center">
			<button type="button" id="save_user" class="btn blue btn_confirm">提交</button>
			<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
		</div>
	</form>
</div>
<script>
$(document).ready(function(){
	var autoset = getUrlParam("autoset");
	if(autoset==1){
		$(".close",parent.document).hide();
		$(".btn_cancel").hide();
	}
	$.ajax({
	    url: "${ctx}/user/currentUser",
	    datatype: 'json',
	    type: "GET",
	    success: function (data) {
	    	$("#userId").val(data.userId);
	    	$("#account").val(data.account);
	    }
	 });
	var formRules = {
		oldPassword: {
	         required: true,
	         maxlength: 12
	     },
	     newPassword: {
	         required: true,
	         rangelength: [6,8],
			 regPassword: "^(?=.{6,8})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",  
	     },
	     confirmPassword:{
	         required: true,
	         equalTo: "#newPassword"
	     }
	 };
	var formValidateMessages = {
		oldPassword:{
	        required: "请输入初始密码"
	    },
	    newPassword:{
	        required: "请输入密码",
	        rangelength: jQuery.format("密码长度为6-8位数"),
	        regPassword: "请使用大小写或数字和字母的组合"
	    },
	    confirmPassword:{
	        required: "请输入确认密码",
	        equalTo: "两次密码不一样"
	    }
	};
	$("#save_user").bind("click",function(){
		if($("#userinfoForm").valid()){
			var userId = $("#userId").val();
			var oldPassword=document.getElementById("oldPassword").value;
			var newPassword=document.getElementById("newPassword").value;
			var confirmPassword=document.getElementById("confirmPassword").value;
			if(newPassword!=confirmPassword){
				new alertdialog({title:"提示",content:"新密码与确认密码不一致！"});
				return;
			}else if(oldPassword==newPassword){
				new alertdialog({title:"提示",content:"登录密码不能与初始密码一致！"});
				return;
			}else if(newPassword.length<6){
				new alertdialog({title:"提示",content:"密码长度应为6-12位！"});
				return;
			}
			var reInt=/[0-9]/;
			var reString=/[a-zA-Z]/;
			if(reInt.test(newPassword) && reString.test(newPassword)){
				new comformDialog({title:"提示",content:"是否确认修改密码？",confirm:function(){
					$.ajax({
					     url: "${ctx}/user/setPassword",
					     datatype: 'json',
					     type: "Post",
					     data:{"userId":userId,"password":hex_md5(newPassword),"oldPassword":hex_md5(oldPassword)},
					     success: function (data) {
						   	 if(data.flag == 'success'){
						        document.getElementById("oldPassword").value='';
						        document.getElementById("newPassword").value='';
						        document.getElementById("confirmPassword").value='';
                                 loginOut();
						     }else {
							      new alertdialog({title:"提示",content:data.message});
						     }
						 }
					});
				}});
			}else{
				new alertdialog({title:"提示",content:"密码需含有数字和字符！"});
				return;
			}
		}
	});
	validateForm("userinfoForm",formRules,formValidateMessages);
	//密码强度校验 lzh
	$("#newPassword").keyup(function(){
		showProgress($(this));
	})
    
    
    function loginOut() {
        window.location.href="${ctx}/loginOut";
    }
});
	

</script>
</body>
</html>