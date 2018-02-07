<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品品牌信息修改</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<c:if test="${empty vo.id }">
		添加商品品牌
		</c:if>
		<c:if test="${not empty vo.id }">
		修改商品品牌信息
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
		<c:if test="${not empty vo.id }">
		<input type="hidden" name="id" value="${vo.id }" />
		</c:if>
		<input type="hidden" name="areaCode" value="${vo.areaCode}" />
		<table class="tableBasic" style="width: 100%;">
			<tr>
				<td><label>品牌名称</label>：</td>
				<td>
					<input class="inpMain required" required type="text" name="name" value="${vo.name }" />
				</td>
			</tr>
			<tr>
				<td><label>备注</label>：</td>
				<td>
					<input class="inpMain" type="text" name="remark" value="${vo.remark }" />
				</td>
			</tr>
			<tr>
				<td style="width: 150px;"></td>
				<td>
					<input id="btn_cancel" class="btn" type="button" value="取消并返回" />
					<input id="btn_submit" class="btn" type="button" value="保存并返回" title="保存并返回" />
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
			var url = '/commodityBrand/save.html';
			btn_disable($('#btn_submit'));
			$.post(url, $('#editForm').serialize(), function(data){
				if (data === 'success') {
					window.parent.layer.msg('保存成功');
					window.location.href = '/commodityBrand/list.html';
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($('#btn_submit'));
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