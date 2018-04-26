<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include_easyui.jsp"%>
<link href="${ctx}/plugins/jquery-validation/jquery.validate.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/additional-methods.js" ></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
<!-- md5加密 -->
<script src="${ctx}/plugins/md5andbase64.js" type="text/javascript"></script>
<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajaxSetup({
        beforeSend:function(xhr){
            if(header && token){
                xhr.setRequestHeader(header,token);
            }
        }
    })
</script>

</head>
<body class="page-header-fixed">
<div class="page-container" id="dgformdiv">
	<div class="page-content">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal" id="userinfoForm" method="post" style="margin-top:20px;width:850px">
			<input type="hidden" name="enabled" id="enabled" value="1"/>
			<input type="hidden" id="areaId" name="areaId" value="-1"/>
			<div class="form-body" style="width:850px;">
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录名：</label>
							<div class="col-xs-8">
								<input name="userId" id="userId" type="hidden" />
								<input name="account" id="account" class="form-control"  />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					<!--/span-->
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>用户姓名：</label>
							<div class="col-xs-8">
								<input name="userName" id="userName" class="form-control" />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					<!--/span-->
				</div>
				<div class="row" <c:if test="${user.userId>0}">style="display: none;"</c:if> >
					<div class="col-xs-6" >
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录密码：</label>
							<div class="col-xs-8">
								<input  type="password" name="pwd" id="pwd" class="form-control" id="pwd"  autocomplete="off"/>
								<span class="help-block"></span>
							</div>
							<table style="margin-left:44.3%;">
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
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>密码确认：</label>
							<div class="col-xs-8">
								<input type="password" name="passwordAgain"  class="form-control" id="passwordAgain" autocomplete="off"/>
								<span class="help-block"></span>
							</div>	
						</div>
					</div>
				</div>
				<div class="row">
					<c:if test="${isSuperAdmin ne 'false'}">
					<!-- <div class="col-xs-6" id="b_tentant" <c:if test="${user.userId>0}">style="display: none;"</c:if>>
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>所属租户：</label>
							<div class="col-xs-8">
								<select name="tenantId" id="tenantId" class="form-control">
								</select>
								<span class="help-block"></span>
							</div>
						</div>
					</div> -->
					</c:if>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>联系电话：</label>
							<div class="col-xs-8">
								<input name="msisdn" id="msisdn" class="form-control"  value=""/>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">邮箱：</label>
							<div class="col-xs-8">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
									<input name="email" id="email" class="form-control"  value=""/>
								</div>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				<c:if test="${isSuperAdmin eq 'false'}">
				</div>
				<div class="row">
				</c:if>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">详细地址：</label>
							<div class="col-xs-8">
								<input name="detailAddress" id="detailAddress" class="form-control"  value=""/>
								<span class="help-block"></span>
							</div>	
						</div>
					</div>
				</div>
			</div>
			<div align="center"> 
				<button type="button" id="save_user"  class="btn blue btn_confirm">提交</button>
				<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
			</div>
		</form>	
	</div>
</div>
	
<script>
var isSuperAdmin = ${isSuperAdmin} ;
$(document).ready(function(){
		var userId = '${user.userId }';
		var selectOrganId = '${selectOrganId}';
		/*if(!isSuperAdmin){
			$("#b_tentant").hide();
		}*/
		initTenant();
		var formRules = {
			account: {
                  minlength: 2,
                  maxlength: 18,
                  isCodeGroup: true,
                  required: true,
                  remote:{//自带远程验证存在的方法  
	                 url:"${ctx}/register/validateAccount",  
	                 type:"post",  
	                 data:{  
	                	 account:function(){return $("#account").val();}  
	                 },  
	                 dataFilter: function(data, type) {  
	                      if (data == "yes")  
	                          return true;  
	                      else {
		                      if($("#userId").val()){
									return true;
			                   }else{
	                    	  		return false;
			                   }
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
  			 passwordAgain: {
  				required: true,
  				equalTo: "#pwd"
  			 },
              email:{
            	  email:true
              },
              msisdn:{
            	  required:true,
            	  isMobile:true,
            	  remote:{//自带远程验证存在的方法  
 	                 url:"${ctx}/register/validatePhone",  
 	                 type:"post",  
 	                 data:{  
 	                	msisdn:function(){return $("#msisdn").val();}  
 	                 },  
 	                 dataFilter: function(data, type) {  
 	                      if (data == "yes")  
 	                          return true;  
 	                      else {
 		                      if($("#userId").val()){
 									return true;
 			                   }else{
 	                    	  		return false;
 			                   }
 		                   }  
 	                 }  
 	              }  
              },
         };
		var formValidateMessages = {
			account:{
         	 	required: "请输入登录名称",
         	 	remote:"登录名称已存在"
         	 },
         	 userName:{
         		required: "请输入用户姓名"
             },
             pwd: {
   				required: "请输入密码",
   				rangelength: jQuery.format("密码长度为6-8位数"),
   				regPassword: "请使用大小写或数字和字母的组合"
   			 },
   			 passwordAgain: {
 	  			required: "请重新输入密码",
 	  			equalTo: "两次密码不一样"
 	  		 },
             email:{
            	 email: "请输入正确的邮箱地址"       
             },
             msisdn:{
            	 required: "请输入电话号码",
            	 isMobile:"请输入正确的电话号码" ,
              	 remote:"手机号已存在"
             }
         };
		
		$("#save_user").bind("click",function(){
			if($("#userinfoForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				var saveUrl = "";
				if(userId){
					saveUrl = "${ctx}/user/updateUser";
				}else {
					saveUrl = "${ctx}/user/addUser";
				}
				var datas = {};
				var formData = $("#userinfoForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				if(!isSuperAdmin){
					delete datas.tenantId;
				}
				/*if(!efficacyPassword(datas.pwd)){
					return;
				}*/
				datas.pwd=hex_md5(datas.pwd);
			    delete datas.passwordAgain;
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
			        	if(data.flag=="success"){
			        		_closeModal(true);
			        	} else{
			        		new alertdialog({title:"提示",content:data.message,confirmFun:function(){
					    		$(".btn_confirm").removeAttr("disabled");
							}});
			        	}
			        }
				 });
			}
		});
		validateForm("userinfoForm",formRules,formValidateMessages);
	});
	
	function initTenant(){
		$.ajax({
	        url: '${ctx}/tenant/datasByPage',
	        datatype: 'json',
	        type: "Post",
	        data:{"pageNo":1,pageSize:99999},
	        success: function (data) {
	        	var str='';
	        	var items=data.result;
	        	for(var i=0;i<items.length;i++){
	        		str+='<option value="'+items[i].tenantId+'">'+items[i].name+'</option>';
	        	}
	        	$("#tenantId").append(str);
	        }
		});
	}
	//密码强度校验 lzh
	$("#pwd").keyup(function(){
		showProgress($(this));
	})

</script>
</body>
</html>