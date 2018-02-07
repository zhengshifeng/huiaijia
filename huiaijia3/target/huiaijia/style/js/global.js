var UNKNOWN_ERROR = '未知异常...\n\r再试一次吧，如果还不行就问一下程序猿GG';

/**
 +----------------------------------------------------------
 * 下拉菜单
 +----------------------------------------------------------
 */
$(function () {
	$('.M').hover(function () {
			$(this).addClass('active');
		},
		function () {
			$(this).removeClass('active');
		});
});

/**
 +----------------------------------------------------------
 * 刷新验证码
 +----------------------------------------------------------
 */
function refreshimage() {
	var cap = document.getElementById('vcode');
	cap.src = cap.src + '?';
}

/**
 +----------------------------------------------------------
 * 无组件刷新局部内容
 +----------------------------------------------------------
 */
function dou_callback(page, name, value, target) {
	$.ajax({
		type: 'GET',
		url: page,
		data: name + '=' + value,
		dataType: "html",
		success: function (html) {
			$('#' + target).html(html);
		}
	});
}

/**
 +----------------------------------------------------------
 * 表单全选
 +----------------------------------------------------------
 */
function selectcheckbox(form) {
	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name != 'chkall' && e.disabled != true) e.checked = form.chkall.checked;
	}
}

/**
 +----------------------------------------------------------
 * 显示服务端扩展列表
 +----------------------------------------------------------
 */
function get_cloud_list(unique_id, get, localsite) {
	$.ajax({
		type: 'GET',
		url: 'http://cloud.douco.com/extend&rec=client',
		data: {'unique_id': unique_id, 'get': get, 'localsite': localsite},
		dataType: 'jsonp',
		jsonp: 'jsoncallback',
		success: function (cloud) {
			$('.selector').html(cloud.selector)
			$('.cloudList').html(cloud.html)
			$('.pager').html(cloud.pager)
		}
	});
}

/**
 +----------------------------------------------------------
 * 写入可更新数量
 +----------------------------------------------------------
 */
function cloud_update_number(localsite) {
	$.ajax({
		type: 'GET',
		url: 'http://cloud.douco.com/extend&rec=cloud_update_number',
		data: {'localsite': localsite},
		dataType: 'jsonp',
		jsonp: 'jsoncallback',
		success: function (cloud) {
			change_update_number(cloud.update, cloud.patch, cloud.module, cloud.plugin, cloud.theme, cloud.mobile)
		}
	});
}

/**
 +----------------------------------------------------------
 * 弹出窗口
 +----------------------------------------------------------
 */
function douFrame(name, frame, url) {
	$.ajax({
		type: 'POST',
		url: url,
		data: {'name': name, 'frame': frame},
		dataType: 'html',
		success: function (html) {
			$(document.body).append(html);
		}
	});
}

/**
 +----------------------------------------------------------
 * 显示和隐藏
 +----------------------------------------------------------
 */
function douDisplay(target, action) {
	var traget = document.getElementById(target);
	if (action == 'show') {
		traget.style.display = 'block';
	} else {
		traget.style.display = 'none';
	}
}

/**
 +----------------------------------------------------------
 * 清空对象内HTML
 +----------------------------------------------------------
 */
function douRemove(target) {
	var obj = document.getElementById(target);
	obj.parentNode.removeChild(obj);
}

/**
 +----------------------------------------------------------
 * 无刷新自定义导航名称
 +----------------------------------------------------------
 */
function change(id, choose) {
	document.getElementById(id).value = choose.options[choose.selectedIndex].title;
}

/**
 +----------------------------------------------------------
 * 激活当前菜单
 +----------------------------------------------------------
 */
function activeMenu(name) {
	$('.sub_menu', parent.document).removeClass('cur');
	$('#' + name, parent.document).addClass('cur');
}

/**
 +----------------------------------------------------------
 * 右侧iframe后退
 +----------------------------------------------------------
 */
function goBack() {
	window.history.back();
}

/**
 +----------------------------------------------------------
 * 右侧iframe前进
 +----------------------------------------------------------
 */
function goForward() {
	window.history.forward();
}

var interval;
var pass = true;
var invalidTime = 10 * 1000;
function time_sub() {
	this.invalidTime = this.invalidTime - 1000;
	if (this.invalidTime < 2000) {
		this.invalidTime = 10 * 1000;
		this.pass = true;
		clearInterval(this.interval);
	}
}

/**
 +----------------------------------------------------------
 * 刷新右侧iframe页面
 +----------------------------------------------------------
 */
function refresh() {
	var url = window.frames["mainFrame"].location.href;
	// window.frames["mainFrame"].location.reload();
	if (url.indexOf('villageCount.html') > -1) {
		if (this.pass) {
			layer.load(2);
			this.interval = setInterval('time_sub()', 1000);
			window.frames["mainFrame"].location.reload();
			this.pass = false;
		} else {
			layer.msg('您的操作过于频繁，请10秒后重试');
		}
	} else {
		window.frames["mainFrame"].location.reload();
	}
}

/**
 +----------------------------------------------------------
 * 必填字段前加*号
 +----------------------------------------------------------
 */
function prependRedStar() {
	var red_star = '<span class="red_star">*</span>';
	$('.required').each(function () {
		$(this).parent().prev().prepend(red_star)
	});
}

/**
 +----------------------------------------------------------
 * 检查必填字段
 +----------------------------------------------------------
 */
function requiredCheck() {
	var notNull = '请填写';
	var flag = true;
	$('.required').each(function () {
		if ($(this).is('select') && (!$(this).val() || $(this).val() == 0)) {
			$(parent.document.body).animate({ scrollTop: $(this).offset().top - 500});
			alert(notNull+$(this).parent().prev().find('label').text());
			flag = false;
			return false;
		} else {
			if (!$(this).val() || $(this).val() == 0) {
				$(this).focus();
				alert(notNull+$(this).parent().prev().find('label').text());
				flag = false;
				return false;
			}
		}
	});
	return flag;
}

/**
 +----------------------------------------------------------
 * 根据操作符格式化日期
 +----------------------------------------------------------
 */
function date_format(date, separator) {
	// 设置月份格式，个位数前补0
	var month = date.getMonth() + 1;
	if (month > 0 && month < 10) {
		month = '0' + month;
	}

	// 设置今日日期格式，个位数前补0
	var day = date.getDate();
	if (day > 0 && day < 10) {
		day = '0' + day;
	}

	return date.getFullYear() + separator + month + separator + day;
}

/**
 +----------------------------------------------------------
 * 禁用当前按钮
 +----------------------------------------------------------
 */
function btn_disable(btn) {
	$(btn).val('正在处理...');
	$(btn).attr('disabled', 'disabled');
}

/**
 +----------------------------------------------------------
 * 启用当前按钮
 +----------------------------------------------------------
 */
function btn_enable(btn) {
	$(btn).val($(btn).attr('title'));
	$(btn).removeAttr('disabled');
}

/**
 +----------------------------------------------------------
 * 关闭当前选项卡
 +----------------------------------------------------------
 */
function closeWindows() {
	var userAgent = navigator.userAgent;
	if (userAgent.indexOf("Firefox") > -1
		|| userAgent.indexOf("Chrome") > -1) {
		close();// webkit内核直接调用JQUERY close方法关闭
	} else {
		// IE
		window.opener = null;
		window.open("", "_self");
		window.close();
	}
}