

//刷新验证码
function refreshValidateCode(ctx){
	$("#vCodeImg").attr("src",ctx+"/images/verifyImg?random="+Math.random());
}
function clearIt(){
	if(InterValObj!="undefined"){
		window.clearInterval(InterValObj);// 停止计时器
		$(".btnSendCode").val("发送验证码");
		$(".btnSendCode").removeAttr("disabled");
	}
}


//写cookies 
function setCookie(b, d, a, f, c, e) {
	document.cookie = b + "=" + escape(d)
		+ ((a) ? "; expires=" + a.toGMTString() : "")
		+ ((f) ? "; path=" + f : "") + ((c) ? "; domain=" + c : "")
		+ ((e) ? "; secure" : "")
}
//读取cookies 
function getCookie(name){ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null;
} 
//删除cookies 
function delCookie(name,path,domain){ 
	if (getCookie(name)) {
		var exp = new Date(); 
		exp.setTime(exp.getTime() - 1); 
		var cval=getCookie(name); 
		document.cookie= name + "="+cval+";expires="+exp.toGMTString()+((path) ? "; path=" + path : "") + ((domain) ? "; domain=" + domain : "")
	}
} 
function getUserNameAndPwd(){
	if(getCookie("account")!==null){
		$("#username").val(getCookie("account"));
		if(getCookie("userpassword")!==null&&getCookie("userpassword")!==""){
			$("#login_pwd").val(getCookie("userpassword"));
			$("#remember").attr("checked",true)
		}
		$("#login_pwd").focus()
	}else{
		$("#username").focus()
	}
}
function saveUsername(){
	var a=new Date();
	a.setTime(a.getTime()+24*30*60*60*1000);
	setCookie("account",$("#username").val(),a,powerPath);
	if($("#remember").is(":checked")===true){
		setCookie("userpassword",$("#login_pwd").val(),a,powerPath)
	}else{
		setCookie("userpassword","",a,powerPath)
	}
}