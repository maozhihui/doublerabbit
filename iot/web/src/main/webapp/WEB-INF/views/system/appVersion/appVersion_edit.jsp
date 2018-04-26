<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
    <script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
    <style>
        fieldset {
            padding: 0.35em 0.625em 0.75em;
            margin: 0 2px;
            border: 1px solid #c0c0c0;
        }
        .file{
            position:relative;
            display:inine-block;
            background:#D0EEFF;
            border:1px solid #99D3F5;
            border-radius:4px;
            padding:4px 12px;
            overflow:hidden;
            color:#1E88C7;
            text-decoration:none;
            text-indent:0;
            line-height:20px;
        }

        .file input{
            position:absolute;
            font-size:10px;
            right:0;
            top:0;
            opacity:0;
        }

        .file:hover{
            background:#AADFFD;
            border-color:#78C3F3;
            color:#004974;
            text-decoration:none;
        }
    </style>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content">
		<form action="#" class="form-horizontal" id="appVersionInfoForm" method="post" style="margin-top:30px">
			<input name="id" id="id" type="hidden"  value="${appVersion.id }" />
			<input name="path" id="path" type="hidden"  value="${appVersion.path }" />
			<div class="form-body" >
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">版本名称：</label>
							<div class="col-xs-8">
								<input name="version" id="version"  class="form-control"  value="${appVersion.version }"   />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">文件名：</label>
							<div class="col-xs-8">
								<input name="fileName" id="fileName"  class="form-control" readonly />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">文件大小：</label>
                            <div class="col-xs-8">
                                <input name="fileSize" id="fileSize"   class="form-control" readonly />
                                <span class="help-block"></span>
                            </div>
                        </div>
                    </div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="control-label col-xs-4">文件描述：</label>
							<div class="col-xs-8">
								<input name="fileDesc" id="fileDesc"  class="form-control"  value="${appVersion.fileDesc }"   />
								<span class="help-block"></span>
							</div>
						</div>
					</div>
				</div>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label col-xs-2">上传版本：</label>
                        <div  class="col-xs-10" >
                        <table >
                            <tr>
                                <td >
                                    <a href="javascript:;" class="file"><input type="file" onchange="fileChange(this.value)" id="appFile" name="appFile"/>浏览文件
                                    </a>
                                </td>
                                <td>
                                    <input class="form-control" type="text" id="filetext_scroll" name="filetext_scroll" readonly="readonly">
                                </td>
                                <td ></td>
                                <td>
                                    <input type="button" onclick="upload()" title="Upgrade" value="上传版本" class="btn search_head_searchBtn">
                                </td>
                                <td style="width:50%;">
                                </td>
                            </tr>
                        </table>
                   </div>
                </div>
			    </div>
            </div>
		</form>
		<div align="center" style="clear: both;">
			<button type="button" id="save_appVersion"  class="btn blue btn_confirm">提交</button>
			<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
		</div>
	</div>
</div>
	
<script type="text/javascript">
    $(document).ready(function(){
        var formRules = {
            version: {
                required: true,
                /*stringCheck: true,*/
                maxlength: 30
            },
            fileName: {
                required: true,
            },
            fileSize: {
                required: true,
            }
        };
        var formValidateMessages = {
            version:{
                required: "名称不能为空"
            },
            fileName: {
                required: "文件名为空，文件未上传完",
            },
            fileSize: {
                required: "文件大小为空，文件未上传完",
            }
        };
    $("#save_appVersion").bind("click",function(){
        var appVersionId = '${appVersion.id }';
        if($("#appVersionInfoForm").valid()){
            var saveUrl = "";
            if(appVersionId){
                saveUrl = "${ctx}/appVersion/updateAppVersion";
            }else {
                saveUrl = "${ctx}/appVersion/addAppVersion";
            }
            var datas = {};
            var formData = $("#appVersionInfoForm").serializeArray();
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
        }});
        validateForm("appVersionInfoForm",formRules,formValidateMessages);
    });

function upload() {
    $.ajaxFileUpload({
        url : '${ctx}/appVersion/import',
        secureuri : false,
        fileElementId : ['appFile'],
        dataType : 'json',
        async:false,
        success : function(data) {
            if(data.flag=="success"){
                document.getElementById("fileSize").value=data.relateData.fileSize;
                document.getElementById("path").value=data.relateData.path;
                document.getElementById("fileName").value=data.relateData.fileName;
                new alertdialog({title:"提示",content:"上传成功!",confirmFun:function(){
                }});
            } else {
                new alertdialog({title:"提示",content:data.message,confirmFun:function(){
                }});
            }
        }
    });
}

function fileChange(val) {
    document.getElementById("filetext_scroll").value=val;
}

</script>
</body>
</html>