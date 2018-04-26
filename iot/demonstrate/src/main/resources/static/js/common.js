
$(function(){
	window.document.onkeydown=function(event){
		if(event.keyCode==27){
			if($("#toggleMenu").css("display")=="none"){
				$("#toggleMenu").slideDown();
			} else {
				$("#toggleMenu").slideUp();
			}
		}
		if(event.keyCode==122){
			window.location.reload();
		}
	}
})
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