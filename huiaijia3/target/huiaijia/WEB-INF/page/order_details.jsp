<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>订单详情</h3>
		<table class="tableBasic" style="margin-bottom: 15px;">
			<tr>
				<th>订单号:</th>
				<td>${orderMap.orderNo}</td>
				<th>下单时间:</th>
				<td>${orderMap.createTime}</td>
				<th>订单状态:</th>
				<td>
					<c:if test="${orderMap.status == 1}">等待付款</c:if>
					<c:if test="${orderMap.status == 2}">等待配送</c:if>
					<c:if test="${orderMap.status == 3}">已发货</c:if>
					<c:if test="${orderMap.status == 4}">已完成</c:if>
					<c:if test="${orderMap.status == 5}">待退款</c:if>
					<c:if test="${orderMap.status == 6}">已取消</c:if>
					<c:if test="${orderMap.status == 7}">退款完成</c:if>
				</td>
				<th>小区:</th>
				<td>${orderMap.residential}</td>
				<th>小区地址:</th>
				<td colspan="3">${orderMap.address}</td>
			</tr>
			<tr>
				<th>收件人:</th>
				<td>${orderMap.receiver}</td>
				<th>联系方式:</th>
				<td>${orderMap.contactPhone}</td>
				<th>配送员:</th>
				<td>${orderMap.name}</td>
				<th>联系方式:</th>
				<td>${orderMap.telphone}</td>
				<th>订单售后:</th>
				<td>${orderMap.orderSales}</td>
				<th>联系方式:</th>
				<td>${orderMap.orderSalesPhone}</td>
			</tr>
			<tr>
				<th>订单总价:</th>
				<td>￥${orderMap.totalMoney}</td>
				<th>优惠金额:</th>
				<td>￥${orderMap.feeWaiver}</td>
				<th>优惠券优惠:</th>
				<td>￥${orderMap.counponMoney}</td>
				<th>商品实收:</th>
				<td>￥${orderMap.actualPayment}</td>
				<th>配送小费:</th>
				<td>￥${orderMap.dispensingTip}</td>
				<th>订单实付:</th>
				<td>￥${orderMap.actualPayment+orderMap.dispensingTip+orderMap.postFee}</td>
			</tr>
			<tr>
				<th>客服备注:</th>
				<td colspan="11">
					<textarea class="textArea" readonly="readonly" style="width: 99%; height: 100px; border: none;overflow: visible">${orderMap.remark}</textarea>
				</td>
			</tr>
			<tr>
				<th>整单售后记录:</th>
				<td colspan="11">
					<table class="tableBasic">
						<c:choose>
							<c:when test="${not empty orderProblemList}">
								<c:forEach items="${orderProblemList}" var="problem" varStatus="vs">
									<tr>
										<td>${vs.index+1}，${problem.record}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td>无</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
		</table>
		
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table class="tableBasic list">
					<tr>
						<th colspan="8">商品列表</th>
					</tr>
					<tr>
						<th>序号</th>
						<th>属性 - 大分类 - 小分类</th>
						<th>商品名称</th>
						<th>商品规格</th>
						<th>商品数量</th>
						<th>商品实收金额</th>
						<th>商品售后记录</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty detailsList}">
							<c:forEach items="${detailsList}" var="detail" varStatus="vs">
								<tr class="main_info">
									<td>${vs.index+1}</td>
									<td>${detail.commodityAttr} - ${detail.sbTypeName} - ${detail.scTypeName}</td>
									<td>${detail.commodityName}</td>
									<td>${detail.weight}</td>
									<td>${detail.number}</td>
									<td>￥${detail.actualPayment}</td>
									<td>${detail.afterSaleRecord}</td>
									<td>
										<c:if test="${(orderMap.status > 2 && orderMap.status < 6) || orderMap.status == 7}">
										<input type="button" class="btn after_sale" value="售后"
											data-order="${orderMap.orderId}"
										  	data-commodity="${detail.commodityNo}"
											data-detail="${detail.id}"
										/>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="6">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn"></div>

		<c:if test="${(orderMap.status > 2 && orderMap.status < 6) || orderMap.status == 7}">
		<!-- 申请退款 -->
		<div>
			<form id="saleForm">
			<input type="hidden" id="orderId" name="orderId" value="${orderMap.orderId}" />
			<input type="hidden" id="cellPhone" name="cellPhone" value="${orderMap.contactPhone}" />
			<input type="hidden" name="obj" value="0" />
			<input type="hidden" name="number" value="1" />
			<table class="tableBasic" style="float: left">
				<tr>
					<th colspan="3">整单售后</th>
				</tr>
				<tr>
					<th>处理办法</th>
					<th>操作</th>
					<th>备注说明</th>
				</tr>
				<tr>
					<td>
						<select id="method" name="method">
							<option value="">请选择处理办法</option>
							<option value="0">补发</option>
							<option value="1">退款</option>
						</select>
					</td>
					<td>
						<select id="type" name="type">
							<option value="">请选择问题类型</option>
							<option value="1">未送达</option>
							<option value="0">其他</option>
						</select>
					</td>
					<td>
						<input type="text" id="quersion" class="inpMain" name="quersion" placeholder="其他备注" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						责任部门：
						<label><input type="checkbox" name="depts" value="1"/>生产部</label>
						<label><input type="checkbox" name="depts" value="2"/>质检部</label>
						<label><input type="checkbox" name="depts" value="3"/>配送部</label>
						<label><input type="checkbox" name="depts" value="4"/>采购部</label>
						<label><input type="checkbox" name="depts" value="5"/>仓储部</label>
						<label><input type="checkbox" name="depts" value="0"/>无法判断</label>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="button" class="btn" id="btn_refund" value="提交售后" title="提交售后" disabled="disabled" />
					</td>
				</tr>
			</table>
			</form>
		</div>
		</c:if>
	</div>

	<script type="text/javascript">
		// 售后退款
		function saleRefund() {
			var money = $("#money").val();
			if (!money) {
				alert('退款的话，要写退款金额哦，你484洒');
				return false;
			}
			var quersion = $("#quersion").val();
			if (!quersion) {
				alert('为啥要整单退款呢？备注一下原因。\r但是你所写的一切，都将成为呈堂证供[奸笑]');
				return false;
			}

			if (confirm("确定要添加到售后退款单吗？")) {
				btn_disable($('#btn_refund'));
				$('#btn_refund').css('background-color', '#60BBFF');

				var url = "/orderSale/save.html";
				$.post(url, $('#saleForm').serialize(), function(data) {
					if (data.error == '0') {
						alert('退款售后已提交，请前往确认');
						$('#btn_refund').val('已申请');
						window.location.href = '/orderSale/getAll.html';
					} else {
						alert(data.msg);
						btn_enable($('#btn_refund'));
						$('#btn_refund').css('background-color', '#0072C6');
					}
				});
			}
		}

		// 补单
		function reorder() {
			var quersion = $("#quersion").val();
			if (!quersion) {
				alert('为啥要补发整单呢？备注一下原因。\r但是你所写的一切，都将成为呈堂证供[奸笑]');
				return false;
			}
			if (confirm("确定要整单给用户补发吗？")) {
				btn_disable($('#btn_refund'));
				$('#btn_refund').css('background-color', '#60BBFF');

				var url = "/order/reorder.html";
				$.post(url, $('#saleForm').serialize(), function(data) {
					if (data.error == '0') {
						alert('商品补发成功，请检查');
						$('#btn_refund').val('已申请');
						window.location.href = '/order/list.html';
					} else {
						alert(data.msg);
						btn_enable($('#btn_refund'));
						$('#btn_refund').css('background-color', '#0072C6');
					}
				});
			}
		}

		$(function () {
			$('#btn_refund').click(function () {
				var method = $("#method").val();
				if (method) {
					var type = $("#type").val();
					if (!type) {
						alert('你还没选问题类型呢');
						return false;
					}

					var len = $("input[name='depts']:checked").length;
					if (len < 1) {
						alert('责任部门是一定要选的哦');
						return false;
					}

					if (method == 1) {
						saleRefund();
					} else if (method == 0) {
						reorder();
					}
				}
			});

			var money_html = '<input type="text" id="money" name="money" class="inpMain short"' +
				'onkeyup="value=value.replace(/[^\\d\\.\\d{2}]/g, \'\')"' +
				'placeholder="剩余 ￥${maxRefundAmount} 可退" />';
			$('#method').change(function () {
				var method_val = $(this).val();
				if (method_val) {
					$('#btn_refund').removeProp('disabled');
					if (method_val == 1) {
						$('#type').after(money_html);
					} else {
						$('#money').remove();
					}
				} else {
					$('#money').remove();
				}
			});

			$('.after_sale').click(function () {
				var problemList = '${orderProblemList}';
				if (problemList && problemList != '' && problemList != '[]') {
					alert('您已进行整单售后了，不可对商品再进行操作');
				} else {
					window.location.href = '/orderSale/afterSaleEdit.html?' +
						'orderId=' + $(this).attr('data-order') +
						'&commodityNo=' + $(this).attr('data-commodity') +
						'&detailId=' + $(this).attr('data-detail')
					;
				}
			});
		});
	</script>
</body>
</html>
