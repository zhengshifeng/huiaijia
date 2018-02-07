<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>取消WMS订单</title>
<link href="../../style/css/public.css?v=20160827" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../style/js/jquery.min.js"></script>
<script type="text/javascript" src="../../style/js/global.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>
		取消WMS订单
	</h3>
	
	<form method="post" name="editForm" id="editForm">
	<table class="tableBasic" style="width: 100%;  ">
		<tr class="info">
			<td width="150px">订单号:</td>
			<td><input type="text" name="ordeno" id="ordeno" class="inpMain" value=""/></td>
		</tr>
		
		<tr>
			<td></td>
			<td><input id="btn_submit" class="btn" type="button" value="提交" /></td>
		</tr>
	</table>
	</form>	
</div>
<script type="text/javascript">
$(function(){
	$('#btn_submit').click(function(){
		var url = '/hajwmscancle/wmsCancle.html';
		$.post(url, $('#editForm').serialize(), function(data){
			if (data == 'success') {
				alert('取消成功');
			} else {
				alert('未知错误');
			}
		});
	});
	
});
</script>
</body>
</html>