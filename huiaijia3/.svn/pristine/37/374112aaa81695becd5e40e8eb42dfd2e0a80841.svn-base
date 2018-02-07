<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单支付查询</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>订单支付查询</h3>
	<form action="/orderPayment/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			支付方式：
			<select name="paymentWay" id="paymentWay" style="vertical-align: middle; margin-right: 15px;">
				<option value="">不限</option>
				<option value="0" <c:if test="${vo.paymentWay=='0'}">selected</c:if>>余额</option>
				<option value="1" <c:if test="${vo.paymentWay=='1'}">selected</c:if>>微信</option>
				<option value="2" <c:if test="${vo.paymentWay=='2'}">selected</c:if>>支付宝</option>
			</select>

			支付状态：
			<select name="orderStatus" id="orderStatus" style="vertical-align: middle;">
				<option value="">不限</option>
				<option value="1" <c:if test="${vo.orderStatus=='1'}">selected</c:if>>待支付</option>
				<option value="4" <c:if test="${vo.orderStatus=='4'}">selected</c:if>>已支付</option>
			</select>

			有无退款：
			<select name="isRefund" id="isRefund" style="vertical-align: middle;">
				<option value="">不限</option>
				<option value="1" <c:if test="${vo.isRefund=='1'}">selected</c:if>>仅看有退款</option>
			</select>

			手机号：
			<input type="text" class="inpMain short" placeholder="手机号" name="mobilePhone" id="mobilePhone"
				   value="${vo.mobilePhone }"/>

			订单号：
			<input type="text" class="inpMain" placeholder="订单号" name="orderNo" id="orderNo"
				   value="${vo.orderNo }"/>
		</div>
		<div class="filter">
			支付时间：
			<input type="button" class="btnGray" id="btn_today" value="今日"/>
			<input type="text" class="inpMain short date_picker" id="dateStart" name="dateStart" value="${vo.dateStart}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				   readonly="readonly" placeholder="开始时间"/>
			-
			<input type="text" id="dateEnd" name="dateEnd" class="inpMain short date_picker" value="${vo.dateEnd}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				   readonly="readonly" placeholder="结束时间"/>

			<input class="btn" type="submit" value="搜索"/>
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>订单号</th>
					<th>手机号码</th>
					<th>支付方式</th>
					<th>支付流水号</th>
					<th>支付状态</th>
					<th>订单实付金额</th>
					<th>支付时间</th>
					<th>退款金额</th>
					<th>退款时间</th>
				</tr>
				<c:choose>
					<c:when test="${not empty orderPaymentList}">
						<c:forEach items="${orderPaymentList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>${vs.index + 1}</td>
								<td>${list.orderNo }</td>
								<td>${list.mobilePhone }</td>
								<td>
									<c:choose>
										<c:when test="${list.paymentWay == '0' }">余额</c:when>
										<c:when test="${list.paymentWay == '1' }">微信</c:when>
										<c:when test="${list.paymentWay == '2' }">支付宝</c:when>
										<c:otherwise>未知</c:otherwise>
									</c:choose>
								</td>
								<td>${list.tradeNo }</td>
								<td>
									<c:choose>
										<c:when test="${list.orderStatus == '1'}">
											<span class="text_blue">待支付</span>
										</c:when>
										<c:when test="${list.orderStatus == '9'}">
											<span class="text_red">支付超时，已取消</span>
										</c:when>
										<c:otherwise><span class="text_green">已支付</span></c:otherwise>
									</c:choose>
								</td>
								<td>￥${list.actualPayment }</td>
								<td>
									<fmt:parseDate value="${list.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss" var="paymentTime"/>
									<fmt:formatDate value="${paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.orderStatus == '6' }">
											<span class="text_red">订单取消￥${list.actualPayment }</span>
										</c:when>
										<c:when test="${list.totalRefund != null}">
											<span class="text_blue">售后退款￥${list.totalRefund }</span>
										</c:when>
									</c:choose>
								</td>
								<td>
									<fmt:parseDate value="${list.refundTime}" pattern="yyyy-MM-dd HH:mm:ss" var="refundTime"/>
									<fmt:formatDate value="${refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<input type="button" id="btn_excel" class="btn" value="导出列表" title="导出列表" />

			<input type="button" id="btn_statistics" class="btn" value="支付统计" title="支付统计" />
			<b id="span_order_paid" style="padding-right: 15px;">已支付：￥0.00</b>
			<b id="span_order_refund" style="padding-right: 15px;">已退款：￥0.00</b>
			<b id="span_received" style="padding-right: 15px;">实收：￥0.00</b>
		</div>

		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript">

	$(function () {
		// 批量导出
		$('#btn_excel').click(function () {
			btn_disable($(this));
			document.location = "/orderPayment/export2Excel.html?" + $('#searchForm').serialize();
			setTimeout('btn_enable($("#btn_excel"))', 10000);
		});

		$('#btn_today').click(function () {
			var today = date_format(new Date(), '-');
			$('#dateStart').val(today + ' 00:00:00');
			$('#dateEnd').val(today + ' 23:59:59');
		});

		$('#btn_statistics').click(function () {
			btn_disable($('#btn_statistics'));
			var url = '/orderPayment/calcOrderPayment.html';
			var data = $('#searchForm').serialize();
			$.post(url, data, function (result) {
				btn_enable($('#btn_statistics'));
				$('#span_order_paid').text('已支付：￥' + result.orderPaid);
				$('#span_order_refund').text('已退款：￥' + result.orderRefund);
				$('#span_received').text('实收：￥' + result.received);
			});
		})
	});

</script>
</body>
</html>
