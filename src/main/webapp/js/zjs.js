(function() {
	var ezj = function() {
	}
	window.ezj = ezj;

	ezj.execute = function(url, param, callback) {
		// 扩展页面url参数
		var all = window.location.href;
		var index = all.indexOf('?');
		if (index != -1) {
			if (url.indexOf('?') > -1)
				url += '&' + all.substring(index + 1);
			else
				url += '?' + all.substring(index + 1);
		}

		mui.ajax({
			url : url,
			data : param,
			dataType : 'json',
			type : 'post',
			success : function(data) {
				if (callback != null)
					callback(data);
			},
			error : function(error) {
				mui.toast('请求失败！' + error);
			}
		});
	}

})();
