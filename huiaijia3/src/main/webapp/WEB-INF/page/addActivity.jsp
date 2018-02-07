<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加活动</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<style>
	input, img {
		vertical-align: middle;
	}
</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>活动编辑</h3>
		<form action="saveActivity.html" method="post" name="userForm" id="userForm">
			<input type="hidden" name="residentials" value="全部小区" />
			<input type="hidden" name="id" value="${activity.id}" />
			<table class="tableBasic" style="width: 100%;">
				<tr>
					<th>活动名称：</th>
					<td>
						<input class="inpMain" type="text" id="activityName" name="activityName" value="${activity.activityName}" />

						<!-- 活动状态 -->
						<select name="status" id="status" style="vertical-align: middle;">
							<option value="0" <c:if test="${activity.status==0}">selected</c:if>>未进行</option>
							<option value="1" <c:if test="${activity.status==1}">selected</c:if>>进行中</option>
						</select>

						<!-- 活动城市 -->
						<c:if test="${isAdmin}">
							<select name="areaCode" id="areaCode">
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code }" <c:if test="${activity.areaCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						</c:if>
					</td>
				</tr>
				<tr>
					<th>活动统一价格：</th>
					<td>
						<input class="inpMain" type="text" id="discountAmount" name="discountAmount" value="${activity.discountAmount}" placeholder="可不填" />
					</td>
				</tr>
				<tr>
					<th>最大购买数（人 / 天）：</th>
					<td>
						<input class="inpMain short" type="number" id="number" name="number" value="${activity.number}" placeholder="可不填">
					</td>
				</tr>
				<tr>
					<th>活动时间：</th>
					<td>
						<input class="inpMain short date_picker" type="text" id="beginTime" name="beginTime" value="${activity.beginTime}"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
						-
						<input class="inpMain short date_picker" type="text" id="endTime" name="endTime" value="${activity.endTime}" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>活动类型：</th>
					<td>
						<select name="activityType" id="activityType">
							<option value="">活动类型</option>
							<option value="0" <c:if test="${activity.activityType==0}">selected</c:if>>今日特价</option>
							<option value="1" <c:if test="${activity.activityType==1}">selected</c:if>>1元购</option>
							<option value="2" <c:if test="${activity.activityType==2}">selected</c:if>>首页轮播图</option>
							<option value="3" <c:if test="${activity.activityType==3}">selected</c:if>>新品推荐</option>
							<option value="4" <c:if test="${activity.activityType==4}">selected</c:if>>爆品热卖</option>
							<option value="5" <c:if test="${activity.activityType==5}">selected</c:if>>其他</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>活动图片：</th>
					<td>
						<input class="inpMain" type="text" id="img" name="img" value="${activity.img }"/>
						<c:set var="imgUrl" value=""></c:set>
						<c:if test="${not empty activity.img }">
							<c:set var="imgUrl" value="${activity.img }"></c:set>
						</c:if>
						<img alt="选择文件" title="选择文件" id="imgImg" src="${imgUrl }"
							 style="width: 90px; height: 30px;" />
						<input class="inpFlie" type="file" id="imgFile" accept=".jpg,.jpeg,.gif,.png" />
					</td>
				</tr>
				<tr>
					<th>活动链接：</th>
					<td>
						<input class="inpMain long" type="text" id="link" name="link" value="${activity.link}" placeholder="可不填">
					</td>
				</tr>
				<tr>
					<th>活动描述：</th>
					<td>
						<textarea class="inpMain" cols="100" rows="4" name="describer">${activity.describer}</textarea>
					</td>
				</tr>
				<tr>
					<th style="width: 150px;"></th>
					<td>
						<input type="button" id="btn_save" class="btn" value="保存" title="保存" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">

	function uploadImage($fileId, $imageObj, $image) {
		//创建FormData对象
		var data = new FormData();

		//为FormData对象添加数据
		$.each($fileId[0].files, function(i, file) {
			data.append('fileName', file);
		});

		$.ajax({
			url : '/hajbackActivity/uploadFile.html',
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

	$(function () {
		$('#btn_save').click(function () {
			var activityName = $("#activityName").val();
			if (activityName == '') {
				$("#activityName").focus();
				alert('请输入活动名称');
				return false;
			}

			var beginTime = $("#beginTime").val();
			if (beginTime == '') {
				$("#beginTime").focus();
				alert('请选择活动开始时间');
				return false;
			}

			var endTime = $("#endTime").val();
			if (endTime == '') {
				$("#endTime").focus();
				alert('请选择活动开始时间');
				return false;
			}

			var activityType = $("#activityType").val();
			if (!activityType) {
				alert('请选择活动类型');
				return false;
			}

			var ids = '';
			var flag = 0;
			$('input[name="codityIds"]:checked').each(function() {
				ids += $(this).val() + ',';
				flag += 1;
			});
			if (0 < flag) {
				$("#commodityIds").val(ids);
			}

			btn_disable($('#btn_save'));
			var url = '/hajbackActivity/saveActivity.html';
			$.post(url, $('#userForm').serialize(), function(data) {
				if (data.error == '0') {
					alert('保存成功');
					window.location.href = '/hajbackActivity/getAll.html';
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($("#btn_save"));
				}
			});
		});

		$('#imgFile').change(function() {
			uploadImage($(this), $('#imgImg'), $('#img'));
		});
	});

</script>
</html>
