<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>专题商品管理</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		专题商品管理
	</h3>
	<div>
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>商品名</th>
					<th>进补描述</th>
					<th>价格</th>
					<th>规格</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityList}">
						<c:forEach items="${commodityList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>${list.id }</td>
								<td>${list.commodityName }</td>
								<td>${list.description2 }</td>
								<td>￥${list.price }</td>
								<td>${list.weight }</td>
								<td>${list.sort }</td>
								<td>
									<a href="/hajSpecialTopicCommodity/edit.html?id=${list.id }">修改</a>&nbsp;|&nbsp;
									<a href="javascript:void(0);" data-id="${list.id }" class="btn_del">从专题中删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="7">此专题中暂无商品，可在下方搜索添加</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">${vo.page.pageStr }</div>
</div>

<!-- 搜索商品添加到专区 -->
<div class="mainBox">
	<h3>搜索商品添加到专区</h3>
	<form id="searchForm" onsubmit="return false;">
		<div class="filter">
			<select name="shelves" id="shelves" style="vertical-align: middle; margin-right: 15px;">
				<option value="">上/下架</option>
				<option value="1" <c:if test="${vo.shelves==1}">selected</c:if>>上架</option>
				<option value="0" <c:if test="${vo.shelves==0}">selected</c:if>>下架</option>
			</select>

			<select name="commodityAttr" id="commodityAttr" style="vertical-align: middle;">
				<option value="">商品属性</option>
				<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
				<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
				<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
				<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
			</select>
			<select name="parentTypeId" id="parentTypeId" style="vertical-align: middle;">
				<option value="0">大类</option>
				<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }"
							<c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="vertical-align: middle; margin-right: 15px;">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }"
							<c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"/>
			<input class="btn" type="button" id="btn_search" value="搜索"/>
		</div>
	</form>
	<div>
		<form>
			<table style="width: 70%;" class="tableBasic list" id="searchCommodityForm">
				<tr>
					<th>序号</th>
					<th>商品名</th>
					<th>进补描述</th>
					<th>促销价</th>
					<th>汇爱家价</th>
					<th>规格</th>
					<th>上下架</th>
					<th>操作</th>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function () {

		$('#btn_search').click(function () {
			$.ajax({
				url: '/commodity/getCommodityListWithoutPage.html',
				type: 'POST',
				data: $('#searchForm').serialize(),
				cache: false,
				success: function (resultMap) {
					var commodityList = resultMap.commodityList;
					var htmlStr;

					// 先组装head
					htmlStr += '<tr>';
					htmlStr += '	<th>序号</th>';
					htmlStr += '	<th>商品名</th>';
					htmlStr += '	<th>进补描述</th>';
					htmlStr += '	<th>促销价</th>';
					htmlStr += '	<th>汇爱家价</th>';
					htmlStr += '	<th>规格</th>';
					htmlStr += '	<th>上下架</th>';
					htmlStr += '	<th>操作</th>';
					htmlStr += '</tr>';
					$('#searchCommodityForm').empty();
					$('#searchCommodityForm').append(htmlStr);

					// table body部分
					if (commodityList) {
						var putOn = '<span class="text_green">上架</span>';
						var putOff = '已下架';
						for (var i = 0, len = commodityList.length; i < len; i++) {
							htmlStr = '';
							htmlStr += '<tr class="main_info">';
							htmlStr += '<td>' + (i + 1) + '</td>';
							htmlStr += '<td>' + commodityList[i].commodityName + '</td>';
							htmlStr += '<td>' + commodityList[i].description2 + '</td>';
							htmlStr += '<td>￥' + commodityList[i].promotionPrice + '</td>';
							htmlStr += '<td>￥' + commodityList[i].originalPrice + '</td>';
							htmlStr += '<td>' + commodityList[i].weight + '</td>';
							htmlStr += '<td>' + (commodityList[i].shelves == 1 ? putOn : putOff) + '</td>';
							htmlStr += '<td>';
							htmlStr += '	<a href="javascript:void(0);" ';
							htmlStr += '		data-id="' + commodityList[i].id + '"';
							htmlStr += '		onclick="add2topic(this)">添加到专题</a>';
							htmlStr += '</td>';
							htmlStr += '</tr>';

							$('#searchCommodityForm').append(htmlStr);
						}
					}

					// 重新定义iframe高度
					window.parent.document.getElementById('mainFrame').setAttribute('height', document.body.scrollHeight + 'px');
				},
				error: function () {
					alert('未知异常');
				}
			});
		});

		$('.btn_del').click(function () {
			if (confirm('真的从此专题中删掉这个商品吗？')) {
				$.ajax({
					url: '/hajSpecialTopicCommodity/delete.html?id=' + $(this).attr('data-id'),
					type: 'GET',
					cache: false,
					success: function (result) {
						if (result == 'success') {
							alert('删除成功');
							window.location.reload();
						}
					},
					error: function () {
						alert('未知异常');
					}
				});
			}
		});

		$('#commodityNo').keypress(function (e) {
			if (e.keyCode == 13) {
				$('#btn_search').click();
				return false;
			}
		});
	});

	function add2topic(obj) {
		if (confirm('确定要添加此商品？')) {
			$.ajax({
				url: '/hajSpecialTopicCommodity/save.html',
				type: 'POST',
				data: {'specialTopicId': '${specialTopicId}', 'commodityId': obj.getAttribute('data-id')},
				cache: false,
				success: function (result) {
					if (result == 'success') {
						alert('添加成功');
						window.location.reload();
					}
				},
				error: function () {
					alert('未知异常');
				}
			});
		}
	}

</script>
</body>
</html>
