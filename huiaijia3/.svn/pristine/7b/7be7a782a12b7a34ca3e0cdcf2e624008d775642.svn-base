<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>兑换商品信息</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/integralMall/list.html" class="actionBtn">积分商城管理</a>兑换商品信息
	</h3>
	<form method="post" name="editForm" id="editForm">
		<c:if test="${not empty vo.id}">
		<input type="hidden" name="id" value="${vo.id }" />
		</c:if>
		<table class="tableBasic" style="width: 100%;">
			<tr>
				<td style="text-align: left;">商品类型：</td>
				<td>
					<input type="hidden" name="commodityType" value="1" />优惠卷
				</td>
			</tr>
			<tr>
				<td>*商品ID：</td>
				<td>
					<c:if test="${empty vo.id}">
						<input class="inpMain" type="text" id="commodityId" name="commodityId"/>
						备注：只能填写优惠券ID或者商品ID
					</c:if>

					<c:if test="${not empty vo.id}">
						${vo.commodityId}
						<input class="inpMain" type="hidden" id="commodityId" name="commodityId" value="${vo.commodityId }"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>商品名称：</td>
				<td>
					<input class="inpMain" type="text" name="commodityName" value="${vo.commodityName }"/>
				</td>
			</tr>
			<tr>
				<td>商品简介：</td>
				<td>
					<input class="inpMain" type="text" name="description" value="${vo.description }"/>
				</td>
			</tr>
			<tr>
				<td>备注说明：</td>
				<td>
					<textarea rows="4" cols="50" name="remark" id="remark" class="textArea" maxlength="30" >${vo.remark }</textarea>
				</td>
			</tr>
			<tr>
				<td>发行量：</td>
				<td>
					<input class="inpMain short" type="number" id="total" name="total" value="${vo.total }"/>
				</td>
			</tr>
			<tr>
				<td>兑换积分：</td>
				<td>
					<input class="inpMain short" type="number" name="integral" value="${vo.integral }"/>
				</td>
			</tr>

			<tr>
				<td>*兑换限制：</td>
				<td>
					<c:if test="${empty vo.id}">
						<label style="line-height: 38px;">
							<input type="radio" name="exchangeLimit" value="1"/>无次数限制
						</label>
						<br />
						<label>
							<input type="radio" name="exchangeLimit" value="2" checked/>
							每人限领&nbsp;
							<input type="number" id="limitCount" name="limitCount" class="inpMain short"
										  value="0" style="width: 20px;"/>&nbsp;张
						</label>
						<br />
						备注：只能填写正整数
					</c:if>
					<c:if test="${not empty vo.id}">
						<c:if test="${vo.exchangeLimit > 1}">
							每人限领${vo.limitCount}次
						</c:if>
						<c:if test="${vo.exchangeLimit < 2}">
							无次数限制
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>是否隐藏：</td>
				<td>
					<select name="hide" id="hide" style="vertical-align: middle;">
						<option value="0" <c:if test="${vo.hide == 0}" >selected="selected" </c:if> >不隐藏</option>
						<option value="1" <c:if test="${vo.hide == 1}" >selected="selected" </c:if> >隐藏</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>所属城市：</td>
				<td>
					<select name="areaCode" id="areaCode" style="vertical-align: middle;">
						<option value="">不限</option>
						<option value="100002" <c:if test="${vo.areaCode == 100002}" >selected="selected" </c:if>>深圳市</option>
						<option value="100010" <c:if test="${vo.areaCode == 100010}" >selected="selected" </c:if>>广州市</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>排序值：</td>
				<td>
					<input class="inpMain" type="text" name="sort" value="${vo.sort }"/>
				</td>
			</tr>
			<tr>
				<td width="150px;">商品图片:</td>
				<td>
					<div style="float: left; width: 150px;">
						<c:set var="image" value="${defaultImage }"></c:set>
						<c:if test="${not empty vo.image }">
							<c:set var="image" value="${vo.image }"></c:set>
						</c:if>
						<label>
							<img alt="选择文件" title="选择文件" id="imageImg" src="${image }" style="width: 100px; height: 100px;" />
							<input class="inpFile disable" type="file" id="imageFile" accept=".jpg,.jpeg,.gif,.png" />
						</label>
						<input type="hidden" id="image" name="image" value="${vo.image }" />
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

	function showRule() {
		if ($('input[name="exchangeLimit"]:checked').val() == 2) {
			$('#limitCount').removeAttr('readonly');
		} else {
			$('#limitCount').attr('readonly', 'readonly');
			$('#limitCount').val(0);
		}
	}

	$('#imageFile').change(function() {
		uploadImage($(this), $('#imageImg'), $('#image'));
	});

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
			url : '/integralMall/uploadFile.html',
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
					alert('我的天呐，上传失败了，赶紧问一下程序员GG原因：'+result.result);
				}
			},
			error : function() {
				alert('请先选择您要上传的图片');
			}
		});
	}

	$(function() {
		showRule();

		$('input[name="exchangeLimit"]').click(function () {
			showRule();
		});

		$('#btn_submit').click(function() {
			<c:if test="${empty vo.id}">
				if (!/^[0-9]+$/.test($('#limitCount').val())) {
					alert('每人限领张数只能输入正整数');
					return false;
				}
			</c:if>

			var commodityId = $("#commodityId").val();
			if(commodityId==null) {
				alert('优惠券ID或者商品ID不能为空');
				return false;
			}

			if (!/^[0-9]+$/.test($('#total').val())) {
				alert('发行量只能增加且只能为正整数');
				return false;
			}

			if (confirm('带星号的项填写完之后将不可修改，确认要提交吗？')) {
				btn_disable($('#btn_submit'));
				var url = '/integralMall/save.html';
				$.post(url, $('#editForm').serialize(), function(data) {
					if (data == 'success') {
						alert('搞定啦！');
						window.location.href = '/integralMall/list.html';
					} else {
						alert(UNKNOWN_ERROR);
						btn_enable($('#btn_submit'));
					}
				});
			}
		});
	});
</script>
</body>
</html>