<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类目</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<c:if test="${empty vo.id }">
		添加商品类目
		</c:if>
		<c:if test="${not empty vo.id }">
		修改商品类目
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<c:if test="${not empty vo.id }">
	<input type="hidden" name="id" value="${vo.id }" />
	</c:if>
	<input type="hidden" name="areaCode" value="${vo.areaCode}" />
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td><label>类目名称</label>：</td>
			<td>
				<input class="inpMain required" required type="text" name="name" value="${vo.name }" />
			</td>
		</tr>
		<tr>
			<td><label>类目排序</label>：</td>
			<td>
				<input class="inpMain required" required type="number" name="sort" value="${vo.sort }" style="width: 60px;"/>
			</td>
		</tr>
		<tr>
			<td style="width: 150px;"></td>
			<td>
				<input id="btn_cancel" class="btn" type="button" value="取消" />
				<input id="btn_submit" class="btn" type="button" value="保存并返回" />
			</td>
		</tr>
	</table>
	</form>	
</div>

<script type="text/javascript">
$(function(){
	prependRedStar();

	$('#btn_submit').click(function(){
		if (requiredCheck()) {
			var url = '/category/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data === 'success') {
					window.parent.layer.msg('保存成功');
					window.location.href = '/category/list.html';
				} else {
					alert(UNKNOWN_ERROR);
				}
			});
		}
	});
	
	$('#btn_cancel').click(function () {
		window.history.back();
	});
});
</script>
</body>
</html>