<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品品牌管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/commodityBrand/add.html" class="actionBtn add">添加品牌</a>商品品牌管理
		</h3>
		<form name="searchForm" action="/commodityBrand/list.html" method="get">
			<div class="filter">
				关键词搜索：
				<input type="text" class="inpMain" name="name" value="${vo.name }" />
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>名称</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityBrandList}">
						<c:forEach items="${commodityBrandList}" var="list" varStatus="vs">
							<tr>
								<td>${list.id}</td>
								<td>${list.name}</td>
								<td>${list.remark }</td>
								<td>
									<a href="/commodityBrand/edit.html?id=${list.id }" >修改</a>
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
</body>
</html>