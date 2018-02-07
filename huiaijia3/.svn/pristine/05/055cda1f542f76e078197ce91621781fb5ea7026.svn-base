<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后编辑</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style>
		input, img {
			vertical-align: middle;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>售后编辑</h3>
		<div id="list">
			<table class="tableBasic">
				<tr>
					<th colspan="5">商品信息</th>
				</tr>
				<tr>
					<th>属性 - 大分类 - 小分类</th>
					<th>商品名称</th>
					<th>商品规格</th>
					<th>商品数量</th>
					<th>商品实收金额</th>
				</tr>
				<tr class="main_info">
					<td>${detail.commodityAttr} - ${detail.sbTypeName} - ${detail.scTypeName}</td>
					<td>${detail.commodityName}</td>
					<td>${detail.weight}</td>
					<td>${detail.number}</td>
					<td>${detail.actualPayment}</td>
				</tr>
				<tr class="main_info">
					<th colspan="6">售后记录</th>
				</tr>
				<tr>
					<td colspan="6">
						<table class="tableBasic">
							<c:choose>
								<c:when test="${not empty orderProblemList}">
									<c:forEach items="${orderProblemList}" var="problem" varStatus="vs">
										<tr>
											<td>${vs.index+1}，${problem.record}</td>
											<td>
												<c:if test="${not empty problem.pic}">
												<a href="${problem.pic}" target="_blank" style="color: blue; text-decoration: underline">查看凭证</a>
												</c:if>
												<c:if test="${empty problem.pic}">
													未上传
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td>无</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn"></div>

		<c:if test="${(orderMap.status > 2 && orderMap.status < 6) || orderMap.status == 7}">
		<div>
			<form id="saleForm">
			<input type="hidden" id="orderId" name="orderId" value="${orderMap.orderId}" />
			<input type="hidden" id="orderDetailId" name="orderDetailId" value="${detail.id}" />
			<input type="hidden" id="commodityNo" name="commodityNo" value="${detail.commodityNo}" />
			<input type="hidden" id="commodityName" name="commodityName" value="${detail.commodityName}" />
			<input type="hidden" id="cellPhone" name="cellPhone" value="${orderMap.contactPhone}" />
			<input type="hidden" name="obj" value="1" />
			<table class="tableBasic" style="float: left">
				<tr>
					<th>处理办法</th>
					<th>处理数目</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>
						<select id="method" name="method">
							<option value="">请选择处理办法</option>
							<option value="0">补发</option>
							<option value="1">退款</option>
						</select>
					</td>
					<td>
						<input type="number" id="number" name="number" class="inpMain short" value="${detail.number}" />
					</td>
					<td>
						<select id="type" name="type">
							<option value="">请选择问题类型</option>
							<option value="101">少送</option>
							<option value="102">错送</option>
							<option value="103">重量不足</option>
							<option value="104">品质问题</option>
							<option value="105">受外力破坏</option>
							<option value="106">过期</option>
							<option value="107">未冷藏</option>
							<option value="108">仓库缺货</option>
							<option value="100">无法判断</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						责任部门息（必选可多选）：
						<label><input type="checkbox" name="depts" value="1"/>生产部</label>
						<label><input type="checkbox" name="depts" value="2"/>质检部</label>
						<label><input type="checkbox" name="depts" value="3"/>配送部</label>
						<label><input type="checkbox" name="depts" value="4"/>采购部</label>
						<label><input type="checkbox" name="depts" value="5"/>仓储部</label>
						<label><input type="checkbox" name="depts" value="0"/>无法判断</label>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						凭证信息（可选）：
						<input class="inpMain" type="hidden" id="pic" name="pic"/>
						<img alt="选择文件" title="选择文件" id="picImg" style="width: 90px; height: 30px;" />
						<input class="inpFlie" type="file" id="picFile" accept=".jpg,.jpeg,.png" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="button" class="btn" id="btn_refund" value="提交售后" title="提交售后" disabled="disabled" />
					</td>
				</tr>
			</table>
			</form>
		</div>
		</c:if>
	</div>

	<script type="text/javascript">
		// 售后退款
		function saleRefund() {
			var number = $("#number").val();
			if (!number) {
				alert('客户要求退几个呢？');
				return false;
			} else {
				number = parseInt(number);
				if (number < 1) {
					alert('客户要求退几个呢？');
					return false;
				}
			}
			var money = $("#money").val();
			if (!money) {
				alert('退款的话，要写退款金额哦，你484洒');
				return false;
			}
			money = parseFloat(money);
			var actualPayment = '${detail.actualPayment}';
			actualPayment = parseFloat(actualPayment);
			if (money > actualPayment) {
				alert('最多可退￥'+actualPayment);
				return false;
			}

			if (confirm("确定要添加到售后退款单吗？")) {
				btn_disable($('#btn_refund'));
				$('#btn_refund').css('background-color', '#60BBFF');

				var url = "/orderSale/save.html";
				$.post(url, $('#saleForm').serialize(), function(data) {
					if (data.error == '0') {
						alert('退款售后已提交，请前往确认');
						$('#btn_refund').val('已申请');
						window.location.href = '/orderSale/getAll.html';
					} else {
						alert(data.msg);
						btn_enable($('#btn_refund'));
						$('#btn_refund').css('background-color', '#0072C6');
					}
				});
			}
		}

		// 补单
		function reorder() {
			if (confirm("确定要给用户补发吗？")) {
				btn_disable($('#btn_refund'));
				$('#btn_refund').css('background-color', '#60BBFF');

				var url = "/order/reorder.html";
				$.post(url, $('#saleForm').serialize(), function(data) {
					if (data.error == '0') {
						alert('商品补发成功，请检查');
						$('#btn_refund').val('已申请');
						window.location.href = '/order/list.html';
					} else {
						alert(data.msg);
						btn_enable($('#btn_refund'));
						$('#btn_refund').css('background-color', '#0072C6');
					}
				});
			}
		}

		function uploadImage($fileId, $imageObj, $image) {
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($fileId[0].files, function(i, file) {
				if (file.size/1024 > 1024) {
					alert('图片大小不能大于1M');
					return false;
				} else {
					data.append('fileName', file);
				}
			});

			$.ajax({
				url : '/hajOrderProblem/uploadFile.html',
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
			$('#btn_refund').click(function () {
				var method = $("#method").val();
				if (method) {
					var type = $("#type").val();
					if (!type) {
						alert('你还没选问题类型呢');
						return false;
					}

					var len = $("input[name='depts']:checked").length;
					if (len < 1) {
						alert('责任部门是一定要选的哦');
						return false;
					}

					if (method == 1) {
						saleRefund();
					} else if (method == 0) {
						reorder();
					}
				}
			});

			var money_html = '<input type="text" id="money" name="money" class="inpMain short"' +
				'onkeyup="value=value.replace(/[^\\d\\.\\d{2}]/g, \'\')"' +
				'value="${detail.actualPayment}"' +
				'placeholder="￥${detail.actualPayment} 可退" />';
			$('#method').change(function () {
				var method_val = $(this).val();
				if (method_val) {
					$('#btn_refund').removeProp('disabled');
					if (method_val == 1) {
						$('#type').after(money_html);
					} else {
						$('#money').remove();
					}
				} else {
					$('#money').remove();
				}
			});

			$('#picFile').change(function() {
				uploadImage($(this), $('#picImg'), $('#pic'));
			});

			$('#number').on('input', function () {
				var number = $('#number').val();
				number = parseInt(number);
				if (number && number > 0) {
					var actualPayment = '${detail.actualPayment}';
					actualPayment = parseFloat(actualPayment);
					$('#money').val($('#number').val() * actualPayment);
				}
			});
		});
	</script>
</body>
</html>
