<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单售后</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript">
	function trade(page) {
		if (page == 1) {
			alert("当前页已为第一页！");
			return;
		}
	}
</script>
</head>
<body>
	<div class="filter">
		<table class="tableBasic list" style="width: 100%;">
			<tr class="main_info">
				<th colspan="4" height="15px" align="center">订单售后</th>
			</tr>
			<tr class="main_info">
				<th align="center">评价人</th>
				<th align="center">联系电话</th>
				<th align="center">评价内容</th>
				<th align="center">快速评价</th>
			</tr>
			<c:choose>
				<c:when test="${not empty commentList}">
					<c:forEach items="${commentList}" var="comment" varStatus="vs">
						<tr class="main_info">
							<td>${comment.commentName }</td>
							<td>${comment.contactPhone }</td>
							<td>${comment.content }</td>
							<td>${comment.quickComment }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="4" align="center">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<div style="text-align: center;">
			<c:if test="${(page-1) == 0}">
				<a class="btn" href="javascript:trade(${page });">上一页</a>
			</c:if>
			<c:if test="${(page-1) > 0}">
				<a class="btn" href="afterSaleComment.html?communityId=${communityId }&page=${page -1}" target="comment">上一页</a>
			</c:if>
			<a class="btn" href="afterSaleComment.html?communityId=${communityId }&page=${page +1}" target="comment">下一页</a>
		</div>
	</div>
</body>
</html>