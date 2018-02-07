<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/user.html" class="actionBtn">用户管理</a>
		<c:if test="${empty user.userId }">
		添加用户
		</c:if>
		<c:if test="${not empty user.userId }">
		修改用户
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<input type="hidden" name="userId" value="${user.userId }" />
	<table class="tableBasic" style="width: 100%;  ">
		<tr class="info">
			<td width="150px">登录名:</td>
			<td><input type="text" name="loginname" id="loginname" class="inpMain" value="${user.loginname }"/></td>
		</tr>
		<tr class="info">
			<td>密码:</td>
			<td><input type="password" name="password" id="password" class="inpMain" placeholder="******"/></td>
		</tr>
		<tr class="info">
			<td>确认密码:</td>
			<td><input type="password" name="ckbpwd" id="ckbpwd" class="inpMain" placeholder="******"/></td>
		</tr>
		<tr class="info">
			<td>姓名:</td>
			<td><input type="text" name="username" id="username" class="inpMain" value="${user.username }"/></td>
		</tr>
		<tr class="info">
			<td>角色:</td>
			<td>
				<select name="roleId" id="roleId" class="input_txt">
					<option value="">请选择</option>
					<c:forEach items="${roleList}" var="role">
						<option value="${role.roleId }">${role.roleName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<c:if test="${isAdmin}">
			<tr class="info">
				<td>城市:</td>
				<td>
					<select name="areaCode" id="areaCode" class="input_txt">
						<option value="">所有城市</option>
						<c:forEach items="${cityList}" var="city">
							<option value="${city.code }">${city.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</c:if>
		<tr>
			<td></td>
			<td><input id="btn_submit" class="btn" type="button" value="提交" /></td>
		</tr>
	</table>
	</form>	
</div>
<script type="text/javascript">
$(function(){
	$('#btn_submit').click(function(){
		if ($('#ckbpwd').val() === $('#password').val()) {
			var url = 'save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data == 'success') {
					window.location.href = '/user.html';
				} else {
					alert(data);
				}
			});
		} else {
			alert('两次输入的密码不一致！');
		}
	});
	
	$('#roleId').val('${user.roleId}');
	$('#areaCode').val('${user.areaCode}');
});
</script>
</body>
</html>