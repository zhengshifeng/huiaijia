<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" 
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="pragma" content="no-cache" />
<title>汇爱家</title>
<style type="text/css">
html, body{
	padding: 0;
	margin: 0;
}

#haj_ad {
	position: absolute;
	display: none;
}

#haj_ad img {
    display:block;
	width: 100%;
	/* height: 100%; */
}

#community {
	font-size: 10.7vw;
	position: absolute;
	bottom: 22.3%;
	left: 5px;
	z-index: 3;
	font-weight: bolder;
	color: yellow;
}

#btn_join_us {
	font-size: 10.7vw;
	position: absolute;
	bottom: 5%;
	z-index: 3;
	font-weight: bolder;
	color: black;
	text-align: center;
	width: 94%;
	left: 4%;
}

/* 微信遮罩层 */
#weixin-tip {
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	background: rgba(0, 0, 0, 0.7);
	filter: alpha(opacity = 80);
	width: 100%;
	height: 100%;
	z-index: 100;
}

#weixin-tip p {
	text-align: center;
	margin-top: 10%;
	padding: 0 5%;
	position: relative;
}

#weixin-tip p img {
	max-width: 100%;
	height: auto;
}

#weixin-tip .close {
	color: #fff;
	padding: 5px;
	font: bold 20px/24px simsun;
	text-shadow: 0 1px 0 #ddd;
	position: absolute;
	top: 0;
	left: 5%;
}

</style>
</head>
<body>
	<div id="weixin-tip">
		<p><img src="/style/images/wechat/guide_android.png" alt="微信打开" /></p>
	</div>
	
	<div id="haj_ad">
	<!-- <img alt="汇爱家" src="/style/images/wechat/share.jpg?v=20160622"> -->	


		<img alt="汇爱家" src="/style/images/wechat/share_1.png">
		<img alt="汇爱家" src="/style/images/wechat/share_2.png">
		<img alt="汇爱家" src="/style/images/wechat/share_3.png">
		<img alt="汇爱家" src="/style/images/wechat/share_4.png">
		<img alt="汇爱家" src="/style/images/wechat/share_5.png">
		<img alt="汇爱家" src="/style/images/wechat/share_6.png">
		<img alt="汇爱家" src="/style/images/wechat/share_7.png">
		<img alt="汇爱家" src="/style/images/wechat/share_8.png">
		<img alt="汇爱家" src="/style/images/wechat/share_9.png">
		<img alt="汇爱家" src="/style/images/wechat/share_10.png">
		
		
		
		<span id="community"></span>
		<span id="btn_join_us">立即体验</span>
	</div>

<script type="text/javascript" src="/style/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	var userAgent = navigator.userAgent;
	var isIOS = userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var $winHeight = $(window).height(); // 当前屏幕高度
	var $btn = $('#btn_join_us'); // 立即体验按钮
	var $tip = $('#weixin-tip'); // 提示遮罩层
	var $community = $('#community'); // 显示小区名的区域
	var community = '${community}';
	
	// 使文字样式自适应屏幕
	function autoCommunityStyle() {
		var len = community.length;
		var fontSize = '5vw';
		if (len > 9) {
			fontSize = '5vw';
		} if (len == 9) {
			fontSize = '6vw';
		} else if (len == 8) {
			fontSize = '6.5vw';
		} else if (len == 7) {
			fontSize = '7.8vw';
		} else if (len == 6) {
			fontSize = '9vw';
		} else if (len == 5) {
			fontSize = '10.7vw';
		} else if (len == 4) {
			fontSize = '13vw';
		} else if (len < 4) {
			fontSize = '15vw';
		}
		$community.html(community);
		$community.css('font-size', fontSize);
	}
	
	// 判断是否使用微信中的浏览器
	function isWeixin() {
		var ua = navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
	
	// 判断是否在移动端打开
	function isMobile() {
		var agents = new Array("Android", "iPhone", "iPad", "iPod");
		var flag = false;
		for (var v = 0; v < agents.length; v++) {
			if (userAgent.indexOf(agents[v]) > 0) { flag = true; break; }
		}
		return flag;
	}
	
	// 应用宝下载APP
	function yingyongbao() {
		window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.chengshe.huiaj";
	}
	
	function toAppStore() {
		window.location.href="http://itunes.apple.com/cn/app/hui-ai-jia/id1126408215?mt=8";
	}

	// 直接下载官网apk
	function apkdownload() {
		window.location.href="http://www.huiaj.com/apk/huiaijia.apk";
	}
	
	if (isMobile()) {
		autoCommunityStyle();
		// 加载完字体再显示
		$('#haj_ad').css('display', 'block');
		
		if (isIOS) { // IOS移动端
			$btn.click(function() {
				if (isWeixin()) {
					// IOS使用应用宝中转然后自动跳转至app store
					yingyongbao();
				} else {
					// IOS中用浏览器打开此页面直接跳转至app store
					toAppStore();
				}
			});
		} else { // android移动端
			$btn.click(function() {
				if (isWeixin()) { // 微信内打开
					// android弹出使用浏览器打开的提示
					$tip.css('height', $winHeight);
					$tip.show();
					
					$tip.click(function() {
						$tip.hide();
					});
				} else {
					// android浏览器打开自动从官网下载apk
					apkdownload();
				}
			});
		}
	} else {
		// 非移动端打开直接跳转应用宝，避免出现样式问题
		$('#haj_ad').empty();
		yingyongbao();
	}

});
</script>
</body>
</html>