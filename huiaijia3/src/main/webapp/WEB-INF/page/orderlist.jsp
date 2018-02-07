<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单列表</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>订单列表</h3>
	<form action="/order/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			订单分类：
			<select name="isGrouponOrder" id="isGrouponOrder" style="vertical-align: middle;">
				<option value="" <c:if test="${order.isGrouponOrder==''}">selected</c:if>>不限</option>
				<option value="0" <c:if test="${order.isGrouponOrder=='0'}">selected</c:if>>普通</option>
				<option value="1" <c:if test="${order.isGrouponOrder=='1'}">selected</c:if>>团购</option>
				<option value="2" <c:if test="${order.isGrouponOrder=='2'}">selected</c:if>>补单</option>
			</select>

			订单状态：
			<select name="status" id="status" style="vertical-align: middle;">
				<option value="0" <c:if test="${order.status==0}">selected</c:if>>不限</option>
				<option value="1" <c:if test="${order.status==1}">selected</c:if>>等待付款</option>
				<option value="2" <c:if test="${order.status==2}">selected</c:if>>等待配送</option>
				<option value="3" <c:if test="${order.status==3}">selected</c:if>>已发货</option>
				<option value="4" <c:if test="${order.status==4}">selected</c:if>>已完成</option>
				<option value="5" <c:if test="${order.status==5}">selected</c:if>>待退款</option>
				<option value="6" <c:if test="${order.status==6}">selected</c:if>>已取消</option>
				<option value="7" <c:if test="${order.status==7}">selected</c:if>>已退款</option>
			</select>

			订单总价：
			<input class="inpMain short" type="text" name="sprice" value="${order.sprice }"/>
			-
			<input type="text" class="inpMain short" name="eprice" value="${order.eprice }"/>
		</div>
		<div class="filter">
			下单日期范围：
			<input type="button" class="btnGray" id="btn_today" value="今日"/>
			<input type="text" class="inpMain short date_picker" id="beginTime" name="beginTime"
				   value="${order.beginTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly"
				   placeholder="开始时间"/>
			-
			<input type="text" id="endTime" name="endTime" class="inpMain short date_picker" value="${order.endTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly"
				   placeholder="结束时间"/>
		</div>
		<div class="filter">
			<select name="sorterId" id="sorterId" style="vertical-align: middle; margin-right: 10px;">
				<option value="">选择分拣小组</option>
				<c:forEach items="${sorterList}" var="list">
					<option value="${list.id }"
							<c:if test="${order.sorterId==list.id}">selected</c:if>>${list.sorterName }
					</option>
				</c:forEach>
			</select>

			<textarea class="textArea" rows="1" cols="70" placeholder="小区名称：景鹏大厦,景亿山庄" id="residentialList"
					  name="residentialList"><c:forEach items='${order.residentialList }' var='residential'
														varStatus="vs">
				${residential }
				<c:if test="${!vs.last }">,</c:if>
			</c:forEach></textarea>
		</div>
		<div class="filter">
			<input placeholder="订单编号" class="inpMain short" type="text" name="orderNo" value="${order.orderNo }"/>
			<input type="text" class="inpMain short" placeholder="用户名 / 联系方式 / 用户ID" name="userInfo"
				   value="${order.userInfo }"/>

			<input type="text" class="inpMain short" placeholder="商品名称" name="commodityName"
				   value="${order.commodityName }"/>

			<input class="btn" type="submit" value="搜索"/>
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>
						<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()"/>
					</th>
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
					<th>优惠金额</th>
					<th>优惠券优惠</th>
					<th>商品实收</th>
					<th>商品成本</th>
					<th>商品盈亏</th>
					<th>配送小费</th>
					<th>运费</th>
					<th>订单实付</th>
					<th>售后退款</th>
					<th>本单积分</th>
					<th>订单状态</th>
					<th>订单分类</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty orderList}">
						<c:forEach items="${orderList}" var="order" varStatus="vs">
							<tr class="main_info">
								<td>
									<input type="checkbox" name="orderIds" id="orderIds${order.orderId }"
										   value="${order.orderId }"/>
								</td>
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
								<td>${order.feeWaiver }</td>
								<td>${order.counponMoney }</td>
								<td>${order.actualPayment }</td>
								<td>${order.commodityCost }</td>
								<td>${order.orderRealPay }</td>
								<td>${order.dispensingTip }</td>
								<td>${order.postFee }</td>
								<td>${order.actualPayment+order.dispensingTip+order.postFee }</td>
								<td>${order.refundMoney }</td>
								<td>${order.points + order.dispensingTip+order.postFee  }</td>
								<td>
									<c:if test="${order.status=='1'}">等待付款 </c:if>
									<c:if test="${order.status=='2'}">等待配送 </c:if>
									<c:if test="${order.status=='3'}">已发货 </c:if>
									<c:if test="${order.status=='4'}">已完成 </c:if>
									<c:if test="${order.status=='5'}">待退款 </c:if>
									<c:if test="${order.status=='6'}">已取消 </c:if>
									<c:if test="${order.status=='7'}">退款完成 </c:if>
									<c:if test="${order.status=='8'}">补单 </c:if>
									<c:if test="${order.status=='9'}">支付失败</c:if>
								</td>
								<td>
									<c:if test="${order.isGrouponOrder=='2'}">补单 </c:if>
									<c:if test="${order.isGrouponOrder=='1'}">团购 </c:if>
									<c:if test="${order.isGrouponOrder=='0'}">普通 </c:if>
								</td>
								<td>
									<a href="/order/orderDetail.html?orderId=${order.orderId }">查看详情</a> |
									<a href="javascript:cancelOrder('${order.orderNo }','${order.isGrouponOrder}','${order.status}');">取消补单</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="24">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div>
			<input type="button" id="btn_excel" class="btn" value="批量导出" title="批量导出"/>
			<input type="button" id="btn_hebingexcel" class="btn" value="合并导出" title="合并导出"/>
			<input type="button" id="btn_hebingskuexcel" class="btn" value="货位号合并导出" title="货位号合并导出"/>
			<input type="button" id="btn_report" class="btn" value="批量报表" title="批量报表"/>
		</div>
		${order.page.pageStr }
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

	function delOrder(orderId, createTime) {
		var date = new Date(createTime);
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		if (hours >= 16 && minutes >= 10 && seconds >= 0) {
			alert('此订单不能删除，如需处理，请联系管理员！');
		} else {
			if (confirm("确定要删除这条订单吗？")) {
				var url = "/order/del.html?orderId=" + orderId;
				$.get(url, function (data) {
					if (data == "1") {
						alert('删除成功！');
						document.location.reload();
					}
				});
			}
		}
	}

	function cancelOrder(orderNo, isGrouponOrder, status) {
		if (isGrouponOrder == 2) {
			if (status == 2) {
				if (confirm("确定要取消订单吗？")) {
					var url = "/order/cancleOrder.html?orderNo=" + orderNo;
					$.get(url, function (data) {
						alert(data.msg);
						if (data.error == "0") {
							document.location.reload();
						}
					});
				}
			} else {
				alert('只能取消已支付状态的补单！');
			}
		} else {
			alert('后台只能取消补单！');
		}
	}

	$(function () {
		// 批量导出
		$('#btn_excel').click(function () {
			var status = $("#status").val();
			if (status == 0) {
				alert('请先选择订单状态');
				return false;
			}
			document.location = "/order/excelOrderDetail.html?" + $('#searchForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});

		// 合并导出
		$('#btn_hebingexcel').click(function () {
			var status = $("#status").val();
			if (status == 0) {
				alert('请先选择订单状态');
				return false;
			}
			document.location = "/order/excelHebingOrderDetail.html?" + $('#searchForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});

		// 货位号合并导出
		$('#btn_hebingskuexcel').click(function () {
			var status = $("#status").val();
			if (status == 0) {
				alert('请先选择订单状态');
				return false;
			}
			document.location = "/order/excelHebingskuOrderDetail.html?" + $('#searchForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});

		// 批量报表
		$('#btn_report').click(function () {
			var status = $("#status").val();
			document.location = "/order/excelOrderReportDetail.html?" + $('#searchForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});

		$('#btn_today').click(function () {
			var today = date_format(new Date(), '-');
			$('#beginTime').val(today + ' 00:00:00');
			$('#endTime').val(today + ' 23:59:59');
		});

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
			$.get(url, function (data) {
				if (data.data) {
					$('#residentialList').val(function () {
						return data.data;
					});
				} else {
					$('#residentialList').val('');
				}
			}, "json");
		});
	});
</script>
</body>
</html>
