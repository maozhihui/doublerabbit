/**
 * 多条件查询 lzh
 */
/* ------------ */
ConditionQuery = function(config) {
    this.init(config);
};

ConditionQuery.prototype = {
    search_arr : [],
    pageGrid : new PageGrid({}),

    init: function(config) {
        this.config = config;
        this.conditionLKeyup();
    },
    getSearchArr: function (){
        return this.search_arr;
    },
    queryWay : function(){
        if($(".add-search-div").size()!=0){
            pageGrid.pageQuery_2("searchForm",this.getSearchArr());
        }else{
            pageGrid.pageQuery_2("searchForm");
        }
        return;
    },
    conditionLKeyup : function(){
        $(".searchText").bind("keypress",function(e){
            var keycode = document.all?event.keyCode:e.which;
            if(keycode == 13){
                //$("#queryBtn").click();
                try{
                    document.getElementById("queryBtn").click();
                    return false;
                }catch(error){
                    new alertdialog({title:"提示",content:error});
                    return false;
                }

            }
        });
    },
    addDiv: function(){

        var optionName = this.getOptionName();
        var optionValue = this.getOptionValue();
        var searchValue;
        var searchName;
        if($(".searchText").css("display")=="block"){
            searchValue = this.getSearchValue();
            searchName = this.getSearchValue();
        } else {
            searchValue = this.getSearchValue_2();
            searchName = this.getSearchText_2();
        }
        if(optionValue =="" || searchValue ==""){
            new alertdialog({title:"提示",content:"查询内容为空！"});
            return false;
        }
        if($(".add-search-div").size() >=3){
            new alertdialog({title:"提示",content:"查询条件不能超过3个！"});
            return false;
        }

        /*for(var i in this.search_arr){
            for(var key in search_arr[i]){
                if(optionName == key)
                    new alertdialog({title:"提示",content:"已存在该查询条件，请选择其它查询条件！"});
                    return;
            }
        }*/

        var conDiv = "<div class='add-search-div' title='"+searchValue+"'><span class='search-div-span'>"+optionValue+"&nbsp:&nbsp"+searchName+
            "</span><i class='fa fa-times-circle' style='cursor:pointer;vertical-align: top;' onclick='conditionQuery.deleteDiv(this);'></i>"+"</div>";
        $(".search_area").append(conDiv);

        var map = {};
        map[optionName] = searchValue;
        this.search_arr.push(map);
        /* search_arr[optionName] = searchValue; */
    },
    getOptionName: function(){
        var optionName = $(".selectC option:selected").attr("name");
        return optionName;
    },
    getOptionValue: function(){
        var optionValue = $(".selectC option:selected").val();
        return optionValue;
    },
    getSearchValue: function(){
        var searchValue = $(".searchText").val();
        return searchValue;
    },
    getSearchValue_2: function(){
        var searchValue;
        $(".selectV").each(function(){
            if($(this).css("display")=="block"){
                searchValue = $(this).val();
            }
        });
        return searchValue;
    },
    getSearchText_2: function(){
        var searchText;
        $(".selectV").each(function(){
            if($(this).css("display")=="block"){
                searchText = $(this).find("option:selected").text();
            }
        });
        return searchText;
    },
    /* ------------ */
    deleteDiv: function(i){
        var this_i = i;
        var index = $(".add-search-div").index($(this_i).parent("div"));
        this.search_arr.splice(index,1);
        this_i.parentNode.remove();
    }
}