<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加小区</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>添加小区</h3>
		<form action="/addVillage.html" method="post" name="villageForm" id="villageForm">
			<div class="filter" style="height: 10%;">
				<input type="hidden" name="photoPath" id="photoPath" value="${village.photoPath}" />
				<input type="hidden" name="afterSalePhoto" id="afterSalePhoto" value="${village.afterSalePhoto}" />
				<table style="width: 100%;  " class="tableBasic">
					<tr>
						<th><label>小区名称</label>:</th>
						<td>
							<input type="hidden" name="version" value="1" />
							<input type="text" class="inpMain required" name="communityName" required="required" />
						</td>
						<th><label>所在区域</label>:</th>
						<td>
							<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
								<option value="0">省</option>
								<c:forEach items="${provinceList}" var="list">
									<option value="${list.code }" <c:if test="${criteria.provinceCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
							<select name="cityCode" id="cityCode" style="vertical-align: middle;">
								<option value="0">市</option>
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code }" <c:if test="${criteria.cityCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
							<select name="communityCode" id="communityCode" class="required" style="vertical-align: middle; margin-right: 15px;">
								<option value="">区/县</option>
								<c:forEach items="${communityList}" var="list">
									<option value="${list.code }" <c:if test="${criteria.communityCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						</td>
						<th><label>小区地址</label>:</th>
						<td>
							<input type="text" class="inpMain required" name="address" />
						</td>
						<th>会员是否已满:</th>
						<td>
							<select name="memberStatus" id="memberStatus" style="vertical-align: middle;">
								<option value="0">未满</option>
								<option value="1">已满</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>小区状态:</th>
						<td>
							<select name="status" id="status" style="vertical-align: middle;">
								<option value="0">未激活</option>
								<option value="1">激活</option>
							</select>
							<select name="needPostFee" id="needPostFee" style="vertical-align: middle;">
								<option value="">不收运费</option>
								<option value="1">需要收取运费</option>
							</select>
						</td>
						<th>小区等级 / 合作状态</th>
						<td>
							<select name="level" id="level" style="vertical-align: middle;">
								<option value="1">低档</option>
								<option value="2">中档</option>
								<option value="3">高档</option>
							</select>
							<select name="cooperationStatus" id="cooperationStatus" style="vertical-align: middle;">
								<option value="1">等待批复</option>
								<option value="2">合作中</option>
								<option value="3">解除合作</option>
							</select>
						</td>
						<th>合作开始时间</th>
						<td>
							<input type="text" class="inpMain short date_picker" onclick="WdatePicker()" readonly="readonly"
								name="cooperationBegin" />
						</td>
						<th>合作结束时间:</th>
						<td>
							<input type="text" class="inpMain short date_picker" onclick="WdatePicker()" readonly="readonly"
								name="cooperationOver" />
						</td>
					</tr>
					<tr>
						<th>预约人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="appointmentNum" value="0" />
						</td>
						<th>计划会员人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="planMemberNumber" value="0" />
						</td>
						<th>注册人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="registererNumber" value="0" />
						</td>
						<th>一元购会员人数:</th>
						<td>
							<input type="number" class="inpMain" min="0" name="membersNumber" value="0" />
						</td>
					</tr>
					<tr>
						<th>小区对接人:</th>
						<td>
							<input type="text" class="inpMain" name="cellAccess" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="pickUpContact" />
						</td>
						<th>汇爱家负责人:</th>
						<td>
							<input type="text" class="inpMain" name="hajCellAccess" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="hajPickUpContact" />
						</td>
					</tr>
					<tr>
						<th>订单售后:</th>
						<td>
							<input type="text" class="inpMain" name="orderSales" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="orderSalesPhone" />
						</td>
						<th>配送员:</th>
						<td>
							<input type="text" class="inpMain" name="name" />
						</td>
						<th>联系方式:</th>
						<td>
							<input type="text" class="inpMain" name="telphone" />
						</td>
					</tr>
					<tr>
						<th>售前微信号:</th>
						<td>
							<input type="text" class="inpMain" name="wxcode" />
						</td>
						<th>楼层数:</th>
						<td>
							<input type="number" class="inpMain" name="floor" />
						</td>
						<th>经度</th>
						<td>
							<input type="text" class="inpMain" name="longitude" />
						</td>
						<th>纬度</th>
						<td>
							<input type="text" class="inpMain" name="latitude" />
						</td>
					</tr>
					<tr>
						<th>售后微信号:</th>
						<td>
							<input type="text" class="inpMain" name="afterServiceWechatId" />
						</td>
					</tr>
					<tr>
						<%-- <th>小区地址单位</th>
						<td colspan="3">
							<input type="text" class="inpMain" name="addressSpecification" value="${village.addressSpecification }"
								placeholder="如：栋@单元@号" />
						</td> --%>
							<th>配送情况</th>
						<td>
							<select name="distributionStatus" id="distributionStatus" style="vertical-align: middle;">
								<option value="0">停止配送</option>
								<option value="1">配送中</option>
							</select>
						</td>
						
						<th>填写须知:</th>
						<td colspan="3">
							<textarea class="inpMain" cols="80" rows="4" name="completeNotes"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
							<input type="button" class="btn" width="100%" name="addVillage" id="addVillage" value="添加" title="添加" />
						</td>
					</tr>
				</table>
			</div>

			<div class="filter" style="margin-top: 3%; height: 5%;">
				<div style="float: left;">
					<a href="javascript:;" class="file">
						上传配送员头像
						<input type="file" name="inputfile" id="inputfile" />
					</a>
					<div style="position: relative; margin-left: 30px; width: 180px; height: 180px; border: solid 1px red;">
						<img alt="" src="" width="180px" height="180px" id="imgPre">
					</div>
				</div>
				
				<div style="float: left;">
					<a href="javascript:;" class="file">
						上传售后员头像
						<input type="file" name="inputfile" id="uploadAfterSalePhoto" />
					</a>
					<div style="position: relative; margin-left: 30px; width: 180px; height: 180px; border: solid 1px red;">
						<img alt="" src="" width="180px" height="180px" id="afterSalePhotoImgPre">
					</div>
				</div>
			</div>
		</form>

	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			prependRedStar();

			$('#addVillage').click(function() {
				$("#communityName").val($.trim($("#communityName").val()));

				if (!requiredCheck()) {
					return false;
				}

				btn_disable($('#addVillage'));
				var url = '${pageContext.request.contextPath }/addVillage.html';
				$.post(url, $('#villageForm').serialize(), function(data) {
					if (data.status == 'success') {
						alert('添加成功');
						window.location.href = "villageList.html";
					} else {
						alert(data.status);
						btn_enable($('#addVillage'));
					}
				});
			});

			$("#inputfile").change(function() {
				//创建FormData对象
				var data = new FormData();
				//为FormData对象添加数据
				//
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

			$("#uploadAfterSalePhoto").change(function() {
				//创建FormData对象
				var data = new FormData();
				//为FormData对象添加数据
				//
				$.each($('#uploadAfterSalePhoto')[0].files, function(i, file) {
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
							$('#afterSalePhotoImgPre').attr('src', data.uploadFile);
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
