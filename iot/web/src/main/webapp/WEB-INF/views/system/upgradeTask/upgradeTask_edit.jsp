<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
    <link rel="stylesheet" href="${ctx}/plugins/bootstrap-multiselect/css/bootstrap-multiselect.css" type="text/css">
    <script type="text/javascript" src="${ctx}/plugins/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="${ctx}/plugins/bootstrap-multiselect/js/bootstrap-multiselect-collapsible-groups.js"></script>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content">
		<form action="#" class="form-horizontal" id="updateTaskInfoForm" method="post" style="margin-top:30px">
			<input name="id" id="id" type="hidden"  value="${updateTask.id }" />
			<div class="form-body" >
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">任务名称：</label>
							<div class="col-xs-8">
								<input name="name" id="name"  class="form-control"  value="${updateTask.name }"   />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
                </div>
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">升级设备：</label>
                            <div class="col-xs-8">
                                <select name="deviceIdList" id="deviceIdList"  class="form-control" multiple="multiple">
                                </select>
                                <span class="help-block"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">版本：</label>
                            <div class="col-xs-8">
                                <select name="versionId" id="versionId"  type=""  class="form-control" >
                                </select>
                                <span class="help-block"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">描述：</label>
                            <div class="col-xs-8">
                                <input name="taskDesc" id="taskDesc"  type="text[1,127]"  class="form-control"  value="${updateTask.taskDesc }"   />
                                <span class="help-block"></span>
                            </div>
                        </div>
                    </div>
                </div>
			</div>
		</form>
		<div align="center" style="clear: both;">
			<button type="button" id="save_updateTask"  class="btn blue btn_confirm">启动任务</button>
			<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
		</div>
	</div>
</div>
	
<script>
    $(document).ready(function() {
        var versionId = '${updateTask.versionId }';
        var deviceIdList = '${updateTask.deviceIdList }';
        initTaskList(versionId);
        initDevice(deviceIdList);
    });

    $("#save_updateTask").bind("click",function(){
        var updateTaskId = '${updateTask.id }';
        if($("#updateTaskInfoForm").valid()){
            var saveUrl = "";
            if (updateTaskId){
                saveUrl = "${ctx}/upgradeTask/updateTask";
            }else{
                saveUrl = "${ctx}/upgradeTask/addUpgradeTask";
            }
            var datas = {};
            var formData = $("#updateTaskInfoForm").serializeArray();
            $.each(formData, function () {
                if(this.name!=undefined){
                    datas[this.name] = this.value;
                }
            });
            datas.deviceIdList = $("#deviceIdList").val().join(",");
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
                        new alertdialog({title:"提示",content:data.message});
                    }
                }
             });
        }
    });
		
		
    function initTaskList(versionId) {
        var url = '${ctx}/appVersion/datasByPage';
        $.ajax({
            url: url,
            datatype: 'json',
            type: "Post",
            async:false,
            data:{pageNo:1,pageSize:99999},
            success: function (data) {
                items = data.result;
                var str="" ;
                for(var i=0;i<items.length;i++){
                    str+='<option value="'+items[i].id+'">'+items[i].version+'</option>';
                }
                $("#versionId").empty();
                $("#versionId").append(str);
                $("#versionId").val(versionId);
            }
        });
    }

    function initDevice(deviceIdList){
        var items_d;
        var devIds = deviceIdList.split(",");
        $.ajax({
            url: "${ctx}/device/datasByPage",
            datatype: 'json',
            type: "Post",
            async:false,
            data:{pageNo:1,pageSize:9999,isGateWay:1},
            success: function (data) {
                items_d = data.result;
                var str_d="" ;
                for(var i=0;i<items_d.length;i++){
                    str_d+='<option value="'+items_d[i].devId+'">'+items_d[i].name+'</option>';
                }
                $("#deviceIdList").empty();
                $("#deviceIdList").append(str_d);
                $('#deviceIdList').multiselect({buttonWidth: '100%'});
            }
        });
        debugger;
        for(var index in devIds){
            var id = devIds[index];
            $("#deviceIdList  option[value='" + id + "'] ").prop("checked", true);
        }

    }

</script>
</body>
</html>