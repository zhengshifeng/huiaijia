<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区订单统计</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>小区订单统计</h3>
		<form action="villageCount.html" method="post" name="listForm" id="listForm">
			<div class="filter">
				<select name="level" id="level" style="vertical-align: middle;">
					<option value="">小区等级</option>
					<option value="1" <c:if test="${criteria.level eq '1'}">selected</c:if>>低档</option>
					<option value="2" <c:if test="${criteria.level eq '2'}">selected</c:if>>中档</option>
					<option value="3" <c:if test="${criteria.level eq '3'}">selected</c:if>>高档</option>
				</select>
				<select name="planMemberNumber" id="planMemberNumber" style="vertical-align: middle;">
					<option value="">会员计划数量</option>
					<c:forEach items="${memberPlanNumList}" var="num">
						<option value="${num }" <c:if test="${criteria.planMemberNumber eq  num }">selected</c:if>>${num  }</option>
					</c:forEach>
				</select>
				<select name="status" id="status" style="vertical-align: middle;">
					<option value="">小区状态</option>
					<option value="1" <c:if test="${criteria.status eq '1'}">selected</c:if>>激活</option>
					<option value="0" <c:if test="${criteria.status eq '0'}">selected</c:if>>未激活</option>
				</select>
				<select name="memberStatus" id="memberStatus" style="vertical-align: middle;">
					<option value="">会员数量</option>
					<option value="0" <c:if test="${criteria.memberStatus eq '0'}">selected</c:if>>未满</option>
					<option value="1" <c:if test="${criteria.memberStatus eq '1'}">selected</c:if>>已满</option>
				</select>
				小区地址
				<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
					<option value="0">不限</option>
					<c:forEach items="${provinceList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.provinceCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="cityCode" id="cityCode" style="vertical-align: middle;">
					<option value="0">不限</option>
					<c:forEach items="${cityList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.cityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="communityCode" id="communityCode" style="vertical-align: middle; margin-right: 15px;">
					<option value="">不限</option>
					<c:forEach items="${communityList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.communityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
			</div>
			<div class="filter">
				<input name="communityName" class="inpMain" type="text" value="${criteria.communityName}" placeholder="小区名称 / 小区ID" />
				<input class="btn" type="button" id="Search" value="搜索" title="搜索" />
			</div>
		</form>

		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;" class="tableBasic list">
					<tr class="main_head">
						<th>小区ID</th>
						<th>小区名称</th>
						<th>历史订单总量</th>
						<th>历史交易总额</th>
						<th>历史盈亏</th>
						<th>最近七天订单量</th>
						<th>最近七天平均交易额</th>
						<th>最近七天盈亏</th>
						<th>日平均订单量</th>
						<th>日平均交易额</th>
						<th>日平均盈亏</th>
						<th>今日订单量</th>
						<th>今日交易额</th>
						<th>今日盈亏</th>
					</tr>
					<c:choose>
						<c:when test="${not empty villageList}">
							<c:forEach items="${villageList}" var="village" varStatus="vs">
								<tr class="main_info">
									<td>${village.id }</td>
									<td>${village.communityName }</td>
									<td>${village.hisOrderNum }</td>
									<td>
										￥
										<c:if test="${village.orderTotalMoney == null }">0</c:if>
										<c:if test="${village.orderTotalMoney >= 0 }">${village.orderTotalMoney }</c:if>
									</td>
									<td>￥${village.profitlossTotalMoney }</td>
									<td>${village.lastSevenDaysOrderNum }</td>
									<td>
										￥
										<c:if test="${village.lastSevenDaysAvg == null }">0</c:if>
										<c:if test="${village.lastSevenDaysAvg >= 0 }">${village.lastSevenDaysAvg }</c:if>
									</td>
									<td>￥${village.profitlossSevenDaysMoney }</td>
									<td>
										<c:if test="${village.dayAvgNum == null }">0</c:if>
										<c:if test="${village.dayAvgNum >= 0 }">${village.dayAvgNum }</c:if>
									</td>
									<td>
										￥
										<c:if test="${village.dayAvgMoney == null }">0</c:if>
										<c:if test="${village.dayAvgMoney >= 0 }">${village.dayAvgMoney }</c:if>
									</td>
									<td>￥${village.dayAvgProfitlossMoney }</td>
									<td>${village.todayOrderNum }</td>
									<td>
										￥
										<c:if test="${village.todayMoney == null }">0</c:if>
										<c:if test="${village.todayMoney >= 0 }">${village.todayMoney }</c:if>
									</td>
									<td>￥${village.todayprofitloss }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="10" align="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="page_and_btn">
			<ul>
				<c:if test="${(criteria.currentPage-1) == 0}">
					<li class="pageinfo">
						<a href="javascript:search(${criteria.currentPage });">第${criteria.currentPage }页</a>
					</li>
				</c:if>
				<c:if test="${(criteria.currentPage-1) > 0}">
					<li class="pageinfo">
						<a href="javascript:search(${criteria.currentPage-1 });">第${criteria.currentPage-1 }页</a>
					</li>
				</c:if>
				<li class="current">本页：第${criteria.currentPage }页</li>
				<li class="pageinfo">
					<a href="javascript:search(${criteria.currentPage+1 });">第${criteria.currentPage+1 }页</a>
				</li>
				<li class="pageinfo">
					<input type="text" id="page" style="width: 20px; border: 0px;" />
				</li>
				<li class="pageinfo">
					<a href="javascript:jump();">跳转</a>
				</li>
			</ul>
		</div>
		
		
		<h3>统计</h3>
		<table style="width: 100%;" class="tableBasic list">
			<tr class="main_head">
				<th>历史订单总量</th>
				<th>历史交易总额</th>
				<th>历史盈亏</th>
				<th>最近七天订单量</th>
				<th>最近七天平均交易额</th>
				<th>最近七天盈亏</th>
				<th>日平均订单量</th>
				<th>日平均交易额</th>
				<th>日平均盈亏</th>
				<th>今日订单量</th>
				<th>今日交易额</th>
				<th>今日盈亏</th>
			</tr>
			<c:choose>
				<c:when test="${not empty villageMap}">
					<tr class="main_info">
						<td>${villageMap.hisOrderNum }</td>
						<td>
							￥
							<c:if test="${villageMap.orderTotalMoney == null }">0</c:if>
							<c:if test="${villageMap.orderTotalMoney >= 0 }">${villageMap.orderTotalMoney }</c:if>
						</td>
						<td>￥${villageMap.profitlossTotalMoney }</td>
						<td>${villageMap.lastSevenDaysOrderNum }</td>
						<td>
							￥
							<c:if test="${villageMap.lastSevenDaysAvg == null }">0</c:if>
							<c:if test="${villageMap.lastSevenDaysAvg >= 0 }">${villageMap.lastSevenDaysAvg }</c:if>
						</td>
						<td>￥${villageMap.profitlossSevenDaysMoney }</td>
						<td>
							${villageMap.dayAvgNum }
						</td>
						<td>
							￥
							${villageMap.dayAvgMoney }
						</td>
						<td>￥${villageMap.dayAvgProfitlossMoney }</td>
						<td>${villageMap.todayOrderNum }</td>
						<td>
							￥
							<c:if test="${villageMap.todayMoney == null }">0</c:if>
							<c:if test="${villageMap.todayMoney >= 0 }">${villageMap.todayMoney }</c:if>
						</td>
						<td>￥${villageMap.todayprofitloss }</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="10" align="center">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>

	<script type="text/javascript">
		function jump() {
			var jumpPage = parseInt($('#page').val());
			if (jumpPage) {
				window.parent.layer.load(2);
				search(jumpPage);
			}
		}

		function search(page) {
			window.parent.layer.load(2);
			$("#listForm").attr("action", "villageCount.html?currentPage=" + page);
			$("#listForm").submit();
		}

		function btnTimeOut() {
			$('#Search').removeAttr('disabled');
		}

		$(function () {
			$('#Search').attr('disabled', 'disabled');
			$('#Search').click(function () {
				btn_disable($(this))
				$('#listForm').submit();
			});
			setTimeout('btnTimeOut()', 5000);

			window.parent.layer.closeAll('loading');
		});
	</script>
</body>
</html>
