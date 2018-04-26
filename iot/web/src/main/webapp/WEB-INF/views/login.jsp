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
<script src="${ctx}/js/common/common.js" type="text/javascript"></script> 
<script src="${ctx}/js/common/login.js" type="text/javascript"></script>
<!-- md5加密 -->
<script src="${ctx}/plugins/md5andbase64.js" type="text/javascript"></script>
<style type="text/css">

</style>
</head>
<body>
	<div class="container">
		<div class="form row">
			<div style="background:#3c8dbc;height: 93px;">
				<img src="${ctx}/images/login_title_2.png" style="margin-top: 10px;height: 75px;margin-left:20px;float:left"/>
				<div style=" padding-top: 21px;">
					<span style="font-size: 22px;font-weight: bold;color: #eae0c3;margin-left: 16px;letter-spacing: 3.2px;">京信物联网管理平台</span><br>
					<span style="font-size: 20px;font-weight: bold;color: #eae0c3;letter-spacing: 1.2px; margin-left: 53px;">Comba IoT</span></div>
				</div>
			<div class="form-title form_switch">
			   <div class="switch" id="switch">
			   		<a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">账号登录</a>
					<a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">短信登录</a>
					<div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 65px; left: 0px;"></div>
			   </div>
   			</div>
   			<!-- 密码登录 -->
		
			<form action="${ctx}/login" method="post" class="form-horizontal col-sm-offset-2 col-md-offset-2" id="login_form">
				

				<div class="col-sm-9 col-md-9">
					<div  style="margin-left:-15px;color:#a94442;margin-bottom:5px;display:none" class="login_error">账号或密码错误</div>
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i>
						<input  type="hidden"  name="type" value="1"/>
						<input class="form-control required" type="text" placeholder="用户账号" name="username" id="username" autofocus="autofocus" maxlength="20"/>
					</div>
					<div class="form-group">
							<i class="fa fa-lock fa-lg"></i>
							<input class="form-control required" type="hidden" placeholder="密码" name="password" id="md5Password"/>
							<input class="form-control required" type="password" placeholder="密码"  id="login_pwd" autocomplete="off"/>
					</div>
					<div class="form-group">
						<table>
							<tr>
								<td>
									<i class="fa fa-lock fa-lg"></i>
									<input class="form-control required" type="text" placeholder="验证码" name="caption" id="caption"
										maxlength="8" style="width: 170px;margin-right:5px"/>
								</td>
								<td onclick="refreshValidateCode('${ctx}');" style="vertical-align: top;">
									<img src="${ctx}/images/verifyImg" 	id="vCodeImg" name="vCodeImg"  style="height: 34px;width: 97px;border-radius: 5px;"/>
								</td>
							</tr>
						</table>	
					</div>
					<div class="form-group">
						<!-- <label class="checkbox" style="font-size:14px;">
							<input type="checkbox" name="remember" id="remember" value="1"/> 记住我
						</label> -->
						<hr style="margin:10px 0"/>
						<a href="${ctx}/register/jumpToPage" id="register_toggle" class="col-xs-5" style="text-align:right">注册账号</a>
						<span class="col-xs-1">|</span>
						<a href="${ctx}/findPassword" id="fgPassword" class="col-xs-5">忘记密码</a>
						
					</div>
					<div class="form-group">
						<input type="submit" class="btn pull-right login_btn" value="登陆 " id="login_btn"/>   
					</div>
				</div>
			</form>
			<!-- 密码登录结束 -->
			<!-- 短信登录 -->
			<form action="${ctx}/login" method="post" class="form-horizontal col-sm-offset-2 col-md-offset-2" id="smsLogin_form"  style="display:none">
				<div class="col-sm-9 col-md-9">
					<div  style="margin-left:-15px;color:#a94442;margin-bottom:5px;display:none" class="login_error">验证码错误</div>
					<div class="form-group">
						<table>
							<tr>
								<td>
									<i class="fa fa-phone fa-lg"></i>
									<input  type="hidden"  name="type" value="2"/>
									<input class="form-control required" type="text" placeholder="电话号码" name="msisdn" id="msisdn"/>
								</td>
								
								<td style="vertical-align: top;">
									<input id="btnSendCode" class="btnSendCode" name="btnSendCode" type="button"   value="发送验证码" onclick="sendMessage('btnSendCode','${ctx}');" />
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
						    <input class="form-control required" type="text" placeholder="验证码" name="msCaption"/>
					</div>
					<div class="form-group">
						<hr style="margin:10px 0"/>
						<a href="${ctx}/register/jumpToPage" id="register_toggle">注册账号</a>
					</div>
					<div class="form-group">
						<input type="submit" class="btn pull-right login_btn" value="登陆 " id="smsLogin_btn"/>   
					</div>
				</div>
			</form>
			<!-- 短信登录 结束-->
		</div>
		</div>



<script type="text/javascript">
var powerPath = "/";
$().ready(function() {
	$("#login_form").validate({
		rules: {
			username: "required",
			password: {
				required: true,
			},
			caption: {
				required: true,
				remote:{//自带远程验证存在的方法
                    headers:{"X-CSRF-TOKEN":token},
	                url:"${ctx}/auth/validateCode",  
	                type:"post",  
	                data:{  
	                	validateCode:function(){return $("#caption").val();}  
	                },  
	                dataFilter: function(data, type) {  
	                	if(data == 1){
	                		return true;
	    		        }else{
	    		        	return false;
	    				} 
	                }  
	            }  
			}
		},
		messages: {
            username: "请输入账号",
			password: {
				required: "请输入密码",
			},
			caption: {
				required: "请输入验证码",
				remote:"验证码错误"
			}
		}
	});
	$("#smsLogin_form").validate({
		rules: {
			msisdn: {
				required: true,
				isMobile:true
			},
			msCaption: "required",
		},
		messages: {
			msisdn: {
				required: "请输入电话号码",
				isMobile:"请输入正确的电话号码"  
			},
			msCaption: "请输入验证码",
		}
	});
	
	$("#login_btn").bind("click",function(event){
		event.preventDefault();
		//saveUsername();
		$("#md5Password").val(hex_md5($("#login_pwd").val()));
		$("#login_form").submit();
	})
	
});
$(function() {
	BrowserType() ;
	if(window.top && window.top.location.href!=window.location.href){
		window.top.location.href = window.location.href;
	}
	$('#switch_qlogin').click(function(){
		clearIt();
		$(".login_error").hide();
		$('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_bottom').animate({left:'0px'});
		$('#smsLogin_form').css('display','none');
		$('#login_form').css('display','block');
		$('#jbPhoneTip').empty();
	});
	$('#switch_login').click(function(){
		clearIt();
		$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_bottom').animate({left:'137px'});
		$('#smsLogin_form').css('display','block');
		$('#login_form').css('display','none');
	});
	$("#return_btn").click(function(){
		$("input[type='text'],input[type='password']").each(function(){
			$(this).val("");
		})
		$("#level").removeClass("pw-weak pw-medium pw-strong");
		$("label.error").remove();
		$(".form_switch,#switch_qlogin").show();
		$('#switch_qlogin').trigger("click");
	})
	var error = getUrlParam("error");
	if(error){
		$(".login_error").show();
		var loginType = "${sessionScope.loginType}";
		if(loginType==2){
			$('#switch_login').trigger("click");
		}
	} else {
		$(".login_error").hide();
	}
	//getUserNameAndPwd();
});

function BrowserType(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
   // console.log(userAgent);
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器  
    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器  
    var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器  
    if (isIE){  
         var reIE = new RegExp("MSIE (\\d+\\.\\d+);");  
         reIE.test(userAgent);  
         var fIEVersion = parseFloat(RegExp["$1"]);
         if(fIEVersion < 10){ 
        	 new alertdialog({title:"提示",content:"当前IE浏览器版本过低，请更新到更高版本或使用其它浏览器（firfox、chrome）进行浏览!"});return;
         }  
        
     }//isIE end 
    if (isFF) {
    	var reFF = new RegExp("Firefox/(\\d+\\.\\d+)"); 
    	reFF.test(userAgent); 
    	var ffVersion = parseFloat(RegExp["$1"]);  
    	console.log(ffVersion);
    	if(ffVersion < 39){ 
        	new alertdialog({title:"提示",content:"当前firfox浏览器版本过低，请更新到更高版本或使用其它浏览器（chrome、IE10）进行浏览!"});return;
        } 
    } //isFF end
    if (isChrome) {
    	var reChrome = new RegExp("Chrome/(\\d+\\.\\d+)");
    	reChrome.test(userAgent);  
    	var chromeVersion = parseFloat(RegExp["$1"]);  
    	if(chromeVersion < 58){ 
        	new alertdialog({title:"提示",content:"当前chrome浏览器版本过低，请更新到更高版本或使用其它浏览器（firfox、IE10）进行浏览!"});return;
        } 
    }  //isChrome end 
 }

</script>
</body>
</html>