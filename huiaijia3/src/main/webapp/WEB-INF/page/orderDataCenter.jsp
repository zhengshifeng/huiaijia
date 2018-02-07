<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营数据中心</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>运营数据中心</h3>
		<div id="list">
			<table style="width: 700px; text-align: center" class="tableBasic">
				<tr>
					<th colspan="4">今日小区情况</th>
				</tr>
				<tr>
					<th>已开通小区数</th>
					<th>下单小区数</th>
					<th>新开通小区数</th>
					<th>待开通小区数</th>
				</tr>
				<tr class="main_info">
					<td>${operationsData.openedCommunities }</td>
					<td>${operationsData.orderCommunities }</td>
					<td>${operationsData.todayOpenedCommunities }</td>
					<td>${operationsData.waitOpenCommunities }</td>
				</tr>
			</table>
			<table style="width: 700px; text-align: center; margin-top: 20px;" class="tableBasic">
				<tr>
					<th colspan="4">今日订单情况</th>
				</tr>
				<tr>
					<th>普通订单数</th>
					<th>普通订单成交金额</th>
					<th>普通订单盈亏</th>
					<th>普通订单客单价</th>
				</tr>
				<tr>
					<td>${operationsData.normalOrderCount }</td>
					<td>￥${operationsData.normalOrderAmount }</td>
					<td>￥${operationsData.normalOrderProfitAndLoss }</td>
					<td>￥${operationsData.normalOrderPCT }</td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<th>团购订单数</th>
					<th>团购成交金额</th>
					<th>团购订单盈亏</th>
					<th>团购订单客单价</th>
				</tr>
				<tr>
					<td>${operationsData.teamOrderCount }</td>
					<td>￥${operationsData.teamOrderAmount }</td>
					<td>￥${operationsData.teamOrderProfitAndLoss }</td>
					<td>￥${operationsData.teamOrderPCT }</td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<th>补单订单数</th>
					<th>总成交金额</th>
					<th>总盈亏金额</th>
					<th>配送次数</th>
				</tr>
				<tr>
					<td>${operationsData.reorders }</td>
					<td>￥${operationsData.todayAmount }</td>
					<td>￥${operationsData.todayProfitAndLoss }</td>
					<td>${operationsData.deliveryTimes }</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
