<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务报表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>财务报表</h3>
			
			<div class="filter" >
				下单日期范围：
				<input type="button" class="btnGray" id="btn_today" value="今日" />
				<input type="text" class="inpMain short date_picker" id="beginTime" name="beginTime" value="${order.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" id="endTime" name="endTime" class="inpMain short date_picker" value="${order.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="结束时间" />

			</div>
			
			<div class="filter" >
				<input type="text" class="inpMain short" placeholder="用户编号/手机号码" name="mobilePhone" value="${order.mobilePhone }" />

				<input type="text" class="inpMain short" placeholder="小区ID/小区名称" name="residential" value="${order.residential }" />

			</div>

		
		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<input type="button" id="btn_excel" class="btn" value="订单报表导出" title="订单报表导出" />
				<input type="button" id="btn_hebingexcel" class="btn" value="客户余额明细导出" title="客户余额明细导出" />
			</div>
		</div>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	

		$(function() {
			// 批量导出
			$('#btn_excel').click(function(){
				btn_disable($('#btn_excel'));
				var beginTime = $("input[name='beginTime']").val();
				var endTime = $("input[name='endTime']").val();
				var mobilePhone = $("input[name='mobilePhone']").val();
				var residential = $("input[name='residential']").val();
			

				document.location = "/tradingHistory/batchFinancial.html?beginTime=" + beginTime + "&endTime=" + endTime
					+ "&mobilePhone=" + mobilePhone + "&residential=" + residential;

				setTimeout('btn_enable($("#btn_excel"))', 10000);
			});
		});
		
		
		$(function() {
			// 合并导出
			$('#btn_hebingexcel').click(function(){
				var beginTime = $("input[name='beginTime']").val();
				var endTime = $("input[name='endTime']").val();
				var mobilePhone = $("input[name='mobilePhone']").val();
				var residential = $("input[name='residential']").val();
			
				document.location = "/batchCustomer.html?beginTime=" + beginTime + "&endTime=" + endTime
				+ "&mobilePhone=" + mobilePhone + "&residential=" + residential;

				setTimeout('btn_enable($("#btn_hebingexcel"))', 10000);
			});
		});

	

		$(function(){
			$('#btn_today').click(function(){
				var today = date_format(new Date(), '-');
				$('#beginTime').val(today + ' 00:00:00');
				$('#endTime').val(today + ' 23:59:59');
			});
		});
			
	</script>
</body>
</html>
