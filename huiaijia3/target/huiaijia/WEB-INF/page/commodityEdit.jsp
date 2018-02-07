<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js" language="javascript" charset="UTF8"></script>
<style type="text/css">
input[type="file"] {
	opacity: 0;
	filter: alpha(opacity = 0);
	width: 0;
	height: 0;
}

#smallPicImg {
	width: 100px;
	height: 100px;
	cursor: pointer;
	border: 1px dotted grey;
	background-color: grey;
}

#imagePathImg {
	width: 150px;
	height: 154px;
	cursor: pointer;
	border: 1px dotted grey;
	background-color: grey;
}
</style>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>
			<a href="/commodity/list.html" class="actionBtn">商品运营</a>
			<c:if test="${empty vo.id }">
			添加商品
			</c:if>
			<c:if test="${not empty vo.id }">
				<c:if test="${vo.commodityAttr=='团购'}">
				<a href="/commodityGroupBuying/edit.html?commodityId=${vo.id}" class="actionBtn">完善团购信息</a>
				</c:if>
			修改商品
			</c:if>
		</h3>
		<form method="post" name="editForm" id="editForm">
			<c:if test="${empty vo.id }">
				<input type="hidden" name="commodityNo" value="${vo.commodityNo }" />
			</c:if>
			<c:if test="${not empty vo.id }">
				<input type="hidden" name="id" value="${vo.id }" />
			</c:if>
			<table class="tableBasic" style="width: 100%;  ">
				<tr>
					<td width="150px;">
						商品小图(260 x 260)<br />商品大图(506 x 524)：
					</td>
					<td>
						<div style="float: left; width: 150px;">
							<c:set var="smallImageUrl" value="${smallImage }"></c:set>
							<c:if test="${not empty vo.smallPic }">
								<c:set var="smallImageUrl" value="${vo.smallPic }"></c:set>
							</c:if>
							<img alt="选择文件" title="选择文件" id="smallPicImg" src="${smallImageUrl }"
								onclick="getElementById('smallPicFile').click()" >
							<input class="inpFlie" type="file" id="smallPicFile" accept=".jpg,.jpeg,.gif,.png" />
							<input type="hidden" id="smallPic" name="smallPic" value="${vo.smallPic }" />
						</div>
						<div style="float: left;">
							<c:set var="bigImageUrl" value="${bigImage }"></c:set>
							<c:if test="${not empty vo.imagePath }">
								<c:set var="bigImageUrl" value="${vo.imagePath }"></c:set>
							</c:if>
							<img alt="选择文件" title="选择文件" id="imagePathImg" src="${bigImageUrl }"
								onclick="getElementById('imagePathFile').click()">
							<input class="inpFlie" type="file" id="imagePathFile" accept=".jpg,.jpeg,.gif,.png" />
							<input type="hidden" id="imagePath" name="imagePath" value="${vo.imagePath }" />
						</div>
					</td>
				</tr>
				<tr>
					<td>商品编号：</td>
					<td>${vo.commodityNo }</td>
				</tr>
				<tr>
					<td><label>商品物料码(sku)</label>：</td>
					<td>
						<c:if test="${empty vo.id }">
							<input class="inpMain required" type="text" name="sku" />
						</c:if>
						<c:if test="${not empty vo.id }">${vo.sku }</c:if>
					</td>
				</tr>
				<tr>
					<td><label>商品名称</label>：</td>
					<td>
						${vo.name}
					</td>
				</tr>
				<tr>
					<td><label>供货属性</label>：</td>
					<td>
						<c:if test="${vo.attribute=='0'}">自产</c:if>
						<c:if test="${vo.attribute=='1'}">采购</c:if>
						<c:if test="${vo.attribute=='2'}">备货</c:if>
					</td>
				</tr>
				<tr>
					<td><label>损耗百分比</label>：</td>
					<td>
						${vo.percentLoss}%
					</td>
				</tr>
				<tr>
					<td><label>erp商品id</label>：</td>
					<td>
						${vo.cloudsId}
					</td>
				</tr>
				<tr>
					<td><label>货位号</label>：</td>
					<td>				 
						<input class="inpMain short" type="text" name="storageNo" value="${vo.storageNo}" />
					</td>
				</tr>
				<c:if test="${empty vo.id && isAdmin}">
					<!-- 添加商品时才可以选择城市 -->
					<tr>
						<td><label>供应城市</label>：</td>
						<td>
							<select class="required" name="areaCode" id="areaCode">
								<c:forEach items="${cityList}" var="list">
									<option value="${list.code }" <c:if test="${vo.areaCode==list.code}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<c:if test="${!isAdmin}">
					<input type="hidden" name="areaCode" value="${userSession.areaCode}"/>
				</c:if>
				<tr>
					<td><label>商品别名</label>：</td>
					<td>
						<input class="inpMain required" type="text" name="alias" value="${vo.alias }" />
					</td>
				</tr>
				<tr>
					<td><label>商品简介</label>：</td>
					<td>
						<input class="inpMain required" type="text" name="description" value="${vo.description }" />
					</td>
				</tr>
				<tr>
					<td><label>商品类别</label>：</td>
					<td>
						<select name="commodityAttr" id="commodityAttr">
							<option value="不限">属性</option>
							<option value="生鲜" <c:if test="${vo.commodityAttr=='生鲜'}">selected</c:if>>生鲜</option>
							<option value="水果" <c:if test="${vo.commodityAttr=='水果'}">selected</c:if>>水果</option>
							<option value="团购" <c:if test="${vo.commodityAttr=='团购'}">selected</c:if>>团购</option>
							<option value="早餐" <c:if test="${vo.commodityAttr=='早餐'}">selected</c:if>>早餐</option>
						</select>
						<select name="parentTypeId" id="parentTypeId">
							<option value="0">大类</option>
							<c:forEach items="${parentTypeList}" var="list">
								<option value="${list.id }" <c:if test="${vo.parentTypeId==list.id}">selected</c:if>>${list.typeName }</option>
							</c:forEach>
						</select>
						<select class="required" name="typeId" id="typeId">
							<option value="0">小类</option>
							<c:forEach items="${subTypeList}" var="list">
								<option value="${list.id }" <c:if test="${vo.typeId==list.id}">selected</c:if>>${list.typeName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>供应商</label>：</td>
					<td>
						<select class="required" name="supplyChain" id="supplyChain">
							<option value="0">不限</option>
							<c:forEach items="${supplyChainNames}" var="list">
								<option value="${list.cloudsSupplierId}" <c:if test="${vo.supplyChain==list.cloudsSupplierId}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>品牌</label>：</td>
					<td>
						<select name="brandId" id="brandId">
							<option value="0">不限</option>
							<c:forEach items="${brands}" var="list">
								<option value="${list.id }" <c:if test="${vo.brandId==list.id}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>包装规格</label>：</td>
					<td>
						<input class="inpMain required" type="text" name="weight" value="${vo.weight }" />
					</td>
				</tr>
				<tr>
					<td><label>产地</label>：</td>
					<td>
						<input class="inpMain required" type="text" name="producer" value="${vo.producer }" />
					</td>
				</tr>
				<tr>
					<td><label>订单详情所在列</label>：</td>
					<td>
						<select class="required" name="orderClassification" id="orderClassification">
							<option value="A" <c:if test="${vo.orderClassification=='A'}">selected</c:if>>A</option>
							<option value="B" <c:if test="${vo.orderClassification=='B'}">selected</c:if>>B</option>
							<option value="C" <c:if test="${vo.orderClassification=='C'}">selected</c:if>>C</option>
							<option value="D" <c:if test="${vo.orderClassification=='D'}">selected</c:if>>D</option>
							<option value="E" <c:if test="${vo.orderClassification=='E'}">selected</c:if>>E</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>活动选择：</td>
					<td>
						<select name="activityId" id="activityId">
							<option value="0">不参与促销活动</option>
							<c:forEach items="${activityList}" var="list">
								<option value="${list.id }" <c:if test="${vo.activityId==list.id}">selected</c:if>>${list.activityName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>标签文案：</td>
					<td>
						<label>
							<input type="radio" name="mark" value="new" <c:if test="${vo.mark=='new'}">checked</c:if> />
							显示新品角标
						</label>
						&nbsp;
						<label>
							<input type="radio" name="mark" value="hot" <c:if test="${vo.mark=='hot'}">checked</c:if> />
							显示热销角标
						</label>
						&nbsp;
						<label>
							<input type="radio" name="mark" value="recommend" <c:if test="${vo.mark=='recommend'}">checked</c:if> />
							显示推荐角标
						</label>
						&nbsp;
						<label>
							<input type="radio" name="mark">
							无角标显示
						</label>
						&nbsp;
						<label>
							<input type="radio" name="mark">自定义
							<input class="inpMain" type="text" name="discountWord" value="${vo.discountWord }" placeholder="如：7.5折" />
						</label>
					</td>
				</tr>
				<tr>
					<td>商品水印：</td>
					<td>
						<label>
							<input type="checkbox" name="stamp" value="1" <c:if test="${vo.stamp==1}">checked</c:if> />
							显示全球精选印章
						</label>
					</td>
				</tr>
				<tr>
					<td>商品标签：</td>
					<td>
						<select id="slt_label3">
							<option value="">其他
							<option value="供港基地">供港基地
							<option value="已排酸">已排酸
						</select>
						<input class="inpMain" type="text" id="label3" name="label3" value="${vo.label3 }" placeholder="输入或右侧选择→" />
					</td>
				</tr>
				<tr>
					<td>商品作废：</td>
					<td>
						<label>
							<input type="checkbox" name="invalid" value="1" <c:if test="${vo.invalid==1}">checked</c:if> />
							作废
						</label>
						（作废后仓库管理员将不可见）
					</td>
				</tr>
				<tr>
					<td>商品专区：</td>
					<td>
						<label>
							<!-- 以前叫 家人喜好推荐，然后改成 猜你喜欢，现在改成 新品推荐 -->
							<input type="checkbox" name="familyPreferences" value="1" <c:if test="${vo.familyPreferences==1}">checked</c:if> />
							新品推荐
						</label>
						&nbsp;
						<label>
							<!-- 以前叫入店必买 -->
							<input type="checkbox" name="mustBuy" value="1" <c:if test="${vo.mustBuy==1}">checked</c:if> />
							业主推荐
						</label>
						&nbsp;
						<label>
							<input type="checkbox" name="ishot" value="1" <c:if test="${vo.ishot==1}">checked</c:if> />
							搜索中推荐
						</label>
					</td>
				</tr>
				<tr>
					<td>分拣批次：</td>
					<td>
						<input class="inpMain short" type="number" name="sortingBatch" value="${vo.sortingBatch }" />
					</td>
				</tr>
				<tr>
					<td>好评数：</td>
					<td>
						<input class="inpMain short" type="number" name="praise" value="${vo.praise }" />
					</td>
				</tr>
				<tr>
					<td>销量：</td>
					<td>
						<input class="inpMain short" type="number" name="salesVolume" value="${vo.salesVolume }" />
					</td>
				</tr>
				<tr>
					<td>排序：</td>
					<td>
						<input class="inpMain short" type="number" name="sort" value="${vo.sort }" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input id="btn_submit" class="btn" type="button" value="提交" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="passport" />
		</form>
	</div>

	<script type="text/javascript" src="../../style/js/jquery.cookie.js"></script>
	<script type="text/javascript">
		/**
		 * 上传图片时调用
		 */
		function uploadImage($fileId, $imageObj, $image) {
			//创建FormData对象
			var data = new FormData();

			//为FormData对象添加数据
			$.each($fileId[0].files, function(i, file) {
				data.append('fileName', file);
			});

			$.ajax({
				url : '/commodity/uploadFile.html',
				type : 'POST',
				data : data,
				cache : false,
				contentType : false, //不可缺
				processData : false, //不可缺
				success : function(result) {
					if (result.result = 'success') {
						$imageObj.attr('src', result.filePath);
						$image.val(result.filePath);
					} else {
						alert('我的天呐，上传失败了，赶紧问一下程序员GG');
					}
				},
				error : function() {
					alert('请先选择您要上传的图片');
				}
			});
		}

		$(function() {
			$('#btn_submit').click(function() {
				if (requiredCheck() && confirm('不是手抖吧？')) {
					window.parent.layer.load(2);
					btn_disable($('#btn_submit'));
					var url = '/commodity/save.html';
					$.post(url, $('#editForm').serialize(), function(data) {
						if (data.error == '0') {
							alert('好了！');
							window.parent.layer.closeAll('loading');
							<c:if test="${empty vo.id }">
							window.location.href = '/commodity/list.html';
							</c:if>
							<c:if test="${not empty vo.id }">
							window.location.href = '/commodity/list.html?'+$.cookie('haj_commodity_list_params');
							</c:if>
						} else {
							alert(data.msg);
							btn_enable($('#btn_submit'));
						}
					});
				}
			});

			$('#smallPicFile').change(function() {
				uploadImage($(this), $('#smallPicImg'), $('#smallPic'));
			});

			$('#imagePathFile').change(function() {
				uploadImage($(this), $('#imagePathImg'), $('#imagePath'));
			});

			$('#slt_label3').change(function() {
				$('#label3').val($(this).val());
			});

		});

		prependRedStar();
	</script>
</body>
</html>