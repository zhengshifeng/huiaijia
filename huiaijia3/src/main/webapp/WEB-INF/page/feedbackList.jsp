<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户反馈列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>客户反馈列表</h3>
	<form action="/feedback/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			日期：
			<input type="button" class="btnGray" id="btn_today" value="今日" />
			<input type="text" class="inpMain short date_picker" name="beginTime" id="beginTime" value="${vo.beginTime }" onclick="WdatePicker()" readonly="readonly" /> - 
			<input type="text" class="inpMain short date_picker" name="endTime" id="endTime" value="${vo.endTime }" onclick="WdatePicker()" readonly="readonly" />
		</div>
		<div class="filter">
			用户：
			<select name="ismember" style="vertical-align: middle;">
				<option value="0">用户角色</option>
				<option value="1" <c:if test="${vo.ismember==1}">selected</c:if>>仅注册</option>
				<option value="2" <c:if test="${vo.ismember==2}">selected</c:if>>预备会员</option>
				<option value="3" <c:if test="${vo.ismember==3}">selected</c:if>>一元购会员</option>
				<option value="4" <c:if test="${vo.ismember==4}">selected</c:if>>会员取消</option>
				<option value="5" <c:if test="${vo.ismember==5}">selected</c:if>>普通会员</option>
			</select>
			<input type="text" class="inpMain" placeholder="用户ID / 手机号码 / 收货人" name="userinfo" value="${vo.userinfo }"/>
		</div>
		<div class="filter">
			地区：
			<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
				<option value="0">省</option>
				<c:forEach items="${provinceList}" var="list">
				<option value="${list.code }" <c:if test="${vo.provinceCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select name="cityCode" id="cityCode" style="vertical-align: middle;">
				<option value="0">市</option>
				<c:forEach items="${cityList}" var="list">
				<option value="${list.code }" <c:if test="${vo.cityCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select name="communityCode" id="communityCode" style="vertical-align: middle;">
				<option value="0">区/县</option>
				<c:forEach items="${communityList}" var="list">
				<option value="${list.code }" <c:if test="${vo.communityCode==list.code}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<input type="text" class="inpMain" placeholder="小区ID / 小区名称" name="communityInfo" value="${vo.communityInfo }"/>
			
			<select name="resolved" style="vertical-align: middle;">
				<option value="">解决情况</option>
				<option class="text_red" value="0" <c:if test="${vo.resolved==0}">selected</c:if>>未解决</option>
				<option class="text_blue" value="1" <c:if test="${vo.resolved==1}">selected</c:if>>已解决</option>
				<option class="text_green" value="2" <c:if test="${vo.resolved==2}">selected</c:if>>已回复</option>
				<option class="text_invalid" value="3" <c:if test="${vo.resolved==3}">selected</c:if>>不处理</option>
			</select>
			<input id="btn_search" class="btn" type="submit" value="搜索" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;  "
				class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>省份</th>
					<th>城市</th>
					<th>区域</th>
					<th>小区名称</th>
					<th>用户名称</th>
					<th>注册手机号码</th>
					<th>用户角色</th>
					<th>反馈内容</th>
					<th>反馈时间</th>
					<th>解决情况</th>
					<th>备注</th>
				</tr>
				<c:choose>
					<c:when test="${not empty feedbackList}">
						<c:forEach items="${feedbackList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>${list.id }</td>
							<td>${list.province }</td>
							<td>${list.city }</td>
							<td>${list.community }</td>
							<td>${list.communityName }</td>
							<td>${list.username }</td>
							<td>${list.telPhone }</td>
							<td>
								<c:choose>
									<c:when test="${list.ismember == 1 }">仅注册</c:when>
									<c:when test="${list.ismember == 2 }">预备会员</c:when>
									<c:when test="${list.ismember == 3 }">一元购会员</c:when>
									<c:when test="${list.ismember == 4 }">会员取消</c:when>
									<c:when test="${list.ismember == 5 }">普通会员</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td style="white-space: normal; overflow: hidden; max-width: 250px;">
								${list.quickComment };${list.content }
							</td>
							<td>
								<fmt:parseDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss" var="parseValue"/>
								<fmt:formatDate value="${parseValue}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<select name="resolved" data-id="${list.id }" data-remark="${list.remark2 }" 
										data-val="${list.resolved}" style="vertical-align: middle;" 
										<c:choose>
											<c:when test="${list.resolved == 0 }">class="text_red"</c:when>
											<c:when test="${list.resolved == 1 }">class="text_blue"</c:when>
											<c:when test="${list.resolved == 2 }">class="text_green"</c:when>
											<c:when test="${list.resolved == 3 }">class="text_invalid"</c:when>
										</c:choose>>
									<option class="text_red" value="0" >未解决</option>
									<option class="text_blue" value="1" <c:if test="${list.resolved==1}">selected</c:if>>已解决</option>
									<option class="text_green" value="2" <c:if test="${list.resolved==2}">selected</c:if>>已回复</option>
									<option class="text_invalid" value="3" <c:if test="${list.resolved==3}">selected</c:if>>不处理</option>
								</select>
							</td>
							<td>${list.remark2 }</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="12">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">${vo.page.pageStr }</div>
</div>

<script type="text/javascript">
	function search(){
		$("#searchForm").submit();
	}
	
	
	$(function(){

		$('#btn_today').click(function(){
			var today = date_format(new Date(), '-');
			$('#beginTime').val(today + ' 00:00:00');
			$('#endTime').val(today + ' 23:59:59');
		});
		
		
		$('#deleteForm select[name="resolved"]').change(function(){
			if (confirm('如果不是手抖，点确定就可以修改状态了！')) {
				var feedback = new Object();
				feedback.id = $(this).attr('data-id');
				feedback.resolved = $(this).val();
				feedback.remark2 = $(this).attr('data-remark');
				
				// 没有备注默认为已解决
				feedback.remark2 = feedback.remark2 ? feedback.remark2 : '';
				
				if (feedback.resolved == 2) {
					feedback.remark2 = prompt('是怎么回复客户的呢？简单的描述一下！', feedback.remark2);
				}
				$.ajax({
					url : '/feedback/update.html?id='+feedback.id+'&resolved='+feedback.resolved+'&remark2='+feedback.remark2,
					type : 'GET',
					cache : false,
					contentType : false, //不可缺
					processData : false, //不可缺
					success : function(result) {
						if (result == 'success') {
							location.reload();
						} else {
							alert(UNKNOWN_ERROR);
						}
					},
					error : function() {
						alert(UNKNOWN_ERROR);
					}
				});
			} else {
				// 点取消还原之前选中的状态
				$(this).val($(this).attr('data-val'));
			}
		});
	});
</script>
</body>
</html>
