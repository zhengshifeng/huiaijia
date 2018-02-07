<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加订单</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<link href="../../style/css/order.css?v=20170513" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		.hidden_1, .is_type, .type_shu, .user_areaCode {
			visibility: hidden;
		}
	</style>
</head>
<body style="background: #fff">
<div class="mainBox" style="min-height: 180px;">
	<h3>
		<a href="http://cdn.huiaj.com/xlsx/补单模板.xlsx" class="actionBtn">下载补单模板</a>
		批量补单
	</h3>
	<div class="filter" style="float: left; width: 100%;">
		<input type="file" id="uploadFile" accept=".xlsx"/>
		<button id="importFile" style="width: 120px;">批量导入补单</button>
	</div>
	<div class="filter" style="float: left;">
		<span class="batchImportTip text_red"></span>
	</div>
</div>
<div class="mainBox">
	<!--头部-->
	<div class="head">
		<h3 class="biaoTi">
			单用户补单<span class="explain">说明：下单时间0点至18点。请务必查询该用户是否已经下了补单了，请勿重复补单。</span>
		</h3>
	</div>

	<!--查找用户-->
	<div class="search">
		<input type="text" class="phone" name="phone" placeholder="手机号码"/>
		<button id="search">查找</button>
		<span class="explain budan_tip"></span>
	</div>
	<input id="nowdate" class="hidden_1" name="nowdate" value="${nowdate}">
	<!--确认用户信息-->
	<div class="confirmation">
		<span>请确认客户信息：</span>
		<table>
			<thead>
			<tr>
				<th>用户id</th>
				<th>注册手机号码</th>
				<th>小区</th>
				<th>用户地址</th>
				<th>收货人</th>
				<th>用户角色</th>
				<th class="type_shu">用户数字</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="user_id"></td>
				<td class="user_phone"></td>
				<td class="user_residential"></td>
				<td class="user_address"></td>
				<td class="user_receiver"></td>
				<td class="is_member"></td>
				<td class="is_type"></td>
				<td class="user_areaCode"></td>
			</tr>
			</tbody>
		</table>

	</div>
	<!--添加商品、删除-->
	<div class="details">
		<span>补单商品信息：</span>
		<table class="totalQetails">
			<thead>
			<tr>
				<th><input type="checkbox" class="allXuan"/><span>全选</span></th>
				<th>商品名称</th>
				<th>份数</th>
				<th>商品单价</th>
				<th>补单原因</th>
				<th>扣除余额</th>
				<th class="hidden_1">汇爱家价</th>
				<th class="hidden_1">是否团购</th>
				<th class="hidden_1">商品存在</th>
			</tr>
			</thead>
			<tbody>
			<tr class="trD">
				<td>
					<input type="checkbox" class="danXuan" name="items"/>
					<span class="geShu">1</span>
				</td>
				<td>
					<input class="search_good _1" type="text" name="name" data-id="1" placeholder="请输入"/>
				</td>
				<td class="number">
					<input type="text" class="number _1" data-id="1" value="1"  onkeyup="numberKeyup(this)"/>
				</td>
				<td class="danJia"><input type="text" value="0"/></td>
				<td>
					<select class="budan_reason _1" data-id="1" data-name="" data-number="1" onchange="reasonChange(this)">
						<option value="">请选择</option>
						<option value="0">质量问题</option>
						<option value="1">仓库缺货</option>
					</select>
				</td>
				<td class="danZhongJ">0.00</td>
				<td class="huajJ hidden_1"></td>
				<td class="tuanGouLei hidden_1"></td>
				<td class="cunZai hidden_1"></td>
			</tr>
			</tbody>
		</table>
		<button class="addCommodity">继续添加商品</button>
		<button class="delete1">删除勾选的商品</button>
	</div>
	<!--总结份数和金额-->
	<div class="zongJie">
		<div class="geShu">
			商品种类：<span class="numTotal">1</span>种
		</div>
		<div class="TotalMoney">
			扣取金额：<span>0.00</span>元
		</div>
	</div>
	<!--备注-->
	<div class="Remarks">
		<p class="beiZhu">备注：</p>
		<textarea class="textArea remark" cols="30" rows="5" placeholder="必填"></textarea>

		<div class="wanCheng">
			<p class="peple">提交人：${user.username}</p>
			<input type="button" id="btn_submit" class="Submit" value="确认提交" title="确认提交" />
		</div>
		<input type="hidden" id="areaCode" value="${user.areaCode}">
	</div>
</div>

<script type="text/javascript">
	var flag = 1;
	var geShuNum = 1;// 个数是否正确
	var danjiaNum = 1;// 单价是否正确
	/* 数量 是否为负 */

	function creatGeNum(ary) {
		var ary_1 = new Array();
		for (var i = 0; i < ary.length; i++) {
			var cur = ary[i];
			if (cur > 0) {
				ary_1.push("2");
			} else {
				ary_1.push("1");
			}
			if (ary_1.indexOf("1") > -1) {
				geShuNum = 1;
				return;
			} else {
				geShuNum = 2;
			}

		}
	}
	/* 单价 是否为负 */
	function creatDanNum(ary) {
		var ary_1 = new Array();
		for (var i = 0; i < ary.length; i++) {
			var cur = ary[i];
			if (cur >= 0) {
				ary_1.push("2");
			} else {
				ary_1.push("1");
			}
			if (ary_1.indexOf("1") > -1) {
				danjiaNum = 1;
				return;

			} else {
				danjiaNum = 2;
			}

		}
	}

	// 封装input失去焦点时
	function creatDanJa() {
		$(".number input").focus(function () {
			if ($(this).val() == this.defaultValue) {
				$(this).val("");
			}
		}).blur(function () {
			if ($(this).val() == '') {
				$(this).val(this.defaultValue);
			} else {
				var number = $(this).val();// 商品个数
				if (number > 0) {
					var danJia_1 = $(this).parents("tr").find(".danJia input").val();// 商品单价
					var danJia_11 = parseFloat(danJia_1) * 100;// 当单价出现小数时避免出错乘100；
					var danZhongJ = parseFloat(number) * danJia_11;// 一份商品的总价
					var danZhongJ_1 = danZhongJ / 100;// 缩小100
					var danZhongJ_11 = danZhongJ_1.toFixed(2);// 保留两位小数
					$(this).parents("tr").find(".danZhongJ").text(danZhongJ_11);// 放到扣除余额里边；
				} else {
					// 商品数量不能小于1份
					$(this).val(1);
					return;
				}

			}
			creatTotalP();
		});
		$(".danJia input").focus(function () {
			if ($(this).val() == this.defaultValue) {
				$(this).val("");
			}
		}).blur(function () {
			if ($(this).val() == '') {
				$(this).val(this.defaultValue);
			} else {
				var danJia_1 = $(this).val();// 商品单价
				if (danJia_1 >= 0) {
					var danJia_11 = parseFloat(danJia_1) * 100;// 当单价出现小数时避免出错乘100；
					var number = $(this).parents("tr").find(".number input").val();// 商品个数
					var danZhongJ = parseFloat(number) * danJia_11;
					var danZhongJ_1 = danZhongJ / 100;// 缩小100;
					var danZhongJ_11 = danZhongJ_1.toFixed(2);// 保留两位小数
					$(this).parents("tr").find(".danZhongJ").text(danZhongJ_11);// 放到扣除余额里边
				} else {
					alert("请查看商品单价是否正确  ");
					return;
				}
			}
			creatTotalP();
		})
		/* 一商品名称获取后台数据 */
		$(".search_good").focus(function () {
			if ($(this).val() == this.defaultValue) {
				$(this).val("");
			}
		}).blur(function () {
			if ($(this).val() == '') {
				$(this).val(this.defaultValue);
			} else {
				// 去掉前后空格
				var name = $.trim($(this).val());

				// 去空格后的值重新赋值
				$(this).val(name);
				$(this).parents(".totalQetails").find("tr").removeClass("selected_1");

				$(this).parents("tr").addClass("selected_1 ");

				var dataId = $(this).attr('data-id');
				var userAreaCode =	$(".user_areaCode").html();
		
				$.ajax({
					// 获取id，challenge，success（是否启用failback）
					url: "/commodity/commoidtyByName.html",
					type: "post",
					dataType: "json",
					data: {'name': name,'areaCode':userAreaCode},
					success: function (data) {
						var panDuan = data.error;
						var data_1 = data.commodity;

						if (panDuan == "0") {
							var originalPrice = data_1.originalPrice; // 汇爱家价格

							var commodityAttr = data_1.commodityAttr; // 类型

							if (commodityAttr == "团购") {
								var isGrouponOrder = "3";
							} else if (commodityAttr == "水果") {
								var isGrouponOrder = "2";
							} else if (commodityAttr == "生鲜") {
								var isGrouponOrder = "1";
							}
							$(".selected_1").find(".huajJ").html(originalPrice);// 汇爱家价格
							$(".selected_1").find(".tuanGouLei").html(isGrouponOrder);// 类型
							$(".selected_1").find(".cunZai").html(panDuan);// 汇爱家价格

							// 补单原因自定义属性（商品名），用于自动填写备注
							$('.budan_reason._' + dataId).attr('data-name', name);
							flag = 2;
						} else {
							flag = 1;
							alert("没有此商品");
							return;
							$(".selected_1").find(".cunZai").html(panDuan);// 汇爱家价格
						}
					}
				});
			}
		})
	}
	creatDanJa();
	// 计算商品总金额
	function creatTotalP() {
		var tdList = $(".totalQetails .trD").length;
		var aryList = new Array();
		for (var i = 0; i < tdList; i++) {
			var goodMoney = parseFloat($(".totalQetails .trD").eq(i).find(".danZhongJ ").html());
			aryList.push(goodMoney);
		}
		var totalPrice_11 = totalAry(aryList);
		$(".TotalMoney").find("span").text(totalPrice_11);
	}
	// 排序
	function creatPaiXu() {
		var tdList = $(".totalQetails .trD").length;
		for (var i = 0; i < tdList; i++) {
			var j = i + 1;
			$(".totalQetails .trD").eq(i).find("td:first-child .geShu").html(j);
		}
	}

	// 添加一行
	$(".addCommodity").click(function () {
		var rowShu = $(".totalQetails .trD:last-child").find("span").text();
		var rowShu_1 = parseFloat(rowShu);
		if (rowShu_1 > 0) {
			var rowShu_1 = rowShu_1;
		} else {
			rowShu_1 = 0;
		}

		// 每行的index
		var _i = rowShu_1 + 1;

		var html = "";
		html += '<tr class="trD">'
			+ '<td>'
			+ '	<input type="checkbox" class="danXuan" name="items" />'
			+ '	<span class="geShu">' + _i + '</span>'
			+ '</td>'
			+ '<td><input class="search_good _' + _i + '" type="text" name="name" data-id="' + _i + '" placeholder="请输入" /></td>'
			+ '<td class="number"><input type="text" class="number _' + _i + '" data-id="' + _i + '" value="1" onkeyup="numberKeyup(this)"/></td>'
			+ '<td class="danJia"><input type="text" value="0" /></td>'
			+ '<td>'
			+ '	<select class="budan_reason _' + _i + '" data-id="' + _i + '" data-name="" data-number="1" onchange="reasonChange(this)">'
			+ '		<option value="">请选择</option>'
			+ '		<option value="0">质量问题</option>'
			+ '		<option value="1">仓库缺货</option>'
			+ '	</select>'
			+ '</td>'
			+ '<td class="danZhongJ">0.00</td>'
			+ '<td class="huajJ hidden_1"></td>'
			+ '<td class="tuanGouLei hidden_1"></td>'
			+ '<td class="cunZai hidden_1"></td>'
			+ '</tr>';

		$(".totalQetails").append(html);
		// 商品个数
		var aa = $(".totalQetails .trD").length;
		$(".numTotal").text(+aa);
		creatDanJa();
		creatQuanXuan();
	});

	// 删除一行
	$(".delete1").click(function () {
		$('[name=items]:checkbox:checked').parents("tr").detach();
		// 商品个数
		var aa = $(".totalQetails tr").length - 1;
		$(".numTotal").text(+aa);
		creatTotalP();
		creatPaiXu();
		$(".allXuan").attr("checked", false);// 取消全选选中
	})

	// 全选
	function creatQuanXuan() {
		$(".allXuan").click(function () {
			$('.danXuan').attr("checked", this.checked);
		});
		$('[name=items]:checkbox').click(function () {
			var $tmp = $('[name=items]:checkbox');
			$('.allXuan').attr('checked', $tmp.length == $tmp.filter(':checked').length);
		});
	}

	creatQuanXuan();

	/* =================================================================== */
	/* 数组求和 函数 */
	function totalAry(ary) {
		var total = 0;
		for (var i = 0; i < ary.length; i++) {
			var cur = ary[i];
			var cur_1 = parseFloat(cur) * 100;
			total += cur_1;
		}
		var total_1 = total / 100;
		var total_11 = total_1.toFixed(2);
		return total_11;
	}
	function totalAryShu(ary) {
		var total = 0;
		for (var i = 0; i < ary.length; i++) {
			var cur = parseFloat(ary[i]);
			total += cur;
		}
		return total;
	}

	/**
	 * 检查该用户今日是否已有待配送的补单
	 */
	function checkTodayOrders(mobilePhone) {
		var budanTip = '该用户有待配送补单，请核实，避免重复补单。';
		$.ajax({
			url: "/order/getBuDanByMobile.html?mobile=" + mobilePhone,
			type: "get",
			dataType: "json",
			success: function (data) {
				if (data && data.msg == 'success') {
					$('.explain.budan_tip').html(budanTip + '<a href="/order/orderDetail.html?orderId=' + data.order.id
						+ '" target="_blank">点击查看该订单详情</a>');
				} else {
					$('.explain.budan_tip').empty();
				}
			}
		});
	}

	/* =================================================================== */
	// 点击查找
	$("#search").click(function () {
		var phone = $(".phone").val();

		$.ajax({
			url: "/hajUserInfo.html?userCondition=" + phone,
			type: "get",
			dataType: "json",
			success: function (data) {
				var user = data.user;
				var msg = data.error;

				if (msg == "0") {
					var id = user.id;
					var mobilePhone = user.mobilePhone;
					var residential = user.residential;
					var address = user.address;
					var receiver = user.receiver;
					var ismember = user.ismember;
					var areaCode = user.areaCode;

					if (ismember == 1) {
						var state = "仅注册";
					} else if (ismember == 2) {
						var state = "预备会员";
					} else if (ismember == 3) {
						var state = "一元购会员";
					} else if (ismember == 4) {
						var state = "会员取消";
					} else if (ismember == 5) {
						var state = "普通会员";
					}
					$(".user_id").html(id);
					$(".user_phone").html(mobilePhone);
					$(".user_residential").html(residential);
					$(".user_receiver").html(receiver);
					$(".user_address").html(address);
					$(".is_member").html(state);
					$(".is_type").html(ismember);
					$(".user_areaCode").html(areaCode);
					checkTodayOrders(phone);

				} else if (msg == "1") {
					alert("请输入正确的手机号码 ")
					return;
				}

			},

		});
	})
	/* 点击查找结束 */
	/* =================================================================== */
	/* 点击提交按钮 */

	creatSubmit()

	function creatSubmit() {
		$(".Submit").click(function () {
			btn_disable($("#btn_submit"));
			/* =================================================================== */
			var sendTime = $("#nowdate").val();// 获取当天时间start
			/* =================================================================== */
			/* 商品列表的信息 */
			var trList = $(".totalQetails").find(".trD ");
			var aryList = new Array();// 商品列表
			var obj = new Object();
			var aryList_type = new Array();// 商品类型
			var aryList_price = new Array();// 汇爱家价格
			var dianJiaTotal = new Array();// 单价数组判断1元购
			var fenShuTotal = new Array();// 总份数
			var chunZai = new Array();// 商品是否存在

			for (var i = 0; i < trList.length; i++) {
				var name = $(".totalQetails .trD").eq(i).find("td").eq(1).find("input").val();
				name = $.trim(name);// 去掉空格
				var number = $(".totalQetails .trD").eq(i).find("td").eq(2).find("input").val();
				var danJia = $(".totalQetails .trD").eq(i).find("td").eq(3).find("input").val();
				var budanReason = $(".totalQetails .trD").eq(i).find("td").eq(4).find("select").val();
				var danZhongJ = $(".totalQetails .trD").eq(i).find("td").eq(5).html();
				var huiajJ = $(".totalQetails .trD").eq(i).find("td").eq(6).html();
				var tuanG = $(".totalQetails .trD").eq(i).find("td").eq(7).html();
				var cunZai_1 = $(".totalQetails .trD").eq(i).find("td").eq(8).html();

				if (!name) {// 商品名称为空
					alert("包含商品名称为空的商品 ");
					btn_enable($("#btn_submit"));
					return;
				}
				chunZai.push(cunZai_1);// 把是否存在类别放到数组了
				fenShuTotal.push(number);// 份数数组
				dianJiaTotal.push(danJia);// 总单价数组
				aryList_price.push(huiajJ);// 汇爱家价格
				aryList[i] = obj;
				obj["commodityName"] = name;// 名字
				obj["number"] = number;// 个数
				obj["commodityListPrice"] = danJia;// 单价
				obj["actualPayment"] = danZhongJ;// 总价
				obj["source"] = 0;
				obj["allSource"] = 1;
				obj["commodityType"] = tuanG;// 商品类型
				obj["remark3"] = budanReason;
				obj = new Object();
			}
			/* =================================================================== */

			var receiver_1 = $(".user_receiver").html();// 收货人
			var phone_1 = $(".user_phone").html();// 电话
			var dinDanType = $(".is_type").html();// 订单状态
			var residential_1 = $(".user_residential").html();// 小区
			var id_1 = $(".user_id").html();// id
			var address_1 = $(".user_address").html();// 地址
			var textArea_1 = $(".textArea").val() + '~~ by:${user.username}~~';// 备注

			var actualPayment_1 = $(".TotalMoney").find("span").text();// 总消费价
			var userAreaCode =	$(".user_areaCode").html();
			var adminAreaCode=	$("#areaCode").val();
			
			if(adminAreaCode!='' && userAreaCode!=adminAreaCode)
			{
				alert("当前操作员没有权限补此用户订单！");
				btn_enable($("#btn_submit"));
				return;
			}
			
			if (id_1 != null && id_1 != " ") {
				var id_1 = id_1;
			} else {
				alert("用户信息为空 ");
				btn_enable($("#btn_submit"));
				return;
			}
			if (dinDanType == 1) {
				alert("仅注册用户");
				btn_enable($("#btn_submit"));
				return;
			}
			if (dinDanType == 2) {
				alert("预备会员用户");
				btn_enable($("#btn_submit"));
				return;
			}
			if (dinDanType == 4) {
				alert("会员取消用户 ");
				btn_enable($("#btn_submit"));
				return;
			}
			/* =================================================================== */
			/* 总份数 */
			var numberS = totalAryShu(fenShuTotal);// 份数
			/* 汇爱家价格总和 */
			var huiajPrice = 0;
			for (var i = 0; i < aryList_price.length; i++) {
				var price_haj = aryList_price[i];
				var fenNmu = fenShuTotal[i];
				var p_haj = parseFloat(price_haj) * 100;
				var danHuaiJ_1 = fenNmu * p_haj;
				huiajPrice += parseFloat(danHuaiJ_1);
			}
			var huiajPrice = huiajPrice / 100;
			var huiajPrice = huiajPrice.toFixed(2);
			/* 总优惠 */
			var youHui_1 = parseFloat(huiajPrice) - parseFloat(actualPayment_1);
			var youHui = youHui_1.toFixed(2);
			/* =================================================================== */
			/* 判断1元购 */
			if (dianJiaTotal.indexOf("1") > -1) {
				var yiYUanGou = "1";
			} else {
				var yiYUanGou = "0";
			}
			/* 判断1元购end */
			/* =================================================================== */
			var objectShu = new Object();
			sendTime = sendTime.substring(0, 4) + "年" + sendTime.substring(5, 7) + "月" + sendTime.substring(8, 10)
				+ "日" + "7点0分";

			/* 赋值 */
			objectShu["deliveryTime"] = sendTime;// 送达时间赋值
			objectShu["points"] = 0;// 0
			objectShu["receiver"] = receiver_1;// 收货人
			objectShu["dispensingTip"] = 0;// 0
			objectShu["contactPhone"] = phone_1;// 电话
			objectShu["residential"] = residential_1;// 小区
			objectShu["userId"] = id_1;// id
			objectShu["address"] = address_1;// 地址
			objectShu["remark"] = textArea_1;// 备注
			objectShu["actualPayment"] = actualPayment_1;// 总消费价
			objectShu["orderDetailList"] = aryList;// 商品列表
			objectShu["isGrouponOrder"] = 2;// 是否是团购大类
			objectShu["totalMoney"] = huiajPrice;// 汇爱家总价
			objectShu["number"] = numberS;// 总份数
			objectShu["feeWaiver"] = youHui;// 总优惠
			objectShu["postFee"] = 0;// 运费
			objectShu["toDayOrderNumber"] = yiYUanGou;// 一元购
			objectShu["application"] = 2; // 补单应用程序为后台
			/* =================================================================== */
			if (chunZai.indexOf('1') > -1) {// 判断商品列表里是否有下架商或不存在商品
				flag = 1;
			} else {
				flag = 2;
			}

			creatGeNum(fenShuTotal);// 商品份数
			creatDanNum(dianJiaTotal);// 单价是否为负

			var objectShuJ = JSON.stringify(objectShu); // 将JSON对象转化为JSON字符

			// 判断不但商品有无选择补单原因
			var reason_flag = true;
			$('.budan_reason').each(function (index, e) {
				if (!e.value) {
					alert('要选补单原因啊亲 -_-|||');
					reason_flag = false;
					btn_enable($("#btn_submit"));
					return false;
				}
			});

			if (reason_flag && flag == 2 && geShuNum == 2 && danjiaNum == 2) {
				/* 向后台传参 */
				$.ajax({
					url: "/hajOrder/addOrderapp.action?sign=6be04cc03f6f0a63748ed0308aef1f68",
					type: "POST",
					data: {'orderStr': objectShuJ},
					success: function (data) {
						var orderNo = data.orderNo;
						var error_1 = data.error;
						if (error_1 == "0") {
							$.ajax({
								url: "/order/budanInventoryReduce.html?orderNo=" + orderNo,
								type: "POST",
								success: function (result) {
									var msg = result.msg;
									var error_22 = result.error;
									if (error_22 == "0") {// 判断库存
										if (confirm('补单成功！\r\n点击确定查看订单详情，点击取消继续补下一单')) {
											window.location.href = '/order/orderDetail.html?orderId=' + result.orderId;
										} else {
											window.location.reload();
										}
									}
								}
							})
						} else if (error_1 == '3') {
							alert('请确认客户信息是否完善');
							btn_enable($("#btn_submit"));
						} else {
							alert(data.msg);
							btn_enable($("#btn_submit"));
						}
					}
				})
			} else if (geShuNum == 1) {
				alert("请查看是否包含商品数量小于0的商品 ");
				btn_enable($("#btn_submit"));
			} else if (danjiaNum == 1) {
				alert("请查看是否包含商品单价为负的商品  ");
				btn_enable($("#btn_submit"));
			} else if (!reason_flag) {
				btn_enable($("#btn_submit"));
			} else {
				btn_enable($("#btn_submit"));
				alert('未知异常');
			}
		})
	}
</script>
<script type="text/javascript">
	function numberKeyup(obj) {
		var _i = obj.getAttribute('data-id');
		if (!obj.value) {
			obj.value = 1;
		}
		// 补单原因自定义属性（补单商品数量），用于自动填写备注
		$('.budan_reason._' + _i).attr('data-number', obj.value);
	}

	/** 选择补单原因后，自动将原因追加到备注中 */
	function reasonChange(obj) {
		var commodityName = obj.getAttribute('data-name');
		if (commodityName) {
			var commodityNumber = obj.getAttribute('data-number');
			var reason = obj.value;
			if (reason == '0') {
				reason = '质量问题';
			} else if (reason == '1') {
				reason = '仓库缺货';
			} else {
				return false;
			}

			$('.textArea.remark').val(function (index, value) {
				return value + commodityName + '*' + commodityNumber + '，（' + reason + '）；';
			});
		} else {
			return false;
		}
	}

	$('#importFile').click(function () {
		$('#importFile').html('正在导入...');
		$('.batchImportTip').empty();

		//创建FormData对象
		var data = new FormData();

		//为FormData对象添加数据
		$.each($('#uploadFile')[0].files, function (i, file) {
			data.append('fileName', file);
		});

		$.ajax({
			url: '/order/batchImport.html',
			type: 'POST',
			data: data,
			cache: false,
			contentType: false, //不可缺
			processData: false, //不可缺
			success: function (resultMap) {
				if (resultMap.result == 'success') {
					alert('搞定啦，赶紧去查一下订单有没有问题！');
					window.location.reload();
				} else if (!resultMap.result) {
					alert(UNKNOWN_ERROR);
					window.location.reload();
				} else {
					alert(resultMap.result);
					$('.batchImportTip').html(resultMap.result);
					$('#importFile').html('批量导入补单');
				}
			},
			error: function () {
				alert('请先选择您要上传的文件');
				window.location.reload();
			}
		});
	});
</script>
</body>
</html>
