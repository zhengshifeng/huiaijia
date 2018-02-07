<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
	<style>
		.tableBasic.detail th,td{
			width: 150px;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>活动详情</h3>
		<table class="tableBasic detail">
			<tr>
				<th>活动ID:</th>
				<td>${activity.id}</td>
				<th>活动状态:</th>
				<td>
					<c:if test="${activity.status=='1'}">进行中</c:if>
					<c:if test="${activity.status=='0'}">未进行</c:if>
				</td>
				<th>活动名称:</th>
				<td>${activity.activityName}</td>
				<th>活动价:</th>
				<td>${activity.discountAmount}</td>
			</tr>
			<tr>
				<th>购买限制:</th>
				<td>${activity.number}</td>
				<th>活动开始时间:</th>
				<td>${activity.beginTime}</td>
				<th>活动结束时间:</th>
				<td>${activity.endTime}</td>
				<th>活动城市:</th>
				<td>
					<input type="hidden" name="areaCode" value="${userSession.areaCode}"/>
					<c:forEach items="${cityList}" var="city">
						<c:if test="${city.code == list.areaCode}">
							${city.name}
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>活动提交人:</th>
				<td>${activity.operator}</td>
				<th>提交时间:</th>
				<td>
					<fmt:formatDate value="${activity.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<th>活动描述</th>
				<td colspan="3">${activity.describer}</td>
			</tr>
		</table>
	</div>
	<div class="mainBox">
		<h3>活动商品详情</h3>
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>商品编号</th>
					<th>商品名称</th>
					<th>活动价</th>
					<th>汇爱家价</th>
					<th>市场价</th>
					<th>排序</th>
					<th>推荐</th>
					<th>属性</th>
					<th>大分类</th>
					<th>小分类</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityList}">
						<c:forEach items="${commodityList}" var="commodity" varStatus="vs">
							<tr>
								<td>${vs.index+1}</td>
								<td>${commodity.commodityNo}</td>
								<td>${commodity.name}</td>
								<td>${commodity.promotionPrice}</td>
								<td>${commodity.originalPrice}</td>
								<td>${commodity.marketPrice}</td>
								<td>${commodity.sort}</td>
								<td>
									<c:choose>
										<c:when test="${commodity.recommend == 1 }"><span class="text_red">是</span></c:when>
										<c:when test="${commodity.recommend == 0 }">否</c:when>
										<c:otherwise>未知</c:otherwise>
									</c:choose>
								</td>
								<td>${commodity.commodityAttr}</td>
								<td>${commodity.sbTypeName}</td>
								<td>${commodity.scTypeName}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
</body>
</html>
