<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>小区限制</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<link rel="stylesheet" href="../../style/plugins/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
	<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		小区限制
	</h3>

	<form name="editForm" id="editForm" method="post">
		<table class="tableBasic" style="width: 100%;  ">
			<tr>
				<td>
					<input type="button" id="btn_back" class="btn" value="取消并返回" />
					<input type="button" id="btn_check_all" class="btn" value="全选" data-status="0" />
					<input type="button" id="btn_expand" class="btn" value="全部折叠" data-status="0" />
					<input type="button" id="btn_submit" class="btn" value="确认提交" title="确认提交" />
					<span class="text_red" style="font-size: 16px; font-weight: bolder">搜索请按 Ctrl + F</span>
				</td>
			</tr>
			<tr>
				<td>
					<ul id="tree" class="ztree" style="overflow:auto;"></ul>
				</td>
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

	$(document).ready(function () {
		var zNodes = eval('${treeNodes}');
		$.fn.zTree.init($("#tree"), setting, zNodes);
		var zTree = $.fn.zTree.getZTreeObj("tree");

		$('#btn_check_all').click(function () {
			var status = $(this).attr('data-status');
			if (status == 0) {
				$(this).attr('data-status', 1);
				zTree.checkAllNodes(true);
			} else {
				$(this).attr('data-status', 0);
				zTree.checkAllNodes(false);
			}
		});

		$("#btn_submit").click(function () {
			btn_disable($(this));
			var checked_nodes = zTree.getCheckedNodes();
			var residential = [];
			for (var i = 0; i < checked_nodes.length; i++) {
				if (checked_nodes[i].level == 1) {
					residential.push(checked_nodes[i].id);
				}
			}

			if (residential.length > 0) {
				var url = '/couponResidential/save.html';
				var data = 'couponId=${vo.couponId}&residentialList='+residential;
				$.post(url, data, function(result) {
					if (result == 'success') {
						alert('保存成功');
						window.location.reload();
					} else {
						alert('保存失败');
					}
					btn_enable($("#btn_submit"));
				});
			}
		});

		$("#btn_expand").click(function () {
			var status = $(this).attr('data-status');
			if (status == 0) {
				$(this).attr('data-status', 1);
				zTree.expandAll(false);
			} else {
				$(this).attr('data-status', 0);
				zTree.expandAll(true);
			}
		});

		$("#btn_back").click(function () {
			window.history.back();
		});

	});
</script>
</body>
</html>