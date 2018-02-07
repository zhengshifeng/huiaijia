<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>redis_value</title>
<script type="text/javascript" src="../../style/js/jquery.min.js"></script>
<style type="text/css">
p {
	outline: 1px solid #ccc;
	padding: 5px;
	margin: 5px;
}
</style>
</head>
<body>
	<p id="result"></p>
	<script type="text/javascript">
		$('#result').html(JSON.stringify('${redisValue}', null, '\t'));
	</script>
</body>
</html>