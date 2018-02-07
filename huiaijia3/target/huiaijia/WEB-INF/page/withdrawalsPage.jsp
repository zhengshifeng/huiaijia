<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品采购单列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>客户申请提现工单备注</h3>
		<table style="border: 0; padding: 0;">
			<tr class="info">
				<th>成功/失败:</th>
				<td>
					<select name="status" id="status" style="vertical-align: middle;">
						<option value="1">成功</option>
						<option value="0">失败</option>
					</select>
				</td>
			</tr>

			<tr class="info">
				<th>备注:</th>
				<td>
					<textarea id="remark" class="textArea" cols="100" rows="3" placeholder="必填"></textarea>
				</td>
			</tr>
			<tr class="info">
				<th></th>
				<td>
					<a href="javascript:addWithdrawals(${id},${userId});" class="btn">
						<em>提交</em>
					</a>
				</td>
			</tr>
		</table>
	</div>


	<script type="text/javascript">
		function addWithdrawals(id, userId) {
			var remark = $("#remark").val();
			var status = $("#status").val();
			if (confirm("确定对已提现订单进行退款操作吗？")) {
				var url = "/withdrawals/confirmWithdrawals.html?id=" + id + "&userId=" + userId + "&status=" + status
						+ "&remark=" + remark;
				$.get(url, function(data) {
					if (data == "1") {
						alert("成功！");
						window.opener.location.href = window.opener.location.href;
						window.close();
					} else {
						alert("失败！");
					}
				});
			}
		}
	</script>
</body>
</html>
