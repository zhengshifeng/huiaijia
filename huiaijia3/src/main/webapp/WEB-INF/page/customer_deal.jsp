<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户交易</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>客户交易</h3>
		<form action="customerDeal.html" method="post" name="userForm" id="userForm">
			<div class="filter" style="height: 5%;">

				<input type="text" class="inpMain" placeholder="小区名称" name="residential" value="${criteria.residential }" />

				<select name="ismember" id="ismember" style="vertical-align: middle;">
					<option value="">用户角色</option>
					<option value="1" <c:if test="${criteria.ismember eq '1'}">selected</c:if>>仅注册</option>
					<option value="2" <c:if test="${criteria.ismember eq '2'}">selected</c:if>>预备会员</option>
					<option value="3" <c:if test="${criteria.ismember eq '3'}">selected</c:if>>一元购会员</option>
					<option value="4" <c:if test="${criteria.ismember eq '4'}">selected</c:if>>会员取消</option>
					<option value="5" <c:if test="${criteria.ismember eq '5'}">selected</c:if>>普通会员</option>
				</select>

				日期：
				<input type="text" class="inpMain short date_picker" name="beginTime" value="${criteria.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" name="endTime" class="inpMain short date_picker" value="${criteria.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="结束时间" />

				<input type="text" class="inpMain" placeholder="用户名/手机号码/收货人" name="username" value="${criteria.username }" />

				<input name="listSearch" class="btn" type="submit" id="listSearch" value="搜索" />
			</div>
		</form>

		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;" class="tableBasic list">
					<tr class="main_head">
						<th>
							<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />
						</th>
						<th>用户ID</th>
						<th>用户名</th>
						<th>手机号码</th>
						<th>收货人</th>
						<th>用户角色</th>
						<th>小区</th>
						<th>小区地址</th>
						<th>用户住址</th>
						<th>充值金额</th>
						<th>充值退款</th>
						<th>消费金额</th>
						<th>消费退款</th>
						<th>条件余额</th>
						<th>实时余额</th>
					</tr>
					<c:choose>
						<c:when test="${not empty customer_dealList}">
							<c:forEach items="${customer_dealList}" var="customer" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="orderIds" id="orderIds${customer.id }" value="${customer.id }" />
									</td>
									<td>${customer.id }</td>
									<td>${customer.username }</td>
									<td>${customer.mobilePhone }</td>
									<td>${customer.receiver }</td>
									<td>
										<c:if test="${customer.ismember eq '1'}">仅注册</c:if>
										<c:if test="${customer.ismember eq '2'}">预备会员</c:if>
										<c:if test="${customer.ismember eq '3'}">一元购会员</c:if>
										<c:if test="${customer.ismember eq '4'}">会员取消</c:if>
										<c:if test="${customer.ismember eq '5'}">普通会员</c:if>
									</td>
									<td>${customer.residential }</td>
									<td>${customer.shippingAddress }</td>
									<td>${customer.address }</td>

									<td>
										<c:if test="${customer.rechargeMoney == null }">￥0</c:if>
										<c:if test="${customer.rechargeMoney >= 0 }">￥${customer.rechargeMoney }</c:if>
									</td>
									<td>
										<c:if test="${customer.refundNum == null }">￥0</c:if>
										<c:if test="${customer.refundNum >= 0 }">￥${customer.refundNum }</c:if>
									</td>

									<td>
										<c:if test="${customer.orderMoney == null }">￥0</c:if>
										<c:if test="${customer.orderMoney >= 0 }">￥${customer.orderMoney }</c:if>
									</td>
									<td>
										<c:if test="${customer.refundMoney == null }">￥0</c:if>
										<c:if test="${customer.refundMoney >= 0 }">￥${customer.refundMoney }</c:if>
									</td>

									<td>￥${customer.rechargeMoney + customer.refundMoney - customer.orderMoney}</td>
									<td>
										<c:if test="${customer.balance == null }">￥0</c:if>
										<c:if test="${customer.balance >= 0 }">￥${customer.balance }</c:if>
									</td>

								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="18" align="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="page_and_btn">
			<div>
				<a href="javascript:exportCustomer();" class="btn">
					<em>导出</em>
				</a>
			</div>
			${criteria.page.pageStr }
		</div>
	</div>

	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='orderIds']").prop("checked", true);
			} else {
				$("input[name='orderIds']").prop("checked", false);
			}
		}

		function exportCustomer() {
			var ismember = $("#ismember").val();
			var residential = $("input[name='residential']").val();
			var beginTime = $("input[name='beginTime']").val();
			var endTime = $("input[name='endTime']").val();
			var username = $("input[name='username']").val();

			document.location = "exportCustomerDeal.html?ismember=" + ismember + "&residential=" + residential
					+ "&beginTime=" + beginTime + "&endTime=" + endTime + "&username=" + username;

		}
	</script>
</body>
</html>
