<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品分拣单列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>商品分拣单</h3>
		<form action="getAll.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				商品类别：
				<select name="commodityAttr" id="commodityAttr" onchange="selectBtypy();">
					<option value="">商品属性</option>
					<option value="生鲜" <c:if test="${sorting.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
					<option value="水果" <c:if test="${sorting.commodityAttr=='水果'}">selected</c:if>>水果</option>
					<option value="团购" <c:if test="${sorting.commodityAttr=='团购'}">selected</c:if>>团购</option>
					<option value="早餐" <c:if test="${sorting.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
				</select>
				<select name="bTypeName" id="bTypeName" style="vertical-align: middle;" onchange="selectStypy();">
					<option value="">请选择</option>
					<c:forEach items="${parentTypeList}" var="list">
						<option value="${list.id }" <c:if test="${sorting.bTypeName==list.id}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>
				<select name="sTypeName" id="sTypeName" style="vertical-align: middle;">
					<option value="">请选择</option>
					<c:forEach items="${subTypeList}" var="list">
						<option value="${list.typeName }" <c:if test="${sorting.sTypeName==list.typeName}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>

				分拣日期：
				<input type="button" class="btnGray" id="btn_today" value="今日" />
				<input class="inpMain short date_picker" type="text" id="beginTime" name="beginTime" value="${sorting.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
				-
				<input class="inpMain short date_picker" type="text" id="endTime" name="endTime" value="${sorting.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			</div>
			<div class="filter" >
				商品名称：
				<input class="inpMain" placeholder="商品名称/商品编号" type="text" name="commodityNo" value="${sorting.commodityNo }" />

				供应商：
				<input placeholder="供应商" class="inpMain" type="text" name="supplyChain" value="${sorting.supplyChain }" />

				分拣小组：
				<select name="sorterId" id="sorterId" style="vertical-align: middle; margin-right: 10px;">
					<option value="">选择分拣小组</option>
					<c:forEach items="${sorterList}" var="list">
						<option value="${list.id }" <c:if test="${sorting.sorterId==list.id}">selected</c:if>>${list.sorterName }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="filter" style="height: 50px;">
				批量查找：
				<textarea class="textArea" rows="2" cols="70" placeholder="小区名称：景鹏大厦,景亿山庄" id="residentialList" name="residentialList"><c:forEach 
				items='${sorting.residentialList }' var='residential' varStatus="vs">${residential }<c:if test="${!vs.last }">,</c:if></c:forEach></textarea>
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
						<th>商品编号</th>
						<th>属性</th>
						<th>大类</th>
						<th>小类</th>
						<th>商品名称</th>
						<th>分拣单号</th>
						<th>供应商</th>
						<th>小区</th>
						<th>分拣时间</th>
						<th>规格</th>
						<th>数量</th>
						<th>交易单价</th>
						<th>合计</th>
						<th>售出合计</th> 
					</tr>
					<c:choose>
						<c:when test="${not empty sortingList}">
							<c:forEach items="${sortingList}" var="order" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="orderIds" id="orderIds${order.id }" value="${order.id }" />
									</td>
									<td>${order.commodityNo }</td>
									<td>${order.commodityAttr }</td>
									<td>${order.bTypeName }</td>
									<td>${order.sTypeName }</td>
									<td>${order.name }</td>
									<td>${order.sortingNo }</td>
									<td>${order.supplyChain }</td>
									<td>${order.residential }</td>
									<td>${order.createTime }</td>
									<td>${order.weight }</td>
									<td>${order.number }</td>
									<td>${order.price }</td>
									<td>${order.money }</td>
									<td>${order.saleMoney }</td> 
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="15">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>

		<div class="search_div" style="line-height: 30px; text-align: right;">
			<span style="padding-right: 15px; font-size: 20px;">种类：${totalPurchase.totalCount}</span> <span
				style="padding-right: 15px; font-size: 20px;">数量：${totalPurchase.sumNumber} </span> <span
				style="padding-right: 15px; font-size: 20px;">总价：${totalPurchase.sumMoney}</span> <span
				style="padding-right: 15px; font-size: 20px;">售出总价：${totalPurchase.sumSaleMoney}</span>
		</div> 

		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<input type="button" id="btn_export_purchase" class="btn" value="导出" title="导出" />
				<input type="button" id="btn_export_sort_order" class="btn" value="合并导出" title="合并导出" />
				<!-- <input type="button" id="btn_export_sale_purchase" class="btn" value="统计导出" title="统计导出" /> -->
			</div>
			${sorting.page.pageStr }
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

		$(function(){

			$('#btn_today').click(function(){
				var today = date_format(new Date(), '-');
				$('#beginTime').val(today + ' 00:00:00');
				$('#endTime').val(today + ' 23:59:59');
			})

			/**
			 * 根据分拣小组查询其下的小区
			 */
			$('#sorterId').change(function () {
				var sorterId = $(this).val();
				if (!sorterId) {
					$('#residentialList').val('');
					return;
				}

				var url = "/community/getCommunitiesBySorterId.html?sorterId=" + sorterId;
				$.get(url, function(data) {
					if (data.data) {
						$('#residentialList').val(function () {
							return data.data;
						});
					} else {
						$('#residentialList').val('');
					}
				}, "json");
			});

			// 导出
			$('#btn_export_purchase').click(function () {
				btn_disable($(this));
				document.location = "excel.html?" + $('#userForm').serialize();
				setTimeout('btn_enable($("#btn_export_purchase"))', 10000);
			});

			// 合并导出
			$('#btn_export_sort_order').click(function () {
				btn_disable($(this));
				document.location = "exportSortOrder.html?" + $('#userForm').serialize();
				setTimeout('btn_enable($("#btn_export_sort_order"))', 10000);
			});

			// 统计导出
			$('#btn_export_sale_purchase').click(function () {
				btn_disable($(this));
				document.location = "salexcel.html?" + $('#userForm').serialize();
				setTimeout('btn_enable($("#btn_export_sale_purchase"))', 10000);
			});
		});
		
	</script>
</body>
</html>
