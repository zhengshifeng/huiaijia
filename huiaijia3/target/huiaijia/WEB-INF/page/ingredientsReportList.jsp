<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>食材检测报告</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">

	<h3>
		<a href="/ingredientsReport/toEdit.html?reportId=0" class="actionBtn add">添加检测报告</a>食材检测报告
	</h3>
	<form action="list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">

			<strong >日期:</strong>
			<input class="btn"  type="button" id="today" value="今日" onclick="getDate()" style="background-color: #aaaaaa"/>

			<input class="inpMain short date_picker" placeholder="年/月/日" type="text" id="beginTime" name="beginTime"
				   value="${report.beginTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly'})" readonly="readonly" />
			至
			<input class="inpMain short date_picker" placeholder="年/月/日" type="text" id="endTime" name="endTime"
				   value="${report.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly'})" readonly="readonly" />
			<input class="btn" type="submit" value="搜索" />

		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm" style="text-align:center">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>日期</th>
					<th>所属城市</th>
					<th>是否显示</th>
					<th>编辑</th>
				</tr>
				<c:choose>
					<c:when test="${not empty reportList}">
						<c:forEach items="${reportList}" var="report" varStatus="vs">
							<tr class="main_info">
								<td>${vs.index + 1 }</td>
								<td>
										${report.date}
								</td>
								<td>
									<c:forEach items="${cityList}" var="city">
										<c:if test="${city.code == report.areaCode}">
											${city.name}
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:if test="${report.state=='1'}"><font color="green">显示</font></c:if>
									<c:if test="${report.state=='0'}"><font color="red">隐藏</font></c:if>
								</td>
								<td>
									<a href="javascript:updateReport(${report.id});" style="color: #0000FF;text-decoration: underline">进入</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">${report.page.pageStr }</div>
</div>

<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript">

	function getDate()
	{
		var today = document.getElementById("today").value;

		var formatDate = function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			m = m < 10 ? '0' + m : m;
			var d = date.getDate();
			d = d < 10 ? ('0' + d) : d;
			return y + '-' + m + '-' + d;
		};

		today = formatDate(new Date());

		document.getElementById("beginTime").value= today ;
		document.getElementById("endTime").value= today ;
	}

	function updateReport(reportId) {
		location = "toEdit.html?reportId=" + reportId;
	}



</script>
</body>
</html>
