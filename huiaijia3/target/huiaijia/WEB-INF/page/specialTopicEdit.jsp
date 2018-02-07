<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改专题</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style>
		input, img {
			vertical-align: middle;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/hajSpecialTopic/list.html" class="actionBtn">专题管理</a>修改专题
		</h3>
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="${vo.id }" />
			<table class="tableBasic" style="width: 100%;">
				<tr>
					<td>专题名称：</td>
					<td><input class="inpMain required" type="text" name="name" value="${vo.name }"/></td>
				</tr>
				<tr>
					<td>专题链接：</td>
					<td><input class="inpMain required" type="text" name="url" value="${vo.url }"/></td>
				</tr>
				<tr>
					<td>活动时间：</td>
					<td>
						<input type="text" class="inpMain short date_picker required" id="beginTime" name="startTime"
							   value="<fmt:formatDate value="${vo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"
							   readonly="readonly" placeholder="开始时间" />
						-
						<input type="text" id="endTime" name="endTime" class="inpMain short date_picker"
							   value="<fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"
							   readonly="readonly" placeholder="结束时间" />
					</td>
				</tr>
				<c:if test="${isAdmin && vo.id == null}">
					<tr>
						<td><label>所属城市</label>：</td>
						<td>
							<select name="areaCode" id="areaCode" class="required">
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code }" <c:if test="${vo.areaCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<c:if test="${!isAdmin}">
					<input type="hidden" name="areaCode" value="${userSession.areaCode}"/>
				</c:if>
				<tr>
					<td>状态：</td>
					<td>
						<select id="invalid" name="invalid" class="required">
							<option value="">请选择</option>
							<option value="0">进行中</option>
							<option value="1">未启用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>选择入口图片：</td>
					<td>
						<input class="inpMain" type="text" id="banner" name="banner" value="${vo.banner }"/>
						<c:set var="bannerUrl" value=""></c:set>
						<c:if test="${not empty vo.banner }">
							<c:set var="bannerUrl" value="${vo.banner }"></c:set>
						</c:if>
						<img alt="选择文件" title="选择文件" id="bannerImg" src="${bannerUrl }"
							 style="width: 90px; height: 30px;" />
						<input class="inpFile required" type="file" id="bannerFile" accept=".jpg,.jpeg,.gif,.png" />
					</td>
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

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
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
				url : '/hajSpecialTopic/uploadFile.html',
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
				if ($('#invalid').is(':checked')) {
					$('#sort').val(0);
				}
				if (confirm('确定不是手抖吗？')) {
					var url = '/hajSpecialTopic/save.html';
					$.post(url, $('#editForm').serialize(), function(data) {
						if (data == 'success') {
							alert('搞定啦！');
							window.location.href = '/hajSpecialTopic/list.html';
						} else {
							alert(UNKNOWN_ERROR);
						}
					});
				}
			});
			$('#bannerFile').change(function() {
				uploadImage($(this), $('#bannerImg'), $('#banner'));
			});

			/* 弃用时将排序设为0，启用时再重新设置排序（为了避免弃用的专题排序出现混乱） */
			var sort_val = $('#sort').val();
			$('#sort').change(function () {
				sort_val = $(this).val();
			});

			$('#invalid').change(function () {
				if ($('#invalid').is(':checked')) {
					$('#sort').val(0);
				} else {
					$('#sort').val(sort_val);
				}
			});

			$('#invalid').val(${vo.invalid});
		});
	</script>
</body>
</html>