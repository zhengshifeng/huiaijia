<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值套餐记录详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<c:if test="${not empty hajRechargePackageRecord.id }">
		 充值套餐记录详情
		</c:if>
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<c:if test="${not empty hajRechargePackageRecord.id }">
	<input type="hidden" name="id" value="${hajRechargePackageRecord.id }" />
	</c:if>
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td><label>客户id</label>：</td>
			<td>
                ${hajRechargePackageRecord.userId}
		     <input type="hidden" name="userId" value="${hajRechargePackageRecord.userId}"  id="userId"/>

			</td>
		</tr>
		<tr>
			<td><label>客户手机号码</label>：</td>
			<td>
				${hajRechargePackageRecord.phone}
			</td>
		</tr>
		<tr>
			<td><label>购买金额</label>：</td>
			<td>
 			   ${hajRechargePackageRecord.purchaseAmount}
			</td>
		</tr>
		<tr>
			<td><label>支付状态</label>：</td>
			<td>
				<c:if test="${hajRechargePackageRecord.payStatus==0}">失败</c:if>
				<c:if test="${hajRechargePackageRecord.payStatus==1}">成功</c:if>
			</td>
		</tr>
		<tr>
			<td><label>支付流水号</label>：</td>
			<td>
 					${hajRechargePackageRecord.payNumber}

			</td>
		</tr>
		<tr>
			<td><label>可退款金额</label>：</td>
			<td>
				${hajRechargePackageRecord.refundableAmount}
				<input type="hidden" name="refundableAmount" value="${hajRechargePackageRecord.refundableAmount}"  id="refundableAmount"/>
			</td>
		</tr>
		<tr>
			<td><label>退款金额</label>：</td>
			<td>
				<input class="inpMain required" required type="number"  id="refundAmount" name="refundAmount" value="" style="width: 60px;"/>
			</td>
		</tr>
		<tr>
			<td><label>操作备注</label>：</td>
			<td>
				<input class="inpMain required" required name="remark" value="" style="width: 60px;"/>
			</td>
		</tr>
		<tr>
			<td style="width: 150px;"></td>
			<td>
				<input id="btn_cancel" class="btn" type="button" value="取消" />
				<c:if test="${hajRechargePackageRecord.payStatus==1}">
				<input id="btn_submit" class="btn" type="button" value="保存并返回" />
				</c:if>
			</td>
		</tr>
	</table>
	</form>	
</div>

<div class="mainBox">
	<h3>退款记录</h3>
	<form name="deleteForm" id="deleteForm">
		<table style="width: 100%;" class="tableBasic list">
			<tr>
				<th>序号</th>
				<th>退款金额</th>
				<th>操作员</th>
				<th>日期</th>
				<th>操作备注</th>
			</tr>
			<c:choose>
				<c:when test="${not empty hajRefundsRecordList}">
					<c:forEach items="${hajRefundsRecordList}" var="refundsRecord" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
							<td>${refundsRecord.refundAmount}</td>
							<td>${refundsRecord.operator}</td>
							<td>
								<fmt:formatDate value="${refundsRecord.operatorTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
 							</td>
							<td>${refundsRecord.operatorRemark}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="7">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
	prependRedStar();
	$('#btn_submit').click(function(){
        var refundableAmount=$.trim($('#refundableAmount').val());
		var refundAmount=$.trim($('#refundAmount').val());
        if (!refundAmount) {
            alert('请输入退款金额!');
            return false;
        }
        if(parseFloat(refundAmount)>parseFloat(refundableAmount)){
            alert('退款金额不能大于可退款金额，请重新输入!');
            return false;
		}
		if (requiredCheck()) {
			var url = '/rechargePackageRecord/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data.msg === 'success') {
					window.parent.layer.msg('保存成功');
					window.location.href = '/rechargePackageRecord/list.html';
				} else {
					alert(data.msg);
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