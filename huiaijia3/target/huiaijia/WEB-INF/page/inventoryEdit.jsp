<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品库存</title>
<jsp:include page="common/public_js_css.jsp" flush="true" />
<script type="text/javascript"
	src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript"
	src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/commodity/inventoryList.html" class="actionBtn">商品销售管理</a>修改商品库存
		</h3>
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="${vo.id }" id="commentId" />
			<input type="hidden" id="inventoryInit" name="inventoryInit" value="${vo.inventoryInit }" />
			<table class="tableBasic" style="width: 100%;">
				<tr>
					<td>商品名称：</td>
					<td>${vo.name }</td>
				</tr>
				<tr>
					<td>商品物料码：</td>
					<td>${vo.sku }</td>
				</tr>

				<tr>
					<td>上下架：</td>
					<td>
						<select name="shelves" id="shelves">
							<option value="1" <c:if test="${vo.shelves==1}">selected</c:if>>上架</option>
							<option value="0" <c:if test="${vo.shelves==0}">selected</c:if>>下架</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>成品库存（份）：</td>
					<td>
						<input class="inpMain short" type="number" min="0" id="inventory" name="inventory" value="${vo.inventory }" />
						<%--<input id="btn_inventory_sync" class="btn" type="button" value="同步erp库存" title="同步erp库存" />--%>
					</td>
				</tr>
				<tr>
					<td>警告库存值（份）：</td>
					<td>
						<input class="inpMain short" type="number" min="0" id="warningInvt" name="warningInvt" value="${vo.warningInvt}" />
					</td>
				</tr>
				<tr>
					<td>供货属性：</td>
					<td>
						<select name="attribute" id="attribute">
							<option value="">请选择</option>
							<option value="0" <c:if test="${vo.attribute==0}">selected</c:if>>当天生产</option>
							<option value="1" <c:if test="${vo.attribute==1}">selected</c:if>>当天采购</option>
							<option value="2" <c:if test="${vo.attribute==2}">selected</c:if>>备货销售</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>损耗百分比：</td>
					<td>
						<input class="inpMain short" type="number" min="0" id="percentLoss" name="percentLoss" value="<fmt:formatNumber type="number" value="${vo.percentLoss}" pattern="0.00" maxFractionDigits="2"/>" /> %
					</td>
				</tr>
				<tr>
					<td>是否同步ERP库存：</td>
					<td>
						<select name="syncInvt" id="syncInvt">
							<option value="1" <c:if test="${vo.syncInvt==1}">selected</c:if>>是</option>
							<option value="0" <c:if test="${vo.syncInvt==0}">selected</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 150px;"></td>
					<td><input id="btn_submit" class="btn" type="button" value="提交" title="提交"/></td>
				</tr>
			</table>
		</form>
	</div>

	<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
	<script type="text/javascript">
	$(function() {
	    $('#btn_submit').click(function() {
	        var attribute=$.trim($('#attribute').val());
            if (!attribute) {
                alert('请选择供货属性');
                return false;
            }

	        var percentLoss=$.trim($('#percentLoss').val());
            if (!percentLoss) {
                alert('请先录入损耗百分比');
                return false;
            }

	        if (confirm('确定不是手抖吗？')) {
	        	btn_disable( $('#btn_submit'));
	        	var inventory = $('#inventory').val();
	        	var inventoryBefore = '${vo.inventory}';
	        	if (inventory != inventoryBefore) {
	        		console.log('inventory: ' + inventory);
	        		console.log('inventoryBefore: ' + inventoryBefore);
	        		console.log('inventoryInit: ' + $('#inventoryInit').val());
					$('#inventoryInit').val(inventory);
				}

	            var url = '/commodity/updateInventory.html';
	            $.post(url, $('#editForm').serialize(), function(data) {
	                if (data.error == '0') {
	                    alert('搞定啦！');
	                    window.location.href = '/commodity/inventoryList.html?' + $.cookie('haj_inventory_list_params');
	                } else if(data.error == '1'){
						alert(data.msg);
					}else {
	                    alert(UNKNOWN_ERROR);
	                }
					btn_enable( $('#btn_submit'));
	            });
	        }
	    });

	    $('#btn_inventory_sync').click(function() {
	        var commentId = $('#commentId').val();
	        if (!commentId) {
	            alert('请先录入商品物料编码');
	            return false;
	        }
	        if (confirm('不是手抖吧？')) {
				btn_disable( $('#btn_inventory_sync'));
	            var url = '/erp/inventory/syncInventory.html?id=' + commentId;
	            $.get(url,
	            function(data) {
	                if (data.error == '0') {
	                    $('#inventory').val(data.inventory);
	                    $('#btn_inventory_sync').val('已同步');
	                    $('#btn_inventory_sync').prop('class', 'btnGray');
	                } else {
	                    alert(data.msg);
						btn_enable( $('#btn_inventory_sync'));
	                }
	            });
	        }
	    });
	});
	</script>
</body>
</html>