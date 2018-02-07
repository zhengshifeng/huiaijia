<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品销售管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<%--<input type="button" id="btn_inventory_sync" class="actionBtn" value="同步erp库存" title="同步erp库存" />--%>
		商品销售管理
	</h3>
	<form action="/commodity/inventoryList.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			商品状态：
			<select name="shelves" id="shelves" style="vertical-align: middle; margin-right: 15px;">
				<option value="">上/下架</option>
				<option value="1" <c:if test="${vo.shelves==1}">selected</c:if>>上架</option>
				<option value="0" <c:if test="${vo.shelves==0}">selected</c:if>>下架</option>
			</select>
			<select name="invalid" id="invalid" style="vertical-align: middle; margin-right: 15px;">
				<option value="1" <c:if test="${vo.invalid==1}">selected</c:if>>已作废</option>
				<option value="0" <c:if test="${vo.invalid==0}">selected</c:if>>未作废</option>
			</select>

			商品分类：
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
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="vertical-align: middle; margin-right: 15px;">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>

			供应商：
			<input list="supplyChain" name="supplyChain" class="inpMain" placeholder="供应商" value="${vo.supplyChain}">
			<datalist id="supplyChain" class="selectBox">
				<c:forEach items="${supplyChainNames}" var="list">
				<option value="${list.name }">
				</c:forEach>
			</datalist>
		</div>
		<div class="filter">
			排序方式：
			<select name="orderByClause" id="orderByClause" style="vertical-align: middle;">
				<option value="">无</option>
				<option value="c.inventory DESC">库存从多到少</option>
				<option value="c.inventory ASC">库存从少到多</option>
				<option value="orders.todaySales DESC">销量从多到少</option>
				<option value="orders.todaySales ASC">销量从少到多</option>
			</select>

			是否有销量：
			<select name="todaySales" id="todaySales" style="vertical-align: middle;">
				<option value="">请选择</option>
				<option value="1">有销量</option>
			</select>
		</div>
		<div class="filter">
			商品名称：
			<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"
				value="${vo.commodityNo }" />
			<input class="btn" type="submit" value="搜索" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th><label><input type="checkbox" id="chb_all" onclick="selectAll()"/>序号</label></th>
					<th>供应城市</th>
					<th>商品编号</th>
					<th>物料号</th>
					<th>属性</th>
					<th>大类</th>
					<th>小类</th>
					<th>供应商</th>
					<th>商品名</th>
					<th>规格</th>
					<th>今日销量</th>
					<th>剩余库存量</th>
					<th>初始库存量</th>
					<th>商品状态</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty inventoryList}">
						<c:forEach items="${inventoryList}" var="list" varStatus="vs">
						<tr class="main_info" <c:if test="${list.inventory < list.warningInvt}">style="color: red;" </c:if>>
							<td>
								<label>
									<input class="lbl_id" num="${list.sku}" type="checkbox" name="ids" value="${list.id }" />
									${vs.index + 1 }
								</label>
							</td>
							<td>${list.city }</td>
							<td>${list.commodityNo }</td>
							<td>${list.sku }</td>
							<td>${list.commodityAttr }</td>
							<td>${list.parentTypeName }</td>
							<td>${list.typeName }</td>
							<td title="${list.supplyNo }">${list.supplyName }</td>
							<td>${list.commodityName }</td>
							<td>${list.weight }</td>
							<td>${list.todaySales }</td>
							<td>${list.inventory}</td>
							<td>${list.inventoryInit}</td>
							<td>
								<c:choose>
									<c:when test="${list.shelves == 1 }"><span class="text_green">上架</span></c:when>
									<c:when test="${list.shelves == 0 }"><span class="text_invalid">下架</span></c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.invalid == 0 }">
										<a href="/commodity/inventoryEdit.html?id=${list.id }">修改</a>
									</c:when>
									<c:otherwise><span class="text_red text_invalid">修改</span></c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="14">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<input id="btn_excel" class="btn" type="button" value="导出销量和库存" />
			<input id="btn_excel_more" class="btn" type="button" value="信息导出" />

			批量调整为：
			<select id="slt_batch_update" style="vertical-align: middle;">
				<option value="">请选择</option>
				<option value="1">上架</option>
				<option value="0">下架</option>
			</select>
			<input id="btn_ok" class="btn" type="button" value="确定" title="确定" />
		</div>
		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript" src="../../style/js/"></script>
<script type="text/javascript">
	function selectAll(){
		if($("#chb_all").prop("checked")){
			$("input[name='ids']").prop("checked",true);
		} else {
			$("input[name='ids']").prop("checked",false);
		}
	}

	$(function() {
		// 将筛选条件存入cookie，以便编辑后返回之前页面并刷新
		$.cookie('haj_inventory_list_params', $('#searchForm').serialize());
	});

	$(function() {
		// 导出销量和库存
		$('#btn_excel').click(function() {
			try {
				document.location = "excelInventoryList.html?" + $('#searchForm').serialize();
				window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
					btn: ['关闭'] //按钮
				});
			} catch (e) {
				alert('未知异常');
			}
		});

		// 信息导出
		$('#btn_excel_more').click(function() {
			var passport = prompt('请输入通行口令');

			var url = '${pageContext.request.contextPath }/commodity/passportAuth/price.html?passport=' + passport;
			$.get(url, function(data) {
				if (data.result == 'success') {
					try {
						document.location = "/commodity/excelInventoryListMore.html?" + $('#searchForm').serialize()
							+ '&passport=' + passport;
						window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
							btn: ['关闭'] //按钮
						});
					} catch (e) {
						alert('未知异常');
					}
				} else {
					alert('口令错误，请重新输入');
				}
			});
		});

		$('select#orderByClause').val('${vo.orderByClause}');
		$('select#todaySales').val('${vo.todaySales}');

		/**
		 * 批量调整商品状态
		 */
		$('#btn_ok').click(function() {
			var len = $("input[name='ids']:checked").length;
			var list = $('.lbl_id:checked');
			for (var i = 0; i < list.length; i++) {
				var sku = $(list[i]).attr("num");
				if (!sku) {
					alert('请先录入商品物料编码');
					return false;
				}
			}
			if (len > 0) {
				if (confirm('确定调整选中商品的状态吗？')) {
					btn_disable($('#btn_ok'));
					var url = '${pageContext.request.contextPath }/commodity/updateStatus.html?';
					url += $('#deleteForm').serialize();
					url += '&status=' + $('#slt_batch_update').val();
					$.get(url, function(data) {
						alert(data.msg);
						if (data.error == '0') {
							window.location.reload();
						}
						btn_enable($('#btn_ok'));
					});
				}
			} else {
				alert('请先选择商品！');
			}
		});

		$('#btn_inventory_sync').click(function() {
			if (confirm('此操作将同步erp所有商品库存到我们平台，确定要这样做吗？')) {
				btn_disable($('#btn_inventory_sync'));
				var url = '${pageContext.request.contextPath }/erp/inventory/syncInventory.html';
				$.get(url,  function(data) {
					btn_enable($('#btn_inventory_sync'));
					alert(data.msg);

					if (data.error == '0') {
						window.location.reload();
					}
				});
			}
		});
	});
</script>
</body>
</html>