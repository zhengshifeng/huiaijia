<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类目管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/category/add.html?areaCode=${vo.areaCode }" class="actionBtn add">添加商品类目</a>商品类目管理
		</h3>
		<div id="list">
			<form name="searchForm" action="/category/list.html" method="get">
				<input type="hidden" name="areaCode" value="${vo.areaCode }"/>
			</form>
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>名称</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty categoryList}">
						<c:forEach items="${categoryList}" var="list" varStatus="vs">
							<tr>
								<td>${list.id}</td>
								<td>${list.name }</td>
								<td>${list.sort }</td>
								<td>
									<a href="/category/edit.html?id=${list.id }" >修改</a> |
									<a href="javascript:;" onclick="rm(${list.id }, '${list.name }')">删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<div class="page_and_btn">
				${vo.page.pageStr }
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function rm(id, name) {
			var layer = window.parent.layer;

			layer.confirm('确定要删除【'+name+'】吗？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				var url = '/category/delete.html?id='+id;
				$.get(url, function(data){
					if (data === 'success') {
						layer.msg('已删除', {icon: 1});
						window.location.reload();
					} else {
						alert(UNKNOWN_ERROR);
					}
				});
			});
		}
	</script>
</body>
</html>