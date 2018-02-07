<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="menu/add.html" class="actionBtn add">添加菜单</a>菜单管理
		</h3>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>名称</th>
					<th>资源路径</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty menuList}">
						<c:forEach items="${menuList}" var="menu" varStatus="vs">
							<tr id="tr${menu.menuId }" style="font-weight: bolder;">
								<td>${menu.menuId}</td>
								<td>${menu.menuName }</td>
								<td>${menu.menuUrl }</td>
								<td>${menu.sort }</td>
								<td>
									<a href="menu/edit.html?menuId=${menu.menuId }" >修改</a> | 
									<a href="#" onclick="delmenu(${menu.menuId }, true)">删除</a>
								</td>
							</tr>
							<c:forEach items="${menu.subMenu}" var="sub" varStatus="sub_vs">
								<tr id="tr${sub.menuId }">
									<td>├───${sub.menuId}</td>
									<td>├───${sub.menuName }</td>
									<td>├───${sub.menuUrl }</td>
									<td>├───${sub.sort }</td>
									<td>├───
										<a href="menu/edit.html?menuId=${sub.menuId }" >修改</a> | 
										<a href="javascript:;" onclick="delmenu(${sub.menuId }), false">删除</a>
									</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">没有相关数据</td>
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
		function delmenu(menuId,isParent){
			var flag = false;
			if(isParent){
				if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
					flag = true;
				}
			} else {
				if(confirm("确定要删除该菜单吗？")){
					flag = true;
				}
			}
			if(flag){
				var url = "menu/del.html?menuId="+menuId;
				$.get(url,function(data){
					document.location.reload();
				});
			}
		}
	</script>
</body>
</html>