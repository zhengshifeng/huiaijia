<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>缓存管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>缓存管理</h3>
		<form action="keys.html" method="post" name="searchForm" id="searchForm">
			<div class="filter">
				<input type="text" class="inpMain" placeholder="精确查找" name="searchkey" value="${searchkey }" />
				<input type="text" class="inpMain" placeholder="模糊查找" name="patternkey" value="${patternkey }" />
				<label><input type="checkbox" name="user_redis" value="1" <c:if test="${user_redis != null }">checked</c:if> />查看用户缓存</label>
				<label><input type="checkbox" name="login_vcode" value="1" <c:if test="${login_vcode != null }">checked</c:if> />查看登录验证码</label>
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr class="main_head">
					<th>序号</th>
					<th>配置key</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty keyList}">
						<c:forEach items="${keyList}" var="key" varStatus="vs">
							<tr class="main_info">
								<td>${vs.index+1}</td>
								<td>${key.redisKey }</td>
								<td>
									<a href="getValueByKey.html?key=${key.redisKey }">查看value</a>
									 | 
									<a href="javascript:clearKey('${key.redisKey }');">清空</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<div class="action">
				<input class="btn" type="button" value="清空所有缓存" onclick="clearAllKeys()"/>
				<input class="inpMain" type="text" name="likeKeys" id="likeKeys" placeholder="输入key前缀"/>
				<input class="btn" type="button" value="清空匹配缓存" onclick="clearlikeKeys()"/>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function clearKey(redisKey) {
			if (confirm("确定要清空【" + redisKey + "】记录？")) {
				var url = "deleteRedisKey.html?redisKey=" + redisKey;
				$.get(url, function(data) {
					if (data == "success") {
						document.location.reload();
					}
				});
			}
		}

		function clearAllKeys() {
			if (confirm("确定要清空所有redis缓存记录？")) {
				var url = "deleteAllRedisKey.html";
				$.get(url, function(data) {
					if (data == "success") {
						document.location.reload();
					}
				});
			}
		}

		function clearlikeKeys() {
			var likeKey = $("#likeKeys").val();
			if (confirm("确定要清空【" + likeKey + "】记录？")) {
				var url = "deletelikeKeys.html?likeKeys=" + likeKey;
				$.get(url, function(data) {
					if (data == "success") {
						document.location.reload();
					}
				});
			}
		}
	</script>
</body>
</html>
