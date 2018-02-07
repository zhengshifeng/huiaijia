<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改分拣小组</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/hajCommunitySorter/list.html" class="actionBtn">分拣小组管理</a>
			<c:if test="${empty vo.id}">添加分拣小组</c:if>
			<c:if test="${not empty vo.id}">修改分拣小组</c:if>
		</h3>
		<form method="post" name="editForm" id="editForm" onsubmit="return false;">
			<input type="hidden" name="id" value="${vo.id }" />
			<table class="tableBasic" style="width: 100%;">
				<tr>
					<td>分拣小组：</td>
					<td><input class="inpMain" type="text" id="sorterName" name="sorterName" value="${vo.sorterName }"/></td>
				</tr>
				<tr>
					<td style="width: 150px;"></td>
					<td>
						<input id="btn_submit" class="btn" type="button" value="提交" />
					</td>
				</tr>
			</table>
		</form>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#btn_submit').click(function() {
				var url = '/hajCommunitySorter/save.html';
				$.post(url, $('#editForm').serialize(), function(data) {
					if (data == 'success') {
						window.location.href = '/hajCommunitySorter/list.html';
					} else {
						alert(UNKNOWN_ERROR);
					}
				});
			});

			$('#sorterName').on('keypress', function(e) {
				if (e.keyCode == 13) {
					$('#btn_submit').click();
				}
			});
		});
	</script>
</body>
</html>