<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区更新</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="javascript:history.back();" class="actionBtn">返回</a>
			小区更新
		</h3>
		<form action="updateHajCommunityPersionById.html" method="post" name="villageForm" id="villageForm">
			<div class="filter" style="height: 10%;">
				<input type="hidden" name="id" value="${village.id}" />
				<input type="hidden" name="photoPath" id="photoPath" value="${village.photoPath}" />
				<input type="hidden" name="afterSalePhoto" id="afterSalePhoto" value="${village.afterSalePhoto}" />
				<input type="hidden" name="version" id="version" value="${village.version}" />
				<table style="width: 100%;  " class="tableBasic">
					<tr class="info">
						<th>小区ID:</th>
						<td>${village.id}</td>
						<th>小区状态:</th>
						<td>
							<select name="status" id="status" style="vertical-align: middle;">
								<option value="1" <c:if test="${village.status eq '1'}">selected</c:if>>激活</option>
								<option value="0" <c:if test="${village.status eq '0'}">selected</c:if>>未激活</option>
							</select>
							<select name="needPostFee" id="needPostFee" style="vertical-align: middle;">
								<option value="" <c:if test="${village.needPostFee == 0}">selected</c:if>>不收运费</option>
								<option value="1" <c:if test="${village.needPostFee == 1}">selected</c:if>>需要收取运费</option>
							</select>
						</td>
						<th>激活时间:</th>
						<td>${village.activationTime}</td>
						<th>小区名称:</th>
						<td>
							<input type="text" class="inpMain short" name="communityName" value="${village.communityName}" />
						</td>
					</tr>
					<tr class="info">
						<th>小区地址:</th>
						<td>
							<input type="text" class="inpMain" name="address" value="${village.address}" />
						</td>
						<th>录入时间:</th>
						<td>${village.createTime}</td>
						<th>小区等级:</th>
						<td>

							<select name="level" id="level" style="vertical-align: middle;">
								<option value="1" <c:if test="${village.level eq '1'}">selected</c:if>>低档</option>
								<option value="2" <c:if test="${village.level eq '2'}">selected</c:if>>中档</option>
								<option value="3" <c:if test="${village.level eq '3'}">selected</c:if>>高档</option>
							</select>
						</td>
						<th>会员是否已满:</th>
						<td>
							<select name="memberStatus" id="memberStatus" style="vertical-align: middle;">
								<option value="0" <c:if test="${village.memberStatus eq '0'}">selected</c:if>>未满</option>
								<option value="1" <c:if test="${village.memberStatus eq '1'}">selected</c:if>>已满</option>
							</select>
						</td>
					</tr>
					<tr class="info">
						<th>预约人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="appointmentNum"
								value="${village.appointmentNum }" />
						</td>
						<th>计划会员人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="planMemberNumber"
								value="${village.planMemberNumber}" />
						</td>
						<th>注册人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="registererNumber"
								value="${village.registererNumber}" />
						</td>
						<th>会员人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="membersNumber"
								value="${village.membersNumber}" />
						</td>
					</tr>
					<tr class="info">
						<th>小区对接人:</th>
						<td>
							<input type="text" class="inpMain" name="cellAccess" value="${village.cellAccess}" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="pickUpContact"
								value="${village.pickUpContact}" />
						</td>
						<th>汇爱家负责人</th>
						<td>
							<input type="text" class="inpMain" name="hajCellAccess"
								value="${village.hajCellAccess}" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="hajPickUpContact"
								value="${village.hajPickUpContact}" />
						</td>
					</tr>
					<tr class="info">
						<th>订单售后:</th>
						<td>
							<input type="text" class="inpMain" name="orderSales" value="${village.orderSales}" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="orderSalesPhone"
								value="${village.orderSalesPhone}" />
						</td>
						<th>地址区域</th>
						<td>
							<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
								<option value="0">不限</option>
								<c:forEach items="${provinceList}" var="list">
									<option value="${list.code }" <c:if test="${areaByCode3.code==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
							<select name="cityCode" id="cityCode" style="vertical-align: middle;">
								<option value="0">不限</option>
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code }" <c:if test="${areaByCode2.code==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
							<select name="communityCode" id="communityCode" style="vertical-align: middle; margin-right: 15px;">
								<option value="">不限</option>
								<c:forEach items="${communityList}" var="list">
									<option value="${list.code }" <c:if test="${areaByCode1.code==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						</td>
						<th>配送员电话:</th>
						<td>
							<input type="text" class="inpMain" name="telphone" value="${village.telphone}" />
						</td>
					</tr>
					<tr class="info">
						<th>配送员姓名:</th>
						<td>
							<input type="text" class="inpMain" name="name" value="${village.name}" />
						</td>
						<th>经度</th>
						<td>
							<input type="text" class="inpMain" name="longitude" value="${village.longitude }" />
						</td>
						<th>纬度</th>
						<td>
							<input type="text" class="inpMain" name="latitude" value="${village.latitude }" />
						</td>
						<th>仓库编号</th>
						<td>
							<select name="whcode" id="whcode" style="vertical-align: middle;">
								<c:forEach items="${warehouseList}" var="list">
									<option value="${list.whcode }" <c:if test="${village.whcode==list.whcode}">selected</c:if>>${list.whname }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr class="info">
						<th>楼层数:</th>
						<td>
							<input type="number" class="inpMain" name="floor" value="${village.floor}" />
						</td>
						<th>售前微信号:</th>
						<td>
							<input type="text" class="inpMain" name="wxcode" value="${village.wxcode}" />
						</td>
						<th>售后微信号:</th>
						<td>
							<input type="text" class="inpMain" name="afterServiceWechatId" value="${village.afterServiceWechatId}" />
						</td>
					</tr>
					<tr class="info">
					<%-- 	<th>小区地址单位</th>
						<td colspan="3">
							<textarea class="inpMain" cols="80" rows="4"
									  id="addressSpecification"
									  name="addressSpecification">${village.addressSpecification}</textarea>
						</td> --%>
						<th>配送情况</th>
						<td>
							<select name="distributionStatus" id="distributionStatus" style="vertical-align: middle;">
								<option value="0" <c:if test="${village.distributionStatus eq '0'}">selected</c:if>>停止配送</option>
								<option value="1" <c:if test="${village.distributionStatus eq '1'}">selected</c:if>>配送中</option>
							</select>
						</td>
						
						<th>填写须知:</th>
						<td colspan="3">
							<textarea class="inpMain" cols="80" rows="4" name="completeNotes">${village.completeNotes}</textarea>
						</td>
					</tr>
					<tr class="info">
						<td colspan="8" align="center">
							<input type="button" class="btn" id="updateVillage" value="确定更新" />
						</td>
					</tr>
				</table>
			</div>

			<div class="filter" style="margin-top: 3%; height: 5%;">
				<div style="float: left;">
					<a href="javascript:;" class="file">
						更改配送员头像
						<input type="file" id="inputfile" />
					</a>
					<div style="position: relative; margin-left: 30px; width: 180px; height: 180px; border: solid 1px red;">
						<img alt="" src="${village.photoPath}" width="180px" height="180px" id="imgPre">
					</div>
				</div>

				<div style="float: left;">
					<a href="javascript:;" class="file">
						更改售后员头像
						<input type="file" id="afterSalePhotoFile" />
					</a>
					<div style="position: relative; margin-left: 30px; width: 180px; height: 180px; border: solid 1px red;">
						<img alt="" src="${village.afterSalePhoto}" width="180px" height="180px" id="afterSaleImgPre">
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#updateVillage').click(function() {
				var status = $('#status').val();
				/* var addressSpecification = $('#addressSpecification').val();
				addressSpecification = $.trim(addressSpecification);
				if (status == 1 && !addressSpecification) {
					alert('激活小区前必须填写【小区地址单位】哦!');
				} else { */
					var url = '${pageContext.request.contextPath }/updateHajCommunityPersionById.html';
					$.post(url, $('#villageForm').serialize(), function(data) {
						if (data.status == 'success') {
							alert('更新成功');
							window.location.href = "villageList.html";
						} else {
							alert('未知错误');
						}
					});
				//}
			});

			$("#inputfile").change(function() {
				//创建FormData对象
				var data = new FormData();
				//为FormData对象添加数据
				$.each($('#inputfile')[0].files, function(i, file) {
					data.append('fileName', file);
				});
				$.ajax({
					url : 'uploadfile.html',
					type : 'POST',
					data : data,
					cache : false,
					contentType : false, //不可缺
					processData : false, //不可缺
					success : function(data) {
						if (data.status = 'success') {
							$('#imgPre').attr('src', data.uploadFile);
							$('#photoPath').val(data.uploadFile);
						} else {
							alert("图片上传失败！");
						}
					},
					error : function() {
						alert('未知错误！');
					}
				});
			});

			$("#afterSalePhotoFile").change(function() {
				//创建FormData对象
				var data = new FormData();
				//为FormData对象添加数据
				$.each($('#afterSalePhotoFile')[0].files, function(i, file) {
					data.append('fileName', file);
				});
				$.ajax({
					url : 'uploadfile.html',
					type : 'POST',
					data : data,
					cache : false,
					contentType : false, //不可缺
					processData : false, //不可缺
					success : function(data) {
						if (data.status = 'success') {
							$('#afterSaleImgPre').attr('src', data.uploadFile);
							$('#afterSalePhoto').val(data.uploadFile);
						} else {
							alert("图片上传失败！");
						}
					},
					error : function() {
						alert('未知错误！');
					}
				});
			});
		});
	</script>
</body>
</html>
