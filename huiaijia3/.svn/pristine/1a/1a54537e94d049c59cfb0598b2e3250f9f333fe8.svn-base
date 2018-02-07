<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后订单</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>售后订单</h3>
		<form action="/orderSale/getAll.html" method="post" name="searchForm" id="searchForm">
			<div class="filter" style="height: 50px;">
				售后状态：
				<select name="isdeal" id="isdeal" style="vertical-align: middle; margin-right: 10px;">
					<option value="">不限</option>
					<option value="0" <c:if test="${sale.isdeal == 0}">selected</c:if>>未处理</option>
					<option value="1" <c:if test="${sale.isdeal == 1}">selected</c:if>>已处理</option>
				</select>

				支付方式：
				<select name="paymentWay" id="paymentWay" style="vertical-align: middle; margin-right: 10px;">
					<option value="">不限</option>
					<option value="0" <c:if test="${sale.paymentWay == 0}">selected</c:if>>余额</option>
					<option value="1" <c:if test="${sale.paymentWay == 1}">selected</c:if>>微信</option>
					<option value="2" <c:if test="${sale.paymentWay == 2}">selected</c:if>>支付宝</option>
				</select>

				提交日期：
				<input type="button" class="btnGray" id="btn_today" value="今日" />
				<input type="text" class="inpMain short date_picker" name="dateStart" id="dateStart"
					   value="${sale.dateStart }" onclick="WdatePicker()" readonly="readonly" /> -
				<input type="text" class="inpMain short date_picker" name="dateEnd" id="dateEnd"
					   value="${sale.dateEnd }" onclick="WdatePicker()" readonly="readonly" />
			</div>
			<div class="filter" style="height: 50px;">
				排序：
				<select name="sort" id="sort" style="vertical-align: middle; margin-right: 10px;">
					<option value="">不限</option>
					<option value="1" <c:if test="${sale.sort == 1}">selected</c:if>>提交时间从早到晚</option>
					<option value="2" <c:if test="${sale.sort == 2}">selected</c:if>>处理时间从早到晚</option>
					<option value="3" <c:if test="${sale.sort == 3}">selected</c:if>>下单时间从晚到早</option>
				</select>
				<input type="text" placeholder="用户ID / 手机号码" class="inpMain short" name="userInfo"
					   value="${sale.userInfo }" />

				<input type="text" placeholder="订单编号" class="inpMain short" name="orderNo" value="${sale.orderNo }" />
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />全选</th>
						<th>订单编号</th>
						<th>手机号码</th>
						<th>所属城市</th>
						<th>小区名称</th>
						<th>订单下单时间</th>
						<th>售后提交时间</th>
						<th>订单实付金额</th>
						<th>申请售后退还金额</th>
						<th>原因</th>
						<th>申请人</th>
						<th>处理人</th>
						<th>订单状态</th>
						<th>支付方式</th>
						<th>售后状态</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty saleList}">
							<c:forEach items="${saleList}" var="list" varStatus="vs">
								<tr class="main_info">
									<td>
										<c:if test="${list.isdeal=='0'}">
										<label>
											<input type="checkbox" name="saleIds" value="${list.id }" />
											${vs.index + 1}
										</label>
										</c:if>
									</td>
									<td>${list.orderNo }</td>
									<td>${list.mobilePhone }</td>
									<td>${list.city }</td>
									<td>${list.communityName }</td>
									<td>${list.orderTime }</td>
									<td>${list.refundTime }
									<td>${list.actualPayment }
									<td>${list.money }</td>
									<td>${list.quersion }</td>
									<td>${list.applicant }</td>
									<td>${list.dealer }</td>
									<td>
										<c:if test="${list.status=='1'}">等待付款</c:if>
										<c:if test="${list.status=='2'}">等待配送</c:if>
										<c:if test="${list.status=='3'}">已发货</c:if>
										<c:if test="${list.status=='4'}">已完成</c:if>
										<c:if test="${list.status=='5'}">待退款</c:if>
										<c:if test="${list.status=='6'}">已取消</c:if>
										<c:if test="${list.status=='7'}">已退款</c:if>
									</td>
									<td>
										<c:if test="${list.paymentWay=='0'}">
											余额
										</c:if>
										<c:if test="${list.paymentWay=='1'}">
											微信
										</c:if>
										<c:if test="${list.paymentWay=='2'}">
											支付宝
										</c:if>
									</td>
									<td>
										<c:if test="${list.isdeal=='0'}">
											<div style="color: red">等待处理</div>
										</c:if>
										<c:if test="${list.isdeal=='1'}">
											已处理
										</c:if>
									</td>
									<td>
										<c:if test="${list.isdeal=='0'}">
											<a href="javascript:;" title="点击退款"
											   onclick="saleRefund('${list.id }', '${list.isdeal}')" >退款</a>
											|
											<a href="javascript:;" onclick="cancelOrder(${list.id })"
											   title="点击取消申请">取消</a>
										</c:if>
									</td>
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
		<div class="clear"></div>
		<div class="page_and_btn">
			<div class="action">
				<input type="button" id="btn_batch_refund" class="btn" value="批量退款" title="批量退款" />
			</div>
			${sale.page.pageStr }
		</div>
	</div>

	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='saleIds']").prop("checked", true);
			} else {
				$("input[name='saleIds']").prop("checked", false);
			}
		}

		function saleRefund(saleId, isdeal) {
			if (isdeal === '0') {
				if (confirm('确定要退款吗？')) {
					var url = '/orderSale/saleRefund.html';
					var jsonData = 'saleIds=' + saleId;
					$.post(url, jsonData, function(result) {
						alert(result.msg);
						search();
					});
				}
			} else {
				alert('这笔售后订单已经退款过了...');
			}
		}

		function cancelOrder(id) {
			if (confirm("确定要取消售后订单吗？")) {
				var url = "/orderSale/deleteOrder.html?id=" + id;
				$.get(url, function(data) {
					if (data.error == "1") {
						alert(data.msg);
						document.location.reload();
					} else {
						alert(data.msg);
					}
				});
			}
		}

		function search() {
			$("#searchForm").submit();
		}

		$(function(){
			// 批量退款
			$('#btn_batch_refund').click(function () {
				btn_disable($(this));
				var len = $("input[name='saleIds']:checked").length;
				if (len > 0) {
					if (confirm('确认是这些了吗？确认我就要退款了哦？')) {
						var url = '${pageContext.request.contextPath }/orderSale/saleRefund.html';
						$.post(url, $("input[name='saleIds']:checked").serialize(), function(result){
							alert(result.msg);
							window.location.reload();
						});
					}
				} else {
					alert('你还没选订单呢');
					btn_enable($(this));
				}
			});

			$('#btn_today').click(function(){
				var today = date_format(new Date(), '-');
				$('#dateStart').val(today);
				$('#dateEnd').val(today);
			})
		});
	</script>
</body>
</html>
