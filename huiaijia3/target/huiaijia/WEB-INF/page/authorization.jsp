<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<link rel="stylesheet" href="../../style/plugins/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/role.html" class="actionBtn">角色管理</a>
		<c:if test="${empty role.roleId }">
		添加角色
		</c:if>
		<c:if test="${not empty role.roleId }">
		修改角色
		</c:if>
	</h3>
	
	<form name="editForm" id="editForm" method="post" >
	<input type="hidden" name="roleId" value="${role.roleId }" />
	<table class="tableBasic" style="width: 100%;  ">
		<tr>
			<td width="150px">角色名称：</td>
			<td><input type="text" name="roleName" id="roleName" class="inpMain" value="${role.roleName }"/></td>
		</tr>
		<tr>
			<td>权限：</td>
			<td>
				<ul id="tree" class="ztree" style="overflow:auto;"></ul>
				<input type="hidden" name="menuIds" id="menuIds"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input id="btn_submit" class="btn" type="button" value="提交" title="提交" /></td>
		</tr>
	</table>
	</form>	
</div>
<script type="text/javascript">
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

var zn = '${zTreeNodes}';
var zTreeNodes = eval(zn);
var zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);

$(function(){
	$('#btn_submit').click(function(){
		var nodes = zTree.getCheckedNodes();
		var tmpNode;
		var ids = "";
		for(var i=0; i<nodes.length; i++){
			tmpNode = nodes[i];
			if(i!=nodes.length-1){
				ids += tmpNode.id+",";
			}else{
				ids += tmpNode.id;
			}
		}
		var roleName = $('#roleName').val();
		var url = "auth/save.html";
		$('#menuIds').val(ids);
		var postData = $('#editForm').serialize();

		btn_disable($('#btn_submit'));
		$.post(url, postData, function(data){
			if(data=="1") {
				alert('操作成功');
				window.location.href = "/role.html";
			} else if (data == '2') {
				alert('保存角色名失败');
				btn_enable($('#btn_submit'));
			} else {
				alert('操作失败');
				btn_enable($('#btn_submit'));
			}
		});
	});
});
</script>
</body>
</html>