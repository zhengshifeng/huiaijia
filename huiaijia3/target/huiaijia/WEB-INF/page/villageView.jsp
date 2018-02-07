<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/communityBuilding/list.html?communityId=${village.id}" class="actionBtn" style="margin-left: 7px;">小区楼栋管理</a>
			<a href="/communityUnit/list.html?communityId=${village.id}" class="actionBtn" style="margin-left: 7px;">小区单元地址管理</a>
			<a href="javascript:history.back();" class="actionBtn">小区列表</a>
			小区详情
		</h3>
		<div class="filter" style="height: 270px;">
			<table style="width: 100%;  " class="tableBasic">
				<tr class="info">
					<th>小区ID:</th>
					<td>${village.id}</td>
					<th>小区状态:</th>
					<td>
						<c:if test="${village.status eq '0'}">未激活</c:if>
						<c:if test="${village.status eq '1'}">已激活</c:if>
					</td>
					<th>激活时间:</th>
					<td>${village.activationTime}</td>
					<th>小区名称:</th>
					<td>${village.communityName}</td>
					<th>小区地址:</th>
					<td>${village.address}</td>
				</tr>
				<tr class="info">
					<th>录入时间:</th>
					<td>${village.createTime}</td>
					<th>小区等级:</th>
					<td>
						<c:if test="${village.level eq '1'}">低档</c:if>
						<c:if test="${village.level eq '2'}">中档</c:if>
						<c:if test="${village.level eq '3'}">高档</c:if>
					</td>
					<th>计划会员人数:</th>
					<td>${village.planMemberNumber}</td>
					<th>注册人数:</th>
					<td>${village.registererNumber}</td>
					<th>会员人数:</th>
					<td>${village.membersNumber}</td>
				</tr>
				<tr class="info">
					<th>预约人数:</th>
					<td>${village.appointmentNum }</td>
					<th>小区对接人:</th>
					<td>${village.cellAccess}</td>
					<th>联系方式:</th>
					<td>${village.pickUpContact}</td>
					<th>汇爱家负责人:</th>
					<td>${village.hajCellAccess}</td>
					<th>联系方式:</th>
					<td>${village.hajPickUpContact}</td>
				</tr>
				<tr class="info">
					<th>会员人数是否已满:</th>
					<td>
						<c:if test="${village.memberStatus == 0 }">未满</c:if>
						<c:if test="${village.memberStatus == 1 }">已满</c:if>
					</td>
					<th>所属区域:</th>
					<td>${areaByCode3.name }-${areaByCode2.name }-${areaByCode1.name }</td>
					<th>是否收取运费:</th>
					<td>
						<c:if test="${empty village.needPostFee}">未知</c:if>
						<c:if test="${village.needPostFee == 0 }">否</c:if>
						<c:if test="${village.needPostFee == 1 }">是</c:if>
					</td>
					<th>所属仓库:</th>
					<td>${village.whname}</td>
					<th>微信号:</th>
					<td>${village.wxcode}</td>
				</tr>

				<tr class="info">
					<th>小区规范:</th>
					<td colspan="9" style="max-width: 250px; overflow: hidden;
							text-overflow: ellipsis; white-space: nowrap;" title="${village.addressSpecification }">
						${village.addressSpecification }</td>
				</tr>

				<tr class="info">
					<th>填写须知:</th>
					<td colspan="9" style="max-width: 250px; overflow: hidden; text-overflow: ellipsis;
							white-space: nowrap;" title="${village.completeNotes }">
						${village.completeNotes}
					</td>
				</tr>
			</table>
		</div>

		<div class="filter" style="width: 50%; float: left;">
			<input type="hidden" name="residential" id="residential" value="${village.communityName}" />
			<table style="width: 100%;" class="tableBasic" style="width: 50%; margin: auto;">
				<tr class="info">
					<td rowspan="4" width="120px">
						<img src="${village.photoPath}" width="122px" height="188px">
					</td>
					<th width="90px">配送员</th>
					<td>${village.name}</td>
				</tr>
				<tr class="info">
					<th>配送准点率</th>
					<td>99%</td>
				</tr>
				<tr class="info">
					<th>收到配送小费</th>
					<td>
						<c:if test="${village.tip == null}">0</c:if>
						<c:if test="${village.tip != null}">${village.tip}</c:if>
						元
					</td>
				</tr>
				<tr class="info">
					<th>联系方式</th>
					<td>${village.telphone}</td>
				</tr>
			</table>
			<br>
			<br>
			<table class="tableBasic" style="width: 100%; margin: auto;">
				<tr class="info">
					<td rowspan="4" width="120px">
						<img src="${village.afterSalePhoto}" width="122px" height="198px">
					</td>
					<th width="90px">售后员</th>
					<td>${village.orderSales}</td>
				</tr>
				<tr class="info">
					<th>联系方式</th>
					<td>${village.orderSalesPhone}</td>
				</tr>
				<tr class="info">
				</tr>
				<tr class="info">
				</tr>
			</table>
			<br>
			<br>
			<table class="tableBasic" style="width: 100%;  margin: auto;">
				<tr class="info">
					<th>日期范围</th>
					<td>
						<input type="text" class="inpMain short date_picker" name="beginTime" id="beginTime" onclick="WdatePicker()"
							readonly="readonly" value="" />
					</td>
					<td>至</td>
					<td>
						<input type="text" class="inpMain short date_picker" name="overTime" id="overTime" onclick="WdatePicker()"
							readonly="readonly" value="" />
					</td>
					<td>
						<input name="search" class="btn" class="btn" type="button" id="search" value="搜索" />
					</td>
				</tr>
				<tr class="info">
					<th>订单数（笔）</th>
					<td colspan="5" id="orderNum"></td>
				</tr>
				<tr class="info">
					<th>平均订单金额（元）</th>
					<td colspan="5" id="avgOrderMoney"></td>
				</tr>
				<tr class="info">
					<th>订单合计金额（元）</th>
					<td colspan="5" id="OrderTotalMoney"></td>
				</tr>
			</table>
			<br>
		</div>

		<div style="width: 50%; height: 500px; float: right;">
			<iframe name="comment" id="comment" src="afterSaleComment.html?communityId=${village.id }&page=1"
				style="width: 100%; height: 100%; border: 0;"></iframe>
		</div>

	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#search").click(function() {
				var beginTime = $("#beginTime").val();
				var overTime = $("#overTime").val();
				var villageId = '${village.id}';
				var reg = /^\d{4}-\d{2}-\d{2}$/;
				if (reg.test($.trim(beginTime)) && reg.test($.trim(overTime))) {
					$.ajax({
						type : "GET",
						url : "orderCountForResidential.html",
						contentType : "application/json",
						data : {
							"beginTime" : $.trim(beginTime),
							"overTime" : $.trim(overTime),
							"villageId" : villageId
						},
						dataType : "json",
						success : function(data) {
							$("#orderNum").text(data.orderCount.orderNum);
							if (typeof (data.orderCount.avgMoney) == "undefined") {
								$("#avgOrderMoney").text(0);
							} else {
								$("#avgOrderMoney").text(data.orderCount.avgMoney);
							}
							if (typeof (data.orderCount.sumMoney) == "undefined") {
								$("#OrderTotalMoney").text(0);
							} else {
								$("#OrderTotalMoney").text(data.orderCount.sumMoney);
							}

						}
					});
				} else {
					alert("请选择日期");
				}
			});
		});
	</script>
</body>
</html>
