<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>类目管理</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<link rel="stylesheet" href="../../style/plugins/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
	<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.exedit.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/categoryThree/findCategoryById.html?&oneId=0&twoId=0&threeId=0" class="actionBtn" style="margin-left: 10px;">返回管理页面</a>
		类目管理
	</h3>

	<form name="editForm" id="editForm" method="post">
		<table class="tableBasic" style="width: 100%;  ">
			<tr>
				<td>
					<input type="button" id="btn_add_root_node" class="btn" value="添加根节点" />
					<ul id="tree" class="ztree" style="overflow:auto;"></ul>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: true,
			showRenameBtn: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeRemove: beforeRemove,
			onRemove: onRemove,
			onDrop: onDrop,
			onRename: onRename
		}
	};

	var className = "dark";

	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(treeNode);
		return confirm("确认删除【" + treeNode.name + "】吗？");
	}

	//删除节点
	function onRemove(e, treeId, treeNode) {
		var url = "/categoryThree/delete.html";
		if (treeNode.id && treeNode.id > 0) {
			var postData = 'id=' + treeNode.id;
			// console.log(url + postData);

			$.post(url, postData, function (result) {
				if (result == 'success') {
					window.location.reload();
				}
			});
		}
	}


	/*
	* 保存类目节点
	* */
	function onRename(e, treeId, treeNode) {
		var url = "/categoryThree/save.html";
		var postData = 'id=' + treeNode.id
			+ '&name=' + treeNode.name
			+ '&sort=' + treeNode.getIndex()
			+ '&level=' + treeNode.level;

		if (treeNode.getParentNode() !== null) {
			postData += '&parentId=' + treeNode.getParentNode().id;
		} else {
			postData += '&parentId=0';
		}

		// console.log(url+postData);
		if (treeNode.level==3) {
			alert("最多添加三级类目,不能再多了!");
			window.location.reload();
			return false;
		}else{
			$.post(url, postData, function(result){
				alert(result.msg);
				if (result.error == '0') {
					window.location.reload();
				}
			});
		}



	}

	/*
	* 更新类目节点
	* */
	function onDrop (event, treeId, treeNodes, targetNode, moveType) {
		var treeNode = treeNodes[0];
		console.log('onDrop: ' + treeNode.name);
		if (treeNode.id && treeNode.id > 0) {
			var url = "/categoryThree/save.html";
			var postData = 'id=' + treeNode.id
				+ '&sort=' + treeNode.getIndex()
				+ '&level=' + treeNode.level
			;

			if (treeNode.getParentNode() !== null) {
				postData += '&parentId=' + treeNode.getParentNode().id;
			} else {
				postData += '&parentId=0';
			}

			console.log(url + postData);

			$.post(url, postData, function (result) {
				// nothing to do
				// console.log(result.msg);
			});
		}
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);

		// 添加节点
		if (btn) btn.bind("click", function (e) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var newNode = zTree.addNodes(treeNode, {id: (newCount*-1), pId: treeNode.id, name: "new node" + (newCount++)});
			zTree.editName(newNode[0]);
			return false;
		});
	}

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	}

	$(document).ready(function () {
		var zNodes = eval('${buildings}');
		$.fn.zTree.init($("#tree"), setting, zNodes);
		var zTree = $.fn.zTree.getZTreeObj("tree");

		$('#btn_add_root_node').click(function () {
			var newNode = zTree.addNodes(null, {id:(newCount*-1), pId:0, isParent:true, name:"new node" + (newCount++)});
			zTree.editName(newNode[0]);
		});
	});
</script>
</body>
</html>