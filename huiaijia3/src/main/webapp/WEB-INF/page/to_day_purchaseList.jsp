<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自主加工商品今日采购表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>自主加工商品今日采购表</h3>
		<form action="getToDayPurchaseList.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				商品类别：
				<select name="commodityAttr" id="commodityAttr" onchange="selectBtypy();">
					<option value="">商品属性</option>
					<option value="生鲜" <c:if test="${purchase.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
					<option value="水果" <c:if test="${purchase.commodityAttr=='水果'}">selected</c:if>>水果</option>
					<option value="团购" <c:if test="${purchase.commodityAttr=='团购'}">selected</c:if>>团购</option>
					<option value="早餐" <c:if test="${purchase.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
				</select>

				<select name="bTypeName" id="bTypeName" style="vertical-align: middle;" onchange="selectStypy();">
					<option value="">请选择</option>
					<c:forEach items="${parentTypeList}" var="list">
						<option value="${list.id }" <c:if test="${purchase.bTypeName==list.id}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>

				<select name="sTypeName" id="sTypeName" style="vertical-align: middle;">
					<option value="">请选择</option>
					<c:forEach items="${subTypeList}" var="list">
						<option value="${list.typeName }" <c:if test="${purchase.sTypeName==list.typeName}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>
			</div>
		
			<div class="filter" >
				<input class="inpMain" placeholder="商品名称/商品编号" type="text" name="commodityNo" value="${purchase.commodityNo }" />
				<input class="btn" type="button" value="搜索" onclick="search();" />
			</div>

		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>
							<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />
							全选
						</th>
						<th>属性</th>
						<th>大类</th>
						<th>小类</th>
						<th>商品名称</th>
						<th>下单时间</th>
						<th>包装规格/Kg</th>
						<th>包装份数/份</th>
						<th>包装净重/Kg</th>
						<th>昨日损耗率</th>
						<th>毛菜重量/Kg</th>
						<th>毛菜库存/Kg</th>
						<th>毛菜采购量/Kg</th>
					</tr>
					<c:choose>
						<c:when test="${not empty purchaseList}">
							<c:forEach items="${purchaseList}" var="order" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="orderIds" id="orderIds${order.id }" value="${order.id }" />
									</td>
									<td>${order.commodityAttr }</td>
									<td>${order.bTypeName }</td>
									<td>${order.sTypeName }</td>
									<td>${order.name }</td>
									<td>${order.createTime }</td>
									<td>${order.packedWeight }</td>
									<td>${order.number }</td>
									<td>${order.packageWeight }</td>
									<td>${order.wastageRate }%</td>
									<td>${order.weightofvegetable}</td>
									<td>${order.grossWeight }</td>
									<td>${order.weightofvegetable-order.grossWeight}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="14">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>

	<%-- 	<div class="search_div" style="line-height: 30px; text-align: right;">
			<span style="padding-right: 15px; font-size: 20px;">种类：${totalPurchase.totalCount}</span> <span
				style="padding-right: 15px; font-size: 20px;">数量：${totalPurchase.sumNumber} </span> <span
				style="padding-right: 15px; font-size: 20px;">总价：${totalPurchase.sumMoney}</span> <span
				style="padding-right: 15px; font-size: 20px;">售出总价：${totalPurchase.sumSaleMoney}</span>
		</div> --%>

		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<a href="javascript:exportPurchase();" class="btn">
					<em>导出</em>
				</a>
				<!-- <a href="javascript:exportSalePurchase();" class="btn">
					<em>统计导出</em>
				</a>
				<a href="javascript:excelHebingDetail();" class="btn">
					<em>合并导出</em>
				</a> -->
				
			</div>
			${purchase.page.pageStr }
		</div>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='orderIds']").prop("checked", true);
			} else {
				$("input[name='orderIds']").prop("checked", false);
			}
		}

		function selectBtypy() {
			var commodityAttr = $("#commodityAttr").val();
			var url = "/commodityType/getBigType.html?commodityAttr=" + commodityAttr;
			$.get(url, function(data) {
				if (data.length > 0) {
					document.getElementById("bTypeName").length = 0;
					$("#bTypeName").append("<option value=''>请选择</option>");
					$.each(data, function(i) {
						$("#bTypeName").append("<option value='"+this.id+"'>" + this.typeName + "</option>");
					});
				}
			}, "json");
		}

		function selectStypy() {
			var parentId = $("#bTypeName").val();
			var url = "/commodityType/getSmallType.html?parentId=" + parentId;
			$.get(url, function(data) {
				if (data.length > 0) {
					document.getElementById("sTypeName").length = 0;
					$("#sTypeName").append("<option value=''>请选择</option>");
					$.each(data, function(i) {
						$("#sTypeName").append("<option value='"+this.typeName+"'>" + this.typeName + "</option>");
					});
				}
			}, "json");
		}

		function search() {
			$("#userForm").submit();
		}

		function exportPurchase() {
			var commodityNo = $("input[name='commodityNo']").val();
			var bTypeName = $("#bTypeName").val();
			var sTypeName = $("#sTypeName").val();
			document.location = "excelToDayPurchase.html?commodityNo=" + commodityNo + "&bTypeName=" + bTypeName + "&sTypeName="
					+ sTypeName + "";
		}
		
		function excelHebingDetail() {
			var beginTime = $("input[name='beginTime']").val();
			var endTime = $("input[name='endTime']").val();
			var supplyChain = $("input[name='supplyChain']").val();
			var commodityNo = $("input[name='commodityNo']").val();
			var bTypeName = $("#bTypeName").val();
			var sTypeName = $("#sTypeName").val();
			document.location = "excelHebingDetail.html?beginTime=" + beginTime + "&endTime=" + endTime + "&supplyChain="
					+ supplyChain + "&commodityNo=" + commodityNo + "&bTypeName=" + bTypeName + "&sTypeName="
					+ sTypeName + "";
		}
		
		
		function exportSalePurchase() {
			var beginTime = $("input[name='beginTime']").val();
			var endTime = $("input[name='endTime']").val();
			var supplyChain = $("input[name='supplyChain']").val();
			var commodityNo = $("input[name='commodityNo']").val();
			var bTypeName = $("#bTypeName").val();
			var sTypeName = $("#sTypeName").val();
			document.location = "salexcel.html?beginTime=" + beginTime + "&endTime=" + endTime + "&supplyChain="
					+ supplyChain + "&commodityNo=" + commodityNo + "&bTypeName=" + bTypeName + "&sTypeName="
					+ sTypeName + "";
		}
	</script>
</body>
</html>
