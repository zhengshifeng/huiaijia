<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品运营</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
	<%--	<a href="/commodity/add.html" class="actionBtn add" style="margin-left: 10px;">添加商品</a>--%>
		<%--<a href="http://cdn.huiaj.com/xlsx/商品导入模板3.0.xlsx" class="actionBtn">批量创建模板</a>--%>
		商品运营
	</h3>
	<form action="/commodity/list.html" method="post" name="searchForm" id="searchForm">
		<div class="filter">
			<select name="commodityAttr" id="commodityAttr" style="vertical-align: middle;width: 150px">
				<option value="">商品属性</option>
				<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
				<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
				<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
				<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
			</select>
			<select name="parentTypeId" id="parentTypeId" style="vertical-align: middle;width: 150px">
				<option value="0">大类</option>
				<c:forEach items="${parentTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>
			<select name="typeId" id="typeId" style="vertical-align: middle;width: 150px">
				<option value="0">小类</option>
				<c:forEach items="${subTypeList}" var="list">
					<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
				</c:forEach>
			</select>

			<select name="shelves" id="shelves" style="margin-right: 15px;">
				<option value="">上/下架</option>
				<option value="1" <c:if test="${vo.shelves==1}">selected</c:if>>上架</option>
				<option value="0" <c:if test="${vo.shelves==0}">selected</c:if>>下架</option>
			</select>

			<select name="invalid" style="margin-right: 15px;">
				<option value="">是否作废</option>
				<option value="1" <c:if test="${vo.invalid==1}">selected</c:if>>已作废</option>
				<option value="0" <c:if test="${vo.invalid==0}">selected</c:if>>未作废</option>
			</select>

			<strong>排序方式：</strong>
			<select name="orderByClause" id="orderByClause" style="margin-right: 15px;">
				<option value="">无</option>
				<option value="c.salesVolume DESC">历史销量从高到低</option>
				<option value="c.salesVolume ASC">历史销量从低到高</option>
				<option value="c.originalPrice DESC">售价从高到低</option>
				<option value="c.originalPrice ASC">售价从低到高</option>
			</select>
			<select name="specialTopicId" id="specialTopicId" style="vertical-align: middle;width: 150px">
				<option value="">请选择专题</option>
				<c:forEach items="${specialTopicList}" var="list">
					<option value="${list.id }" <c:if test="${vo.specialTopicId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select name="plate" id="plate" style="vertical-align: middle;width: 150px">
				<option value="">请选择板块</option>
				<option value="mustBuy" <c:if test="${vo.mustBuy=='1'}">selected</c:if>>业主推荐</option>
				<option value="familyPreferences" <c:if test="${vo.familyPreferences=='1'}">selected</c:if>>新品推荐</option>
				<option value="ishot" <c:if test="${vo.ishot=='1'}">selected</c:if>>搜索推荐</option>
			</select>
			<select name="mark" id="mark" style="vertical-align: middle;width: 150px">
				<option value="">请选择角标</option>
				<option value="new" <c:if test="${vo.mark=='new'}">selected</c:if>>新品</option>
				<option value="hot" <c:if test="${vo.mark=='hot'}">selected</c:if>>热销</option>
				<option value="recommend" <c:if test="${vo.mark=='recommend'}">selected</c:if>>推荐</option>
			</select>
		</div>
		<div class="filter">
			<strong>供&nbsp; 应&nbsp; 商：</strong>
			<input list="supplyChain" name="supplyChain" class="inpMain" placeholder="供应商" value="${vo.supplyChain}">
			<datalist id="supplyChain" class="selectBox">
				<c:forEach items="${supplyChainNames}" var="list">
					<option value="${list.name }">
				</c:forEach>
			</datalist>
			&nbsp;&nbsp;&nbsp;
			<select name="promotionAreaId" id="promotionAreaId" style="vertical-align: middle;width: 150px">
				<option value="">选择商品专区</option>
				<c:forEach items="${promotionAreaList}" var="list">
					<option value="${list.id }" <c:if test="${vo.promotionAreaId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>

			<select name="brandId" id="brandId" style="vertical-align: middle;width: 150px">
				<option value="">选择商品品牌</option>
				<c:forEach items="${brandList}" var="list">
					<option value="${list.id }" <c:if test="${vo.brandId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>

			<select name="activityId" id="activityId" style="vertical-align: middle;width: 150px">
				<option value="">选择商品活动</option>
				<c:forEach items="${activityList}" var="list">
					<option value="${list.id }" <c:if test="${vo.activityId==list.id}">selected</c:if>>${list.activityName }</option>
				</c:forEach>
			</select>
		</div>


		<div class="filter">
			<strong>商品搜索：</strong>
			<input type="text" class="inpMain" placeholder="商品名称 / 商品编号" name="commodityNo" id="commodityNo"
				value="${vo.commodityNo }" />
			&nbsp;&nbsp;&nbsp;
			<input class="btn" type="submit" value="搜索" />
		</div>

		<div class="filter" style="float: left;">
			<%--<input type="file" id="uploadFile" accept=".xlsx" />--%>
			<%--<input class="btnGray" type="button" id="importFile" value="批量创建商品" />--%>
			 <input type="file" id="modifiedFile" accept=".xlsx" />
			 <input class="btnGray" type="button" id="modifiedFileBtn" value="批量修改商品" />
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;"
				class="tableBasic list">
				<tr>
					<th>ID</th>
					<th>图片</th>
					<th>商品名称</th>
					<th>商品别名</th>
					<th>物料编码</th>
					<th>供应城市</th>
					<th>供应商</th>
					<th>属性</th>
					<th>大类</th>
					<th>小类</th>
					<th>规格</th>
					<th>成本价</th>
					<th>售价</th>
					<th>参考价</th>
					<th>活动价</th>
					<th>品牌</th>
					<th>上下架</th>
					<th>作废</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty commodityList}">
						<c:forEach items="${commodityList}" var="list" varStatus="vs">
						<tr style="text-align: center">
							<td><label>
								<input type="checkbox" name="commodityId" value="${list.id }"/>${list.id }
							</label></td>
							<td>
								<img src="${list.smallPic }" style="width: 50px; height: 50px;">
							</td>
							<td>${list.commodityName }</td>
							<td>${list.alias }</td>
							<td>${list.sku}</td>
							<td>
								<c:forEach items="${cityList}" var="city">
									<c:if test="${city.code == list.areaCode}">
										${city.name}
									</c:if>
								</c:forEach>
							</td>
							<td title="${list.supplyNo }">${list.supplyName }</td>
							<td>${list.commodityAttr }</td>
							<td>${list.parentTypeName }</td>
							<td>${list.typeName }</td>
							<td>${list.weight }</td>
							<td>￥${list.costPrice }</td>
							<td>￥${list.originalPrice }</td>
							<td>￥${list.marketPrice }</td>
							<td>￥${list.promotionPrice }</td>
							<td>${list.brand}</td>
							<td>
								<c:choose>
									<c:when test="${list.shelves == 1 }"><span class="text_green">上架</span></c:when>
									<c:when test="${list.shelves == 0 }"><span class="text_invalid">下架</span></c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.invalid == 1 }"><span class="text_red">是</span></c:when>
									<c:when test="${list.invalid == 0 }">否</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<td>
								<a href="/commodity/edit.html?id=${list.id }" style="color: #0000FF;text-decoration: underline">修改</a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="21" style="text-align: center">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">
			<strong>批量操作：</strong>
			<select id="slt_add2promotionArea" style="vertical-align: middle;width: 150px">
				<option value="">请选择商品专区</option>
				<c:forEach items="${promotionAreaList}" var="list">
					<option value="${list.id }" <c:if test="${vo.promotionAreaId==list.id}">selected</c:if>>${list.name }</option>
				</c:forEach>
			</select>
			<select id="slt_add2specialTopic" style="vertical-align: middle;width: 150px">
				<option value="">请选择专题</option>
				<c:forEach items="${specialTopicList}" var="list">
					<option value="${list.id }" >${list.name }</option>
				</c:forEach>
			</select>
			<select id="slt_add2activity" style="vertical-align: middle;width: 150px">
				<option value="">请选择活动</option>
				<c:forEach items="${activityList}" var="list">
					<option value="${list.id }" <c:if test="${vo.activityId==list.id}">selected</c:if>>${list.activityName }</option>
				</c:forEach>
			</select>
			<select id="slt_add2plate" style="vertical-align: middle;width: 150px">
				<option value="">请选择板块</option>
				<option value="mustBuy">业主推荐</option>
				<option value="familyPreferences">新品推荐</option>
				<option value="ishot">搜索推荐</option>
			</select>
			<a class="btn" id="btn_add2promotionArea" href="javascript:;">批量添加</a>
			<a class="btn" id="btn_updatePromotionAreaIdTo0" href="javascript:;" style="background-color: red">批量移除</a>
			<input type="button" id="btn_export" class="btn" value="导出商品列表" title="导出商品列表" />
		</div>
		${vo.page.pageStr }
	</div>
</div>

<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
<script type="text/javascript">

	$(function(){

		// 批量删除
		$('#btn_delete').click(function(){
			var len = $("input[name='id']:checked").length;
			if (len > 0) {
				if (confirm('此操作不可恢复，确定要这样做么？')) {
					var url = '${pageContext.request.contextPath }/commodity/delete.html';
					$.get(url, $('#deleteForm').serialize(), function(data){
						if (data == 'success') {
							window.location.reload();
						} else {
							alert(UNKNOWN_ERROR);
						}
					});
				}
			} else {
				alert('请选择需要删除的商品');
			}
		});
		
		$('#importFile').click(function(){
			$('#importFile').val('正在导入...');
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($('#uploadFile')[0].files, function(i, file) {
				data.append('fileName', file);
			});

			data.append('update', false);

			$.ajax({
				url: '/commodity/batchImport.html',
				type: 'POST',
				data: data,
				cache: false,
				contentType: false, //不可缺
				processData: false, //不可缺
				success: function(resultMap){
					if (resultMap.result == 'success') {
						$('#importFile').val('成功！');
						alert('数据导入成功！');
					} else if (!resultMap.result) {
						alert(UNKNOWN_ERROR);
					} else {
						alert(resultMap.result);
					}
					window.location.reload();
				},
				error: function(){
					alert('请先选择您要上传的文件');
					window.location.reload();
				}
			});
		});

		$('#modifiedFileBtn').click(function(){
			$(this).val('正在导入...');
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($('#modifiedFile')[0].files, function(i, file) {
				data.append('fileName', file);
			});

			data.append('update', true);

			$.ajax({
				url: '/commodity/batchImport.html',
				type: 'POST',
				data: data,
				cache: false,
				contentType: false, //不可缺
				processData: false, //不可缺
				success: function(resultMap){
					if (resultMap.result == 'success') {
						$(this).val('成功！');
						alert('数据修改成功！');
					} else if (!resultMap.result) {
						alert(UNKNOWN_ERROR);
					} else {
						alert(resultMap.result);
					}
					window.location.reload();
				},
				error: function(){
					alert('请先选择您要上传的文件');
					window.location.reload();
				}
			});
		});

		
		// 批量添加商品到指定的模块中(一次只能添加一个模块)
		$('#btn_add2promotionArea').click(function() {

			var promotionAreaId = $('#slt_add2promotionArea').val();	//专区id
			var specialTopicId = $('#slt_add2specialTopic').val();		//专题id
			var activityId = $('#slt_add2activity').val();				//活动id
			var plate = $('#slt_add2plate').val();						//板块字段

			var count = 0;
			var commodityId = $('#deleteForm').serialize();
			var len = $("input[name='commodityId']:checked").length;
			if (len > 0) {
				//一次只能添加一个模块
				if (promotionAreaId!="" ) {
					count++;
				}
				if (specialTopicId!="" ) {
					count++;
				}
				if (activityId!="" ) {
					count++;
				}
				if (plate!="" ) {
					count++;
				}
				if (count==0) {
					alert('请选择需要添加商品的模块!');
					return false;
				}else if (count>1) {
					alert('一次只能批量添加到一个模块中!');
					return false;
				}
				//批量添加商品到专区中
				if (promotionAreaId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/add2promotionArea.html?';
					var data = commodityId + '&promotionAreaId=' + promotionAreaId;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('添加成功，即将为你查询该专区下的商品...');
							window.location.href = '/commodity/list.html?promotionAreaId='+promotionAreaId;
						} else {
							alert('未知错误');
						}
					});
				}

				//批量添加商品到专题中
				if (specialTopicId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/add2specialTopic.html?';
					var data = commodityId + '&specialTopicId=' + specialTopicId;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('添加成功，即将为你查询该专题下的商品...');
							window.location.href = '/commodity/list.html?specialTopicId='+specialTopicId;
						} else {
							alert('未知错误');
						}
					});
				}


				//批量添加商品到活动中
				if (activityId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/add2activity.html?';
					var data = commodityId + '&activityId=' + activityId;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('添加成功，即将为你查询该活动下的商品...');
							window.location.href = '/commodity/list.html?activityId='+activityId;
						} else {
							alert('未知错误');
						}
					});
				}


				//批量添加商品到板块中
				if (plate && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/add2plate.html?';
					var data = commodityId + '&plate=' + plate;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('添加成功，即将为你查询该活动下的商品...');
							window.location.href = '/commodity/list.html?plate='+plate;
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请先选择商品！');
			}
		});
		
		
		// 将指定的商品从制定模块中移除
		$('#btn_updatePromotionAreaIdTo0').click(function() {

			var promotionAreaId = $('#slt_add2promotionArea').val();	//专区id
			var specialTopicId = $('#slt_add2specialTopic').val();		//专题id
			var activityId = $('#slt_add2activity').val();				//活动id
			var plate = $('#slt_add2plate').val();						//板块字段

			var count=0;
			var commodityId = $('#deleteForm').serialize();
			var len = $("input[name='commodityId']:checked").length;
			if (len > 0) {
				//一次只能移除一个模块的商品
				if (promotionAreaId!="" ) {
					count++;
				}
				if (specialTopicId!="" ) {
					count++;
				}
				if (activityId!="" ) {
					count++;
				}
				if (plate!="" ) {
					count++;
				}
				if (count==0) {
					alert('请选择需要移除商品的模块!');
					return false;
				}else if (count>1) {
//					alert(count);
					alert('一次只能批量移除一个模块中的商品!');
					return false;
				}

				//将指定商品从专区中批量移除
				if (promotionAreaId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/updatePromotionAreaIdTo0.html?';
					url = url + commodityId + '&promotionAreaId=' + promotionAreaId;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('移除成功，即将为你查询该分区下的商品!');
							window.location.href = '/commodity/list.html?promotionAreaId='+promotionAreaId;
						} else {
							alert('未知错误');
						}
					});
				}


				//将指定商品从活动中批量移除
				if (activityId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/updateActivityIdTo0.html?';
					url = url + commodityId + '&activityId=' + activityId;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('移除成功，即将为你查询该活动下的商品!');
							window.location.href = '/commodity/list.html?activityId='+activityId;
						} else {
							alert('未知错误');
						}
					});
				}


				//将指定商品从专题中批量移除
				if (specialTopicId && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/updateSpecialTopicIdTo0.html?';
					url = url + commodityId + '&specialTopicId=' + specialTopicId;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('移除成功，即将为你查询该专题下的商品!');
							window.location.href = '/commodity/list.html?specialTopicId='+specialTopicId;
						} else {
							alert('未知错误');
						}
					});
				}


				//批量移除板块中商品
				if (plate && confirm('不是手滑？')) {
					var url = '${pageContext.request.contextPath }/commodity/updateByPlate.html?';
					var data = commodityId + '&plate=' + plate;
					url = url + data;

					$.get(url, function(result) {
						if (result.error == 'success') {
							alert('移除成功，即将为你查询该板块下的商品...');
							window.location.href = '/commodity/list.html?plate='+plate;
						} else {
							alert('未知错误');
						}
					});
				}
			} else {
				alert('请先选择商品！');
			}
		});
		
		// 将筛选条件存入cookie，以便编辑后返回之前页面并刷新
		$.cookie('haj_commodity_list_params', $('#searchForm').serialize());

		$('#btn_export').click(function(){
			btn_disable($(this));
			location.href = '/commodity/exportCommodity.html?'+$('#searchForm').serialize();
			setTimeout('btn_enable($("#btn_export"))', 10000);
		});

		$('select#orderByClause').val('${vo.orderByClause}');
	});
</script>
</body>
</html>
