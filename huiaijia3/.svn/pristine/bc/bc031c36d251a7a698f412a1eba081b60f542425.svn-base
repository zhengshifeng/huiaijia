<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改专题商品信息</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<style type="text/css">
input[type="file"] {
	opacity: 0;
	filter: alpha(opacity = 0);
	width: 0px;
	height: 0px;
}

#image3Img {
	width: 150px;
	height: 150px;
	cursor: pointer;
	border: 1px dotted gray;
}
</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/hajSpecialTopicCommodity/list.html?specialTopicId=${specialTopicId }" class="actionBtn">专题商品管理</a>修改专题商品信息
		</h3>
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="${commodity.id }" />
			<table class="tableBasic" style="width: 100%;">
				<tr>
					<td>商品：</td>
					<td>${commodity.name }</td>
				</tr>
				<tr>
					<td>进补商品图：</td>
					<td>
						<div style="float: left;">
							<c:set var="image3Url" value="${defaultImg }"></c:set>
							<c:if test="${not empty commodity.image3 }">
								<c:set var="image3Url" value="${commodity.image3 }"></c:set>
							</c:if>
							<img alt="选择文件" title="选择文件" id="image3Img" src="${image3Url }"
								onclick="getElementById('image3File').click()">
							<input class="inpFlie" type="file" id="image3File" accept=".jpg,.jpeg,.gif,.png" />
							<input type="hidden" id="image3" name="image3" value="${commodity.image3 }" />
						</div>
					</td>
				</tr>
				<tr>
					<td>进补描述：</td>
					<td><input class="inpMain" type="text" name="description2" value="${commodity.description2 }"/></td>
				</tr>
				<tr>
					<td>排序：</td>
					<td><input class="inpMain short" type="number" name="sort" value="${commodity.sort }"/></td>
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
		/**
		 * 上传图片时调用
		 */
		function uploadImage($fileId, $imageObj, $image) {
			//创建FormData对象
			var data = new FormData();
	
			//为FormData对象添加数据
			$.each($fileId[0].files, function(i, file) {
				data.append('fileName', file);
			});
	
			$.ajax({
				url : '/hajSpecialTopicCommodity/uploadFile.html',
				type : 'POST',
				data : data,
				cache : false,
				contentType : false, //不可缺
				processData : false, //不可缺
				success : function(result) {
					if (result.result = 'success') {
						$imageObj.attr('src', result.filePath);
						$image.val(result.filePath);
					} else {
						alert('我的天呐，上传失败了，赶紧问一下程序员GG');
					}
				},
				error : function() {
					alert('请先选择您要上传的图片');
				}
			});
		}
	
		$(function() {
			$('#btn_submit').click(function() {
				if (confirm('确定不是手抖吗？')) {
					var url = '/hajSpecialTopicCommodity/updateCommodity.html';
					$.post(url, $('#editForm').serialize(), function(data) {
						if (data == 'success') {
							alert('搞定啦！');
							window.location.href = '/hajSpecialTopicCommodity/list.html?specialTopicId=${specialTopicId }';
						} else {
							alert(UNKNOWN_ERROR);
						}
					});
				}
			});
			
			$('#image3File').change(function() {
				uploadImage($(this), $('#image3Img'), $('#image3'));
			});
		});
	</script>
</body>
</html>