<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓库列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3><a href="/warehouse/addWarehouse.html" class="actionBtn add">添加仓库</a>添加仓库</h3>
		<form action="/warehouse/getAll.html" method="post" name="userForm" id="userForm">
			<div class="filter">
				<input type="text" class="inpMain" placeholder="仓库编号" name="whcode" value="${warehouse.whcode }" />
				<input type="text" class="inpMain" placeholder="仓库名称" name="whname" value="${warehouse.whname }" />
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>仓库编号</th>
					<th>仓库名称</th>
					<th>创建时间</th>
					<th>描述</th>
				</tr>
				<c:choose>
					<c:when test="${not empty warehouseList}">
						<c:forEach items="${warehouseList}" var="warehouse" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
							<td>${warehouse.whcode }</td>
							<td>${warehouse.whname }</td>
							<td>${warehouse.createTime}</td>
							<td>${warehouse.remark}</td>
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
			<div class="page_and_btn">
				${warehouse.page.pageStr }
			</div>
		</div>
	</div>
</body>
</html>
