<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>积分明细</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		积分明细
	</h3>
	<div id="list">
		<table style="width: 100%;" class="tableBasic list">
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>名称</th>
				<th>积分</th>
				<th>时间</th>
			</tr>
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach items="${list}" var="list" varStatus="vs">
						<tr>
							<td>${vs.index + 1}</td>
							<td>${list.id}</td>
							<td>${list.remark}</td>
							<td>${list.detail}</td>
							<td>
								<fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="5">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<div class="page_and_btn">
			${vo.page.pageStr}
		</div>
	</div>
</div>
</body>
</html>