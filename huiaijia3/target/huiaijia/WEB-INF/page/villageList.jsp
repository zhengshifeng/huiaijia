<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="addCommunity.html" class="actionBtn add">添加小区</a>
			小区列表
		</h3>
		<form action="villageList.html" method="post" name="listForm" id="listForm">
			<div class="filter">
				<select name="level" id="level" style="vertical-align: middle;">
					<option value="">小区等级</option>
					<option value="1" <c:if test="${criteria.level eq '1'}">selected</c:if>>低档</option>
					<option value="2" <c:if test="${criteria.level eq '2'}">selected</c:if>>中档</option>
					<option value="3" <c:if test="${criteria.level eq '3'}">selected</c:if>>高档</option>
				</select>
				<select name="planMemberNumber" id="planMemberNumber" style="vertical-align: middle;">
					<option value="">会员计划数量</option>
					<c:forEach items="${memberPlanNumList}" var="num">
						<option value="${num }" <c:if test="${criteria.planMemberNumber eq  num }">selected</c:if>>${num  }</option>
					</c:forEach>
				</select>
				<select name="status" id="status" style="vertical-align: middle;">
					<option value="">小区状态</option>
					<option value="1" <c:if test="${criteria.status eq '1'}">selected</c:if>>激活</option>
					<option value="0" <c:if test="${criteria.status eq '0'}">selected</c:if>>未激活</option>
				</select>
				<select name="memberStatus" id="memberStatus" style="vertical-align: middle; margin-right: 15px;">
					<option value="">会员数量</option>
					<option value="0" <c:if test="${criteria.memberStatus eq '0'}">selected</c:if>>未满</option>
					<option value="1" <c:if test="${criteria.memberStatus eq '1'}">selected</c:if>>已满</option>
				</select>

				小区地址
				<select name="provinceCode" id="provinceCode" style="vertical-align: middle;">
					<option value="0">不限</option>
					<c:forEach items="${provinceList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.provinceCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="cityCode" id="cityCode" style="vertical-align: middle;">
					<option value="0">不限</option>
					<c:forEach items="${cityList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.cityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>
				<select name="communityCode" id="communityCode" style="vertical-align: middle; margin-right: 15px;">
					<option value="">不限</option>
					<c:forEach items="${communityList}" var="list">
						<option value="${list.code }" <c:if test="${criteria.communityCode==list.code}">selected</c:if>>${list.name }</option>
					</c:forEach>
				</select>

				排序条件：
				<select name="orderByClause" id="orderByClause" style="vertical-align: middle;">
					<option value="">默认</option>
					<option value="registererNumber" <c:if test="${criteria.orderByClause eq 'registererNumber'}">selected</c:if>>注册人数</option>
					<option value="membersNumber" <c:if test="${criteria.orderByClause eq 'membersNumber'}">selected</c:if>>一元购会员人数</option>
					<option value="appointmentNum" <c:if test="${criteria.orderByClause eq 'appointmentNum'}">selected</c:if>>预备人数</option>
					<option value="commonNumber" <c:if test="${criteria.orderByClause eq 'commonNumber'}">selected</c:if>>普通会员人数</option>
					<option value="cancalNumber" <c:if test="${criteria.orderByClause eq 'cancalNumber'}">selected</c:if>>取消会员人数</option>
					<option value="needPostFee" <c:if test="${criteria.orderByClause eq 'needPostFee'}">selected</c:if>>收取运费</option>
				</select>
			</div>
			<div class="filter">
				录入时间:
				<input class="inpMain short date_picker" id="createTimeMin" name="createTimeMin" value="${criteria.createTimeMin}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'createTimeMax\')||\'%y-%M-%d\'}'})"
					   placeholder="开始时间" readonly="" type="text" >
				-
				<input class="inpMain short date_picker" id="createTimeMax" name="createTimeMax" value="${criteria.createTimeMax}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly',minDate:'#F{$dp.$D(\'createTimeMin\')}',maxDate:'%y-%M-%d '})"
					   placeholder="结束时间" readonly="" type="text">
				&nbsp;&nbsp;&nbsp;
				激活时间:
				<input class="inpMain short date_picker" id="activationTimeMin" name="activationTimeMin" value="${criteria.activationTimeMin}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'activationTimeMax\')||\'%y-%M-%d\'}'})"
					   placeholder="开始时间" readonly="" type="text" >
				-
				<input class="inpMain short date_picker" id="activationTimeMax" name="activationTimeMax" value="${criteria.activationTimeMax}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',readOnly:'readOnly',minDate:'#F{$dp.$D(\'activationTimeMin\')}',maxDate:'%y-%M-%d '})"
					   placeholder="结束时间" readonly="" type="text">
			</div>
			<div class="filter">
				配送情况:
				<select name="distributionStatus" id="distributionStatus" style="vertical-align: middle;">
					<option value="">默认</option>
					<option value="1" <c:if test="${criteria.distributionStatus == 1}">selected='selected'</c:if>>配送中</option>
					<option value="0" <c:if test="${criteria.distributionStatus == 0}">selected='selected'</c:if>>停止配送</option>
				</select>
				小区：
				<input name="communityName" class="inpMain" type="text" value="${criteria.communityName }" placeholder="小区名称 / 小区ID"/>
				<input class="btn" type="submit" value="搜索" />
			</div>
		</form>

		<div class="filter">
			<input type="file" id="uploadFile" accept=".xlsx" />
			<input type="button" class="btnGray" id="importFile" value="批量创建小区" title="批量创建小区"/>
			&nbsp;
			<input type="file" id="batchUpdateFile" accept=".xlsx" />
			<input type="button" class="btnGray" id="btn_batch_update" value="批量修改小区" title="批量修改小区"/>

			<label style="color: #ff3300" id="upload_tip"></label>
		</div>

		<div id="list">
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						<th><input type="checkbox" id="ckb_all" onclick="selectAll()"/>小区ID</th>
						<th>供应城市</th>
						<th>小区名称</th>
						<th>限制会员人数</th>
						<th>注册人数</th>
						<th>预备会员人数</th>
						<th>一元购会员人数</th>
						<th>普通会员人数</th>
						<th>取消会员人数</th>
						<th>会员人数情况</th>
						<th>是否收取运费</th>
						<th>售后客服</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty villageList}">
							<c:forEach items="${villageList}" var="village" varStatus="vs">
								<tr>
									<td><input type="checkbox" name="communityIds" value="${village.id }" />${village.id }</td>
									<td>${village.cityName }</td>
									<td>${village.communityName }</td>
									<td>${village.planMemberNumber }</td>
									<td>${village.registererNumber }</td>
									<td>${village.appointmentNum }</td>
									<td>${village.membersNumber }</td>
									<td>${village.commonNumber }</td>
									<td>${village.cancalNumber }</td>
									<td>
										<c:if test="${village.memberStatus eq '0'}">未满</c:if>
										<c:if test="${village.memberStatus eq '1'}">已满</c:if>
									</td>
									<td>
										<c:if test="${empty village.needPostFee}">未知</c:if>
										<c:if test="${village.needPostFee == 0}">否</c:if>
										<c:if test="${village.needPostFee == 1}"><span class="text_red">是</span></c:if>
									</td>
									<td>${village.afterServiceWechatId }</td>
									<td>
										<a href="/exactSearchVillage.html?parameter=${village.id }&page=villageView">查看详情</a>
										|
										<a href="/commodityFailure/list.html?areasId=${village.id }">管理商品</a>
										|
										<a href="/exactSearchVillage.html?parameter=${village.id }&page=villageUpdate">编辑</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="10" align="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="page_and_btn">
			<div class="action">
				<input type="button" class="btn" id="btn_export" value="导出小区列表" title="导出小区列表"/>
			</div>
			${criteria.page.pageStr }
		</div>
	</div>

	<script type="text/javascript">

		function selectAll(){
			if($("#ckb_all").prop("checked")){
				$("input[name='communityIds']").prop("checked",true);
			} else {
				$("input[name='communityIds']").prop("checked",false);
			}
		}

		$('#importFile').click(function() {
			btn_disable($(this));
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($('#uploadFile')[0].files, function(i, file) {
				data.append('fileName', file);
			});

			$.ajax({
				url : 'batchImportVillage.html',
				type : 'POST',
				data : data,
				cache : false,
				contentType : false, //不可缺
				processData : false, //不可缺
				success : function(resultMap) {
					if (resultMap.result == 'success') {
						alert('上传成功！');
						window.location.reload();
					} else if (!resultMap.result) {
						alert(UNKNOWN_ERROR);
						btn_enable($('#importFile'));
					} else {
						alert(resultMap.result);
						btn_enable($('#importFile'));
					}
				},
				error : function() {
					alert('请先选择您要上传的文件');
					btn_enable($('#importFile'));
				}
			});
		});

		$('#btn_batch_update').click(function() {
			btn_disable($(this));
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($('#batchUpdateFile')[0].files, function(i, file) {
				data.append('fileName', file);
			});

			$.ajax({
				url : '/batchUpdateVillage.html',
				type : 'POST',
				data : data,
				cache : false,
				contentType : false, //不可缺
				processData : false, //不可缺
				success : function(resultMap) {
					if (resultMap.result == 'success') {
						alert('修改成功！');
						window.location.reload();
					} else if (!resultMap.result) {
						alert(UNKNOWN_ERROR);
						btn_enable($('#btn_batch_update'));
					} else {
						alert(resultMap.result);
						btn_enable($('#btn_batch_update'));
					}
				},
				error : function() {
					alert('请先选择您要上传的文件');
					btn_enable($('#btn_batch_update'));
				}
			});
		});

		$('#btn_export').click(function(){
			btn_disable($(this));
			location.href = '/exportCommunity.html?'+$('#listForm').serialize();
            window.parent.layer.confirm('正在拼命导出，请耐心等待...', {
                btn: ['关闭'] //按钮
            });
			setTimeout('btn_enable($("#btn_export"))', 10000);
		});

	</script>
	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
</body>
</html>
