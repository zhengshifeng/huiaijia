<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发放优惠券</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			发放优惠券详情
		</h3>
		<table class="tableBasic" style="width: 100%;" class="selected">
			<tr>
				<th>ID：</th>
				<td>${vo.id}</td>
			</tr>
			<tr>
				<th>优惠券：</th>
				<td>${vo.name}</td>
			</tr>
			<c:if test="${vo.sendType == 1}">
				<tr>
					<th style="width: 126px;">发放方式：</th>
					<td>系统派发</td>
				</tr>
				<tr>
					<th>是否指定用户：</th>
					<td>
						<c:if test="${userListSize == 0}">
							不指定用户
						</c:if>
						<c:if test="${userListSize > 0}">
							指定用户
						</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${vo.sendType == 2}">
				<tr>
					<th style="width: 126px;">发放方式：</th>
					<td>兑换码兑换</td>
				</tr>
				<tr>
					<th>兑换码：</th>
					<td>${vo.couponNo}</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>
