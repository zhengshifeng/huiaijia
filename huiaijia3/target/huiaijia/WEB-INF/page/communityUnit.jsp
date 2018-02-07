<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区地址管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>${community.communityName }单元管理</h3>
		
		<div class="filter">
			<input class="btn" id="btnBatchAdd" type="button" value="更新地址组合" />
			<input type="hidden" id="communityId" name="communityId" value="${community.id }" />
			<b class="tip" style="font-size: larger; color: red;"></b>
		</div>

		<div id="list">
			<table style="width: 100%;" class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>地址</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty unitList}">
						<c:forEach items="${unitList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>${list.id }</td>
								<td>${list.unit }</td>
								<td>${list.sort }</td>
								<td><a href="javascript:" onclick="delUtil('${list.id }', '${list.unit }')">删除</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="clear"></div>
	</div>

	<script type="text/javascript">

		function delUtil(id, util) {
			if (confirm('确定要删除['+util+']？')) {
				var url = '/communityUnit/delete.html';
				$.post(url, 'id='+id, function(result) {
					alert(result.msg);
					if (result.error == '0') {
                        window.location.reload();
					}
				});
			}
		}

		//====================================
		// 文字闪烁效果
		//====================================
		var colorArr = ["white", "red"];
		var i = 0;
		function changeTextColor() {
			$('.tip').css('color', colorArr[i++]);
			i = (i > 1) ? 0 : i;
		}
		
		$(function() {
			$('#btnAdd').click(function() {
				var unit = '';
				for (var i = 0, len = $('.slt_unit').size(); i < len; i++) {
					unit += ($('.slt_unit')[i].value) == '无' ? '' : $('.slt_unit')[i].value;
				}
				$('#unit').val(unit);
				
				if (unit && confirm('您即将添加的地址为['+unit+']，如有误请取消！')) {
					var url = '/communityUnit/save.html';
					$.post(url, $('#addForm').serialize(), function(result) {
						alert(result.msg);
						if (result.result == 'success') {
							window.location.reload();
						}
						alert(result.msg);
					});
				}
			});
			
			$('#btnBatchAdd').click(function() {
				var communityId = $('#communityId').val();
				if (communityId && confirm('更新地址组合将根据【楼栋管理】中的结构重新生成地址组合，但不会删除现有的组合，确定要更新吗？')) {
					$('.tip').html('正在处理...');
					setInterval("changeTextColor()", 500);

					var url = '/communityUnit/batchGenerate.html?communityId='+communityId;
					$.getJSON(url, function(result) {
						if (result.result == 'success') {
							alert(result.msg);
							window.location.reload();
						} else {
							alert(result.msg);
						}
					});
				}
			});
		});
	</script>
</body>
</html>
