<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/meta.jsp"%>
	<%@include file="/common/include.jsp"%>
	
</head>
<body class="page-header-fixed">
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
		<div class="page-content" align="center">
				<div class="panel panel-default">
				<div class=" form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="filterForm" method="post">
						<div class="form-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>名称：</label>
										<div class="col-xs-9">
											<input name="name" id="name" class="form-control"  value=""  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2"><em style="color:#FF0000;font-style:normal">*</em>过滤器类型：</label>
										<div class="col-xs-9">
											<select name="filterType" id="filterType" class="form-control" >
												<!--<c:forEach var="item" items="${filterTypes }">
													<option value="${item.key}" >${item.value}</option>
												</c:forEach>-->
										   </select>
											<span class="help-block"></span>
										</div>
									</div>	
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="control-label col-xs-2">配置：</label>
										<div class="col-xs-9">
											<textarea name="additionalInfo" id="additionalInfo" class="form-control" rows="5" cols="10"></textarea>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
						<div > 
							<div align="center">
								<button type="button" id="save_filter"  class="btn blue btn_confirm">提交</button>
								<button type="button" onclick="_closeModal();" class="btn default btn_cancel">关闭</button>                              
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	
	<script>
	var filterId = '${filterId}';
	var filterType = '${filterTypes }';
	$(document).ready(function(){
		initFilterType();
		initFilter(filterId);
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
		
		$("#save_filter").bind("click",function(){
			if($("#filterForm").valid()){
				var datas = {};
				var formData = $("#filterForm").serializeArray();
				$.each(formData, function () {
					if(this.name!=undefined){
						datas[this.name] = this.value;
					}
				});
				datas.filterTypeText = $("#filterType").find("option:selected").text(); 
				if(!filterId){
					filterId = new Date().getTime();
				}
				datas.id = filterId;
				/*if(window.parent.addFilterDatas){
					window.parent.addFilterDatas(datas);
				}*/
				if(window.parent.editFilterDatas){
					window.parent.editFilterDatas(datas);
				}
				_closeModal();
			}
		});
		validateForm("filterForm",formRules,formValidateMessages);
	});
	function initFilter(id){
		if(id && window.parent.getFilter){
			var initData = window.parent.getFilter(id);
			if(initData){
				$("#name").val(initData.name);
				$("#additionalInfo").val(initData.additionalInfo);
				$("#filterType").val(initData.filterType);
			}
		}
	}
	function initFilterType(){
		var str="";
		filterType = filterType.replace(/\{/,"").replace(/\}/,"");
		var array = filterType.split(",");
		for(var i=0;i< array.length;i++){
			var thisFilter = array[i].split("=");
			str += '<option value="'+thisFilter[0].trim()+'" >'+thisFilter[1]+'</option>';
		}
		$("#filterType").append(str);
	}

</script>
</body>
</html>