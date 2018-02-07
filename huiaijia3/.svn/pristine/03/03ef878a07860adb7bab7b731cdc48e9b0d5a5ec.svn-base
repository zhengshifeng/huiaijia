<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>客户更新</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style>
		label{
			margin-right: 7px;
		}
		.divtest {float:left;}
	</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/getHajUserManagerById.html?parameter=${userManager.id }&page=customerView"
		   class="actionBtn">查看客户详情</a>
		【${userManager.mobilePhone }】客户信息编辑
	</h3>
	<form action="updateCustomerById.html" method="post" name="userForm" id="userForm">
		<input type="hidden" name="id" value="${userManager.id  }"/>
		<input type="hidden" id="buildingId" name="buildingId" value="${userManager.buildingId  }"/>
		<div class="filter">
			<table class="tableBasic" style="margin: auto; width: 100%; ">
				<tr>
					<td>用户角色</td>
					<td>
						<input type="hidden" name="ismember" value="${userManager.ismember }"/>
						<c:choose>
							<c:when test="${userManager.ismember eq '1'}">仅注册</c:when>
							<c:when test="${userManager.ismember eq '2'}">预备会员</c:when>
							<c:when test="${userManager.ismember eq '3'}">一元购会员</c:when>
							<c:when test="${userManager.ismember eq '4'}">会员取消</c:when>
							<c:when test="${userManager.ismember eq '5'}">普通会员</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>所在小区</td>
					<td>${userManager.residential }</td>
				</tr>
				<tr>
					<td>收货人</td>
					<td>
						<input type="text" class="inpMain" name="receiver" id="receiver" value="${userManager.receiver }"/>
					</td>
				</tr>
				<tr>
					<td>用户住址</td>
					<td>${userManager.address }</td>
				</tr>
				<tr>
					<td>修改住址</td>
					<td >
						<%--<c:choose>--%>
							<%--<c:when test="${not empty unitList}">--%>
						<%--<select id="communityUnitId" name="communityUnitId">--%>
							<%--<option value="0">请选择小区单元</option>--%>
							<%--<c:if test="${not empty unitList}">--%>
								<%--<c:forEach items="${unitList}" var="list" varStatus="vs">--%>
									<%--<option value="${list.id }" data-building-id="${list.buildingId }">${list.unit }</option>--%>
								<%--</c:forEach>--%>
							<%--</c:if>--%>
						<%--</select>--%>
							<%--</c:when>--%>
						<%--</c:choose>--%>

				<div class="divtest">
					<span>
						<input type="text" class="inpMain short" name="houseNumber" id="houseNumber"
							   value="" placeholder="门牌号" onchange="if(/[^A-Z0-9]/g.test(this.value)){alert('只能输入大写英文字母和数字!');this.value='';}" />
					</span>
					<span>
						<select id="floor" name="floor" style="width: 130px">
							<option value="">请选择楼层</option>
							<c:if test="${not empty community}">
								<c:forEach var="i" begin="1" end="${community.floor }" step="1">
									<option value="${i}">${i}层</option>
								</c:forEach>
							</c:if>
						</select>
					</span>

					<span id="test" style="">
						<select id="term" name="term" onchange="getTerms(this.value)" style="width: 130px">
							<option value="-1">请选择</option>
							<c:forEach items="${buildingList}" var="list" varStatus="vs">
								<option value="${list.id }" >${list.name }</option>
							</c:forEach>
						</select>
					</span>

				</div>
					</td>
				</tr>
				<tr>
					<td>是否预约</td>
					<td>
						<label><input type="radio" name="isAppointment" value="1" <c:if
								test="${userManager.isAppointment eq '1'}"> checked="checked"</c:if>>预约</label>
						<label><input type="radio" name="isAppointment" value="0" <c:if
								test="${userManager.isAppointment eq '0'}"> checked="checked"</c:if>>取消预约</label>
					</td>
				</tr>
				<tr>
					<td>是否收取配送费</td>
					<td>
						<label><input type="radio" name="isPay" value="2">后台取消收取配送费</label>
					</td>
				</tr>
				<tr>
					<td style="width: 150px;">&nbsp;</td>
					<td>
						<input type="button" class="btn" name="updateCustomer" id="updateCustomer" value="更新"/>
						<input type="button" class="btn" value="返回" onclick="javascript:history.back();"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript">
	$(function () {
		$("#updateCustomer").click(function () {
			if ($('#term').val()!=-1) {
				if (!$('#floor').val()) {
					alert('请选择楼层');
					return false;
				}
				if (!$('#houseNumber').val()) {
					alert('请选择门牌号');
					return false;
				}
			}else{
				var houseNumber = '${userManager.houseNumber}';
				if (houseNumber && houseNumber != '') {
					$('#houseNumber').val('${userManager.houseNumber}');
				}
				var floor = '${userManager.floor}';
				if (floor && floor != '') {
					$('#floor').val('${userManager.floor}');
				}
			}



			// 将buildingId写入用户表
//			$('#buildingId').val($('#communityUnitId').find('option:selected').attr('data-building-id'));
			var url = '${pageContext.request.contextPath }/updateCustomerById.html';
			$.post(url, $('#userForm').serialize(), function (data) {
				alert(data.msg);
				if (data.result == 'success') {
					window.location.href = '/userManger.html?' + $.cookie('haj_front_user_list_params');
				}
			});
		});

		var communityUnitId = '${userManager.communityUnitId}';
		if (communityUnitId && communityUnitId != '0') {
			$('#communityUnitId').val('${userManager.communityUnitId}');
		}

//		var floor = '${userManager.floor}';
//		if (floor && floor != '0') {
//			$('#floor').val('${userManager.floor}');
//		}

		// 限制门牌号只能输入数字跟字母
		$('#houseNumber').on('keypress', function (e) {
			var keyCode = e.keyCode || e.which;
			return ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122));
		});


		//初始化楼期下的楼栋列表
//		$("select[name$='term']").each(function(){
//			//若楼期下拉框有初始值,则显示对应楼栋下拉框
//			if(this.value)
//				this.onchange();
//		});

		getTerms(parentId);

	});

	//id 代表下级下拉框ID,parentId代表的是父级菜单代码,所有级菜单在同一张表,
	function getTerms(parentId){
		if(parentId) {
			$("#loading").show();//显示正在加载提示层
			var loadUrl="/getBuildList.html";
			var loadData="&parentId="+parentId;
			$.ajax({
				url:loadUrl,
				data:loadData,
				method:'POST',
				dataType:'json',
				success:function(data){
					if(!jQuery.isEmptyObject(data)) {
						$(".flag").remove();
						$("#test").append("<span id='buildSp'><select id='build' name='build' onchange='getTerms1(this.value)' class='flag' style='width: 130px'></select></span>");
						$("#build").append("<option value=''>请选择</option>");
						for (var i in data) {
							var op = $("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
							$("#build").append(op);
						}

					} else {
						$(".flag").remove();
					}
						$("#loading").hide();
				}
			});
		} else {
			$("#build"+count).hide();		//如果被选的元素已被显示，则隐藏该元素。
		}
	}

	function getTerms1(parentId){
		if(parentId) {
			$("#loading").show();//显示正在加载提示层
			var loadUrl="/getBuildList.html";
			var loadData="&parentId="+parentId;
			$.ajax({
				url:loadUrl,
				data:loadData,
				method:'POST',
				dataType:'json',
				success:function(data){
					if(!jQuery.isEmptyObject(data)) {
						$("#unit").remove();
						$("#seat").remove();
						$("#buildSp").append("<span id='unitSp'><select id='unit' name='unit' onchange='getTerms2(this.value)' class='flag' style='width: 130px'></select></span>");
						$("#unit").append("<option value=''>请选择</option>");
						for (var i in data) {
							var op = $("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
							$("#unit").append(op);
						}
					} else {
						$("#unit").remove();
						$("#seat").remove();
					}
					$("#loading").hide();
				}
			});
		} else {
			$("#unit").hide();		//如果被选的元素已被显示，则隐藏该元素。
		}
	}


	function getTerms2(parentId){
		if(parentId) {
			$("#loading").show();//显示正在加载提示层
			var loadUrl="/getBuildList.html";
			var loadData="&parentId="+parentId;
			$.ajax({
				url:loadUrl,
				data:loadData,
				method:'POST',
				dataType:'json',
				success:function(data){
					if(!jQuery.isEmptyObject(data)) {
						$("#seat").remove();
						$("#unitSp").append("<select id='seat' name='seat' onchange='getTerms3(this.value)' class='flag' style='width: 130px'></select>");
						$("#seat").append("<option value=''>请选择</option>");
						for (var i in data) {
							var op = $("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
							$("#seat").append(op);
						}
					} else {
						$("#seat").remove();
					}
					$("#loading").hide();
				}
			});
		} else {
			$("#seat").hide();		//如果被选的元素已被显示，则隐藏该元素。
		}
	}

</script>
</body>
</html>