<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<%--<a href="/supplyChain/add.html" class="actionBtn add">添加供应商</a>--%>供应商列表
	</h3>
	<form action="/supplyChain/list.html" method="post" name="searchForm" id="searchForm">
	<div class="filter">
		<select name="commodityAttr" id="commodityAttr" style="vertical-align: middle;">
			<option value="">商品属性</option>
			<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
			<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
			<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
			<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
		</select>
		<select name="parentTypeId" id="parentTypeId" style="vertical-align: middle;">
			<option value="0">大类</option>
			<c:forEach items="${parentTypeList}" var="list">
			<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
			</c:forEach>
		</select>
		<select name="typeId" id="typeId" style="vertical-align: middle; margin-right: 15px;">
			<option value="0">小类</option>
			<c:forEach items="${subTypeList}" var="list">
			<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
			</c:forEach>
		</select>
		
		<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
			<option value="0">省份</option>
			<c:forEach items="${provinceList}" var="list">
			<option value="${list.code }" <c:if test="${vo.provinceCode==list.code}">selected</c:if>>${list.name }</option>
			</c:forEach>
		</select>
		<select name="cityCode" id="cityCode" style="vertical-align: middle;">
			<option value="0">城市</option>
			<c:forEach items="${cityList}" var="list">
			<option value="${list.code }" <c:if test="${vo.cityCode==list.code}">selected</c:if>>${list.name }</option>
			</c:forEach>
		</select>
		<select name="communityCode" id="communityCode" style="vertical-align: middle;">
			<option value="">区域</option>
			<c:forEach items="${communityList}" var="list">
			<option value="${list.code }" <c:if test="${vo.communityCode==list.code}">selected</c:if>>${list.name }</option>
			</c:forEach>
		</select>
		
		<select name="status" id="status" style="vertical-align: middle; margin-left: 15px;">
			<option value="0">合作状态</option>
			<option value="1" <c:if test="${vo.status==1}">selected</c:if>>合作中</option>
			<option value="2" <c:if test="${vo.status==2}">selected</c:if>>等待审批</option>
			<option value="3" <c:if test="${vo.status==3}">selected</c:if>>解除合作</option>
		</select>
	</div>
	<div class="filter">
		<input class="inpMain" type="text" name="supplyNo" id="supplyNo" placeholder="供应商 / 供应商编号" value="${vo.supplyNo }"/>
		<input class="inpMain" type="text" name="commodityNo" id="commodityNo" placeholder="商品名称 / 商品编号" value="${vo.commodityNo }"/>
		<input class="btn" type="submit" value="搜索" />
	</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;  "
				class="tableBasic list">
				<tr>
					<th>
						<label><input type="checkbox" id="ckb_id" onclick="selectAll()"/>全选</label>
					</th>
					<th>序号</th>
					<th>供应商编号</th>
					<th>供应商名称</th>
					<th>供应商地址</th>
					<th>供应商对接人</th>
					<th>联系方式</th>
					<th>汇爱家负责人</th>
					<th>联系方式</th>
					<th>录入时间</th>
					<th>合作状态</th>
					<th>合同期限</th>
					<th>供应省份</th>
					<th>供应城市</th>
					<th>供应区域</th>
					<th>商品属性</th>
					<th>商品大类</th>
					<th>商品小类</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty supplyChainList}">
						<c:forEach items="${supplyChainList}" var="list" varStatus="vs">
						<tr class="main_info">
							<td>
								<input type="checkbox" name="id" id="id_${list.id }" value="${list.id }"/>
							</td>
							<td>${vs.index+1}</td>
							<td>${list.supplyNo }</td>
							<td>${list.supplyName }</td>
							<td>${list.address }</td>
							<td>${list.docker }</td>
							<td>${list.cellphone }</td>
							<td>${list.hajdocker }</td>
							<td>${list.hajcellphone }</td>
							<td>
								<fmt:parseDate value="${list.createTime}" pattern="yyyy-MM-dd" var="parseValue"/>
								<fmt:formatDate value="${parseValue}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.status == 1 }">合作中</c:when>
									<c:when test="${list.status == 2 }">等待批复</c:when>
									<c:when test="${list.status == 3 }">解除合作</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>${list.contractPeriod }</td>
							<td>${list.province }</td>
							<td>${list.city }</td>
							<td>${list.community }</td>
							<td>${list.commodityAttr }</td>
							<td>${list.parentTypeName }</td>
							<td>${list.typeName }</td>
							<td>
								<a href="/supplyChain/edit.html?id=${list.id }">修改</a>
								<a href="/supplyChain/info.html?id=${list.id }">查看详情</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="19">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<input id="btn_delete" class="btn" type="button" value="批量删除" />
			<select id="slt_update_status" name="status">
				<option>批量更新状态</option>
				<option value="1">合作中</option>
				<option value="2">等待批复</option>
				<option value="3">解除合作</option>
			</select>
		</div>
		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript">

	// 选中所有选择框
	function selectAll(){
		if($("#ckb_id").prop("checked")){
			$("input[name='id']").prop("checked",true);
		} else {
			$("input[name='id']").prop("checked",false);
		}
	}
	
	function search(){
		$("#searchForm").submit();
	}
	
	$(function(){
		// 批量删除
		$('#btn_delete').click(function(){
			var len = $("input[name='id']:checked").length;
			if (len > 0) {
				if (confirm('此操作不可恢复，确定要这样做么？')) {
					var url = '${pageContext.request.contextPath }/supplyChain/delete.html';
					$.post(url, $('#deleteForm').serialize(), function(data){
						if (data.status == 'success') {
							window.location.reload();
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请选择需要删除的供应商');
			}
		});
		
		// 批量更新状态
		$('#slt_update_status').change(function(){
			var len = $("input[name='id']:checked").length;
			if (len > 0) {
				if ($(this).val() && confirm('确定更新选中的供应商状态吗？')) {
					var url = '${pageContext.request.contextPath }/supplyChain/updateStatus.html';
					$.get(url, $('#deleteForm').serialize(), function(data){
						if (data.status == 'success') {
							window.location.reload();
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请选择需要修改的供应商');
				$('#slt_update_status').prop('selectedIndex', 0);
			}
		});
	});
</script>
</body>
</html>
