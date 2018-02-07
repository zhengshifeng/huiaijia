<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择优惠券类型</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style>
		div.btn {
			width: 250px;
			height: 100px;
			line-height: 100px;
			color: #fff;
			text-align: center;
			vertical-align: middle;
			font-size: 24px;
			margin-right: 10px;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>选择优惠券类型</h3>
		<div class="btn" id="coupon_1">商品满减优惠券</div>
		<div class="btn" id="coupon_2">品类品牌优惠券</div>
	</div>
<script>
	$(function () {
		$('#coupon_1').click(function () {
			window.location.href = '/hajCouponInfo/list.html?category=1';
		});
		$('#coupon_2').click(function () {
			window.location.href = '/hajCouponInfo/list.html?category=2';
		});
	});
</script>
</body>
</html>
