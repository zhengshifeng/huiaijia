<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/role.html" class="actionBtn">角色管理</a>
		<c:if test="${empty role.roleId }">
		添加角色
		</c:if>
		<c:if test="${not empty role.roleId }">
		修改角色
		</c:if>
	</h3>
	
	<form name="editForm" id="editForm" method="post" >
	<c:if test="${not empty role.roleId }">
	<input type="hidden" name="roleId" value="${role.roleId }" />
	</c:if>
	<table class="tableBasic list" style="width: 100%;  ">
		<tr>
			<td width="150px">角色名称：</td>
			<td><input type="text" name="roleName" id="roleName" class="inpMain" value="${role.roleName }"/></td>
		</tr>
	</table>
	</form>	
</div>
</body>
</html>