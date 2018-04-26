<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/include.jsp"%>
<link href="${ctx}/css/product_base.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.product_ul,
.product_ul li{
	margin:0px;
	padding:0px;
}
.product_ul li{
	margin-left:2%;
	margin-bottom:25px;
	float:left;
	width:30%;
	cursor:pointer;
}
.product_ul li:hover{
	background-color: #948484;
}
.product_ul li:nth-child(3n+1){
	margin-left:3%;
}
.product_ul .list_title{
	text-align:left;
	padding:10px 15px;
	background: #28d7f5;
    color: white;
    font-weight: bold;
}
.list_category{
	float: right;
	background:-moz-linear-gradient(left,#fff 0%,#866A6A 100%);
	background:-webkit-linear-gradient(left,#fff 0%,#866A6A 100%);
    background: linear-gradient(to right, #fff, #866A6A);
    padding: 4px 7px;
    color: rgba(56, 25, 25, 0.54);
	-webkit-border-radius: 14px 0px 14px;
	-moz-border-radius: 14px 0px 14px;
	-ms-border-radius: 14px 0px 14px;
    border-radius: 14px 0px 14px;
	font-size:13px;
} 
.product_ul .list_content{
	padding:20px 15px 0px;
	border:1px solid #ddd;
	background:-moz-linear-gradient(top,#fff 0%,rgba(175, 163, 163, 0.49) 100%);
	background:-webkit-linear-gradient(top,#fff 0%,rgba(175, 163, 163, 0.49) 100%);
	background: linear-gradient(to bottom, #fff, rgba(175, 163, 163, 0.49));
}
.list_content > p:first-child{
	color:#4e4d4d;
}

.list_bottom{
	text-align:right;
	margin-top:15px;
	margin-bottom:10px;
}
.list_bottom button{
	padding: 3px 10px;
	cursor:pointer;
	background-color: #00c7ff;
    border-radius: 3px;
    border-color: #00c7ff;
    color: white;
}
.query_item button{
	padding: 8px 15px;
    background-color: blue;
    color: white;
    border-radius: 3px;
}
.list_time{
	color: #ad8787; 
}
</style>
</head>

<body class="page-header-fixed"> 
	<div class="page-container">
		<div class="page-heading">
				<h3 class="panel-title">产品总览</h3>
		</div>
		<div class="page-content">
			<div class="list_search_head">
				<form id="searchForm" name="searchForm" action="" method="post" class="form-horizontal" >
					<div class="list_search_head_left">
					 <a href="javascript:add_product();" id="add_product" class="button expand fr  create j_create i"><i class="icon-product-add"></i>创建产品</a>
						<div class="search_area"></div>
						<div>
							<label class="search_head control-label">查询条件：</label>
						</div>
						<div class="">
							<select class="form-control selectC" name="selectC">
								<option name="name" value="名称">名称</option>
								<option name="additionalInfo" value="备注">备注</option>
							</select>
						</div>
						<div class="">
							<select class="form-control" style="display:none;">
								<option value="1">包含</option>
								<option value="2">等于</option>
							</select>
						</div>
						<div class="">
							<input style="width: 135px;" type="text" name="searchText" class="form-control searchText" placeholder="查询内容">
						</div>
						<div>
							<i class="fa fa-plus-circle" style="color: #4C99E4;cursor: pointer;height: 30px;width: 15px;line-height: 30px;" onclick="conditionQuery.addDiv();"></i>
						</div>
						<div class="">
							<a href="javascript:" id="queryBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_search"></i>
								<span class="search_head_span">查询</span>
							</a>
							<!--  <a href="javascript:" id="exportBtn" class="btn search_head_searchBtn"  >
								<i class="search_head_export"></i>
								<span class="search_head_span">导出</span>
							</a>-->
						</div>
					</div>
						
					<!-- <div class="list_search_head_right">
						<a href="javascript:add_plugin();" id="addNeInfo" class="btn btn-sm search_a_add"> 
							<i class="search_head_add"></i> 
							<span class="search_head_span">新建</span>
						</a>
						<a href="javascript:view_plugin()"  id="checkNeInfo" class="btn btn-sm search_a_check"  >
							<i class="search_head_check"></i>
							<span class="search_head_span">查看</span>
						</a>
						<a href="javascript:edit_plugin();" class="btn btn-sm search_a_modify"> 
							<i class="search_head_modify"></i> 
							<span class="search_head_span">修改</span>
						</a> 
						<a href="javascript:del_plugin()" class="btn btn-sm search_a_delete"> 
							<i class="search_head_delete"></i> 
							<span class="search_head_span">删除</span>
						</a>
					</div> -->
					<div style="clear: both;"></div>
				</form>
			</div>
			<ul style="list-style:none;width:100%;margin-top:40px;" class="product_ul">
				<li>
					<div class="list_title">产品一<span class="list_category">互联网</span></div>
					<div class="list_content">
						<p>接入方式：http</p>
						<p class="list_time">创建时间：20140412</p>
						<div class="list_bottom">
							<button type="button">修改</button>
							<button type="button">删除</button>
						</div>
					</div>
				</li>
				<li>
					<div class="list_title">产品一<span class="list_category">互联网</span></div>
					<div class="list_content">
						<p>接入方式：http</p>
						<p class="list_time">创建时间：20140412</p>
						<div class="list_bottom">
							<button type="button">修改</button>
							<button type="button">删除</button>
						</div>
					</div>
				</li>
				<li>
					<div class="list_title">产品一<span class="list_category">互联网</span></div>
					<div class="list_content">
						<p>接入方式：http</p>
						<p class="list_time">创建时间：20140412</p>
						<div class="list_bottom">
							<button type="button">修改</button>
							<button type="button">删除</button>
						</div>
					</div>
				</li>
				<li>
					<div class="list_title">产品一<span class="list_category">互联网</span></div>
					<div class="list_content">
						<p>接入方式：http</p>
						<p class="list_time">创建时间：20140412</p>
						<div class="list_bottom">
							<button type="button">修改</button>
							<button type="button">删除</button>
						</div>
					</div>
				</li>
				<li>
					<div class="list_title">产品一<span class="list_category">互联网</span></div>
					<div class="list_content">
						<p>接入方式：http</p>
						<p class="list_time">创建时间：20140412</p>
						<div class="list_bottom">
							<button type="button">修改</button>
							<button type="button">删除</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</body>
<script type="text/javascript">
var conditionQuery = new ConditionQuery({});
var productPageGrid = new PageGrid({});
function add_product(){
	productPageGrid.createModal("新建产品","${ctx}/product/product_edit",450,600);
}
</script>
</html>
