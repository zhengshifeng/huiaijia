<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>专题管理</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/hajSpecialTopic/add.html" class="actionBtn add">添加专题</a>
		专题管理
	</h3>
	<!-- 此form用于分页 -->
	<form action="/hajSpecialTopic/list.html" method="post" name="searchForm">
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>专题名称</th>
					<th>活动城市</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty specialTopicList}">
						<c:forEach items="${specialTopicList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>${list.id }</td>
								<td>${list.name }</td>
								<td>
									<c:choose>
										<c:when test="${list.areaCode == '100002' }">
											深圳市
										</c:when>
										<c:when test="${list.areaCode == '100010' }">
											广州市
										</c:when>
									</c:choose>
								</td>
								<td><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<c:choose>
										<c:when test="${list.invalid == 1 }">
											<span>未启用</span>
										</c:when>
										<c:when test="${list.invalid == 0 }">
											<span class="text_green">进行中</span>
										</c:when>
										<c:otherwise>未知</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="/hajSpecialTopic/edit.html?id=${list.id }">修改</a>&nbsp;|&nbsp;
									<a href="/hajSpecialTopicCommodity/list.html?specialTopicId=${list.id }">查看商品</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="7">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
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
