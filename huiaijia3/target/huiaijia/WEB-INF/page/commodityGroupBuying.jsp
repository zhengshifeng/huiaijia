<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>团购信息补充页</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<style type="text/css">
		input[type="file"] {
			opacity: 0;
			filter: alpha(opacity = 0);
			width: 0;
			height: 0;
			padding: 0;
		}

		.img_select {
			width: 200px;
			height: auto;
			cursor: pointer;
			border: 1px dotted grey;
			background-color: grey;
		}

		.div_img {
			float: left;
			width: 200px;
			height: auto;
			text-align: center;
			margin-right: 10px;
		}

		.button_div {
			width: 100px;
			float: left;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>团购信息补充页</h3>
	<form method="post" name="editForm" id="editForm">
		<input type="hidden" name="id" value="${vo.id}" />
		<input type="hidden" name="commodityId" value="${vo.commodityId}" />
		<table class="tableBasic" style="width: 100%;  ">
			<tr>
				<td>商品名称：</td>
				<td>${commodity.name}</td>
			</tr>
			<tr>
				<td>截止预订时间：</td>
				<td>
					<input type="text" class="inpMain short date_picker" name="cutOffTime" id="cutOffTime"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"
						   readonly="readonly"
						   <c:if test="${not empty vo.cutOffTime }">value="<fmt:formatDate value="${vo.cutOffTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
					/>
				</td>
			</tr>
			<tr>
				<td>预计送达时间：</td>
				<td>
					<input type="text" class="inpMain short date_picker" name="deliveryTime" id="deliveryTime"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"
						   readonly="readonly"
						   <c:if test="${not empty vo.deliveryTime }">value="<fmt:formatDate value="${vo.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
					/>
				</td>
			</tr>
			<tr>
				<td>
					列表图：
				</td>
				<td>
					<div class="div_img">
						<c:set var="commonAttr" value="listPic"></c:set>
						<label>
							<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.png"
								   data-attr="${commonAttr}" />
							<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
								 src="${vo.listPic }<c:if test="${vo.listPic == ''}">${placeholder}</c:if>"/>
						</label>
						<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
						<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.listPic}" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					轮播图：
				</td>
				<td>
					<c:forEach var="sliderPic" items="${vo.sliderPicArr}" varStatus="vs">
					<c:set var="commonAttr" value="sliderPic${vs.index}"></c:set>
					<div class="div_img" id="${commonAttr}div">
						<label>
							<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.png"
								   data-attr="${commonAttr}" />
							<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
								 src="${sliderPic }<c:if test="${sliderPic == ''}">${placeholder}</c:if>" />
						</label>
						<input class="btn remove" type="button" data-attr="${commonAttr}" value="删除" />
						<input type="hidden" id="${commonAttr}" name="sliderPicArr" value="${sliderPic}" />
					</div>
					</c:forEach>
					<div id="sliderMultiDiv" class="button_div">
						<input class="inpFile multiFile" type="file" accept=".jpg,.jpeg,.png" multiple
							   data-target="slider"/>
						<input class="btn multiButton" type="button" value="点击批量选择图片" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					详情图：
				</td>
				<td>
					<c:forEach var="detailPic" items="${vo.detailPicArr}" varStatus="vs">
					<c:set var="commonAttr" value="detailPic${vs.index}"></c:set>
					<div class="div_img" id="${commonAttr}div">
						<label>
							<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.png"
								   data-attr="${commonAttr}" />
							<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
								 src="${detailPic }<c:if test="${detailPic == ''}">${placeholder}</c:if>" />
						</label>
						<input class="btn remove" type="button" data-attr="${commonAttr}" value="删除" />
						<input type="hidden" id="${commonAttr}" name="detailPicArr" value="${detailPic}" />
					</div>
					</c:forEach>
					<div id="detailMultiDiv" class="button_div">
						<input class="inpFile multiFile" type="file" accept=".jpg,.jpeg,.png" multiple
							   data-target="detail"/>
						<input class="btn multiButton" type="button" value="点击批量选择图片" />
					</div>
				</td>
			</tr>
			<tr>
				<td style="width: 150px;"></td>
				<td>
					<input id="btn_submit" class="btn" type="button" value="提交" title="提交" />
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	$(function(){
		$('#btn_submit').click(function(){
			btn_disable($('#btn_submit'));
			var url = '/commodityGroupBuying/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data === 'success') {
					window.parent.layer.msg('保存成功');
					btn_enable($('#btn_submit'));
					window.location.reload();
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($('#btn_submit'));
				}
			});
		});

		$(document).on('change','.inpFile',function(){
			var dataAttr = $(this).attr('data-attr');
			uploadImage($(this), $('#'+dataAttr+'Img'), $('#'+dataAttr));
		});

		$(document).on('click','input.btn.remove',function(){
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr+'div').remove();
		});

		$('input.btn.empty').click(function() {
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr).val('');
			$('#'+dataAttr+'Img').attr('src', '');
		});

		$('.btn.multiButton').click(function() {
			$(this).siblings().click();
		});

		$('.inpFile.multiFile').change(function() {
			multiUploadImage($(this));
		});

		$("img").error(function(){
			$(this).attr('src', '/style/images/placeholder.png');
		});
	});

	/**
	 * 单张图片上传
	 */
	function uploadImage($fileId, $imageObj, $image) {
		//创建FormData对象
		var data = new FormData();

		//为FormData对象添加数据
		$.each($fileId[0].files, function(i, file) {
			data.append('fileName', file);
		});

		window.parent.layer.msg('正在上传...', {
			icon: 16
			,shade: 0.01
		});

		$.ajax({
			url : '/commodityGroupBuying/uploadFile.html',
			type : 'POST',
			data : data,
			cache : false,
			contentType : false, //不可缺
			processData : false, //不可缺
			success : function(result) {
				if (result.result = 'success') {
					$imageObj.attr('src', result.filePath);
					$image.val(result.filePath);
					window.parent.layer.closeAll('loading');
				} else {
					alert('上传失败');
					window.parent.layer.closeAll('loading');
				}
			},
			error : function() {
				alert('请先选择您要上传的图片');
			}
		});
	}

	function callImage(target, i, url) {
		return '<div class="div_img" id="'+target+i+'div">'+
			'<label>'+
			'	<input class="inpFile" type="file" id="'+target+i+'File" accept=".jpg,.jpeg,.png"'+
			'		data-attr="'+target+i+'" />'+
			'	<img alt="点击选择文件" title="点击选择文件" id="'+target+i+'Img" class="img_select"'+
			'		src="'+url+'" />'+
			'</label>'+
			'<input class="btn remove" type="button" data-attr="'+target+i+'" value="删除" />'+
			'<input type="hidden" id="'+target+i+'" name="'+target+'Arr" value="'+url+'" />'+
		'</div>';
	}

	var img_index = 100;

	function multiUploadImage($fileId) {
		//创建FormData对象
		var data = new FormData();

		var target = $fileId.attr('data-target');

		//为FormData对象添加数据
		$.each($fileId[0].files, function(i, file) {
			data.append('fileName', file);
		});

		window.parent.layer.msg('正在上传...', {
			icon: 16
			,shade: 0.01
		});

		$.ajax({
			url : '/commodityGroupBuying/multiUploadFile.html',
			type : 'POST',
			data : data,
			cache : false,
			contentType : false, //不可缺
			processData : false, //不可缺
			success : function(result) {
				if (result.result = 'success') {
					for (var i = 0; i < result.filePath.length; i++) {
						$('#' + target + 'MultiDiv').before(callImage(target+'Pic', img_index + i, result.filePath[i]));
					}
					img_index = img_index + result.filePath.length;
					window.parent.layer.closeAll('loading');
				} else {
					alert('上传失败');
					window.parent.layer.closeAll('loading');
				}
			},
			error : function() {
				alert('请先选择您要上传的图片');
			}
		});
	}
</script>
</body>
</html>