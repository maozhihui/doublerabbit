/**
 * 分页列表 组件封装,需要依赖boostrap的pagination.js
 * 使用方法：
 * var columns = [{
		field : "description",
		title : "操作",
		width : 200
		} ] ;
 var pageGrid = new PageGrid({});
 pageGrid.paging("list",columns,'${ctx}/audit/datasByPage');
 $("#queryBtn").bind("click",function(){
    	pageGrid.pageQuery("searchForm"); 
    });
 参数控制：
 isNotShowCheckbox :true表示不显示复选框
 noShowNumPerPage：true:表示不显示选择每页面选择显示的行数
 isNoPaginationBottom：true:表示分页内容不放在界面的最下面，默认是false
 setRowClickFun(name,vals):表示行点击函数，name表示函数名，vals；是个数组，表示需要传row中的值
 setWidthFormatByNum //设置列表宽度用具体数值，长度超出，则用滚动条，,默认用真实宽度
 setWidthFormatByPercent //设置列表宽度用百分比
 * */
PageGrid = function(config) {
    this.init(config);
};

PageGrid.prototype = {
    init: function(config) {
        this.config = config;

        this.configSorting();
    },

    configSorting: function() {
        var self = this;

        $('#' + self.config.id + ' .sorting').each(function(index, element) {
            var elem = $(element);
            var column = elem.attr('name');

            $(element).click(function() {
                if (elem.hasClass('sorting-asc')) {
                    self.changeOrder(column, 'DESC');
                } else {
                    self.changeOrder(column, 'ASC');
                }
            });

            if (column == self.config.orderBy) {
                elem.addClass('sorting-' + (self.config.asc ? 'asc' : 'desc'));
            }
        });
    },
    setConfig: function(newConfig){
        var targetParams = {};
        $.extend(targetParams, this.config, newConfig);
        this.config = targetParams;
    },
    changeOrder: function (orderBy, order) {
        var params = {
            'pageNo': this.config.pageNo,
            'pageSize': this.config.pageSize,
            'orderBy': orderBy,
            'order': order
        };
        var targetParams = {};
        $.extend(targetParams, this.config.params, params);

        //var url = this.buildUrl(targetParams);
        //location.href = url;
    },
    setWidthFormatByNum: function(){//设置列表宽度用具体数值，长度超出，则用滚动条
        if(!this.config){
            this.config = {};
        }
        this.config.widthFormat = 1;
    },
    setWidthFormatByPercent: function(newConfig){//设置列表宽度用百分比
        if(!this.config){
            this.config = {};
        }
        this.config.widthFormat = 0;
    },
    isWidthFormatByPercent: function(){//判断列宽是否用百分比
        if(this.config && this.config.widthFormat==0){
            return true;
        }
        return false;
    },
    setRowClickFun :function(name,vals){
        this.config.rowClickFun_name = name;
        this.config.rowClickFun_vals = vals;
    },
    getRowClickFun :function(configParam,item){//item为行数据
        var vals = configParam.rowClickFun_vals;
        var name = configParam.rowClickFun_name;
        if(name){
            var valsStr = "";
            if(item && vals && vals.length>0){
                for(var i=0;i<vals.length;i++){
                    var valT = '\'' + item[vals[i]] + '\'';
                    if(valsStr.length>0){
                        valsStr +=","
                    }
                    valsStr +=valT;
                }
            }
            return 'onclick="'+ name + '('+ valsStr + ');"';
        }
        return "";
    },
    changePageNo: function(pageNo) {
        if (pageNo != this.config.pageNo) {
            var params = {
                'pageNo': pageNo,
                'pageSize': this.config.pageSize,
                'orderBy': this.config.orderBy,
                'order': this.config.asc ? 'ASC' : 'DESC'
            };

            var targetParams = {};
            $.extend(targetParams, this.config.params, params);
            this.pagingByParams(targetParams);
        }
        return false;
    },

    changePageSize: function(pageSize) {
        if (pageSize != this.config.pageSize) {
            var params = {
                'pageNo': this.config.pageNo,
                'pageSize': pageSize,
                'orderBy': this.config.orderBy,
                'order': this.config.asc ? 'ASC' : 'DESC'
            };

            var targetParams = {};
            $.extend(targetParams, this.config.params, params);
            this.pagingByParams(targetParams);
        }
        return false;
    },
    pageQuery : function(formId,params){
        if(formId && $("#"+formId)){
            var queryParams = {};
            var formData = $("#"+formId).serializeArray();
            $.each(formData, function () {
                if(this.name!=undefined){
                    queryParams[this.name] = this.value;
                }
            });
            var pageParams = {
                'pageNo': 1,
                'pageSize': this.config.pageSize,
                'orderBy': this.config.orderBy,
                'order': this.config.asc ? 'ASC' : 'DESC'
            };
            if(!params){
                params = {};
            }
            //合并查询参数，后面的覆盖前在的
            var targetParams = {};
            $.extend(targetParams, queryParams, params, pageParams);
            this.pagingByParams(targetParams);
        }
    },
    getPageQueryParam:function(formId,searchArr,params){
        if(formId && $("#"+formId)){
            var queryParams = {};
            var ckey = [];
            var ckeyValue =[];
            var keyValue = "";
            var keyValue_2 = "";
            if(searchArr && searchArr.length>0){
                for(var i in searchArr){
                    //queryParams = $.extend(queryParams,searchArr[i]);
                    for(var j in searchArr[i]){
                        ckey[i] = j;
                        ckeyValue[i] = searchArr[i][j];
                    }
                    if(ckey[0]==ckey[1]||ckey[0]==ckey[2]||((ckey[1]==ckey[2])&&ckey[1]!=undefined)||((ckey[1]==ckey[2])&&ckey[2]!=undefined)){
                        if((ckey[0]==ckey[1]) && ckey[2] == undefined){
                            keyValue = ckey[0];
                            queryParams[keyValue] = ckeyValue[0]+'###'+ckeyValue[1];
                        }else if((ckey[0]==ckey[1]) && ckey[0] != ckey[2] && ckey[2] != undefined){
                            keyValue = ckey[0];
                            keyValue_2 = ckey[2];
                            queryParams[keyValue] = ckeyValue[0]+'###'+ckeyValue[1];
                            queryParams[keyValue_2] = ckeyValue[2];
                        }else if(ckey[0]==ckey[2] && ckey[0] != ckey[1] && ckey[1] != ckey[2]){
                            keyValue = ckey[0];
                            keyValue_2 = ckey[1];
                            queryParams[keyValue] = ckeyValue[0]+'###'+ckeyValue[2];
                            queryParams[keyValue_2] = ckeyValue[1];
                        }else if(ckey[1]==ckey[2] && ckey[0] != ckey[1] && ckey[0] != ckey[2]){
                            keyValue = ckey[1];
                            keyValue_2 = ckey[0];
                            queryParams[keyValue] = ckeyValue[1]+'###'+ckeyValue[2];
                            queryParams[keyValue_2] = ckeyValue[0];
                        }else{
                            keyValue = ckey[0];
                            queryParams[keyValue] = ckeyValue[0]+'###'+ckeyValue[1]+'###'+ckeyValue[2];
                        }
                    }else{
                        queryParams = $.extend(queryParams,searchArr[i]);
                    }
                }
                /*if(!Object.keys(queryParams).length <=1 && keyValue.length !=0){
                    queryParams = {};
                    queryParams[keyValue] = value;
                }*/
            }else{
                var selectOp = $(".selectC option:selected").attr("name");
                var selectVal;
                if($(".searchText").css("display")=="block"){
                    selectVal = $(".searchText").val();
                } else {
                    $(".selectV").each(function(){
                        if($(this).css("display")=="block"){
                            selectVal = $(this).val();
                        }
                    });
                }
                if(selectVal != ""){
                    queryParams[selectOp] = selectVal;
                }
            }
            var pageParams = {
                'pageNo': 1,
                'pageSize': this.config.pageSize,
                'orderBy': this.config.orderBy,
                'order': this.config.asc ? 'ASC' : 'DESC'
            };
            if(!params){
                params = {};
            }
            //合并查询参数，后面的覆盖前在的
            var targetParams = {};
            $.extend(targetParams,queryParams, params, pageParams);
            return targetParams;
        }
    },
    pageExport:function(exportUrl,formId,baseUrl,fileName,searchArr,params){
        var _self = this;
        if(!fileName){
            fileName = '';
        }
        var params = this.getPageQueryParam(formId,searchArr,params);
        params['orderBy'] = this.config.keyField;
        params['order'] = 'DESC';
        params['fileName'] = fileName;
        var columnsTemp = [];
        if(this.config.columns){
            for(var s in this.config.columns){
                var columnT = this.config.columns[s];
                columnT['formatter'] = '';
                columnsTemp.push(columnT);
            }
        }
        params['columns'] = columnsTemp;
        _self.showCover("正在努力为您导出，请稍候...");
        $.ajax({
            url: exportUrl,
            datatype: 'json',
            type: "Post",
            data:params,
            async:false,
            error:function(){
                _self.removeCover();
            },
            success: function (data) {
                _self.removeCover();
                if(data && data.flag=='success'){
                    window.location.href = baseUrl + data.message.replace(/\\/g,"/");
                }else{
                    new alertdialog({title:"提示",content:data.message});
                }

            }
        });
    },
    //查询 v2.0 lzh
    pageQuery_2 : function(formId,searchArr,params){
        if(formId && $("#"+formId)){
            var targetParams = this.getPageQueryParam(formId,searchArr,params);
            this.pagingByParams(targetParams);
        }
    },
    //根据已有配置显示分页
    pagingByParams :function(params){
        this.config.params = params;
        this.paging(this.config.tarId,this.config.keyField,this.config.columns,this.config.dataUrl);
    },
    //刷新列表
    pageRefresh :function(){
        var ids = this.getSelectedRowIds();
        if(ids.length==1){
            this.config.selectKeyId = ids[0];
        }
        this.paging(this.config.tarId,this.config.keyField,this.config.columns,this.config.dataUrl);
    },
    // 分页
    paging : function (tarId,keyField,columns,dataUrl,dataParams){
        var self = this;
        var tbBodyId = tarId + '_table_body';
        var tbId = tarId + '_table' + new Date().getTime();
        var tarTbDivId = tarId + '_div';
        var tbHtml = [];
        if(!self.config.noShowNumPerPage){
            //$("#"+tarId).css("border","solid 1px rgb(225, 225, 225)");
            tbHtml.push('<div id="'+tarTbDivId+'" style="width:100%;/*overflow-x: auto;min-height: 350px;*/">');
        }else{
            tbHtml.push('<div id="'+tarTbDivId+'" style="width:100%;/*overflow-x: auto;min-height: 235px;*/">');
        }

        tbHtml.push('<table id="'+tbId+'" class="table table-striped  table-hover dataTable">');
        tbHtml.push('<thead>');
        tbHtml.push('<tr>');
        if(!self.config.isNotShowCheckbox){
            tbHtml.push('<th  style="text-align: center;width:35px;" class="id"> ');
            tbHtml.push('<input style="zoom: 150%;margin-left: 3px;" type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" /> ');
            tbHtml.push('</th> ');
        }
        for(var k in columns){
            var colT = columns[k];
            var title = '';
            var width = '';
            var field = "";
            var isHide = "";
            if(colT){
                title = colT.title;
                if(colT.width){
                    if(self.isWidthFormatByPercent()){//判断列宽是否用真实宽度
                        width = ' width:'+colT.width+'%; ';
                    }else{
                        width = ' min-width:'+colT.width+'px;max-width:'+colT.width+'px;width:'+colT.width+'px; ';
                    }

                }
                field = colT.field;
                if(colT.isHide){
                    isHide = 'display:none;';
                }
            }


            tbHtml.push('<th style="'+isHide+width+';line-height:20px;font-size: 14px;"  class="sorting" name="a.'+field+'">'+title+'</th>');
        }
        tbHtml.push('</tr>');
        tbHtml.push('</thead>');
        tbHtml.push('<tbody id="'+tbBodyId+'">');
        tbHtml.push('</tbody>');
        tbHtml.push('</table>');
        tbHtml.push('</div>');
        var paramData = {};
        if(this.config && this.config.params){
            paramData = this.config.params;
        }
        paramData['orderBy'] = keyField;
        paramData['order'] = 'DESC';
        var targetParams = {};
        if(!dataParams){
            dataParams = {};
        }
        $.extend(targetParams,paramData,dataParams);
        if(!self.config.showMask){
            self.showCover('正在努力为您加载，请稍候...');
        }
        $.ajax({
            url: dataUrl,
            datatype: 'json',
            type: "Post",
            data:targetParams,
            error:function(){
                self.removeCover();
            },
            success: function (data) {
                self.removeCover();
                $("#"+tarId).html("");
                $("#"+tarId).append(tbHtml.join(""));
                if (data != null) {
                    var tbBodyHtml = [];
                    if(data.result && data.result.length>0){
                        var dataArr = data.result;
                        if(self.config.indexDataNum){dataArr = data.result.splice(0,self.config.indexDataNum-1);};
                        $.each(dataArr, function (index, item) { //遍历返回的json
                            var keyVal = item[keyField];
                            var isChecked = '';
                            if(self.config.selectKeyId == keyVal){
                                isChecked = 'checked';
                            }
                            tbBodyHtml.push('<tr '+self.getRowClickFun(self.config,item)+'>');
                            if(!self.config.isNotShowCheckbox){
                                tbBodyHtml.push('<td style="text-align: center;" class="id"> ');
                                tbBodyHtml.push('<input style="zoom: 150%;" type="checkbox" '+isChecked+' name="checkdel" class="selectedItem a-check"  value="'+keyVal+'"/> ');
                                tbBodyHtml.push('</td> ');
                            }
                            for(var k in columns){
                                var colT = columns[k];
                                var field = colT.field;
                                var valueT = item[field];
                                var idT = keyVal + "_" + field;

                                if(idT.indexOf(".")>=0){
                                    idT = idT.replace(".","_");
                                }
                                var width = '';
                                if(colT.width){
                                    if(self.isWidthFormatByPercent()){//判断列宽是否用真实宽度
                                        width = ' width:'+colT.width+'%; ';
                                    }else{
                                        width = ' min-width:'+colT.width+'px;max-width:'+colT.width+'px;width:'+colT.width+'px; ';
                                    }

                                }
                                //处理对象中包括对象的情况
                                if(field && field.indexOf(".")>0){
                                    var fieTs = field.split(".");
                                    var fieldlength = fieTs.length;
                                    var vObjTemp = item;
                                    for(var s=0;s<fieTs.length;s++){
                                        var st = s + 2;
                                        if(st<=fieldlength && vObjTemp){
                                            vObjTemp = vObjTemp[fieTs[s]];
                                        }else{
                                            //vObjTemp = vObjTemp[fieTs[s]];
                                            if(vObjTemp){
                                                valueT = vObjTemp[fieTs[s]];
                                            }
                                        }
                                    }

                                }
                                if(valueT==undefined){
                                    valueT="";
                                }
                                var valBeforeFormat = valueT;
                                if(colT.formatter){
                                    valueT = colT.formatter(valueT,item);
                                }
                                var isHide = "";
                                if(colT.isHide){
                                    isHide = 'style="display:none;"';
                                }
                                tbBodyHtml.push('<td '+isHide+' ondblclick="pageGrid_showAllContent(this)" ');
                                tbBodyHtml.push(' style="text-align: left;white-space: nowrap;line-height: 29px;text-overflow: ellipsis; overflow: hidden;'+width+'" ');
                                tbBodyHtml.push(' title="'+valBeforeFormat+'" id="'+idT+'" value="'+valBeforeFormat+'">' + valueT + '</td>');
                            }
                            tbBodyHtml.push('</tr>');
                        });
                        $("#"+tbBodyId).append(tbBodyHtml.join(" "));
                    }else{
                        //处理没数据的情况
                        $("#"+tarTbDivId).append('<div align="center" style="font-weight: bold;padding: 30px;border:1px solid #ddd;border-top:0px">暂无数据！</div>');
                    }

                    /*增加列宽拖动*/
                    if(!self.config.noLiveDrag){
                        $("#"+tbId).colResizable({
                            liveDrag:true,
                            gripInnerHtml:"<div class='grip'></div>",
                            draggingClass:"dragging",
                            resizeMode:'overflow'
                        });
                    }

                    /*构建分页菜单 */
                    if(!self.config.indexDataNum){
                        /*分页栏位置是否放在最底部*/
                        var paginationPosition = '';
                        if(!self.config.isNoPaginationBottom){
                            //paginationPosition = 'position: fixed;';
                        }
                        var totalHtml = ' 共'+ data.totalCount + '条记录 ';
                        var pagiHtml = [];
                        pagiHtml.push('<div class="form-actions fluid" style="margin-top: 0px;'+paginationPosition+'width: 100%;bottom: 0;padding:10px;border-top:1px solid #ddd;right:0px;padding-right:20px;">');
                        pagiHtml.push('<div class="col-xs-5" style="line-height:34px;">  ');
                        var changePageSizeId = tarId + "_changePageSize"
                        if(!self.config.noShowNumPerPage){
                            pagiHtml.push('每页显示');
                            pagiHtml.push('<select id="'+changePageSizeId+'" class="m-wrap xsmall">');
                            pagiHtml.push('<option value="10">10</option> ');
                            pagiHtml.push('<option value="20">20</option>');
                            pagiHtml.push('<option value="50">50</option>');
                            pagiHtml.push('</select>');
                            pagiHtml.push('条 &nbsp;');
                        }
                        pagiHtml.push(totalHtml);
                        pagiHtml.push('</div>');
                        pagiHtml.push('<div class="col-xs-7">');
                        pagiHtml.push('<div class="dataTables_paginate paging_bootstrap  pull-right">  ');
                        pagiHtml.push('<button class="btn btn-sm default">&lt;</button>');
                        pagiHtml.push('<button class="btn btn-sm default">1</button>');
                        pagiHtml.push(' <button class="btn btn-sm default">&gt;</button>');
                        pagiHtml.push('</div></div></div>');
                        $("#"+tarId).append(pagiHtml.join(""));
                    }
                    var pageNo = (data.pageNo)?data.pageNo:1;
                    var pageSize = (data.pageSize)?data.pageSize:0;
                    var totalCount = (data.totalCount)?data.totalCount:0;
                    var resultSize = (data.resultSize)?data.resultSize:0;
                    var pageCount = (data.pageCount)?data.pageCount:0;
                    var orderBy = (data.orderBy)?data.orderBy:0;
                    var order = (data.order)?data.order:0;
                    $.extend(paramData,dataParams);
                    var config = {
                        tarId : tarId,
                        tbId : tbId,
                        keyField : keyField,
                        columns : columns,
                        dataUrl : dataUrl,
                        id: tbId,
                        pageNo: pageNo,
                        pageSize: pageSize,
                        totalCount: totalCount,
                        resultSize: resultSize,
                        pageCount: pageCount,
                        orderBy: orderBy,
                        order: order,
                        params: paramData,
                        selectedItemClass: 'selectedItem',
                        gridFormId: 'form1'
                    };
                    //合并查询参数，后面的覆盖前在的
                    var targetConfig = {};
                    $.extend(targetConfig, self.config,config);
                    self.init(targetConfig);
                    self.configPagination('.dataTables_paginate');
//	                self.configPageInfo('.dataTables_info');
                    self.configPageSize('#'+changePageSizeId);
                }
            }
        });
    },
    //获取选中的列
    getSelectedRowIds : function(){
        var ids = [];
        var tbId = this.config.tbId;
        var formData = $('#'+tbId+' input:checkbox[name=checkdel]:checked').serializeArray();
        $.each(formData, function () {
            ids.push(this.value);
        });
        return ids;
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
        modalHtml.push('<h4 class="modal-title" style="color:#0af;text-align:center;">'+title+'</h4>');
        modalHtml.push('</div>');
        modalHtml.push('<div class="modal-body">');
        modalHtml.push('<iframe id="'+iframeId+'" name="bb" src="'+url+'" width="'+iframeWidth+'" height="'+height+'" scrolling="auto" frameborder="0"></iframe>');
        modalHtml.push('</div></div></div></div>');
        $(document.body).append(modalHtml.join(""));
        $('#'+modalId).modal('show');
    },
    getValByKeyAndField: function(keyVal,field){ // 通过key值和field字段获取对应列的值
        var tarId = this.config.tarId + " #" + keyVal + "_" + field;
        if("#"+ tarId){
            return $("#"+ tarId).attr("value");
        }else{
            return '';
        }
    },
    buildUrl: function (params) {
        var url = location.pathname;

        var separator = url.indexOf('?') == -1 ? '?' : '&';
        for (var key in params) {
            var value = params[key];
            if (typeof value == 'undefined' || value == null || value == '') {
                continue;
            }
            url += separator + key + '=' + encodeURIComponent(value);
            if (separator == '?') {
                separator = '&';
            }
        }
        return url;
    },

    configPagination: function(expr) {
        var self = this;
        $(expr).boostrapPagination(this.config.totalCount, {
            callback: function(pageIndex, jq) {
                self.changePageNo(pageIndex + 1);
            }, // 点击页数执行的回调函数
            current_page: this.config.pageNo - 1, // 当前页码
            items_per_page: this.config.pageSize //每页显示几项
        });
    },

    configPageInfo: function(expr) {
        var start = (this.config.pageNo - 1) * this.config.pageSize + 1;
        var end = start + this.config.resultSize - 1;
        // var html = "共" + this.config.totalCount + "条记录 显示" + start + "到" + end + '条记录';
        var html = '';
        if (this.config.totalCount == 0) {
            html = this.messages['page.empty'];
        } else {
            html = this.messages['page.info'].call(this, this.config.totalCount, start, end);
        }
        $(expr).html(html);
    },

    configPageSize: function(expr) {
        var self = this;

        $(expr).val(this.config.pageSize);

        $(expr).bind('change', function() {
            self.changePageSize($(expr).val());
        });
    },

    removeAll: function() {
        var len = $('.' + this.config.selectedItemClass + ':checked').length;
        if (len == 0) {
            $.showMessage(this.messages['select.record'], {
                position: 'top',
                size: '36',
                fontSize: '20px'
            });
            return false;
        }
        if (confirm(this.messages['confirm.delete'])) {
            $('#' + this.config.gridFormId).submit();
            return true;
        } else {
            return false;
        }
    },

    exportExcel: function() {
        var url = this.config.exportUrl;

        var params = {
            'pageNo': this.config.pageNo,
            'pageSize': this.config.pageSize,
            'orderBy': this.config.orderBy,
            'order': this.config.asc ? 'ASC' : 'DESC'
        };

        var targetParams = {};
        $.extend(targetParams, this.config.params, params);

        var separator = url.indexOf('?') == -1 ? '?' : '&';
        for (var key in params) {
            var value = params[key];
            if (typeof value == 'undefined' || value == null || value == '') {
                continue;
            }
            url += separator + key + '=' + encodeURIComponent(value);
            if (separator == '?') {
                separator = '&';
            }
        }
        location.href = url;
    },

    messages: {
        'page.info': function() {
            return '共' + arguments[0] + "条记录 显示" + arguments[1] + "到" + arguments[2] + '条记录';
        },
        'page.empty': '没有数据',
        'select.record': '请选择需要删除的记录',
        'confirm.delete': '确认要删除选择的记录吗？'
    },
    formatDate :function(now){
        if(!now){
            return '';
        }
        var year=new Date(now).getFullYear();
        var month=new Date(now).getMonth()+1;
        if(month<10){
            month="0"+month;
        }
        var date=new Date(now).getDate();
        if(date<10){
            date="0"+date;
        }
        var hour=new Date(now).getHours();
        if(hour<10){
            hour="0"+hour;
        }
        var minute=new Date(now).getMinutes();
        if(minute<10){
            minute="0"+minute;
        }
        var second=new Date(now).getSeconds();
        if(second<10){
            second="0"+second;
        }
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
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

function pageGrid_showAllContent(tar){
    if($(tar)){
        $(tar).css("white-space","");
    }
}
//列表选中操作
function toggleSelectedItems(isChecked) {
    $(".selectedItem").each(function(index, el) {
        $(el).prop("checked", isChecked);
        if ($(el).parent()[0].tagName != 'SPAN' && $(el).parent()[0].tagName != 'span') {
            return;
        }
        if (isChecked) {
            $(el).parent().addClass("checked");
        } else {
            $(el).parent().removeClass("checked");
        }
    });
}
function toggleSelectedItems2(isChecked) {
    $(".selectedItem2").each(function(index, el) {
        $(el).prop("checked", isChecked);
        if ($(el).parent()[0].tagName != 'SPAN' && $(el).parent()[0].tagName != 'span') {
            return;
        }
        if (isChecked) {
            $(el).parent().addClass("checked");
        } else {
            $(el).parent().removeClass("checked");
        }
    });
}
