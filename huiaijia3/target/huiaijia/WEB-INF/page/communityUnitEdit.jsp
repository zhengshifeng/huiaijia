<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${community.communityName }楼栋排序</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>${community.communityName }楼栋排序</h3>
		
		<div class="filter">
			<input class="btn" id="btn_save" type="button" value="提交" />
			<input class="btn" id="btn_reset" type="button" value="重新排列" />
			<input class="btn" id="btn_back" type="button" value="返回" />
		</div>

		<div id="list">
			<form id="listForm" name="listForm" onsubmit="return false">
			<table style="width: auto;" class="tableBasic list">
				<tr>
					<th>ID</th>
					<th style="min-width: 200px;">楼栋</th>
					<th style="min-width: 200px;">排序值【数值越小则优先配送】</th>
				</tr>
				<c:choose>
					<c:when test="${not empty unitList}">
						<c:forEach items="${unitList}" var="list" varStatus="vs">
							<tr class="main_info">
								<td>
									${list.id }
								</td>
								<td>${list.unit }</td>
								<td>
									<input type="number" class="inpMain short" name="sort"
										   data-id="${list.id }" value="${list.sort }" />
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="3">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			</form>
		</div>
		<div class="clear"></div>
	</div>

	<script type="text/javascript">
		$(function() {
			// 页面加载后将数据保存起来
			var sort_arr_before = [];
			$.each($('input[name="sort"]'), function (i, v) {
				sort_arr_before[i] = $(v).val();
			});

			var update_flag = false;

			$('#btn_save').click(function() {
				// 记录新的排序数组
				var sort_arr = [];
				var json = '[';
				$.each($('input[name="sort"]'), function (i, v) {
					sort_arr[i] = $(v).val();
					if (sort_arr_before[i] != $(v).val()) {
						json += '{"id":'+$(v).attr('data-id')+', "sort":'+$(v).val()+'},';
						update_flag = true;
					}
				});
				json = json.substr(0, json.length-1);
				if (json.length > 0) {
					// 判断是否有重复的排序，如果有，则提示用户进行修改
					sort_arr = sort_arr.sort();
					$.each(sort_arr, function (i, v) {
						if (v == sort_arr[i+1]) {
							alert('有重复的排序值，请修改');
							update_flag = false;
							return false;
						}
					});

					if (update_flag) {
						$('#btn_save').val('正在处理...');
						$('#btn_save').attr('disabled', 'disabled');
						json = 'params=' + json + ']';

						var url = '/communityUnit/updateSort.html';
						$.post(url, json, function (result) {
							alert(result.msg);
							$('#btn_save').val('提交');
							$('#btn_save').removeAttr('disabled');
							if (result.error == '0') {
								window.location.reload();
							}
						});
					}
				}
			});

			$('#btn_back').click(function () {
				$.each($('input[name="sort"]'), function (i, v) {
					if (sort_arr_before[i] != $(v).val()) {
						update_flag = true;
						return false;
					}
				});
				if (update_flag) {
					if (confirm('是否放弃当前的编辑')) {
						history.back();
					}
				} else {
					history.back();
				}
			});

			$('#btn_reset').click(function () {
				if (confirm('是否重新排列？')) {
					$.each($('input[name="sort"]'), function (i, v) {
						$(v).val(i);
					});
				}
			});
		});
	</script>
</body>
</html>
