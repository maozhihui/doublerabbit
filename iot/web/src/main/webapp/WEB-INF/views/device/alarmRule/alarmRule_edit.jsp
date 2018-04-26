<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>	
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content">
		<form action="#" class="form-horizontal" id="alarmRuleInfoForm" method="post" style="margin-top:30px">
			<input name="id" id="id" type="hidden"  value="${alarmRule.id }" />
			<div class="form-body" >
				<div class="row">
                    <div class="form-group">
                        <label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>规则名称：</label>
                        <div class="col-xs-8">
                            <input name="productId" id="product_select" type="hidden"  value="${CUR_PRODUCT_ID}" />
                            <input name="name" id="name" class="form-control"  value="${alarmRule.name }"  />
                            <span class="help-block"></span>
                        </div>
                    </div>
				</div>
				<div class="row">
                    <div class="form-group">
                        <label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>属性条件：</label>
                        <div class="col-xs-8">
                            <div class="form-group" style="margin-bottom:0px">
                                <div class="col-xs-4 form-group" style="margin:0;padding: 0 0 0 15px;">
                                    <select  id="attribute" name="attribute" class="form-control">
                                    </select>
                                </div>
                                <div class=" col-xs-4">
                                    <select name="ruleCondition" class="form-control" id="ruleCondition" value="${alarmRule.ruleCondition }">
                                        <option value=">">&gt;</option>
                                        <option value=">=">&gt;=</option>
                                        <option value="<">&lt;</option>
                                        <option value="<=">&lt;=</option>
                                        <option value="==">==</option>
                                    </select>
                                </div>
                                <div class="col-xs-4 form-group" style="margin:0;padding: 0 15px 0 0;">
                                    <input class="form-control" name="value" placeholder="数据值" value="${alarmRule.value }" type="text">
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
				<div class="row">
                    <div class="form-group">
                        <label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>告警类型</label>
                        <div class="col-xs-8">
                            <select name="type" id="type" class="form-control"  value="${alarmRule.type }">
                                <option value="1">告警通知</option>
                                <option value="2">告警恢复</option>
                            </select>
                            <span class="help-block"></span>
                        </div>
					</div>
				</div>
                <div class="row">
                    <div class="form-group" id="alarmNameForm">
                        <label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>告警名称：</label>
                        <div class="col-xs-8">
                            <select name="alarmId" class="form-control" id="alarmId" value="${alarmRule.alarmId }">
                            </select>
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group" id="alarmContentForm">
                        <label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal"></em>告警内容：</label>
                        <div class="col-xs-8">
                            <textarea name="alarmContent" id="alarmContent" class="form-control" rows="4" value="${alarmRule.alarmContent }"></textarea>
                            <span class="help-block"></span>
                        </div>
                    </div>
                </div>
			</div>
		</form>
		<div align="center" style="clear: both;">
			<button type="button" id="save_alarmRule"  class="btn blue btn_confirm">提交</button>
			<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
		</div>
	</div>
</div>
	
<script>

    var alarmContents = JSON.parse('${alarmContents}');

	$(document).ready(function(){
		var alarmRuleId = '${alarmRule.id }';
		var ruleCondition = '${alarmRule.ruleCondition}';
		var alarmId = '${alarmRule.alarmId }';
		var type = '${alarmRule.type }';

		var formRules = {
            name: {required: true, maxlength: 30},
            value: { required: true,},
         };
		var formValidateMessages = {
            name:{required: "请输入规则名称"},
            value:{required: "请输入值"},
         };

        initAttribute();

        if (alarmRuleId){
            $("#ruleCondition").val(ruleCondition);
            $("#alarmId").val(alarmId);
            $("#type").val(type);
        }

		$("#save_alarmRule").bind("click",function(){
			if($("#alarmRuleInfoForm").valid()){
				var saveUrl = "";
				if(alarmRuleId){
					saveUrl = "${ctx}/alarmRule/updateAlarmRule";
				}else {
					saveUrl = "${ctx}/alarmRule/addAlarmRule";
				}
				var datas = {}; 
				var formData = $("#alarmRuleInfoForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
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
			        	if(data.flag =="success"){
			        		_closeModal(true);
			        	}else{
			        		new alertdialog({title:"提示",content:"保存失败!"});
			        	}
			        }
				 });
			}
		});
		validateForm("alarmRuleInfoForm",formRules,formValidateMessages);
	});


    function initAttribute(){
        var items_a;
        $.ajax({
            url: "${ctx}/attributesTemplate/get",
            datatype: 'json',
            type: "POST",
            async:false,
            success: function (data) {
                if(data.flag=='success'){
                    items_a = data.relateData.attributes;
                }
            }
        });
        var str_a="" ;
        for(var i=0;i<items_a.length;i++){
            str_a+='<option value="'+items_a[i].name+'">'+items_a[i].name+'</option>';
        }
        $("#attribute").empty();
        $("#attribute").append(str_a);

        if (alarmContents){
            initAlarmContent();
        }
    }

    function initAlarmContent() {
        $("#alarmId").empty();

        for(var i in alarmContents){
            $("#alarmId").append('<option value="'+alarmContents[i].alarmId+'">'+alarmContents[i].alarmName+'</option>');
        }
        $("#alarmContent").val(alarmContents[0].alarmContent);
    }

    $("#alarmId").change(function () {
        var alarmId = $("#alarmId").val();
        for(var i in alarmContents){
            if(alarmContents[i].alarmId == alarmId){
                $("#alarmContent").val(alarmContents[i].alarmContent);
            }
        }
    })

</script>
</body>
</html>