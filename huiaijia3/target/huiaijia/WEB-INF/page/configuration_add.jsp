<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加配置</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="list.html" class="actionBtn">缓存配置</a>
		<c:if test="${empty configuration.id }">
		添加配置
		</c:if>
		<c:if test="${not empty configuration.id }">
		修改配置
		</c:if>
	</h3>
	
	<form name="editForm" id="editForm" method="post" >
	<c:if test="${not empty configuration.id }">
	<input type="hidden" name="id" value="${configuration.id }" />
	</c:if>
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td width="150px">配置名称：</td>
			<td><input type="text" name="name" id="name" class="inpMain" value="${configuration.name }"/></td>
		</tr>
		<tr>
			<td>配置值:</td>
			<td>
				<textarea rows="4" cols="50" name="value" id="value" class="textArea">${configuration.value }</textarea>
			</td>
		</tr>
		<tr>
			<td>描述:</td>
			<td>
				<input type="text" name="configDescribe" id="configDescribe" class="inpMain" value="${configuration.configDescribe }"/>
			</td>
		</tr>
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
		var url = 'save.html';
		$.post(url, $('#editForm').serialize(), function(data){
			if (data == 'success') {
				window.location.href = document.referrer;
			} else {
				alert('未知异常，可能该配置名已存在');
				$('#name').focus();
			}
		});
	});
});
</script>
</body>
</html>