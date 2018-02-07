<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改优惠券</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/hajCouponInfo/list.html?category=${category}" class="actionBtn">优惠券管理</a>修改优惠券
	</h3>
	<form method="post" name="editForm" id="editForm">
		<c:if test="${not empty vo.id}">
		<input type="hidden" name="id" value="${vo.id }" />
		</c:if>
		<input type="hidden" name="category" value="${category }" />
		<table class="tableBasic" style="width: 100%;">
			<tr>
				<th colspan="2" style="text-align: left;">优惠信息：</th>
			</tr>
			<tr>
				<td>卡券名称：</td>
				<td>
					<input class="inpMain" type="text" name="name" value="${vo.name }"
						   placeholder="10字以内" maxlength="10"
					/>
				</td>
			</tr>
			<tr>
				<td>使用说明：</td>
				<td>
					<textarea rows="4" cols="50" name="remark" id="remark" class="textArea" maxlength="30" placeholder="30字以内">${vo.remark }</textarea>
				</td>
			</tr>
			<tr>
				<td>*优惠金额：</td>
				<td>
					<c:if test="${empty vo.id}">
					<input class="inpMain short" type="number" name="couponMoney" value="0"/>
					</c:if>
					<c:if test="${not empty vo.id}">
						￥${vo.couponMoney}
					</c:if>
				</td>
			</tr>
			<tr>
				<td>*使用门槛：</td>
				<td>
					<c:if test="${empty vo.id}">
						<label>
							<input type="radio" name="radio_rule" value="1" checked/>
							满&nbsp;<input type="number" id="rule" name="rule" class="inpMain short"
										  value="0" style="width: 20px;"/>&nbsp;元使用
						</label>
						<br />
						<label style="line-height: 38px;">
							<input type="radio" name="radio_rule" value="2"/>无条件使用
						</label>
						<br />
						备注：满元金额不能为空且必须为正数
					</c:if>
					<c:if test="${not empty vo.id}">
						<c:if test="${vo.rule > 0}">
						满${vo.rule}元使用
						</c:if>
						<c:if test="${vo.rule < 1}">
						无条件使用
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>*有效时间：</td>
				<td>
					<c:if test="${empty vo.id}">
					<label>
						<input type="radio" name="invalidWay" value="0" checked/>
						绝对时间：
						<input type="text" class="inpMain short date_picker" name="beginTime" id="beginTime"
							   onclick="WdatePicker()" readonly="readonly"
								<c:if test="${not empty vo.beginTime }">value="<fmt:formatDate value="${vo.beginTime}" pattern="yyyy-MM-dd"/>" </c:if>
						/> 至
						<input type="text" class="inpMain short date_picker" name="endTime" id="endTime"
							  onclick="WdatePicker()" readonly="readonly"
							   <c:if test="${not empty vo.endTime }">value="<fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd"/>" </c:if>
						/>
					</label>
					<br />
					<label style="line-height: 38px;">
						<input type="radio" name="invalidWay" value="1"/>
						相对时间（即领取
						<input type="number" name="dateNumbers" id="dateNumbers" class="inpMain short"
							   value="0" disabled/>
						天内可以使用）
					</label>
					<br />
					备注：用户可以提前领取
					</c:if>
					<c:if test="${not empty vo.id}">
						<c:if test="${vo.invalidWay == 0}">
							<fmt:formatDate value="${vo.beginTime}" pattern="yyyy-MM-dd"/>
							至
							<fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd"/>
						</c:if>
						<c:if test="${vo.invalidWay == 1}">
							领取${vo.dateNumbers}天内可以使用
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<th colspan="2" style="text-align: left;">基本规则：</th>
			</tr>
			<tr>
				<td>*门槛金额计算方式：</td>
				<td>
					<c:if test="${empty vo.id}">
					<label>
						<input type="checkbox" name="type" value="0" checked/>仅统计按照原售价结算的有效商品
					</label>
					</c:if>
					<c:if test="${not empty vo.id && vo.type == 0}">
						仅统计按照原售价结算的有效商品
					</c:if>
					<c:if test="${not empty vo.id && vo.type == 1}">
						无限制
					</c:if>
				</td>
			</tr>
			<tr>
				<td>发放数量：</td>
				<td>
					<input type="number" id="number" name="number" value="${vo.number}" class="inpMain short"/>
					张（只能填写整数哦，修改只可以增加不可减少，谨慎设置！）
				</td>
			</tr>
			<tr>
				<td>*每人限领：</td>
				<td>
					<c:if test="${empty vo.id}">
						<input type="number" id="limitNumber" name="limitNumber" value="${vo.limitNumber}"
							   class="inpMain short"/>
						张（只能填写整数哦）
					</c:if>
					<c:if test="${not empty vo.id}">
						${vo.limitNumber}张
					</c:if>
				</td>
			</tr>
			<tr>
				<td>*会员限制：</td>
				<td>
					<c:if test="${empty vo.id}">
					<select name="memberLimit" id="memberLimit" style="vertical-align: middle;">
						<option value="0">无限制</option>
						<option value="2">预备会员</option>
						<option value="3">一元购会员</option>
						<option value="5">普通会员</option>
					</select>
					</c:if>
					<c:if test="${not empty vo.id}">
						<c:choose>
							<c:when test="${vo.memberLimit == 0}">无限制</c:when>
							<c:when test="${vo.memberLimit == 2}">预备会员</c:when>
							<c:when test="${vo.memberLimit == 3}">一元购会员</c:when>
							<c:when test="${vo.memberLimit == 5}">普通会员</c:when>
						</c:choose>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>创建人：</td>
				<td>${vo.creator}</td>
			</tr>
			<tr>
				<td>创建时间：</td>
				<td><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			<tr>
				<td>创建备注：</td>
				<td>
					<textarea class="textArea" name="createRemark" rows="4" cols="50">${vo.createRemark}</textarea>
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
		if ($('input[name="radio_rule"]:checked').val() == 1) {
			$('#rule').removeAttr('readonly');
		} else {
			$('#rule').attr('readonly', 'readonly');
			$('#rule').val(0);
		}
	}

	function showInvalidWay() {
		if ($('input[name="invalidWay"]:checked').val() == 0) {
			$('#beginTime').removeAttr('disabled');
			$('#endTime').removeAttr('disabled');
			$('#dateNumbers').attr('disabled', 'disabled');
		} else {
			$('#beginTime').attr('disabled', 'disabled');
			$('#endTime').attr('disabled', 'disabled');
			$('#dateNumbers').removeAttr('disabled');
		}
	}

	$(function() {
		var number_before = parseInt($('#number').val());

		showRule();
		showInvalidWay();

		$('input[name="radio_rule"]').click(function () {
			showRule();
		});

		$('input[name="invalidWay"]').click(function () {
			showInvalidWay();
		});

		$('#btn_submit').click(function() {
			<c:if test="${empty vo.id}">
			if (!/^[0-9]+$/.test($('#rule').val())) {
				alert('使用门槛满元金额不能为空且必须为正数');
				return false;
			}
			if (!/^[0-9]+$/.test($('#limitNumber').val())) {
				alert('每人限领数量只能输入正整数');
				return false;
			}
			</c:if>

			if ($('input[name="invalidWay"]:checked').val() == 1 && $('#dateNumbers').val() < 1) {
				alert('相对时间必须设置1天或以上');
				$('#dateNumbers').focus();
				return false;
			}

			if (!/^[0-9]+$/.test($('#number').val()) || parseInt($('#number').val()) < number_before) {
				alert('发放数量只能增加且只能为正整数');
				return false;
			}

			if (confirm('带星号的项填写完之后将不可修改，确认要提交吗？')) {
				btn_disable($('#btn_submit'));
				var url = '/hajCouponInfo/save.html';
				$.post(url, $('#editForm').serialize(), function(data) {
					if (data == 'success') {
						alert('搞定啦！');
						window.location.href = '/hajCouponInfo/list.html?category=${category }';
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