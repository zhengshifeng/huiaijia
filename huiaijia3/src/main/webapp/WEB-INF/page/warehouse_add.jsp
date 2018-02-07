<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加仓库</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3><a href="/warehouse/getAll.html" class="actionBtn add">仓库列表</a>仓库列表</h3>
	<form name="editForm" id="editForm" method="post" >
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td width="150px">仓库编号：</td>
			<td><input type="text" name="whcode" id="whcode" class="inpMain" ></td>
		</tr>
		<tr>
			<td width="150px">仓库名称：</td>
			<td><input type="text" name="whname" id="whname" class="inpMain" ></td>
		</tr>
		<tr>
			<td>描述:</td>
			<td>
				<input type="text" name="remark" id="remark" class="inpMain"/>
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
		var url = '/warehouse/saveWarehouse.html';
		$.post(url, $('#editForm').serialize(), function(data){
			if (data == 'success') {
				window.location.href = "/warehouse/getAll.html";
			} else {
				alert('未知异常，可能仓库已存在');
				$('#name').focus();
			}
		});
	});
});
</script>
</body>
</html>