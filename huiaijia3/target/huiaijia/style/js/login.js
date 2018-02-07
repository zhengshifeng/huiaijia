$(function() {
	
	function validateLogin() {
		var loginname = $.trim($("#loginname").val());
		var password = $.trim($("#password").val());
		if (!loginname) {
			$("#loginname").focus();
		} else if (!password) {
			$("#password").focus();
		} else {
			login();
		}
	}
	
	function login() {
		var url = '/login.html';
		$.post(url, $('#loginForm').serialize(), function(data){
			if (data.msg == 'success') {
				window.location.href = '/index.html';
			} else {
				alert(data.msg);
			}
		});
	}
	
	// 输完密码把手拿开
	$("#password").focus(function() {
		$("#left_hand").animate({
			left : "150",
			top : " -38"
		}, {
			step : function() {
				if (parseInt($("#left_hand").css("left")) > 140) {
					$("#left_hand").attr("class", "left_hand");
				}
			}
		}, 2000);
		$("#right_hand").animate({
			right : "-64",
			top : "-38px"
		}, {
			step : function() {
				if (parseInt($("#right_hand").css("right")) > -70) {
					$("#right_hand").attr("class", "right_hand");
				}
			}
		}, 2000);
	});
	
	// 输密码时把眼睛遮住
	$("#password").blur(function() {
		$("#left_hand").attr("class", "initial_left_hand");
		$("#left_hand").attr("style", "left: 100px; top: -12px;");
		$("#right_hand").attr("class", "initial_right_hand");
		$("#right_hand").attr("style", "right: -112px; top: -12px");
	});
	
	// 输完密码后的回车事件
	$("#password").keypress(function(e) {
		if (e.keyCode == 13) {
			$('#btn_login').click();
		}
	});
	

	/*===================================================================*/
	/*添加登录校验码 start*/
	var locationUrl = window.location.href;
	if (locationUrl.indexOf("112.124.118.24") > -1) {
		var handlerPopup = function(captchaObj) {
			captchaObj.onSuccess(function () {
				var result = captchaObj.getValidate();
				if (!result) {
					return alert('请完成验证');
				}
				$.ajax({
					url: 'VerifyLoginServlet',
					type: 'POST',
					dataType: 'json',
					data: {
						geetest_challenge: result.geetest_challenge,
						geetest_validate: result.geetest_validate,
						geetest_seccode: result.geetest_seccode
					},
					success : function(data) {
						if (data && (data.status === "success")) {
							validateLogin();
						} else {
							alert("验证失败");
						}
					}
				});
			});
			$("#btn_login").click(function() {
				captchaObj.verify();
			});
		};

		$.ajax({
			// 获取id，challenge，success（是否启用failback）
			url : "StartCaptchaServlet?t=" + (new Date()).getTime(),
			type : "get",
			dataType : "json",
			success : function(data) {
				// 调用 initGeetest 初始化参数
				// 参数1：配置参数
				// 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
				initGeetest({
					gt: data.gt,
					challenge: data.challenge,
					new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
					offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
					product: "bind", // 产品形式，包括：float，popup
					width: "100%"
					// 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
				}, handlerPopup);
			}
		});
	} else {
		$('#btn_login').click(function(){
			var loginname = $.trim($("#loginname").val());
			var password = $.trim($("#password").val());
			if (!loginname) {
				$("#loginname").focus();
			} else if (!password) {
				$("#password").focus();
			} else {
				login();
			}
		});
	}
		
	/*添加登录校验码 end*/
	/*===================================================================*/
});