<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自主加工商品损耗率表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>自主加工商品损耗率表</h3>
	<form action="/hajCommodityWastageRate/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			<select name="commodityAttr" id="commodityAttr" style="vertical-align: middle;">
				<option value="">商品属性</option>
				<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
				<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
				<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
				<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
			</select>
			<select name="parentTypeId" id="parentTypeId" style="vertical-align: middle;">
				<option value="0">大类</option>
				<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="vertical-align: middle; margin-right: 15px;">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			
			往期数据：
			<input class="inpMain short date_picker" type="text" id="beginDate" name="beginDate" value="${vo.beginDate}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'${now}'})" readonly="readonly" />
			-
			<input class="inpMain short date_picker" type="text" id="endDate" name="endDate" value="${vo.endDate}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'${now}'})" readonly="readonly" />
			<input class="btn" type="submit" value="搜索" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;"
				class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>保存时间</th>
					<th>商品分类</th>
					<th>商品名</th>
					<th>包装规格/kg</th>
					<th>包装份数/份</th>
					<th>包装净重/kg</th>
					<th>出库重量/kg</th>
					<th>损耗率</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityWastageRateList}">
						<c:forEach items="${commodityWastageRateList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>${vs.index+1 }</td>
							<td>
								<fmt:parseDate value="${list.createTime}" pattern="yyyy-MM-dd" var="parseValue"/>
								<fmt:formatDate value="${parseValue}" pattern="yyyy-MM-dd"/>
							</td>
							<td>${list.commodityAttr } - ${list.parentTypeName } - ${list.typeName }</td>
							<td>${list.commodityName }</td>
							<td>${list.packedWeight }</td>
							<td>${list.todaySales }</td>
							<td>${list.grossWeight }</td>
							<td>
								<input type="text" value="${list.outputWeight }" readonly="readonly" 
									class="inpMain short inp_outputWeight" 
									id="outputWeight_${vs.index+1 }_${list.commodityId }" 
									data-id="${list.id }" 
									data-commodityId="${list.commodityId }" 
									data-packedWeight="${list.packedWeight }" 
									data-todaySales="${list.todaySales }" 
									data-grossWeight="${list.grossWeight }" />
							</td>
							<td>${list.wastageRate }%</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="9">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div>
			<a id="btn_excel" class="btn" style="margin-right: 7px;">
				<em>导出excel</em>
			</a>
		</div>
		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript">
	$(function(){
		var ids = '';
		// 批量导出
		$('#btn_excel').click(function(){
			var commodityAttr = $("#commodityAttr").val();
			var parentTypeId = $("#parentTypeId").val();
			var typeId = $("#typeId").val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();

			document.location = "/hajCommodityWastageRate/export2excel.html?commodityAttr="
					+ commodityAttr + "&parentTypeId=" + parentTypeId + "&typeId=" + typeId 
					+ "&beginDate=" + beginDate + "&endDate=" + endDate;
					
		});
		
		
		$('.inp_outputWeight').click(function(){
			var outputWeight = prompt('请输入出库重量/kg', 0);
			var id = $(this).attr('data-id');
			var commodityId = $(this).attr('data-commodityId');
			var packedWeight = $(this).attr('data-packedWeight');
			var todaySales = $(this).attr('data-todaySales');
			var grossWeight = $(this).attr('data-grossWeight');
			
			if (outputWeight && outputWeight > 0) {
				var url = '/hajCommodityWastageRate/save.html';
				var postData = 'id='+id
				postData += '&commodityId='+commodityId
				postData += '&packedWeight='+packedWeight
				postData += '&packedNumber='+todaySales
				postData += '&grossWeight='+grossWeight
				postData += '&outputWeight='+outputWeight
				$.post(url, postData, function(data) {
					if (data.status == 'success') {
						window.location.reload();
					} else {
						alert(data.rate);
					}
				});
			}
		});
	});
	
</script>
</body>
</html>
