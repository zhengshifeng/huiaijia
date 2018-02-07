<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/frontUserUpdateLog/list.html?frontUserId=${userManager.id }&username=${userManager.username}"
		   class="actionBtn" style="margin-left: 7px;">信息修改记录</a>
		<%--<a href="/tradingHistory/list.html?userId=${userManager.id }&remark3=${userManager.username}"--%>
		   <%--class="actionBtn" style="margin-left: 7px;">运费宝使用明细</a>--%>
		<a href="/tradingHistory/list.html?userId=${userManager.id }&remark3=${userManager.username}"
		   class="actionBtn" style="margin-left: 7px;">余额使用明细</a>
		<a href="/integralDetails/list.html?userId=${userManager.id }"
		   class="actionBtn" style="margin-left: 7px;">积分使用明细</a>
		<a href="/getHajUserManagerById.html?parameter=${userManager.id }&page=customerUpdate"
		   class="actionBtn" style="margin-left: 7px;">进入编辑</a>
		客户详情
	</h3>
	<strong>基础信息 : </strong>
	<div class="filter" style="height: 250px;">
		<input type="hidden" name="id" id="id" value="${userManager.id }"/>
		<table class="tableBasic" style="width: 100%;  ">
			<tr>
				<th>用户ID</th>
				<td>${userManager.id}</td>
				<th>用户角色</th>
				<td>
					<c:if test="${userManager.ismember eq '1'}">仅注册</c:if>
					<c:if test="${userManager.ismember eq '2'}">预备会员</c:if>
					<c:if test="${userManager.ismember eq '3'}">一元购会员</c:if>
					<c:if test="${userManager.ismember eq '4'}">会员取消</c:if>
				</td>
				<th>用户名称</th>
				<td>${userManager.username}</td>
				<th>手机号码</th>
				<td>${userManager.mobilePhone }</td>
			</tr>
			<tr>
				<th>用户性别</th>
				<td>
					<c:if test="${userManager.sex eq '0'}">女</c:if>
					<c:if test="${userManager.sex eq '1'}">男</c:if>
				</td>
				<th>出生日期</th>
				<td>${userManager.birthday }</td>
				<th>用户职业</th>
				<td>${userManager.occupation }</td>
				<th>家庭成员</th>
				<td>${userManager.familymembers }</td>
			</tr>
			<tr>
				<th>小区名称</th>
				<td>${userManager.residential }</td>
				<th>小区地址</th>
				<td>${userManager.shippingAddress  }</td>
				<th>用户住址</th>
				<td>${userManager.address }</td>
				<th>账户余额</th>
				<td>${userManager.balance }（元）</td>
			</tr>
			<tr>
				<th>收货人</th>
				<td>${userManager.receiver  }</td>
				<th>联系方式</th>
				<td>${userManager.mobilePhone }</td>
				<th>会员有效期(天)</th>
				<td>${userManager.memberTerm }</td>
				<th>会员开始日期</th>
				<td>${userManager.memberBeginTime }</td>
			</tr>
			<tr>
				<th>会员结束日期</th>
				<td>${userManager.memberOverDate }</td>
				<th>签到次数(天)</th>
				<td>${userManager.signNum }</td>
				<th>历史订单数(笔)</th>
				<td>${userManager.orderNum }</td>
				<th>用户积分</th>
				<td>${userManager.rating }</td>
			</tr>
			<tr>
				<th>手机机型</th>
				<td>${userManager.phoneModel }</td>
				<th>最后登录时间</th>
				<td>${userManager.lastLoginTime }</td>
				<th>正在使用的APP版本</th>
				<td>${userManager.appVersion }</td>

				<th>是否预约</th>
				<td>
					<c:if test="${userManager.isAppointment eq '1'}">是</c:if>
					<c:if test="${userManager.isAppointment eq '0'}">否</c:if></td>
			<tr>
			</tr>
			<tr>
				<th>预约时间</th>
				<td>${userManager.appointmentTime }</td>
				<th>取消会员反馈</th>
				<td colspan="7">${userManager.cancelFeedBack }</td>
			</tr>
		</table>
	</div>
	<div class="clear"></div>
	<strong>订单记录:</strong>
		<div class="filter" style="width: 100%;">
			<table class="tableBasic" style="width: 48%; float: left;">
				<thead>
				<tr>
					<th colspan="5">订单统计</th>
				</tr>
				<tr>
					<th style="width: 150px;">日期筛选</th>
					<td colspan="5">
						<input type="text" class="inpMain short date_picker" name="beginTime" id="beginTime"
							   onclick="WdatePicker()" readonly="readonly" value=""/>
						-
						<input type="text" class="inpMain short date_picker" name="overTime" id="overTime"
							   onclick="WdatePicker()" readonly="readonly" value=""/>
						<input name="search" class="btn" type="button" id="search" value="搜索"/>
					</td>
				</tr>
				</thead>
				<tbody id="statisticsOrderBody">
				</tbody>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		$(function () {
			$("#search").click(function () {
				var beginTime = $("#beginTime").val();
				var overTime = $("#overTime").val();
				var id = $("#id").val();
				var reg = /^\d{4}-\d{2}-\d{2}$/;
				if (reg.test($.trim(beginTime)) && reg.test($.trim(overTime))) {
					$.ajax({
						type: "GET",
						url: "statisticsOrderByUser.html",
						contentType: "application/json",
						data: {
							"beginTime": $.trim(beginTime),
							"overTime": $.trim(overTime),
							"id": id
						},
						dataType: "json",
						success: function (data) {
							$('#statisticsOrderBody').empty();
							var html_order = '<tr><th>订单数（笔）</th>' +
								'<td colspan="5">' + data.statisticsOrder.orderCount + '</td>' +
								'</tr>';
							if (data.statisticsOrder.actualPayment) {
								html_order = html_order + '<tr><th>平均订单金额（元）</th>' +
									'<td colspan="5">￥' + data.statisticsOrder.actualPaymentAvg + '</td>' +
									'</tr>';
							}
							if (data.statisticsOrder.actualPayment) {
								html_order = html_order + '<tr><th>订单合计金额（元）</th>' +
									'<td colspan="5">￥' + data.statisticsOrder.actualPayment + '</td>' +
									'</tr>';
							}
							if (data.statisticsOrderCommodityType) {
								var list = data.statisticsOrderCommodityType;
								for (var i = 0; i < list.length; i++) {
									html_order = html_order + '<tr><th>' + list[i].typeName + '（元）</th>' +
										'<td colspan="5">￥' + list[i].actualPayment + '</td>' +
										'</tr>';
								}
							}
							$('#statisticsOrderBody').append(html_order);

						}
					});
				} else {
					alert("请选择日期范围");
				}
			});
		});
	</script>
</div>
</body>
</html>
