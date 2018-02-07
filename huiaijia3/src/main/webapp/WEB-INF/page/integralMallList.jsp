<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>积分商城</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/layer/layer.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			积分商城列表
			<a href="/integralMall/add.html" class="actionBtn add">新建兑换商品</a>
		</h3>
		<form action="/integralMall/list.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				兑换商品名称：
				<input type="text" placeholder="兑换商品名称" class="inpMain" name="commodityName" value="${vo.commodityName }" />
				
				排序方式：
				<select name="sort" id="sort">
					<option value="0" <c:if test="${vo.sort=='0'}">selected</c:if>>创建时间从早到晚</option>
					<option value="1" <c:if test="${vo.sort=='1'}">selected</c:if>>创建时间从晚到早</option>
					<option value="2" <c:if test="${vo.sort=='2'}">selected</c:if>>兑换数从多到少</option>
					<option value="3" <c:if test="${vo.sort=='3'}">selected</c:if>>兑换数从少到多</option>
				</select>

				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>ID</th>
						<th>商品类型</th>
						<th>商品名称</th>
						<th>所属城市</th>
						<th>发行量</th>
						<th>兑换数</th>
						<th>结存量</th>
					 	<th>积分</th> 
						<th>是否隐藏</th>
						<th>排序值</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty integralMallList}">
							<c:forEach items="${integralMallList}" var="list" varStatus="vs">
								<tr class="main_info">
									<td>${list.id}</td>
									<td>
										<c:if test="${list.commodityType=='1'}">优惠卷</c:if>
										<c:if test="${list.commodityType=='2'}">商品</c:if>
									</td>
									<td>${list.commodityName }</td>
									<td>
										<c:if test="${list.areaCode=='100002'}">深圳市</c:if>
										<c:if test="${list.areaCode=='100010'}">广州市</c:if>
									</td>
									<td>${list.total }</td>
									<td>${list.totalOfExchanged }</td>
									<td>${list.total - list.totalOfExchanged}</td>
									<td>${list.integral }</td>
									<td>
										<c:if test="${list.hide=='1'}">隐藏</c:if>
										<c:if test="${list.hide=='0'}"><span class="text_green">不隐藏</span></c:if>
									</td>
									<td>${list.sort }</td>
									<td>
										<fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>
										<a href="/integralMall/edit.html?id=${list.id}">修改</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="12">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">
			${vo.page.pageStr }
		</div>
	</div>
</body>
</html>
