<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP配置</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<style type="text/css">
	input[type="file"] {
		opacity: 0;
		filter: alpha(opacity = 0);
		width: 0;
		height: 0;
		padding: 0;
	}

	.img_select {
		width: 100px;
		height: 100px;
		cursor: pointer;
		border: 1px dotted grey;
		background-color: grey;
	}

	.img_select.banner {
		width: 320px;
	}

	.div_img {
		float: left;
		width: 100px;
		height: 150px;
		text-align: center;
		margin-right: 10px;
	}

	.div_img_banner {
		float: left;
		width: 320px;
		text-align: center;
	}
</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>APP配置</h3>
	<div class="navList">
		<ul class="tab">
			<li id="tab_other"><a href="javascript:;" class="selected">其他配置</a></li>
			<li id="tab_image"><a href="javascript:;">图片配置</a></li>
		</ul>
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="1" />
			<div class="tab_image_div" style="display: none;">
				<h3 style="font-size: 20px; padding-bottom: 10px; margin-top: 15px; margin-bottom: 15px;">
					首页icon
				</h3>

				<!-- 爱家餐桌等8个icon start -->
				<div class="div_img">
					<c:set var="commonAttr" value="iconTable"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconTable }<c:if test="${vo.iconTable == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconTable}" />
				</div>
				<div class="div_img">
					<c:set var="commonAttr" value="iconGroup"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconGroup }<c:if test="${vo.iconGroup == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconGroup }" />
				</div>
				<div class="div_img">
					<c:set var="commonAttr" value="iconNew"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconNew }<c:if test="${vo.iconNew == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconNew }" />
				</div>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconHot"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconHot }<c:if test="${vo.iconHot == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconHot}" />
				</div>
				<div class="clear"></div>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconSignIn"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconSignIn }<c:if test="${vo.iconSignIn == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconSignIn }" />
				</div>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconBalance"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconBalance }<c:if test="${vo.iconBalance == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconBalance }" />
				</div>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconCoupon"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconCoupon }<c:if test="${vo.iconCoupon == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconCoupon }" />
				</div>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconInvitation"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconInvitation }<c:if test="${vo.iconInvitation == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconInvitation }" />
				</div>
				<div class="clear"></div>
				<%--食材检测--%>
				<div class="div_img" >
					<c:set var="commonAttr" value="iconReport"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select"
							 src="${vo.iconReport }<c:if test="${vo.iconReport == ''}">${placeholder}</c:if>" />
					</label>
					<input class="btn empty" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.iconReport }" />
				</div>

				<!-- 爱家餐桌等8个icon end -->
				<div class="clear"></div>
				<h3></h3>
				<h3 style="font-size: 20px; padding-bottom: 10px; margin-bottom: 15px;">
					今日特价活动区头部图
				</h3>
				<div class="div_img_banner">
					<c:set var="commonAttr" value="bannerCheap"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.bannerCheap }" />
					</label>
					<input class="btn emptyBanner" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.bannerCheap }" />
				</div>
				<div class="clear"></div>
				<br/>
				<h3 style="font-size: 20px; padding-bottom: 10px; margin-bottom: 15px;">
					业主推荐区头部图
				</h3>
				<div class="div_img_banner">
					<c:set var="commonAttr" value="bannerRecommend"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.bannerRecommend }" />
					</label>
					<input class="btn emptyBanner" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.bannerRecommend }" />
				</div>
				<div class="clear"></div>
				<h3></h3>

				<%--3.12.2及其以上版本使用--%>
				<h3 style="font-size: 20px; padding-bottom: 10px; margin-bottom: 15px;">
					今日特价背景图(3.12.2及其以上版本使用)
				</h3>
				<div class="div_img_banner">
					<c:set var="commonAttr" value="bannerCheap2"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.bannerCheap2 }" />
					</label>
					<input class="btn emptyBanner" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.bannerCheap2 }" />
				</div>
				<div class="clear"></div>
				<br/>
				<h3 style="font-size: 20px; padding-bottom: 10px; margin-bottom: 15px;">
					业主推荐背景图(3.12.2及其以上版本使用)
				</h3>
				<div class="div_img_banner">
					<c:set var="commonAttr" value="bannerRecommend2"></c:set>
					<label>
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.bannerRecommend2 }" />
					</label>
					<input class="btn emptyBanner" type="button" data-attr="${commonAttr}" value="清空" />
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.bannerRecommend2 }" />
				</div>
				<div class="clear"></div>
				<h3></h3>

			</div>
			<div class="tab_other_div" style="display: table;">
				<div style="margin-top: 15px; margin-bottom: 15px;">
					<div style="font-size: 16px;">爱家头条：</div>
					<div>
						<textarea rows="5" cols="70" class="textArea" name="headlines" id="headlines">${headlines}</textarea>
						<input class="btn empty" type="button" data-attr="headlines" value="清空" />
					</div>
					<div>备注：每条之间使用@符号分割；建议每条字数在20字内。</div>
				</div>
				<div style="margin-bottom: 15px;">
					<div style="font-size: 16px;">深圳站购物车通知消息：</div>
					<div>
						<textarea rows="5" cols="70" class="textArea" name="shoppingCartTipSZ" id="shoppingCartTipSZ">${shoppingCartTipSZ}</textarea>
						<input class="btn empty" type="button" data-attr="shoppingCartTipSZ" value="清空" />
					</div>
				</div>
				<div style="margin-bottom: 15px;">
					<div style="font-size: 16px;">广州站站购物车通知消息：</div>
					<div>
						<textarea rows="5" cols="70" class="textArea" name="shoppingCartTipGZ" id="shoppingCartTipGZ">${shoppingCartTipGZ}</textarea>
						<input class="btn empty" type="button" data-attr="shoppingCartTipGZ" value="清空" />
					</div>
				</div>
				<div style="margin-bottom: 15px;">
					<div style="font-size: 16px;">深圳新用户优惠券id（使用英文逗号隔开）：</div>
					<div>
						<input class="inpMain" value="${newUsersCouponsSz}" name="newUsersCouponsSz" />
					</div>
				</div>
				<div style="margin-bottom: 15px;">
					<div style="font-size: 16px;">广州新用户优惠券id（使用英文逗号隔开）：</div>
					<div>
						<input class="inpMain" value="${newUsersCouponsGz}" name="newUsersCouponsGz" />
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<h3></h3>
			<input id="btn_submit" class="btn" type="button" value="提交并使用" title="提交并使用" />
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#btn_submit').click(function(){
			btn_disable($('#btn_submit'));
			var url = '/hajAppConfig/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data === 'success') {
					window.parent.layer.msg('保存成功');
					btn_enable($('#btn_submit'));
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($('#btn_submit'));
				}
			});
		});

		$('.inpFile').change(function() {
			var dataAttr = $(this).attr('data-attr');
			uploadImage($(this), $('#'+dataAttr+'Img'), $('#'+dataAttr));
		});

		$('.btn.empty').click(function() {
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr).val('');
			$('#'+dataAttr+'Img').attr('src', '');
		});

		$('.btn.emptyBanner').click(function() {
			var dataAttr = $(this).attr('data-attr');
			$('#'+dataAttr).val('');
			$('#'+dataAttr+'Img').attr('src', '');
		});

		$('#tab_image').click(function () {
			$(this).children('a').addClass('selected');
			$('#tab_other').children('a').removeClass('selected');

			$('.tab_image_div').css('display', 'table');
			$('.tab_other_div').css('display', 'none');
		});

		$('#tab_other').click(function () {
			$(this).children('a').addClass('selected');
			$('#tab_image').children('a').removeClass('selected');

			$('.tab_other_div').css('display', 'table');
			$('.tab_image_div').css('display', 'none');
		});

		$("img").error(function(){
			$(this).attr('src', '/style/images/placeholder.png');
		});
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
			url : '/hajAppConfig/uploadFile.html',
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
</script>
</body>
</html>