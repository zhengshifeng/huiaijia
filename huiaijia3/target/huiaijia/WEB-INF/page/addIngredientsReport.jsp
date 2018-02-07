<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>食材检测报告详情</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
	<script type="text/javascript" src="../../style/js/jquery.js"></script>

</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">

	<h3>
		<input class="actionBtn add" type="button" id="add" class="btn" value="增加分类" title="增加分类" onclick="del()" />
		食材检测报告详情
	</h3>


	<div id="list">
		<form method="post" name="editForm" id="editForm" style="text-align:center">
			<input type="hidden" name="id" value="${report.id}" />
			<div class="filter" style="text-align: left" >

				<strong >日期:</strong>
				<input class="inpMain short date_picker" type="text" id="date" name="date" value="${report.date}"
					   style="width: 275px"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" readonly="readonly"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong >请选择:</strong>
				<select name="state" id="state" style="vertical-align: middle;width: 300px" >
					<option value="1" <c:if test="${report.state==1}">selected</c:if>>显示</option>
					<option value="0" <c:if test="${report.state==0}">selected</c:if>>隐藏</option>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong >所属城市:</strong>
				<select name="areaCode" id="areaCode" style="vertical-align: middle;width: 300px" >
					<c:forEach items="${cityList}" var="list">
						<option value="${list.code }" <c:if test="${report.areaCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
			</div>
		</form>
			<table style="width: 70%; text-align: center " class="tableBasic list" id="tb">
				<tr>
					<th>序号</th>
					<th>分类名称</th>
					<th>排序值</th>
					<th>操作</th>

				</tr>
				<c:choose>
					<c:when test="${not empty report.confVoList}">
						<c:forEach items="${report.confVoList}" var="confVo" varStatus="vs">
							<tr class="main_info">
								<td >${vs.index + 1 }</td>
								<td>${confVo.catename }</td>
								<td id="td${vs.index + 1}">${confVo.catesort }</td>
								<td>
									<a href="javascript:updateReportConf(${confVo.id});"  style="color: #0000FF">编辑</a>
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
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<div>
				<input id="submit" class="btn" type="button" value="提交" title="提交" />
			</div>

	</div>

	<div class="clear"></div>
	<div class="page_and_btn">${report.page.pageStr }</div>
</div>

<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript">

	$(function(){


		$('#submit').click(function(){

			var date = $("#date").val();
			if (date == '') {
				$("#date").focus();
				alert('请填写检测报告的日期!');
				return false;
			}

			btn_disable($('#submit'));
			var url = '/ingredientsReport/saveReport.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data.error == '0') {
					window.parent.layer.msg('保存成功');
					btn_enable($('#submit'));
					window.location.reload();
					window.location.href = '/ingredientsReport/list.html';
					<%--window.location.href = 'toEdit.html?reportId=' + ${data.reportId};--%>
				} else {
					alert(UNKNOWN_ERROR);
					btn_enable($('#submit'));
				}
			});
		});
	});




	function sltAllUser() {
		if ($("#sltAll").prop("checked")) {
			$("input[name='orderIds']").prop("checked", true);
		} else {
			$("input[name='orderIds']").prop("checked", false);
		}
	}



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

	function updateReportConf(confId) {
		location = "toEditConf.html?confId=" + confId;
	}



	/*
	* 新增新分类
	* */
	function toConf(reportId,sort){
		if (reportId==null || reportId=="") {
			confirm("您的报告不存在,请先提交该报告后增加更多分类!");
		}else{
			location = "toConf.html?reportId=" + reportId+"&sort="+sort;
		}

	}


	if (${not empty report.confVoList}) {
		var index1 = ${fn:length(report.confVoList)};
		var sort = document.getElementById('td'+index1).innerHTML;
	}else{
		var index1 = 0;
		var sort = 0;
	}
	var index2 = 1;

	function del(){
		$("#tb").append(" <tr class='main_info'> <td>"+ ++index1 +"</td> <td>未命名"+index2++ +"</td> <td>"+ ++sort +"</td>"
			+ "<td> <a href='javascript:toConf(${report.id},"+sort+");' style='color: #0000FF'>编辑</a> </td> </tr>"
		);
	}


</script>
</body>
</html>
