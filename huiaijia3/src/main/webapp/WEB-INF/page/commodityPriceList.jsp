<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品价格管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>商品价格管理</h3>
	<form action="/commodityPrice/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			商品分类：
			<select name="commodityAttr" id="commodityAttr">
				<option value="">商品属性</option>
				<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
				<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
				<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
				<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
			</select>
			<select name="parentTypeId" id="parentTypeId">
				<option value="0">大类</option>
				<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="margin-right: 15px;">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>

			状态：
			<select name="shelves" id="shelves" style="margin-right: 15px;">
				<option value="">上/下架</option>
				<option value="1" <c:if test="${vo.shelves==1}">selected</c:if>>上架</option>
				<option value="0" <c:if test="${vo.shelves==0}">selected</c:if>>下架</option>
			</select>

			<select name="invalid" style="margin-right: 15px;">
				<option value="">是否作废</option>
				<option value="1" <c:if test="${vo.invalid==1}">selected</c:if>>已作废</option>
				<option value="0" <c:if test="${vo.invalid==0}">selected</c:if>>未作废</option>
			</select>

			商品品牌：
			<select name="brandId" id="brandId" style="margin-right: 15px;">
				<option value="">选择商品品牌</option>
				<c:forEach items="${brandList}" var="list">
					<option value="${list.id }" <c:if test="${vo.brandId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>

			商品活动：
			<select name="activityId" id="activityId" style="margin-right: 15px;">
				<option value="">选择商品活动</option>
				<c:forEach items="${activityList}" var="list">
					<option value="${list.id }" <c:if test="${vo.activityId==list.id}">selected</c:if>>${list.activityName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="filter" style="float: left;">
			供应商:
			<input list="supplyChain" name="supplyChain" class="inpMain" placeholder="供应商" value="${vo.supplyChain}">
			<datalist id="supplyChain" class="selectBox"  >
				<c:forEach items="${supplyChainList}" var="supplyChain">
					<option value="${supplyChain.name }" ></option>
				</c:forEach>
			</datalist>

			商品搜索：<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"
				value="${vo.commodityNo }" />
			<input class="btn" type="submit" value="搜索" />
			<input type="file" id="modifiedFile" accept=".xlsx" />
			<input class="btnGray" type="button" id="modifiedFileBtn" value="上传并调整" title="上传并调整" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>供应城市</th>
					<th>供应商</th>
					<th>属性</th>
					<th>大类</th>
					<th>小类</th>
					<th>商品名</th>
					<th>规格</th>
					<th>成本价</th>
					<th>售价</th>
					<th>参考价</th>
					<th>活动价</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityList}">
						<c:forEach items="${commodityList}" var="list" varStatus="vs">
						<tr>
							<td>${vs.index + 1}</td>
							<td>
								<c:forEach items="${cityList}" var="city">
									<c:if test="${city.code == list.areaCode}">
										${city.name}
									</c:if>
								</c:forEach>
							</td>
							<td title="${list.supplyNo }">${list.supplyName }</td>
							<td>${list.commodityAttr }</td>
							<td>${list.parentTypeName }</td>
							<td>${list.typeName }</td>
							<td>${list.commodityName }</td>
							<td>${list.weight }</td>
							<td>￥${list.costPrice }</td>
							<td>￥${list.originalPrice }</td>
							<td>￥${list.marketPrice }</td>
							<td>￥${list.promotionPrice }</td>
							<td>
								<a href="/commodityPrice/edit.html?id=${list.id }">修改</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="13">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<input type="button" id="btn_export" class="btn" value="导出价格表" title="导出价格表" />
		</div>
		${vo.page.pageStr }
	</div>
</div>
<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript">
	$(function(){
		$('#modifiedFileBtn').click(function(){
			btn_disable($('#modifiedFileBtn'));
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($('#modifiedFile')[0].files, function(i, file) {
				data.append('fileName', file);
			});

			data.append('update', true);

			// 需要用户输入通行证密码
			var passport = prompt('通行口令', '');
			if (passport) {
				if (confirm('确定可以有这样的操作吗？')) {
					$.ajax({
						url: '/commodityPrice/batchUpdate.html?passport=' + passport,
						type: 'POST',
						data: data,
						cache: false,
						contentType: false, //不可缺
						processData: false, //不可缺
						success: function (resultMap) {
							if (resultMap.result == 'success') {
								$(this).val('成功！');
								alert('数据修改成功！');
							} else if (!resultMap.result) {
//								alert(UNKNOWN_ERROR);
								alert(resultMap.msg);
							} else {
								alert(resultMap.result);
							}
							window.location.reload();
						},
						error: function () {
							alert('请先选择您要上传的文件');
							btn_enable($('#modifiedFileBtn'));
						}
					});
				}else {
					window.location.reload();
				}
			} else {
				alert('请先进行身份验证！');
				window.location.reload();
			}
		});

		// 将筛选条件存入cookie，以便编辑后返回之前页面并刷新
		$.cookie('haj_commodity_price_list_params', $('#searchForm').serialize());

		$('#btn_export').click(function(){
			location.href = '/commodityPrice/export.html?'+$('#searchForm').serialize();
			window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
				btn: ['关闭'] //按钮
			});
		});
	});
</script>
</body>
</html>
