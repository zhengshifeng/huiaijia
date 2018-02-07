<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../style/js/jquery.min.js"></script>
<script type="text/javascript" src="../../style/js/global.js"></script>
<title>进入后台后的欢迎页面</title>
</head>
<body>
	<div style="margin-top: 150px; text-align: center; font-size: 26px;">
		<b class="welcome_word">欢迎进入汇爱家后台管理系统！</b>
	</div>
	<script type="text/javascript">
		if (window.navigator.userAgent.indexOf("Chrome") < 0) {
			$('.welcome_word').append('<br /><br /><span style="color: red;">推荐使用 <a href="http://www.baidu.com/s?wd=谷歌浏览器" target="_blank">谷歌浏览器</a> 访问本系统</span>');

			window.parent.layer.msg('推荐使用谷歌浏览器访问本系统', {anim: 1});
		}
	</script>
</body>
</html>