<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品分拣小组</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/hajCommunitySorter/add.html" class="actionBtn add" style="margin-left: 7px;">添加分拣小组</a>
		<a href="/warehouse/addWarehouse.html" class="actionBtn add">添加仓库</a>
		商品分拣小组
	</h3>
	<form action="/community/communitySorterList.html" method="post" name="listForm" id="listForm">
		<div class="filter">
			省市区：
			<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
				<option value="0">省</option>
				<c:forEach items="${provinceList}" var="list">
					<option value="${list.code }" <c:if test="${vo.provinceCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select name="cityCode" id="cityCode" style="vertical-align: middle;">
				<option value="0">市</option>
				<c:forEach items="${cityList}" var="list">
					<option value="${list.code }" <c:if test="${vo.cityCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select name="communityCode" id="communityCode" style="vertical-align: middle; margin-right: 10px;">
				<option value="0">区/县</option>
				<c:forEach items="${communityList}" var="list">
					<option value="${list.code }" <c:if test="${vo.communityCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			排序条件：
			<select name="orderByClause" id="orderByClause" style="vertical-align: middle; margin-right: 10px;">
				<option value="">默认</option>
				<option value="commonVipNum" <c:if test="${vo.orderByClause eq 'commonVipNum'}">selected</c:if>>普通会员人数从多到少</option>
				<option value="vipNum" <c:if test="${vo.orderByClause eq 'vipNum'}">selected</c:if>>一元购会员人数从多到少</option>
			</select>

			分拣小组：
			<select name="sorterId" id="sorterId" style="vertical-align: middle; margin-right: 10px;">
				<option value="">选择分拣小组</option>
				<c:forEach items="${sorterList}" var="list">
					<option value="${list.id }" <c:if test="${vo.sorterId==list.id}">selected</c:if>>${list.sorterName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="filter">
			小区名：
			<input name="communityName" class="inpMain" type="text" value="${vo.communityName }" placeholder="小区名称 / 小区ID"/>
			<input class="btn" type="submit" value="搜索" />
		</div>
	</form>

	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th style="width: 15px;"><input type="checkbox" id="ckb_all" /></th>
					<th>小区ID</th>
					<th>省份</th>
					<th>城市</th>
					<th>区域</th>
					<th>小区名称</th>
					<th>小区地址</th>
					<th>普通会员人数</th>
					<th>一元购会员人数</th>
					<th>分拣小组</th>
					<th>仓库归属</th>
					<th>楼栋排序</th>
				</tr>
				<c:choose>
					<c:when test="${not empty communitySorterList}">
						<c:forEach items="${communitySorterList}" var="list" varStatus="vs">
							<tr>
								<td><input type="checkbox" name="communityIds" value="${list.id }" /></td>
								<td>${list.id }</td>
								<td>${list.province }</td>
								<td>${list.city }</td>
								<td>${list.district }</td>
								<td>${list.communityName }</td>
								<td>${list.address }</td>
								<td>${list.commonVipNum }</td>
								<td>${list.vipNum }</td>
								<td>${list.sorterName }</td>
								<td>${list.whname }</td>
								<td><a href="/communityUnit/edit.html?communityId=${list.id }">修改</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="10" align="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="page_and_btn">
		<div class="action">
			分拣小组：
			<select id="slt_add2sorter" style="vertical-align: middle;">
				<option value="">选择分拣小组</option>
				<c:forEach items="${sorterList}" var="list">
					<option value="${list.id }" <c:if test="${vo.sorterId==list.id}">selected</c:if>>${list.sorterName }</option>
				</c:forEach>
			</select>
			<a class="btn" id="btn_add2sorter" href="javascript:;">添加到分拣小组</a>
			<a class="btn" id="btn_removeSorterId" href="javascript:;">从分拣小组中移除</a>
		</div>
		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript">
	/* 批量添加小区到指定的分拣小组 */
	$('#btn_add2sorter').click(function() {
		var sorterId = $('#slt_add2sorter').val();
		var communityId = $('#deleteForm').serialize();

		var len = $("input[name='communityIds']:checked").length;
		if (len > 0) {
			if (sorterId && confirm('不是手滑？')) {
				var url = '${pageContext.request.contextPath }/community/add2Sorter.html?';
				var data = communityId + '&sorterId=' + sorterId;
				url = url + data;

				$.get(url, function(result) {
					if (result.error == 'success') {
						alert('添加成功！');
						$('#listForm').submit();
					} else {
						alert('未知错误');
					}
				});
			}
		} else {
			alert('请先选择小区！');
		}
	});


	/* 将指定的小区从分拣小组中移除 */
	$('#btn_removeSorterId').click(function() {
		var communityId = $('#deleteForm').serialize();
		var len = $("input[name='communityIds']:checked").length;
		if (len > 0) {
			if (communityId && confirm('不是手滑？')) {
				var url = '${pageContext.request.contextPath }/community/removeSorterId.html?';
				url = url + communityId;

				$.get(url, function(result) {
					if (result.error == 'success') {
						alert('移除成功！');
						$('#listForm').submit();
					} else {
						alert('未知错误');
					}
				});
			}
		} else {
			alert('请先选择小区！');
		}
	});
</script>
</body>
</html>
