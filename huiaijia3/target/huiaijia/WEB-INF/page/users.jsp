<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/user/add.html" class="actionBtn add">添加用户</a>用户管理
	</h3>
	<form action="/user.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			<input class="inpMain" type="text" name="loginname" id="loginname" placeholder="用户名" value="${user.loginname }"/>
			
			<input type="text" class="inpMain short date_picker" name="lastLoginStart" onclick="WdatePicker()" readonly="readonly" style="min-width: 70px;"
				value="<fmt:formatDate value="${user.lastLoginStart}" pattern="yyyy-MM-dd"/>" placeholder="登录日期"/> -
			<input type="text" class="inpMain short date_picker" name="lastLoginEnd" onclick="WdatePicker()" readonly="readonly" style="min-width:70px;"
				 value="<fmt:formatDate value="${user.lastLoginEnd}" pattern="yyyy-MM-dd"/>" placeholder="登录日期"/>
			
			<select name="roleId" id="roleId" style="vertical-align: middle;">
				<option value="">角色</option>
				<c:forEach items="${roleList}" var="role">
				<option value="${role.roleId }" <c:if test="${user.roleId==role.roleId}">selected</c:if>>${role.roleName }</option>
				</c:forEach>
			</select>
			<input class="btn" type="submit" value="搜索" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;  "
				class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>用户名</th>
					<th>名称</th>
					<th>角色</th>
					<th>最近登录</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty userList}">
						<c:forEach items="${userList}" var="user" varStatus="vs">
						<tr>
							<td>${user.userId}</td>
							<td>${user.loginname }</td>
							<td>${user.username }</td>
							<td>${user.role.roleName }</td>
							<td><fmt:formatDate value="${user.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<a href="user/edit.html?userId=${user.userId }">修改</a> | 
								<a href="javascript:delUser(${user.userId });">删除</a>
							</td>
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
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<input id="btn_export" class="btn" type="button" value="导出" />
		</div>
		${user.page.pageStr }
	</div>
</div>

<script type="text/javascript">
function sltAllUser(){
	if($("#sltAll").prop("checked")){
		$("input[name='userIds']").prop("checked",true);
	}else{
		$("input[name='userIds']").prop("checked",false);
	}
}

function delUser(userId){
	if(confirm("确定要删除该记录？")){
		var url = "user/delete.html?userId="+userId;
		$.get(url,function(data){
			if(data=="success"){
				document.location.reload();
			}
		});
	}
}

$(function(){
	$("#btn_export").click(function(){
		document.location = "user/excel.html";
	});
});
</script>
</body>
</html>
