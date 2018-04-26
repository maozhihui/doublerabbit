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
<style type="text/css">
.panel-title{
    line-height: 30px;
    background: #fff;
    margin-left: 30px;
    margin-bottom: 15px;
    display: block;
    color: #3a6fd7;
    font-size: 16px;
    font-weight: bold;
}
</style>
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
		<div class="page-content" align="center" style="padding-top: 10px;">
		<h3 class="panel-title" style="text-align: left;display: block;padding-bottom: 30px;">
			查看规则
		</h3>
				<div class="panel panel-default">
				<div class=" form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="ruleForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-2 label-view-status">名称：</label>
										<label style="text-align:left" class="control-label col-xs-10 label-viewcontent-color">
											${rule.name }
										</label>
									</div>
								</div>	
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label form-right col-xs-2 label-view-status">描述：</label>
										<label style="text-align:left" class="control-label col-xs-10 label-viewcontent-color">
											${rule.additionalinfo }
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>插件：</label>
										<div class="col-xs-10">
										   <input id="pluginId" name="pluginId" disabled="disabled"/>
											<span class="help-block"></span>
										</div>
									</div>	
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>过滤器配置：</label>
										<div class="col-xs-10">
											<div class="form-group">
												<table class="table" style="width: 95%;display: none;" id="filterTable">
												   <thead>
												      <tr>
												         <th>过滤器名称</th>
												         <th>过滤器类型</th>
												         <th></th>
												      </tr>
												   </thead>
												   <tbody>
												   </tbody>
												</table>
											</div>	
										</div>
									</div>
									<div class="col-xs-12">
										
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>处理器配置：</label>
										<div class="col-xs-10">
											<div class="form-group" >
												<table class="table" style="width: 95%;display: none;" id="processTable" >
												   <thead>
												      <tr>
												         <th>处理器名称</th>
												         <th>处理器类型</th>
												         <th></th>
												      </tr>
												   </thead>
												   <tbody>
												   </tbody>
												</table>
											</div>	
										</div>
									</div>
									<div class="col-xs-12">
										
									</div>
								</div>
							</div>		
						</div>
						<div > 
							<div align="center">
								<button type="button" onclick="returnRules();" class="btn default btn_cancel">返回</button>    
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var ruleId = '${rule.ruleId}';
	var filters = '${rule.filters}';
	var processor = '${rule.processor}';
	var filterDatas = [];
	var processDatas = [];
	function initFilter(filters){
		if(filters){
			var datas = JSON.parse(filters);
			for(var i in datas){
				var fd = datas[i];
				if(fd){
					addFilterDatas(fd);
				}
			}
		}
	}
	function initProcess(processor){
		if(processor){
			var datas = JSON.parse(processor);
			for(var i in datas){
				var fd = datas[i];
				if(fd){
					addProcessDatas(fd);
				}
			}
		}
	}
	
	$(document).ready(function(){
		initFilter(filters);
		initProcess(processor);
		var pluginId = '${rule.pluginId }';
		$('#pluginId').combotree({  
            url: "${ctx}/plugin/getTreeData",  
            required: false,
            method : "get",
            cascadeCheck:false,
            width : 225,
            panelHeight : 150,
            editable : false,
            onLoadSuccess:function(node,data){ 
                if(pluginId){
                	$('#pluginId').combotree('setValue', pluginId);
                }else{
                	if(data && data[0]){
                		$('#pluginId').combotree('setValue', data[0].id);
                	}
                }
            }
        }); 
		
		
		var formRules = {
			  name: {
                  required: true,
                  stringCheck: true,
                  maxlength: 30
              }
         };
		var formValidateMessages = {
			 name:{
         	 	required: "名称不能为空"
         	 }
         };
		
		$("#save_rule").bind("click",function(){
			if($("#ruleForm").valid()){
				$("#pluginId").val($('#pluginId').combotree('getValue'));
				var saveUrl = "";
				if(ruleId){
					saveUrl = "${ctx}/rule/updateRule";
				}else {
					saveUrl = "${ctx}/rule/addRule";
				}
				var datas = {};
				var formData = $("#ruleForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				datas.filters = JSON.stringify(filterDatas);
				datas.processor = JSON.stringify(processDatas);
				 $.ajax({
			        url: saveUrl,
			        datatype: 'json',
			        type: "Post",
			        data:datas,
			        success: function (data) {
			        	if(data.flag=='success'){
			        		returnRules();
				    	}else{
			        		new alertdialog({title:"提示",content:"保存失败!"});
			        	}
			        }
				 });
			}
		});
		validateForm("ruleForm",formRules,formValidateMessages);
	});
	
	/*处理过滤器*/
	function addFilterDatas(obj){
		filterDatas.push(obj);
		$("#filterTable").show().append(getFilterHtml(obj));
	}
	function getFilterHtml(data){
		var idText = "'"+data.id+"'";
		var htmlData = [];
		htmlData.push('<tr id="'+data.id+'"> ');
		htmlData.push('<td>'+data.name+'</td>');
		htmlData.push(' <td>'+data.filterTypeText+'</td>');
		htmlData.push('</tr>');
		return htmlData.join('');
		
	}
	
	var filterGrid = new PageGrid({});
	function createFilter(){
		filterGrid.createModal("新建过滤器","${ctx}/rule/to_filter_edit",650,900);
	}
	function editFilter(id){
		filterGrid.createModal("编辑过滤器","${ctx}/rule/to_filter_edit?filterId="+id,650,900);
	}
	function deleteFilter(id){
		var dataTemp = [];
		for(var i in filterDatas){
			var fd = filterDatas[i];
			if(fd.id!=id){
				dataTemp.push(fd); 
			}
		}
		filterDatas = dataTemp;
		$("#"+id).remove();
	}
	function getFilter(id){
		var dataTemp = {};
		for(var i in filterDatas){
			var fd = filterDatas[i];
			if(fd.id==id){
				dataTemp = fd; 
			}
		}
		return fd;
	}
	
	
	
	
	/*处理器逻辑*/
	function addProcessDatas(obj){
		processDatas.push(obj);
		$("#processTable").show().append(getProcessHtml(obj));
	}
	function getProcessHtml(data){
		var idText = "'"+data.id+"'";
		var htmlData = [];
		htmlData.push('<tr id="'+data.id+'"> ');
		htmlData.push('<td>'+data.name+'</td>');
		htmlData.push(' <td>'+data.processTypeText+'</td>');
		htmlData.push(' </tr>');
		return htmlData.join('');
		
	}
	
	var processGrid = new PageGrid({});
	function createProcess(){
		processGrid.createModal("新建处理器","${ctx}/rule/to_processor_edit",650,900);
	}
	function editProcess(id){
		processGrid.createModal("编辑处理器","${ctx}/rule/to_processor_edit?processId="+id,650,900);
	}
	function deleteProcess(id){
		var dataTemp = [];
		for(var i in processDatas){
			var fd = processDatas[i];
			if(fd.id!=id){
				dataTemp.push(fd); 
			}
		}
		processDatas = dataTemp;
		$("#"+id).remove();
	}
	function getProcess(id){
		var dataTemp = {};
		for(var i in processDatas){
			var fd = processDatas[i];
			if(fd.id==id){
				dataTemp = fd; 
			}
		}
		return fd;
	}
		
	
	
	
	//返回
	function returnRules(){
		
		window.location.href="${ctx}/rule/list";
	}
	
	function hide_modal(tarId){
		if(tarId && $("#"+tarId)){
			$('#'+tarId).modal('hide');
			$("#"+tarId).empty();
		}
	}
</script>
</body>
</html>