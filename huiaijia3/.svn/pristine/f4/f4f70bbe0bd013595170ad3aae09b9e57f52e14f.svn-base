<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类目管理</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
<script type="text/javascript" src="../../style/js/jquery.js"></script>
<style type="text/css">
	input[type="file"] {
		opacity: 0;
		filter: alpha(opacity = 0);
		width: 0;
		height: 0;
	}
</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<div>
		<a href="javascript:history.go(-1)"><font class="btn">返回</font></a>
	</div>
	<br/>
	<br/>
	<div id="list">
		<form method="post" name="editForm" id="editForm">
			<input type="hidden" name="id" value="${confVo.id}" />
			<div class="filter">
				<strong>请选择:</strong>
				<select id="one" name="one" onchange="getCate(this.value,'two')" style="width: 150px" >
					<option value="0">一级类目</option>
				</select>&nbsp;&nbsp;&nbsp;
				<select id="two" name="two" onchange="getCate(this.value,'three')" style="width: 150px" >
					<option value="0">二级类目</option>
				</select>&nbsp;&nbsp;&nbsp;
				<select id="three" name="three" style="width: 150px" >
					<option value="0">三级类目</option>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn" id="find" href="javascript:;">查找</a>
			</div>
		</form>

			<table style="width: 100%; text-align: center " class="tableBasic list" id="tb">
				<tr>
					<th>序号</th>
					<th>一级类目</th>
					<th>二级类目</th>
					<th>三级类目</th>
					<th>图片</th>
				</tr>
				<c:choose>
					<c:when test="${not empty listCate}">
						<c:forEach items="${listCate}" var="cate" varStatus="vs">
							<tr class="main_info" >
								<td width=70>${vs.index + 1 }</td>
								<td width=100>${cate.oneName}</td>
								<td width=100>${cate.towName}</td>
								<td width=100>${cate.threeName}</td>
								<td width=100>
									<div class="div">

										<img alt="选择文件" title="选择文件" id="${vs.index}Img" data-id="${vs.index}" src="${cate.icon}"
											 onclick="getElementById('${vs.index}File').click()" height="50" width="50">
										<input class="inpFlie" type="file" id="${vs.index}File" data-id="${vs.index}" accept=".jpg,.png" />
										<input type="hidden" id="${vs.index}Icon" data-id="${vs.index}" name="icon" value="${cate.icon}" />
										<input type="hidden" id="${vs.index}Id" data-id="${vs.index}" name="id" value="${cate.threeId}" />
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10">没有相关数据</td>
						</tr>

					</c:otherwise>
				</c:choose>

			</table>

	</div>

	<div class="clear"></div>
	<div class="page_and_btn" style="width: 100%">${vo.page.pageStr }</div>
</div>

<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
<script type="text/javascript">


	/**
	 * 上传图片时调用
	 */
	function uploadImage($fileId, $imageObj, $image, $id) {

		var data = new FormData();
		$.each($fileId[0].files, function(i, file) {
			data.append('fileName', file);
		});

		data.append('cateId',$id.val());

		$.ajax({
			url : '/commodity/uploadCateFile.html',
			type : 'post',
			data : data,
			cache : false,
			contentType : false, //不可缺
			processData : false, //不可缺
			success : function(result) {
				if (result.result = 'success') {
					$imageObj.attr('src', result.filePath);
					$image.val(result.filePath);
					alert('上传成功!');
					window.location.href = '/categoryThree/findCategoryById.html?&oneId=0&twoId=0&threeId=0';
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
		//一级类目
		var url = '/categoryThree/getTwoCate.html';
		$.post(url, {parentId:0}, function(data) {
			$.each(data, function(i, value) {
				$("#one").append($("<option value='"+value.id+"'>" + value.name + "</option>"));
			});
			}, "json"
		);

		$('.inpFlie').change(function() {
			var dataId = $(this).attr('data-id');
			var fileAdd = dataId+"File";
			var aa =document.getElementById(fileAdd).value.toLowerCase().split('.');		//以“.”分隔上传文件字符串

			if(aa[aa.length-1]=='jpg' ||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){	//判断图片格式
				var imagSize =  document.getElementById(fileAdd).files[0].size;
				if(imagSize<102400){
					uploadImage($(this), $('#'+dataId+'Img'), $('#'+dataId+'Icon'),$('#'+dataId+'Id'));
					return true;
				}else{
					alert("该图有"+imagSize/1024+"KB,请上传大小在100KB以内de图片!");
					return false;
				}
			}
			else {
				alert('请选择格式为*.jpg、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，
				return false;
			}
		});


		<%--查找--%>
		$('#find').click(function(){
			btn_disable($(this));
			var oneId = $('#one').val();		//一级类目id
			var twoId = $('#two').val();		//二级类目id
			var threeId = $('#three').val();	//三级类目id
			location.href = '/categoryThree/findCategoryById.html?'+'&oneId=' + oneId +'&twoId=' + twoId+ '&threeId=' + threeId;
		});

	});

	//二三级类目联动
	function getCate(parentId,cateId) {
		$.post("/categoryThree/getTwoCate.html", {
			parentId : parentId
		}, function(data) {
			$("#"+cateId).empty();
			$.each(data, function(i, value) {
				$("#"+cateId).append($("<option value='"+value.id+"'>" + value.name + "</option>"));
			});
		}, "json")
	}




</script>
</body>
</html>
