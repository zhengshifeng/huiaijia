<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置参数</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3><a href="add.html" class="actionBtn add">添加配置</a>配置参数</h3>
		<form action="list.html" method="post" name="userForm" id="userForm">
			<div class="filter">
				<input type="text" class="inpMain" placeholder="配置名" name="name" value="${configuration.name }" />
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>配置名称</th>
					<th>配置值</th>
					<th>创建时间</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty configurationList}">
						<c:forEach items="${configurationList}" var="config" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
							<td>${config.name }</td>
							<td title="${config.value }" style="max-width: 250px; overflow: hidden; 
									text-overflow: ellipsis; white-space: nowrap; course:hand;">${config.value }</td>
							<td>${config.createTime}</td>
							<td>${config.configDescribe}</td>
							<td>
								<a href="edit.html?configurationId=${config.id }">修改</a> | 
								<a href="javascript:delConfiguration(${config.id });">删除</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<div class="page_and_btn">
				${configuration.page.pageStr }
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function delConfiguration(configId){
			if(confirm("确定要删除该记录？")){
				var url = "delete.html?id="+configId;
				$.get(url,function(data){
					if(data=="success"){
						document.location.reload();
					}
				});
			}
		}
	</script>
</body>
</html>
