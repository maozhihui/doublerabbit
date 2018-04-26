<%@page language="java" pageEncoding="UTF-8" %>
<!--******************************** style******************************** -->
<!-- ************************************************************************-->
<!--  字体 -->
<link href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<!-- 全局-->
<link href="${ctx}/css/icon.css" rel="stylesheet" type="text/css"/>

<link href="${ctx}/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<%-- <link href="${ctx}/common/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/> --%>
<%-- <link href="${ctx}/common/css/style-metronic.css" rel="stylesheet" type="text/css"/> --%>
<%-- <link href="${ctx}/common/css/style.css" rel="stylesheet" type="text/css"/> --%>
<%-- <link href="${ctx}/common/css/style-responsive.css" rel="stylesheet" type="text/css"/> --%>
<%-- <link href="${ctx}/common/css/default.css" rel="stylesheet" type="text/css" id="style_color"/> --%>
<link href="${ctx}/css/custom.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/css/right_content.css" rel="stylesheet" type="text/css"/>
<!-- jqeury -->
<script src="${ctx}/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<!-- bootstrap -->
<script src="${ctx}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<!-- ******************************** script ********************************-->
<script src="${ctx}/js/common/common.js" type="text/javascript"></script> 

<!-- table -->
<script src="${ctx}/plugins/colResizable/colResizable-1.6.js" type="text/javascript"></script><!-- 列宽拖动 -->
<script src="${ctx}/js/common/pageGrid.js" type="text/javascript"></script> 
<script src="${ctx}/js/common/conditionQuery.js" type="text/javascript"></script>
<script src="${ctx}/plugins/pagination/pagination.js" type="text/javascript"></script>
<!-- 日期 -->
<script src="${ctx}/plugins/My97DatePicker48Beta/WdatePicker.js" type="text/javascript"></script>
<!-- validate -->
<link href="${ctx}/plugins/jquery-validation/jquery.validate.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
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