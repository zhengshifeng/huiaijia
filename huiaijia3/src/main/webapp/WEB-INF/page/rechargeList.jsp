<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后订单</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>充值记录</h3>
		<form action="/recharge/list.html" method="post" name="userForm" id="userForm">
			<div class="filter">
				充值状态：
				<select name="rechargeStatus" id="rechargeStatus" style="vertical-align: middle; margin-right: 15px;">
				    <option value="1" <c:if test="${vo.rechargeStatus==1}">selected</c:if>>充值成功</option>
					<option value="0" <c:if test="${vo.rechargeStatus==0}">selected</c:if>>充值失败</option>
				</select>

				充值金额：
				<select name="rechargeMoney" id="rechargeMoney" style="vertical-align: middle; margin-right: 15px;">
					<option value="">不限</option>
					<option value="10" <c:if test="${vo.rechargeMoney==10}">selected</c:if>>10</option>
					<option value="100" <c:if test="${vo.rechargeMoney==100}">selected</c:if>>100</option>
					<option value="300" <c:if test="${vo.rechargeMoney==300}">selected</c:if>>300</option>
					<option value="500" <c:if test="${vo.rechargeMoney==500}">selected</c:if>>500</option>
					<option value="1000" <c:if test="${vo.rechargeMoney==500}">selected</c:if>>1000</option>
					<option value="2000" <c:if test="${vo.rechargeMoney==500}">selected</c:if>>2000</option>
				</select>

				充值方式：
				<select name="rechargeType" id="rechargeType" style="vertical-align: middle; margin-right: 15px;">
					<option value="">不限</option>
					<option value="1" <c:if test="${vo.rechargeType==1}">selected</c:if>>微信</option>
					<option value="2" <c:if test="${vo.rechargeType==2}">selected</c:if>>支付宝</option>
				</select>

				有无退款：
				<select name="isRefund" id="isRefund" style="vertical-align: middle; margin-right: 15px;">
					<option value="">不限</option>
					<option value="1" <c:if test="${vo.isRefund == '1'}">selected</c:if>>已退款</option>
					<option value="0" <c:if test="${vo.isRefund == '0'}">selected</c:if>>未退款</option>
				</select>
			</div>
			<div class="filter">
				充值日期：
				<input class="inpMain short date_picker" type="text" id="beginTime" name="beginTime" value="${vo.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
				-
				<input class="inpMain short date_picker" type="text" id="endTime" name="endTime" value="${vo.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			</div>
			<div class="filter">
				<input type="text" placeholder="用户名 / 手机号" name="username" class="inpMain" id="username" value="${vo.username }" />
				<input type="text" placeholder="流水号" class="inpMain" name="bankNo" id="bankNo" value="${vo.bankNo }" />
				<input type="text" placeholder="支付帐号" class="inpMain" name="prepay_id" id="prepay_id" value="${vo.prepay_id }" />
				<input class="btn" type="button" value="搜索" onclick="search();" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>序号</th>
						<th>用户名</th>
						<th>手机号</th>
						<th>流水号</th>
						<th>充值金额</th>
						<th>充值状态</th>
						<th>充值方式</th>
						<th>支付帐号</th>
						<th>支付时间</th>
						<th>退款金额</th>
						<th>是否退过款</th>
						<th>退款更新时间</th>
					</tr>
					<c:choose>
						<c:when test="${not empty rechargeList}">
							<c:forEach items="${rechargeList}" var="list" varStatus="vs">
								<tr class="main_info">
									<td>${vs.index+1}</td>
									<td>${list.username }</td>
									<td>${list.mobilePhone }</td>
									<td>${list.bankNo }</td>
									<td>￥${list.money }</td>
									<td>
										<c:choose>
											<c:when test="${list.rechargeStatus == 1 }">
												<span style="color: green;">充值成功</span>
											</c:when>
											<c:when test="${list.rechargeStatus == 0 }">
												<span style="color: red;">充值失败</span>
											</c:when>
											<c:otherwise>未知</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.rechargeType == 1 }">微信</c:when>
											<c:when test="${list.rechargeType == 2 }">支付宝</c:when>
											<c:otherwise>未知</c:otherwise>
										</c:choose>
									</td>
									<td>${list.prepay_id }</td>
									<td>
										<fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>
										<c:choose>
											<c:when test="${empty list.refundNum }">￥0.00</c:when>
											<c:otherwise>￥${list.refundNum }</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.isRefund eq '0'}">未退款</c:when>
											<c:when test="${list.isRefund eq '1'}">已退款</c:when>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty list.refundUpdateTime }">暂无</c:when>
											<c:otherwise>${list.refundUpdateTime }</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="12">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="search_div" style="line-height: 30px; text-align: right;">
			<span style="padding-right: 15px; font-size: 20px;">充值总额：${totalRechargee.sumMoney}</span> <span
				style="padding-right: 15px; font-size: 20px;" id="refund">退款总额(元)：${totalRefund.sumRefundNum}</span> <span
				style="font-size: 20px;"><input class="btnGray" type="button" value="查询退款总额" onclick="query();" /></span>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">${vo.page.pageStr }</div>
	</div>

	<script type="text/javascript">	   
		function search() {
			$("#userForm").submit();
		}

		function query() {
			var endTime = $("#endTime").val();
			var beginTime = $("#beginTime").val();
			if(!endTime || !beginTime) {
				alert('请最好选择时间段来退款查询，时间段最好在一个月之内');
				return;
			}
			$("#refund").text("退款总额(元)：查询中...");
			$.ajax({
				type : "GET",
				url : "/recharge/queryRefund.html",
				contentType : "application/json",
				data : $('#userForm').serialize(),
				dataType : "json",
				success : function(data) {
					$("#refund").text("退款总额(元)：" + data.totalRefund.sumRefundNum);
				}
			});
		}
	</script>
</body>
</html>
