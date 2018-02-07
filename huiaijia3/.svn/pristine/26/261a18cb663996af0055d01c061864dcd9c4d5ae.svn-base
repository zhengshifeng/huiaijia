<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优惠券【${couponInfo.name}】领取和使用详情</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>优惠券【${couponInfo.name}】领取和使用详情</h3>
		<div id="list">
			<form name="searchForm" action="/hajCouponInfo/useInfo.html" method="post">
				<input type="hidden" name="id" value="${couponInfo.id}" />
			</form>
			<form name="listForm" id="listForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>序号</th>
						<th>手机号码</th>
						<th>小区名称</th>
						<th>领取时间</th>
						<th>领取方式</th>
						<th>使用情况</th>
						<th>有效期</th>
						<th>订单号</th>
						<th>订单实付</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty couponInfoList}">
							<c:forEach items="${couponInfoList}" var="coupon" varStatus="vs">
								<tr class="main_info">
									<td>${vs.index + 1 }</td>
									<td>${coupon.mobilePhone }</td>
									<td>${coupon.communityName }</td>
									<td><fmt:formatDate value="${coupon.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<c:if test="${coupon.sendType == 1}">系统发放</c:if>
										<c:if test="${coupon.sendType == 2}">兑换码兑换</c:if>
									</td>
									<td>
										<c:if test="${coupon.isUsed == 0 or coupon.orderStatus == 1 or coupon.orderStatus == 9}">
											<span class="text_green">未使用</span>
										</c:if>
										<c:if test="${coupon.isUsed == 1 and (coupon.orderStatus > 1 and coupon.orderStatus < 9)}">
											<span class="text_red">已使用</span>
										</c:if>
									</td>
									<td>
										<c:if test="${coupon.isValidate == 0}"><span class="text_green">有效</span></c:if>
										<c:if test="${coupon.isValidate == 1}"><span class="text_invalid">失效</span></c:if>
									</td>
									<td>${coupon.orderNo}</td>
									<td>${coupon.actualPayment}</td>
									<td>
										<c:if test="${not empty coupon.orderId}">
										<a href="/order/orderDetail.html?orderId=${coupon.orderId}">使用详情</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="10">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">
			<div class="action">
				<input type="button" id="btn_export" class="btn" value="全部下载" title="全部下载" />
			</div>
			${vo.page.pageStr }
		</div>
	</div>

	<script>
		$(function() {
			// 批量导出
			$('#btn_export').click(function(){
				document.location = "/hajCouponInfo/export2excel.html?id=${couponInfo.id}&name=${couponInfo.name}";

				window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
					btn: ['关闭'] //按钮
				});

			});
		});
	</script>
</body>
</html>
