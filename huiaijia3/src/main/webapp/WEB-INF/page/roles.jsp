<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3><a class="actionBtn add" href="role/auth.html?roleId=0">添加</a>角色管理</h3>
		<div id="list">
			<table style="width: 100%;  " class="tableBasic list">
				<tr>
					<th>序号</th>
					<th>角色名称</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty roleList}">
						<c:forEach items="${roleList}" var="role" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
							<td id="roleNameTd${role.roleId }">${role.roleName }</td>
							<td>
								<a href="javascript:;" onclick="delrole(${role.roleId })">删除</a> | 
								<a href="role/auth.html?roleId=${role.roleId }">权限</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="3">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function editRights(roleId){
		var dg = new $.dialog({
			title:'角色授权',
			id:'auth',
			width:280,
			height:370,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			resize:false,
			page:'role/auth.html?roleId='+roleId
			});
		dg.ShowDialog();
	}
	
	function delrole(roleId){
		var flag = false;
	
		if(confirm("确定要删除该菜单吗？")){
			flag = true;
		}
		if(flag){
			var url = "role/del.html?roleId="+roleId;
			$.get(url,function(data){
				if(data=='1'){
					document.location.reload();
				}
			});
		}
	}
	</script>
</body>
</html>
