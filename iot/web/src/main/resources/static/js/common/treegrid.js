/**
 * 树形表格组件
 */

treeTableGrid = function(config) {
	this.init(config);
};
treeTableGrid.prototype = {
		
	init: function(config) {
		this.config = config;
	},
	//构建树结构
	buildTree : function(tarId,columns) {
		this.config.tarId = tarId;
		this.config.columns = columns;
		var $list = $("#"+tarId);
		$list.treegrid({
			data : {},
			idField : "id",
			treeField : "name",
			rownumbers : false,
			pagination : false,
			fitColumns : true,
			fit : true,
			animate : true,
			nowrap : false,
			singleSelection : true,
			onLoadError : function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("提示", XMLHttpRequest.responseText);
			},
			onLoadSuccess : function(row, data) {

				var panel = $(this).treegrid("getPanel");
				panel.find(">div.datagrid-view").unbind("click").bind("click",
						function(e) {
							if (e.target.className == "datagrid-body") {
							}
						});
			},
			onClickRow : function(row) {
			},
			onDblClickRow : function(row) {
				$(this).treegrid("toggle", row.id);
			},
			columns : columns
		});
		return $list;
	},
	//加载树结构数据
	reloadTree : function(url) {
		this.config.url = url;
		var $treegrid = $("#"+this.config.tarId);
		$.ajax({
            headers:{"X-CSRF-TOKEN":token},
			url:url,
			dataType:'json',
			type:'post',
			data:{},
			success:function(result){
				$treegrid.treegrid("loadData", result);
			}
		});
	},
	//树形列表查询，tarId表示树的ID，url:获取数据的url,formId:查询表单的Id
	treeQuery : function(formId,params){
		var _self = this;
		var tarId = this.config.tarId;
		var url = this.config.url;
		if(formId && $("#"+formId)){
			var queryParams = {};
			var formData = $("#"+formId).serializeArray();
			$.each(formData, function () {
				if(this.name!=undefined){
					queryParams[this.name] = this.value;
				}
			});
			if(!params){
				params = {};
			}
			//合并查询参数，后面的覆盖前在的
			var targetParams = {};
			$.extend(targetParams, queryParams, params);
			var $treegrid = $("#"+tarId);
			_self.showCover("正在努力为您加载，请稍候...");
			$.ajax({
                headers:{"X-CSRF-TOKEN":token},
				url:url,
				dataType:'json',
				type:'post',
				error:function(){
		        	_self.removeCover();
		        },
				data:targetParams,
				success:function(result){
					_self.removeCover();
					$treegrid.treegrid("loadData", result);
				}
			});
		}
	},
	getQueryParam : function(formId,params){
		var targetParams = {};
		if(formId && $("#"+formId)){
			var queryParams = {};
			var selectOp = $(".selectC option:selected").attr("name");
			var selectVal = $(".searchText").val();
			if(selectVal != ""){
				queryParams[selectOp] = selectVal;
			}
			if(!params){
				params = {};
			}
			//合并查询参数，后面的覆盖前在的
			$.extend(targetParams, queryParams, params);
		}
		return targetParams;
	},
	treeQuery_2 : function(formId,params){
		var _self = this;
		var tarId = this.config.tarId;
		var url = this.config.url;
		var targetParams = this.getQueryParam(formId,params);
		var $treegrid = $("#"+tarId);
		_self.showCover("正在努力为您加载，请稍候...");
		$.ajax({
            headers:{"X-CSRF-TOKEN":token},
			url:url,
			dataType:'json',
			type:'post',
			error:function(){
	        	_self.removeCover();
	        },
			data:targetParams,
			success:function(result){
				_self.removeCover();
				$treegrid.treegrid("loadData", result);
			}
		});
	},
	//导出
	pageExport:function(exportUrl,formId,baseUrl,fileName,params){
		var _self = this;
		if(!fileName){
			fileName = '';
		}
		var params = this.getQueryParam(formId,params);
		params['orderBy'] = this.config.keyField;
		params['order'] = 'DESC';
		params['fileName'] = fileName;
		params['columns'] = this.config.columns[0];
		_self.showCover("正在努力为您导出，请稍候...");
		$.ajax({
	        url: exportUrl,
	        datatype: 'json',
	        type: "Post",
	        data:params,
	        error:function(){
	        	_self.removeCover();
	        },
	        success: function (data) {
	        	_self.removeCover();
	        	if(data && data.flag=='sucess'){
	        		window.location.href = baseUrl + '/' + data.message;
	        	}else{
	        		new alertdialog({title:"提示",content:data.message});
	        	}
			}
		});
	},
	//创建弹框
	createModal : function (title,url,height,width){
		var iframeId = new Date().getTime();//临时生成一个模块ID
		var modalId = iframeId + "_modal";
		var iframeWidth = "100%";
		if(!width){
			width="100%";
		}else{
			iframeWidth = (width-10) + "px";
			width= width + "px";
		}
		if(!height) height="500";
		height = height + 'px';
		var modalHtml = [];
		modalHtml.push('<div class="modal fade" id="'+modalId+'"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">');
		modalHtml.push('<div class="modal-dialog" style="width:'+width+'">');
		modalHtml.push('<div class="modal-content">');
		modalHtml.push('<div class="modal-header">');
		modalHtml.push('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>');
		modalHtml.push('<h4 class="modal-title">'+title+'</h4>');
		modalHtml.push('</div>');
		modalHtml.push('<div class="modal-body">');
		modalHtml.push('<iframe id="'+iframeId+'" name="bb" src="'+url+'" width="'+iframeWidth+'" height="'+height+'" scrolling="auto" frameborder="0"></iframe>');
		modalHtml.push('</div></div></div></div>');
		$(document.body).append(modalHtml.join(""));
		$('#'+modalId).modal('show');
	},
	initTreeGrid : function(tarId,columns,url){
		this.buildTree(tarId,columns);
		this.reloadTree(url);
	},
	//获取选中的列
	getSelectedRowId : function(){
		$row = $("#"+this.config.tarId).treegrid("getSelected");
		return $row.id;
	},
	//获取选中的列
	getSelectedRow : function(){
		return $("#"+this.config.tarId).treegrid("getSelected");
	},
	showCover:function(k) {
		if (!k) {
			k = "loading"
		}
		this.removeCover();
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
	},
	removeCover :function() {
		var j = "bodyCover";
		$("#" + j + "gridLoading").fadeOut("normal", function() {
			$("#" + j + "gridLoading").remove()
		});
		$("#" + j + "gridCover").remove()
	}
}

