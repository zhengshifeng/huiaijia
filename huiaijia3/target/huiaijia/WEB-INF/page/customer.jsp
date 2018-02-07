
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>客户列表</h3>
		<form action="userManger.html" method="post" name="userForm" id="userForm">
			<div class="filter">
				筛选条件:
				<select name="ismember" id="ismember" style="vertical-align: middle;">
					<option value="">用户角色</option>
					<option value="1" <c:if test="${criteria.ismember eq '1'}">selected</c:if>>仅注册</option>
					<option value="2" <c:if test="${criteria.ismember eq '2'}">selected</c:if>>预备会员</option>
					<option value="3" <c:if test="${criteria.ismember eq '3'}">selected</c:if>>一元购会员</option>
					<option value="4" <c:if test="${criteria.ismember eq '4'}">selected</c:if>>会员取消</option>
					<option value="5" <c:if test="${criteria.ismember eq '5'}">selected</c:if>>普通会员</option>
				</select>

				<input type="number" placeholder="家庭人口数量" class="inpMain short" min="0" name="familymembers"
					value="${criteria.familymembers }" />
				<input type="text" class="inpMain short" placeholder="小区名称/小区ID" name="residential" value="${criteria.residential }" />

				<input type="text" class="inpMain" placeholder="详细地址" name="address" value="${criteria.address }" />

				注册时间:

				<input class="inpMain short date_picker" id="registerBeginTime" name="registerBeginTime"
					   value="${criteria.registerBeginTime }"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'registerEndTime\')||\'%y-%M-%d\'}'})" readonly="readonly" type="text">
				-
				<input class="inpMain short date_picker" id="registerEndTime" name="registerEndTime" value="${criteria.registerEndTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly',minDate:'#F{$dp.$D(\'registerBeginTime\')}',maxDate:'%y-%M-%d 23:59:59'})" readonly="readonly" type="text">

			</div>
			<div class="filter">
				精确查找:
				<input type="text" class="inpMain" placeholder="用户名/联系方式/用户ID" name="exactSearch" id="exactSearch"
					value="${criteria.exactSearch }" />
			</div>
			<div class="filter" style="height: 50px;">
				批量查找:
				<textarea class="textArea" rows="2" cols="70" placeholder="格式如：13800138000,15712345678,15964853412" name="phoneList"><c:forEach
						items='${criteria.phoneList }' var='phone' varStatus="vs">${phone }<c:if test="${!vs.last }">,</c:if></c:forEach></textarea>
				
			</div>
			
			<div class="filter">
				是否预约:
				<select name="isAppointment" id="isAppointment" style="vertical-align: middle;">
					<option value="">请选择</option>
					<option value="1" <c:if test="${criteria.isAppointment eq '1'}">selected</c:if>>是</option>
					<option value="0" <c:if test="${criteria.isAppointment eq '0'}">selected</c:if>>否</option>
				</select>
				
				排序方式:
				<select name="sortType" id="sortType" style="vertical-align: middle;">
					<option value="">请选择</option>
					<option value="createTime" <c:if test="${criteria.sortType eq 'createTime'}">selected</c:if>>注册时间(从早到晚)</option>
					<option value="appointmentTime" <c:if test="${criteria.sortType eq 'appointmentTime'}">selected</c:if>>预约时间(从早到晚)</option>
				</select>
				<input id="batchSearch" class="btn" type="submit" value="搜索" />
			</div>
			
		</form>
		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;" class="tableBasic list">
					<tr class="main_head">
						<th>
							<label>
								<input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()" />
								用户ID
							</label>
						</th>
						<th>注册手机号码</th>
						<th>注册时间</th>
						<th>所属城市</th>
						<th>小区</th>
						<th>小区是否激活</th>
						<th>用户住址</th>
						<th>收货人</th>
						<th>用户角色</th>
						<th>是否预约</th>
						<th>年龄</th>
						<th>性别</th>
						<th>职业</th>
						<th>家庭成员</th>
						<th>历史订单数</th>
						<th>历史订单总价</th>
						<th>平均订单实付金额</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty userManagerList}">
							<c:forEach items="${userManagerList}" var="customer" varStatus="vs">
								<tr class="main_info">
									<td>
										<label>
											<input type="checkbox" name="id" value="${customer.id }" />${customer.id }
										</label>
									</td>
									<td>${customer.mobilePhone }</td>
									<td>${customer.createTime }</td>
									<td>${customer.cityName }</td>
										
									<td>${customer.residential }</td>
									<td>
										<c:if test="${customer.status eq '1'}">是</c:if>
										<c:if test="${customer.status eq '0'}">否</c:if>
									</td>
									<td>${customer.address }</td>
									<td>${customer.receiver }</td>
									<td>
										<c:if test="${customer.ismember eq '1'}">仅注册</c:if>
										<c:if test="${customer.ismember eq '2'}">预备会员</c:if>
										<c:if test="${customer.ismember eq '3'}">一元购会员</c:if>
										<c:if test="${customer.ismember eq '4'}">会员取消</c:if>
										<c:if test="${customer.ismember eq '5'}">普通会员</c:if>
									</td>
									<td>
										<c:if test="${customer.isAppointment eq '1'}">是</c:if>
										<c:if test="${customer.isAppointment eq '0'}">否</c:if>
									</td>
									<td>${customer.age }岁</td>
									<td>
										<c:if test="${customer.sex eq '1' }">男</c:if>
										<c:if test="${customer.sex eq '0' }">女</c:if>
									</td>
									<td>${customer.occupation }</td>
									<td>${customer.familymembers }</td>
									<td>${customer.orderNum }</td>
									<td>
										<c:if test="${customer.sumMoney == null }">￥0</c:if>
										<c:if test="${customer.sumMoney >= 0 }">￥${customer.sumMoney }</c:if>
									</td>
									<td>
										<c:if test="${customer.avgMoney == null }">￥0</c:if>
										<c:if test="${customer.avgMoney >= 0 }">￥${customer.avgMoney }</c:if>
									</td>
									<td>
										<a href="/getHajUserManagerById.html?parameter=${customer.id }&page=customerView" style="color: #229922">查看详情</a>
										|
										<a href="/getHajUserManagerById.html?parameter=${customer.id }&page=customerUpdate" style="color: #0000FF;text-decoration: underline">编辑</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="18" align="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="page_and_btn">
			<div class="action">
				<a href="javascript:exportCustomer();" class="btn" style="margin-right: 20px;">
					<em>导出列表</em>
				</a>

				<strong>调整用户角色为：</strong>
				&nbsp;
				<select id="slt_update_status">
					<option value="">--请选择--</option>
					<option value="1">仅注册</option>
					<option value="2">预备会员</option>
					<option value="3">一元购会员</option>
					<option value="4">会员取消</option>
					<option value="5">普通会员</option>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>小区:</strong>&nbsp;
				<input id="communityName" list="community" name="community" class="inpMain" placeholder="小区名称" value="${criteria.community}">
				<datalist id="community" class="selectBox">
					<%--小区名称列表--%>
					<c:forEach items="${communityList}" var="list">
						<option value="${list.communityName }">
					</c:forEach>
				</datalist>

				<input id="btn_update_status" class="btn" type="button" value="确定" title="确定" />
			</div>
			<ul>
				<c:if test="${(criteria.currentPage-1) == 0}">
					<li class="pageinfo">
						<a href="javascript:search(${criteria.currentPage });">第${criteria.currentPage }页</a>
					</li>
				</c:if>
				<c:if test="${(criteria.currentPage-1) > 0}">
					<li class="pageinfo">
						<a href="javascript:search(${criteria.currentPage-1 });">第${criteria.currentPage-1 }页</a>
					</li>
				</c:if>
				<li class="current">本页：第${criteria.currentPage }页</li>
				<li class="pageinfo">
					<a href="javascript:search(${criteria.currentPage+1 });">第${criteria.currentPage+1 }页</a>
				</li>
				<li class="pageinfo">
					<input type="text" id="page" style="width: 20px; border: 0px;" />
				</li>
				<li class="pageinfo">
					<a href="javascript:jump();">跳转</a>
				</li>
			</ul>
		</div>
	</div>

	<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='id']").prop("checked", true);
			} else {
				$("input[name='id']").prop("checked", false);
			}
		}

		function jump() {
			var jumpPage = $('#page').val();
			if (jumpPage == '') {
				alert("请输入跳转页数!");
				return;
			}
			search(jumpPage);
		}

		function search(page) {
			$("#userForm").attr("action", "userManger.html?currentPage=" + page);
			$("#userForm").submit();
		}

		function exportCustomer() {
			document.location = "exportCustomerInfo.html?" + $("#userForm").serialize();
		}

		$(function() {
			$('#btn_update_status').click(function() {
				var userStatus = $('#slt_update_status').val();
				var community = $('#communityName').val();
				var userIds = $('#deleteForm').serialize();
				var count = 0;
				var len = $("input[name='id']:checked").length;
				if (len > 0) {
					//一次只能添加一个模块
					if (userStatus!="" ) {
						count++;
					}
					if (community!="") {
						count++;
					}
					if (count==0) {
						alert('请选择操作模块!');
						return false;
					}else if (count>1) {
						alert('一次只能批量添加到一个模块中!');
						return false;
					}

					// 需要用户输入通行证密码
					var passport = prompt('通行口令', '');
					if (passport) {
						//批量修改用户角色
						if (userStatus && confirm('确定批量更新选中的客户状态吗？')) {
							btn_disable($('#btn_update_status'));
							var url = '${pageContext.request.contextPath }/updateUsersStatus.html?';
							var data = userIds + '&status=' + userStatus;
							url = url + data + '&passport=' + passport;

							$.get(url, function(result) {
								alert(result.msg);
								btn_enable($('#btn_update_status'));
								if (result.error == '0') {
									window.location.reload();
								}
							});
						}

						//批量修改用户小区
						if (community && confirm('确定批量更新选中的客户小区地址吗？')) {
							btn_disable($('#btn_update_status'));
							var url = '${pageContext.request.contextPath }/updateCommunityName.html?';
							var data = userIds + '&communityName=' + community;
							url = url + data + '&passport=' + passport;

							$.get(url, function(result) {
								alert(result.msg);
								btn_enable($('#btn_update_status'));
								if (result.error == '0') {
									window.location.reload();
								}
							});
						}


					} else {
						alert('请先进行身份验证！');
					}
				} else {
					alert('请选择需要修改的客户！');
				}
			});

			
			// 将筛选条件存入cookie，以便编辑后返回之前页面并刷新
			$.cookie('haj_front_user_list_params', $('#userForm').serialize());
		});
	</script>
</body>
</html>
