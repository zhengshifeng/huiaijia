<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/hajbackActivity/toAdd.html" class="actionBtn add">添加活动</a>
			活动管理
		</h3>
		<form action="getAll.html" method="post" name="searchForm" id="searchForm">
			<div class="filter">

				活动类型:
				<select name="activityType" id="activityType">
					<option value="">活动类型</option>
					<option value="0" <c:if test="${activity.activityType==0}">selected</c:if>>今日特价</option>
					<option value="1" <c:if test="${activity.activityType==1}">selected</c:if>>1元购</option>
					<option value="2" <c:if test="${activity.activityType==2}">selected</c:if>>首页轮播图</option>
					<option value="3" <c:if test="${activity.activityType==3}">selected</c:if>>新品推荐</option>
					<option value="4" <c:if test="${activity.activityType==4}">selected</c:if>>爆品热卖</option>
					<option value="5" <c:if test="${activity.activityType==5}">selected</c:if>>其他</option>
				</select>

				<input class="inpMain" placeholder="请输入活动名称" type="text" name="activityName" value="${activity.activityName }" />
				活动时间：
				<input class="inpMain short date_picker" type="text" name="beginTime" value="${activity.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
				-
				<input class="inpMain short date_picker" type="text" name="endTime" value="${activity.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" />
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th>活动ID</th>
						<th>活动优惠金额</th>
						<th>活动名称</th>
						<th>活动开始时间</th>
						<th>活动结束时间</th>
						<th>活动城市</th>
						<th>状态</th>
						<th>活动限制数量</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty activityList}">
							<c:forEach items="${activityList}" var="activity" varStatus="vs">
								<tr class="main_info">
									<td>${activity.id }</td>
									<td>
										<c:if test="${empty activity.DiscountAmount}">不限</c:if>
										<c:if test="${!empty activity.DiscountAmount}">${activity.DiscountAmount }</c:if>
									</td>
									<td>${activity.activityName }</td>
									<td>${activity.beginTime }</td>
									<td>${activity.endTime }</td>
									<td>
										<c:forEach items="${cityList}" var="city">
											<c:if test="${city.code == activity.areaCode}">
												${city.name}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:if test="${activity.status=='1'}">进行中</c:if>
										<c:if test="${activity.status=='0'}">未进行</c:if>
									</td>
									<td>
										<c:if test="${empty activity.number}">不限</c:if>
										<c:if test="${!empty activity.number}">${activity.number }</c:if>
									</td>
									<td>
										<a href="javascript:activityDetail(${activity.id});">查看详情 | </a>
										<a href="javascript:updateActivity(${activity.id});">编辑 | </a>
										<c:if test="${activity.status=='1'}">
											<a class="text_red" href="javascript:updateActivityType('${activity.id}',0);">结束此活动</a>
										</c:if>
										<c:if test="${activity.status=='0'}">
											<a class="text_green" href="javascript:updateActivityType('${activity.id}',1);">开始此活动</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="10">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">${activity.page.pageStr }</div>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='orderIds']").prop("checked", true);
			} else {
				$("input[name='orderIds']").prop("checked", false);
			}
		}

		function activityDetail(activityId) {
			location.href = "getDetail.html?activityId=" + activityId;
		}

		function updateActivityType(activityId, status) {
			var confirmInfo = '确定要进行此次活动吗？';
			if (status == 0) {
				confirmInfo = '确定要结束此次活动吗？';
			}
			if (confirm(confirmInfo)) {
				var url = "updateActivity.html?activityId=" + activityId + "&status=" + status;
				$.get(url, function(data) {
					if (data == "1") {
						alert('更新成功！');
						document.location.reload();
					}
				});
			}
		}

		function updateActivity(activityId) {
			location = "toEdit.html?activityId=" + activityId;
		}

		function deleteActivity(activityId) {
			location = "deleteActivity.html?activityId=" + activityId;
		}
	</script>
</body>
</html>
