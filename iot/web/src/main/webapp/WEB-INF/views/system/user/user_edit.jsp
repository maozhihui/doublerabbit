<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
    <%@include file="/common/include.jsp"%>
	<link href="${ctx}/plugins/jquery-validation/jquery.validate.css" rel="stylesheet">
	<script type="text/javascript" src="${ctx}/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
	
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content">
	<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal" id="userinfoForm" method="post" style="margin-top:30px">
			<input type="hidden" name="enabled" id="enabled" value="1"/>
			<input type="hidden" id="areaId" name="areaId" value="-1"/>
			<div class="form-body" style="width:850px;">
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录名：</label>
							<div class="col-xs-8">
								<input name="userId" id="userId" type="hidden"  value="${user.userId }" />
								<input type="hidden" id="type" name="type" value="${user.type }"/>
								<input name="account" id="account" class="form-control"  value="${user.account }" <c:if test="${user.userId ne null && user.userId ne ''}">readonly="readonly"	</c:if>  />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					<!--/span-->
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>用户姓名：</label>
								<div class="col-xs-8">
									<input name="userName" id="userName" class="form-control"  value="${user.userName }" />
									<span class="help-block"></span>
								</div>
						</div>
					</div>
				</div>
				<div class="row" id="password_row">
					<div class="col-xs-6" >
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录密码：</label>
							<div class="col-xs-8">
								<input  type="password" name="pwd"  class="form-control" id="pwd" value="${user.pwd}" autocomplete="off"/>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">密码确认：</label>
							<div class="col-xs-8">
								<input type="password" name="passwordAgain"  class="form-control" id="passwordAgain" value="${user.pwd}" autocomplete="off"/>
								<span class="help-block"></span>
							</div>	
						</div>
					</div>
				</div>	
				<div class="row">
					
					<div class="col-xs-6" id="b_tenant" <c:if test="${isSuperAdmin ne 'false'}">style="display:none"</c:if>>
						<div class="form-group">
							<label class="control-label col-xs-4">所属租户：</label>
							<div class="col-xs-8">
								<input name="tenantId" id="tenantId" class="form-control" value="${user.tenantId }"/>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					 
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>联系电话：</label>
							<div class="col-xs-8">
								<input name="msisdn" id="msisdn" class="form-control"  value="${user.msisdn }"/>
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
									<input name="email" id="email" class="form-control"  value="${user.email }"/>
								</div>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>	
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">详细地址：</label>
							<div class="col-xs-8">
								<input name="detailAddress" id="detailAddress" class="form-control"  value="${user.detailAddress }"/>
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div align="center">
			<button type="button" id="save_user"  class="btn blue btn_confirm">提交</button>
			<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
		</div>
	</div>
</div>
	
<script>
var isSuperAdmin = "${isSuperAdmin}" ;
	$(document).ready(function(){
		var userId = '${user.userId }';
		if(userId){
			$("#password_row,#b_tenant").hide();
		}
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
    	                	msisdn:function(){return $("#msisdn").val();},
    	                	account:function(){return $("#account").val();}
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
              remark: {
            	  maxlength: 200
              }
         };
		var formValidateMessages = {
           	 account:{
         	 	required: "登录名称不能为空",
         	 	remote:"登陆名称已存在"
         	 },
         	 userName:{
         		required: "用户姓名不能为空"
             },
             email:{
            	 email: "请输入正确的邮箱地址"       
             },
             msisdn:{
            	 required: "请输入电话号码",
              	 isPhone:"请输入正确的电话号码" ,
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
				/*if(!isSuperAdmin){
					delete datas.tenantId;
				}*/
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
	        	var str='<option value="">请选择租户</option>';
	        	var items=data.result;
	        	for(var i=0;i<items.length;i++){
	        		str+='<option value="'+items[i].tenantId+'">'+items[i].name+'</option>';
	        	}
	        	$("#tenant").append(str);
	        	$("#tenant").val("8a8aeb845cf69066015cf6a46e4e0005");
	        }
		});
	}

</script>
</body>
</html>