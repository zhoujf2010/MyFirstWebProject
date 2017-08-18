<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>Hello MUI</title>
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
	<div class="mui-content">
		<button type="button" class="mui-btn" onclick='fun1()'>默认</button>
	</div>
	<script src="../js/mui.js"></script>
	<script>
		mui.init({
			statusBarBackground : '#f7f7f7'
		});

		function fun1() {
			mui.openWindow({
				url : 'page1',
				id : 'page1',
				extras : {
					pam : "传值12",
					pam2 : "abc"
				}
			});
		}
	</script>
</body>

</html>