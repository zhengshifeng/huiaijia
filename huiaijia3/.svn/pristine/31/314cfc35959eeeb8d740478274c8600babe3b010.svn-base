<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>促销专区管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/promotionArea/add.html" class="actionBtn add">添加专区</a>
			促销专区管理
		</h3>
		<!-- 此form用于分页 -->
		<div class="filter">
			<form action="/promotionArea/list.html" method="post" name="searchForm" id="searchForm">
				<strong>所属城市:</strong>
				<select name="code" id="code" style="width: 130px">
					<option value="" style="text-align: center">--请选择--</option>
					<c:forEach items="${cityList}" var="list">
						<option value="${list.code }" <c:if test="${vo.code==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;&nbsp;
				<input class="inpMain" placeholder="专区名称" type="text" name="name" id="name" value="${vo.name}" />
				&nbsp;&nbsp;&nbsp;
				<strong>是否启用:</strong>
				<select name="display" id="display" style="width: 130px">
					<option value="" style="text-align: center">--请选择--</option>
					<option value="1" <c:if test="${vo.display==1}">selected</c:if>>已启用</option>
					<option value="0" <c:if test="${vo.display==0}">selected</c:if>>未启用</option>
				</select>
				&nbsp;&nbsp;&nbsp;
				<strong>专区类型:</strong>
				<select name="areaType" id="areaType" style="width: 130px">
					<option value="" style="text-align: center">--请选择--</option>
					<option value="1" <c:if test="${vo.areaType==1}">selected</c:if>>品牌专区</option>
					<option value="2" <c:if test="${vo.areaType==2}">selected</c:if>>推荐专区</option>
					<option value="3" <c:if test="${vo.areaType==3}">selected</c:if>>商品类目</option>
					<option value="4" <c:if test="${vo.areaType==4}">selected</c:if>>商品分类</option>
				</select>
				&nbsp;&nbsp;&nbsp;
				<input class="btn" type="submit" value="查找" />
			</form>
		</div>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;" class="tableBasic list">
					<tr>
						<th>序号</th>
						<th>ID</th>
						<th>专区名</th>
						<th>专区类型</th>
						<th>所属城市</th>
						<th>是否启用</th>
						<th>排序值</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty promotionAreaList}">
							<c:forEach items="${promotionAreaList}" var="list" varStatus="vs">
								<tr class="main_info" style="text-align:center">
									<td>${vs.index+1}</td>
									<td>${list.id }</td>
									<td>${list.name }</td>
									<td>
										<c:if test="${1 == list.areaType}">
											品牌专区
										</c:if>
										<c:if test="${2 == list.areaType}">
											推荐专区
										</c:if>
										<c:if test="${3 == list.areaType}">
											商品类目
										</c:if>
										<c:if test="${4 == list.areaType}">
											商品分类
										</c:if>
									</td>
									<td>
										<c:forEach items="${cityList}" var="city">
											<c:if test="${city.code == list.areaCode}">
												${city.name}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.display == 1 }">
												<span class="text_green">已启用</span>
											</c:when>
											<c:when test="${list.display == 0 }">
												<span>弃用</span>
											</c:when>
											<c:otherwise>未知</c:otherwise>
										</c:choose>
									</td>
									<td>${list.sort }</td>
									<td>
										<a href="/commodity/list.html?promotionAreaId=${list.id }" style="color: #008ead">查看商品</a>&nbsp;|&nbsp;
										<a href="/promotionArea/edit.html?id=${list.id }" style="color: #0000FF;text-decoration: underline">修改</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="8" style="text-align: center">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">
				<ul>
					<c:if test="${(vo.currentPage-1) == 0}">
						<li class="pageinfo">
							<a href="javascript:search(${vo.currentPage });">第${vo.currentPage }页</a>
						</li>
					</c:if>
					<c:if test="${(vo.currentPage-1) > 0}">
						<li class="pageinfo">
							<a href="javascript:search(${vo.currentPage-1 });">第${vo.currentPage-1 }页</a>
						</li>
					</c:if>
					<li class="current">本页：第${vo.currentPage }页</li>
					<li class="pageinfo">
						<a href="javascript:search(${vo.currentPage+1 });">第${vo.currentPage+1 }页</a>
					</li>
					<li class="pageinfo">
						<input type="text" id="page" style="width: 20px; border: 0px;" />
					</li>
					<li class="pageinfo">
						<a href="javascript:jump();">跳转</a>
					</li>
				</ul>
		</div>
	</div>

	<script type="text/javascript">

		function jump() {
			var jumpPage = $('#page').val();
			if (jumpPage == '') {
				alert("请输入跳转页数!");
				return;
			}
			search(jumpPage);
		}

		function search(page) {
			$("#searchForm").attr("action", "/promotionArea/list.html?currentPage=" + page);
			$("#searchForm").submit();
		}

	</script>
</body>
</html>
