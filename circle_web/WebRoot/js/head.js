//注册登陆关闭样式事件
$("#regist").live("click",function(){
	$(".mask").show();
	$(".reg").show();
});

$("#login").live("click",function(){
	$(".mask").show();
	$(".login").show();
	$("#viewId").val("1");
});