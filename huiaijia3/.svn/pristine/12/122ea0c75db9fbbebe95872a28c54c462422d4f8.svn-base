<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加菜单</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/menu.html" class="actionBtn">菜单管理</a>
			<c:if test="${empty menu.menuId }">添加菜单</c:if>
			<c:if test="${not empty menu.menuId }">修改菜单</c:if>
		</h3>

		<form method="post" name="editForm" id="editForm">
			<c:if test="${not empty menu.menuId }">
				<input type="hidden" name="menuId" value="${menu.menuId }" />
			</c:if>
			<table class="tableBasic" style="width: 100%;  ">
				<tr>
					<td>从属菜单:</td>
					<td>
						<select name="parentId" id="parentId" onchange="setMUR()">
							<option value="">根目录</option>
							<c:forEach items="${menuList}" var="menu">
								<option value="${menu.menuId }">${menu.menuName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>菜单名:</td>
					<td>
						<input type="text" name="menuName" id="menuName" class="inpMain" value="${menu.menuName }" />
					</td>
				</tr>
				<tr>
					<td>图标:</td>
					<td>
						<input type="text" name="icon" id="icon" class="inpMain" value="${menu.icon }" placeholder="可不填" />
					</td>
				</tr>
				<tr>
					<td>资源路径:</td>
					<td>
						<input type="text" name="menuUrl" id="menuUrl" class="inpMain" value="${menu.menuUrl }" />
					</td>
				</tr>
				<tr>
					<td>排序:</td>
					<td>
						<input type="number" name="sort" class="inpMain short" value="${menu.sort }" />
					</td>
				</tr>
				<tr>
					<td width="150px;"></td>
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
				var url = '/menu/save.html';
				$.post(url, $('#editForm').serialize(), function(data) {
					if (data == 'success') {
						window.location.href = document.referrer;
					} else {
						alert('未知错误');
					}
				});
			});

			$("#parentId").val('${menu.parentId}');
			setMUR();
		});

		function setMUR() {
			if (!$("#parentId").val()) {
				$("#menuUrl").prop("readonly", true);
				$("#menuUrl").prop("placeholder", "根目录无需填写此项");
				$("#menuUrl").val();
				$("#menuUrl").addClass("input_disabled");
			} else {
				$("#menuUrl").removeProp("readonly");
				$("#menuUrl").prop("placeholder", "");
				$("#menuUrl").removeClass("input_disabled");
			}
		}
	</script>
</body>
</html>