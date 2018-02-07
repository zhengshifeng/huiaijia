<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品分类</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/commodityType/add.html" class="actionBtn add">添加分类</a>
			<a href="/category/list.html" class="actionBtn">商品类目管理</a>
			商品分类
		</h3>
		<div class="navList">
			<ul class="tab">
				<li><a href="/commodityType/list.html?attribute=default" <c:if test="${'default' == attribute}">class="selected"</c:if>>生鲜</a></li>
				<li><a href="/commodityType/list.html?attribute=fruits" <c:if test="${'fruits' == attribute}">class="selected"</c:if>>水果</a></li>
				<li><a href="/commodityType/list.html?attribute=group" <c:if test="${'group' == attribute}">class="selected"</c:if>>团购</a></li>
				<li><a href="/commodityType/list.html?attribute=breakfast" <c:if test="${'breakfast' == attribute}">class="selected"</c:if>>早餐</a></li>
			</ul>
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th style="width: 100px;">分类ID</th>
					<th>所属类目</th>
					<th>分类名称</th>
					<th>分类别名</th>
					<th>排序值</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityTypeList}">
						<c:forEach items="${commodityTypeList}" var="list" varStatus="vs">
							<tr style="font-weight: bolder; font-size: larger;">
								<td>${list.id}</td>
								<td>${list.remark1}</td>
								<td <c:if test="${list.display==0}">class="text_invalid text_red"</c:if>>${list.typeName }</td>
								<td>${list.description }</td>
								<td>${list.sort }</td>
								<td>
									<a href="/commodityType/edit.html?id=${list.id }&attribute=${attribute}" >修改</a>
								</td>
							</tr>
							<c:if test="${not empty list.subList}">
								<c:forEach items="${list.subList}" var="sub" varStatus="vs_sub">
									<tr>
										<td>├───${sub.id}</td>
										<!-- 直接显示父类的类目 -->
										<td>├───${list.remark1}</td>
										<td <c:if test="${sub.display==0}">class="text_invalid text_red"</c:if>>├───${sub.typeName }</td>
										<td>├───${sub.description}</td>
										<td>├───${sub.sort }</td>
										<td>
											├───<a href="/commodityType/edit.html?id=${sub.id }&attribute=${attribute}">修改</a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
</body>
</html>
