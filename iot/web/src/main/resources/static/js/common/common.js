//显示遮置
function _showCover(k) {
	if (!k) {
		k = "正在提交，请稍候..."
	}
	_removeCover();
	var l = "bodyCover";
	var j = $(document.body);
	$(document.body)
			.append(
					"<div id='"
							+ l
							+ "gridLoading' style='background-color: #ccc;z-index: 10000;position: absolute;'><table width='100%' height='100%'><tr><td align='center'><table><tr><td  class='loading-img-only'>&nbsp;</td><td id='gridLoadingTip'>"
							+ k
							+ "</td></tr></table></td></tr></table></div>");
	$(document.body).append(
			"<div id='" + l + "gridCover' class='cover'></div>");
	$("#" + l + "gridLoading").css({
		width : j.width(),
		height : '100%',
		left : j.offset().left,
		top : j.offset().top,
		opacity : "0.8",
		display : "none"
	});
	$("#" + l + "gridCover").css({
		width : j.width(),
		height : j.height(),
		left : j.offset().left,
		top : j.offset().top
	});
	$("#" + l + "gridLoading").fadeIn("normal")
}
function _removeCover(){
	var j = "bodyCover";
	$("#" + j + "gridLoading").fadeOut("normal", function() {
		$("#" + j + "gridLoading").remove()
	});
	$("#" + j + "gridCover").remove()
}
function _closeModal(isRefresh){
	var frameId = window.frameElement && window.frameElement.id;
	if(window.parent.hide_modal){
		window.parent.hide_modal(frameId+"_modal",isRefresh);
	}
}

//alert弹框,参数：title,content,width,height,confirmFun()，isclose
alertdialog = function(config) {
	this.init(config);
};
alertdialog.prototype = {
	init: function(config) {
		this.config = config;
		this.createModal();
	},
	//创建弹框
	createModal : function (){
		var _self = this;
		var title = this.config.title;
		var content = this.config.content;
		var height = this.config.height;
		var width = this.config.width;
		var marginleft = this.config.marginleft;
		var isclose = this.config.isclose;
		var idTemp = new Date().getTime();
		var modalId = idTemp + "_modal";
		var confirmId = idTemp + "_confirm";
		this.config.modalId = modalId;
		if(!width){
			width="400px";
		}else{
			width= width + "px";
		}
		if(!height) height="200";
			height = height + 'px';
		if(!marginleft) marginleft="";
			marginleft = marginleft + 'px';
		var modalHtml = [];
		modalHtml.push('<div class="modal fade" id="'+modalId+'"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">');
		modalHtml.push('<div class="modal-dialog" style="width:'+width+';margin-left:'+marginleft+';">  <div class="modal-content">');
		modalHtml.push('<div class="modal-header" style="padding: 5px;background: #2795dc;border-top-left-radius:5px;border-top-right-radius:5px;color:#fff;text-align:center;">  <h4 class="modal-title">'+title+'</h4>  </div>');
		modalHtml.push('<div class="modal-body">  <p style="text-align: center;margin-top: 20px;">'+content+'</p> ');
		modalHtml.push('<div style="margin-top: 20px;text-align: center;">  <button id="'+confirmId+'" type="button" class="btn btn-default btn_confirm" style="color:#fff;margin-right:0px;" >确定</button></div>');
		modalHtml.push('</div></div></div></div>');
		$(document.body).append(modalHtml.join(""));
		$('#'+confirmId).bind("click",function(){
			$('#'+modalId).modal('hide');
			if(isclose){
				_closeModal(true);
			}
			if(_self.config.confirmFun){
				_self.config.confirmFun();
			}
		});
		$('#'+modalId).modal('show');
		
	}
}
//alert弹框
comformDialog = function(config) {
	this.init(config);
};
comformDialog.prototype = {
	init: function(config) {
		this.config = config;
		this.createModal();
	},
	//创建弹框
	createModal : function (){
		var _self = this;
		var title = this.config.title;
		var content = this.config.content;
		var height = this.config.height;
		var width = this.config.width;
		var marginleft = this.config.marginleft;
		var modalId = new Date().getTime(); + "_modal";
		var confirmId = modalId + "_confirm";
		var cancelId = modalId + "_cancel";
		this.config.modalId = modalId;
		if(!width){
			width="400px";
		}else{
			width= width + "px";
		}
		if(!height) height="200";
			height = height + 'px';
		if(!marginleft) marginleft="";
			marginleft = marginleft + 'px';
		var modalHtml = [];
		modalHtml.push('<div class="modal fade" id="'+modalId+'"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">');
		modalHtml.push('<div class="modal-dialog" style="width:'+width+';margin-left:'+marginleft+';">  <div class="modal-content">');
		modalHtml.push('<div class="modal-header" style="padding: 5px;background: #e56a49;border-top-left-radius:5px;border-top-right-radius:5px;color:#fff;text-align:center;">  <h4 class="modal-title">'+title+'</h4>  </div>');
		modalHtml.push('<div class="modal-body">  <p style="text-align: center;margin-top: 20px;">'+content+'</p> ');
		modalHtml.push('<div style="margin-top: 20px;text-align: center;"> <button type="button" id="'+confirmId+'" class="btn default btn_confirm" style="margin-right:5px;">确定</button> <button type="button" id="'+cancelId+'" class="btn default btn_cancel" style="margin-right:0px;">取消</button> </div>');
		modalHtml.push('</div></div></div></div>');
		$(document.body).append(modalHtml.join(""));
		$('#'+modalId).modal('show');
		$("#"+confirmId).bind("click",function(){
			if(_self.config.confirm){
				_self.config.confirm();
			}
			$('#'+modalId).modal('hide');
		});
		$("#"+cancelId).bind("click",function(){
			if(_self.config.cancel){
				_self.config.cancel();
			}
			$('#'+modalId).modal('hide');
		});
	}
}

//form验证
function validateForm(tarId,formRules,formValidateMessages){
	return $('#'+tarId).validate({
         errorElement: 'span', //default input error message container
         errorClass: 'help-block', // default input error message class
         focusInvalid: false, // do not focus the last invalid input
         ignore: "",
         rules:formRules , 
         messages: formValidateMessages,
         errorPlacement: function (error, element) { 
             if (element.parent(".input-group").size() > 0) {
                 error.insertAfter(element.parent(".input-group"));
             } else if (element.attr("data-error-container")) { 
                 error.appendTo(element.attr("data-error-container"));
             } else if (element.parents('.radio-list').size() > 0) { 
                 error.appendTo(element.parents('.radio-list').attr("data-error-container"));
             } else if (element.parents('.radio-inline').size() > 0) { 
                 error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
             } else if (element.parents('.checkbox-list').size() > 0) {
                 error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
             } else if (element.parents('.checkbox-inline').size() > 0) { 
                 error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
             } else {
                 error.insertAfter(element);
             }
         },
         highlight: function (element) { // hightlight error inputs
            $(element)
                 .closest('.form-group').addClass('has-error'); // set error class to the control group
         },
         unhighlight: function (element) { 
             $(element)
                 .closest('.form-group').removeClass('has-error'); // set error class to the control group
         },
         success: function (label) {
             label.closest('.form-group').removeClass('has-error'); // set success class to the control group
         }
     });
}

//时间格式化
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}
//传入参数为 时间戳 和格式类型 （' 1496748741000','yyyy-MM-dd h:m:s'）
function dataFormat(timestamp,format){ 
	var timestamp = timestamp;
	var newDate = new Date();
	newDate.setTime(timestamp);
	return newDate.format(format);
}

//get Form JSON
function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            /*if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');*/
            o[this.name] = o[this.name]+","+this.value;
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

//获取链接参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function initStartDate(endDate){
	var minDate;
	var maxDate;
	if($(endDate).val()!==""){
		maxDate = $(endDate).val();
	} else {
		maxDate ='%y-%M-%d';
	}
	WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate: maxDate});
}
function initEndDate(startDate){
	var minDate;
	if($(startDate).val()!==""){
		minDate = $(startDate).val();
		WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate: minDate,maxDate: '%y-%M-%d'});
	} else {
		WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate: '%y-%M-%d'});
	}
}


/*手机短信验证开始*/
var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage(target,ctx) {
	var msisdn = $("#msisdn").val();
	if(!msisdn){
		new alertdialog({title:"提示",content:"请输入手机号"});return;
	}
	var size = $("#msisdn").next(".error").size();
	var isHide = false;
	if(size!=0){
		isHide = ($("#msisdn").next(".error").css("display")=="none")?true:false;
	}
	if(size==0 || isHide){
	} else {
		return;
	}
	curCount = count;
	// 设置button效果，开始计时
	document.getElementById(target).setAttribute("disabled","true" );//设置按钮为禁用状态
	document.getElementById(target).value=curCount + "后重新发送";//更改按钮文字
	InterValObj = window.setInterval(function(){SetRemainTime(target)}, 1000); // 启动计时器timer处理函数，1秒执行一次
	// 向后台发送处理数据
	$.ajax({
        headers:{"X-CSRF-TOKEN":token},
		type: "POST", // 用POST方式传输
		dataType: "json", // 数据格式:JSON
		url: ctx+"/sendCode", // 目标地址
		data: {"msisdn":msisdn},
		success: function (data){
			if(data.flag == "success"){
				$("#jbPhoneTip").html("<font color='#339933'>短信验证码已发到您的手机,请查收</font>");
	        }else {
				$("#jbPhoneTip").html("<font color='red'>短信验证码发送失败</font>");
			}
		}
	});
}

//timer处理函数
function SetRemainTime(target) {
	if (curCount == 0) {                
		window.clearInterval(InterValObj);// 停止计时器
		document.getElementById(target).removeAttribute("disabled");//移除禁用状态改为可用
		document.getElementById(target).value="重新发送验证码";
	}else {
		curCount--;
		document.getElementById(target).value=curCount + "后重新发送";
	}
}
/*手机短信验证结束*/

//密码强度显示
function showProgress(target){
	var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$","g");
	var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$","g");
	var enoughRegex = new RegExp("^(?=.{6,}).*","g");

	if(false == enoughRegex.test(target.val())){
		//小于6 灰色
		$("#level").removeClass("pw-weak pw-medium pw-strong");
		$("#level").addClass("pw-defule");
	}else if(strongRegex.test(target.val())){
		// 强 大小写特殊字符 
		$("#level").removeClass("pw-weak pw-medium pw-strong");
		$("#level").addClass("pw-strong");
	}else if(mediumRegex.test(target.val())){
		// 中 
		$("#level").removeClass("pw-weak pw-medium pw-strong");
		$("#level").addClass("pw-medium");
	}else{
		// 弱
		$("#level").removeClass("pw-weak pw-medium pw-strong");
		$("#level").addClass("pw-weak");
	}
	return true;
}