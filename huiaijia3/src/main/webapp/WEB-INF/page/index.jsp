<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇爱家后台管理系统</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<link rel="stylesheet" href="../../style/plugins/zTree_v3/css/metroStyle/metroStyle.css?v=2" type="text/css">
<script type="text/javascript" src="../../style/plugins/zTree_v3/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="../../style/plugins/layer/layer.js"></script>
</head>
<body>
	<!--[if lt IE 9]><div class="browser-upgrade"><div class="wrapper">您的浏览器版本过低，为了保证更好的安全性和浏览体验，建议更换<a href="http://www.baidu.com/s?wd=谷歌浏览器">谷歌浏览器</a></div></div><style>body {padding-top: 40px;}.browser-upgrade {background-color: #DA7575;border-bottom: 1px solid #D65F39;color: #F0F0F0;padding: 10px 0;position: absolute;top: 0;left: 0;width: 100%;z-index: 10000;}.browser-upgrade .wrapper {width: 450px;margin: 0 auto;}.browser-upgrade a {color: yellow;margin-right: 10px;text-decoration: underline;}</style><![endif]-->
	<div id="dcWrap">
		<div id="dcHead">
			<div id="head">
				<div class="logo">
					<a href="index.html">
						<img src="../../style/images/logo.png" alt="logo">
					</a>
				</div>
				<div class="nav">
					<ul>
						<li>
							<a href="javascript:;" onclick="toggleLeft(this);">隐藏菜单栏</a>
						</li>
						<li>
							<a href="javascript:;" onclick="goBack();">后退</a>
						</li>
						<li>
							<a href="javascript:;" onclick="goForward()">前进</a>
						</li>
						<li class="noRight">
							<a href="javascript:;" onclick="refresh();">刷新${user.roleId}</a>
						</li>
					</ul>
					<ul class="navRight">
						<c:if test="${iCanChangeManageCity}">
						<li>
							<select id="areaCode">
								<option value="">所有城市</option>
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code}">${list.name}</option>
								</c:forEach>
							</select>
						</li>
						</c:if>
						<li class="noLeft">
							<a href="javascript:;">您好，${user.username }</a>
						</li>
						<li class="noRight">
							<a href="javascript:;" onclick="logout()">退出</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- dcHead 结束 -->
		<div id="dcLeft">
			<ul id="tree" class="ztree" style="overflow:auto;"></ul>
		</div>
		<div id="dcMain">
			<iframe name="mainFrame" id="mainFrame" src="default.html" onLoad="reinitIframe()"
				style="width: 100%; border: 0;"></iframe>
		</div>
		<div class="clear"></div>
		<div id="dcFooter">
			<div id="footer">
				<div class="line"></div>
				<div class="copyright">Copyright © 汇爱家后台管理系统</div>
			</div>
		</div>
		<!-- dcFooter 结束 -->
		<div class="clear"></div>
	</div>

	<script type="text/javascript">
		var closeLeft = true;
		var iframe = document.getElementById("mainFrame");

		// 使iframe自适应大小显示
		function reinitIframe() {
			try {
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				iframe.height = Math.max(bHeight, dHeight);
				if (iframe.height < 768) {
					iframe.height = 768;
				}
			} catch (ex) {
			}

			// 每次iframe加载完后让页面回到顶部
			$('html, body').animate({
				scrollTop : 0
			}, 0);
		}

		function logout() {
			if (confirm("确定要退出吗？")) {
				document.location = "/logout.html";
			}
		}

		//===================================
		// 左侧菜单栏开关
		//===================================
		function toggleLeft(obj) {
			if (closeLeft) {
				$("#dcLeft").css("display", "none");
				$("#dcMain").css("margin-left", "0px");
				$(obj).html("打开菜单栏");
				closeLeft = false;
			} else {
				$("#dcLeft").css("display", "block");
				$("#dcMain").css("margin-left", "179px");
				$(obj).html("隐藏菜单栏");
				closeLeft = true;
			}
		}

		$(function() {
			var locationHref = window.location.href;
			if ((locationHref.indexOf("115.29.170.224") > -1)) {
				document.title = "测试环境-后台";
			} else if ((locationHref.indexOf("localhost") > -1) || (locationHref.indexOf("192.168.") > -1)) {
				document.title = "localhost";
			}

			<c:if test="${iCanChangeManageCity}">
			$('#areaCode').change(function () {
				var url = '/changeManageCity.html?areaCode='+$(this).val();
				$.get(url, function(data) {
					if (data.error == 0) {
						window.frames["mainFrame"].location.reload();
					} else {
						alert(data.msg);
					}
				});
			});

			$('#areaCode').val('${user.areaCode}');
			</c:if>
		});
	</script>
	<script type="text/javascript">
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: menu_click
			}
		};

		var zTreeNodes = eval('${zTreeNodes}');
		$.fn.zTree.init($("#tree"), setting, zTreeNodes);

		function menu_click(e, treeId, node) {
			if (node.level !== 0) {
				if (node.menuUrl.indexOf('villageCount.html') > -1) {
					if (pass) {
						layer.load(2);

						interval = setInterval('time_sub()', 1000);
						iframe.src = node.menuUrl;
						pass = false;
					} else {
						layer.msg('您的操作过于频繁，请10秒后重试');
					}
				} else {
					iframe.src = node.menuUrl;
				}
			}
		}
	</script>
</body>
</html>