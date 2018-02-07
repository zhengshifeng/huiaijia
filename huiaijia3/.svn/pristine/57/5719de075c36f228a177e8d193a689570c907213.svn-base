<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优惠券管理</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/plugins/layer/layer.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<c:if test="${not empty user.areaCode}">
			<a href="/hajCouponInfo/add.html?category=${vo.category}" class="actionBtn add">添加优惠卷</a>
			</c:if>
			<c:if test="${vo.category == 1}">
				商品满减优惠券管理
			</c:if>
			<c:if test="${vo.category == 2}">
				品类品牌优惠券管理
			</c:if>
		</h3>
		<form action="/hajCouponInfo/list.html" method="post" name="userForm" id="userForm">
			<input type="hidden" name="category" value="${vo.category }" />
			<div class="filter" >
				状态
				<select name="status" id="status">
					<option value="" <c:if test="${vo.status==''}">selected</c:if>>不限</option>
					<option value="1" <c:if test="${vo.status==1}">selected</c:if>>未开始</option>
					<option value="2" <c:if test="${vo.status==2}">selected</c:if>>进行中</option>
					<option value="3" <c:if test="${vo.status==3}">selected</c:if>>已结束</option>
				</select>

				优惠卷关键字：
				<input type="text" placeholder="优惠卷关键字" class="inpMain" name="name" value="${vo.name }" />
				<input class="btn" type="button" value="搜索" onclick="search();" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>ID</th>
						<th>名称</th>
						<th>优惠条件</th>
						<th>优惠金额</th>
						<th>发放数量</th>
						<th>领取张数</th>
						<th>已使用张数</th>
					 	<th>投入产出比</th> 
						<th>开始时间</th>
						<th>结束时间</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty hajCouponInfoList}">
							<c:forEach items="${hajCouponInfoList}" var="coupon" varStatus="vs">
								<tr class="main_info">
									<td>
										${coupon.id}
									</td>
									<td>${coupon.name }</td>
									<td>${coupon.rule }</td>
									<td>${coupon.couponMoney }</td>
									<td>${coupon.number }</td>
									<td>${coupon.receiveQuantity }</td>
									<td>${coupon.usedQuantity }</td>
									<td>
										<div id="rate_${coupon.id}"><a href="javascript:selectRate('${coupon.id}');">查看</a></div></td>
									<td>
										<fmt:formatDate value="${coupon.beginTime }" pattern="yyyy-MM-dd" />
									</td>
									<td>
										<fmt:formatDate value="${coupon.endTime }" pattern="yyyy-MM-dd" />
									</td>
									<td>
										<c:if test="${coupon.status=='1'}">未开始</c:if>
										<c:if test="${coupon.status=='2'}"><span class="text_green">进行中</span> </c:if>
										<c:if test="${coupon.status=='3'}">已结束</c:if>
									</td>
									
									<td>
										<c:if test="${not empty user.areaCode}">
											<a class="load" href="/couponResidential/list.html?couponId=${coupon.id}">小区</a>|

											<c:if test="${vo.category == 1}">
											<a class="load" href="/couponCommodity/list.html?couponId=${coupon.id}">商品</a>|
											</c:if>
											<c:if test="${vo.category == 2}">
											<a class="load" href="/couponType/listWithTreeNodes.html?couponId=${coupon.id}">商品</a>|
											</c:if>

											<c:if test="${coupon.sendType == 0}">
											<a href="/hajCouponInfo/issueCoupon.html?couponId=${coupon.id}&category=${vo.category}" class="text_blue">待发放</a>|
											</c:if>
										</c:if>
										<c:if test="${coupon.sendType > 0}">
											<a href="/hajCouponInfo/issueDetail.html?couponId=${coupon.id}">已发放</a>|
										</c:if>
										<a href="/hajCouponInfo/edit.html?id=${coupon.id}">修改</a>|
										<a href="/hajCouponInfo/useInfo.html?id=${coupon.id}">使用详情</a>|
										<c:if test="${coupon.status == 2}">
											<a href="javascript:;" onclick="updateStatus('${coupon.id}', '${coupon.name}',3)">结束</a>
										</c:if>
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
	<script type="text/javascript">
		function search() {
			$("#userForm").submit();
		}
		
		function selectRate(couponId) {
			var url ='/hajCouponInfo/selectRate.html?couponId='+couponId;
			$("#rate_"+couponId).html('请稍候...');
			$.get(url, function(data) {
				$("#rate_"+couponId).html(data.result+'%');
			});
		}

		function updateStatus(id, name, status) {
			if (confirm('优惠券结束后将无法重新开启，且所有已领取的优惠券都将失效，确定要这样做吗？')) {
				var url = '/hajCouponInfo/updateStatus.html';
				var data = 'id='+id+'&name='+name+'&status='+status;
				$.post(url, data, function(data) {
					if (data.result == 'success') {
						alert('操作成功');
						window.location.reload();
					} else {
						alert(data.result);
					}
				});
			}
		}
	</script>
</body>
</html>
