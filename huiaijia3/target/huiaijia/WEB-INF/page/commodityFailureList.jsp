<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区商品管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>小区【${communityName }】商品销售管理</h3>
		<form action="/commodityFailure/list.html" method="post" name="searchForm" id="searchForm">
			<input type="hidden" name="areasId" value="${vo.areasId }" />
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
				
				<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"
					value="${vo.commodityNo }" />
				<input class="btn" type="submit" value="搜索" />
				<b class="tip" style="font-size: larger; color: red;"></b>
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th style="width: 50px;">序号</th>
						<th>商品编号</th>
						<th>属性</th>
						<th>大类</th>
						<th>小类</th>
						<th>商品名</th>
					</tr>
					<c:choose>
						<c:when test="${not empty commodityList}">
							<c:forEach items="${commodityList}" var="list" varStatus="vs">
								<tr class="main_info <c:if test="${list.failureCommodityId gt 0 }">text_red text_invalid</c:if>">
									<td>
										<label><input type="checkbox" name="commodityId" value="${list.id }" />${vs.index+1 }</label>
									</td>
									<td>${list.commodityNo }</td>
									<td>${list.commodityAttr }</td>
									<td>${list.parentTypeName }</td>
									<td>${list.typeName }</td>
									<td>${list.commodityName }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="5">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="action">
					<input type="button" id="btn_enable" class="btn" title="启用选中商品" value="启用" />
					<input type="button" id="btn_disenable" class="btn" title="禁用选中商品" value="禁用">
					<div class="page_and_btn">${vo.page.pageStr }</div>
				</div>
				<input type="hidden" name="communityId" value="${vo.areasId }" />
			</form>
		</div>
	</div>

	<script type="text/javascript">
		function search() {
			$("#searchForm").submit();
		}
		
		
		//====================================
		// 文字闪烁效果
		//====================================
		var colorArr = new Array("white", "red");
		var i = 0, times = 0;
		function changeTextColor() {
			$('.tip').css('color', colorArr[i++]);
			i = (i > 1) ? 0 : i;
		}
		
		//====================================
		// 启用/禁用 选中的商品开关，通用方法
		//====================================
		function toggleEnable(action) {
			var confirmInfo = ("enable" == action) ? "点击确定，则将选中的商品重新向该小区开放" : "点击确定，则该小区中的用户将无法购买选中的商品";
			var actionName = ("enable" == action) ? "enable.html" : "disenable.html";
			
			var len = $("input[name='commodityId']:checked").length;
			if (len > 0) {
				if (confirm(confirmInfo)) {
					$('.tip').html('正在处理...');
					
					setInterval("changeTextColor()", 500);
					
					var url = '${pageContext.request.contextPath }/commodityFailure/'+actionName;
					$.post(url, $('#deleteForm').serialize(), function(data) {
						if (data == 'success') {
							window.location.reload();
						} else {
							alert(UNKNOWN_ERROR);
						}
					});
				}
			} else {
				alert('请选择商品');
			}

			$('.tip').html();
		}

		$(function() {
			//====================================
			// 启用选中的商品
			//====================================
			$('#btn_enable').click(function() {
				toggleEnable("enable");
			});

			//====================================
			// 禁用选中的商品
			//====================================
			$('#btn_disenable').click(function() {
				toggleEnable("disenable");
			});

		});
	</script>
</body>
</html>
