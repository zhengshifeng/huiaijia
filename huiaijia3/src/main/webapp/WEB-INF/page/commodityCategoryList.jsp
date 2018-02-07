<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品类目运营</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/categoryThree/list.html" class="actionBtn" style="margin-left: 10px;">类目层级管理</a>
		<a href="/categoryThree/findCategoryById.html?&oneId=0&twoId=0&threeId=0" class="actionBtn" style="margin-left: 10px;">进入类目管理</a>
		商品类目运营
	</h3>
	<form action="/commodity/ccList.html" method="post" name="searchForm" id="searchForm" >
		<div class="filter">
			<strong>商品分类:</strong>
			<select name="commodityAttr" id="commodityAttr" style="width: 150px">
				<option value="">属性</option>
				<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
				<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
				<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
				<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
			</select>
			<select name="parentTypeId" id="parentTypeId" style="width: 150px" >
				<option value="0">大类</option>
				<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="width: 150px">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<strong>商品品牌: </strong>
			<select name="brandId" id="brandId" style="width: 150px">
				<option value="">--请选择--</option>
				<c:forEach items="${brandList}" var="list">
					<option value="${list.id }" <c:if test="${vo.brandId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;&nbsp;
			<select id="onec" name="onec" onchange="getCate(this.value,'twoc')" style="width: 150px" >
				<option value="-1">一级类目</option>
			</select>&nbsp;&nbsp;&nbsp;
			<select id="twoc" name="twoc" onclick="getCate(this.value,'threec')" style="width: 150px" >
				<option value="-1">二级类目</option>
				<c:if test="${not empty twoList}">
					<c:forEach items="${twoList}" var="list">
						<option value="${list.id }" <c:if test="${vo.twoc==list.id}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</c:if>

			</select>&nbsp;&nbsp;&nbsp;
			<select id="threec" name="threec"  style="width: 150px" >
				<option value="">三级类目</option>
				<c:if test="${not empty threeList}">
					<c:forEach items="${threeList}" var="list">
						<option value="${list.id }" <c:if test="${vo.threec==list.id}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</c:if>
			</select>

		</div>
		<div class="filter">
			<strong>供 应 商:</strong>
			<input list="supplyChain" name="supplyChain" class="inpMain" placeholder="供应商" value="${vo.supplyChain}">
			<datalist id="supplyChain" class="selectBox">
				<c:forEach items="${supplyChainNames}" var="list">
				<option value="${list.name }">
					</c:forEach>
			</datalist>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<strong>是否加入类目:</strong>
			<select name="isaddcate" id="isaddcate" style="width: 150px">
				<option value="" style="text-align: center">--请选择--</option>
				<option value="1" <c:if test="${vo.isaddcate==1}">selected</c:if>>已加入类目</option>
				<option value="0" <c:if test="${vo.isaddcate==0}">selected</c:if>>未加入类目</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<strong>商品搜索：</strong>
			<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"
				   value="${vo.commodityNo }" />
			&nbsp;&nbsp;&nbsp;
			<input class="btn" type="submit" value="筛选" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm" style="text-align:center">
			<table style="width: 100%;" class="tableBasic list">
				<tr class="main_head">
					<th>
						<label>
							<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />
							序号
						</label>
					</th>
					<th>属性</th>
					<th>大类</th>
					<th>小类</th>
					<th>商品名称</th>
					<th>商品别名</th>
					<th>品牌</th>
					<th>规格</th>
					<th>上下架</th>
					<th>是否作废</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityList}">
						<c:forEach items="${commodityList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>
									<label>
										<input type="checkbox" name="commodityId" value="${list.id }"/>${vs.index + 1 }
									</label>
								</td>
								<td>${list.commodityAttr }</td>
								<td>${list.parentTypeName }</td>
								<td>${list.typeName }</td>
								<td>${list.commodityName}</td>
								<td>${list.alias}</td>
								<td>${list.brand}</td>
								<td>${list.weight }</td>
								<td>
									<c:choose>
										<c:when test="${list.shelves == 1 }"><span class="text_green">上架</span></c:when>
										<c:when test="${list.shelves == 0 }"><span class="text_invalid">下架</span></c:when>
										<c:otherwise>未知</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.invalid == 1 }"><span class="text_red">是</span></c:when>
										<c:when test="${list.invalid == 0 }">否</c:when>
										<c:otherwise>未知</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="21" style="text-align: center">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<select id="one" name="one" onchange="getCate(this.value,'two')" style="width: 150px">
				<option value="-1">一级类目</option>
			</select>&nbsp;&nbsp;&nbsp;
			<select id="two" name="two" onchange="getCate(this.value,'three')" style="width: 150px">
				<option value="-1">二级类目</option>
			</select>&nbsp;&nbsp;&nbsp;
			<select id="three" name="three"  style="width: 150px">
				<option value="">三级类目</option>
			</select>&nbsp;&nbsp;&nbsp;
			<a class="btn" id="btn_add2category" href="javascript:;">添加</a>
			<a class="btn" id="btn_deletecommodity" href="javascript:;" style="background-color: red">移除</a>
		</div>
		<ul>
			<c:if test="${(vo.currentPage-1) == 0}">
				<li class="pageinfo">
					<a href="javascript:search(${vo.currentPage });">第${vo.currentPage }页</a>
				</li>
			</c:if>
			<c:if test="${(vo.currentPage-1) > 0}">
				<li class="pageinfo">
					<a href="javascript:search(${vo.currentPage-1 });">第${vo.currentPage-1 }页</a>
				</li>
			</c:if>
			<li class="current">本页：第${vo.currentPage }页</li>
			<li class="pageinfo">
				<a href="javascript:search(${vo.currentPage+1 });">第${vo.currentPage+1 }页</a>
			</li>
			<li class="pageinfo">
				<input type="text" id="page" style="width: 20px; border: 0px;" />
			</li>
			<li class="pageinfo">
				<a href="javascript:jump();">跳转</a>
			</li>
		</ul>
	</div>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript">

	function sltAllUser() {
		if ($("#sltAll").prop("checked")) {
			$("input[name='commodityId']").prop("checked", true);
		} else {
			$("input[name='commodityId']").prop("checked", false);
		}
	}

	function jump() {
		var jumpPage = $('#page').val();
		if (jumpPage == '') {
			alert("请输入跳转页数!");
			return;
		}
		search(jumpPage);
	}

	function search(page) {
		$("#searchForm").attr("action", "/commodity/ccList.html?currentPage=" + page);
		$("#searchForm").submit();
	}

	$(function(){

		//一级类目 :
		var url = '/categoryThree/getTwoCate.html';
		$.post(url, {parentId:0}, function(data) {
			$.each(data, function(i, value) {
				$("#one").append($("<option value='"+value.id+"'>" + value.name + "</option>"));
				$("#onec").append($("<option value='"+value.id+"'>" + value.name + "</option>"));
			});
			if ("${vo.onec}"!="") {
				$("#onec option").each(function () {
					if ($(this).val() == "${vo.onec}") {
						$(this).attr("selected", true)
					}
				})
			}else{
				$("#twoc option").each(function () {
					if ($(this).val() == "-1") {
						$(this).attr("selected", true)
					}
				})
				$("#threec option").each(function () {
					if ($(this).val() == "") {
						$(this).attr("selected", true)
					}
				})
				$("#two option").each(function () {
					if ($(this).val() == "-1") {
						$(this).attr("selected", true)
					}
				})
				$("#three option").each(function () {
					if ($(this).val() == "") {
						$(this).attr("selected", true)
					}
				})
			}
			}, "json"
		);


		// 批量添加商品到指定的类目中
		$('#btn_add2category').click(function() {
			var threeId = $('#three').val();	//三级类目
			if (threeId == 0 || threeId =='') {
				$("#three").focus();
				alert('请添加到第三级类目中!');
				return false;
			}
			var commodityId = $('#deleteForm').serialize();	//选中的商品id数组
			var len = $("input[name='commodityId']:checked").length;
			if (len > 0) {
				if (threeId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodityCategory/add2category.html?';
					var data = commodityId + '&threeId=' + threeId;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('添加成功!');
							window.location.href = '/commodity/ccList.html?threec='+threeId;
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请先选择商品！');
			}
		});


		// 将指定的商品从类目中移除
		$('#btn_deletecommodity').click(function() {
			var threeId = $('#three').val();	//三级类目
			if (threeId == 0 || threeId =='') {
				$("#three").focus();
				alert('请选择移除出的第三级类目!');
				return false;
			}
			var commodityId = $('#deleteForm').serialize();	//选中的商品id数组
			var len = $("input[name='commodityId']:checked").length;
			if (len > 0) {
				if (commodityId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodityCategory/deleteCommodityToCategory.html?';
					url = url + commodityId + '&threeId=' + threeId;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('移除成功!');
							window.location.href = '/commodity/ccList.html?threec='+threeId;
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请先选择商品！');
			}
		});


		// 将筛选条件存入cookie，以便编辑后返回之前页面并刷新
		$.cookie('haj_commodity_list_params', $('#searchForm').serialize());



	});

//	function getCate(parentId,cateId) {
//		$.post("/categoryThree/getTwoCate.html", {
//			parentId : parentId
//		}, function(data) {
//				$("#"+cateId).empty();
//				$.each(data, function(i, value) {
//					$("#"+cateId).append($("<option value='"+value.id+"'>" + value.name + "</option>"));
//				});
//		}, "json")
//	}


	function getCate(parentId,id){
		$("#"+id+" option:gt(0)").remove();
		if(parentId) {
			$("#loading").show();//显示正在加载提示层
			var loadUrl="/categoryThree/getTwoCate.html";
			var loadData="&parentId="+parentId;
			$.ajax({
				url:loadUrl,
				data:loadData,
				method:'POST',
				dataType:'json',
				success:function(data){
					if(data!=null) {
//						$("#"+id).empty();
						$.each(data, function(i, value) {
							$("#"+id).append($("<option value='"+value.id+"'>" + value.name + "</option>"));
						});
					}
					else {
						$("#"+id).hide();
					}
					$("#loading").hide();
				}
			});
		}
//		else {
//			$("#"+id).hide();		//如果被选的元素已被显示，则隐藏该元素。
//		}
	}



</script>
</body>
</html>
