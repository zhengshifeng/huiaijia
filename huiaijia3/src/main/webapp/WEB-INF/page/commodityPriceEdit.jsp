<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品价格编辑</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js" language="javascript"  charset="UTF8"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			商品价格编辑
		</h3>
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="${vo.id }" />
			<table class="tableBasic" style="width: 100%;  ">
				<tr>
					<td>供应城市：</td>
					<td>
						<c:forEach items="${cityList}" var="list">
							<c:if test="${vo.areaCode==list.code}">
								${list.name }
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>供应商：</td>
					<td>
						<c:forEach items="${supplyChainNames}" var="list">
							<c:if test="${vo.supplyChain==list.cloudsSupplierId}">
								${list.name }
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>商品类别：</td>
					<td>
						${vo.commodityAttr} - ${vo.parentTypeName} - ${vo.typeName}
					</td>
				</tr>
				<tr>
					<td>商品名称：</td>
					<td>
						${vo.name }
					</td>
				</tr>
				<tr>
					<td><label>包装规格</label>：</td>
					<td>
						${vo.weight }
					</td>
				</tr>
				<tr>
					<td><label>成本价</label>：</td>
					<td>
						<input class="inpMain required" type="number" name="costPrice" value="${vo.costPrice }" />
					</td>
				</tr>
				<tr>
					<td><label>售价</label>：</td>
					<td>
						<input class="inpMain required" type="number" name="originalPrice" value="${vo.originalPrice }" />
					</td>
				</tr>
				<tr>
					<td><label>参考价</label>：</td>
					<td>
						<input class="inpMain required" type="number" name="marketPrice" value="${vo.marketPrice }" />
					</td>
				</tr>
				<tr>
					<td>活动价格：</td>
					<td>
						<c:if test="${vo.activityId > 0}">
						<input class="inpMain short" type="number" name="promotionPrice" value="${vo.promotionPrice }" />
						</c:if>
						<c:if test="${vo.activityId <= 0}">
						<input type="hidden" name="promotionPrice" value="${vo.promotionPrice }" />${vo.promotionPrice }
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="width: 150px;"></td>
					<td>
						<input id="btn_submit" class="btn" type="button" value="提交" title="提交" />
					</td>
				</tr>
			</table>
			<input type="hidden" id="activityId" value="${vo.activityId}" />
			<input type="hidden" name="passport" />
		</form>
	</div>

	<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
	<script type="text/javascript">

		// 页面加载完之后，先记录修改前的价格，如果保存前价格被修改过，则进一步判断价格是否符合规定
		// 否则无需判断价格
		var before_cost_price;
		var before_promotion_price;
		var before_original_price;
		var before_market_price;
		$(function(){
			before_cost_price = $('input[name="costPrice"]').val();
			before_promotion_price = $('input[name="promotionPrice"]').val();
			before_original_price = $('input[name="originalPrice"]').val();
			before_market_price = $('input[name="marketPrice"]').val();
		});

		/**
		 * 价格合法性校验：
		 *    成本价 <= 活动价 <= 售价 < 市场参考价
		 */
		function checkPrice(costPrice, promotionPrice, originalPrice, marketPrice) {
			try {
				// 当价格发生改变时，才需要判断
				if (costPrice !== before_cost_price
					|| promotionPrice !== before_promotion_price
					|| originalPrice !== before_original_price
					|| marketPrice !== before_market_price) {

					// 当比较的位数不同时，比较值类型会变，因此需要转换成同一类型进行比较
					costPrice = parseFloat(costPrice);
					promotionPrice = parseFloat(promotionPrice);
					originalPrice = parseFloat(originalPrice);
					marketPrice = parseFloat(marketPrice);

					if (promotionPrice && promotionPrice > 0) {
						return (costPrice <= promotionPrice && promotionPrice <= originalPrice
						&& originalPrice < marketPrice);
					} else {
						return (costPrice <= originalPrice && originalPrice < marketPrice);
					}
				} else {
					return true;
				}
			} catch(e) {
				return false;
			}
		}

		/**
		 * 检查商品参加活动过后是否设置过活动价
		 * @returns {boolean}
		 */
		function promotionPriceCheck() {
			var select_activity = $('#activityId').val();
			if (select_activity && parseInt(select_activity) > 0) {
				var promotionPrice = $('input[name="promotionPrice"]').val();
				if (promotionPrice && parseFloat(promotionPrice) > 0) {
					return true;
				} else {
					if (confirm('该商品参与活动，是否将商品活动价设置为0元？')) {
						var passport = prompt('请输入管理口令');
						if (passport) {
							$('input[name="passport"]').val(passport);
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				}
			} else {
				return true;
			}
		}

		$(function() {
			// 所有的校验通过后设为true
			var permission = false;

			// 验证价格的正则表达式，2位小数
			var exp_money = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			$('#btn_submit').click(function() {
				if (!(exp_money.test($('input[name="costPrice"]').val()))) {
					$('input[name="costPrice"]').focus();
					alert('成本价保留两位小数');
				} else if (!(exp_money.test($('input[name="marketPrice"]').val()))) {
					$('input[name="marketPrice"]').focus();
					alert('市场参考价保留两位小数');
				} else if (!(exp_money.test($('input[name="originalPrice"]').val()))) {
					$('input[name="originalPrice"]').focus();
					alert('汇爱家价格保留两位小数');
				} else {
					permission = checkPrice(
						$('input[name="costPrice"]').val(),
						$('input[name="promotionPrice"]').val(),
						$('input[name="originalPrice"]').val(),
						$('input[name="marketPrice"]').val()
					);
					if (!permission) {
						if (confirm('价格不符合规定，确定要这样做吗？')) {
							var passport = prompt('强行修改价格需要管理口令');
							if (passport) {
								$('input[name="passport"]').val(passport);
								permission = true;
							}
						}
					}

					// 前面通过后再判断是否参加活动
					if (permission) {
						permission = promotionPriceCheck();
					}
				}

				if (permission && requiredCheck() && confirm('不是手抖吧？')) {
					window.parent.layer.load(2);
					btn_disable($('#btn_submit'));
					var url = '/commodityPrice/save.html';
					$.post(url, $('#editForm').serialize(), function(data) {
						if (data.error == '0') {
							alert('好了！');
							<c:if test="${empty vo.id }">
							window.location.href = '/commodityPrice/list.html';
							</c:if>
							<c:if test="${not empty vo.id }">
							window.location.href = '/commodityPrice/list.html?'+$.cookie('haj_commodity_price_list_params');
							</c:if>
						} else {
							alert(data.msg);
							btn_enable($('#btn_submit'));
						}
						window.parent.layer.closeAll('loading');
					});
				}
			});
		});

		prependRedStar();
	</script>
</body>
</html>