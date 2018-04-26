<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<title>${_title }</title>

<!-- Bootstrap -->
<link href="${ctx}/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/css/login.css" rel="stylesheet">
<script src="${ctx}/js/jquery-2.0.3.min.js"></script>
<script src="${ctx}/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/jquery.validate.min.js" ></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/additional-methods.js" ></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
<script src="${ctx}/js/common/common.js" type="text/javascript"></script> 
<!-- md5加密 -->
<script src="${ctx}/plugins/md5andbase64.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="container">
		<div class="form row">
			<div style="background:#3c8dbc"><img src="${ctx}/images/login_title.png" style="width:270px;margin-left:20px;"/></div>
			<!-- 注册 -->
			<form class="form-horizontal col-sm-offset-2 col-md-offset-2" id="register_form" method="post">
				<h3 class="form-title" style="color: #428bca;font-size: 16px;height:38px;">注册账号</h3>
				<div class="col-sm-9 col-md-9">
					<div  style="margin-left:-15px;color:#a94442;margin-bottom:5px;display:none" class="register_error">验证码错误</div>
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i>
						<input class="form-control required" type="text" placeholder="用户账号" name="account" autofocus="autofocus" id="rg_account"/>
					</div>
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i>
						<input class="form-control required" type="text" placeholder="用户姓名" name="userName"/>
					</div>
					<div class="form-group">
						<i class="fa fa-lock fa-lg"></i>
						<input class="form-control required" type="password" placeholder="密码" id="register_password" name="pwd" autocomplete="off"/>
						<table style="margin-left:43px;margin-top:5px;">
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
							<i class="fa fa-check fa-lg"></i>
							<input class="form-control required" type="password" placeholder="请重新输入密码" name="rpassword" autocomplete="off"/>
					</div>
					<div class="form-group">
							<i class="fa fa-envelope fa-lg"></i>
							<input class="form-control eamil" type="text" placeholder="邮箱地址" name="email"/>
					</div>
					<div class="form-group">
						<table>
							<tr>
								<td>
									<i class="fa fa-phone fa-lg"></i>
									<input class="form-control required" type="text" placeholder="电话号码" name="msisdn" id="msisdn"/>
								</td>
								
								<td style="vertical-align: top;">
									<input id="btnSendCode"  class="btnSendCode" type="button"   value="发送验证码" onclick="sendMessage('btnSendCode','${ctx}');" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div id="jbPhoneTip" style="margin-top:3px;"></div>
								</td>
							</tr>
						</table>
					</div>
					<div class="form-group">
							<i class="fa fa-lock fa-lg"></i>
						    <input class="form-control required" type="text" placeholder="验证码" name="msCaption" 
										maxlength="8"/>
					</div>
				<!--  	<div class="form-group">
						<i class="fa fa-hdd-o fa-lg"></i>
						<input class="form-control eamil" type="text" placeholder="通信地址" name="detailAddress"/>
					</div>-->
					<div class="form-group" style="text-align:center">
						<input type="button" class="btn" value="注  册 " id="register_btn">
						<input type="button" class="btn return_btn" value="返  回 " id="return_btn" onclick="javascript:window.location.href='${ctx}/login'">
					</div>
				</div>
			</form>
			<!-- 注册 结束-->
		</div>
	</div>
<script type="text/javascript">
$(function(){
	$("#register_form").validate({
		rules: {
			account: {
                minlength: 2,
                maxlength: 18,
                isCodeGroup: true,
                required: true,
                remote:{//自带远程验证存在的方法
                     headers:{"X-CSRF-TOKEN":token},
	                 url:"${ctx}/register/validateAccount",  
	                 type:"post",  
	                 data:{  
	                	 account:function(){return $("#rg_account").val();}  
	                 },  
	                 dataFilter: function(data, type) {  
	                     if (data == "yes"){  
	                         return true;  
	                     } else {
	                    	  return false;
		                 }  
	                 }  
	              }  
            },
			userName: {
           	  minlength: 2,
           	  maxlength: 18,
           	  isNameGroup: true,
                required: true
            },
			pwd: {
				required: true,
				rangelength: [6,8],
				regPassword: "^(?=.{6,8})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",  
			},
			rpassword: {
				required: true,
				equalTo: "#register_password"
			},
			email: {
				//required: true,
				email: true
			},
			msisdn:{
          	  required:true,
          	  isMobile:true,
          	  remote:{//自带远程验证存在的方法
                     headers:{"X-CSRF-TOKEN":token},
	                 url:"${ctx}/register/validatePhone",  
	                 type:"post",  
	                 data:{  
	                	msisdn:function(){return $("#msisdn").val();}  
	                 },  
	                 dataFilter: function(data, type) {  
	                      if (data == "yes")  
	                          return true;  
	                      else {
	                    	  return false;
		                   }  
	                 }  
	              }  
            },
			msCaption:"required",
		},
		messages: {
			account:{
         	 	required: "请输入用户账号",
         	 	remote:"用户账号已存在"
         	},
			userName:{
         		required: "请输入用户姓名"
             },
			pwd: {
				required: "请输入密码",
				rangelength: jQuery.format("密码长度为6-8位数"),
				regPassword: "请使用大小写或数字和字母的组合"
			},
			rpassword: {
				required: "请重新输入密码",
				equalTo: "两次密码不一样"
			},
			email: {
				//required: "请输入邮箱",
				email: "请输入有效邮箱"
			},
			msisdn:{
           	    required: "请输入电话号码",
           	    isMobile:"请输入正确的电话号码" ,
             	remote:"手机号已存在"
            },
			msCaption:"请输入电话验证码"
		}
	});
	$("#register_btn").bind("click",function(){
		if($("#register_form").valid()){
			var datas = {};
			var formData = $("#register_form").serializeArray();
			$.each(formData, function () {
				if(this.name!=undefined){
					datas[this.name] = this.value;
				}
			});
		    datas.pwd=hex_md5(datas.pwd);
		    delete datas.rpassword;
			 $.ajax({
                headers:{"X-CSRF-TOKEN":token},
		        url: '${ctx}/register/newUser',
		        datatype: 'json',
		        type: "Post",
		        data:datas,
		        success: function (data) {
		        	if(data.flag=='success'){
		        		new alertdialog({title:"提示",content:"注册成功",confirmFun:function(){
		        			$("#return_btn").trigger("click");
						}});
			    	}else{
		        		new alertdialog({title:"提示",content:data.message});
		        	}
		        }
			 });
		}
	});
})

//密码强度校验 lzh
$("#register_password").keyup(function(){
	showProgress($(this));
});
</script>
</body>
</html>