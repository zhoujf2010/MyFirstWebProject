
var epoint=function(){}

epoint.execute=function(actionname,funname,callback){
	$.ajax({
		type : "get",
		url : actionname + ".action?cmd=" + funname +"&t=" + Math.random(),
		success : function(msg) {
			var data=eval("(" +msg +")");
			$(":input").each(function(a,b){
				var field=$(b).attr("bind");
				if (field != null){
					var value=data[field];
					$(b).val(value);
				}
			});
			if (callback != null)
				callback(data);
		}
	});
}