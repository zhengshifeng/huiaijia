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
	}

	#haj_ad img {
		width: 100%;
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

	#div_img {
		position: relative;
	}

</style>
</head>
<body>
<div id="weixin-tip">
	<p><img src="/style/images/wechat/guide_android.png" alt="微信打开" /></p>
</div>

<div id="haj_ad">
	<div id="div_img">
		<img alt="汇爱家" src="/style/images/wechat/download_bg_0713.jpg">
	</div>
</div>
<script type="text/javascript" src="/style/js/jquery.min.js"></script>
<script type="text/javascript">
var userAgent = navigator.userAgent;
var isIOS = userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var $winHeight = $(window).height(); // 当前屏幕高度
var $tip = $('#weixin-tip'); // 提示遮罩层
var $btn = $('#div_img');

// 判断是否使用微信中的浏览器
function isWeixin() {
	var ua = navigator.userAgent.toLowerCase();
	return ua.match(/MicroMessenger/i) == 'micromessenger';
}

// 应用宝下载APP
function yingyongbao() {
	window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.chengshe.huiaj";
}

function toAppStore() {
	window.location.href="http://itunes.apple.com/cn/app/hui-ai-jia/id1126408215?mt=8";
}

// 直接下载官网apk
function apkDownload() {
	window.location.href="http://cdn.huiaj.com/apk/huiaijia.apk";
}

$(function(){
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
		if (isWeixin()) { // 微信内打开
			$btn.click(function() {
				// android弹出使用浏览器打开的提示
				$tip.css('height', $winHeight);
				$tip.show();
				
				$tip.click(function() {
					$tip.hide();
				});
			});
		} else {
			// android浏览器打开自动从官网下载apk
			$('#haj_ad').find('img').load(function(){
				apkDownload();
			});
			
			$btn.click(function() {
				apkDownload();
			})
		}
	}
});
</script>
<%@ include file="cs.jsp" %>
<%
	CS cs = new CS(1259784215);
	cs.setHttpServlet(request,response);
	String imgurl = cs.trackPageView();
%>
<img src="<%= imgurl %>" width="0" height="0" />
</body>
</html>