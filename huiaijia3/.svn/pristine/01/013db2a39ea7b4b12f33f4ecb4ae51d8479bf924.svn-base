<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加供应商</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/supplyChain/list.html" class="actionBtn">供应商列表</a>
		<c:if test="${empty vo.id }">
		添加供应商
		</c:if>
		<c:if test="${not empty vo.id }">
		修改供应商
		</c:if>
	</h3>
	<form method="post" name="editForm" id="editForm">
	<c:if test="${empty vo.id }">
	<input type="hidden" name="supplyNo" value="${vo.supplyNo }" />
	</c:if>
	<c:if test="${not empty vo.id }">
	<input type="hidden" name="id" value="${vo.id }" />
	</c:if>
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td width="150px">id：</td>
			<td>${vo.id}</td>
		</tr>
		<tr>
			<td width="150px">erp供应商id：</td>
			<td>${vo.cloudsSupplierId}</td>
		</tr>
		<tr>
			<td width="150px">供应商编号：</td>
			<td>${vo.supplyNo }</td>
		</tr>
		<tr>
			<td>供应商名称：</td>
			<td>
				<input type="text" class="inpMain" name="name" value="${vo.supplyName }" />
			</td>
		</tr>
		<tr>
			<td>供应商地址：</td>
			<td>
				<input type="text" class="inpMain" name="address" value="${vo.address }" />
			</td>
		</tr>
		<tr>
			<td>供应商对接人：</td>
			<td>
				<input type="text" class="inpMain" name="docker" value="${vo.docker }" />
			</td>
		</tr>
		<tr>
			<td>联系方式：</td>
			<td>
				<input type="text" class="inpMain" name="cellphone" value="${vo.cellphone }" />
			</td>
		</tr>
		<tr>
			<td>汇爱家对接人：</td>
			<td>
				<input type="text" class="inpMain" name="hajdocker" value="${vo.hajdocker }" />
			</td>
		</tr>
		<tr>
			<td>联系方式：</td>
			<td>
				<input type="text" class="inpMain" name="hajcellphone" value="${vo.hajcellphone }" />
			</td>
		</tr>
		<tr>
			<td>开始合作时间：</td>
			<td>
				<input type="text" class="inpMain short date_picker" name="beginTime" value="${vo.beginTime }" onclick="WdatePicker()" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td>合作结束时间：</td>
			<td>
				<input type="text" class="inpMain short date_picker" name="endTime" value="${vo.endTime }" onclick="WdatePicker()" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td>合同有效期限：</td>
			<td>
				<input type="text" class="inpMain" name="contractPeriod" value="${vo.contractPeriod }" />
			</td>
		</tr>
		<tr>
			<td>合作状态：</td>
			<td>
				<select name="status" >
					<option value="0">合作状态</option>
					<option value="1" <c:if test="${vo.status==1}">selected</c:if>>合作中</option>
					<option value="2" <c:if test="${vo.status==2}">selected</c:if>>等待审批</option>
					<option value="3" <c:if test="${vo.status==3}">selected</c:if>>解除合作</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>供应地区：</td>
			<td>
				<select name="provinceCode" id="provinceCode" >
					<option value="0">省</option>
					<c:forEach items="${provinceList}" var="list">
					<option value="${list.code }" <c:if test="${vo.provinceCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="cityCode" id="cityCode" >
					<option value="0">市</option>
					<c:forEach items="${cityList}" var="list">
					<option value="${list.code }" <c:if test="${vo.cityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="communityCode" id="communityCode">
					<option value="0">区 / 县</option>
					<c:forEach items="${communityList}" var="list">
					<option value="${list.id }" <c:if test="${vo.communityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>商品类别：</td>
			<td>
				<select name="commodityAttr" id="commodityAttr" >
					<option value="不限">属性</option>
					<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
					<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
					<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
					<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
				</select>
				<select name="parentTypeId" id="parentTypeId" >
					<option value="0">大类</option>
					<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>
				<select name="typeId" id="typeId" >
					<option value="0">小类</option>
					<option value="0">不限</option>
					<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input id="btn_submit" class="btn" type="button" value="提交" />
				<input type="reset" class="btn" />
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
	$('#btn_submit').click(function(){
		if (confirm('老师说，交卷前要养成检查的好习惯，确定现在提交吗？')) {
			var url = '/supplyChain/save.html';
			$.post(url, $('#editForm').serialize(), function(data){
				if (data == 'success') {
					alert('well, 搞定！');
					<c:if test="${empty vo.id }">
					window.location.href = '/supplyChain/list.html';
					</c:if>
					<c:if test="${not empty vo.id }">
					window.location.href = document.referrer;
					</c:if>
				} else {
					alert('未知错误');
				}
			});
		}
	});
});
</script>
</body>
</html>