<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商详情</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<style type="text/css">
	table#info tr {
		height: 40px;
		line-height: 40px;
		margin-bottom: 10px;
	}
	
	table#info td.title {
		text-align: right;
	}
	
	table#info td.content {
		text-align: left;
	}
	
	table#info input[type="button"] {
		width: 100px;
		cursor: pointer;
	}
}
</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="javascript:;" id="btn_edit" class="actionBtn">修改供应商</a>
		<a href="javascript:;" id="btn_delete" class="actionBtn" style="margin-right: 7px;">删除供应商</a>
		供应商详情
	</h3>
	
	<table id="info">
		<tr>
			<td class="title">供应商编号：</td>
			<td class="content">${vo.supplyNo }</td>
			<td class="title">供应商名称：</td>
			<td class="content">${vo.supplyName }</td>
		</tr>
		<tr>
			<td class="title">供应商地址：</td>
			<td class="content" colspan="3">${vo.address }</td>
		</tr>
		<tr>
			<td class="title">供应商对接人：</td>
			<td class="content">${vo.docker }</td>
			<td class="title">联系方式：</td>
			<td class="content">${vo.cellphone }</td>
		</tr>
		<tr>
			<td class="title">汇爱家对接人：</td>
			<td class="content">${vo.hajdocker }</td>
			<td class="title">联系方式：</td>
			<td class="content">${vo.hajcellphone }</td>
		</tr>
		<tr>
			<td class="title">开始合作时间：</td>
			<td class="content">${vo.beginTime }</td>
			<td class="title">合作结束时间：</td>
			<td class="content">${vo.endTime }</td>
		</tr>
		<tr>
			<td class="title">合同有效期限：</td>
			<td class="content">${vo.contractPeriod }</td>
			<td class="title">合作状态：</td>
			<td class="content">
			<c:choose>
				<c:when test="${vo.status==1}">合作中</c:when>
				<c:when test="${vo.status==2}">等待审批</c:when>
				<c:when test="${vo.status==3}">解除合作</c:when>
				<c:otherwise>不限</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td class="title">供应地区：</td>
			<td class="content" colspan="3">${vo.province } - ${vo.city } - ${vo.community }</td>
		</tr>
		<tr>
			<td class="title">商品类别：</td>
			<td class="content" colspan="3">
				属性：${vo.commodityAttr } - 
				大类：${vo.parentTypeName } - 
				小类：${vo.typeName }
			</td>
		</tr>
	</table>

	<div id="list">
		<table style="width: 100%;  "
			class="tableBasic list">
			<tr class="main_head">
				<th colspan="6">供应商品明细</th>
			</tr>
			<tr class="main_head">
				<th>序号</th>
				<th>商品编号</th>
				<th>商品名称</th>
				<th>商品属性</th>
				<th>商品大类</th>
				<th>商品小类</th>
			</tr>
			<c:choose>
				<c:when test="${not empty commodityVoList}">
					<c:forEach items="${commodityVoList}" var="list" varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1}</td>
						<td>${list.commodityNo }</td>
						<td>${list.name }</td>
						<td>${list.commodityAttr }</td>
						<td>${list.parentTypeName }</td>
						<td>${list.typeName }</td>
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
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$('#btn_edit').click(function(){
			window.location.href = '/supplyChain/edit.html?id=${vo.id}';
		});
		
		$('#btn_delete').click(function(){
			if (confirm('此操作不可恢复，确定要这样做么？')) {
				var url = '/supplyChain/delete.html';
				$.get(url, {'id': '${vo.id}'}, function(data){
					if (data.status == 'success') {
						window.location.href = document.referrer;
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
