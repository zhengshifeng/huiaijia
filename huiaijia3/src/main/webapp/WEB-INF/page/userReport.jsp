<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户上报</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>用户上报</h3>
		<form name="searchForm" id="searchForm" action="userReport.html" method="post">
			<div class="filter">
				区域：
				<input type="text" class="inpMain" name="area" id="area" placeholder="格式如：广东省深圳市福田区" value="${criteria.area }" />
				小区：
				<input type="text" class="inpMain" name="communityName" id="communityName" placeholder="小区名称" value="${criteria.communityName }" />
				
				<select name="pushStatus" id="pushStatus" style="vertical-align: middle;">
					<option value="">推送状态</option>
					<option value="1" <c:if test="${criteria.pushStatus eq '1'}">selected</c:if>>已推送</option>
					<option value="0" <c:if test="${criteria.pushStatus eq '0'}">selected</c:if>>未推送</option>
				</select>

				<input class="btn" type="submit" onclick="search()" value="搜索" />
			</div>
		</form>
		<form name="pushForm" id="pushForm">
			<div id="list">
				<table style="width: 100%;  " class="tableBasic list">
					<tr class="main_head" align="center">
						<th style="width: 7px;">
							<input type="checkbox" id="sltAll" onclick="selectAll()" />
						</th>
						<th>序号</th>
						<th>用户名</th>
						<th>手机号码</th>
						<th>区域</th>
						<th>小区名字</th>
						<th>上报时间</th>
						<th>推送状态</th>
					</tr>
					<c:choose>
						<c:when test="${not empty listPageReport}">
							<c:forEach items="${listPageReport}" var="report" varStatus="vs">
								<tr class="main_info">
									<td>
										<input type="checkbox" name="phoneList" id="id_${report.id }" value="${report.mobilePhone }" />
									</td>
									<td>${report.id }</td>
									<td>${report.username }</td>
									<td>${report.mobilePhone }</td>
									<td>${report.area }</td>
									<td>${report.villageCode }</td>
									<td>${report.reportTime }</td>
									<td>
										<c:if test="${report.pushStatus ==0 }">未推送</c:if>
										<c:if test="${report.pushStatus ==1 }">已推送</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="8" align="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<div class="clear"></div>
			<div class="page_and_btn">
				<div>
					<input type="text" class="inpMain" name="village" id="village" placeholder="小区名字"/>
					<a id="btn_push" class="btn">
						<em>批量推送</em>
					</a>
					<a id="btn_excel" class="btn">
						<em>导出excel</em>
					</a>
				</div>
				${criteria.page.pageStr }
			</div>
		</form>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			// 批量推送
			$('#btn_push').click(function() {
				if ($('#village').val() == "") {
					alert("请输入推送小区名称!");
					return;
				}
				var len = $("input[name='phoneList']:checked").length;
				if (len == 0) {
					alert("请勾选至少一条信息!");
					return;
				}
				if (len > 200) {
					alert("批量所选条数不能大于200！");
				} else {
					if (confirm('确定全部推送吗？')) {
						var url = '/hajSMSPush/userReportSMSPush.html';
						$.post(url, $('#pushForm').serialize(), function(data) {
							if (data.status == 'true') {
								alert("批量推送成功!");
								window.location.reload();
								$("input[name='phoneList']").prop("checked", false);
							} else {
								alert("未知异常!");
							}

						});
					}
				}
			});
		});

		function search() {
			$("#searchForm").submit();
		}

		// 选中所有选择框
		function selectAll() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='phoneList']").prop("checked", true);
			} else {
				$("input[name='phoneList']").prop("checked", false);
			}
		}
		
		$(function(){
			var ids = ''; 
			// 批量导出
			$('#btn_excel').click(function(){
				var area = $("#area").val();
				var communityName = $("#communityName").val();
				var pushStatus = $("#pushStatus").val();

				document.location = "exportUserReport.html?area="+area+"&communityName="+communityName+"&pushStatus="+pushStatus;
				
			});
		});
	</script>
</body>
</html>
