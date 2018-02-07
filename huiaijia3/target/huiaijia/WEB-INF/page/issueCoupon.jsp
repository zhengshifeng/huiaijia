<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发放优惠券</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			发放优惠券
		</h3>
		<div class="navList">
			<ul class="tab">
				<li id="tab_system"><a href="javascript:;" class="selected">系统派发</a></li>
				<li id="tab_code"><a href="javascript:;">兑换码兑换</a></li>
			</ul>

			<form name="editForm" id="editForm">
				<input type="hidden" name="id" value="${couponId}">
				<input type="hidden" id="sendType" name="sendType" value="1">
				<table class="tableBasic system" style="min-width: 500px;">
					<tr>
						<th style="width: 126px;">是否指定用户：</th>
						<td>
							<label><input type="radio" name="userLimit" value="0" checked />不指定用户</label>
							<label><input type="radio" name="userLimit" value="1" />指定用户</label>
						</td>
					</tr>
					<tr class="customer_list" style="display: none">
						<th>上传客户列表（<br /><span class="text_red">1000人/次</span>）：</th>
						<td>
							<input type="file" id="uploadFile" accept=".xlsx" />
							<%--<input class="btnGray" type="button" id="importFile" value="导入客户列表" />--%>
							<input class="btnGray" type="button" id="deleteUpload" value="删除已上传的客户" />
							<a class="btnGray" href="http://cdn.huiaj.com/xlsx/领取优惠券模板.xlsx">下载客户列表模板</a>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="btn_submit btn" type="button" value="确认发放" title="确认发放" />
						</td>
					</tr>
				</table>

				<table class="tableBasic code" style="display: none; min-width: 500px;">
					<tr>
						<th style="width: 126px;">兑换码：</th>
						<td>
							<input class="inpMain required" type="text" name="couponNo" id="couponNo" maxlength="9"
								onkeypress="return /[a-zA-Z0-9\u4e00-\u9fa5]/g.test(String.fromCharCode(window.event.keyCode))"
								ondragenter="return false"
							/>
						</td>
					</tr>
					<tr>
						<th>说明：</th>
						<td>
							<p>1、仅能由汉字，数字，大写字母，小写字母任意组合。
							<p>2、10个字符以内。
							<p>3、不可以和当前非【已结束】状态的优惠券设置相同的兑换码。
							<p>4、兑换码设置并发放后将不可更改。
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="btn_submit btn" type="button" value="确认发放" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">

		function submitForm(passport) {
			var url = '/hajCouponInfo/issue.html';
			$.post(url, $('#editForm').serialize() + '&passport=' + passport, function (data) {
				if (data.result == 'success') {
					alert('已发放');
					window.location.href = '/hajCouponInfo/list.html?category=${category}';
				} else {
					alert(data.result);
					btn_enable($('.btn_submit'));
				}
			});
		}

		$(function () {
			$('#tab_system').click(function () {
				$(this).children('a').addClass('selected');
				$('#tab_code').children('a').removeClass('selected');

				$('.tableBasic.code').css('display', 'none');
				$('.tableBasic.system').css('display', 'table');

				$('#sendType').val(1);
			});

			$('#tab_code').click(function () {
				$(this).children('a').addClass('selected');
				$('#tab_system').children('a').removeClass('selected');

				$('.tableBasic.system').css('display', 'none');
				$('.tableBasic.code').css('display', 'table');
				$('#sendType').val(2);
			});

			$('.btn_submit').click(function () {
				var passport = prompt('输入通行口令，并确认', '');

				if (passport) {
					btn_disable($('.btn_submit'));
					if ($('input[name="userLimit"]:checked').val() == 1) {
						//创建FormData对象
						var data = new FormData();

						//为FormData对象添加数据
						$.each($('#uploadFile')[0].files, function(i, file) {
							data.append('fileName', file);
						});

						data.append('couponId', '${couponId}');

						$.ajax({
							url: '/hajCouponSendUser/batchImport.html',
							type: 'POST',
							data: data,
							cache: false,
							contentType: false, //不可缺
							processData: false, //不可缺
							success: function(resultMap){
								if (resultMap.result) {
								    var successList = resultMap.userList;
								    var errorUsers = resultMap.errorUsers;
								    alert("操作成功！,"+errorUsers);
									submitForm(passport);
//									alert('导入成功，准备发放...');
								} else {
									alert(resultMap.errorUsers);
									btn_enable($('.btn_submit'));
								}
							},
							error: function(){
								alert('请先选择您要上传的文件');
								window.location.reload();
							}
						});
					} else {
						submitForm(passport);
					}
				}
			});

			$('input[name="userLimit"]').click(function () {
				var value = $(this).val();
				if (value == 0) {
					$('.customer_list').css('display', 'none');
				} else {
					$('.customer_list').css('display', 'table-row');
				}
			});

			$("#couponNo").on("paste",function(event){
				event.preventDefault();
				var clipboardData =  window.event.clipboardData.getData('text');
				var lastData = $("#couponNo").val() + clipboardData;
				if (!/[^a-zA-Z0-9\u4e00-\u9fa5]/g.test(clipboardData) && lastData.length < 10) {
					$("#couponNo").val(lastData);
				}
			});

			$('#deleteUpload').click(function () {
				if (confirm('朋友，删掉你导入的用户之后不可恢复，谨慎！谨慎！谨慎！')) {
					$.ajax({
						url: '/hajCouponSendUser/deleteByCoupon.html?couponId=${couponId}',
						type: 'POST',
						cache: false,
						contentType: false, //不可缺
						processData: false, //不可缺
						success: function(data) {
							if (data == 'success') {
								alert('已删除');
								window.location.reload();
							} else {
								alert('优惠券ID异常');
							}
						},
						error: function(){
							alert(UNKNOWN_ERROR);
						}
					});
				}
			});
		});
	</script>
</body>
</html>
