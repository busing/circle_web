$(document).ready(function(){
	getUserInfo(initUserInfo);
});

function toLogin(){
	window.location.href = page_url + "user/login.html?backurl=" + window.location;
}

function toReg(){
	window.location.href = page_url + "user/register.html";
}

function toCenter(){
	window.location.href = page_url + "mobile/usercenter_order.html?r=" + new Date().getTime();
}

function index(){
	window.location.href = page_url + "index.html";
}


function loginOut(){
	$.ajax({ 
		 type: "Post", //访问WebService使用Post方式请求 
		 contentType: "application/json", //WebService 会返回Json类型 
		 url: service_url + "user/logout.action", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		 data: '', //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到 	
		 success: function (response) {
//			 window.location.href = page_url + "index.html";
			location.reload();
		 }
	 }); 
}

function getUserInfo(callback){
	var code = requestParams()["code"];
	$.ajax({ 
		 type: "Post", //访问WebService使用Post方式请求 
		 contentType: "application/json", //WebService 会返回Json类型
		 url: service_url + "weixin/getuserinfo.action?code=" + code + "&r=" + new Date().getTime(), //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		 data: '', //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到 	
		 success: function (response) {
			if(response && !response.errcode){
				callback(response);
			}
		 }
	 }); 
}

function initUserInfo(response) {
	if(response.name && response.weixinImage){
		var html = '<div class="col-xs-2 msg">' + 
			        	'<i></i>' + 
			        '</div>' + 
			        '<div class="col-xs-8 center">' + 
			            '<i class="loc"></i>' + 
			            '<span class="tit">' + response.name + '</span>' + 
			            '<i class="arrow"></i>' + 
			        '</div>' + 
			        '<div class="col-xs-2 user"  onclick="toCenter()">' + 
			            '<img style="height: 25px;width: 25px; margin-left: 4px;margin-top: -3px;vertical-align: middle;" src="' + response.weixinImage + '" />' + 
			        '</div>' 
		$(".head").html(html);
	}
}