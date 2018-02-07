<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现申请</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>提现申请</h3>
		<form action="getAll.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				状态：
				<select name="status" id="status">
					<option value="">不限</option>
					<option value="1" <c:if test="${withdrawals.status=='1'}">selected</c:if>>成功</option>
					<option value="0" <c:if test="${withdrawals.status=='0'}">selected</c:if>>失败</option>
				</select>
				
				<input placeholder="手机号码" class="inpMain" type="text" name="mobilePhone" value="${withdrawals.mobilePhone }" />
				<input class="btn" type="button" value="搜索" onclick="search();" />
			</div>
		
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th style="width: 7px;">
							<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />
						</th>
						<th>ID</th>
						<th>用户ID</th>
						<th>手机号码</th>
						<th>申请提现金额</th>
						<th>申请时间</th>
						<th>是否成功</th>
						<th>操作时间</th>
						<th>备注</th>
						<th>财务确认</th>
					</tr>
					<c:choose>
						<c:when test="${not empty withdrawalsList}">
							<c:forEach items="${withdrawalsList}" var="order" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="orderIds" id="orderIds${order.id }" value="${order.id }" />
									</td>
									<td>${order.id }</td>
									<td>${order.userId }</td>
									<td>${order.mobilePhone }</td>
									<td>${order.money }</td>
									<td>${order.createTime }</td>
									<td>
										<c:if test="${order.status=='1'}">成功 </c:if>
										<c:if test="${order.status=='0'}">失败 </c:if>
									</td>
									<td>${order.operationTime }</td>
									<td>${order.remark }</td>
									<td>
										<c:if test="${order.isConfirm=='1'}">已退款 </c:if>
										<c:if test="${order.isConfirm=='0'}">
											<a class="btnGray" href="javascript:confirmWithdrawals('${order.id}','${order.userId }');">确认</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="11">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>

		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<a href="javascript:exportwithdrawals();" class="btn">
					<em>导出至excel</em>
				</a>
				
			</div>
			${withdrawals.page.pageStr }
		</div>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='orderIds']").prop("checked", true);
			} else {
				$("input[name='orderIds']").prop("checked", false);
			}
		}

		function exportwithdrawals() {
			var status = $("#status").val();
			var mobilePhone = $("input[name='mobilePhone']").val();
			document.location = "/withdrawals/excel.html?status=" + status + "&mobilePhone=" + mobilePhone;
		}
		
		function confirmWithdrawals(id,userId) {
		    var url='/withdrawals/withdrawalsPage.html?id='+id+'&userId='+userId; //转向网页的地址;
	        var name='withdrawals'; //网页名称，可为空;
	        var iWidth=720; //弹出窗口的宽度;
	        var iHeight=400; //弹出窗口的高度;
	        //获得窗口的垂直位置 
	        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
	        //获得窗口的水平位置 
	        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
	        window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
		}

		function search() {
			$("#userForm").submit();
		}
	</script>
</body>
</html>
