<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	<script src="${ctx}/plugins/easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/easyui/thems/bootstrap/easyui.css">
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<div class="page-content" align="center">
		<div class="panel panel-default">
			<div class="form normalForm" style="padding:0px 20px;">
				<!-- BEGIN FORM-->
				<form action="#" class="form-horizontal" id="deviceForm" method="post">
					<div class="form-body">
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>设备名称：</label>
									<div class="col-xs-8">
										<input name="devId" id="devId" type="hidden"  value="${device.devId }" />
										<input name="name" id="name" class="form-control"  value="${device.name }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>	
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>硬件标识：</label>
									<div class="col-xs-8">
										<input <c:if test="${device.devId != null}">disabled="selected"</c:if> name="hardIdentity" id="hardIdentity" class="form-control"  value="${device.hardIdentity }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6" id="div_templdate">
								<div class="form-group">
									<label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>参数模板 ：</label>
									<div class="col-xs-8">
										<!-- <input id="categoryId" name="categoryId"/> -->
										<select id="deviceTemplateId" name="deviceTemplateId" class="form-control" <c:if test="${not empty device.devId}">disabled</c:if>></select>
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">设备编号：</label>
									<div class="col-xs-8">
										<input name="sn" id="sn" class="form-control"  value="${device.sn }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
                        </div>
						<!-- <div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">鉴权用户名：</label>
									<div class="col-xs-8">
										<input name="userName" id="userName" class="form-control"  value="${device.userName }"  />
										<span class="help-block"></span>
									</div>
								</div>
							</div>			
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">鉴权密钥：</label>
									<div class="col-xs-8">
										<input name="secretKey" id="secretKey" class="form-control"  value="${device.secretKey }"  maxlength=100 />
										<span class="help-block"></span>
									</div>
								</div>
							</div>
						</div> -->
                        <div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-xs-4">是否网关：</label>
									<div class="col-xs-8">
										<select name="isGateWay" id="isGateWay" class="form-control" <c:if test="${not empty device.devId}">disabled</c:if>>
											<option value="0" <c:if test="${device.isGateWay==0}">selected="selected"</c:if>>否</option>
											<option value="1"   <c:if test="${device.isGateWay==1}">selected="selected"</c:if> >是</option>
										</select>
										<span class="help-block"></span>
									</div>
								</div>	
							</div>

                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4">设备位置：</label>
                                    <div class="col-xs-8">
                                        <input name="position" id="position" class="form-control"  value="${device.position }"  />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
						</div>
                        <c:if test="${not empty cameraType}">
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>设备ip：</label>
                                    <div class="col-xs-8">
                                        <input name="deviceCamera.serverIp" id="serverIp" class="form-control"  value="${device.deviceCamera.serverIp }"  maxlength=100 />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>设备端口：</label>
                                    <div class="col-xs-8">
                                        <input name="deviceCamera.port" id="port" class="form-control"  value="${device.deviceCamera.port }"  maxlength=10 />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录账号：</label>
                                    <div class="col-xs-8">
                                        <input name="deviceCamera.userName" id="userName" class="form-control"  value="${device.deviceCamera.userName }"  maxlength=100 />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4"><em style="color:#FF0000;font-style:normal">*</em>登录密码：</label>
                                    <div class="col-xs-8">
                                        <input name="deviceCamera.pwd" id="pwd" class="form-control"  value="${device.deviceCamera.pwd }"  maxlength=100 />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:if>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4">同步lora：</label>
                                    <div class="col-xs-8">
                                        <select name="isLora" id="isLora" class="form-control" >
                                            <option value="0" <c:if test="${device.isLora==0}">selected="selected"</c:if>>否</option>
                                            <option value="1"   <c:if test="${device.isLora==1}">selected="selected"</c:if> >是</option>
                                        </select>
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-6" id="appKeyForm">
                                <div class="form-group" >
                                    <label class="control-label col-xs-4">app key：</label>
                                    <div class="col-xs-8">
                                        <input name="appKey" id="appKey" class="form-control"  value="${device.appKey }"  />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label class="control-label col-xs-4">设备描述：</label>
                                    <div class="col-xs-8">
                                        <input name="description" id="description" class="form-control"  value="${device.description }"  maxlength=100 />
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
					<div align="center">
						<button type="button" id="save_device"  class="btn blue btn_confirm">提交</button>
						<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	
<script>
	var deviceId = '${device.devId}';
	var deviceTemplateId = '${device.deviceTemplateId }';
	var categoryId  = '${categoryId}';
	var frompage = getUrlParam("frompage");
    var isLora = '${device.isLora}';

    if (isLora != 1){
        $("#appKeyForm").hide();
    }

	$(document).ready(function(){
		/*if(deviceId){
			$("#div_templdate").hide();
		}*/
		initDeviceTemplate();
		/*$('#categoryId').combotree({  
            url: "${ctx}/category/getTreeData",  
            required: false,
            method : "get",
            cascadeCheck:false,
            width : 225,
            panelHeight : 150,
            editable : false,
            onSelect:function(node){
            	if(node.id==categoryId){
            		new alertdialog({title:"提示",content:"不能选择当前结点作为父结点!"});
            		$('#categoryId').combotree('setValue', curParentId);
            	}
            	return false;
            },
            onLoadSuccess:function(node,data){ 
            	
                if(categoryId){
                	$('#categoryId').combotree('setValue', categoryId);
                }else{
                	$('#categoryId').combotree('setValue', data[0].id);
                }
            }
        }); */
		
		var formRules = {
			  name: {
                  required: true,
                  /*stringCheck: true,*/
                  maxlength: 30,
                  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/device/validateDeviceName",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  devId:function(){return $("#devId").val();}  
		              },  
		              dataFilter: function(data, type) {  
		            	data = JSON.parse(data);
		                if (data.flag == "success")  
		                    return true;  
		                else {
		                    return false;
			            }  
		             }  
		          }  
              },
              hardIdentity:{
            	  required: true,
            	  remote:{//自带远程验证存在的方法  
		              url:"${ctx}/device/validateDeviceHardIdentity",  
		              type:"post", 
		              data:{  
		            	  name:function(){return $("#name").val();},
		            	  hardIdentity:function(){return $("#hardIdentity").val();},
		            	  devId:function(){return $("#devId").val();}
		              },  
		              dataFilter: function(data, type) {  
		            	data = JSON.parse(data);
		                if (data.flag == "success")  
		                    return true;  
		                else {
		                    return false;
			            }  
		             }  
		          }  
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "请输入设备名称",
         	 	remote:"设备名称已存在"
         	 },
         	hardIdentity:{
          	  required: "请输入硬件标识",
          	  remote:"硬件标识已存在"
            }
         };
		
		$("#save_device").bind("click",function(){
			if($("#deviceForm").valid()){
				$(".btn_confirm").attr("disabled",true);
				var saveUrl = "";
				if(deviceId){
					saveUrl = "${ctx}/device/updateDevice";
				}else {
					saveUrl = "${ctx}/device/addDevice";
				}
				$("#isGateWay,#deviceTemplateId").removeAttr("disabled");
				var datas = {};
				var formData = $("#deviceForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				datas.productId = '${CUR_PRODUCT_ID}';
				 $.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:datas,
			        success: function (data) {
			        	if(data.flag=='success'){
			        		if(frompage=="product"){
			        			window.parent.initNumber();
			        			window.parent.getDeviceStat('month');
			        		}
			        		_closeModal(true);
				    	}else{
			        		new alertdialog({title:"提示",content:"保存失败!"+data.message,confirmFun:function(){
					    		$(".btn_confirm").removeAttr("disabled");
							}});
			        	}
			        }
				 });
			}
		});
		validateForm("deviceForm",formRules,formValidateMessages);
	});
	
	function initDeviceTemplate(){
		var url;
		if(deviceId){
			url = "${ctx}/deviceTemplate/datasByPage";
		} else {
			url = "${ctx}/deviceTemplate/datasByPage?categoryId="+categoryId;
		}
		$.ajax({
			url:url ,
		    datatype: 'json',
		    type: "Post",
		    data:{pageNo:1,pageSize:99999},
		    async:false,
		    success: function (data) {
		    	items = data.result;
		    }
		});
		var str="" ;
		for(var i=0;i<items.length;i++){
			str+='<option value="'+items[i].deviceTemplateId+'">'+items[i].name+'</option>';
		}
		$("#deviceTemplateId").empty();
		$("#deviceTemplateId").append(str);
		if(deviceTemplateId){
			$("#deviceTemplateId").val(deviceTemplateId);
		}
	}

	$("#isLora").change(function () {
        var val = $("#isLora").val();
        if(val == '0'){
            $("#appKeyForm").hide();
        }else{
            $("#appKeyForm").show();
        }
    })


</script>
</body>
</html>