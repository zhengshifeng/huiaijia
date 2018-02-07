<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送费支付查询</title>
<link href="../../style/css/public.css?v=20161223" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../style/js/jquery.min.js"></script>
<script type="text/javascript" src="../../style/js/global.js"></script>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>配送费支付查询</h3>
		<form action="/orderPostFee/getAll.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				支付方式：
				<select name="rechargeType" id="rechargeType" style="vertical-align: middle;">
					<option value="-1" <c:if test="${postFee.rechargeType=='-1'}">selected</c:if>>不限</option>
					<option value="-2" <c:if test="${postFee.rechargeType=='-2'}">selected</c:if>>余额</option>
					<option value="1" <c:if test="${postFee.rechargeType=='1'}">selected</c:if>>微信</option>
					<option value="2" <c:if test="${postFee.rechargeType=='2'}">selected</c:if>>支付宝</option>
				</select>

				支付状态：
				<select name="isPay" id="isPay">
					<option value="" <c:if test="${postFee.isPay==''}">selected</c:if>>不限</option>
					<option value="0" <c:if test="${postFee.isPay==0}">selected</c:if>>待支付</option>
					<option value="1" <c:if test="${postFee.isPay==1}">selected</c:if>>已支付</option>
					<option value="2" <c:if test="${postFee.isPay==2}">selected</c:if>>后台取消</option>
				</select>

				手机号码：
				<input type="text" placeholder="手机号码" class="inpMain" name="mobilePhone" value="${postFee.mobilePhone }" />
			</div>
			<div class="filter" >
				生成时间：
				<input type="text" class="inpMain short date_picker" name="beginTime" value="${postFee.beginTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
					   readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" name="endTime" class="inpMain short date_picker" value="${postFee.endTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
					   readonly="readonly" placeholder="结束时间" />
				支付时间：
				<input type="text" class="inpMain short date_picker" name="payBeginTime" value="${postFee.payBeginTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2017-04-11 00:00:00',readOnly:'readOnly'})"
					   readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" name="payEndTime" class="inpMain short date_picker" value="${postFee.payEndTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2017-04-11 00:00:00',readOnly:'readOnly'})"
					   readonly="readonly" placeholder="结束时间" />
		
				<input class="btn" type="submit" value="搜索"/>
			</div>

		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>
							<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />序号
						</th>
						<th>手机号码</th>
						<th>应收配送费</th>
						<th>生成时间</th>
						<th>支付状态</th>
						<th>实付配送费</th>
						<th>支付时间</th>
						<th>支付方式</th>
						<th>支付流水号</th>
					</tr>
					<c:choose>
						<c:when test="${not empty postFeeList}">
							<c:forEach items="${postFeeList}" var="order" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="orderIds" id="orderIds${order.orderId }"
											   value="${order.orderId }" />${vs.index + 1}
									</td>
									<td>${order.mobilePhone }</td>
									<td>${order.postFee }</td>
									<td>${order.remark }</td>
									<td><c:if test="${order.isPay=='0'}">待支付 </c:if>
										<c:if test="${order.isPay=='1'}">已支付 </c:if>
										<c:if test="${order.isPay=='2'}">后台取消 </c:if>
									</td>
									<td>
										<c:if test="${order.isPay=='0'}">0 </c:if>
										<c:if test="${order.isPay=='1'}">${order.postFee } </c:if>
										<c:if test="${order.isPay=='2'}">0 </c:if>	
									</td>
									<td>
										${order.payTime }
									</td>
									<td>
										<c:if test="${order.rechargeType=='0'}">余额</c:if>
										<c:if test="${order.rechargeType=='1'}">微信</c:if>
										<c:if test="${order.rechargeType=='2'}">支付宝</c:if>	
										</td>
									<td>${order.bankNo }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="19">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="search_div" style="line-height: 30px; text-align: right;">
			<span style="padding-right: 15px; font-size: 20px;">应收取: ${totalsMap['receivableTotal'] }</span>
			<span style="padding-right: 15px; font-size: 20px;" id="refund">实收: ${totalsMap['paidTotal']} </span>
			<span style="padding-right: 15px; font-size: 20px;" id="refund">待收取：${totalsMap['notPaidTotal']}</span>
			<div class="page_and_btn">
		<div class="page_and_btn">
			<div>
				<input type="button" id="btn_excel" class="btn" value="导出列表" title="导出列表" />
			</div>
			${postFee.page.pageStr }
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

		$(function() {
			$('#btn_excel').click(function(){
				btn_disable($(this));
				document.location = "/orderPostFee/excelOrderPostFee.html?" + $('#userForm').serialize();
				setTimeout('btn_enable($("#btn_excel"))', 10000);
			});
		});
	</script>
</body>
</html>
