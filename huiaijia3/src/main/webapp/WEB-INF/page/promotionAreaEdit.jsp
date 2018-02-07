<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改促销专区</title>
	<jsp:include page="common/public_js_css.jsp" flush="true"/>
	<style>
		input, img {
			vertical-align: middle;
		}

		img {
			width: 300px;
		}
	</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		<a href="/promotionArea/list.html" class="actionBtn">促销专区管理</a>修改促销专区
	</h3>
	<form method="post" name="editForm" id="editForm">
		<input type="hidden" name="id" value="${vo.id }" />
		<table class="tableBasic" style="width: 100%;">
			<tr>
				<td>商品专区ID：</td>
				<td>${vo.id}</td>
			</tr>
			<tr>
				<td>专区名：</td>
				<td><input class="inpMain" type="text" name="name" value="${vo.name }"/></td>
			</tr>
			<tr>
				<td>专区简介：</td>
				<td><input class="inpMain" type="text" name="description" value="${vo.description }"/></td>
			</tr>
			<%--<c:if test="${isAdmin && vo.id == null}">--%>
				<tr>
					<td><label>所属城市：</label>：</td>
					<td>
						<select name="areaCode" id="areaCode">
							<c:forEach items="${cityList}" var="list">
								<option value="${list.code }" <c:if test="${vo.areaCode==list.code}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			<%--</c:if>--%>
			<%--<c:if test="${!isAdmin}">--%>
				<%--<input type="hidden" name="areaCode" value="${userSession.areaCode}"/>--%>
			<%--</c:if>--%>
			<tr>
				<td>专区类型：</td>
				<td>
					<select id="areaType" name="areaType" onchange="f1()"  style="width: 120px">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${vo.areaType=='1'}">selected</c:if>>品牌专区</option>
						<option value="2" <c:if test="${vo.areaType=='2'}">selected</c:if>>推荐专区</option>
						<option value="3" <c:if test="${vo.areaType=='3'}">selected</c:if>>商品类目</option>
						<option value="4" <c:if test="${vo.areaType=='4'}">selected</c:if>>商品分类</option>
					</select>

					<select id="areaTypeId" name="areaTypeId" style="width: 120px">
						<option value="-1">--请选择--</option>
						<c:if test="${vo.areaType=='1' || vo.areaType=='2'}">
							<option value="${list.id}" <c:if test="${vo.areaTypeId==0}">selected</c:if>>--无--</option>
						</c:if>
						<c:if test="${vo.areaType=='3'}">
							<c:forEach items="${cateList}" var="list">
								<option value="${list.id}" <c:if test="${vo.areaTypeId==list.id}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</c:if>
						<c:if test="${vo.areaType=='4'}">
							<c:forEach items="${categoryList}" var="list">
								<option value="${list.id}" <c:if test="${vo.areaTypeId==list.id}">selected</c:if>>${list.name }</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td>是否启用：</td>
				<td>
					<select id="display" name="display">
						<option value="">请选择</option>
						<option value="1" <c:if test="${vo.display=='1'}">selected</c:if>>已启用</option>
						<option value="0" <c:if test="${vo.display=='0'}">selected</c:if>>作废</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>排序：</td>
				<td><input class="inpMain short" type="number" name="sort" value="${vo.sort }"/></td>
			</tr>
			<tr>
				<td>首页图片：</td>
				<td>
					<c:set var="commonAttr" value="homeBanner"></c:set>
					<label>
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.homeBanner }" />
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
					</label>
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.homeBanner }" />
				</td>
			</tr>
			<tr>
				<td>商品列表头部图：</td>
				<td>
					<c:set var="commonAttr" value="banner3_2"></c:set>
					<label>
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.banner3_2 }" />
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
					</label>
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.banner3_2 }" />
				</td>
			</tr>
			<tr>
				<td>分类页面头部图：</td>
				<td>
					<c:set var="commonAttr" value="categoryBanner"></c:set>
					<label>
						<img alt="点击选择文件" title="点击选择文件" id="${commonAttr}Img" class="img_select banner" src="${vo.categoryBanner }" />
						<input class="inpFile" type="file" id="${commonAttr}File" accept=".jpg,.jpeg,.gif,.png"
							   data-attr="${commonAttr}" />
					</label>
					<input type="hidden" id="${commonAttr}" name="${commonAttr}" value="${vo.categoryBanner }" />
				</td>
			</tr>
			<tr>
				<td style="width: 150px;"></td>
				<td>
					<input id="btn_submit" class="btn" type="button" value="提交" />
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		$('#btn_submit').click(function() {
			if (confirm('确定不是手抖吗？')) {
				var url = '/promotionArea/save.html';
				$.post(url, $('#editForm').serialize(), function(data) {
					if (data == 'success') {
						alert('搞定啦！');
						window.location.href = '/promotionArea/list.html';
					} else {
						alert(UNKNOWN_ERROR);
					}
				});
			}
		});

		$('.inpFile').change(function() {
			var dataAttr = $(this).attr('data-attr');
			uploadImage($(this), $('#'+dataAttr+'Img'), $('#'+dataAttr));
		});

		$('#categoryId').val(${vo.categoryId});
		$('#display').val(${vo.display});
		$('#indexDisplay').val(${vo.indexDisplay});
		$('#categoryDisplay').val(${vo.categoryDisplay});
	});

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
			url : '/promotionArea/uploadFile.html',
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


	//通过专区类型得到相应的类目
	function f1(areaTypeId) {
		var areaType = $("#areaType").val();
		$.post("/promotionArea/getCateList.html", {
			parentId : $("#areaType").val()
		}, function(data) {
			$("#areaTypeId").empty();
			if (areaType==1 || areaType==2 ) {
				$("#areaTypeId").append("<option value='0'>--无--</option>");
			}else if (areaType==-1) {
				$("#areaTypeId").append("<option value='-1'>--请选择--</option>");
			}
			for ( var i in data) {
				var op = $("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
				$("#areaTypeId").append(op);
				if(areaTypeId!=null){
					$("#areaTypeId option").each(function(){
						if($(this).text()==areaTypeId){
							$(this).attr("selected",true)
						}
					})
				}
			}
		}, "json")
	}




</script>
</body>
</html>