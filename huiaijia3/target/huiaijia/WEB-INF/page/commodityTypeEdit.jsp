<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style type="text/css">
		input[type="file"] {
			opacity: 0;
			filter: alpha(opacity = 0);
			width: 0;
			height: 0;
		}

		#iconImg {
			max-width: 300px;
			max-height: 300px;
			cursor: pointer;
			border: 1px dotted grey;
			background-color: grey;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/commodityType/list.html" class="actionBtn">商品分类</a>
		<c:if test="${empty vo.id }">
		添加分类
		</c:if>
		<c:if test="${not empty vo.id }">
		修改分类
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<c:if test="${not empty vo.id }">
	<input type="hidden" name="id" value="${vo.id }" />
	</c:if>
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td><label>从属类目</label>：</td>
			<td>
				<c:choose>
					<c:when test="${vo.parentId != null && vo.parentId == 0 }">
						<select id="categoryId" name="categoryId" class="required">
							<option value="">请选择</option>
							<c:forEach items="${categoryList}" var="list">
								<option value="${list.id}">${list.name}</option>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						${category}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>属性：</td>
			<td>
				<select name="commodityAttr" id="commodityAttr" class="select" >
					<option value="生鲜">生鲜</option>
					<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
					<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
					<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>父类：</td>
			<td>
				<select name="parentId" id="parentTypeId" class="select" >
					<option value="0">大类</option>
					<c:forEach items="${parentTypeList}" var="list">
						<c:if test="${list.id != vo.id}">
							<option value="${list.id }" <c:if test="${vo.parentId==list.id}">selected</c:if>>
								${list.typeName }
							</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>分类名称：</td>
			<td>
				<input class="inpMain" type="text" name="typeName" value="${vo.typeName }" />
			</td>
		</tr>
		<tr>
			<td>分类别名：</td>
			<td>
				<input class="inpMain" type="text" name="description" value="${vo.description }" />
			</td>
		</tr>
		<tr>
			<td>排序：</td>
			<td>
				<input class="inpMain short" type="number" name="sort" value="${vo.sort }" style="width: 60px;"/>
			</td>
		</tr>
		<tr>
			<td>隐藏：</td>
			<td>
				<label><input type="checkbox" name="display" value="0" <c:if test="${vo.display==0}">checked</c:if> />隐藏</label>
			</td>
		</tr>
		<tr>
			<td>图片（点击上传）：</td>
			<td>
				<div style="float: left; width: 150px;">
					<c:set var="iconUrl" value="${defaultImage }"></c:set>
					<c:if test="${not empty vo.icon }">
						<c:set var="iconUrl" value="${vo.icon }"></c:set>
					</c:if>
					<img alt="选择文件" title="选择文件" id="iconImg" src="${iconUrl }"
						 onclick="getElementById('iconFile').click()">
					<input class="inpFlie" type="file" id="iconFile" accept=".jpg,.jpeg,.gif,.png" />
					<input type="hidden" id="icon" name="icon" value="${vo.icon }" />
				</div>
			</td>
		</tr>
		<tr>
			<td width="150px"></td>
			<td><input id="btn_submit" class="btn" type="button" value="提交" title="提交" /></td>
		</tr>
	</table>
	</form>	
</div>

<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
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
		url : '/commodityType/uploadFile.html',
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

$(function(){

	<c:if test="${vo.parentId != null && vo.parentId == 0 &&not empty vo.categoryId }">
		$('#categoryId').val(${vo.categoryId});
	</c:if>

	$('#btn_submit').click(function(){
		btn_disable($(this));
		window.parent.layer.load(2);
		var url = '/commodityType/save.html';
		$.post(url, $('#editForm').serialize(), function(data){
			if (data == 'success') {
				window.parent.layer.closeAll('loading');
				window.location.href = '/commodityType/list.html?attribute=${attribute}';
			} else if (data == 'repeat') {
				alert('已存在该分类，请更换类别名称');
			} else {
				alert('未知异常');
			}
			btn_enable($('#btn_submit'));
			window.parent.layer.closeAll('loading');
		});
	});

	$('#iconFile').change(function() {
		uploadImage($(this), $('#iconImg'), $('#icon'));
	});
});
</script>
</body>
</html>