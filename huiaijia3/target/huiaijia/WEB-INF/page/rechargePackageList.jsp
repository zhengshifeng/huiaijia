<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值套餐管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
 	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>

</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/rechargePackage/add.html" class="actionBtn add" style="margin-left: 10px;">创建套餐</a>
 		充值套餐管理
	</h3>
	<form action="/rechargePackage/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			请选择：
			<input type="text" class="inpMain" placeholder="名称/ID" name="name" id="name"
				   value="${hajRechargePackage.name}" />

			创建日期：
			<input class="inpMain short date_picker" type="text" name="beginTime" value="${hajRechargePackage.beginTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			-
			<input class="inpMain short date_picker" type="text" name="endTime" value="${hajRechargePackage.endTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
			是否生效：
			<select name="status" id="status" style="vertical-align: middle; margin-right: 15px;">
				<option value="">不限</option>
				<option value="0" <c:if test="${hajRechargePackage.status==0}">selected</c:if>>生效</option>
				<option value="1" <c:if test="${hajRechargePackage.status==1}">selected</c:if>>作废</option>
			</select>

			<input class="btn" type="submit" value="搜索" />
		</div>

	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>购买金额</th>
					<th>赠送金额</th>
					<th>到账金额</th>
					<th>创建时间</th>
					<th>是否生效</th>
					<th>购物份数</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty rechargePackageList}">
						<c:forEach items="${rechargePackageList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>
									${list.id}
							</td>
							<td>${list.name}</td>
							<td>${list.purchaseAmount}</td>
							<td>${list.donationAmount }</td>
							<td>${list.accountAmount }</td>
							<td>
								<fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.status == 1 }"><span class="text_invalid">作废</span></c:when>
									<c:when test="${list.status == 0 }"><span class="text_green">生效</span></c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>${list.totalNumber}</td>
							<td>
								<a href="/rechargePackage/detail.html?id=${list.id }">查看详情</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="14">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		${hajRechargePackage.page.pageStr}
	</div>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript">
</script>
</body>
</html>