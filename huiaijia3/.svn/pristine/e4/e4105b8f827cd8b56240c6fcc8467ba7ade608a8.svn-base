<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值套餐记录管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
 	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>

</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		充值套餐记录管理
	</h3>
	<form action="/rechargePackageRecord/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			请选择：
			<input type="text" class="inpMain" placeholder="用户id/ 手机号码" name="name" id="name" value="${hajRechargePackageRecord.name}" />
			日期：
			<input class="inpMain short date_picker" type="text" name="beginTime" value="${hajRechargePackageRecord.beginTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			-
			<input class="inpMain short date_picker" type="text" name="endTime" value="${hajRechargePackageRecord.endTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			支付方式：
			<select name="payWay" id="payWay" style="vertical-align: middle; margin-right: 15px;">
				<option value="">不限</option>
 				<option value="1" <c:if test="${hajRechargePackageRecord.payWay==1}">selected</c:if>>微信</option>
				<option value="2" <c:if test="${hajRechargePackageRecord.payWay==2}">selected</c:if>>支付宝</option>
			</select>
			支付状态：
			<select name="payStatus" id="payStatus" style="vertical-align: middle; margin-right: 15px;">
				<option value="">不限</option>
				<option value="0" <c:if test="${hajRechargePackageRecord.payStatus==0}">selected</c:if>>失败</option>
				<option value="1" <c:if test="${hajRechargePackageRecord.payStatus==1}">selected</c:if>>成功</option>
			</select>
			<input class="btn" type="submit" value="搜索" />
		</div>

	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>客户ID</th>
					<th>手机号码</th>
					<th>套餐名称</th>
					<th>购买金额</th>
					<th>赠送金额</th>
					<th>到账金额</th>
					<th>支付时间</th>
					<th>支付状态</th>
					<th>支付方式</th>
					<th>支付流水号</th>
					<th>退款金额</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty rechargePackageRecordList}">
						<c:forEach items="${rechargePackageRecordList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>
									${list.id}
							</td>
							<td>${list.userId}</td>
							<td>${list.phone}</td>
							<td>${list.name}</td>
							<td>${list.purchaseAmount }</td>
							<td>${list.donationAmount }</td>
							<td>${list.accountAmount }</td>
							<td>
								<fmt:formatDate value="${list.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.payStatus == 0 }"><span class="text_green">失败</span></c:when>
									<c:when test="${list.payStatus == 1}"><span class="text_green">成功</span></c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
 									<c:when test="${list.payWay == 1 }"><span class="text_green">微信</span></c:when>
									<c:when test="${list.payWay == 2 }"><span class="text_green">支付宝</span></c:when>
  									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>${list.payNumber}</td>
							<td>${list.refundAmount}</td>
							<td>
								<a href="/rechargePackageRecord/detail.html?id=${list.id }">查看详情</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="14">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>


	<div class="search_div" style="line-height: 30px; text-align: right;">
		<span style="padding-right: 15px; font-size: 20px;">购买金额：${totalChargePackageRecord.TotalPrchaseAmount}元</span> <span
			style="padding-right: 15px; font-size: 20px;">赠送金额：${totalChargePackageRecord.TotalDonationAmount}元</span>
		<span
				style="padding-right: 15px; font-size: 20px;">到账金额：${totalChargePackageRecord.TotalAccountAmount}元</span>
		<span
				style="padding-right: 15px; font-size: 20px;">退款金额：${totalChargePackageRecord.TotalRefundAmount}元</span>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div>
			<input type="button" id="btn_export_ChargePackageRecord" class="btn" value="导出列表" title="导出列表" />
		</div>
		${hajRechargePackageRecord.page.pageStr}
	</div>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript">

    $('#btn_export_ChargePackageRecord').click(function () {
        btn_disable($(this));
        document.location = "excel.html?" + $('#searchForm').serialize();
        setTimeout('btn_enable($("#btn_export_ChargePackageRecord"))', 10000);
    });

</script>
</body>
</html>