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
	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">普通按钮</h1>
	</header>
	<div class="mui-content">
		<div class="mui-content-padded">
			<h5>有底色按钮：</h5>
			<button type="button" class="mui-btn">默认</button>
			<div class="mui-btn mui-btn-primary">蓝色</div>
			<span class="mui-btn mui-btn-success"> 绿色 </span>
			<button type="button" class="mui-btn mui-btn-warning">黄色</button>
			<button type="button" class="mui-btn mui-btn-danger">红色</button>
			<button type="button" class="mui-btn mui-btn-royal">紫色</button>

			<h5 style="margin-top: 10px;">无底色按钮（使用父元素的背景色）：</h5>
			<button type="button" class="mui-btn mui-btn-outlined">默认</button>
			<button type="button" class="mui-btn mui-btn-primary mui-btn-outlined">操作</button>
			<button type="button" class="mui-btn mui-btn-success mui-btn-outlined">成功</button>
			<button type="button" class="mui-btn mui-btn-warning mui-btn-outlined">警告</button>
			<button type="button" class="mui-btn mui-btn-danger mui-btn-outlined">危险</button>
			<button type="button" class="mui-btn mui-btn-royal mui-btn-outlined">高贵</button>
			<h5 style="margin-top: 15px;">链接按钮：</h5>
			<button type="button" class="mui-btn mui-btn-link">添加</button>
			<h5 style="margin-top: 10px;">默认input标签样式：</h5>
			<input type="button" value="type=button" /> <input type="reset" value="type=reset" /> <input type="submit" value="type=submit" />
			<h5 style="margin-top: 10px;">默认button标签样式：</h5>
			<button type="button" onclick="fun1()">按钮</button>
			<a href="#picture" class="mui-btn mui-btn-primary mui-btn-block mui-btn-outlined" style="padding: 5px 20px;">打开actionsheet</a>
		</div>
	</div>
	<div id="picture" class="mui-popover mui-popover-action mui-popover-bottom">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell"><a href="#">拍照或录像</a></li>
			<li class="mui-table-view-cell"><a href="#">选取现有的</a></li>
		</ul>
		<ul class="mui-table-view">
			<li class="mui-table-view-cell"><a href="#picture"><b>取消</b></a></li>
		</ul>
	</div>

	<script src="../js/mui.js"></script>
	<script src="../js/jquery-1.10.2.js"></script>
	<script src="../js/frame.js"></script>
	<script>
		mui.init({
			statusBarBackground : '#f7f7f7'
		});

		function fun1() {
			$.ajax({
				type : "post",
				url : "../weixinAction2/fun1?t=" + Math.random(),
				data : "{'xxx':'222'}",
				success : function(msg) {
					alert(msg);
				},
				error:function(msg){
					alert(msg);
				}
			});
		}
	</script>
</body>
</html>