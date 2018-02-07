<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值套餐添加</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<c:if test="${empty hajRechargePackage.id }">
		添加充值套餐
		</c:if>
		<c:if test="${not empty hajRechargePackage.id }">
		修改充值套餐
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<c:if test="${not empty hajRechargePackage.id }">
	<input type="hidden" name="id" value="${hajRechargePackage.id }" />
	</c:if>
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td><label>套餐名称</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.name }">
                ${hajRechargePackage.name}
				</c:if>
				<c:if test="${empty hajRechargePackage.name }">
				<input class="inpMain required" required type="text" name="name" value="${hajRechargePackage.name }" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td><label>备注</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.remark }">
					${hajRechargePackage.remark}
				</c:if>
				<c:if test="${empty hajRechargePackage.remark }">
					<input class="inpMain required" required type="text" name="remark" value="${hajRechargePackage.remark}" />
				</c:if>

			</td>
		</tr>
		<tr>
			<td><label>购买金额</label>：</td>
			<td>

				<c:if test="${not empty hajRechargePackage.purchaseAmount }">
					${hajRechargePackage.purchaseAmount}
					<input  type="hidden" name="purchaseAmount"   id="purchaseAmount"  value="${hajRechargePackage.purchaseAmount }" />
				</c:if>
				<c:if test="${empty hajRechargePackage.purchaseAmount }">
					<input class="inpMain required" required type="number" name="purchaseAmount"  id="purchaseAmount" value="${hajRechargePackage.purchaseAmount }" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td><label>赠送金额</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.donationAmount }">
					${hajRechargePackage.donationAmount}
					<input required type="hidden" name="donationAmount"  id="donationAmount" value="${hajRechargePackage.donationAmount }" />

				</c:if>
				<c:if test="${empty hajRechargePackage.donationAmount }">
					<input class="inpMain"   type="text" name="donationAmount" id="donationAmount" value="${hajRechargePackage.donationAmount }" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td><label>到账金额</label>：</td>
			<td>
				<c:if test="${empty hajRechargePackage.accountAmount }">
				  购买金额+赠送金额
				</c:if>
				<c:if test="${not empty hajRechargePackage.accountAmount }">
					${hajRechargePackage.accountAmount}
				</c:if>

			</td>
		</tr>
		<tr>
			<td><label>列表排序值</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.sort }">
					${hajRechargePackage.sort}
				</c:if>
				<c:if test="${empty hajRechargePackage.sort }">
					<input class="inpMain required" required type="number" name="sort" value="${hajRechargePackage.sort }" style="width: 60px;"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td><label>是否生效</label>：</td>
			<td>
				<select name="status" id="status">
					<option value="">请选择</option>
					<option value="0" <c:if test="${hajRechargePackage.status==0}">selected</c:if>>生效</option>
					<option value="1" <c:if test="${hajRechargePackage.status==1}">selected</c:if>>作废</option>
				</select>

			</td>
		</tr>
		<tr>
			<td><label>内部备注</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.internalRemark }">
					${hajRechargePackage.internalRemark}
				</c:if>
				<c:if test="${empty hajRechargePackage.internalRemark }">
 					<textarea rows="4" cols="50" required name="internalRemark" id="internalRemark" class="textArea"> </textarea>
				</c:if>
			</td>
		</tr>
		<tr>
			<td><label>创建人</label>：</td>
			<td>
				<c:if test="${not empty hajRechargePackage.createUser }">
					${hajRechargePackage.createUser}
				</c:if>
			</td>
		<tr>
			<td style="width: 150px;"></td>
			<td>
				<input id="btn_cancel" class="btn" type="button" value="取消" />
				<input id="btn_submit" class="btn" type="button" value="保存并返回" />
			</td>
		</tr>
	</table>
	</form>	
</div>

<script type="text/javascript">
$(function(){
	prependRedStar();
	$('#btn_submit').click(function(){
	    var purchaseAmount=$('#purchaseAmount').val();
	    if(purchaseAmount<0){
            alert('购买金额请输入整数!');
            return false;
		}
        var donationAmount=$('#donationAmount').val();
        if(donationAmount<0){
            alert('赠送金额请输入整数!');
            return false;
        }
        var status=$.trim($('#status').val());
        if (!status) {
            alert('请选择是否生效');
            return false;
        }
		if (requiredCheck()) {
			var url = '/rechargePackage/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data.msg === 'success') {
					window.parent.layer.msg('保存成功');
					window.location.href = '/rechargePackage/list.html';
				} else {
					alert(UNKNOWN_ERROR);
				}
			});
		}
	});
	
	$('#btn_cancel').click(function () {
		window.history.back();
	});
});
</script>
</body>
</html>