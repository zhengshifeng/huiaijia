<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="renderer" content="webkit"/>
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="generator" content="MSHTML 11.00.9600.17496"/>
	<title>登录页面</title>
	<script type="text/javascript">
		if (top.document.getElementById("mainFrame")) {
			top.document.location.href = window.location.href;
		}
	</script>
	<link href="../../style/css/login.css" rel="stylesheet" type="text/css"/>
	<script src="../../style/js/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<!--[if lt IE 9]><div class="browser-upgrade"><div class="wrapper">您的浏览器版本过低，为了保证更好的安全性和浏览体验，建议更换<a href="http://www.baidu.com/s?wd=谷歌浏览器">谷歌浏览器</a></div></div><style>body {padding-top: 40px;}.browser-upgrade {background-color: #DA7575;border-bottom: 1px solid #D65F39;color: #F0F0F0;padding: 10px 0;position: absolute;top: 0;left: 0;width: 100%;z-index: 10000;}.browser-upgrade .wrapper {width: 450px;margin: 0 auto;}.browser-upgrade a {color: yellow;margin-right: 10px;text-decoration: underline;}</style><![endif]-->
<form name="loginForm" id="loginForm">
	<div class="top_div"></div>
	<div class="main_div">
		<div class="maotouying_div">
			<div class="tou"></div>
			<div class="initial_left_hand" id="left_hand"></div>
			<div class="initial_right_hand" id="right_hand"></div>
		</div>
		<p style="padding: 30px 0px 10px; position: relative;">
			<span class="u_logo"></span>
			<input class="ipt" type="text" id="loginname" name="loginname" placeholder="帐号"/>
		</p>
		<p style="position: relative;">
			<span class="p_logo"></span>
			<input class="ipt" id="password" name="password" type="password" placeholder="又不看你密码(￣.￣)"/>
		</p>
		<div class="btn_div">
			<p style="margin: 0px 35px 20px 45px;">
				<a href="javascript:;" id="btn_login">登录</a>
			</p>
		</div>
		<div id="popup-captcha"></div>
	</div>
</form>

<!-- 引入封装了failback的接口--initGeetest -->
<script src="../../style/plugins/geetest/gt.js"></script>

<script src="../../style/js/login.js?v=3.6.0" type="text/javascript"></script>

<script type="text/javascript">
	$(function () {
		// 测试环境自动登录
		var locationUrl = window.location.href;
		if ((locationUrl.indexOf("localhost") > -1) //
			|| (locationUrl.indexOf("192.168.0") > -1) //
			|| (locationUrl.indexOf("115.29.170.224") > -1)) {
//			$('#loginname').val('sz');
			$('#loginname').val('admin');
			$('#password').val('admin');
			//$('#btn_login').click();
		}
	});
</script>
</body>
</html>
