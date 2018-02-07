<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户 ${vo.username } 关键资料修改日志</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>用户 ${vo.username } 关键资料修改日志</h3>
	<!-- 用于分页 -->
	<form action="/frontUserUpdateLog/list.html" method="post" name="searchForm">
		<input type="hidden" name="frontUserId" value="${vo.frontUserId }"/>
		<input type="hidden" name="username" value="${vo.username }"/>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;"
				class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>修改时间</th>
					<th>操作内容</th>
					<th>操作者</th>
				</tr>
				<c:choose>
					<c:when test="${not empty frontUserUpdateLogList}">
						<c:forEach items="${frontUserUpdateLogList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>${list.id }</td>
							<td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${list.record }</td>
							<td>${list.operator }</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
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
