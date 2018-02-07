<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vo.remark3 }交易历史</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>【${vo.remark3 }】的交易历史</h3>
	<!-- 用于分页 -->
	<form action="/tradingHistory/list.html" method="post" name="searchForm">
		<input type="hidden" name="userId" value="${vo.userId }"/>
		<input type="hidden" name="remark3" value="${vo.remark3 }"/>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;"
				class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>交易时间</th>
					<th>交易内容</th>
					<th>交易金额</th>
					<th>交易状态</th>
				</tr>
				<c:choose>
					<c:when test="${not empty tradingHistoryList}">
						<c:forEach items="${tradingHistoryList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>${list.id }</td>
							<td>${list.createTime }</td>
							<td>${list.tradingContent }</td>
							<td>${list.money }</td>
							<td>
								<c:choose>
									<c:when test="${list.tradingStatus == 1 }"><span class="text_green">成功</span></c:when>
									<c:when test="${list.tradingStatus == 0 }">失败</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="5">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">${vo.page.pageStr }</div>
</div>
</body>
</html>
