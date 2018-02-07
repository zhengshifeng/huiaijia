<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待配送订单列表</title>
<link href="../../style/css/public.css?v=20161223" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../style/js/jquery.min.js"></script>
<script type="text/javascript" src="../../style/js/global.js"></script>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="javascript:;" id="btn_wms_ship_order" class="actionBtn">导出wms发货单数据</a>
			待配送订单列表
		</h3>
		<form action="/order/deliveryOrder.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				订单分类：
				<select name="isGrouponOrder" id="isGrouponOrder" style="vertical-align: middle;">
					<option value=""  <c:if test="${order.isGrouponOrder==''}">selected</c:if>>不限</option>
					<option value="0" <c:if test="${order.isGrouponOrder=='0'}">selected</c:if>>普通</option>
					<option value="1" <c:if test="${order.isGrouponOrder=='1'}">selected</c:if>>团购</option>
					<option value="2" <c:if test="${order.isGrouponOrder=='2'}">selected</c:if>>补单</option>
				</select>

				订单中含有商品来源：
				<select name="source" id="source">
					<option value="0" <c:if test="${order.source==0}">selected</c:if>>不限</option>
					<option value="1" <c:if test="${order.source==1}">selected</c:if>>家人喜好</option>
					<option value="2" <c:if test="${order.source==2}">selected</c:if>>全部</option>
				</select>

				订单总价：
				<input class="inpMain short" type="text" name="sprice" value="${order.sprice }"/>
				-
				<input type="text" class="inpMain short" name="eprice" value="${order.eprice }"/>
			</div>

			<div class="filter" >
				下单日期范围：
				<input type="text" class="inpMain short date_picker" name="beginTime" value="${order.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" name="endTime" class="inpMain short date_picker" value="${order.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="结束时间" />

				时间段：
				<input type="number" class="inpMain short" name="beginHour" value="${order.beginHour }" />
				-
				<input type="number" class="inpMain short" name="endHour" value="${order.endHour }" />
			</div>

			<div class="filter" >
				<input type="text" placeholder="小区名称" class="inpMain short" name="residential" value="${order.residential }" />
				<input placeholder="订单编号" class="inpMain short" type="text" name="orderNo" value="${order.orderNo }" />
				<input type="text" class="inpMain short" placeholder="用户名  / 联系方式  / 用户ID" name="userInfo" value="${order.userInfo }" />

				<input type="text" class="inpMain short" placeholder="商品名称" name="commodityName" value="${order.commodityName }" />

				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>订单号</th>
						<th>下单时间</th>
						<th>小区</th>
						<th width="60">小区地址</th>
						<th>收货人</th>
						<th width="150">用户住址</th>
						<th>用户名</th>
						<th>手机号</th>
						<th>商品数</th>
						<th>订单总价</th>
						<th>商品实收</th>
						<th>商品成本</th>
						<th>商品盈亏</th>
						<th>配送小费</th>
						<th>运费</th>
						<th>订单实付</th>
						<th>售后退款</th>
						<th>省下</th>
						<th>本单积分</th>
						<th>订单状态</th>
						<th>订单分类</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty orderList}">
							<c:forEach items="${orderList}" var="order" varStatus="vs">
								<tr class="main_info">
									<td>${order.orderNo }</td>
									<td>${order.createTime }</td>
									<td>${order.residential }</td>
									<td>${order.shippingAddress }</td>
									<td>${order.receiver }</td>
									<td>${order.address }</td>
									<td>${order.username }</td>
									<td>${order.mobilePhone }</td>
									<td>${order.number }</td>
									<td>${order.totalMoney}</td>
									<td>${order.actualPayment }</td>
									<td>${order.commodityCost }</td>
									<td>${order.orderRealPay }</td>
									<td>${order.dispensingTip }</td>
									<td>${order.postFee }</td>
									<td>${order.actualPayment+order.dispensingTip+order.postFee }</td>
									<td>${order.refundMoney }</td>
									<td>${order.feeWaiver }</td>
									<td>${order.points + order.dispensingTip+order.postFee  }</td>
									<td>
										<c:if test="${order.status=='1'}">等待付款</c:if>
										<c:if test="${order.status=='2'}">等待配送</c:if>
										<c:if test="${order.status=='3'}">已发货</c:if>
										<c:if test="${order.status=='4'}">已完成</c:if>
										<c:if test="${order.status=='5'}">待退款</c:if>
										<c:if test="${order.status=='6'}">已取消</c:if>
										<c:if test="${order.status=='7'}">退款完成</c:if>
									</td>
									<td>
										<c:if test="${order.isGrouponOrder=='2'}">补单</c:if>
										<c:if test="${order.isGrouponOrder=='1'}">团购</c:if>
										<c:if test="${order.isGrouponOrder=='0'}">普通</c:if>
									</td>
									<td>
										<a href="/order/orderDetail.html?orderId=${order.orderId }">查看详情</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="19">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<a id="btn_hebingexcel" class="btn" style="margin-right: 7px;">
					<em>合并导出</em>
				</a>
			</div>
			${order.page.pageStr }
		</div>
	</div>
</body>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		// 合并导出
		$('#btn_hebingexcel').click(function(){
			document.location.href = "/order/excelHebingOrderDetail.html?status=2&" + $('#userForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});

		$('#btn_wms_ship_order').click(function(){
			document.location.href = "/order/wmsExportShipOrder.html";
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});
	});
</script>
</html>
