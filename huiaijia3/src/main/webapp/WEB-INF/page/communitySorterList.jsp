<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>分拣小组管理</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/hajCommunitySorter/add.html" class="actionBtn add">添加分拣小组</a>
		分拣小组管理
	</h3>
	<!-- 此form用于分页 -->
	<form action="/hajCommunitySorter/list.html" method="post" name="searchForm">
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>分拣小组</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty communitySorterList}">
						<c:forEach items="${communitySorterList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>${list.id }</td>
								<td>${list.sorterName }</td>
								<td>
									<a href="/hajCommunitySorter/edit.html?id=${list.id }">修改</a>&nbsp;|&nbsp;
									<a href="javascript:delSorter(${list.id });">删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="3">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">${vo.page.pageStr }</div>
</div>

<script type="text/javascript">
	function delSorter(id) {
		if (confirm('真的要删吗？')) {
			if (!id) return false;
			var url = '/hajCommunitySorter/delete.html?id=' + id;
			$.get(url, function (data) {
				if (data == 'success') {
					window.location.reload();
				} else {
					alert(UNKNOWN_ERROR);
				}
			});
		}
	}
</script>
</body>
</html>
