
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加检测报告分类</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<%--<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>--%>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<style type="text/css">
		input, img {
			vertical-align: middle;
		}
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
	<a href="javascript:history.go(-1)"><font class="btn">取消并返回</font></a>

	<form method="post" name="editForm" id="editForm">
		<input type="hidden" name="id" value="${confVo.id}" />
		<input type="hidden" name="repid" value="${confVo.repid}" />
		<input type="hidden" name="reportId" value="${reportId}" />
		<table class="tableBasic" id="tb" style="width: 100%;  ">
			<tr>
				<th>分类名称：</th>
				<td>
					<input class="inpMain" type="text" id="catename" name="catename" value="${confVo.catename}"/>
				</td>
			</tr>
			<tr>
				<th>分类排序：</th>
				<td>
					<input class="inpMain" type="text" id="catesort" name="catesort" value="<c:if test="${sort!=null}">${sort}</c:if>${confVo.catesort}"/>
				</td>
			</tr>
			<tr>
				<th>分类说明：</th>
				<td>
					<textarea class="inpMain" cols="120" rows="4" id="description"
							  name="description">${confVo.description}</textarea>
				</td>
			</tr>
			<tr>
				<th>分类图集：</th>
				<td>
					<c:forEach var="confImg" items="${confVo.confImgs}" varStatus="vs">
						<c:set var="commonAttr" value="confImg${vs.index}"></c:set>
						<div class="div_img" id="${commonAttr}div">
							<label>
								<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.png"
									   data-attr="${commonAttr}" />
								<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
									 src="${confImg }<c:if test="${confImg == ''}">${placeholder}</c:if>" />
							</label>
							<input class="btn remove" type="button" data-attr="${commonAttr}" value="删除" />
							<input type="hidden" id="${commonAttr}" name="confImgs" value="${confImg}" />
						</div>
					</c:forEach>
					<div id="confImgsMultiDiv" class="button_div">
						<input class="inpFile multiFile" type="file" accept=".jpg,.jpeg,.png" multiple
							   data-target="confImgs"/>
						<input class="btn multiButton" type="button" value="点击批量选择图片" />
					</div>
				</td>
			</tr>
			<tr>
				<th style="width: 150px;"></th>
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

			var catename = $("#catename").val();
			if (catename == '') {
				$("#catename").focus();
				alert('请选填写检测报告的分类名称!');
				return false;
			}

			var catesort = $("#catesort").val();
			if (catesort == '') {
				$("#catesort").focus();
				alert('请填写检测报告的分类排序!');
				return false;
			}
			if (isNaN(catesort)){
				alert("认真点, 《分类排序》请输入数字...");
				return false;
			}

			var description = $("#description").val();
			if (description == '') {
				$("#description").focus();
				alert('请填写检测报告的分类说明!');
				return false;
			}
			btn_disable($('#btn_submit'));
			var url = '/ingredientsReportConf/saveConf.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data.error == '0') {
					window.parent.layer.msg('保存成功');
					btn_enable($('#btn_submit'));
					window.location.reload();
					window.location.href = '/ingredientsReport/list.html';
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($('#btn_submit'));
				}
			});
		});

		//单图上传
		$(document).on('change','.inpFile',function(){
			var dataAttr = $(this).attr('data-attr');
			uploadImage($(this), $('#'+dataAttr+'Img'), $('#'+dataAttr));
		});

		//绑定事件
		$(document).on('click','input.btn.remove',function(){
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr+'div').remove();
		});

		$('input.btn.empty').click(function() {
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr).val('');
			$('#'+dataAttr+'Img').attr('src', '');
		});

		//筛选
		$('.btn.multiButton').click(function() {
			$(this).siblings().click();
		});

		//多图
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
			'<input type="hidden" id="'+target+i+'" name="'+target+'" value="'+url+'" />'+
			'</div>';
	}

	var img_index = 100;

	function multiUploadImage($fileId) {
		//创建FormData对象
		var data = new FormData();

		//attr设置或返回被选元素的属性值。
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
						$('#' + target + 'MultiDiv').before(callImage(target+'', img_index + i, result.filePath[i]));
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
