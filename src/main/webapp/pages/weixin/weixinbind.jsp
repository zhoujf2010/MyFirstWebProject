<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user bind</title>
<link rel="stylesheet" href="css/mui.min.css">
</head>
<body>
	<form action="weixinbind.do" method="post">
		<table>
			<tr>
				<td><label>用户名</label></td>
				<td><input type="text" name="username" style="width: 120;" /></td>
			</tr>
			<tr>
				<td><label>密&nbsp;码</label></td>
				<td><input type="password" name="password" style="width: 120;" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="login" value="登录" onclick="login()" /></td>
			</tr>
		</table>
	</form>
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/frame.js"></script>
	<script src="js/mui.js"></script>
	<script type="text/javascript">
		epoint.execute("weixingbindAction", "init", function(data) {
		});

		function login() {
			epoint.execute("weixingbindAction", "login", function(data) {
				alert(data.name);
			});
		}
	</script>
</body>
</html>