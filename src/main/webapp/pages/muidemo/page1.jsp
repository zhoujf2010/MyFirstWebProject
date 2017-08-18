<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="../css/mui.min.css">
<style type="text/css">
#list {
	/*避免导航边框和列表背景边框重叠，看起来像两条边框似得；*/
	margin-top: -1px;
}
</style>
</head>

<body>
	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">普通按钮</h1>
	</header>
	<div class="mui-content">
		第一页 <br />

		<button type="button" class="mui-btn" onclick='fun1()'>默认</button>

	</div>
	<script src="../js/mui.min.js"></script>
	<script src="../js/jquery-1.10.2.js"></script>
	<script src="../js/zjs.js"></script>
	<script>
		function fun1() {
			ezj.execute("back.do", {
				'pam1' : "aa",
				'pam2' : "bb中文cc",
			}, function(data) {
				alert(data.result);
			});
		}
	</script>
</body>

</html>